package com.aviv.rebuy;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aviv.rebuy.Model.ModelFirebase;
import com.aviv.rebuy.Model.Product;
import com.aviv.rebuy.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    UserViewModel viewModel;

    private TextView user_name,user_email,user_phone;
    private ImageView user_img;
    private ProgressBar pb;
    private RelativeLayout rellay1,rellay2;
    private Button logout_btn;

    public UserProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        pb = v.findViewById(R.id.feed_progress);
        pb.setVisibility(View.VISIBLE);
        rellay1= v.findViewById(R.id.rellay1);
        rellay2= v.findViewById(R.id.rellay2);
        logout_btn=v.findViewById((R.id.profile_frag_logout));
        rellay1.setAlpha((float) 0.4);
        rellay2.setAlpha((float) 0.4);
        user_name = v.findViewById(R.id.user_name);
        user_email = v.findViewById(R.id.user_email);
        user_phone = v.findViewById(R.id.user_phone);
        user_img = v.findViewById(R.id.user_img);

        viewModel.getUser ( new UserViewModel.GetUserListener() {
            @Override
            public void onComplete(User user) {
                user_name.setText((user!=null)? user.getName():"User_Name");
                user_email.setText((user!=null)? user.getId():"User_Email");
                user_phone.setText((user!=null)? user.getPhoneNumber():"User_Phone");
                if(user.getImageUrl()!=null) {
                    Picasso.get().load(user.getImageUrl()).into(user_img);
                    user_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    user_img.setClipToOutline(true);
                }

                pb.setVisibility(View.INVISIBLE);
                rellay1.setAlpha(1);
                rellay2.setAlpha(1);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(logout_btn).navigate(R.id.action_profileFragment_to_loginFragment);

            }
        });
        return v;
    }
}