package ru.voskhod.crypto.impl;
import crypto.Gost3411Hash;
import org.bouncycastle.jcajce.provider.digest.GOST3411;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.w3c.dom.Element;
import ru.voskhod.crypto.exceptions.SignatureProcessingException;
import util.Sign;

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
        blob.signXMLDSigEnveloped("ns2:SendRequestRequest" , s.getPrivate(), (X509Certificate) s.getCert());

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