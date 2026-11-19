public class TestPw2 {
    public static void main(String[] args) {
        String hash = "$2a$10$x7VbPUuF3ZBxj1zn5NrNaOdeCC2Y5QL3bagUjVsmckEpFCZL9ybQ6";
        String[] list = {"123", "123456", "12345678", "admin", "ptdntn2907", "12345", "1234", "nhhieu"};
        for (String s : list) {
            if (org.mindrot.jbcrypt.BCrypt.checkpw(s, hash)) {
                System.out.println("FOUND: " + s);
                return;
            }
        }
        System.out.println("NOT FOUND");
    }
}
