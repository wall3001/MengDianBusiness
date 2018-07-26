package mengdian.business.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mengdian.business.R;
import mengdian.business.app.utils.CheckUtils;
import mengdian.business.di.component.DaggerLoginComponent;
import mengdian.business.di.module.LoginModule;
import mengdian.business.mvp.contract.LoginContract;
import mengdian.business.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_user_code)
    Button loginUserCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeTimer();

    }

    boolean hasSend;
    private String phone;
    @OnClick({R.id.login_user_code, R.id.login_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_user_code:
                if (!hasSend) {
                    if(TextUtils.isEmpty(loginName.getText().toString())){
                        showMessage("请输入手机号");
                    }else{
                         phone = loginName.getText().toString();
                        if(!CheckUtils.isMobile(phone)){
                            showMessage("手机号码格式不正确");
                            return;
                        }
                        if (mPresenter != null) {
                            mPresenter.sendCode(phone);
                            startTime();
                        }
                    }
                }
                break;
            case R.id.login_submit:
                if (mPresenter != null) {
                    mPresenter.login(loginPwd.getText().toString(), loginName.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    private Disposable mDisposable;

    private void startTime() {
        int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .map(new Function<Long, Long>() {

                    /**
                     * Apply some calculation to the input value and return some other value.
                     *
                     * @param aLong the input value
                     * @return the output value
                     * @throws Exception on error
                     */
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if(loginUserCode!=null){
                            if(!hasSend){
                                hasSend = true;
                            }
                            loginUserCode.setText(String.format(getResources().getString(R.string.format_timer), aLong));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        closeTimer();
                    }
                });


    }

    private void closeTimer() {
        hasSend = false;
        if(loginUserCode!=null){
            loginUserCode.setText("发送验证码");
        }
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

}
