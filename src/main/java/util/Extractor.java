package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Extractor {
    public Extractor(){
    }

    public String extractTagValue(String input, String TagName){
        int pStart=input.indexOf(TagName);
        if (pStart==-1) return null;
        int offset=0;
        int addOffset=0;
        while (input.charAt(pStart+(offset++))!='>') {};
        while (input.charAt(pStart+offset+(addOffset++))!='<') {};
        addOffset--;
        return input.substring(pStart+offset, pStart+offset+addOffset);
    }

    public String parse(String FileName, String tagToFind) throws IOException {
        String result="";
        String input="";
        int pos=-2;
        int stopper = -2;
        File f = new File(FileName);
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        pos = input.indexOf(tagToFind);
        int startParse=pos;
        while (input.charAt(startParse)!='<')
            startParse--;
        stopper = input.indexOf(tagToFind, pos+tagToFind.length() );
        while (input.charAt(stopper)!='>')
            ++stopper;
        result = input.substring(startParse, ++stopper );
        return result;
    }

    public String extractRaw(String FileName, String tagToFind) throws IOException {
        String result="";
        String input="";
        int pos=-2;
        int stopper = -2;
        File f = new File(FileName);
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        pos = input.indexOf(tagToFind);
        int startParse=pos;
        while (input.charAt(startParse++)!='>'){}startParse++;
        stopper = input.indexOf(tagToFind, pos+tagToFind.length() );
        while (input.charAt(--stopper)!='<'){}startParse--;
        result = input.substring(startParse, stopper );
        return result;
    }
}
