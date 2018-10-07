package emanzelekha.com.devtask.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import emanzelekha.com.devtask.CustomClasses.RecyclerViewPositionHelper;
import emanzelekha.com.devtask.MVP.Model.RecyclerModel;
import emanzelekha.com.devtask.MVP.Model.adapter.RecyclerAdapter;
import emanzelekha.com.devtask.MVP.Presenter.MainActivityImpPresenter;
import emanzelekha.com.devtask.MVP.Presenter.MainActivityPresenter;
import emanzelekha.com.devtask.MVP.View.MainActivityView;
import emanzelekha.com.devtask.R;
import emanzelekha.com.devtask.UI.Base.BaseActivity;
import emanzelekha.com.devtask.Utilies.NotificationService;

public class MainActivity extends BaseActivity implements MainActivityView {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.activity_main)
    SwipeRefreshLayout activity_main;
    public static boolean work;
    public static String PagesNum;
    ProgressDialog progressDialog;
    private List<RecyclerModel> disList = new ArrayList<>();
    private RecyclerAdapter mAdapter;
    int DataLength = 0;
    int num = 2;
    private int findLastVisibleItemPosition;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    Boolean flag = true;

    RecyclerViewPositionHelper mRecyclerViewHelper;
    @Inject
    MainActivityPresenter<MainActivityView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnBinder(ButterKnife.bind(this));
        mPresenter = new MainActivityImpPresenter<>();
        mPresenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp() {
        final Intent intent=new Intent(this,NotificationService.class);
        startService(intent);

        ////////////////////////////////////////////////////////////////////////////////////////////
        mAdapter = new RecyclerAdapter(disList);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        PagesNum = "1";
        mPresenter.GetData(this, 1, num);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mRecyclerViewHelper.getItemCount();
                firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                findLastVisibleItemPosition=mRecyclerViewHelper.findLastCompletelyVisibleItemPosition();
                if (findLastVisibleItemPosition
                        == DataLength-1&&flag) {
                    // End has been reached
                    // Do something
                    mPresenter.GetData(getApplicationContext(), 2, num);

                    PagesNum = num + "";
                    num ++;


                }



            }


        });

//////////////////////////////////////////////////////////////////////////////////////////////////


        activity_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPresenter.isNetworkConnected(getApplicationContext())) {
                    disList.clear(); //clear list
                    mAdapter.notifyDataSetChanged();
                    num=2;
                    PagesNum = "1";
                    DataLength=0;
                    mPresenter.GetData(getApplicationContext(), 2, num);


                    Toast.makeText(MainActivity.this, "Refresh", Toast.LENGTH_SHORT).show();
                } else {
                    activity_main.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "No Network Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void ShowProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Waite...");
        progressDialog.show();
    }

    @Override
    public void HideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void SetRefrash(boolean refrash) {
        activity_main.setRefreshing(refrash);
    }

    @Override
    public void AddDataSize(int size) {
        DataLength += size;
    }

    @Override
    public void AddAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void AddData(RecyclerModel recyclerModel) {
        disList.add(recyclerModel);
    }

    @Override
    public void AddFlag(boolean flag1) {
        flag = flag1;
    }
}
