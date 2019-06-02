package by.anelkin.xmlparsing.validator;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXException;


import java.io.File;
import java.io.IOException;

public class XMLFileValidator {
    private static final Logger logger = Logger.getLogger(XMLFileValidator.class);
    private static final String DEFAULT_PATH = "src/main/resources/data/tariffs.xml";

    public boolean validate(String path) {
        if (path == null || !(new File(path)).isFile()) {
            logger.warn("Wrong path to file: " + path + " !!! Path changed to default: " + DEFAULT_PATH);
            path = DEFAULT_PATH;
        }
        boolean isValid;
        try {
            SchemaErrorHandler handler = new SchemaErrorHandler();
            DOMParser parser = new DOMParser();
            parser.setErrorHandler(handler);
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setFeature("http://apache.org/xml/features/validation/schema", true);
            parser.parse(path);
            //here we take boolean from handler to return in validator
            isValid = !handler.isErrorInFile;
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        if (isValid) {
            logger.info("File " + path + " is valid.");
        }
        return isValid;
    }
}
