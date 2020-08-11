package com.example.dooftsaf.ui.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dooftsaf.R;
import com.example.dooftsaf.ui.adapter.OrdersAdapter;
import com.example.dooftsaf.ui.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.google.firebase.firestore.Query.Direction.DESCENDING;


public class HistoryFragment extends Fragment {

    private static ArrayList<Order> mArrayList = new ArrayList<>();;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ListView listView;
    OrdersAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        ((AppCompatActivity) getActivity()).setTitle("Đơn hàng hiện tại");
        listView = root.findViewById(R.id.listViewOrders);
        getListItems();
        return root;
    }

    private void getListItems() {
        mFirebaseFirestore.collection("orders").orderBy("date", DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            mArrayList.clear();
                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Order order = snapshot.toObject(Order.class);
                                order.setId(snapshot.getId());
                                if (mAuth.getCurrentUser().getUid().equals(order.getShipper()))
                                    mArrayList.add(order);
                            }

                            adapter = new OrdersAdapter(getContext(), R.layout.list_order_item, mArrayList);
                            listView.setAdapter(adapter);

                        }
                    }
                });
    }
}