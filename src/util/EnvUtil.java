package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tiện ích đọc cấu hình từ file .env cho toàn bộ ứng dụng.
 */
public class EnvUtil {
    private static final Map<String, String> ENV_VARS = new ConcurrentHashMap<>();
    private static volatile boolean loaded = false;

    static {
        load();
    }

    public static synchronized void load() {
        ENV_VARS.clear();
        File envFile = findEnvFile();
        if (envFile != null && envFile.exists() && envFile.isFile()) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(envFile), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                        continue;
                    }
                    int eqIdx = line.indexOf('=');
                    if (eqIdx > 0) {
                        String key = line.substring(0, eqIdx).trim();
                        String val = line.substring(eqIdx + 1).trim();
                        // Loại bỏ dấu nháy kép hoặc đơn ở 2 đầu
                        if ((val.startsWith("\"") && val.endsWith("\"") && val.length() >= 2) ||
                            (val.startsWith("'") && val.endsWith("'") && val.length() >= 2)) {
                            val = val.substring(1, val.length() - 1);
                        }
                        ENV_VARS.put(key, val);
                    }
                }
                System.out.println("[EnvUtil] Da load thanh cong file .env tu: " + envFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("[EnvUtil] Loi khi doc file .env: " + e.getMessage());
            }
        } else {
            System.out.println("[EnvUtil] Khong tim thay file .env, su dung bien moi truong he thong / fallback.");
        }
        loaded = true;
    }

    private static File findEnvFile() {
        // 1. Kiểm tra biến JVM System Property hoặc System Env
        String customPath = System.getProperty("env.path");
        if (customPath == null || customPath.trim().isEmpty()) {
            customPath = System.getenv("ENV_PATH");
        }
        if (customPath != null && !customPath.trim().isEmpty()) {
            File f = new File(customPath.trim());
            if (f.exists()) return f;
        }

        // 2. Tìm dựa trên vị trí class file (CodeSource)
        try {
            URL url = EnvUtil.class.getProtectionDomain().getCodeSource().getLocation();
            if (url != null) {
                File loc = new File(url.toURI());
                // loc thường là .../WEB-INF/classes
                if (loc.isDirectory()) {
                    File parent = loc.getParentFile(); // WEB-INF
                    if (parent != null) {
                        File webappDir = parent.getParentFile(); // project
                        if (webappDir != null) {
                            File f = new File(webappDir, ".env");
                            if (f.exists()) return f;
                        }
                    }
                }
            }
        } catch (Exception ignored) {}

        // 3. Kiểm tra các đường dẫn tương đối thông dụng
        String[] candidatePaths = {
            ".env",
            "../.env",
            "webapps/project/.env",
            "../webapps/project/.env"
        };
        for (String p : candidatePaths) {
            File f = new File(p);
            if (f.exists()) return f;
        }

        // 4. Kiểm tra dựa trên CATALINA_BASE / CATALINA_HOME
        String catalinaBase = System.getProperty("catalina.base");
        if (catalinaBase != null && !catalinaBase.trim().isEmpty()) {
            File f = new File(catalinaBase, "webapps" + File.separator + "project" + File.separator + ".env");
            if (f.exists()) return f;
        }
        String catalinaHome = System.getProperty("catalina.home");
        if (catalinaHome != null && !catalinaHome.trim().isEmpty()) {
            File f = new File(catalinaHome, "webapps" + File.separator + "project" + File.separator + ".env");
            if (f.exists()) return f;
        }

        return null;
    }

    public static String get(String key) {
        if (!loaded) load();
        String val = ENV_VARS.get(key);
        if (val != null && !val.trim().isEmpty()) {
            return val;
        }
        val = System.getenv(key);
        if (val != null && !val.trim().isEmpty()) {
            return val;
        }
        val = System.getProperty(key);
        if (val != null && !val.trim().isEmpty()) {
            return val;
        }
        return null;
    }

    public static String get(String key, String defaultValue) {
        String val = get(key);
        return val != null ? val : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String val = get(key);
        if (val != null) {
            try {
                return Integer.parseInt(val.trim());
            } catch (NumberFormatException ignored) {}
        }
        return defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = get(key);
        if (val != null) {
            return Boolean.parseBoolean(val.trim());
        }
        return defaultValue;
    }
}
