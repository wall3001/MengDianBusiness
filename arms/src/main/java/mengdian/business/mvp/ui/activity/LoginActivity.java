package mengdian.business.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import mengdian.business.R;
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

    boolean hasSend;
    String code;
    @OnClick({R.id.login_user_code, R.id.login_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_user_code:
                if (!hasSend) {
                    if (mPresenter != null) {
                        code = mPresenter.sendCode(loginName.getText().toString());
                    }
                }
                break;
            case R.id.login_submit:
                if (mPresenter != null) {
                    mPresenter.login(code,loginPwd.getText().toString(),loginName.getText().toString());
                }
                break;
            default:
                break;
        }
    }

}
