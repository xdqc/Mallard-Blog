package db_connector;


import ORM.tables.Article;
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
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static ORM.tables.Article.ARTICLE;
import static ORM.tables.Attachment.ATTACHMENT;
import static ORM.tables.Comment.*;
import static ORM.tables.User.*;
import static org.jooq.impl.DSL.select;

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
     * For ajax load next article of user
     */
    public static Blog getNextBlogsByUserId(int loadedNum, String loadAuthorId) {
        List<Blog> blogs = getBlogsByUserId(loadAuthorId);
        if (blogs.size()<= loadedNum){
            return null;
        } else {
            return blogs.subList(loadedNum, blogs.size()).get(0);
        }
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

            List<Tuple3<UserRecord, CommentRecord, UserRecord>> commentList = create
                    .select(USER.as("Commenter").fields())
                    .select(COMMENT.fields())
                    .select(USER.as("Author").fields())
                    .from(
                            (COMMENT).join(USER.as("Commenter")).onKey())
                    .join(
                            (ARTICLE).join(USER.as("Author")).onKey())
                    .on(COMMENT.PARENT_ARTICLE.eq(ARTICLE.ID))
                    .where(ARTICLE.ID.equalIgnoreCase(articleId))
                    .orderBy(COMMENT.CREATE_TIME.asc())
                    .fetch(
                            r -> new Tuple3<>(
                                    r.into(USER.as("Commenter")).into(UserRecord.class),
                                    r.into(COMMENT).into(CommentRecord.class),
                                    r.into(USER.as("Author")).into(UserRecord.class)
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
                    .where(USER.ISVALID.eq((byte)1))
                    .and(ARTICLE.SHOW_HIDE_STATUS.eq((byte)1))
                    .and(ARTICLE.VALID_TIME.lt(new Timestamp(System.currentTimeMillis())))
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

    /**
     * For ajax load next hot article on homepage
     * @param loadedNum
     * @return
     */
    public static Blog getNextHotBlog(int loadedNum) {
        List<Blog> blogs = getHotBlogsSort();
        if (blogs.size()<= loadedNum){
            return null;
        } else {
            return blogs.subList(loadedNum, blogs.size()).get(0);
        }
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
    public static List<AttachmentRecord> getAttachmentByArticleId(String ownby, String attachType) {
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
     * Edit article
     *
     * @param article existing article
     * @return success or not
     */
    public static boolean updateExistingArticle(ArticleRecord article) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            create.update(ARTICLE)
                    .set(ARTICLE.TITLE, article.getTitle())
                    .set(ARTICLE.CONTENT, article.getContent())
                    .set(ARTICLE.EDIT_TIME, article.getEditTime())
                    .set(ARTICLE.VALID_TIME, article.getValidTime())
                    .where(ARTICLE.ID.eq(article.getId()))
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * Mark an article's show_hide as 0
     *
     * @param articleId article to be delete
     */
    public static void deleteArticleById(String articleId) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            create.update(ARTICLE)
                    .set(ARTICLE.SHOW_HIDE_STATUS, (byte) 0)
                    .where(ARTICLE.ID.eq(Integer.parseInt(articleId)))
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mark a comment's show_hide as 0
     *
     * @param commentId
     */
    public static void deleteCommentById(String commentId) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            create.update(COMMENT)
                    .set(COMMENT.SHOW_HIDE_STATUS, (byte) 0)
                    .where(COMMENT.ID.eq(Integer.parseInt(commentId)))
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new comment
     *
     * @param comment new comment to be inserted
     */
    public static void insertNewComment(CommentRecord comment) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            create.insertInto(COMMENT, COMMENT.COMMENTER, COMMENT.CONTENT,
                    COMMENT.CREATE_TIME, COMMENT.PARENT_ARTICLE, COMMENT.PARENT_COMMENT)
                    .values(comment.getCommenter(), comment.getContent(),
                            comment.getCreateTime(), comment.getParentArticle(), comment.getParentComment())
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit comment
     *
     * @param comment comment to be edit
     */
    public static void updateExistingComment(CommentRecord comment) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            create.update(COMMENT)
                    .set(COMMENT.CONTENT, comment.getContent())
                    .set(COMMENT.EDIT_TIME, comment.getEditTime())
                    .where(COMMENT.ID.eq(comment.getId()))
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param user
     * @return
     */

    public static boolean insertNewUser(UserRecord user) {

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);


                create.insertInto(USER, USER.USER_NAME,USER.PASSWORD,USER.EMAIL,USER.F_NAME,USER.L_NAME,USER.GENDER,USER.DOB,USER.SYSTEM_ROLE,USER.CREATE_TIME,USER.COUNTRY,USER.STATE,USER.CITY,USER.ADDRESS,USER.DESCRIPTION,USER.ISVALID)
                                    .values(user.getUserName(),user.getPassword(),user.getEmail(),user.getFName(),user.getLName(),user.getGender(),user.getDob(),user.getSystemRole(),user.getCreateTime(),user.getCountry(),user.getState(),user.getCity(),user.getAddress(),user.getDescription(),user.getIsvalid())
                                      .execute();

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        return true;

    }

    public static boolean saveAttachmentRecord(AttachmentRecord attachment) {
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            create.insertInto(ATTACHMENT)
                    .columns(ATTACHMENT.FILENAME, ATTACHMENT.PATH, ATTACHMENT.MIME, ATTACHMENT.ATTACH_TYPE, ATTACHMENT.OWNBY, ATTACHMENT.ISACTIVATE)
                    .values(attachment.getFilename(), attachment.getPath(), attachment.getMime(), attachment.getAttachType(), attachment.getOwnby(), attachment.getIsactivate()).execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Find records match search items
     */
    public static List<Tuple<?,?>> findSearchItems(String[] searchItems) {
        List<Tuple<?,?>> records = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            records.addAll(Arrays.stream(searchItems)
                    .map(s -> create
                            .select(USER.fields())
                            .select(USER.fields())
                            .from(USER)
                            .where(USER.USER_NAME.containsIgnoreCase(s))
                            .fetch(r -> new Tuple<>(
                                    r.into(USER).into(UserRecord.class),
                                    r.into(USER).into(UserRecord.class))))
                    .flatMap(List::stream)
                    .collect(Collectors.toList()));

            records.addAll(Arrays.stream(searchItems)
                    .map(s -> create
                            .select(ARTICLE.fields())
                            .select(USER.fields())
                            .from(ARTICLE)
                            .join(USER).onKey()
                            .where(ARTICLE.TITLE.containsIgnoreCase(s))
                            .fetch(r -> new Tuple<>(
                                    r.into(ARTICLE).into(ArticleRecord.class),
                                    r.into(USER).into(UserRecord.class))))
                    .flatMap(List::stream)
                    .collect(Collectors.toList()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }



}
