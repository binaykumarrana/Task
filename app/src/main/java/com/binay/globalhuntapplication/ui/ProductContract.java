package com.binay.globalhuntapplication.ui;


import java.util.List;

/**
 * Created by Binay.
 */

public interface ProductContract {
    interface View {
        /*
        * function to update product list to view
        * */
        void loadProducts(List<Product> products);

        /*
        * function to fetch conversion and update in view
        * */
        void loadConversion(List<Conversion> conversions);

        /*
        * function to update title
        * */
        void showTitle(String title);

        /*
        * function to update progress
        * */
        void showProgress();

        /*
        * function to update hiding progress
        * */

        void hideProgress();

        /*
        * function to show failure msg incase of network or fail in api
        * */
        void onFailure(String msg);
    }


    /*
    * Presenter to connect from presenter patter where all products fetched through API
    * */
    interface Presenter {
        void getProducts();

    }
}
