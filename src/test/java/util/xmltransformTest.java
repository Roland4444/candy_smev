package util;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class xmltransformTest {

    @Test
    public void xmldsig1() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException {
        String input = "xml4test/senderData.xml";
        String output = "xml4test/senderData_.xml";
        xmltransform trans = new xmltransform();
        trans.xmldsig(input,output);
    }
}