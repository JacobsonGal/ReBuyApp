package com.aviv.rebuy.Model;

import android.os.AsyncTask;
import android.util.Log;

;import java.util.List;

public class Model {
    public final static Model instance = new Model();

    ModelFirebase modelFirebase = new ModelFirebase();
  //  ModelSql modelSql = new ModelSql();

    private Model() {

    }
    public interface Listener<T> {
        void onComplete(T result);
    }
//    public interface AddUserListener {
//        void onComplete();
//    }
    public interface AddProductListener {
        void onComplete();
    }

    public interface  GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public void getAllUsers(GetAllUsersListener listener){
        class MyASyncTask extends AsyncTask{
            List<User> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDb.db.userDao().getAllUsers();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }
        MyASyncTask task = new MyASyncTask();
        task.execute();
    }


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

    public void addProduct(final Product product , final AddProductListener listener) {
        modelFirebase.addProduct(product,  listener);
    }

    }
