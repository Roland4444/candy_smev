package util;

import crypto.Gost3411Hash;
import org.apache.xml.security.transforms.TransformationException;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class ExtractorTest {

    @Test
    public void parse() throws IOException {
        Extractor ext = new Extractor();
        assertEquals("<Sender><addtional>ghghghgh</addtional><additi>fgdffdghfdhfddfhdfhfdfdhfdhfdhdfhdffh</additi></Sender>", ext.parse("3.xml", "Sender"));
    }

    @Test
    public void parse2() throws IOException, TransformationException, NoSuchAlgorithmException {
        Extractor ext = new Extractor();
        String result = "<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>";
        assertEquals(result, ext.parse("1.xml", "SenderProvidedRequestData"));
        FileWriter wr = new FileWriter("reqdataonly.xml");
        wr.write(ext.parse("1.xml", "SenderProvidedRequestData"));
        transFromSuckers trans = new transFromSuckers();
        InputStream in  = new FileInputStream("reqdataonly.xml");
        OutputStream out  = new FileOutputStream("result.xml");
        trans.process(in, out);
        Path p = Paths.get("result.xml");
        byte[] arr = Files.readAllBytes(p);
        Gost3411Hash hasher = new Gost3411Hash();
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hasher.h_Base64rfc2045(arr));
    }
}