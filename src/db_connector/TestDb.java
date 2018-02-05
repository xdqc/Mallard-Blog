package db_connector;

import ORM.tables.records.UserRecord;
import controller.Login;
import utililties.Tuple;

import java.util.List;

public class TestDb {
    public static void main(String[] args) {

        new TestDb().run();
    }

    private void run() {
        new DbConnector("conf/dev/mysql.properties");

        String[] strings = new String[]{"test", "first",};

        List<Tuple<?,?>> r = DbConnector.getAllRecords();
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).Val1 instanceof UserRecord){
                UserRecord u = (UserRecord) r.get(i).Val1;

                String password = u.getPassword();
                String username = u.getUserName();
                String hashedPassword = Login.hashingPassword(password, u);

                System.out.println(username + '\t' + hashedPassword);
//                DbConnector.resetPasswordByUserID(hashedPassword, String.valueOf(u.getId()));
            }
        }
    }
}
