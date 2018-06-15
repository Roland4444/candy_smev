package util;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class InjectorTest {

    @Test
    public void getTagValuetest() {
        String xml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>b3b2a8e2-55c5-4423-a570-86e4d1e87ccd</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"C289035F-43EB-41BF-899F-E7CFB97FC08B\" НомерДела=\"56C6FAA6-A314-466E-96C9-07ADA1689F1F\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>1234567890</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        Injector parcer = new Injector();
        assertEquals("b3b2a8e2-55c5-4423-a570-86e4d1e87ccd", parcer.getTagValue(xml, "ns:MessageID"));
    }

    @Test
    public void inject() throws IOException {
        String xml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>b3b2a8e2-55c5-4423-a570-86e4d1e87ccd</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"C289035F-43EB-41BF-899F-E7CFB97FC08B\" НомерДела=\"56C6FAA6-A314-466E-96C9-07ADA1689F1F\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>1234567890</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        Injector parcer = new Injector();
        FileWriter wr = new FileWriter("xml4test/out.xml");
        wr.write(parcer.inject(xml));
        wr.close();
    }

    @Test
    public void injectandExtractTest() throws IOException {
        Injector parcer = new Injector();
        parcer.inject("xml4test/removedMessage.xml","xml4test/check.xml");
        Extractor ext = new Extractor();

      //  assertEquals(result, ext.parse("xml4test/check.xml", "MessageID"));
        FileWriter wr = new FileWriter("xml4test/reqdataonly.xml");
        wr.write(ext.parse("xml4test/1.xml", "SenderProvidedRequestData"));
    }

    @Test
    public void inject1() {
    }

    @Test
    public void injectTag() {
        String xml = "<inj></inj>";
        Injector parcer = new Injector();
        assertEquals("<inj>2</inj>", parcer.injectTag(xml,"inj>","2"));
    }

    @Test
    public void injectTagInFile() throws IOException {
        Injector parcer = new Injector();
        timeBasedUUID gen = new timeBasedUUID();
        String dataInject = gen.generate();
        System.out.println(dataInject);
        parcer.injectTagInFile("xml4test/removedMessage.xml","xml4test/check.xml",":MessageID>", dataInject);
        Extractor ext = new Extractor();
        String result=ext.parse("xml4test/check.xml", "MessageID");
        assertTrue(result.indexOf(dataInject)>0);
        assertEquals(dataInject, ext.extractRaw("xml4test/check.xml", "MessageID"));
        System.out.println(ext.extractRaw("xml4test/check.xml", "MessageID"));
    }
}