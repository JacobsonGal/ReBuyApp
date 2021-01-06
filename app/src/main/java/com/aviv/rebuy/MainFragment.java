package com.aviv.rebuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_main, container, false);

//       Button map=view.findViewById(R.id.nav_bar_maps_btn);
//       map.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_mapsFragment);
//           }
//       });


        return view;
    }
}