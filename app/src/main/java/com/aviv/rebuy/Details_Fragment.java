package com.aviv.rebuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviv.rebuy.Model.Model;
import com.squareup.picasso.Picasso;


public class Details_Fragment extends Fragment {

    FeedViewModel viewModel;
    private TextView itemText;
    private ImageView itemImage;
    private  TextView descText;
    private  TextView priceText;
    private  TextView conditionText;


    public Details_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    int productId = Details_FragmentArgs.fromBundle(getArguments()).getProductId();
Log.d("TAG123","ID IS NOW VERY GOOD " + productId);

        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details_, container, false);
<<<<<<< Updated upstream
=======

        delbtn = v.findViewById(R.id.details_deletebtn);
        editbtn = v.findViewById(R.id.details_editbtn);
        addntb = v.findViewById(R.id.details_likebtn);


        if(viewModel.getList().getValue().get(productId).getOwnerId().equals(FirebaseAuth.getInstance().getUid())) {
            delbtn.setVisibility(View.VISIBLE);
        editbtn.setVisibility(View.VISIBLE);
        }


>>>>>>> Stashed changes
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
        return v;
    }
}