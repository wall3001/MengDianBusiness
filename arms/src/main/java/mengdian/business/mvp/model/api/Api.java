package mengdian.business.mvp.model.api;

import io.reactivex.Observable;
import mengdian.business.mvp.model.entity.BaseJson;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by MVPArmsTemplate
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {

    String APP_DOMAIN = "https://api.github.com";

    String REQUEST_SUCCESS = "0";


    @POST("sendCode")
    @FormUrlEncoded
    Observable<BaseJson<Object>> sendCode(@Field("phone") String phone);







}
