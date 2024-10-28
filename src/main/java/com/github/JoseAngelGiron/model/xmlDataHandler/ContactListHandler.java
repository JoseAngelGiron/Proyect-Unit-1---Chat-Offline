package com.github.JoseAngelGiron.model.xmlDataHandler;
import com.github.JoseAngelGiron.model.entity.Contact;
import com.github.JoseAngelGiron.model.entity.ContactList;
import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.persistance.XMLManager;

import java.util.Iterator;

public class ContactListHandler implements IContactListHandler<Contact, ContactList, String>{

    private static final String contactListFilePath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\xmlStorage\\contactLists\\";

    /**
     * Retrieves a contact list based on the provided name.
     *
     * This method reads an XML file corresponding to the contact list associated with the given name.
     * It uses the XMLManager to parse the XML file and returns a ContactList object containing all
     * the contacts stored in that file.
     *
     * @param name The name associated with the contact list to retrieve.
     * @return ContactList The ContactList object populated with the contacts from the XML file.
     *                     If the file does not exist or is empty, an empty ContactList may be returned.
     */
    @Override
    public ContactList findAll(String name) {

        return XMLManager.readXML(contactListFilePath+name+".xml", ContactList.class);
    }



    /**
     * Saves a contact to the specified contact list.
     *
     * This method retrieves the contact list associated with the provided name and checks if
     * the specified contact already exists in that list. If the contact does not exist, it
     * adds the contact to the list and saves the updated contact list back to the XML file.
     * Finally, it returns the contact that was added.
     *
     * @param entity The name of the contact list to which the contact will be saved.
     * @param entity2 The Contact object to be saved to the contact list.
     * @return Contact The Contact object that was successfully added to the contact list.
     *                 If the contact already exists in the list, an empty Contact object is returned.
     */
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

    /**
     * Deletes a contact from the specified contact list.
     *
     * This method retrieves the contact list associated with the provided name and searches
     * for the contact that matches the given user's ID. If the contact is found, it is removed
     * from the list, and the updated contact list is saved back to the XML file. The method
     * returns true if the contact was successfully deleted, or false if the contact was not found.
     *
     * @param entity The User object representing the contact to be deleted.
     * @param nameOfList The name of the contact list from which the contact will be deleted.
     * @return boolean True if the contact was successfully deleted; false otherwise.
     */
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

    /**
     * Creates a new contact list with the specified name and ID.
     *
     * This method initializes a new ContactList object using the provided name and ID,
     * and then attempts to create an XML file for the contact list using the XMLManager.
     * The XML file is saved with the name of the contact list.
     *
     * @param name The name of the contact list to be created.
     * @param id The ID associated with the contact list.
     * @return boolean True if the contact list was successfully created and the XML file was generated;
     *                 false otherwise.
     */
    @Override
    public boolean create(String name, int id) {
        ContactList contactList = new ContactList(id, name);

        return XMLManager.createXML(contactList, contactListFilePath+name+".xml");
    }

    public static ContactListHandler build(){
            return new ContactListHandler();
    }



}
