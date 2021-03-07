//package com.aviv.rebuy.Model;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//import java.util.List;
//
//@Dao
//public interface UserDao {
//    @Query("select * from User")
//    LiveData<List<User>> getAllUsers();
//
//    @Insert (onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(User... user);
//
//    @Delete
//    void delete(User user);
//
//    //filtering and etc if we want
//}
