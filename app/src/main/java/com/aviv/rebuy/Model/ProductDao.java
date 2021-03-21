package com.aviv.rebuy.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("select * from Product")
    LiveData<List<Product>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... product);

    @Delete
    void delete(Product product);

    //filtering and etc if we want


}

