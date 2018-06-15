package util;

import java.io.*;

public class Injector {
    public String injectTag(String input, String TagwithclosedEmbrace, String valueToInject){
        String result="";
        int pStart=input.indexOf(TagwithclosedEmbrace);
        if (pStart==-1) return input;
        for (int i= 0; i<pStart+TagwithclosedEmbrace.length(); i++)
            result+=input.charAt(i);
        int offset=0;
        while (input.charAt(pStart+offset)!='<') offset++;
        result+=valueToInject;
        for (int i= pStart+offset; i<=input.length()-1;i++)
            result+=input.charAt(i);
        return result;
    }

    public void injectTagInFile(String input, String output, String TagwithclosedEmbrace, String valueToInject) throws IOException {
        FileWriter wr = new FileWriter(output);
        BufferedReader b = new BufferedReader(new FileReader(input));
        String readLine = "";
        String res="";
        while ((readLine = b.readLine()) != null)
            wr.write(injectTag(readLine, TagwithclosedEmbrace, valueToInject).replace("\n",""));
        wr.close();
    }
    @Deprecated
    public String inject(String input){
        String result="";
        int pStart=input.indexOf("MessageID>");
        if (pStart==-1) return input;
        for (int i= 0; i<pStart+9; i++)
            result+=input.charAt(i);
        int offset=0;
        while (input.charAt(pStart+offset)!='<') offset++;
        result+=(new timeBasedUUID().generate());
        for (int i= pStart+offset; i<=input.length()-1;i++)
            result+=input.charAt(i);
        return result;
    }
    @Deprecated
    public void inject(String input, String output) throws IOException {
        FileWriter wr = new FileWriter(output);
        BufferedReader b = new BufferedReader(new FileReader(input));
        String readLine = "";
        String res="";
        while ((readLine = b.readLine()) != null)
            wr.write(inject(readLine).replace("\n",""));
        wr.close();
    };
    @Deprecated
    public String getTagValue(String xml, String tagName){
        return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
    }

    public String cert(String certFile) throws IOException {
        String res="";
        File f = new File(certFile);
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        StringBuffer strBuffer = new StringBuffer();
        while ((readLine = b.readLine()) != null)
            if (readLine.indexOf("CERTIFICATE")<0) strBuffer.append(readLine);
        return strBuffer.toString();
    }

}
