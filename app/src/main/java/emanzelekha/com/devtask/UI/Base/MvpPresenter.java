
package emanzelekha.com.devtask.UI.Base;


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();




}
