package db_connector;

public class TestDb {
    public static void main(String[] args) {

        new TestDb().run();
    }

    private void run() {
        DbConnector dbConnector = new DbConnector();
        DbConnector.fetchAllUserFromDb();
    }
}
