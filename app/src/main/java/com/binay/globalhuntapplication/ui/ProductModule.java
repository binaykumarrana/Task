package com.binay.globalhuntapplication.ui;

import com.binay.globalhuntapplication.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Binay.
 */
@Module
public class ProductModule {
    private final ProductContract.View mView;

    public ProductModule(ProductContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    ProductContract.View providesProductsContractView() {
        return mView;
    }
}
