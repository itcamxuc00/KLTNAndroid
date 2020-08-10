package com.example.dooftsaf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dooftsaf.R;
import com.example.dooftsaf.ui.model.Order;
import com.example.dooftsaf.ui.model.Product;

import java.util.List;

public class DetailOrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Product> productList;

    public DetailOrderAdapter(Context context, int layout, List<Product> products) {
        this.context= context;
        this.layout = layout;
        this.productList = products;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,null);
        TextView txtName = view.findViewById(R.id.textViewPrName);
        TextView txtCount = view.findViewById(R.id.textViewPrCount);
        TextView txtPrice = view.findViewById(R.id.textViewPrPrice);

        Product product = productList.get(i);
        txtName.setText(product.getName());
        txtCount.setText("X" + product.getCount());
        txtPrice.setText("" + product.getTotalPrice());

        return view;
    }
}
