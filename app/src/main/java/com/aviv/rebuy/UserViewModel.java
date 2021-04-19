package com.aviv.rebuy;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.Product;
import com.aviv.rebuy.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {

    public User user=null;

    public UserViewModel(){
        getUser();
    }

    public void getUser() {
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        user = new User();
                        user.fromMap(task.getResult().getData());
                    }
                }
            }
        });
    }

    String getUserName(){return (user!=null)? user.getName():"User_Name"; }
    String getUserEmail(){return (user!=null)? user.getId() :"User_Email"; }
    String getUserPhone(){return (user!=null)? user.getPhoneNumber():"User_Phone"; }
}
