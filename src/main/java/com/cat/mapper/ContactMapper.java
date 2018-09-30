package com.cat.mapper;

import com.cat.module.dto.AddressBook;
import com.cat.module.entity.Contact;
import com.cat.module.vo.ContactVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    List<ContactVo> findListByCustomerId(String customerId);

    void insertList(List<Contact> contactList);
    
    Long maxCareateTime();

	List<AddressBook> findListContact(@Param("list")List<String> listCustomeId, @Param("createTime")Long createTime);

	List<AddressBook> reloadAddressBook(List<String> customerIds);
}
