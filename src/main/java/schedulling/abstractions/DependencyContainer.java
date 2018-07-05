package schedulling.abstractions;
import DB.Executor;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import util.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
public class DependencyContainer {
    public Sign sign;
    public SignerXML xmlsign;
    public PersonalSign personalSign ;
    public OutputStream os;
    public StreamResult sr;
    public Extractor ext;
    public Executor executor;
    public DependencyContainer() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
        this.sign = new Sign();
        this.xmlsign = new SignerXML();
        this.personalSign  = new PersonalSign();
        this.os = new ByteArrayOutputStream();
        this.sr = new StreamResult(os);
        this.ext=new Extractor();
        Readfile r = new Readfile("sqlset");
        this.executor=new Executor(r.read(), true);

    }
}
