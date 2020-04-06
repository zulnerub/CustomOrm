package alararestaurant.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    @Override
    @SuppressWarnings(value = "unchecked")
    public <O> O importFromXml(Class<O> klass, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(klass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new File(path));
    }
}
