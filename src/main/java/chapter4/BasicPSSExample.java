package chapter4;

import org.bouncycastle.jce.ECGOST3410NamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

public class BasicPSSExample
{
    public static void main(
        String[]    args)
        throws Exception
    {



        Security.addProvider(new BouncyCastleProvider());




        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        
        keyGen.initialize(512, new SecureRandom());

        KeyPair             keyPair = keyGen.generateKeyPair();
        Signature           signature = Signature.getInstance("SHA1withRSAandMGF1", "BC");

        // generate a signature
        signature.initSign(keyPair.getPrivate(), Utils.createFixedRandom());

        byte[] message = new byte[] { (byte)'q', (byte)'b', (byte)'c' };
    //    byte[] message = "abc".getBytes();
        System.out.print(message.length);
        signature.update(message);
        byte[]  sigBytes = signature.sign();
        // verify a signature
        signature.initVerify(keyPair.getPublic());
        // set the parameters
        signature.setParameter(new PSSParameterSpec("SHA-1", "MGF1", MGF1ParameterSpec.SHA1, 20, 1));
        signature.update("qbc".getBytes());
        if (signature.verify(sigBytes))
        {
            System.out.println("signature verification succeeded.");
        }
        else
        {
            System.out.println("signature verification failed.");
        }
    }
}