package com.aviv.rebuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.data.model.ListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class FeedFragment extends Fragment {

    FeedViewModel viewModel;
    Button addBtn;
  //  MyAdapter  adapter;
    SwipeRefreshLayout sref;
    public FeedFragment() {
        // Required empty public constructor
    }



    void reloadData(){
        Model.instance.refreshAllProducts(new Model.GetAllProductsListener() {
            @Override
            public void onComplete() {
//                sref.setRefreshing(false);
            }
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reloadData();
        View view = inflater.inflate(R.layout.fragment_feed,container,false);
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
      //TODO: add button and etc
        //sref = view.findViewById(R.id.studentslist_swipe);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerForSuggestions);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter((listAdapter));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);




        return view;
    }





}