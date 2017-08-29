package com.binay.globalhuntapplication.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binay.globalhuntapplication.MainApplication;
import com.binay.globalhuntapplication.R;
import com.binay.globalhuntapplication.netutil.GravitySnapHelper;
import com.binay.globalhuntapplication.netutil.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ProductContract.View {
    @BindView(R.id.rvProduct)
    RecyclerView rvProduct;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnINR)
    Button btnRupees;
    @BindView(R.id.btnAED)
    Button btnAed;
    @BindView(R.id.btnSAR)
    Button btnSar;
    @BindView(R.id.llCarasouelPager)
    LinearLayout linlay_pager;
    private ProgressDialog progressDialog;
    private List<Conversion> conversionList;
    private List<Product> productList, originalList;
    private ProductAdapter productAdapter;
    @Inject
    ProductPresenter productPresenter;
    private String selectedTag = "INR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        /*
        * Dagger components for products
        * */
        DaggerProductComponent.builder()
                .netComponent(((MainApplication) getApplicationContext()).getNetComponent())
                .productModule(new ProductModule(MainActivity.this))
                .build().inject(MainActivity.this);

        /*
        * Checking internet is connected or not if not show setting dialog
        * */
        if (NetworkUtil.isOnline(MainActivity.this)) {
            productPresenter.getProducts();
        } else {
            if (!MainApplication.getInstance().getNoInternetDialogVisibility())
                NetworkUtil.showNoInternetAlertDialog(MainActivity.this);
        }
        /*
        * Setting carasouel on scroll of item
        * */
        setScrollCarasouel();


    }

    /*
    * Scroll indication....
    * */
    private void setScrollCarasouel() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 5; i++) {
            ImageView mImageCirclePager = new ImageView(this);
            if (i == 0) {
                //Image resource used dummy can be changed custom later
                mImageCirclePager.setBackgroundResource(R.drawable.pagination_circle_selected);
            } else {
                mImageCirclePager.setBackgroundResource(R.drawable.pagination_circle_normal);
            }
            linlay_pager.addView(mImageCirclePager, layoutParams);
        }
        /*
        * List scroll listener to track item position
        * */
        rvProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int pos = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
                /*
                * pos = position of list item and bannerlist is number of carasouel button want to be created, can psss list size also but for smaller size OK
                * */
                actionButton(pos, 5);
            }
        });
    }

    private void actionButton(int position, int bannerListSize) {
        for (int i = 0; i < bannerListSize; i++) {
            ImageView imageView = (ImageView) linlay_pager.getChildAt(i);
            if (i == position) {
                imageView.setBackgroundResource(R.drawable.pagination_circle_selected);
            } else {
                imageView.setBackgroundResource(R.drawable.pagination_circle_normal);
            }
        }
    }


    /*
    * Initializing views
    * */
    private void initView() {
        productList = new ArrayList<>();
        originalList = new ArrayList<>();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading products...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void loadProducts(List<Product> products) {
        /*
        * product lists which can be manipulated but original to keep original item to maintain base price
        * */

        productList.addAll(products);
        originalList.addAll(products);
        /*
        * initializing and adding item to list with adapter
        * */
        productAdapter = new ProductAdapter(this, products);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProduct.setLayoutManager(mLayoutManager);
        rvProduct.setAdapter(productAdapter);
        /*
        * GravitySanphelper improves item scroll and their visibility based on portion of item visible on screen
        * */
        GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(rvProduct);

    }

    @Override
    public void loadConversion(List<Conversion> conversions) {
        this.conversionList = conversions;
        updateCurrency(selectedTag);
    }

    @Override
    public void showTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(MainActivity.this, "" + msg, Toast.LENGTH_LONG).show();
    }

    /*
    * currency listeners
    * */
    @OnClick(R.id.btnINR)
    public void inrSelected() {
        btnRupees.setBackgroundResource(R.drawable.currency_bg_selected);
        btnAed.setBackgroundResource(R.drawable.currency_bg_default);
        btnSar.setBackgroundResource(R.drawable.currency_bg_default);
        btnAed.setTextColor(getResources().getColor(android.R.color.black));
        btnSar.setTextColor(getResources().getColor(android.R.color.black));
        btnRupees.setTextColor(getResources().getColor(android.R.color.white));
        selectedTag = "INR";
        updateCurrency(selectedTag);
    }

    @OnClick(R.id.btnAED)
    public void aedSelected() {
        btnRupees.setBackgroundResource(R.drawable.currency_bg_default);
        btnAed.setBackgroundResource(R.drawable.currency_bg_selected);
        btnSar.setBackgroundResource(R.drawable.currency_bg_default);
        btnAed.setTextColor(getResources().getColor(android.R.color.white));
        btnSar.setTextColor(getResources().getColor(android.R.color.black));
        btnRupees.setTextColor(getResources().getColor(android.R.color.black));
        selectedTag = "AED";
        updateCurrency(selectedTag);
    }

    @OnClick(R.id.btnSAR)
    public void sarSelected() {
        btnRupees.setBackgroundResource(R.drawable.currency_bg_default);
        btnAed.setBackgroundResource(R.drawable.currency_bg_default);
        btnSar.setBackgroundResource(R.drawable.currency_bg_selected);
        btnAed.setTextColor(getResources().getColor(android.R.color.black));
        btnSar.setTextColor(getResources().getColor(android.R.color.white));
        btnRupees.setTextColor(getResources().getColor(android.R.color.black));
        selectedTag = "SAR";
        updateCurrency(selectedTag);
    }

    /*
    * updating price based on currency selected
    * */
    public void updateCurrency(String currency) {
        double conversionRateOne = 0, conversionRateTwo = 0, conversionRateThree = 0;
        for (Conversion conversion : conversionList) {
            if (conversion.getFrom().equalsIgnoreCase(currency)) {
                switch (conversion.getTo()) {
                    case "INR":
                        conversionRateOne = Double.valueOf(conversion.getRate());
                        break;
                    case "AED":
                        conversionRateTwo = Double.valueOf(conversion.getRate());
                        break;
                    case "SAR":
                        conversionRateThree = Double.valueOf(conversion.getRate());
                        break;
                    default:


                        break;
                }
            }
        }
        for (int i = 0; i < productList.size(); i++) {
            if (!originalList.get(i).getCurrency().equalsIgnoreCase(currency)) {
                switch (originalList.get(i).getCurrency()) {
                    case "INR":
                        productList.get(i).setCurrency(currency);
                        if (conversionRateOne > 0) {
                            productList.get(i).setPrice("" + round(conversionRateOne * Double.valueOf(originalList.get(i).getPrice()), 2));
                        }
                        break;
                    case "AED":
                        productList.get(i).setCurrency(currency);
                        if (conversionRateTwo > 0) {
                            productList.get(i).setPrice("" + round(conversionRateTwo * Double.valueOf(originalList.get(i).getPrice()), 2));
                        } else {
                            //Temporary used missing conversion rate INR->SAR and SAR->AED
                            if (currency.equalsIgnoreCase("SAR"))
                                productList.get(i).setPrice("" + round(.98 * Double.valueOf(originalList.get(i).getPrice()), 2));
                        }
                        break;
                    case "SAR":
                        productList.get(i).setCurrency(currency);
                        if (conversionRateThree > 0) {
                            productList.get(i).setPrice("" + round(conversionRateThree * Double.valueOf(originalList.get(i).getPrice()), 2));
                        } else {
                            //Temporary used missing conversion rate INR->SAR and SAR->AED
                            if (currency.equalsIgnoreCase("INR"))
                                productList.get(i).setPrice("" + round(0.06 * Double.valueOf(originalList.get(i).getPrice()), 2));
                        }
                        break;
                }
            }
        }

        productAdapter.notifyDataSetChanged();
    }

    /*
    * rounding price to two decimal value
    * */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
