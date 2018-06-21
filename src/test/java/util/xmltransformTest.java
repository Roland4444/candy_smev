package util;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class xmltransformTest {

    @Test
    public void xmldsig() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException {
        xmltransform g = new xmltransform();
        String info ="Exclusive XML Canonicalization от 18 июля 2002 http://www.w3.org/2001/10/xml-exc-c14n#";
        String etalon="<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\" xmlns:ns4=\"http://test/0\" xmlns:ns5=\"http://test/a\" ns4:attC=\"ccc\" ns2:attF=\"fff\" ns3:attD=\"ddd\" ns3:attE=\"eee\" ns5:attZ=\"zzz\" attA=\"aaa\" attB=\"bbb\"></ns3:elementThree></ns2:elementTwo></ns1:elementOne><ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\" xmlns:ns4=\"http://test/0\" xmlns:ns5=\"http://test/a\" ns4:attC=\"ccc\" ns2:attF=\"fff\" ns3:attD=\"ddd\" ns3:attE=\"eee\" ns5:attZ=\"zzz\" attA=\"aaa\" attB=\"bbb\"></ns3:elementThree></ns2:elementTwo></ns1:elementOne>";
        String s2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>";
        System.out.println(g.xmldsig(s2.getBytes()));
        assertEquals(etalon,g.xmldsig(s2.getBytes()));

    }

    @Test
    public void xmldsig1() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException {
        String input = "xml4test/senderData.xml";
        String output = "xml4test/senderData_.xml";
        xmltransform trans = new xmltransform();
        trans.xmldsig(input,output);
    }
}