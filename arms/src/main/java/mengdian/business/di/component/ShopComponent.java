package mengdian.business.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import mengdian.business.di.module.ShopModule;

import com.jess.arms.di.scope.ActivityScope;

import mengdian.business.mvp.ui.activity.ShopActivity;

@ActivityScope
@Component(modules = ShopModule.class, dependencies = AppComponent.class)
public interface ShopComponent {
    void inject(ShopActivity activity);
}