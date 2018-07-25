package mengdian.business.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import mengdian.business.di.module.LoginModule;

import com.jess.arms.di.scope.ActivityScope;

import mengdian.business.mvp.ui.activity.LoginActivity;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}