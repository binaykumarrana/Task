package com.binay.globalhuntapplication.ui;

import com.binay.globalhuntapplication.CustomScope;
import com.binay.globalhuntapplication.NetComponent;

import dagger.Component;

/**
 * Created by Binay.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = ProductModule.class)
public interface ProductComponent {
    void inject(MainActivity mainActivity);
}
