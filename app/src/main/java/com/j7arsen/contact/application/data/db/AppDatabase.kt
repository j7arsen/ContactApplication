package com.j7arsen.contact.application.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.j7arsen.contact.application.data.db.dao.ContactDao
import com.j7arsen.contact.application.data.model.cache.ContactCache

@Database(entities = [ContactCache::class], version = DatabaseInfo.DB_VERSION)
abstract class AppDatabase : RoomDatabase(){

    companion object{

        fun getInstance(context : Context) : AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, DatabaseInfo.DB_NAME).build()
        }

    }

    abstract fun contactDao() : ContactDao

}