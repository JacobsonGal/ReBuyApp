package com.aviv.rebuy.Model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

;import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {
    public final static Model instance = new Model();

    ModelFirebase modelFirebase = new ModelFirebase();
   ModelSql modelSql = new ModelSql();

    private Model() {

    }

    public interface Listener<T> {
        void onComplete(T result);
    }

    LiveData<List<Product>> productList;
    public LiveData<List<Product>> getAllProducts() {
        if (productList == null){
            productList = modelSql.getAllProducts();
            //refreshAllStudents(null);
        }
        return productList;
    }

    public interface GetAllProductsListener{
        void onComplete();
    }

    public interface AddProductListener {
        void onComplete();
    }

    public interface GetProductListener {
        void onComplete(Product product);
    }


    public interface UploadImageListener extends Listener<String>{ }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }


    //User
    public interface  AddUserListener{
        void onComplete();
    }
    public void addUser(final User user , final AddUserListener listener) {
        modelFirebase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }



    }
