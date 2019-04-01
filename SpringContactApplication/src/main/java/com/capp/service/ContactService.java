package com.capp.service;

import java.util.List;

import com.capp.domain.Contact;

public interface ContactService {

	public void save(Contact c);
	public void update(Contact c);
	public void delete(Integer contactId);
	public void delete(Integer[] contactIds);
	
	/**
	 * this method returns all the user contact(user who is logged in) 
	 * @param userId
	 * @return
	 */
	public List<Contact> findUserContact(Integer userId);
	
	/**
	 * This method search contact for user(userId) given on free text criteria (txt)
	 * @param userId   User who is logged in
	 * @param txt      criteria use to search
	 * @return
	 */
	public List<Contact> findUserContact(Integer userId, String txt);
	public Contact findById(Integer contactId);
}
