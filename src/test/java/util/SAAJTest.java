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

    @Test
    public void normal() throws Exception {
        String withIdHashSigCert4 = "xml4test/sendReqMod.xml";
        SAAJ saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert4, "responce.xml"));
    }

    @Test
    public void normalSignedHash() throws Exception {
        String hashSign = "xml4test/2hashSign.xml";
        String withIdHashSigCert = "xml4test/withIdHashSigCert.xml";
        String withIdHashSigCert4 = "xml4test/sendReqMod.xml";
        String base64HashSign = "xml4test/3base64HashSign.xml";
        SAAJ saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert, "responce.xml"));
      //  assertNotEquals(null, saa.send(base64HashSign, "responce.xml"));

    }



}

