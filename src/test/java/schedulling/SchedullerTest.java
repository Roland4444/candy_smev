package schedulling;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DependencyContainer;
import standart.gis;
import util.SignatureProcessorException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class SchedullerTest {
    DependencyContainer deps = new DependencyContainer();
    Scheduller sch = new Scheduller(deps);

    public SchedullerTest() throws SQLException, ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
    }

    @Test
    public void setTasker() throws SQLException {
        assertNotEquals(null, sch);
        assertNotEquals(null, sch.processor);
        assertNotEquals(null, sch.tasker);
    }


    @Test
    public void run1() throws Exception {
        sch.loop();
        String msgIOd = "f6a09006-689a-11e8-8058-012ae3068118";
        assertNotEquals(null, sch.deps.datamap.DataConveer.get(msgIOd));
        Iterator it = sch.deps.datamap.DataConveer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println("Iterator=>" + pair.getKey());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Test
    public void run2() throws Exception {
        sch.loop(2);
        String msgIOd = "f6a09006-689a-11e8-8058-012ae3068118";
        assertNotEquals(null, sch.deps.datamap.DataConveer.get(msgIOd));
        Iterator it = sch.deps.datamap.DataConveer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println("Iterator=>" + pair.getKey());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Test
    public void run22() throws Exception {
        String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        for (int i = 0; i < 1; i++) {
            String res0 = this.deps.inj.injectTagDirect(res, ":MessageID", this.deps.uuidgen.generate());
            PutResult resulter = new PutResult();
            resulter.setOperator("gis");
            resulter.setDataToWork(res0.getBytes());
            String msgId = this.deps.ext.extractTagValue(res0, ":MessageID");
            deps.datamap.DataConveer.put(msgId, resulter);
        }
        //Iterator it = sch.deps.datamap.DataConveer.entrySet().iterator();
        sch.loop(1);
        //sch.processor.sendAll();
        /*
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Iterator=>"+pair.getKey() );
            ResolverInOutData resolver = (ResolverInOutData) pair.getValue();
            assertEquals("gis", resolver.getOperator());
            assertNotEquals(null, resolver);
            assertNotEquals(null, resolver.getOperator());
            if (resolver.getOperator().equals("gis")) {
                deps.gis.setinput(new String(resolver.DataToWork));
                if (new String(deps.gis.SendSoapSigned()).indexOf("requestIsQueued")>0 ){
                    assertNotEquals(null, sch.processor);
                    sch.processor.succesquued();
                }
                else sch.processor.errorquued();
            }
         //   resolver.getOperator().setinput(new String(resolver.DataToWork));
         //   resolver.getOperator().SendSoapSigned();
            it.remove(); // avoids a ConcurrentModificationException
        }
        assertNotEquals(null, sch.deps.executor);
        sch.loop(5);

        */
    }

    @Test
    public void launchalltest() throws Exception {
        String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        for (int i = 0; i < 3; i++) {
            String res0 = this.deps.inj.injectTagDirect(res, ":MessageID", this.deps.uuidgen.generate());
            PutResult resulter = new PutResult();
            resulter.setOperator("gis");
            resulter.setDataToWork(res0.getBytes());
            String msgId = this.deps.ext.extractTagValue(res0, ":MessageID");
            deps.datamap.DataConveer.put(msgId, resulter);
        }
        sch.processor.sendAll();
        sleep(10000);
        System.out.println("******************************************");
        String result = getrespreq();//<= GEtResponceRequest
        while (result.indexOf(":MessageID") > 0) {
            String id = deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid = deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id=" + originalid);
            deps.gis.Ack(id);
            result = getrespreq();
        }
        System.out.println("******************************************");

    }

    String getrespreq() throws Exception {
        String prepared = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2014-02-11T17:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared);
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;


    }
}