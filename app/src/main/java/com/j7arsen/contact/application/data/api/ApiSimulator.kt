package com.j7arsen.contact.application.data.api

import com.j7arsen.contact.application.data.model.api.ContactEntity

class ApiSimulator : IApi {

    override suspend fun getContactList(): List<ContactEntity> {
        return listOf(
            ContactEntity(1, "Oliver", "Stones", null, "oliver@gmail.com"),
            ContactEntity(2, "Jane", "Sterling", "http://megakohz.bget.ru/test_task/test_images/1.jpg", "jane@gmail.com"),
            ContactEntity(3, "George", "Silva", "http://megakohz.bget.ru/test_task/test_images/2.jpg", "george@gmail.com"),
            ContactEntity(4, "Bob", "Dawson", "http://megakohz.bget.ru/test_task/test_images/3.jpg", "bob@gmail.com"),
            ContactEntity(5, "John", "Downing", "http://megakohz.bget.ru/test_task/test_images/4.jpg", "john@gmail.com"),
            ContactEntity(6, "Lisa", "Smith", "http://megakohz.bget.ru/test_task/test_images/5.jpg", "lisa@gmail.com"),
            ContactEntity(7, "Betty", "Atkinson", "http://megakohz.bget.ru/test_task/test_images/6.jpg", "betty@gmail.com"),
            ContactEntity(8, "Allan", "Patrick", "http://megakohz.bget.ru/test_task/test_images/7.jpg", "allan@gmail.com")
        )
    }
}