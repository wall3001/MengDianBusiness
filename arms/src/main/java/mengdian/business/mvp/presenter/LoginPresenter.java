package mengdian.business.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.Random;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import mengdian.business.app.utils.RxUtils;
import mengdian.business.app.utils.VerificationCode;
import mengdian.business.mvp.contract.LoginContract;
import mengdian.business.mvp.model.entity.BaseJson;
import mengdian.business.mvp.model.entity.User;
import mengdian.business.mvp.ui.activity.MainActivity;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void sendCode(String phone) {

        mModel.sendCode(phone).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<Object> objectBaseJson) {
                        if (objectBaseJson.isSuccess()) {

                        }
                    }
                });


    }

    private String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++) {
                fourRandom = "0" + fourRandom;
            }
        }
        return fourRandom;
    }

    public void login(String phone,String code) {

        if (TextUtils.isEmpty(phone)) {
            mRootView.showMessage("请输入手机号");
        }

        if (TextUtils.isEmpty(code)) {
            mRootView.showMessage("请输入验证码");
        }
        mModel.login(phone,code).compose(RxUtils.applySchedulers(mRootView)).subscribe(new ErrorHandleSubscriber<BaseJson<User>>(mErrorHandler) {
            @Override
            public void onNext(BaseJson<User> userBaseJson) {
                if (userBaseJson.isSuccess()) {
                    User user = userBaseJson.getData();
                    ArmsUtils.startActivity(MainActivity.class);
                } else {
                    mRootView.showMessage(userBaseJson.getMsg());
                }
            }
        });


    }
}
