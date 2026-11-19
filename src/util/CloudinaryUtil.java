package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.servlet.http.Part;

public class CloudinaryUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String getCloudName() {
        return EnvUtil.get("CLOUDINARY_NAME", "r5hwa2cj");
    }

    private static String getApiKey() {
        return EnvUtil.get("CLOUDINARY_API_KEY", "585143441282468");
    }

    private static String getApiSecret() {
        return EnvUtil.get("CLOUDINARY_API_SECRET", "iOIjfo3rpO1zM14FXMoBdJKft6M");
    }

    public static String upload(Part filePart) {
        if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName() == null || filePart.getSubmittedFileName().trim().isEmpty()) {
            return null;
        }
        try (InputStream is = filePart.getInputStream()) {
            byte[] fileBytes = is.readAllBytes();
            String fileName = filePart.getSubmittedFileName();
            String contentType = filePart.getContentType();
            if (contentType == null || contentType.isEmpty()) contentType = "image/jpeg";
            return uploadBytes(fileBytes, fileName, contentType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String uploadBytes(byte[] fileBytes, String fileName, String contentType) {
        if (fileBytes == null || fileBytes.length == 0) return null;
        try {
            String cloudName = getCloudName();
            String apiKey = getApiKey();
            String apiSecret = getApiSecret();

            long timestamp = System.currentTimeMillis() / 1000L;
            String toSign = "timestamp=" + timestamp + apiSecret;
            String signature = sha1(toSign);

            String boundary = "----CloudinaryBoundary" + System.currentTimeMillis();
            URL url = new URL("https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(30000);

            try (OutputStream os = conn.getOutputStream()) {
                // api_key
                writeField(os, boundary, "api_key", apiKey);
                // timestamp
                writeField(os, boundary, "timestamp", String.valueOf(timestamp));
                // signature
                writeField(os, boundary, "signature", signature);

                // file
                os.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
                os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n").getBytes(StandardCharsets.UTF_8));
                os.write(("Content-Type: " + contentType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                os.write(fileBytes);
                os.write("\r\n".getBytes(StandardCharsets.UTF_8));

                // end
                os.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
            JsonNode root = objectMapper.readTree(is);
            if (root.has("secure_url")) {
                return root.get("secure_url").asText();
            } else {
                System.err.println("[CloudinaryUtil] Upload failed response: " + root);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeField(OutputStream os, String boundary, String name, String value) throws Exception {
        os.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        os.write(("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        os.write(value.getBytes(StandardCharsets.UTF_8));
        os.write("\r\n".getBytes(StandardCharsets.UTF_8));
    }

    private static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
