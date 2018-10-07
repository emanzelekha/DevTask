package emanzelekha.com.devtask.Utilies;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import emanzelekha.com.devtask.MVP.Model.RecyclerModel;
import emanzelekha.com.devtask.MVP.Model.ResultModle;
import emanzelekha.com.devtask.R;
import emanzelekha.com.devtask.UI.Activities.MainActivity;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static emanzelekha.com.devtask.UI.Activities.MainActivity.work;
import static emanzelekha.com.devtask.Utilies.caching.REWRITE_CACHE_CONTROL_INTERCEPTOR;

public class NotificationService extends Service {

    List<ResultModle> recyclerModelCached = new ArrayList<>();
    List<ResultModle> recyclerModelNew = new ArrayList<>();
    Context context;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                try {
                    if (isNetworkConnected(context)) {
                        GetData(0);//getCach without network
                        work = true;
                        GetData(1);//get new with network


                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    //also call the same runnable to call it at regular interval
                    handler.postDelayed(this, 3600000);
                }
            }
        };
        handler.postDelayed(runnable, 3600000);
        return Service.START_STICKY;
    }


    public void GetData(final int flage) {
        //caching
        File httpCacheDirectory = new File(this.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache).build();
        ////////////////////////////////////////////////////////////////

        String URL = "https://api.github.com/users/square/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).client(client).
                addConverterFactory(GsonConverterFactory.create()).build();
        APIsInterface AllData = retrofit.create(APIsInterface.class);//conected api
        Call<List<ResultModle>> connection = AllData.getData(("repos?page=" + 2 + "&&per_page=10&?access_token=563052695d89c1208f90ff817e2896b723f0e79c"));


        connection.enqueue(new Callback<List<ResultModle>>() {
            @Override
            public void onResponse(Call<List<ResultModle>> call, Response<List<ResultModle>> response) {


                if (response != null) {
                    if (flage == 1) {
                        recyclerModelNew = response.body();
                        if (recyclerModelCached.size() > 0 && recyclerModelNew.size() > 0) {
                            if (recyclerModelCached.get(0).getName().equals(recyclerModelNew.get(0).getName())) {
                                Notification();
                            }

                        }
                    } else {
                        recyclerModelCached = response.body();
                    }


                }
            }

            @Override
            public void onFailure(Call<List<ResultModle>> call, Throwable t) {


            }
        });


    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void Notification() {
        NotificationManager Nm = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
        notification.setContentTitle(getResources().getString(R.string.app_name));
        notification.setColor(getResources().getColor(R.color.colorPrimaryDark));
        notification.setContentText("you Have New Data");
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent1);
        PendingIntent pendingIntent2 = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent2);
        Nm.notify(0, notification.build());

    }

}
