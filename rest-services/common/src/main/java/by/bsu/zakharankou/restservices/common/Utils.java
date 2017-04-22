package by.bsu.zakharankou.restservices.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Collection;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Contains helper methods.
 */
public final class Utils {

    public static final String EMPTY = "";

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    public static final ObjectMapper MAPPER = new ObjectMapper();

    private Utils() {
    }

    public static boolean isBlank(String value) {
        return (value == null) || value.trim().isEmpty();
    }

    public static boolean isBlank(Collection value) {
        return (value == null) || value.isEmpty();
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = input.read(buffer, 0, buffer.length)) != -1) {
            output.write(buffer, 0, read);
        }

        output.flush();

        return output.toByteArray();
    }

    public static String calculateThumbrint(Certificate certificate) throws CertificateEncodingException {
        byte[] thumbprint = DigestUtils.sha(certificate.getEncoded());

        return Base64.encodeBase64String(thumbprint);
    }

}
