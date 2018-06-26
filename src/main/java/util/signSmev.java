package util;

import org.apache.xml.security.transforms.params.XPath2FilterContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class signSmev {
    private static final String XMLDSIG_MORE_GOSTR34102001_GOSTR3411 = "http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411";
    private static final String XMLDSIG_MORE_GOSTR3411 = "http://www.w3.org/2001/04/xmldsig-more#gostr3411";
    private static final String CANONICALIZATION_METHOD = "http://www.w3.org/2001/10/xml-exc-c14n#";
    private static final String DS_SIGNATURE = "//ds:Signature";
    private static final String SIG_ID = "sigID";
    private static final String COULD_NOT_FIND_XML_ELEMENT_NAME = "ERROR! Could not find xmlElementName = ";
    private static final String GRID = "#";
    private static final String XML_SIGNATURE_ERROR = "xmlDSignature ERROR: ";



}
