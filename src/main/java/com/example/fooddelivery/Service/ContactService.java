package com.example.fooddelivery.Service;

import com.example.fooddelivery.Entity.Contact;

import java.util.List;

public interface ContactService {
    Contact saveContact(Contact contact);
    List<Contact> getAllContacts();
    Long contactCount();
}
