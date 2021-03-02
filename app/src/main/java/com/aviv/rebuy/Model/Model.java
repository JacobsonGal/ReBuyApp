package com.aviv.rebuy.Model;

import android.util.Log;

;

public class Model {
    public final static Model instance = new Model();

    ModelFirebase modelFirebase = new ModelFirebase();
  //  ModelSql modelSql = new ModelSql();

    private Model() {

    }
    public interface Listener<T> {
        void onComplete(T result);
    }
    public interface AddUserListener {
        void onComplete();
    }
    public interface AddProductListener {
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

    public void addProduct(final Product product , final AddProductListener listener) {
        modelFirebase.addProduct(product,  listener);
    }

    }
