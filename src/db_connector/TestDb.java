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

        DbConnector.getAllRecords().stream()
                .filter(r -> r.Val1 instanceof UserRecord)
                .map(r -> (UserRecord)r.Val1)
                .map(u -> new Tuple<>(u.getPassword(), u.getUserName()))
                .map(t -> new Tuple<>(
                        t.Val2, Login.hashingPassword(t.Val1, t.Val2)))
                .forEach(t -> System.out.println(t.Val1+'\t'+t.Val2));

        System.out.println();

        List<Tuple<?,?>> r = DbConnector.getAllRecords();
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).Val1 instanceof UserRecord){
                UserRecord u = (UserRecord) r.get(i).Val1;

                String password = u.getPassword();
                String username = u.getUserName();
                String hashedPassword = Login.hashingPassword(password, username);

                System.out.println(username + '\t' + hashedPassword);
            }
        }
    }
}
