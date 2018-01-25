package db_connector;


import ORM.tables.Article;
import ORM.tables.Comment;
import ORM.tables.FollowRelation;
import ORM.tables.User;
import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import jdk.management.resource.internal.inst.UnixAsynchronousSocketChannelImplRMHooks;
import org.jooq.*;
import org.jooq.impl.DSL;
import utililties.Blog;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ORM.tables.Article.ARTICLE;
import static ORM.tables.Comment.*;
import static ORM.tables.User.*;

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

            Result<Record2<Integer, String>> result = create.select(USER.GENDER, USER.F_NAME)
                    .from(USER)
                    .fetch();

            for (Record2<Integer, String> record : result) {
                int id = record.component1();
                String username = record.component2();
                System.out.println(id + " " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserRecord getAuthorByArticleId(String articleId) {
        List<UserRecord> user = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            user = create.select()
                    .from(USER)
                    .join(ARTICLE).onKey()
                    .where(ARTICLE.ID.equalIgnoreCase(articleId))
                    .fetch()
                    .into(UserRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.isEmpty()?null:user.get(0);
    }


    public static UserRecord getUserByUserId(String userId) {
        List<UserRecord> user = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            user = create.select()
                    .from(USER)
                    .where(USER.ID.equalIgnoreCase(userId))
                    .fetch()
                    .into(UserRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.isEmpty()?null:user.get(0);
    }

    public static List<ArticleRecord> getArticlesByUserId(String userId) {
        List<ArticleRecord> articles = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            articles = create.select()
                    .from(ARTICLE)
                    .join(USER).onKey()
                    .where(USER.ID.equalIgnoreCase(userId))
                    .orderBy(ARTICLE.CREATE_TIME.desc())
                    .fetch()
                    .into(ArticleRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static List<CommentRecord> getCommentsByArticleId(String articleId) {
        List<CommentRecord> comments = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            comments = create.select()
                    .from(COMMENT)
                    .join(ARTICLE)
                    .on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .where(ARTICLE.ID.equalIgnoreCase(articleId))
                    .orderBy(COMMENT.CREATE_TIME.desc())
                    .fetch()
                    .into(CommentRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static List<CommentRecord> getCommentsByParentCommentId(String commentId) {
        List<CommentRecord> comments = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            comments = create.select()
                    .from(COMMENT)
                    .where(COMMENT.PARENT_COMMENT.equalIgnoreCase(commentId))
                    .fetch()
                    .into(CommentRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    //get articles sort by hot degree(like_num)
    public static List<ArticleRecord> getHotArticlesSort() {
        List<ArticleRecord> articles = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            articles = create.select()
                    .from(ARTICLE)
                    .orderBy(ARTICLE.LIKE_NUM.desc())
                    .fetch()
                    .into(ArticleRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    //get follower number by userId
    public static int getFollowerNumber(String userId) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            result = create.selectCount()
                    .from(FollowRelation.FOLLOW_RELATION)
                    .where(FollowRelation.FOLLOW_RELATION.FOLLOWEE.eq(Integer.parseInt(userId)))
                    .fetchOne(0, int.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //get post number by userId
    public static int getPostNumber(String userId) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            result = create.selectCount()
                    .from(ARTICLE)
                    .where(ARTICLE.AUTHOR.eq(Integer.parseInt(userId)))
                    .fetchOne(0, int.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get a UserRecord obj by searching username, NULLABLE
     * @param username
     * @return
     */
    public static UserRecord getUserByUsername(String username) {
        List<UserRecord> user = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            user = create.select()
                    .from(USER)
                    .where(USER.USER_NAME.equalIgnoreCase(username))
                    .fetch()
                    .into(UserRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.isEmpty()?null:user.get(0);
    }

    /**
     * Get Article by its id
     * @param articleId
     * @return
     */
    public static ArticleRecord getArticleById(String articleId) {
        List<ArticleRecord> articles = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            articles = create.select()
                    .from(ARTICLE)
                    .where(ARTICLE.ID.equalIgnoreCase(articleId))
                    .fetch()
                    .into(ArticleRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles.isEmpty()?null:articles.get(0);
    }

    /**
     * Get how many comments under an article by its id.
     * @param articleId
     * @return
     */
    public static int getCommentNumberByArticle(String articleId) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            result = create.selectCount()
                    .from(COMMENT)
                    .where(COMMENT.PARENT_ARTICLE.eq(Integer.parseInt(articleId)))
                    .fetchOne(0, int.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get Article with Author and all Comments by articleId
     * @param articleId
     * @return
     */
    public static Blog getBlogByArticleId(String articleId) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            Result<Record> result = create
                    .select()
                    .from(USER)
                    .join(ARTICLE)
                    .on(ARTICLE.AUTHOR.eq(USER.ID))
                    .join(COMMENT)
                    .on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .where(ARTICLE.ID.eq(Integer.valueOf(articleId)))
                    .fetch();

            for (Record record : result) {
                System.out.println(record.formatJSON());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Blog(new ArticleRecord());
    }
}
