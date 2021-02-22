package com.aviv.rebuy.ui.login;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aviv.rebuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    FirebaseAuth fauth=FirebaseAuth.getInstance();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar pb;
    TextView forgotPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        TextView register=view.findViewById(R.id.loginfrag_register);
        final EditText usernameEditText = view.findViewById(R.id.inputSearch);
        final EditText passwordEditText = view.findViewById(R.id.reg_inputPassword);
        final Button loginButton = view.findViewById(R.id.regFrag_reg_btn);
        forgotPassword=view.findViewById(R.id.loginFrag_forgotPassword);
      register.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerUserFragment));
      //login user
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=usernameEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(email) && email.matches(emailPattern))
                {
                    usernameEditText.setError("please enter  correct   email");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    passwordEditText.setError("please enter correct  password");
                    return;
                }

                pb=view.findViewById(R.id.loginFrag_progressBar);
                pb.setVisibility(View.VISIBLE);
                //authenticate the user
                fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(),"Logged in Successfully",Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_global_feedFragment);
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Error! "+ task.getException().getMessage() ,Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            }
        });

//reset password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordRestDialog=new AlertDialog.Builder(v.getContext());
                passwordRestDialog.setTitle("Reset password ?");
                passwordRestDialog.setMessage("Enter Your Email To Received Rest Link");
                passwordRestDialog.setView(resetMail);

                passwordRestDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                //extract the email and sent reset link
                        String mail=resetMail.getText().toString();
                        fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(),"Reset Link Sent To Your Email",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Error ! Reset Link did not Sent !"+e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                } );

                passwordRestDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });
            passwordRestDialog.create().show();
            }
        });




        return view;
    }


}