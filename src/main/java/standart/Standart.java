package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.xml.sax.SAXException;
import util.*;
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

public abstract class Standart  {
    public Standart(){
        //this.saaj=saaj;//new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl");
    }

    public void setSAAJ(SAAJ set){
        this.saaj=set;
    }
    /*http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl*/
    protected SAAJ saaj;
    private StreamResult out;
    protected Injector inj=new Injector();
    protected timeBasedUUID gen = new timeBasedUUID();
    public byte[] InfoToRequest;
    public void setinput(String input) throws IOException {
        String dwithId0 = inj.injectTag(input, ":MessageID>",gen.generate());
        String dwithId = inj.flushTagData(dwithId0, "CallerInformationSystemSignature");
        this.InfoToRequest=dwithId.getBytes();
    }
    public abstract byte[] GetSoap();
    public abstract byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, XMLSecurityException, IOException, CertificateException, NoSuchAlgorithmException, TransformerException, ParserConfigurationException, UnrecoverableEntryException, NoSuchProviderException, SAXException, KeyStoreException;
    public abstract byte[] SendRequestRequest();

    public byte[] SendSoapSigned() throws Exception {
       InputStream in = new ByteArrayInputStream(SignedSoap());
       StreamSource input=new StreamSource(in);
       return this.saaj.send(input);
    }



}
