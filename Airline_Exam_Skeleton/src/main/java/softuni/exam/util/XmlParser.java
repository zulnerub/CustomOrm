package softuni.exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O importFromXml(Class <O> klass, String path) throws JAXBException;
}
