package emanzelekha.com.devtask.MVP.Presenter;

import android.content.Context;

import emanzelekha.com.devtask.MVP.View.MainActivityView;
import emanzelekha.com.devtask.UI.Base.MvpPresenter;

public interface MainActivityPresenter<V extends MainActivityView> extends MvpPresenter<V> {
    void GetData(Context context, int Request,int PagesNum);
    boolean isNetworkConnected(Context context);
}
