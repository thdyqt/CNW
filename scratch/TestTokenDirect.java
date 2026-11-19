public class TestTokenDirect {
    public static void main(String[] args) {
        com.tour.dao.AccountDAO dao = new com.tour.dao.AccountDAO();
        com.tour.model.Account acc = dao.getAccountByToken("tok_adm_test");
        if (acc != null) {
            System.out.println("FOUND ACC: " + acc.getFullName() + ", status=" + acc.getStatus() + ", id=" + acc.getId());
        } else {
            System.out.println("ACC IS NULL");
        }
    }
}
