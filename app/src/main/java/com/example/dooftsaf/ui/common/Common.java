package com.example.dooftsaf.ui.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;

import com.example.dooftsaf.ui.model.Order;
import com.example.dooftsaf.ui.model.User;

public class Common {
    public static User currentUser = new User();
    public static Order curentOrder = null;
    public static Location mLocation = null;
}
