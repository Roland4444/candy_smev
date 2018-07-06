package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import util.*;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class StandartTest {
    Extractor ext = new Extractor();
    Injector inj = new Injector();
    Sign s = new Sign();
    SignerXML x = new SignerXML();
    PersonalSign ps = new PersonalSign();
    OutputStream os = new ByteArrayOutputStream();
    StreamResult sr = new StreamResult(os);
    gis gis = new gis(sr,x, ps, s);
    egrip inn = new egrip(sr,x, s);

    public StandartTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
    }

    @Test
    public void getResReq() throws Exception {
        inn inn = new inn(sr,x, s);
        inn.setSAAJ(new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl"));
        assertNotEquals(null, inn.GetResReq());

    }
}