package util;

import crypto.Gost3411Hash;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.transforms.TransformationException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static org.junit.Assert.*;

public class xmltransformTest {

    @Test
    public void xmldsig1() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException {
        String input = "xml4test/senderData.xml";
        String output = "xml4test/senderData_.xml";
        xmltransform trans = new xmltransform();
        trans.xmldsig(input,output);

    }


    @Test
    public void xmldsig1wtrans() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException, TransformationException {
        String input = "xml4test/senderData.xml";
        String output = "xml4test/senderData_.xml";
        xmltransform trans = new xmltransform();
        trans.xmldsig(input,output);
        transform35 test =  new transform35();
        InputStream in = new FileInputStream("xml4test/senderData_.xml");
        OutputStream out = new FileOutputStream("xml4test/prepared.xml");
        test.process(in, out);

    }

    @Test
    public void signedinfo() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException, TransformationException {
        org.apache.xml.security.Init.init();
        transform35 test =  new transform35();
        InputStream in = new FileInputStream("xml4test/signedinfo.xml");
        OutputStream out = new FileOutputStream("xml4test/signedinfotransformed.xml");
        test.process(in, out);

    }



}