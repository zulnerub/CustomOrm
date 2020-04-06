package hiberspring.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class XmlParserImpl implements XmlParser {

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new File(filePath));
    }
}
