package springdata.xml.ex.springdataxmlexercise.utils;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> void exportToXml(O object, String path) throws JAXBException;

    <O> O importFromXml(Class<O> klass, String path) throws JAXBException;
}
