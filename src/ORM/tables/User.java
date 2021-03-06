/*
 * This file is generated by jOOQ.
*/
package ORM.tables;


import ORM.Indexes;
import ORM.Keys;
import ORM.MallardDev;
import ORM.tables.records.UserRecord;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = -1773933459;

    /**
     * The reference instance of <code>mallard_dev.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>mallard_dev.user.id</code>.
     */
    public final TableField<UserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mallard_dev.user.user_name</code>.
     */
    public final TableField<UserRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>mallard_dev.user.password</code>.
     */
    public final TableField<UserRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "");

    /**
     * The column <code>mallard_dev.user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>mallard_dev.user.f_name</code>.
     */
    public final TableField<UserRecord, String> F_NAME = createField("f_name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * The column <code>mallard_dev.user.l_name</code>.
     */
    public final TableField<UserRecord, String> L_NAME = createField("l_name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * The column <code>mallard_dev.user.gender</code>.
     */
    public final TableField<UserRecord, Integer> GENDER = createField("gender", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>mallard_dev.user.dob</code>.
     */
    public final TableField<UserRecord, Date> DOB = createField("dob", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>mallard_dev.user.system_role</code>.
     */
    public final TableField<UserRecord, Integer> SYSTEM_ROLE = createField("system_role", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mallard_dev.user.create_time</code>.
     */
    public final TableField<UserRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mallard_dev.user.country</code>.
     */
    public final TableField<UserRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * The column <code>mallard_dev.user.state</code>.
     */
    public final TableField<UserRecord, String> STATE = createField("state", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * The column <code>mallard_dev.user.city</code>.
     */
    public final TableField<UserRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * The column <code>mallard_dev.user.address</code>.
     */
    public final TableField<UserRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>mallard_dev.user.description</code>.
     */
    public final TableField<UserRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR(512), this, "");

    /**
     * The column <code>mallard_dev.user.isValid</code>.
     */
    public final TableField<UserRecord, Byte> ISVALID = createField("isValid", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * Create a <code>mallard_dev.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>mallard_dev.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>mallard_dev.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.USER_PRIMARY, Indexes.USER_USER_EMAIL_UINDEX, Indexes.USER_USER_USER_NAME_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY, Keys.KEY_USER_USER_USER_NAME_UINDEX, Keys.KEY_USER_USER_EMAIL_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }
}
