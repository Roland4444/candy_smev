package crypto;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;
import util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;

import static java.time.LocalDate.now;
import static org.junit.Assert.*;

public class CryptoTest {

    @Test
    public void genandsave() throws GeneralSecurityException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        String key = "certs/key.key";
        String gencert = "certs/certs.pem";
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream("certs/123.pfx"), "1235".toCharArray());
        PrivateKey key2 = (PrivateKey)keystore.getKey("key", "123".toCharArray());
    }


    @Test
    public void sihn() throws GeneralSecurityException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        String input = "xml4test/razedNoAttachWithTransformReady!.xml";
        Extractor ext = new Extractor();
        String signingdata = ext.parse(input, "SenderProvidedRequestData");
        Gost3411Hash hash = new Gost3411Hash();
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hash.base64(hash.hash_byte(signingdata)));
    }

    @Test
    public void sihning() throws GeneralSecurityException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        Gost3411Hash hasher =  new Gost3411Hash();
        Crypto crypto = new GOSTCrypto();
        KeyPair root = crypto.generateKeyPair();
        X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));
        KeyPair subject = crypto.generateKeyPair();
        X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));
        crypto.toPEM(subjectCert);
        FileWriter wr = new FileWriter("certs/certTest.pem");
        wr.write(crypto.toPEM(subjectCert));
        wr.close();
        String hello = "hello";
        byte[] input = hello.getBytes();
        byte[] signature = crypto.signEnglish(input, subject.getPrivate(), subject.getPublic());
        assertNotEquals(null, signature);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Signature>>>>\n"+hasher.base64(signature)+"\n<<<<<");
        System.out.println(signature.length);


    }

    @Test
    public void sihningfromFile() throws GeneralSecurityException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        String initFile = "xml4test/razedNoAttachWithTransformReady!.xml";
        String result = "xml4test/razedNoAttachWithTransformReady!Result.xml";
        Gost3411Hash hasher =  new Gost3411Hash();
        Crypto crypto = new GOSTCrypto();
        KeyPair root = crypto.generateKeyPair();
        X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));
        KeyPair subject = crypto.generateKeyPair();
        X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));
        crypto.toPEM(subjectCert);
        FileWriter wr = new FileWriter("certs/certTest.pem");
        wr.write(crypto.toPEM(subjectCert));
        wr.close();
        Extractor ext = new Extractor();
        String hello = ext.parse(initFile, "SenderProvidedRequestData");
        byte[] input = hello.getBytes();
        byte[] signature = (crypto.sign(hello, subject.getPrivate()));
        byte[] sigWithHash = hasher.swapBytes(crypto.signEnglish(input, subject.getPrivate(), subject.getPublic()));
        byte[] swapped = new byte[64];
        for (int i=0; i<=63;i++) swapped[i]=signature[63-i];
        assertNotEquals(null, signature);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Signature>>>>\n"+hasher.base64(swapped)+"\n<<<<<");
        System.out.println("SignatureOriginal>>>>\n"+hasher.base64(signature)+"\n<<<<<");
        System.out.println("SignatureFromHash>>>>\n"+hasher.base64(sigWithHash)+"\n<<<<<");
        System.out.println(swapped.length);
        System.out.print("watch \"certs/certTest.pem\""+result);
        injectSig(initFile, result,hasher.base64(signature));
    }


    @Test
    public void pemPrivate() throws GeneralSecurityException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        Crypto crypto = new GOSTCrypto();
        KeyPair root = crypto.generateKeyPair();
        X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));
        KeyPair subject = crypto.generateKeyPair();
        X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));
        crypto.toPEM(subjectCert);
        Gost3411Hash g = new Gost3411Hash();
        System.out.print(g.h_Base64rfc2045(subject.getPrivate().getEncoded()));
    }




    public void injectSig(String filename, String fileout , String Base64Sig) throws IOException {
        Injector parcer = new Injector();
        parcer.injectTagInFile(filename,fileout,"ds:SignatureValue>", Base64Sig);
    }


}