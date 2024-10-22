package com.github.JoseAngelGiron.model.xmlDataHandler;
import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.persistance.XMLManager;

public class ContactListHandler implements IContactListHandler<String, ContactList>{

    private static final String contactListFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\contactLists\\";

    @Override
    public ContactList findAll(String name) {
        return XMLManager.readXML(contactListFilePath+name+".xml", ContactList.class);
    }

    @Override
    public String save(String entity) {




        return "";
    }

    @Override
    public boolean update(String entity) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public boolean create(String name, int id) {
        ContactList contactList = new ContactList();
        contactList.setNameOfUser(name);
        contactList.setId(id);
        return XMLManager.createXML(contactList, contactListFilePath+name+".xml");
    }


}
