package com.binay.globalhuntapplication;

import com.binay.globalhuntapplication.ui.ProductApiResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Binay.
 */

public interface ApiInterface {
    /**
     * Gets all products
     *
     * @return the product list
     */
    @GET("assignment.json")
    Observable<ProductApiResponse> getAllProduct();
}
