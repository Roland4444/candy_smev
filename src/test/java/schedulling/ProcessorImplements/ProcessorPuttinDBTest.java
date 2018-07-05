package schedulling.ProcessorImplements;

import DB.Executor;
import crypto.Gost3411Hash;
import org.junit.Test;
import readfile.Readfile;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProcessorPuttinDBTest {

    @Test
    public void run() throws SQLException, IOException {
        Readfile r = new Readfile("sqlset");
        ProcessorPuttinDB putin = new ProcessorPuttinDB(new Executor(r.read(), true));
        PreparedStatement pst = putin.executor.getConn().prepareStatement("INSERT INTO lab.gis_files(?,?,?);");
        FileWriter wr = new FileWriter("xml4test/extract.xml");
        ResultSet Select2 = putin.executor.submit("set concat_null_yields_null off; SELECT f_body_xml FROM gis_files WHERE f_stat='0';");
        if (Select2.next()){
            String res =Select2.getString("f_body_xml");
            System.out.print(res);
            wr.write(res);
        }
        wr.close();
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        pst.setObject(1, data);
        pst.setObject(2, "0");
        pst.setObject(3, "000");
       // pst.execute();
    }
}