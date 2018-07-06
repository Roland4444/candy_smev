package standart;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;
import util.Sign;
import util.SignatureProcessorException;
import util.SignerXML;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class inn extends Standart {
    public inn(StreamResult sr, SignerXML sihner, Sign signer){
        this.out = sr;
        this.signer =sihner;
        this.MainSign = signer;
    }
    private StreamSource input2;

    private StreamResult out;
    public void setinput(String input) throws IOException {
        String genned= gen.generate();
        String dwithId0 = inj.injectTag(input, ":MessageID>",genned);
        String dwithId = inj.flushTagData(dwithId0, "CallerInformationSystemSignature");
       // String wiNumberDeal = inj.injectAttribute(dwithId,"ИдДок", genned);
        this.InfoToRequest=dwithId.getBytes();
    }
    public byte[] GetSoap(){
        return InfoToRequest;
    };
    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, XMLSecurityException,
            IOException, CertificateException, NoSuchAlgorithmException, TransformerException,
            ParserConfigurationException, UnrecoverableEntryException,
            NoSuchProviderException, SAXException, KeyStoreException {
       return signer.signcallerns2(MainSign, GetSoap());
    };



}
