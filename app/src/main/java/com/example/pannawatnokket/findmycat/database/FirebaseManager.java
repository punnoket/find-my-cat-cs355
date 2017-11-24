package com.example.pannawatnokket.findmycat.database;

/**
 * Created by pannawatnokket on 23/11/2017 AD.
 */

import android.support.annotation.NonNull;

import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.listener.OnDataSuccessListener;
import com.example.pannawatnokket.findmycat.listener.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class FirebaseManager {
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userReference;
    private Query query;
    private ArrayList<User> userArrayList;

    public FirebaseManager() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        userReference = reference.child("user");
    }

    public void addUser(User user, final OnSuccessListener listener) {
        userReference.push().setValue(user) .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.isSuccess(task.isComplete());
            }
        });
    }

    public void getAllUserHighScore(final OnSuccessListener listener) {
        userArrayList = new ArrayList<>();
        query = userReference.orderByChild("score").limitToLast(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Map map = (Map) postSnapshot.getValue();
                    User user = new User();
                    user.setName(map.get("name").toString());
                    user.setScore(Integer.parseInt(map.get("score").toString()));
                    userArrayList.add(user);
                }
                Collections.reverse(userArrayList);
                listener.getUser(userArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getKeyCreate(final OnDataSuccessListener listener) {
        final String[] key = new String[1];
        query = userReference.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listener.getKey(dataSnapshot.getKey());
                query.removeEventListener(this);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateScore(User user) {
        userReference.child(user.getIdGlobal()).setValue(user);
    }

    public void deleteUser(User user) {
        userReference.child(user.getIdGlobal()).removeValue();
    }
}
