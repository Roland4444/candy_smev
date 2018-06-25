package util;

import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;

public class SignTest {

    @Test
    public void signed() {
    }

    @Test
    public void getPrivate() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException, CertificateException {
        Sign sign = new Sign();
        assertNotEquals(null, sign.getPrivate());
    }

    @Test
    public void getCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        Sign sign = new Sign();
        assertNotEquals(null, sign.getCert());
    }
}



