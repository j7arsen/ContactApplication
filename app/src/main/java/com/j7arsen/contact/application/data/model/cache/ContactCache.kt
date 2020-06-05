package com.j7arsen.contact.application.data.model.cache

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.j7arsen.contact.application.data.db.DatabaseInfo

@Entity(tableName = DatabaseInfo.TABLE_CONTACT)
data class ContactCache(
    @ColumnInfo(name = DatabaseInfo.CONTACT_ID) @PrimaryKey val id: Long,
    @ColumnInfo(name = DatabaseInfo.CONTACT_FIRST_NAME) val firstName: String,
    @ColumnInfo(name = DatabaseInfo.CONTACT_LAST_NAME) val lastName: String,
    @ColumnInfo(name = DatabaseInfo.CONTACT_PHOTO) @Nullable val photo: String?,
    @ColumnInfo(name = DatabaseInfo.CONTACT_EMAIL) val email: String
) {
}