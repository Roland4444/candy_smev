package crypto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;
import util.*;
import java.io.*;
import java.security.*;
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

}