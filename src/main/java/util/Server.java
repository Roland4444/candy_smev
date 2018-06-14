package util;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Server {
    static String modelStateCache = "";
    static String imageBuffCache = "";
    static ArrayList imagesPool = new ArrayList();
    static ArrayList otherInfo = new ArrayList();
    static Map<String, Object> modelDependency = new HashMap<>();
    static ModelAndView view;
    static VelocityTemplateEngine template = new VelocityTemplateEngine();
    static Map<String, String> infMap = new HashMap();

    public static void main(String[] argv) {
        port(12000);
        staticFiles.location("/public");

        get("/", (req, res) -> {
                modelDependency.clear();
                view=new ModelAndView(modelDependency, "/templates/main.html");
            return template.render(view);
        });
        timeBasedUUID gen = new timeBasedUUID();
        get("/uuid", (req,res) -> {
            modelDependency.clear();
            modelDependency.put("UUID", gen.generate());
            view=new ModelAndView(modelDependency, "/templates/uuid.html");
            return template.render(view);
        });

        get("/soap", (req, res) -> {
            modelDependency.clear();
            String message = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">   <S:Body>      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>a3c69138-6bf1-11e8-a126-6d349ec8de0d</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>J3746ks34pOcPGQpKzc0sz3n9+gjPtzZbSEEs4c3sTwbtfdaY7N/hxXzEIvXc+3ad9bc35Y8yBhZ/BYbloGt+Q==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBcDCCAR2gAwIBAgIEHVmVKDAKBgYqhQMCAgMFADAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNJUzIxCzAJBgNVBAYTAlJVMB4XDTE1MDUwNzEyMTUzMFoXDTE4MDUwNjEyMTUzMFowLTEQMA4GA1UECxMHU1lTVEVNMTEMMAoGA1UEChMDSVMyMQswCQYDVQQGEwJSVTBjMBwGBiqFAwICEzASBgcqhQMCAiMBBgcqhQMCAh4BA0MABEDoWGZlTUWD43G1N7TEm14+QyXrJWProrzoDoCJRem169q4bezFOUODcNooQJNg3PtAizkWeFcX4b93u8fpVy7RoyEwHzAdBgNVHQ4EFgQUaRG++MAcPZvK/E2vR1BBl5G7s5EwCgYGKoUDAgIDBQADQQCg25vA3RJL3kgcJhVOHA86vnkMAtZYr6HBPa7LpEo0HJrbBF0ygKk50app1lzPdZ5TtK2itfmNgTYiuQHX3+nE</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>      </ns2:SendRequestRequest>   </S:Body></S:Envelope>";
            modelDependency.put("message", message);
            view=new ModelAndView(modelDependency, "/templates/soap.html");
            return template.render(view);
        });

        post("/soap", (req,res)->{
            String mx = req.queryParams("message");
            System.out.print(mx);
            FileWriter wr = new FileWriter("exch.xml");
            wr.write(mx);
            SAAJ saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
            saa.send("12.xml", "results.xml");
            return "";
        }
        );


        get("/soapg", (req,res)->{
                    return req.queryParams("message");
                }
        );


    }
}