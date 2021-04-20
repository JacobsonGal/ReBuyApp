package com.aviv.rebuy;


import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment implements EasyPermissions.PermissionCallbacks{
     FirebaseAuth fAuth=FirebaseAuth.getInstance();
    EditText fullName;
    EditText email;
    EditText password;
    EditText password2;
    EditText phone;
    Button regBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar pb;
    double longitude = 0;
    double latitude = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register_user, container, false);

        fullName=view.findViewById(R.id.reg_inputName);
        email=view.findViewById(R.id.reg_inputEmail);
        password=view.findViewById(R.id.reg_inputPassword);
        password2=view.findViewById(R.id.reg_inputPassword2);
        regBtn=view.findViewById(R.id.regFrag_reg_btn);
        pb=view.findViewById(R.id.regFrag_progressBar);
        phone=view.findViewById(R.id.reg_inputPhone);

        setupLocationRequest();

        regBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name=fullName.getText().toString().trim();
              String mail=email.getText().toString().trim();
              String pass1=password.getText().toString().trim();
              String pass2=password2.getText().toString().trim();
              String phoneNum=phone.getText().toString().trim();
              if(TextUtils.isEmpty(name))
              {
                  fullName.setError("please enter full name");
                  return;
              }
              if(TextUtils.isEmpty(phoneNum))
              {
                  phone.setError("please enter a phone number");
                  return;
              }
              if(TextUtils.isEmpty(mail) && mail.matches(emailPattern))
              {
                  fullName.setError("please enter  correct   email");
                  return;
              }
              if(TextUtils.isEmpty(pass1))
              {
                  password.setError("please enter a  password");
                  return;
              }

              if((pass1.compareTo(pass2))!=0)
              {

                    password.setError("Please enter identical passwords");
                    return;
              }
        if(pass1.length()<6)
        {
            password.setError("Password must be at least 6 characters long");
            return;
        }
              pb.setVisibility(View.VISIBLE);

        //register the user in Firebase


        fAuth.createUserWithEmailAndPassword(mail,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user=new User();
                    user.setId(mail);
                    user.setName(name);
                    user.setPhoneNumber(phoneNum);
                    user.setLongitude(longitude);
                    user.setLatitude(latitude);
                    Model.instance.addUser(user, new Model.AddUserListener() {
                        @Override
                        public void onComplete() {
                            Log.d("TAG","user added to database");
                        }
                    });

                    Toast.makeText(getContext(),"user registered",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_registerUserFragment_to_loginFragment);
                }
                else
                {
                    Toast.makeText(getContext(),"Error! "+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();

                }
            }
        });


          }
      });

        return view;
    }

    private void setupLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            getUserLocation();
        } else {
            EasyPermissions.requestPermissions(getActivity(), "We need to access to your location", 123, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getUserLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // Add a dialog message here if you want
    }

    @SuppressLint("MissingPermission")
    private void getUserLocation() {
        //Get the location
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation().addOnSuccessListener(getActivity(), location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
        if (latitude == 0 && longitude == 0) {
            new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            latitude = location.getAltitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            };
        }
    }
}