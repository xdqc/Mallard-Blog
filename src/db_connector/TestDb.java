package db_connector;

import ORM.tables.records.CommentRecord;
import org.jooq.Record;
import utililties.Blog;

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
        List<Record> res = DbConnector.findSearchItems(strings);

        res.forEach(System.out::println);
    }
}
