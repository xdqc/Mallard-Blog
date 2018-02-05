package db_connector;

import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import com.sun.org.apache.xpath.internal.SourceTree;
import controller.Login;
import org.jooq.Record;
import utililties.Blog;
import utililties.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
