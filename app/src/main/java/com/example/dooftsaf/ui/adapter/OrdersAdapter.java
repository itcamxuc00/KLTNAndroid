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

import java.text.SimpleDateFormat;
import java.util.List;

public class OrdersAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Order> orderList;

    public OrdersAdapter(Context context, int layout, List<Order> orders) {
        this.context= context;
        this.layout = layout;
        this.orderList = orders;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,null);
        TextView txtId = view.findViewById(R.id.textViewID);
        TextView txtDate = view.findViewById(R.id.textViewDate);
        TextView txtUsername = view.findViewById(R.id.textViewUsername);
        TextView txtAddress = view.findViewById(R.id.textViewAdress);
        TextView txtPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        TextView txtPrice = view.findViewById(R.id.textViewPrice);
        TextView txtStatus = view.findViewById(R.id.textViewStatus);

        Order order = orderList.get(i);
        txtId.setText(order.getId().substring(0,9));
        @SuppressLint("SimpleDateFormat") String sDate = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(order.getDate());
        txtDate.setText(sDate);
        txtUsername.setText(order.getUsername());
        txtAddress.setText(order.getAddress());
        txtPhoneNumber.setText(order.getPhoneNumber());
        txtPrice.setText("" + order.getTotalPrice());
        txtStatus.setText(order.getStatus());

        return view;
    }
}
