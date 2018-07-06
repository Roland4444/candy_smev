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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class gis extends Standart {
    public gis(StreamResult sr, SignerXML sihner, Sign personal, Sign Full){
        this.out = sr;
        this.signer =sihner;
        this.personal=personal;
        this.MainSign =Full;
    }
    private StreamSource input2;
    private SignerXML signer;
    private StreamResult out;
    private Sign personal;
    public void setinput(String input) throws IOException {
        String genned= gen.generate();
        System.out.println(genned);
        String dwithId0 = inj.injectTag(input, ":MessageID>",genned);
        String dwithId = inj.flushTagData(dwithId0, "CallerInformationSystemSignature");
        String dwithId2 = inj.flushTagData(dwithId, "PersonalSignature");
        //String wiNumberDeal = inj.injectAttribute(dwithId,"ИдДок", genned);
        this.InfoToRequest=dwithId2.getBytes();
    }
    public  byte[] GetSoap(){
        return InfoToRequest;
    };
    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, XMLSecurityException,
            IOException, CertificateException, NoSuchAlgorithmException, TransformerException,
            ParserConfigurationException, UnrecoverableEntryException,
            NoSuchProviderException, SAXException, KeyStoreException {
        return signer.signcallerns4(MainSign, signer.personalsign(personal, GetSoap()));
    };

    public byte[] GetResponseRequest() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(MainSign, GetSoap()));
        StreamSource input=new StreamSource(in);
        return this.saaj.send(input);
    }

    public byte[] GetResponseRequestwoFilter() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallernsbycaller(MainSign, GetSoap()));
        StreamSource input=new StreamSource(in);
        return this.saaj.send(input);
    }

    public byte[] ack() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(MainSign, GetSoap()));
        StreamSource input=new StreamSource(in);
        return this.saaj.send(input);
    }


}
