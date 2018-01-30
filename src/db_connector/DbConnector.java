package db_connector;


import ORM.tables.Article;
import ORM.tables.Attachment;
import ORM.tables.FollowRelation;

import ORM.tables.User;
import ORM.tables.records.ArticleRecord;
import ORM.tables.records.AttachmentRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import utililties.Blog;
import utililties.Comments;
import utililties.Tuple;
import utililties.Tuple3;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static ORM.tables.Article.ARTICLE;
import static ORM.tables.Attachment.ATTACHMENT;
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
        return user.isEmpty() ? null : user.get(0);
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
        return user.isEmpty() ? null : user.get(0);
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


//    public static List<CommentRecord> getCommentsByParentCommentId(String commentId) {
//        List<CommentRecord> comments = new ArrayList<>();
//
//        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
//            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
//
//            comments = create.select()
//                    .from(COMMENT)
//                    .where(COMMENT.PARENT_COMMENT.equalIgnoreCase(commentId))
//                    .fetch()
//                    .into(CommentRecord.class);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return comments;
//    }

    //get articles sort by hot degree(like_num)
    public static List<ArticleRecord> getHotArticlesSort() {
        List<ArticleRecord> articles = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            articles = create.select()
                    .from(Article.ARTICLE)
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
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
     *
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
        return user.isEmpty() ? null : user.get(0);
    }

    /**
     * Get Article by its id
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
        return articles.isEmpty() ? null : articles.get(0);
    }

    /**
     * Get how many comments under an article by its id.
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
     */
    public static Blog getBlogByArticleId(String articleId) {
        Blog blog = new Blog();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            Map<Tuple<UserRecord, ArticleRecord>, List<CommentRecord>> result = create
                    .select(USER.fields())
                    .select(ARTICLE.fields())
                    .select(COMMENT.fields())
                    .from((USER).join(ARTICLE).onKey())
                    .leftJoin(COMMENT).on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .where(ARTICLE.ID.eq(Integer.parseInt(articleId)))
                    .orderBy(COMMENT.CREATE_TIME.asc())
                    .fetchGroups(
                            r -> new Tuple<>(r.into(USER).into(UserRecord.class), r.into(ARTICLE).into(ArticleRecord.class)),
                            r -> r.into(COMMENT).into(CommentRecord.class)
                    );

            result.forEach((t, c) -> {
                blog.setKey(t);
                blog.addValue(c);
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blog;
    }

    /**
     * Get a user's all articles with comments list on each article by userId
     */
    public static List<Blog> getBlogsByUserId(String userId) {
        List<Blog> blogs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            List<Tuple3<UserRecord, ArticleRecord, CommentRecord>> result = create
                    .select(USER.fields())
                    .select(ARTICLE.fields())
                    .select(COMMENT.fields())
                    .from((USER).join(ARTICLE).onKey())
                    .leftJoin(COMMENT).on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .where(USER.ID.eq(Integer.parseInt(userId)))
                    .orderBy(ARTICLE.CREATE_TIME.desc(),
                            COMMENT.CREATE_TIME.asc())
                    .fetch(
                            r -> new Tuple3<>(
                                    r.into(USER).into(UserRecord.class),
                                    r.into(ARTICLE).into(ArticleRecord.class),
                                    r.into(COMMENT).into(CommentRecord.class)
                            )
                    );

            addResultToBlogList(blogs, result);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    /**
     * Get an article's comments with commenter
     *
     * @param articleId
     * @return
     */
    public static Comments getCommentsByArticleId(String articleId) {
        Comments comments = new Comments();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            List<Tuple<UserRecord, CommentRecord>> commentList = create
                    .select(USER.fields())
                    .select(COMMENT.fields())
                    .from(COMMENT)
                    .join(USER).onKey()
                    .join(ARTICLE).on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .where(ARTICLE.ID.equalIgnoreCase(articleId))
                    .orderBy(COMMENT.CREATE_TIME.asc())
                    .fetch(
                            r -> new Tuple<>(
                                    r.into(USER).into(UserRecord.class),
                                    r.into(COMMENT).into(CommentRecord.class)
                            )
                    );

            comments.setCommentList(commentList);
            comments.convertListToTree();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }


    /**
     * Get articles sort by hot degree(like_num)
     **/
    public static List<Blog> getHotBlogsSort() {
        List<Blog> blogs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            List<Tuple3<UserRecord, ArticleRecord, CommentRecord>> result = create
                    .select(USER.fields())
                    .select(ARTICLE.fields())
                    .select(COMMENT.fields())
                    .from((USER).join(ARTICLE).onKey())
                    .leftJoin(COMMENT).on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .orderBy(ARTICLE.LIKE_NUM.desc(),
                            COMMENT.CREATE_TIME.asc())
                    .fetch(
                            r -> new Tuple3<>(
                                    r.into(USER).into(UserRecord.class),
                                    r.into(ARTICLE).into(ArticleRecord.class),
                                    r.into(COMMENT).into(CommentRecord.class)
                            )
                    );

            addResultToBlogList(blogs, result);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    /*Helper function*/
    private static void addResultToBlogList(List<Blog> blogs, List<Tuple3<UserRecord, ArticleRecord, CommentRecord>> result) {
        result.forEach(
                t -> {
                    Blog blog = new Blog();
                    blog.setKey(new Tuple<>(t.Val1, t.Val2));

                    // Because of leftJoin, don't add null comment to article with on comments
                    if (t.Val3.getId() != null) {
                        blog.getCommentList().add(t.Val3);
                    }

                    // find if result already contains the article
                    Blog thatBlog = blogs.stream()
                            .filter(b -> b.getArticle().getId().equals(blog.getArticle().getId()))
                            .findFirst().orElse(null);

                    // if there the blog with same Author/Article has been added to result,
                    if (thatBlog != null) {
                        // then, old article,  add comment to that blog
                        thatBlog.getCommentList().add(t.Val3);
                    } else {
                        // else, new article, just add blog into result
                        blogs.add(blog);
                    }
                }
        );
        blogs.forEach(Blog::convertListToTree);
    }


    /**
     * Get attachment by article id
     **/
    public static List<AttachmentRecord> getAttachmentByArticleId(String ownby,String attachType) {
        List<AttachmentRecord> attachments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            attachments = create
                    .select(ATTACHMENT.fields())
                    .from(ATTACHMENT)
                    .where(ATTACHMENT.OWNBY.eq(Integer.parseInt(ownby)))
                    .and(ATTACHMENT.ATTACH_TYPE.eq(attachType))
                    .fetch()
                    .into(AttachmentRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attachments;
    }
    /**
     * Insert a article to db
     *
     * @param article new Article to be inserted to db
     * @return success or not
     */
    public static boolean insertNewArticle(ArticleRecord article) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            create.insertInto(ARTICLE, ARTICLE.AUTHOR, ARTICLE.TITLE, ARTICLE.CONTENT, ARTICLE.CREATE_TIME, ARTICLE.VALID_TIME)
                    .values(article.getAuthor(), article.getTitle(), article.getContent(), article.getCreateTime(), article.getValidTime())
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     *
     * @param user
     * @return
     */
    public static boolean insertNewUser(UserRecord user) {

            try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
                DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

                             create.insertInto(USER, USER.USER_NAME,USER.PASSWORD,USER.EMAIL,USER.F_NAME,USER.L_NAME,USER.GENDER,USER.DOB)
                                 //.values(user.getUserName(),user.getPassword(),user.getEmail(),user.getFName(),user.getLName())
                                     .execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return true;

    }

    public static boolean saveAttachmentRecord(AttachmentRecord attachment) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            create.insertInto(ATTACHMENT)
                    .columns(ATTACHMENT.FILENAME,ATTACHMENT.PATH,ATTACHMENT.MIME,ATTACHMENT.ATTACH_TYPE,ATTACHMENT.OWNBY,ATTACHMENT.ISACTIVATE)
                    .values(attachment.getFilename(),attachment.getPath(),attachment.getMime(),attachment.getAttachType(),attachment.getOwnby(),attachment.getIsactivate()).execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
