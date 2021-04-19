package com.aviv.rebuy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.Product;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class FavoriteFrag extends Fragment {

    FeedViewModel viewModel;
    SearchView search;
    List<Product> favorites;
    ArrayList<String> data = new ArrayList<String>();

    ListView listView;
    ArrayAdapter<String> adapter;

    public FavoriteFrag() {
        // Required empty public constructor
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
//        navBar.setVisibility(View.VISIBLE);
        listView = view.findViewById(R.id.favList);

        data.add("yuval");
        data.add("amber");


//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,data);
//        listView.setAdapter(adapter);

        favorites = new ArrayList<Product>();
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("favorites").get().addOnCompleteListener(task -> {
            favorites.clear();
            favorites.addAll(task.getResult().toObjects(Product.class));
            FavAdapter favAdapter = new FavAdapter(getActivity(), (ArrayList<Product>) favorites);
            listView.setAdapter(favAdapter);

        });

        return view;
    }

}

class FavAdapter extends ArrayAdapter<Product> {
    public FavAdapter(Context context, ArrayList<Product> products) { super(context, 0, products); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product product = getItem(position);
        View view = parent.getRootView().findViewById(R.id.favoriteFrag);// Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fav_item, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.productName);
        ImageView image = (ImageView) convertView.findViewById(R.id.favImage);

        ImageButton favRemove  = (ImageButton)convertView.findViewById(R.id.favRemove);
//        ImageButton infoButton  = (ImageButton)convertView.findViewById(R.id.infoButton);

        favRemove.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("favorites").document(product.getId()).delete();
            this.remove(product);
            notifyDataSetChanged();
        });

//        infoButton.setOnClickListener(v -> {
//            FeedViewModel viewModel = new FeedViewModel();
//            viewModel.getList().getValue().forEach(item->{
//                if(item.getId().equals(product.getId())){
//                    // TODO: 4/8/21 need to fix it getting -  does not have a NavController set
//                     FeedFragmentDirections.ActionFeedFragmentToDetailsFragment action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(viewModel.getList().getValue().indexOf(item));
//                     Navigation.findNavController(view).navigate(action);
//                    return;
//                }
//            });
//
//        });

        // Populate the data into the template view using the data object
        name.setText(product.getId());
        Picasso.get().load(product.getImageUrl()).into(image);

        // Return the completed view to render on screen
        return convertView;
    }
}