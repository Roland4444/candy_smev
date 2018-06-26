package ru.voskhod.crypto.impl;
import crypto.Gost3411Hash;
import org.bouncycastle.jcajce.provider.digest.GOST3411;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.voskhod.crypto.exceptions.SignatureProcessingException;
import util.Sign;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.junit.Assert.*;
public class DigitalSignatureProcessorImplTest {

    @Test
    public void calculateDigest() throws FileNotFoundException, SignatureProcessingException, NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        DigitalSignatureProcessorImpl blob = new DigitalSignatureProcessorImpl();
        InputStream in = new FileInputStream("xml4test/transformedReady.xml");
        Gost3411Hash g = new Gost3411Hash();
        assertNotEquals(null, blob);
    }

    @Test
    public void sign() throws IOException, SignatureProcessingException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException {
        Sign s = new Sign();
        DigitalSignatureProcessorImpl blob = new DigitalSignatureProcessorImpl();
        InputStream in = new FileInputStream("xml4test/razedNoAttachWithTransformReady!.xml");
        Gost3411Hash g = new Gost3411Hash();
        assertNotEquals(null, blob);
    //    blob.signXMLDSigEnveloped("ns2:SendRequestRequest" , s.getPrivate(), (X509Certificate) s.getCert());

    }

    @Test
    public void elem() throws IOException, SignatureProcessingException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, ParserConfigurationException, SAXException {
        org.apache.xml.security.Init.init();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Sign sign = new Sign();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("xml4test/razedNoAttachWithTransformReady!.xml");
        Element root = doc.getDocumentElement();
        NodeList sender = root.getElementsByTagName("ns1:SenderProvidedRequestData");
        NodeList digest = root.getElementsByTagName("ns1:MessageID");
        System.out.println(digest.item(0));
        assertNotEquals(null, sender);
        System.out.println(root.getTagName());
        System.out.println(sender.item(0).getNodeValue());
        assertNotEquals(null, root);
        Element senderel = (Element)sender.item(0);
        assertNotEquals(null, senderel);
        System.out.println(senderel.getAttribute("Id"));
        DigitalSignatureProcessorImpl pr = new DigitalSignatureProcessorImpl();
        pr.signXMLDSigEnveloped(senderel, sign.getPrivate(), (X509Certificate) sign.getCert());
    }


    /**
     * Подписать XML-фрагмент по технологии XMLDSig enveloped.
     * Cгенерированный {http://www.w3.org/2000/09/xmldsig#}Signature будет добавлен как первый child к подписанному элементу.
     * Канонизация - http://www.w3.org/2001/10/xml-exc-c14n#
     * Расчёт хэш-кода - ГОСТ Р 34.11-94, http://www.w3.org/2001/04/xmldsig-more#gostr3411
     * Подписание - ГОСТ Р 34.10-2001, http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411
     *
     * @param argDocumentFragment2Sign XML-фрагмент, который необходимо подписать.
     * @param argPrivateKey            Секретный ключ.
     * @param argCertificate           Сетрификат.
     * @throws SignatureProcessingException Оборачивает любые exceptions, брошенные нижележащим ПО.
     *                                      Кроме того, выбрасывается, если какой-либо из аргументов - null.
     */
  //  void signXMLDSigEnveloped(Element argDocumentFragment2Sign, PrivateKey argPrivateKey, X509Certificate argCertificate) throws SignatureProcessingException;

}