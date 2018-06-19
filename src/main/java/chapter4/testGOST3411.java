package chapter4;

import org.bouncycastle.jce.ECGOST3410NamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

public class testGOST3411
{
    public static void main(
            String[]    args)
            throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("ECGOST3410"); // this is called GOST3410EL in CryptoPro JCP
        keyGenerator.initialize(ECGOST3410NamedCurveTable.getParameterSpec("GostR3410-2001-CryptoPro-A"));

        KeyPair             keyPair = keyGenerator.generateKeyPair();
        Signature           signature = Signature.getInstance("ECGOST3410", "BC");
        System.out.print(keyPair.getPrivate().toString());
        // generate a signature
        signature.initSign(keyPair.getPrivate(), Utils.createFixedRandom());

        byte[] message = new byte[] { (byte)'a', (byte)'b', (byte)'c' };
        signature.update(message);

        byte[]  sigBytes = signature.sign();

        // verify a signature
        signature.initVerify(keyPair.getPublic());

        // set the parameters
        signature.update(message);

        if (signature.verify(sigBytes)) {
            System.out.println("signature verification succeeded.");
        }
        else
        {
            System.out.println("signature verification failed.");
        }
    }
}