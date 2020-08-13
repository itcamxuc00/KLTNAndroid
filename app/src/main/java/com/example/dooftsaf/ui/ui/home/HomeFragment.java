package com.example.dooftsaf.ui.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.dooftsaf.R;
import com.example.dooftsaf.ui.LoginActivity;
import com.example.dooftsaf.ui.MainActivity;
import com.example.dooftsaf.ui.MapActivity;
import com.example.dooftsaf.ui.adapter.DetailOrderAdapter;
import com.example.dooftsaf.ui.common.Common;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView txtId = root.findViewById(R.id.txtOrder);
        TextView txtResName = root.findViewById(R.id.textViewRestaurant);;
        TextView txtResAdress = root.findViewById(R.id.textViewResAddress);
        TextView txtUsername = root.findViewById(R.id.textViewCsName);
        TextView txtUserPhone = root.findViewById(R.id.textViewCsPhone);
        TextView txtUserAddress = root.findViewById(R.id.textViewCsAdress);
        ListView lsvDetail = root.findViewById(R.id.lsvDetail);
        TextView txtShippingFee = root.findViewById(R.id.textViewShippingFee);
        TextView txtTotal = root.findViewById(R.id.textViewTotal);
        ((AppCompatActivity) getActivity()).setTitle("Đơn hàng hiện tại");
        if(Common.curentOrder != null){
            txtId.setText(Common.curentOrder.getId());
            txtResAdress.setText(Common.curentOrder.getRestaurantAddress());
            txtResName.setText(Common.curentOrder.getRestaurantName());
            txtUsername.setText(Common.curentOrder.getUsername());
            txtUserAddress.setText(Common.curentOrder.getAddress());
            txtUserPhone.setText(Common.curentOrder.getPhoneNumber());
            txtShippingFee.setText(""+Common.curentOrder.getShippingFee());
            txtTotal.setText(""+Common.curentOrder.getTotalPrice());
            DetailOrderAdapter adapter = new DetailOrderAdapter(getContext(),R.layout.product_item,Common.curentOrder.getDetail());
            lsvDetail.setAdapter(adapter);
        }

        Button btnButton = root.findViewById(R.id.buttonViewMap);
        btnButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}