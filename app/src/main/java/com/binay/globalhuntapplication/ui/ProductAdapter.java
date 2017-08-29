package com.binay.globalhuntapplication.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binay.globalhuntapplication.R;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Binay on 23/08/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter_item, parent, false);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product != null) {
            Glide.with(context).load(product.getUrl()).into(holder.productImage);
            if (product.getCurrency() != null && !product.getCurrency().isEmpty()) {
                if (product.getCurrency().equalsIgnoreCase("INR"))
                    holder.price.setText(context.getResources().getString(R.string.Rs) + " " + product.getPrice());
                else if (product.getCurrency().equalsIgnoreCase("AED"))
                    holder.price.setText("AED " + product.getPrice());
                else
                    holder.price.setText("SAR " + product.getPrice());
            }
            holder.productName.setText(product.getName());
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProduct)
        ImageView productImage;
        @BindView(R.id.tvPrice)
        TextView price;
        @BindView(R.id.tvName)
        TextView productName;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
