package util;

import crypto.Gost3411Hash;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.XPath2FilterContainer;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.voskhod.crypto.impl.SmevTransformSpi;
import spark.utils.IOUtils;


import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import static org.junit.Assert.*;

public class SignTest {
    private static final String XMLDSIG_MORE_GOSTR34102001_GOSTR3411 = "http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411";
    private static final String XMLDSIG_MORE_GOSTR3411 = "http://www.w3.org/2001/04/xmldsig-more#gostr3411";
    private static final String CANONICALIZATION_METHOD = "http://www.w3.org/2001/10/xml-exc-c14n#";
    private static final String DS_SIGNATURE = "//ds:Signature";
    private static final String SIG_ID = "sigID";
    private static final String COULD_NOT_FIND_XML_ELEMENT_NAME = "ERROR! Could not find xmlElementName = ";
    private static final String GRID = "#";
    private static final String XML_SIGNATURE_ERROR = "xmlDSignature ERROR: ";
    @Test
    public void signed() {
    }

    @Test
    public void getPrivate() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException, CertificateException {
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        assertNotEquals(null, sign.getPrivate());

    }

    @Test
    public void getCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        Sign sign = new Sign();
        Sign sign2 = new Sign();
        assertEquals(sign.toPEM(sign.getCert()), sign2.toPEM(sign2.getCert()));
    }

    @Test
    public void signed1() throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        String f = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        Sign sign = new Sign();
        String preparedT = "xml4test/razedNoAttachWithTransformReady!Result.xml";
        Path p = Paths.get(preparedT);
        byte[] arr = Files.readAllBytes(p);
        Gost3411Hash g = new Gost3411Hash();
        System.out.println(g.base64(sign.signed(arr)));
    }

    @Test
    public void dirtysign() throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        System.out.println(g.base64(sign.dirtysign(input)));
    }

    @Test
    public void dirtysigncompat() throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        System.out.println(g.base64(sign.dirtysigncompat(input)));
    }

    @Test
    public void dirtysignRaw() throws NoSuchAlgorithmException, CertificateException, InvalidKeyException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        byte[] hash = g.hash_byte(input);
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", g.base64(hash));
        byte[] signature=sign.dirtysignRaw(hash);
        assertNotEquals(null, signature);
        System.out.print(g.base64(signature));
    }

    @Test
    public void tinytest() throws NoSuchAlgorithmException, CertificateException, InvalidKeyException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        InputStream in = new FileInputStream("xml4test/prepared.xml");
        byte[] input = IOUtils.toByteArray(in);
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        byte[] hash = g.hash_byte(input);
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", g.base64(hash));
        System.out.println("hash\n"+g.base64(hash));
        byte[] signature=sign.signed(hash);
        assertNotEquals(null, signature);
        System.out.println("signature\n"+g.base64(signature));
        byte[] signature2=sign.signed(input);
        assertNotEquals(null, signature);
        System.out.println("signature2\n"+g.base64(signature2));
        byte[] signature3=sign.dirtysign(g.base64(hash));
        assertNotEquals(null, signature3);
        System.out.println("signature3\n"+g.base64(signature3));
        byte[] signature4=sign.dirtysignRaw(hash);
        assertNotEquals(null, signature4);
        System.out.println("signature4\n"+g.base64(signature4));
    }

    @Test
    public void tinysign() throws NoSuchAlgorithmException, CertificateException, InvalidKeyException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        InputStream in = new FileInputStream("xml4test/signedinfo.xml");
        byte[] input = IOUtils.toByteArray(in);
        Sign sign = new Sign();
        Gost3411Hash g = new Gost3411Hash();
        byte[] hash = g.hash_byte(input);
       // assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", g.base64(hash));
        System.out.println("hash\n"+g.base64(hash));
        byte[] signature=sign.signed(hash);
        assertNotEquals(null, signature);
        System.out.println("signature\n"+g.base64(signature));
        byte[] signature2=sign.signed(input);
        assertNotEquals(null, signature);
        System.out.println("signature2\n"+g.base64(signature2));
        byte[] signature3=sign.dirtysign(g.base64(hash));
        assertNotEquals(null, signature3);
        System.out.println("signature3\n"+g.base64(signature3));
        byte[] signature4=sign.dirtysignRaw(hash);
        assertNotEquals(null, signature4);
        System.out.println("signature4\n"+g.base64(signature4));
    }

    @Test
    public void getelem() throws NoSuchAlgorithmException, CertificateException, InvalidKeyException, KeyStoreException, SignatureException, NoSuchProviderException, UnrecoverableEntryException, IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setExpandEntityReferences(false);
        Document doc = factory.newDocumentBuilder().parse(new File("xml4test/razedNoAttachWithTransformReady!.xml"));
        Element element = doc.getElementById("SIGNED_BY_CONSUMER");
        assertNotEquals(null, element);
    }
    @Test
    public void  sign() throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit.init();
        byte[] data; // XML сообщение в виде массива байтов

        String xmlElementName="ns2:CallerInformationSystemSignature"; // имя элемента в XML вместе с префиксом, в который следует добавить подпись, для СМЭВ-3 в общем случае "ns2:CallerInformationSystemSignature"

        String xmlElementID="SIGNED_BY_CONSUMER"; // ID элемента в XML (если присутствует) вместе с префиксом, на который следует поставить подпись, для СМЭВ-3 в общем случае "SIGNED_BY_CONSUMER"

        X509Certificate certificate; // сертификат открытого ключа проверки подписи

        PrivateKey privateKey; // закрытый ключ подписи
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Sign sign = new Sign();
        // установка флага, определяющего игнорирование пробелов в
        // содержимом элементов при обработке XML-документа
        dbf.setIgnoringElementContentWhitespace(true);

        // установка флага, определяющего преобразование узлов CDATA в
        // текстовые узлы при обработке XML-документа
        dbf.setCoalescing(true);

        // установка флага, определяющего поддержку пространств имен при
        // обработке XML-документа
        dbf.setNamespaceAware(true);

// загрузка содержимого подписываемого документа на основе
        // установленных флагами правил из массива байтов data            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(new File("xml4test/razedNoAttachWithTransformReady!.xml"));

        final String signMethod = XMLDSIG_MORE_GOSTR34102001_GOSTR3411;

        // алгоритм хеширования, используемый при подписи (ГОСТ Р 34.11-94)
        final String digestMethod = XMLDSIG_MORE_GOSTR3411;

        final String canonicalizationMethod = CANONICALIZATION_METHOD;


        String[][] filters = {{XPath2FilterContainer.SUBTRACT, DS_SIGNATURE}};
        String sigId = SIG_ID;

        // инициализация объекта формирования ЭЦП в соответствии с
        // алгоритмом ГОСТ Р 34.10-2001
        XMLSignature sig = new XMLSignature(doc, "", signMethod, canonicalizationMethod);

        // определение идентификатора первого узла подписи

        sig.setId(sigId);

        // получение корневого узла XML-документа
        Element anElement = null;
        if (xmlElementName == null) {
            anElement = doc.getDocumentElement();
        } else {
            NodeList nodeList = doc.getElementsByTagName(xmlElementName);
            anElement = (Element) nodeList.item(0);
        }
        // = doc.getElementById("#AppData");
        // добавление в корневой узел XML-документа узла подписи
        if (anElement != null) {
            anElement.appendChild(sig.getElement());
        } else {

        }

        /*
         * Определение правил работы с XML-документом и добавление в узел подписи этих
         * правил
         */

        // создание узла преобразований <ds:Transforms> обрабатываемого
        // XML-документа
        Transforms transforms = new Transforms(doc);

        // добавление в узел преобразований правил работы с документом
        // transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);
        transforms.addTransform(SmevTransformSpi.ALGORITHM_URN);

        // добавление в узел подписи ссылок (узла <ds:Reference>),
        // определяющих правила работы с
        // XML-документом (обрабатывается текущий документ с заданными в
        // узле <ds:Transforms> правилами
        // и заданным алгоритмом хеширования)
        sig.addDocument(xmlElementID == null ? "" : GRID + xmlElementID, transforms, digestMethod);

        /*
         * Создание подписи всего содержимого XML-документа на основе закрытого ключа,
         * заданных правил и алгоритмов
         */

        // создание внутри узла подписи узла <ds:KeyInfo> информации об
        // открытом ключе на основе


        // создание подписи XML-документа
        sig.sign(sign.getPrivate());

        // определение потока, в который осуществляется запись подписанного
        // XML-документа
        ByteArrayOutputStream bais = new ByteArrayOutputStream();

        // инициализация объекта копирования содержимого XML-документа в
        // поток
        TransformerFactory tf = TransformerFactory.newInstance();

        // создание объекта копирования содержимого XML-документа в поток
        Transformer trans = tf.newTransformer();

        // копирование содержимого XML-документа в поток
        trans.transform(new DOMSource(doc), new StreamResult(bais));
        bais.close();


        try(OutputStream outputStream = new FileOutputStream("thefilename")) {
            bais.writeTo(outputStream);
        }


    }

    @Test
    public void transformMuddafucka() throws ParserConfigurationException, IOException, SAXException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, NoSuchAlgorithmException {
        String hotdata="xml4test/razedNoAttachWithTransformReady!c4555.xml";
        String input = "xml4test/razedNoAttachWithTransformReady!.xml";
        String output = "xml4test/razedNoAttachWithTransformReady!c4.xml";
        String outputs = "xml4test/boeing!c4.xml";
        String outputstrans = "xml4test/boeing!c4!!tr.xml";
        xmltransform trans = new xmltransform();
        trans.xmldsig(input,output);
        transform35 test =  new transform35();
        InputStream in = new FileInputStream("xml4test/razedNoAttachWithTransformReady!c4.xml");
        OutputStream out = new FileOutputStream(hotdata);
        test.process(in, out);
        //gethash!
        Gost3411Hash hasher = new Gost3411Hash();
        Extractor ext = new Extractor();
        ext.parse(input, "SenderProvidedRequestData");
        assertEquals("<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>",
                ext.parse(output, "SenderProvidedRequestData"));
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hasher.h_Base64rfc2045(ext.parse(output, "SenderProvidedRequestData")));
        String signInfo = ext.parse(output, "SignedInfo");
        FileWriter wr = new FileWriter("xml4test/boeing.xml");
        wr.write(signInfo);
        wr.close();
       // trans.xmldsig("boeing.xml",outputs);
        InputStream in2 = new FileInputStream("xml4test/boeing.xml");
        OutputStream out2 = new FileOutputStream(outputstrans);
        test.process(in2, out2);

    }



    @Test
    public void  shortedsign() throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit.init();
        byte[] data; // XML сообщение в виде массива байтов

        String xmlElementName="ns2:CallerInformationSystemSignature"; // имя элемента в XML вместе с префиксом, в который следует добавить подпись, для СМЭВ-3 в общем случае "ns2:CallerInformationSystemSignature"

        String xmlElementID="SIGNED_BY_CONSUMER"; // ID элемента в XML (если присутствует) вместе с префиксом, на который следует поставить подпись, для СМЭВ-3 в общем случае "SIGNED_BY_CONSUMER"

        X509Certificate certificate; // сертификат открытого ключа проверки подписи

        PrivateKey privateKey; // закрытый ключ подписи
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Sign sign = new Sign();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setCoalescing(true);
        dbf.setNamespaceAware(true);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(new File("xml4test/razedNoAttachWithTransformReady!.xml"));

        final String signMethod = XMLDSIG_MORE_GOSTR34102001_GOSTR3411;


        final String digestMethod = XMLDSIG_MORE_GOSTR3411;

        final String canonicalizationMethod = CANONICALIZATION_METHOD;


        String[][] filters = {{XPath2FilterContainer.SUBTRACT, DS_SIGNATURE}};
        String sigId = SIG_ID;

        // инициализация объекта формирования ЭЦП в соответствии с
        // алгоритмом ГОСТ Р 34.10-2001
        XMLSignature sig = new XMLSignature(doc, "", signMethod, canonicalizationMethod);

        // определение идентификатора первого узла подписи

        sig.setId(sigId);

        // получение корневого узла XML-документа
        Element anElement = null;
        if (xmlElementName == null) {
            anElement = doc.getDocumentElement();
        } else {
            NodeList nodeList = doc.getElementsByTagName(xmlElementName);
            anElement = (Element) nodeList.item(0);
        }
        // = doc.getElementById("#AppData");
        // добавление в корневой узел XML-документа узла подписи
        if (anElement != null) {
            anElement.appendChild(sig.getElement());
        } else {

        }

        /*
         * Определение правил работы с XML-документом и добавление в узел подписи этих
         * правил
         */

        // создание узла преобразований <ds:Transforms> обрабатываемого
        // XML-документа
        Transforms transforms = new Transforms(doc);

        // добавление в узел преобразований правил работы с документом
        // transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);


        // добавление в узел подписи ссылок (узла <ds:Reference>),
        // определяющих правила работы с
        // XML-документом (обрабатывается текущий документ с заданными в
        // узле <ds:Transforms> правилами
        // и заданным алгоритмом хеширования)
        sig.addDocument(xmlElementID == null ? "" : GRID + xmlElementID, transforms, digestMethod);

        /*
         * Создание подписи всего содержимого XML-документа на основе закрытого ключа,
         * заданных правил и алгоритмов
         */

        // создание внутри узла подписи узла <ds:KeyInfo> информации об
        // открытом ключе на основе


        // создание подписи XML-документа
        sig.sign(sign.getPrivate());

        // определение потока, в который осуществляется запись подписанного
        // XML-документа
        ByteArrayOutputStream bais = new ByteArrayOutputStream();

        // инициализация объекта копирования содержимого XML-документа в
        // поток
        TransformerFactory tf = TransformerFactory.newInstance();

        // создание объекта копирования содержимого XML-документа в поток
        Transformer trans = tf.newTransformer();

        // копирование содержимого XML-документа в поток
        trans.transform(new DOMSource(doc), new StreamResult(bais));
        bais.close();


        try(OutputStream outputStream = new FileOutputStream("thefilename")) {
            bais.writeTo(outputStream);
        }


    }


}