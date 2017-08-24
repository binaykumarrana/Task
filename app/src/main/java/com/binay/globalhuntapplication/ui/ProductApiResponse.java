package com.binay.globalhuntapplication.ui;

import com.binay.globalhuntapplication.ui.Conversion;
import com.binay.globalhuntapplication.ui.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Binay.
 */
/*
* Api response with two model conversion and product
* */
public class ProductApiResponse {
    @SerializedName("conversion")
    private List<Conversion> conversionList;

    @SerializedName("title")
    private String title;

    @SerializedName("products")
    private List<Product> productList;

    public void setConversionList(List<Conversion> conversionList) {
        this.conversionList = conversionList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Conversion> getConversionList() {
        return conversionList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ProductApiResponse{" +
                "conversionList=" + conversionList +
                ", title='" + title + '\'' +
                ", productList=" + productList +
                '}';
    }
}
