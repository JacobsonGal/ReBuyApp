package com.aviv.rebuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aviv.rebuy.data.model.ListAdapter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
ImageView logout;
    private FirebaseAuth fAuth;
    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerForSuggestions);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter((listAdapter));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

       // logout user

        logout=view.findViewById(R.id.feedFrag_logout);
        fAuth=FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override

            //return to main fragment
            public void onClick(View v) {
                fAuth .signOut();
                Navigation.findNavController(v).navigate(R.id.action_global_mainFragment);
            }
        });



        return view;
    }
}