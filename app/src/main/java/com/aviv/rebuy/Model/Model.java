package com.aviv.rebuy.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

;import androidx.lifecycle.LiveData;

import com.aviv.rebuy.MyApplication;

import java.util.List;

public class Model {
    public final static Model instance = new Model();

   public ModelFirebase modelFirebase = new ModelFirebase();
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
            refreshAllProducts(null);
        }
        return productList;
    }

    public interface GetAllProductsListener{
        void onComplete();
    }


    public void refreshAllProducts(final GetAllProductsListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllProducts (lastUpdated, new ModelFirebase.GetAllProductsListener() {
            @Override
            public void onComplete(List<Product> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Product p: result) {
                    modelSql.addProduct(p,null);
                    if (p.getLastUpdated()>lastU){
                        lastU = p.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdated", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }



    public interface GetProductListener {
        void onComplete(Product product);
    }
    public void getProduct(String id, GetProductListener listener) {
        modelFirebase.getProduct(id, listener);
    }

    public interface AddProductListener {
        void onComplete();
    }

    public void addProduct(final Product student, final AddProductListener listener) {
        modelFirebase.addProduct(student, new AddProductListener() {
            @Override
            public void onComplete() {
                refreshAllProducts(new GetAllProductsListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    public interface UpdateProductListener extends AddProductListener { }

    public void updateProduct(final Product student, final AddProductListener listener) {
        modelFirebase.updateProduct(student, listener);
    }

    interface DeleteListener extends AddProductListener { }

//    public void deleteStudent(Product product, DeleteListener listener) {
//        modelFirebase.delete(product, listener);
//    }



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
