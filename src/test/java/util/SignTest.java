package util;

import crypto.Gost3411Hash;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;

public class SignTest {

    @Test
    public void signed() {
    }

    @Test
    public void getPrivate() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException, CertificateException {
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        assertNotEquals(null, sign.getPrivate());
        System.out.print(g.h_Base64rfc2045(sign.getPrivate().getEncoded()));
    }

    @Test
    public void getCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        Sign sign = new Sign();
        Sign sign2 = new Sign();
        assertEquals(sign.toPEM(sign.getCert()), sign2.toPEM(sign2.getCert()));
    }

    @Test
    public void signed1() throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        String f = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        Sign sign = new Sign();
        String preparedT = "xml4test/razedNoAttachWithTransformReady!Result.xml";
        Path p = Paths.get(preparedT);
        byte[] arr = Files.readAllBytes(p);
        Gost3411Hash g = new Gost3411Hash();
        System.out.println(g.base64(sign.signed(arr)));
    }
}