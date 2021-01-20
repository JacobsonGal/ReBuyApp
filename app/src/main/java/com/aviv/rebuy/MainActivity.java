package com.aviv.rebuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import testFirebase.ModelFirebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ModelFirebase fr=new ModelFirebase();
        fr.addUser();

    }
}