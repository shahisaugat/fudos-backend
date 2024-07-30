package com.example.fooddelivery.Service.Impl;


import com.example.fooddelivery.Entity.Contact;
import com.example.fooddelivery.Repository.ContactRepo;
import com.example.fooddelivery.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Long contactCount() {
        return contactRepository.count();
    }
}
