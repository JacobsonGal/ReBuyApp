package com.aviv.rebuy;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Details_Fragment extends Fragment {

    FeedViewModel viewModel;
    private TextView itemText;
    private ImageView itemImage;
    private  TextView descText;
    private  TextView priceText;
    private  TextView conditionText;
    private Button delbtn;
    private Button editbtn;
    private Button addbtn;
    private ImageButton favoriteButton;

    public Details_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    int productId = Details_FragmentArgs.fromBundle(getArguments()).getProductId();
    Log.d("TAG123","ID IS NOW VERY GOOD " + productId);

        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details_, container, false);

        delbtn = v.findViewById(R.id.details_deletebtn);
        editbtn = v.findViewById(R.id.details_editbtn);
        addbtn = v.findViewById(R.id.details_addbtn);
        favoriteButton = v.findViewById(R.id.favoriteButton);
        favoriteButton.setVisibility(View.INVISIBLE);

        if(viewModel.getList().getValue().get(productId).getOwnerId().equals(FirebaseAuth.getInstance().getUid())) {
            delbtn.setVisibility(View.VISIBLE);
        editbtn.setVisibility(View.VISIBLE);
        }

        Product product = viewModel.getList().getValue().get(productId);

        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .collection("favorites")
                .document(String.valueOf(product.getId())).get().addOnCompleteListener(task -> {
                    if(task.getResult().exists()){
                      addbtn.setVisibility(View.INVISIBLE);
                      favoriteButton.setVisibility(View.VISIBLE);
                    }
        });

        itemText = v.findViewById(R.id.title_textView);
        itemText.setText(viewModel.getList().getValue().get(productId).getName());
        itemImage = v.findViewById(R.id.details_imageView4);
        Picasso.get().load(viewModel.getList().getValue().get(productId).getImageUrl()).into(itemImage);
        descText = v.findViewById(R.id.details_description_textView);
        descText.setText(viewModel.getList().getValue().get(productId).getDescription());
        priceText = v.findViewById(R.id.details_price);
        priceText.setText(viewModel.getList().getValue().get(productId).getPrice().toString());
        conditionText = v.findViewById(R.id.details_condition);
        conditionText.setText(viewModel.getList().getValue().get(productId).getCondition());

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details_FragmentDirections.ActionDetailsFragmentToEditUploadFragment toEditUpload = Details_FragmentDirections.actionDetailsFragmentToEditUploadFragment(productId);
                Navigation.findNavController(v).navigate(toEditUpload);
            }
        });
    delbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        viewModel.getList().getValue().get(productId).setDeleted(true);
        Navigation.findNavController(v).navigate(R.id.action_details_Fragment_to_feedFragment);
    }
});
      
        addbtn.setOnClickListener(event -> {
            addbtn.setVisibility(View.INVISIBLE);
            favoriteButton.setVisibility(View.VISIBLE);
            FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                    .collection("favorites")
                    .document(String.valueOf(product.getId())).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("done");

                }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println(e.getMessage());
                            addbtn.setVisibility(View.VISIBLE);
                            favoriteButton.setVisibility(View.INVISIBLE);
                        }
                    });
        });

        return v;
    }
}