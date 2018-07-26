package mengdian.business.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import mengdian.business.mvp.contract.LoginContract;
import mengdian.business.mvp.model.api.Api;
import mengdian.business.mvp.model.entity.BaseJson;
import mengdian.business.mvp.model.entity.User;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<User>> login(String phone, String code) {
        return null;
    }

    @Override
    public Observable<BaseJson<Object>> sendCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(Api.class).sendCode(phone);
    }
}