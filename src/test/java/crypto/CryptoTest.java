package crypto;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.transforms.TransformationException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;

import org.xml.sax.SAXException;
import util.*;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;

import static java.time.LocalDate.now;
import static org.junit.Assert.*;

public class CryptoTest {



    @Test
    public void sign() throws IOException, TransformationException, GeneralSecurityException, OperatorCreationException {
        Extractor ext = new Extractor();
        Injector inj = new Injector();
        transFromSuckers trans = new transFromSuckers();
        Gost3411Hash hasher = new Gost3411Hash();
        timeBasedUUID uid = new timeBasedUUID();
        String fullSign = "xml4test/1fullSign.xml";
        String hashSign = "xml4test/2hashSign.xml";
        String base64HashSign = "xml4test/3base64HashSign.xml";
        String cert = "certs/certs.pem";
        String input = "xml4test/naked.xml";
        String preparedT = "xml4test/nakedtranaformed.xml";
        String withId1 = "xml4test/withId1.xml";
        String withIdHash = "xml4test/withIdHash.xml";
        String withIdHashSig = "xml4test/withIdHashSig.xml";
        String withIdHashSigCert = "xml4test/withIdHashSigCert.xml";
        String preparedF = "xml4test/prepared.xml";
        String data="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue></ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue></ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate></ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        FileWriter wr = new FileWriter(input);
        wr.write(data);
        wr.close();

        FileWriter prepared = new FileWriter(preparedF);
        prepared.write(ext.parse(input, "SenderProvidedRequestData"));
        prepared.close();
        org.apache.xml.security.Init.init();

        InputStream in  = new FileInputStream(preparedF);
        OutputStream out  = new FileOutputStream(preparedT);
        trans.process(in, out);
        Path p = Paths.get(preparedT);
        byte[] arr = Files.readAllBytes(p);
        Security.addProvider(new BouncyCastleProvider());
        Crypto crypto = new GOSTCrypto();
        KeyPair root = crypto.generateKeyPair();
        X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));
        KeyPair subject = crypto.generateKeyPair();
        X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));
        System.out.print("\nPublic>>>>>>\n"+hasher.base64(subject.getPublic().getEncoded()));
        System.out.println("cert>>>>>>>>>>>>>"+subjectCert);
        System.out.println("************************************************END CERTIFICATE*******************");
        crypto.toPEM(subjectCert);
        FileWriter wr2 = new FileWriter(cert);
        wr2.write(crypto.toPEM(subjectCert));
        wr2.close();
        inj.injectTagInFile(input,withId1,":MessageID>", uid.generate());
        System.out.println("HASH>>>\n"+hasher.h_Base64rfc2045(arr)+"\n");
        byte[] signatureAllTranformed = crypto.sign(arr, subject.getPrivate());
        byte[] signatureHash = crypto.sign(hasher.hash_byte(arr), subject.getPrivate());
        byte[] signatureHashBase64 = crypto.sign(hasher.h_Base64rfc2045(arr), subject.getPrivate());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Signature>@FULL>>>\n"+hasher.base64(signatureAllTranformed)+"\n<<<<<");
        System.out.println("Signature>@HAsh bytes>>>\n"+hasher.base64(signatureHash)+"\n<<<<<");
        System.out.println("Signature>@Base64Hash>>>\n"+hasher.base64(signatureHashBase64)+"\n<<<<<");
        System.out.println("\n");
        inj.injectTagInFile(input,withId1,":MessageID>", uid.generate());
        inj.injectTagInFile(withId1,withIdHash,"DigestValue>", hasher.h_Base64rfc2045(arr));
        inj.injectTagInFile(withIdHash,withIdHashSig,"SignatureValue>", hasher.base64(signatureAllTranformed));
        inj.injectTagInFile(withIdHashSig,withIdHashSigCert,"X509Certificate>", "");
        inj.injectTagInFile(withIdHash,fullSign,"SignatureValue>", hasher.base64(signatureAllTranformed));
        inj.injectTagInFile(withIdHash,hashSign,"SignatureValue>", hasher.base64(signatureHash));
        inj.injectTagInFile(withIdHash,base64HashSign,"SignatureValue>", hasher.base64(signatureHashBase64));
    }



    @Test
    public void signwithinjectIdatfirst() throws IOException, TransformationException, GeneralSecurityException, OperatorCreationException, ParserConfigurationException, SAXException, CanonicalizationException, InvalidCanonicalizerException {
        Extractor ext = new Extractor();
        Injector inj = new Injector();
        transform35 trans = new transform35();
        Gost3411Hash hasher = new Gost3411Hash();
        timeBasedUUID uid = new timeBasedUUID();
        String fullSign = "xml4test/1fullSign.xml";
        String fulltextSign = "xml4test/1fulltextSign.xml";
        String hashSign = "xml4test/2hashSign.xml";
        String base64HashSign = "xml4test/3base64HashSign.xml";
        String cert = "certs/certs.pem";
        String input = "xml4test/naked.xml";
        String senderData="xml4test/senderData.xml";
        String senderData_="xml4test/senderData_.xml";
        String transformedSender="xml4test/transformedSender.xml";
        String transformedSenderwithId="xml4test/transformedSenderwithId.xml";
        String preparedT = "xml4test/nakedtranaformed.xml";
        String withId1 = "xml4test/withId1.xml";
        String withIdHash = "xml4test/withIdHash.xml";
        String withIdHashSig = "xml4test/withIdHashSig.xml";
        String withIdHashSigCert = "xml4test/withIdHashSigCert.xml";
        String preparedF = "xml4test/prepared.xml";
        String preparedFwithId = "xml4test/preparedwithId.xml";
        String data="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue></ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue></ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate></ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        FileWriter wr = new FileWriter(input);
        wr.write(data);
        wr.close();
        FileWriter senderDatawriter = new FileWriter(senderData);
        senderDatawriter.write(ext.parse(input, "SenderProvidedRequestData"));
        senderDatawriter.close();
        xmltransform transxml = new xmltransform();
        transxml.xmldsig(senderData, senderData_);
        InputStream in  = new FileInputStream(senderData_);
        OutputStream out  = new FileOutputStream(transformedSender);
        trans.process(in, out);
        String gen=uid.generate();
        inj.injectTagInFile(transformedSender,transformedSenderwithId,":MessageID00000>",gen );
        org.apache.xml.security.Init.init();
        String readLine="";
        Path p = Paths.get(transformedSenderwithId);
        BufferedReader b = new BufferedReader(new FileReader(transformedSenderwithId));
        StringBuffer buf = new StringBuffer();
        while ((readLine = b.readLine()) != null)
            buf.append(readLine);
        String stringhfromPreparedT=buf.toString();
        byte[] arr = Files.readAllBytes(p);
        Security.addProvider(new BouncyCastleProvider());
        Crypto crypto = new GOSTCrypto();
        KeyPair root = crypto.generateKeyPair();
        X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));
        KeyPair subject = crypto.generateKeyPair();
        X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));
        crypto.toPEM(subjectCert);
        FileWriter wr2 = new FileWriter(cert);
        wr2.write(crypto.toPEM(subjectCert));
        wr2.close();
        System.out.println("HASH>>>\n"+hasher.h_Base64rfc2045(arr)+"\n");
        System.out.println("HASH>>>\n"+hasher.h_Base64rfc2045(stringhfromPreparedT)+"\n");
        byte[] rawSiall = crypto.sign(stringhfromPreparedT, subject.getPrivate());
        byte[] signatureAllTranformed = crypto.sign(arr, subject.getPrivate());
        byte[] signatureHash = crypto.sign(hasher.hash_byte(arr), subject.getPrivate());
        byte[] signatureHashBase64 = crypto.sign(hasher.h_Base64rfc2045(arr), subject.getPrivate());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Signature>@FULL@bytes>>>\n"+hasher.base64(signatureAllTranformed)+"\n<<<<<");
        System.out.println("Signature>@FULL@String>>>\n"+hasher.base64(rawSiall)+"\n<<<<<");
        System.out.println("Signature>@HAsh bytes>>>\n"+hasher.base64(signatureHash)+"\n<<<<<");
        System.out.println("Signature>@Base64Hash>>>\n"+hasher.base64(signatureHashBase64)+"\n<<<<<");
        inj.injectTagInFile(input,withId1,":MessageID000000>", gen); //":MessageID>"
        inj.injectTagInFile(withId1,withIdHash,"DigestValue>", hasher.h_Base64rfc2045(arr));
        inj.injectTagInFile(withIdHash,withIdHashSig,"SignatureValue>", hasher.base64(rawSiall));
        inj.injectTagInFile(withIdHashSig,withIdHashSigCert,"X509Certificate>", "");
        System.out.println("\nCHECK:"+withIdHashSigCert);
        inj.injectTagInFile(withIdHash,fullSign,"SignatureValue>", hasher.base64(signatureAllTranformed));
        inj.injectTagInFile(withIdHash,hashSign,"SignatureValue>", hasher.base64(signatureHash));
        inj.injectTagInFile(withIdHash,base64HashSign,"SignatureValue>", hasher.base64(signatureHashBase64));
    }

    @Test
    public void genandsave() throws GeneralSecurityException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        String key = "certs/key.key";
        String gencert = "certs/certs.pem";
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream("certs/123.pfx"), "1235".toCharArray());
        PrivateKey key2 = (PrivateKey)keystore.getKey("key", "123".toCharArray());
    }



 /*   @Test
    public void testtrans() throws TransformerConfigurationException, GeneralSecurityException, MarshalException, XMLSignatureException, IOException, OperatorCreationException {
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        DigestMethod digestMethod = fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null);
        Reference ref = fac.newReference("#10",digestMethod);
        ArrayList refList = new ArrayList();
        refList.add(ref);
        CanonicalizationMethod cm =  fac.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#",null);
        SignatureMethod sm = fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411",null);
        SignedInfo signedInfo =fac.newSignedInfo(cm,sm,refList);
        DOMSignContext signContext = null;
        Crypto crypto = new GOSTCrypto();
        KeyPair root = crypto.generateKeyPair();
        X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));
        KeyPair subject = crypto.generateKeyPair();
        X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));

      /*  signContext = new DOMSignContext(subject.getPrivate(),securityHeader);
        signContext.setURIDereferencer(new URIResolverImpl());
        KeyInfoFactory keyFactory = KeyInfoFactory.getInstance();
        DOMStructure domKeyInfo = new DOMStructure(tokenReference);
        KeyInfo keyInfo = keyFactory.newKeyInfo(Collections.singletonList(domKeyInfo));
        XMLSignature signature = fac.newXMLSignature(signedInfo,keyInfo)
        signature.sign(signContext);  */
  //  }

}