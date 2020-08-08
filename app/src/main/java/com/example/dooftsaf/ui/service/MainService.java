package com.example.dooftsaf.ui.service;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.dooftsaf.R;
import com.example.dooftsaf.ui.MainActivity;
import com.example.dooftsaf.ui.notification.NewOrderNotification;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainService extends Service  {
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private NotificationManagerCompat notificationManagerCompat;

    public MainService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service
        return null;
    }

    public void onCreate(){
        super.onCreate();
        Log.d("hahaha",mAuth.getCurrentUser().getUid());
        this.notificationManagerCompat = NotificationManagerCompat.from(this);


    }

    public int onStartCommand(Intent intent, int flags, int startId){
        mFirebaseFirestore.collection("orders").whereEqualTo("shipper",mAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("err", "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("username") != null) {
                                sendOnChannel1(doc.getString("username"));
                            }
                        }
                    }
                });
        return START_STICKY;
    }

    private void sendOnChannel1(String message)  {
        String title = "Đơn hàng mới";

        Notification notification = new NotificationCompat.Builder(this, NewOrderNotification.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }


}
