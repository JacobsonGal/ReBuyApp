package com.aviv.rebuy.Model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelSql {


    public LiveData<List<Product>> getAllProducts(){
        return AppLocalDb.db.productDao().getAllProducts();
    }

    public interface AddProductListener{
        void onComplete();
    }
    public void addProduct(final Product product, final Model.AddProductListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.productDao().insertAll(product);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }




}
