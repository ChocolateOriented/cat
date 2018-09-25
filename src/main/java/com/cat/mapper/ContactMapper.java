package com.cat.mapper;

import com.cat.module.entity.Contact;
import com.cat.module.entity.Contact;

import java.util.List;

/**
 * Created by cyuan on 2018/9/21.
 */
public interface ContactMapper {
    List<Contact> fetchContactsByCustomerId(String customerId);

    void deleteContact(String customerId);

    void insert(Contact contact);

    int deleteByPrimaryKey(Long id);
    
    Contact selectByPrimaryKey(Long id);

    List<Contact> selectAll();

    int updateByPrimaryKey(Contact record);
}
