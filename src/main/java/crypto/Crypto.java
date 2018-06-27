package crypto;


import chapter4.Utils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.encoders.Base64;

import javax.security.auth.x500.X500Principal;
import javax.xml.ws.Action;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.bouncycastle.asn1.x509.Extension.*;
import static org.bouncycastle.asn1.x509.KeyPurposeId.*;
import static org.bouncycastle.asn1.x509.KeyUsage.*;

public abstract class Crypto {
  String dnSuffix = "OU=Roland, O=Incorporated, C=CO.UK";
  JcaX509CertificateConverter jcaConverter = new JcaX509CertificateConverter();

  public abstract KeyPair generateKeyPair() throws GeneralSecurityException;

  public abstract ContentSigner getContentSigner(PrivateKey privateKey) throws OperatorCreationException;

  public X509Certificate issueSelfSignedCert(KeyPair keyPair, String cn, LocalDate expiresAt) throws GeneralSecurityException, IOException, OperatorCreationException {
    return issueCert(keyPair, null, keyPair.getPublic(), cn, BigInteger.ONE, expiresAt);
  }

  public X509Certificate issueCert(KeyPair issuer, X509Certificate issuerCert, PublicKey subject, String cn, BigInteger serial, LocalDate expiresAt) throws GeneralSecurityException, IOException, OperatorCreationException {
    String subjectDn = "CN=" + cn + ", " + dnSuffix;
    String issuerDn = issuerCert != null ? issuerCert.getSubjectDN().toString() : subjectDn;

    JcaX509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(new X500Principal(issuerDn),
        serial, new Date(), toDate(expiresAt), new X500Principal(subjectDn), subject);

    JcaX509ExtensionUtils x509Utils = new JcaX509ExtensionUtils();
    certGen.addExtension(basicConstraints, true, new BasicConstraints(true));
    certGen.addExtension(keyUsage, true, new KeyUsage(cRLSign | digitalSignature | keyCertSign));
    certGen.addExtension(extendedKeyUsage, true, new ExtendedKeyUsage(new KeyPurposeId[]{id_kp_OCSPSigning, id_kp_timeStamping, id_kp_codeSigning}));
    certGen.addExtension(subjectKeyIdentifier, false, x509Utils.createSubjectKeyIdentifier(subject));
    certGen.addExtension(authorityKeyIdentifier, false, x509Utils.createAuthorityKeyIdentifier(issuer.getPublic()));
    X509CertificateHolder holder = certGen.build(getContentSigner(issuer.getPrivate()));
    return jcaConverter.getCertificate(holder);
  }

  public byte[] sign(byte[] data, PrivateKey key) throws OperatorCreationException, IOException {
    ContentSigner signer = getContentSigner(key);
    signer.getOutputStream().write(data);
    System.out.println("Candy Charms >>>>>>>>>>>>>>>>>>>>>>"+signer.getSignature());
    return signer.getSignature();
  }

  public byte[] sign(String data, PrivateKey key) throws OperatorCreationException, IOException {
    ContentSigner signer = getContentSigner(key);
    signer.getOutputStream().write(data.getBytes());

    System.out.println("Candy Charms >>>>>>>>>>>>>>>>>>>>>>"+signer.getSignature());
    return signer.getSignature();
  }

  public CMSSignedData signCades(String data, PrivateKey privateKey, X509Certificate certificate) throws CertificateEncodingException, OperatorCreationException, CMSException {
    CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
    gen.addCertificate(new JcaX509CertificateHolder(certificate));
    gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build())
        .build(getContentSigner(privateKey), certificate));
    return gen.generate(new CMSProcessableByteArray(data.getBytes()), true);
  }

  private Date toDate(LocalDate date) {
    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public String toPEM(Object obj) throws IOException {
    StringWriter out = new StringWriter();
    try (PEMWriter pem = new PEMWriter(out)) {
      pem.writeObject(obj);
    }
    return out.toString();
  }

  @Action
  public byte[] signEnglish(byte[] input, PrivateKey key, PublicKey check) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Signature signature = Signature.getInstance("ECGOST3410", "BC");
    // generate a signature
    signature.initSign(key, Utils.createFixedRandom());
    Gost3411Hash hash =  new Gost3411Hash();
    byte[] data = hash.hash_byte(input);
    signature.update(data);

    byte[]  sigBytes = signature.sign();

    signature.initVerify(check);

    // set the parameters
    signature.update(data);

    if (signature.verify(sigBytes)) {
      System.out.println("signature verification succeeded.");

    }
    else
    {
      System.out.println("signature verification failed.");

    }
  return sigBytes;
  }





}
