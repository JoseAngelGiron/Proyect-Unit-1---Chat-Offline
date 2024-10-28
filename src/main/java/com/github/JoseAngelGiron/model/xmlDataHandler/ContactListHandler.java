package com.github.JoseAngelGiron.model.xmlDataHandler;
import com.github.JoseAngelGiron.model.entity.Contact;
import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.persistance.XMLManager;

import java.util.Iterator;

public class ContactListHandler implements IContactListHandler<Contact, ContactList, String>{

    private static final String contactListFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\contactLists\\";

    @Override
    public ContactList findAll(String name) {

        return XMLManager.readXML(contactListFilePath+name+".xml", ContactList.class);
    }




    @Override
    public Contact save(String entity, Contact entity2) {
        Contact contactToReturn = new Contact();
        ContactList contactList = findAll(entity);


        if (!contactList.getContacts().contains(entity2)) {
            contactList.getContacts().add(entity2);
            contactToReturn = entity2;

        }
        XMLManager.writeXML(contactList, contactListFilePath+entity+".xml");

        return contactToReturn;
    }

    /**
     * No usages
     * @param entity
     * @return
     */
    @Override
    public boolean update(Contact entity) {
        return false;
    }


    public boolean delete(User entity, String nameOfList) {
        ContactList contactList = findAll(nameOfList);
        boolean deleted = false;

        Iterator<Contact> iterator = contactList.getContacts().iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();

            if (contact.getId()==entity.getId()) {
                iterator.remove();
                deleted = true;
                break;
            }
        }

        if (deleted) {
            XMLManager.writeXML(contactList, contactListFilePath+nameOfList+".xml");
        }

        return deleted;
    }


    @Override
    public boolean create(String name, int id) {
        ContactList contactList = new ContactList(id, name);

        return XMLManager.createXML(contactList, contactListFilePath+name+".xml");
    }

    public static ContactListHandler build(){
            return new ContactListHandler();
    }



}
