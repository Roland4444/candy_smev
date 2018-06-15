package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InjectId {

    public String inject(String input){
        String result="";
        int pStart=input.indexOf("MessageID>");
        if (pStart==-1) return input;
        for (int i= 0; i<pStart+9; i++)
            result+=input.charAt(i);
        int offset=0;
        while (input.charAt(pStart+offset)!='<') offset++;
        result+=(new timeBasedUUID().generate());
        for (int i= pStart+offset; i<input.length()-1;i++)
            result+=input.charAt(i);
        return result;
    }

    public void inject(String input, String output) throws IOException {
        FileWriter wr = new FileWriter(output);
        BufferedReader b = new BufferedReader(new FileReader(input));
        String readLine = "";
        String res="";
        while ((readLine = b.readLine()) != null)
            wr.write(inject(readLine).replace("\n",""));
        wr.close();
    };

    public String getTagValue(String xml, String tagName){
        return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
    }

}
