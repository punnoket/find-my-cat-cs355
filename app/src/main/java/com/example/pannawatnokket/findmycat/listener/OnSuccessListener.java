package com.example.pannawatnokket.findmycat.listener;

import com.example.pannawatnokket.findmycat.entity.User;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 23/11/2017 AD.
 */

public interface OnSuccessListener {
    boolean isSuccess(boolean isSuccess);
    ArrayList<User> getUser(ArrayList<User> userArrayList);
}
