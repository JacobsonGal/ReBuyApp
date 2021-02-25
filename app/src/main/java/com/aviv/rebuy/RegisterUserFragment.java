package com.aviv.rebuy;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment {
     FirebaseAuth fAuth;
    EditText fullName;
    EditText email;
    EditText password;
    EditText password2;
    Button regBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar pb;
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




        regBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name=fullName.getText().toString().trim();
              String mail=email.getText().toString().trim();
              String pass1=password.getText().toString().trim();
              String pass2=password2.getText().toString().trim();
              if(TextUtils.isEmpty(name))
              {
                  fullName.setError("please enter full name");
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
                    Toast.makeText(getContext(),"user created",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_registerUserFragment_to_feedFragment);
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






}