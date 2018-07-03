package standart;

import org.junit.Test;
import util.Injector;
import util.Sign;
import util.SignerXML;
import util.timeBasedUUID;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.*;

import static org.junit.Assert.*;

public class innTest {
    Sign signer = new Sign();

    @Test
    public void signedSoap() throws Exception {
        String soap  = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        OutputStream os = new ByteArrayOutputStream();
        InputStream is = new ByteArrayInputStream(soap.getBytes());
        StreamResult sr = new StreamResult(os);
        SignerXML  x = new SignerXML();
        inn inn = new inn(sr,x, signer);
        inn.setinput(soap);
        inn.InfoToRequest=soap.getBytes();
        assertNotEquals(null, inn.SendSoapSigned());
        System.out.print(String.valueOf(inn.SendSoapSigned()));
    }

    @Test
    public void personal() throws Exception {
        String soap  = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>76796044-5e83-11e4-a9ff-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:PersonalSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>AUGmtYkL7MD5MqwxwcQULupXi6FtvCrsHED26tKroC0=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>PFtijkGkwB1rSG3q9YSiZwrtpMk2PV53kZfAqDJr/Z29plOShhDwuGTavcrnORMkAspDsYwqQM69hHnOJeqYiQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhjCCATWgAwIBAgIELVa5zjAIBgYqhQMCAgMwLTEQMA4GA1UECxMHU1lTVEVNMTEMMAoGA1UEChMDT1YzMQswCQYDVQQGEwJSVTAeFw0xNDAyMjYwODQ1MTdaFw0xNTAyMjYwODQ1MTdaMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMzELMAkGA1UEBhMCUlUwYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARAvEqmzwoBlO2KHcBQND5WplUT9eBWXoQCrxuzlPQXCE+WKdNRVMkm4TivyPLgLTwgjxMcUnHSIZSeWL8LB97QPqM7MDkwDgYDVR0PAQH/BAQDAgPoMBMGA1UdJQQMMAoGCCsGAQUFBwMCMBIGA1UdEwEB/wQIMAYBAf8CAQUwCAYGKoUDAgIDA0EA1u5TsTfom9Y0levJ0AeFtZT0lqEQUHL2PCKXmygvV1ZKJ1mBBLxVgCtIlii/Hql0SGAVwySDtZX7M20P3gjRgA==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>oQj0a7j/Bw3XHLBhZ6CN0Dd6cNjP4DzyYnF8FwPsOo4=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>jBxo+CrTW1ESNuQdT7+mqAnFZBIrp1yCnE6KY+jy1OxlartHN3b6XpzD5OtnbCoYHpHy6Ne2hIy9g4mqYbQMOQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhjCCATWgAwIBAgIEamam8TAIBgYqhQMCAgMwLTEQMA4GA1UECxMHU1lTVEVNMTEMMAoGA1UEChMDT1YxMQswCQYDVQQGEwJSVTAeFw0xNDAyMjExMzM0NDNaFw0xNTAyMjExMzM0NDNaMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMTELMAkGA1UEBhMCUlUwYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARAfGGhVXYQU0IYAlpug06oPYTIvFrNh6xZTHivwR6sBKTdDKdLp5SFxJh+bFVmXDoAjmeGfPM8AVi1HTikg9bnOqM7MDkwDgYDVR0PAQH/BAQDAgPoMBMGA1UdJQQMMAoGCCsGAQUFBwMCMBIGA1UdEwEB/wQIMAYBAf8CAQUwCAYGKoUDAgIDA0EAqVxfNnmEwwRqy/1EoQGKGTvQO2vVnJr18jbC6byIxqYpfg+Zw7aRw2lEqflzxIz2LZ3OKzPsjYntUy25z89+3A==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        OutputStream os = new ByteArrayOutputStream();
        InputStream is = new ByteArrayInputStream(soap.getBytes());
        StreamResult sr = new StreamResult(os);
        SignerXML  x = new SignerXML();
        inn inn = new inn(sr,x, signer);
        inn.setinput(soap);
        //inn.InfoToRequest=soap.getBytes();
        assertNotEquals(null, inn.SendSoapSigned());
        System.out.print(String.valueOf(inn.SendSoapSigned()));
    }



    @Test
    public void removeblock() throws IOException {
        Injector parcer = new Injector();
        String data =  "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>8efe4ff4-b5df-4e6b-ab20-fd14b8e071da</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.5\" ИдДок=\"F69BB651-80C9-4007-B3FB-09A20BBE2DA5\" НомерДела=\"F05367CD-EDC6-4E37-A7EE-A422F067F6B5\">\n" +
                "            <ns1:ЗапросЮЛ>\n" +
                "              <ns1:ОГРН>5087746429843</ns1:ОГРН>\n" +
                "            </ns1:ЗапросЮЛ>\n" +
                "          </ns1:FNSVipULRequest>\n" +
                "        </ns2:MessagePrimaryContent>\n" +
                "        <ns:TestMessage />\n" +
                "      </ns:SenderProvidedRequestData>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CONSUMER\">\n" +
                "              <ds:Transforms>\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" />\n" +
                "              </ds:Transforms>\n" +
                "              <ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" />\n" +
                "              <ds:DigestValue>000</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "          </ds:SignedInfo>\n" +
                "          <ds:SignatureValue>000</ds:SignatureValue>\n" +
                "          <ds:KeyInfo>\n" +
                "            <ds:X509Data>\n" +
                "              <ds:X509Certificate>000</ds:X509Certificate>\n" +
                "            </ds:X509Data>\n" +
                "          </ds:KeyInfo>\n" +
                "        </ds:Signature>\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";
        String data2 = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>8efe4ff4-b5df-4e6b-ab20-fd14b8e071da</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.5\" ИдДок=\"F69BB651-80C9-4007-B3FB-09A20BBE2DA5\" НомерДела=\"F05367CD-EDC6-4E37-A7EE-A422F067F6B5\">\n" +
                "            <ns1:ЗапросЮЛ>\n" +
                "              <ns1:ОГРН>5087746429843</ns1:ОГРН>\n" +
                "            </ns1:ЗапросЮЛ>\n" +
                "          </ns1:FNSVipULRequest>\n" +
                "        </ns2:MessagePrimaryContent>\n" +
                "        <ns:TestMessage />\n" +
                "      </ns:SenderProvidedRequestData>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">" +
                "</ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";
        String dwithId = parcer.flushTagData(data, "CallerInformationSystemSignature");
        assertEquals(data2, dwithId);
    }

    @Test
    public void egtest1() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>8efe4ff4-b5df-4e6b-ab20-fd14b8e071da</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.5\" ИдДок=\"F69BB651-80C9-4007-B3FB-09A20BBE2DA5\" НомерДела=\"F05367CD-EDC6-4E37-A7EE-A422F067F6B5\">\n" +
                "            <ns1:ЗапросЮЛ>\n" +
                "              <ns1:ОГРН>5087746429843</ns1:ОГРН>\n" +
                "            </ns1:ЗапросЮЛ>\n" +
                "          </ns1:FNSVipULRequest>\n" +
                "        </ns2:MessagePrimaryContent>\n" +
                "        <ns:TestMessage />\n" +
                "      </ns:SenderProvidedRequestData>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CONSUMER\">\n" +
                "              <ds:Transforms>\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" />\n" +
                "              </ds:Transforms>\n" +
                "              <ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" />\n" +
                "              <ds:DigestValue>000</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "          </ds:SignedInfo>\n" +
                "          <ds:SignatureValue>000</ds:SignatureValue>\n" +
                "          <ds:KeyInfo>\n" +
                "            <ds:X509Data>\n" +
                "              <ds:X509Certificate>000</ds:X509Certificate>\n" +
                "            </ds:X509Data>\n" +
                "          </ds:KeyInfo>\n" +
                "        </ds:Signature>\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";
        OutputStream os = new ByteArrayOutputStream();
        StreamResult sr = new StreamResult(os);
        SignerXML  x = new SignerXML();
        inn inn = new inn(sr,x, signer);
        inn.setinput(data);
        assertNotEquals(null, inn.SendSoapSigned());
        System.out.print(String.valueOf(inn.SendSoapSigned()));
    }

    @Test
    public void testcase1() throws Exception {
        String data = "\uFEFF<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>8efe4ff4-b5df-4e6b-ab20-fd14b8e071da</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.6\" ИдДок=\"F69BB651-80C9-4007-B3FB-09A20BBE2DA5\" НомерДела=\"F05367CD-EDC6-4E37-A7EE-A422F067F6B5\">\n" +
                "            <ns1:ЗапросИП>\n" +
                "              <ns1:ОГРНИП>305500910900012</ns1:ОГРНИП>\n" +
                "            </ns1:ЗапросИП>\n" +
                "          </ns1:FNSVipULRequest>\n" +
                "        </ns2:MessagePrimaryContent>\n" +
                "        <ns:TestMessage />\n" +
                "      </ns:SenderProvidedRequestData>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">       \n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";
        OutputStream os = new ByteArrayOutputStream();
        StreamResult sr = new StreamResult(os);
        SignerXML  x = new SignerXML();
        inn inn = new inn(sr,x, signer);
        inn.setinput(data);
        assertNotEquals(null, inn.SendSoapSigned());
        System.out.print(String.valueOf(inn.SendSoapSigned()));
    }

}