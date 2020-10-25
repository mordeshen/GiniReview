package com.e.tbreview.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.e.tbreview.model.ItemModel
import com.e.tbreview.model.ModelContentProvider


@Database(entities = [ItemModel::class,ModelContentProvider::class], version = 2)
abstract class AppDatabase : RoomDatabase(){


    abstract fun getMainDao(): MainDao
    abstract fun getProviderDao(): ProviderDao


    companion object{
        const val DATABASE_NAME = "app_db"
        var appDatabase: AppDatabase?=null

        fun getDatabase(mContext: Context): AppDatabase {

            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                    mContext.applicationContext,
                    AppDatabase::class.java,
                    AppDatabase.DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return appDatabase as AppDatabase

        }
    }


}