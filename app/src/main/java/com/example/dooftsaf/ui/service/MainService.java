package com.example.dooftsaf.ui.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dooftsaf.R;
import com.example.dooftsaf.ui.common.Common;
import com.example.dooftsaf.ui.model.ObjLocation;
import com.example.dooftsaf.ui.model.Order;
import com.example.dooftsaf.ui.model.User;
import com.example.dooftsaf.ui.notification.NewOrderNotification;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainService extends Service  {
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private NotificationManagerCompat notificationManagerCompat;

    User user;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    Location mLastLocation;
    int minimumDistanceBetweenUpdates = 60;

    public MainService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service
        return null;
    }

    public void onCreate(){
        super.onCreate();

        user = Common.currentUser;
        this.notificationManagerCompat = NotificationManagerCompat.from(this);

        buildLocationRequest();
        buildLocationCallBack();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
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
                            if (doc.getString("status").equals("Đang giao hàng")) {
                                Common.curentOrder = doc.toObject(Order.class);
                                Common.curentOrder.setId(doc.getId());
                                mFirebaseFirestore.collection("restaurants").document(doc.getString("restaurant"))
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Common.curentOrder.setRestaurantName(document.getString("name"));
                                                Common.curentOrder.setRestaurantAddress(document.getString("address"));
                                                sendOnChannel1(document.getString("name"));
                                            } else {
                                                Log.e("errr", "No such document");
                                            }
                                        } else {
                                            Log.e("errrr", "get failed with ", task.getException());
                                        }
                                    }
                                });
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


    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mLastLocation = locationResult.getLastLocation();
                Common.mLocation = mLastLocation;
                user.setLocation(new ObjLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude()));
                mFirebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid())
                        .set(user);
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(10f);
        locationRequest.setInterval(180*1000);
        locationRequest.setFastestInterval(90 * 1000);
    }
}
