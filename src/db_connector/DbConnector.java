package db_connector;


import ORM.tables.User;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
    public DbConnector() {
        initialze();
    }

    private static Properties dbProps = new Properties();

    private static void initialze() {
        try (FileInputStream fIn = new FileInputStream("conf/dev/mysql.properties")) {
            dbProps.load(fIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void fetchAllUserFromDb() {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            final ORM.tables.User TABLE = User.USER;
            Result<Record2<Integer, String>> result = create.select(TABLE.GENDER, TABLE.F_NAME)
                    .from(TABLE)
                    .fetch();

            for (Record2<Integer, String> record : result) {
                int id = record.component1();
                String username =record.component2();
                System.out.println(id + " " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
