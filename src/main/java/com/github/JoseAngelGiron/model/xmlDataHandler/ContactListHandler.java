package com.github.JoseAngelGiron.model.xmlDataHandler;
import com.github.JoseAngelGiron.model.entity.Contact;
import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.persistance.XMLManager;

public class ContactListHandler implements IContactListHandler<Contact, ContactList, String>{

    private static final String contactListFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\contactLists\\";

    @Override
    public ContactList findAll(String name) {
        return XMLManager.readXML(contactListFilePath+name+".xml", ContactList.class);
    }

    public Contact findByID(String UserName, int idSender){
        Contact contactToReturn = new Contact();
        ContactList contactList = findAll(UserName+"-"+idSender);

        for (Contact contact: contactList.getContacts()) {
            if(contact.getId() == idSender) {
                contactToReturn = contact;
            }
        }
        return contactToReturn;
    }





    @Override
    public Contact save(String entity, Contact entity2) {
        Contact contactToReturn = new Contact();
        ContactList contactList = findAll(entity+"-"+entity2.getId());


        if (!contactList.getContacts().contains(entity2)) {
            contactList.getContacts().add(entity2);
            contactToReturn = entity2;

        }
        XMLManager.writeXML(contactList, contactListFilePath+entity+".xml");

        return contactToReturn;
    }


    @Override
    public boolean update(Contact entity) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public boolean create(String name, int id) {
        ContactList contactList = new ContactList(id, name);

        return XMLManager.createXML(contactList, contactListFilePath+name+"-"+id+".xml");
    }

    public static ContactListHandler build(){
            return new ContactListHandler();
    }



}
