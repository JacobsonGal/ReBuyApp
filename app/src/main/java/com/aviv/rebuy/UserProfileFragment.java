package com.aviv.rebuy;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviv.rebuy.Model.ModelFirebase;
import com.aviv.rebuy.Model.Product;
import com.aviv.rebuy.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    UserViewModel viewModel;

    private TextView user_name,user_email,user_phone;

    public UserProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        user_name = v.findViewById(R.id.user_name);
        user_email = v.findViewById(R.id.user_email);
        user_phone = v.findViewById(R.id.user_phone);

        viewModel.getUser ( new UserViewModel.GetUserListener() {
            @Override
            public void onComplete(User user) {
                user_name.setText((user!=null)? user.getName():"User_Name");
                user_email.setText((user!=null)? user.getId():"User_Email");
                user_phone.setText((user!=null)? user.getPhoneNumber():"User_Phone");
            }
        });
        return v;
    }
}