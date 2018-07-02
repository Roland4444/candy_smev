package util;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.assertNotEquals;

public class SignTest {

    SignerXML n;
    public SignTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        this.n = new SignerXML();
    }


    @Test
    public void init() throws XMLSecurityException, ClassNotFoundException, SignatureProcessorException, IOException, GeneralSecurityException, TransformerException, ParserConfigurationException, SAXException, OperatorCreationException {
        assertNotEquals(null,n);
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        byte[] signature=n.sign(data.getBytes());
        assertNotEquals(null, signature);
        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream("xml4test/result.xml");
            fileOuputStream.write(signature);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Test
    public void testvipip() throws XMLSecurityException, ClassNotFoundException, SignatureProcessorException, IOException, GeneralSecurityException, TransformerException, ParserConfigurationException, SAXException, OperatorCreationException {
        assertNotEquals(null,n);
        Injector parcer = new Injector();
        timeBasedUUID gen = new timeBasedUUID();
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
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";
        String dwithId = parcer.injectTag(data, ":MessageID>",gen.generate());

        byte[] signature=n.sign(dwithId.getBytes());
        assertNotEquals(null, signature);
        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream("xml4test/resultvipip.xml");
            fileOuputStream.write(signature);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void init2() throws XMLSecurityException, ClassNotFoundException, SignatureProcessorException, IOException, GeneralSecurityException, TransformerException, ParserConfigurationException, SAXException, OperatorCreationException {
        assertNotEquals(null,n);
        String data = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:GetResponseRequest>\n" +
                "      <ns2:MessageTypeSelector xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\">\n" +
                "        <ns2:NamespaceURI>urn://ru/mvd/sovm/p001/1.0.0</ns2:NamespaceURI>\n" +
                "        <ns2:RootElementLocalName>request</ns2:RootElementLocalName>\n" +
                "        <ns2:Timestamp>2018-06-29T10:39:04.683</ns2:Timestamp>\n" +
                "      </ns2:MessageTypeSelector>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CALLER\">\n" +
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
                "    </ns:GetResponseRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        byte[] signature=n.signcaller(data.getBytes());
        assertNotEquals(null, signature);
        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream("xml4test/result2.xml");
            fileOuputStream.write(signature);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void cert(){
        byte[]           input = new byte[] { (byte)0xbe, (byte)0xef };
    }
}