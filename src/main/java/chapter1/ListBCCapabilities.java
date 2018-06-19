package chapter1;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

/**
 * List the available capabilities for ciphers, key agreement, macs, message
 * digests, signatures and other objects in the BC provider.
 */
public class ListBCCapabilities
{
    public static void main(

        String[]    args) throws IOException {
        Security.addProvider(new BouncyCastleProvider());
        Provider	provider = Security.getProvider("BC");
        
        Iterator  it = provider.keySet().iterator();
        FileWriter wr = new FileWriter("certs/list.txt");
        while (it.hasNext())
        {
            String	entry = (String)it.next();

            wr.write(entry+'\n');
            
            // this indicates the entry refers to another entry
            
            if (entry.startsWith("Alg.Alias."))
            {
                entry = entry.substring("Alg.Alias.".length());
            }
            
            String  factoryClass = entry.substring(0, entry.indexOf('.'));
            String  name = entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
        }
        wr.close();
    }
}
