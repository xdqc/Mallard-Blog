package db_connector;

import ORM.tables.records.CommentRecord;
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

        List<Blog> blogs = DbConnector.getBlogsByUserId("1");

        for (Blog blog : blogs) {

            System.out.println(blog.getAuthor());
            System.out.println(blog.getArticle());
            List<CommentRecord> list = new ArrayList<>();
            blog.getCommentTree().traverse(blog.getCommentTree(), list);
            list.forEach(System.out::println);
        }

    }
}
