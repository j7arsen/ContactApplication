package com.j7arsen.contact.application.data.repository

import com.j7arsen.contact.application.data.api.IApi
import com.j7arsen.contact.application.data.db.dao.ContactDao
import com.j7arsen.contact.application.data.model.api.ContactEntity
import com.j7arsen.contact.application.data.model.cache.ContactCache
import com.j7arsen.contact.application.data.model.mapper.IMapper
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.domain.repository.IContactRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ContactRepository(
    private val api: IApi,
    private val contactDao: ContactDao,
    private val mapper: IMapper<ContactEntity, ContactCache, ContactModel>
) : IContactRepository {

    override fun getContactList(isRefreshState: Boolean): Flow<List<ContactModel>> = flow {
        val count = contactDao.getCount()
        if (isRefreshState || count == 0) {
            val apiData = api.getContactList()
            //искуственная задержка
            emit(contactDao.deleteAllAndInsertWithReturnData(contactCacheList = mapper.mapEntityToCache(apiData)))
        } else {
            emit(contactDao.getList())
        }
    }.map { mapper.mapCacheToModel(it) }

    override fun getContactDetail(id: Long): Flow<ContactModel> = flow {
        emit(contactDao.getById(id = id))
    }.map { mapper.mapCacheToModel(it) }

    override fun updateContact(contactModel: ContactModel): Flow<Unit> = flow {
        emit(contactDao.update(contactCache = mapper.mapModelToCache(contactModel)))
    }

    override fun deleteContact(contactModel: ContactModel): Flow<Unit> = flow {
        emit(contactDao.delete(contactCache = mapper.mapModelToCache(contactModel)))
    }
}