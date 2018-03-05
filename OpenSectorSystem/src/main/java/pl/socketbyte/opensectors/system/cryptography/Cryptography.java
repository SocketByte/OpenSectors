package pl.socketbyte.opensectors.system.cryptography;

import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cryptography {

    public static String sha256(String base) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // OpenSectorSystem won't allow unencrypted passwords to go through.
            StackTraceHandler.handle(Cryptography.class, e, StackTraceSeverity.FATAL);
            return null;
        }
        byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
