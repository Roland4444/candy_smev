package chapter1;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Basic class to confirm the Bouncy Castle provider is 
 * installed.
 */
public class SimpleProviderTest
{
    public static void main(String[] args)
    {
        String providerName = "BC";
        Security.addProvider(new BouncyCastleProvider());
        if (Security.getProvider(providerName) == null)
        {
            System.out.println(providerName + " provider not installed");
        }
        else
        {
            System.out.println(providerName + " is installed.");
        }
    }
}
