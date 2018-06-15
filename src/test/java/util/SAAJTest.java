package util;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class SAAJTest {

    @Test
    public void send() throws Exception {
        String file = "xml4test/1withoutcert.xml";
        SAAJ saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(file, "results.xml"));
    }

    @Test
    public void sendFormed() throws Exception {
        String withIdHashSigCert4 = "xml4test/withIdHashSigCert4.xml";
        SAAJ saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert4, "responce.xml"));
    }
}

