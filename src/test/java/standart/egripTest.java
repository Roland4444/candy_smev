package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import util.Sign;
import util.SignatureProcessorException;
import util.SignerXML;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class egripTest {
Sign signer = new Sign();
    @Test
    public void sendEgrip() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>8efe4ff4-b5df-4e6b-ab20-fd14b8e071da</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.6\" ИдДок=\"F69BB651-80C9-4007-B3FB-09A20BBE2DA5\" >\n" +
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
        SignerXML x = new SignerXML();
        egrip inn = new egrip(sr,x, signer);
        inn.setinput(data);
      //  assertNotEquals(null, inn.SendSoapSigned());
        System.out.print(String.valueOf(inn.SendSoapSigned()));
    }
}