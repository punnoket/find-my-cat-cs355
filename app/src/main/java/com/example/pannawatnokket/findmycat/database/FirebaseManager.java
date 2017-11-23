package com.example.pannawatnokket.findmycat.database;

/**
 * Created by pannawatnokket on 23/11/2017 AD.
 */

import android.support.annotation.NonNull;

import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.listener.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
        userReference = reference.child(" ");
    }

    public void addFood(User user, final OnSuccessListener listener) {
        userReference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.isSuccess(task.isComplete());
            }
        });
    }

    public void getAllUserHighScore(final OnSuccessListener listener) {
        userArrayList = new ArrayList<>();
        query = userReference.orderByChild("score");
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
                listener.getUser(userArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
