package org.web4thejob.tjoblet.orm;

import org.hibernate.validator.constraints.NotBlank;
import org.web4thejob.orm.AbstractHibernateEntity;
import org.web4thejob.orm.EntityHierarchyItem;
import org.web4thejob.orm.annotation.InsertTimeHolder;
import org.web4thejob.orm.annotation.UpdateTimeHolder;
import org.web4thejob.orm.annotation.UserIdHolder;
import org.web4thejob.security.UserIdentity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Content extends AbstractHibernateEntity implements EntityHierarchyItem {
    private long id;
    @NotBlank
    private String name;
    @SuppressWarnings("unused")
    private int version;
    @InsertTimeHolder
    private Date bookDate;
    @UpdateTimeHolder
    private Date updateDate;
    @UserIdHolder
    private UserIdentity owner;

    private ContentNotes notes;
    private ContentComments comments;
    private Set<ContentHierarchy> parents = new HashSet<ContentHierarchy>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Serializable getIdentifierValue() {
        return id;
    }

    @Override
    public void setAsNew() {
        id = 0;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserIdentity getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setOwner(UserIdentity owner) {
        this.owner = owner;
    }

    public Set<ContentHierarchy> getParents() {
        return parents;
    }

    public void setParents(Set<ContentHierarchy> parents) {
        this.parents = parents;
    }

    public ContentNotes getNotes() {
        return notes;
    }

    public void setNotes(ContentNotes notes) {
        this.notes = notes;
    }

    public ContentComments getComments() {
        return comments;
    }

    public void setComments(ContentComments comments) {
        this.comments = comments;
    }


}
