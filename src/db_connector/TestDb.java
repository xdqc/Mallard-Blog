package db_connector;

public class TestDb {
    public static void main(String[] args) {

        new TestDb().run();
    }

    private void run() {
        new DbConnector("conf/dev/mysql.properties");
        DbConnector.getBlogByArticleId("1");
    }
}
