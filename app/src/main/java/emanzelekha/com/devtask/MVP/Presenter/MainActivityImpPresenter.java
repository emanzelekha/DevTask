package emanzelekha.com.devtask.MVP.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import emanzelekha.com.devtask.MVP.Model.RecyclerModel;
import emanzelekha.com.devtask.MVP.Model.ResultModle;
import emanzelekha.com.devtask.MVP.View.MainActivityView;
import emanzelekha.com.devtask.UI.Base.BasePresenter;
import emanzelekha.com.devtask.Utilies.APIsInterface;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static emanzelekha.com.devtask.Utilies.caching.REWRITE_CACHE_CONTROL_INTERCEPTOR;

public class MainActivityImpPresenter<V extends MainActivityView> extends BasePresenter<V> implements MainActivityPresenter<V> {
    @Override
    public void GetData(final Context context, final int Request,int PagesNum) {
        final Display display = ((WindowManager) context.getSystemService(context.getApplicationContext().WINDOW_SERVICE)).getDefaultDisplay();

        //caching
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
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
        Call<List<ResultModle>> connection = AllData.getData(("repos?page=" + PagesNum + "&&per_page=10&?access_token=563052695d89c1208f90ff817e2896b723f0e79c"));
        if (Request == 1) {
            getMvpView().ShowProgress();
        }

        connection.enqueue(new Callback<List<ResultModle>>() {
            @Override
            public void onResponse(Call<List<ResultModle>> call, Response<List<ResultModle>> response) {
                if (Request == 1) {
                    getMvpView().HideProgress();
                } else {
                    getMvpView().SetRefrash(false);
                }

                if (response != null) {
                    getMvpView().AddDataSize(response.body().size());
                    try {
                        for (int i = 0; i < response.body().size(); i++) {

                            RecyclerModel disUserControl = new RecyclerModel(response.body().get(i).getName(), response.body().get(i).getDescription()
                                    , response.body().get(i).getOwner().getLogin(), response.body().get(i).getFork()
                                    , response.body().get(i).getHtml_url(), response.body().get(i).getOwner().getHtml_url(), display.getWidth());
                            getMvpView().AddData(disUserControl);
                        }

                        getMvpView().AddAdapter();
                    } catch (Exception ex) {
                        Toast.makeText(context, "API rate limit exceeded", Toast.LENGTH_LONG).show();

                    }
                } else {
                   getMvpView().AddFlag(false);
                }
            }

            @Override
            public void onFailure(Call<List<ResultModle>> call, Throwable t) {


            }
        });


    }

    @Override
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

