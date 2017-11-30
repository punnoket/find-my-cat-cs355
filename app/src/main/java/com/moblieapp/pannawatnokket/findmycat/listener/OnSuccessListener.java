package com.moblieapp.pannawatnokket.findmycat.listener;

import com.moblieapp.pannawatnokket.findmycat.entity.User;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 23/11/2017 AD.
 */

public interface OnSuccessListener {
    void isSuccess(boolean isSuccess);
    void getUser(ArrayList<User> userArrayList);
}
