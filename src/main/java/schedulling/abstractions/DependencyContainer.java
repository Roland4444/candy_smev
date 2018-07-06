package schedulling.abstractions;
import DB.Executor;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import standart.gis;
import standart.inn;
import standart.egrip;
import util.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
public class DependencyContainer {
    public DataMapContainer datamap;
    public Sign sign;
    public SignerXML xmlsign;
    public PersonalSign personalSign ;
    public OutputStream os;
    public StreamResult sr;
    public Extractor ext;
    public Executor executor;
    public Injector inj;
    public SAAJ saaj;
    public timeBasedUUID uuidgen;
    public gis gis;
    public inn inn;
    public egrip egrip;


    public DependencyContainer() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
        this.saaj = new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl");
        this.datamap = new DataMapContainer();
        this.sign = new Sign();
        this.xmlsign = new SignerXML();
        this.personalSign  = new PersonalSign();
        this.os = new ByteArrayOutputStream();
        this.sr = new StreamResult(os);
        this.ext=new Extractor();
        Readfile r = new Readfile("sqlset");
        this.executor=new Executor(r.read(), true);
        this.uuidgen=new timeBasedUUID();
        this.inj = new Injector();
        this.gis = new gis(this.sr, this.xmlsign, this.personalSign, this.sign);
        this.inn = new inn(this.sr,this.xmlsign, this.sign);
        this.egrip = new egrip(this.sr,this.xmlsign, this.sign);
        gis.setSAAJ(this.saaj);
        inn.setSAAJ(this.saaj);
        egrip.setSAAJ(this.saaj);
    }

    public DependencyContainer(String addres) throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
        this.saaj = new SAAJ(addres);
        this.datamap = new DataMapContainer();
        this.sign = new Sign();
        this.xmlsign = new SignerXML();
        this.personalSign  = new PersonalSign();
        this.os = new ByteArrayOutputStream();
        this.sr = new StreamResult(os);
        this.ext=new Extractor();
        Readfile r = new Readfile("sqlset");
        this.executor=new Executor(r.read(), true);
        this.uuidgen=new timeBasedUUID();
        this.inj = new Injector();
        this.gis = new gis(this.sr, this.xmlsign, this.personalSign, this.sign);
        this.inn = new inn(this.sr,this.xmlsign, this.sign);
        this.egrip = new egrip(this.sr,this.xmlsign, this.sign);
        gis.setSAAJ(this.saaj);
        inn.setSAAJ(this.saaj);
        egrip.setSAAJ(this.saaj);

    }
}
