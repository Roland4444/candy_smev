package crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Gost3411HashTest  {
    @Test
    public void h() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String data ="<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("fe35e5ef45f09edb49079b12a24c21f126951f0a368238082d2bb4a8168b500a", hash.h(data));
        System.out.println(hash.h(data));
        String data2 ="<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("7bbea855e6066a914313e3d5ea096c8f45c38cb1f274b31dd1c4a414f63c7d69", hash.h(data2));
        System.out.println(hash.h(data2));
    }

    @Test
    public void shift() {
        byte a;
        a=10;
        int i;
        i = a << 4;
        System.out.println("Original value of a: " + a);
        assertEquals(i,160);
    }

    @Test
    public void swap() {
        int a;
        a = 0x10;
        int shift = 8;
        a = (a>>(32 - shift)) | a << 8;
        System.out.println(Integer.toHexString(a));
    }

    @Test
    public void swapString() {
        Gost3411Hash m = new Gost3411Hash();
        assertEquals("89e1", m.swapString("981e"));
    }

    @Test
    public void h_Base64() throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        Gost3411Hash hash = new Gost3411Hash();
        String data ="<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", "e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=");
        System.out.print(hash.h_Base64rfc2045(data));
    }

    @Test
    public void base64() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", hash.base64(hash.hash_byte(input)));
    }

    @Test
    public void hash_byte() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertNotEquals(null, hash.hash_byte(input));
    }

    @Test
    public void base64rfc2045() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", hash.base64(hash.hash_byte(input)));
    }

    @Test
    public void base64test() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", hash.base64(hash.hash_byte(input)));
    }





    @Test
    public void getBytesFromBase64() throws NoSuchAlgorithmException {
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        Gost3411Hash hash = new Gost3411Hash();
        BigInteger out1 = new BigInteger( 1, hash.hash_byte(input) );
        String hex1 = String.format( "%02x", out1 );
        BigInteger out2 = new BigInteger( 1, hash.getBytesFromBase64("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=") );
        String hex2 = String.format( "%02x",out2 );
        System.out.println(hex1);
        System.out.println(hex2);
        assertEquals(hash.hash_byte(input).length, hash.getBytesFromBase64("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=").length);
    }




}