package emanzelekha.com.devtask.Utilies;


import java.util.List;

import emanzelekha.com.devtask.MVP.Model.ResultModle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;




public interface APIsInterface {
    @GET
    Call<List<ResultModle>> getData(@Url String url);


}
