/*
 * This file is generated by jOOQ.
*/
package ORM.tables.records;


import ORM.tables.Attachment;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


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
public class AttachmentRecord extends UpdatableRecordImpl<AttachmentRecord> implements Record6<Integer, String, String, String, String, Integer> {

    private static final long serialVersionUID = 1877041716;

    /**
     * Setter for <code>mallard_dev.attachment.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mallard_dev.attachment.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mallard_dev.attachment.filename</code>.
     */
    public void setFilename(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mallard_dev.attachment.filename</code>.
     */
    public String getFilename() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mallard_dev.attachment.path</code>.
     */
    public void setPath(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mallard_dev.attachment.path</code>.
     */
    public String getPath() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mallard_dev.attachment.mime</code>.
     */
    public void setMime(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mallard_dev.attachment.mime</code>.
     */
    public String getMime() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mallard_dev.attachment.attach_type</code>.
     */
    public void setAttachType(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mallard_dev.attachment.attach_type</code>.
     */
    public String getAttachType() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mallard_dev.attachment.ownby</code>.
     */
    public void setOwnby(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mallard_dev.attachment.ownby</code>.
     */
    public Integer getOwnby() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Attachment.ATTACHMENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Attachment.ATTACHMENT.FILENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Attachment.ATTACHMENT.PATH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Attachment.ATTACHMENT.MIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Attachment.ATTACHMENT.ATTACH_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Attachment.ATTACHMENT.OWNBY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getAttachType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getOwnby();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAttachType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getOwnby();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord value2(String value) {
        setFilename(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord value3(String value) {
        setPath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord value4(String value) {
        setMime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord value5(String value) {
        setAttachType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord value6(Integer value) {
        setOwnby(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttachmentRecord values(Integer value1, String value2, String value3, String value4, String value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AttachmentRecord
     */
    public AttachmentRecord() {
        super(Attachment.ATTACHMENT);
    }

    /**
     * Create a detached, initialised AttachmentRecord
     */
    public AttachmentRecord(Integer id, String filename, String path, String mime, String attachType, Integer ownby) {
        super(Attachment.ATTACHMENT);

        set(0, id);
        set(1, filename);
        set(2, path);
        set(3, mime);
        set(4, attachType);
        set(5, ownby);
    }
}
