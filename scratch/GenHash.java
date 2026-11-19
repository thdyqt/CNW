public class GenHash {
    public static void main(String[] args) {
        System.out.println(org.mindrot.jbcrypt.BCrypt.hashpw("123456", org.mindrot.jbcrypt.BCrypt.gensalt(10)));
    }
}
