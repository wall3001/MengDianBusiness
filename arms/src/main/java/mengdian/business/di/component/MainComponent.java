package mengdian.business.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import mengdian.business.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;

import mengdian.business.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}