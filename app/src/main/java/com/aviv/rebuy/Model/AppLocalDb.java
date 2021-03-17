package com.aviv.rebuy.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aviv.rebuy.MyApplication;

@Database(entities = {Product.class}, version = 10)
abstract class AppLocalDbRepository extends RoomDatabase {
    //public abstract UserDao userDao();
    public abstract ProductDao productDao();

}
public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}