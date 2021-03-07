package com.aviv.rebuy;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aviv.rebuy.Model.Feed;
import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.Product;

import java.util.List;

public class FeedViewModel extends ViewModel {

    private LiveData<List<Product>> prList;

    public FeedViewModel(){
        Log.d("TAG","StudentListViewModel");
        prList = Model.instance.getAllProducts();
    }
    LiveData<List<Product>> getList(){
        return prList;
    }
}
