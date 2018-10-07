
package emanzelekha.com.devtask.UI.Base;



import android.support.annotation.StringRes;


public interface MvpView {




    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);


    void hideKeyboard();



}
