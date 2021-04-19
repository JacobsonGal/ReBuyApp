package com.aviv.rebuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aviv.rebuy.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    UserViewModel viewModel;

    private TextView user_name,user_email,user_phone;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        ArrayList<String> userlist=viewModel.getList();
//        if(userlist!=null){
//            Log.d("user",userlist.get(0).toString());
//            Log.d("user",userlist.get(1).toString());
//            Log.d("user",userlist.get(2).toString());
            user_name = v.findViewById(R.id.user_name);
            user_name.setText(viewModel.getUserName());
            user_email = v.findViewById(R.id.user_email);
            user_email.setText(viewModel.getUserEmail());
            user_phone = v.findViewById(R.id.user_phone);
            user_phone.setText(viewModel.getUserPhone());
//        }

        return v;
    }
}