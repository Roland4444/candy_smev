package standart;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;
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
    public inn(StreamResult sr){
        this.out = sr;
    }
    private StreamSource input2;
    private StreamResult out;
    public void setinput(String input){
      this.InfoToRequest=input.getBytes();
    }

    public byte[] GetSoap(){
        return InfoToRequest;
    };
    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, XMLSecurityException,
            IOException, CertificateException, NoSuchAlgorithmException, TransformerException,
            ParserConfigurationException, UnrecoverableEntryException,
            NoSuchProviderException, SAXException, KeyStoreException {
        SignerXML sihner = new SignerXML();
        return sihner.sign(GetSoap());
    };


}
