package by.anelkin.xmlparsing.validator;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;


class SchemaErrorHandler implements ErrorHandler {
    private static final Logger logger = Logger.getLogger(SchemaErrorHandler.class);
    boolean isErrorInFile;


    public void warning(SAXParseException e) {
        isErrorInFile = true;
        logger.warn(getLineAddress(e) + "-" +
                e.getMessage());
    }

    public void error(SAXParseException e) {
        isErrorInFile = true;
        logger.error(getLineAddress(e) + " - "
                + e.getMessage());
    }

    public void fatalError(SAXParseException e) {
        isErrorInFile = true;
        logger.fatal(getLineAddress(e) + " - "
                + e.getMessage());
    }

    private String getLineAddress(SAXParseException e) {
        //определение строки и столбца ошибки
        return e.getLineNumber() + " : "
                + e.getColumnNumber();
    }

}


