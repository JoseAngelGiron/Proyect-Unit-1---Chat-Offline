package com.github.JoseAngelGiron.persistance;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;




public class XMLManager {

    public static <T> boolean createXML(T object, String filename){
        File xmlFile = new File(filename);
        boolean exists = true;

        if (!xmlFile.exists()) {

            writeXML(object, filename);
            exists = false;
        }
        return exists;
    }


    /**
     * Writes an object to an XML file using JAXB.
     *
     * @param object   The object to be written to XML.
     * @param filename The name of the XML file to write the object to.
     * @param <T>      The type of the object to be written.
     * @return true if the object was successfully written to the XML file, false otherwise.
     */
    public static <T> boolean writeXML(T object , String filename){
        boolean result=false;
        JAXBContext context;

        try{
            context = JAXBContext.newInstance(object.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            m.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            m.marshal(object, new File(filename));
            result=true;
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Reads an object from an XML file using JAXB.
     *
     * @param object        An object of the type to be read from XML.
     * @param filename The name of the XML file to read the object from.
     * @param <T>      The type of the object to be read.
     * @return The object read from the XML file, or the original object if reading fails.
     */
    public static <T> T readXML(T object, String filename){

        T result = object;
        JAXBContext context;

        try{
            context = JAXBContext.newInstance(object.getClass());
            Unmarshaller um = context.createUnmarshaller();
            result = (T) um.unmarshal(new File(filename));
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T readXML(String filePath, Class<T> class1) {
        T entity = null;
        try {
            JAXBContext context = JAXBContext.newInstance(class1);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            entity = (T) unmarshaller.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return entity;
    }



}
