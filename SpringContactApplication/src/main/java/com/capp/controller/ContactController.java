package com.capp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capp.domain.Contact;
import com.capp.service.ContactService;

@Controller
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@RequestMapping(value = "/user/contact_form")
	public String contactForm(Model m){
		Contact contact = new Contact();
		m.addAttribute("command", contact);
		return "contact_form";
	}
	
	@RequestMapping(value = "/user/save_contact")
	public String saveorUpdateContact(@ModelAttribute("command") Contact c, Model m, HttpSession session){
		Integer contactId = (Integer) session.getAttribute("aContactId");
		if(contactId==null) {
			// save task
			try {
				Integer userId = (Integer) session.getAttribute("userId");
				c.setUserId(userId); //FK >  logged in user ID
				contactService.save(c);
				return "redirect:clist?act=sv"; // redirect it to contact list
				}catch(Exception e) {
					e.printStackTrace();
					m.addAttribute("err", "Failed to save contact");
					return "contact_form";
				}
		}else {
			//update task
			try {
				
				c.setContactId(contactId); //PK
				contactService.update(c);
				return "redirect:clist?act=ed"; // redirect it to contact list
				}catch(Exception e) {
					e.printStackTrace();
					m.addAttribute("err", "Failed to edit contact");
					return "contact_form";
				}
		}
	}
	
	@RequestMapping(value = "/user/clist")
	public String contactSearch(Model m, HttpSession session){
		Integer userId = (Integer) session.getAttribute("userId");
		m.addAttribute("contactList", contactService.findUserContact(userId) );
		return "clist";
	}
	
	@RequestMapping(value = "/user/contact_search")
	public String contactList(Model m, HttpSession session, @RequestParam("freeText") String freeText){
		Integer userId = (Integer) session.getAttribute("userId");
		m.addAttribute("contactList", contactService.findUserContact(userId, freeText) );
		return "clist";
	}
	
	@RequestMapping(value = "/user/del_contact")
	public String deleteContact(@RequestParam("cid") Integer contactId){
		contactService.delete(contactId);
		return "redirect:clist?act=del";
	}
	
	@RequestMapping(value = "/user/bulk_cdelete")
	public String deleteBulkContact(@RequestParam("cid") Integer[] contactIds){
		contactService.delete(contactIds);
		return "redirect:clist?act=del";
	}
	
	@RequestMapping(value = "/user/edit_contact")
	public String prepareEditForm(Model m, HttpSession session, @RequestParam("cid") Integer contactId){
		session.setAttribute("aContactId", contactId);
		Contact c = contactService.findById(contactId);
		m.addAttribute("command",c);
		return "contact_form";
	}
	
}
