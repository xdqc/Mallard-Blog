package db_connector;


import ORM.tables.Article;
import ORM.tables.User;
import ORM.tables.records.ArticleRecord;
import ORM.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbConnector {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DbConnector(String path) {
        try (FileInputStream fIn = new FileInputStream(path)) {
            dbProps.load(fIn);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Properties dbProps = new Properties();


    static void fetchAllUserFromDb() {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            Result<Record2<Integer, String>> result = create.select(User.USER.GENDER, User.USER.F_NAME)
                    .from(User.USER)
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

    public static UserRecord getAuthorByArticleId(String articleId) {
        UserRecord user = null;

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            List<UserRecord> result = create.select()
                    .from(User.USER)
                    .join(Article.ARTICLE).onKey()
                    .where(Article.ARTICLE.ID.equalIgnoreCase(articleId))
                    .fetch()
                    .into(UserRecord.class);

            user = result.size() > 0 ? result.get(0) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public static UserRecord getUserByUserId(String userId) {
        UserRecord user = null;
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            List<UserRecord> result = create.select()
                    .from(User.USER)
                    .where(User.USER.ID.equalIgnoreCase(userId))
                    .fetch()
                    .into(UserRecord.class);

            user = result.size() > 0 ? result.get(0) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<ArticleRecord> getArticlesByUserId(String userId) {
        List<ArticleRecord> articles = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            articles = create.select()
                    .from(Article.ARTICLE)
                    .join(User.USER).onKey()
                    .where(User.USER.ID.equalIgnoreCase(userId))
                    .fetch()
                    .into(ArticleRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
