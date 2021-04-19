package com.aviv.rebuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         navController = Navigation.findNavController(this , R.id.fragment);

        bottomNavigationView =
                findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        bottomNavigationView.setVisibility(View.GONE);

        //hide action bar

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            return navController.navigateUp();
        }else{
            return NavigationUI.onNavDestinationSelected(item,navController);
        }

    }

    public void getNav() {
    }
}