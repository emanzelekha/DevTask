package emanzelekha.com.devtask.MVP.View;

import java.util.List;

import emanzelekha.com.devtask.MVP.Model.RecyclerModel;
import emanzelekha.com.devtask.UI.Base.MvpView;

public interface MainActivityView extends MvpView {
    void ShowProgress();
    void HideProgress();
    void SetRefrash(boolean refrash);
    void AddDataSize(int size);
    void AddAdapter();
    void AddData(RecyclerModel recyclerModel);
    void AddFlag(boolean flag);
}
