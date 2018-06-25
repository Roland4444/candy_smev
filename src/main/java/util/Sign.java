package util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class Sign {
    public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        char[] keyPassword = "sje2017".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("Ñàíãàäæèåâà Þëèÿ Ýðäíèåâíà 403105045", keyPassword);
        return key;
    };

    public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        char[] keyPassword = "sje2017".toCharArray();
        Certificate cert = (Certificate) keyStore.getCertificate("Ñàíãàäæèåâà Þëèÿ Ýðäíèåâíà 403105045");
        return cert;
    };

    byte[] signed (String input) throws NoSuchProviderException, NoSuchAlgorithmException, SignatureException, KeyStoreException, UnrecoverableEntryException, InvalidKeyException {
        byte[] result=null;
        Signature sig = Signature.getInstance("GOST3411withGOST3410EL", "JCP");
        KeyStore keyStore = KeyStore.getInstance("JCP");
        try(InputStream keyStoreData = new FileInputStream("f22")){
            keyStore.load(keyStoreData,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)
                keyStore.getEntry("f22", null);
        PrivateKey privateKey=privateKeyEntry.getPrivateKey();
        sig.initSign(privateKey);
        byte[] data=null;
        sig.update(data);
        return  sig.sign();
    }
}