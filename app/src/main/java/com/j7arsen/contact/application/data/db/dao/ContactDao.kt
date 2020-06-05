package com.j7arsen.contact.application.data.db.dao

import androidx.room.*
import com.j7arsen.contact.application.data.db.DatabaseInfo
import com.j7arsen.contact.application.data.model.cache.ContactCache

@Dao
interface ContactDao {

    @Query("SELECT COUNT(*) FROM ${DatabaseInfo.TABLE_CONTACT}")
    suspend fun getCount() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(contactCache: ContactCache)

    @Query("SELECT * FROM ${DatabaseInfo.TABLE_CONTACT}")
    suspend fun getList() : List<ContactCache>

    @Query("SELECT * FROM ${DatabaseInfo.TABLE_CONTACT} WHERE ${DatabaseInfo.CONTACT_ID} =:id")
    suspend fun getById(id : Long) : ContactCache

    @Delete
    suspend fun delete(contactCache: ContactCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(contactCacheList: List<ContactCache>)

    @Query("DELETE FROM ${DatabaseInfo.TABLE_CONTACT}")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAllAndInsertWithReturnData(contactCacheList: List<ContactCache>) : List<ContactCache> {
        deleteAll()
        insertList(contactCacheList)
        return getList()
    }

}