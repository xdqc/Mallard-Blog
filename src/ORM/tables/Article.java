/*
 * This file is generated by jOOQ.
*/
package ORM.tables;


import ORM.Indexes;
import ORM.Keys;
import ORM.MallardDev;
import ORM.tables.records.ArticleRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Article extends TableImpl<ArticleRecord> {

    private static final long serialVersionUID = -879846693;

    /**
     * The reference instance of <code>mallard_dev.article</code>
     */
    public static final Article ARTICLE = new Article();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ArticleRecord> getRecordType() {
        return ArticleRecord.class;
    }

    /**
     * The column <code>mallard_dev.article.id</code>.
     */
    public final TableField<ArticleRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mallard_dev.article.title</code>.
     */
    public final TableField<ArticleRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>mallard_dev.article.content</code>.
     */
    public final TableField<ArticleRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.VARCHAR(16384), this, "");

    /**
     * The column <code>mallard_dev.article.author</code>.
     */
    public final TableField<ArticleRecord, Integer> AUTHOR = createField("author", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mallard_dev.article.like_num</code>.
     */
    public final TableField<ArticleRecord, Integer> LIKE_NUM = createField("like_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mallard_dev.article.abuse_num</code>.
     */
    public final TableField<ArticleRecord, Integer> ABUSE_NUM = createField("abuse_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mallard_dev.article.create_time</code>.
     */
    public final TableField<ArticleRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mallard_dev.article.edit_time</code>.
     */
    public final TableField<ArticleRecord, Timestamp> EDIT_TIME = createField("edit_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mallard_dev.article.valid_time</code>.
     */
    public final TableField<ArticleRecord, Timestamp> VALID_TIME = createField("valid_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mallard_dev.article.show_hide_status</code>.
     */
    public final TableField<ArticleRecord, Byte> SHOW_HIDE_STATUS = createField("show_hide_status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * Create a <code>mallard_dev.article</code> table reference
     */
    public Article() {
        this(DSL.name("article"), null);
    }

    /**
     * Create an aliased <code>mallard_dev.article</code> table reference
     */
    public Article(String alias) {
        this(DSL.name(alias), ARTICLE);
    }

    /**
     * Create an aliased <code>mallard_dev.article</code> table reference
     */
    public Article(Name alias) {
        this(alias, ARTICLE);
    }

    private Article(Name alias, Table<ArticleRecord> aliased) {
        this(alias, aliased, null);
    }

    private Article(Name alias, Table<ArticleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MallardDev.MALLARD_DEV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ARTICLE_ARTICLE_USER_ID_FK, Indexes.ARTICLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ArticleRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ARTICLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ArticleRecord> getPrimaryKey() {
        return Keys.KEY_ARTICLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ArticleRecord>> getKeys() {
        return Arrays.<UniqueKey<ArticleRecord>>asList(Keys.KEY_ARTICLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ArticleRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ArticleRecord, ?>>asList(Keys.ARTICLE_USER_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article as(String alias) {
        return new Article(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article as(Name alias) {
        return new Article(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Article rename(String name) {
        return new Article(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Article rename(Name name) {
        return new Article(name, null);
    }
}
