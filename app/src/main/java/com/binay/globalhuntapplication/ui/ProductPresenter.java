package com.binay.globalhuntapplication.ui;

import com.binay.globalhuntapplication.ApiInterface;
import com.binay.globalhuntapplication.MainApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Binay.
 */

/*
* Product presenter pattern seperating api calls from view to this presenter pattern, independent from Model and View
* */

public class ProductPresenter implements ProductContract.Presenter {
    public ProductContract.View view;
    private CompositeSubscription compositeSubscription;
    private Retrofit retrofit;

    @Inject
    ProductPresenter(Retrofit retrofit, ProductContract.View view) {
        this.view = view;
        this.retrofit = retrofit;
        this.compositeSubscription = new CompositeSubscription();
    }


    public void onStop() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public void getProducts() {
        view.showProgress();
        Subscription subscription;
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        subscription = Observable.interval(3, TimeUnit.MINUTES, scheduler)
                .takeWhile(aLong -> MainApplication.getInstance().isAppForeGround())
                .startWith(0L)
                .flatMap(aLong -> retrofit.create(ApiInterface.class).getAllProduct())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductApiResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        e.printStackTrace();
                        view.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(ProductApiResponse productApiResponse) {
                        /*
                        * Updating entire data to view through methods defined in ProductContract View
                        * */
                        view.hideProgress();
                        view.loadProducts(productApiResponse.getProductList());
                        view.loadConversion(productApiResponse.getConversionList());
                        view.showTitle(productApiResponse.getTitle());
                    }
                });

        compositeSubscription.add(subscription);
    }
}
