public class TestPw {
    public static void main(String[] args) {
        String hash = "$2a$10$L.lunX/U7GYRqq0IxM2XJe4EGq0vrN9mieATbSmnWOAeAK7yDbaHC";
        String[] list = {"123", "123456", "12345678", "admin", "ptdntn2907", "12345", "1234"};
        for (String s : list) {
            if (org.mindrot.jbcrypt.BCrypt.checkpw(s, hash)) {
                System.out.println("FOUND: " + s);
                return;
            }
        }
        System.out.println("NOT FOUND");
    }
}
