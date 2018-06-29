package util;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class Sign {
    public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        char[] keyPassword = "1".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("vkabank", keyPassword);
        System.out.println(key.toString());
        return key;
    };

    public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        Certificate cert = (Certificate) keyStore.getCertificate("vkabank");
        return cert;
    };


}
