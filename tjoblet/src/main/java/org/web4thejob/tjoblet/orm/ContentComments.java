package org.web4thejob.tjoblet.orm;

import org.hibernate.validator.constraints.NotBlank;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.AbstractHibernateEntity;
import org.web4thejob.orm.annotation.HtmlHolder;
import org.web4thejob.orm.annotation.InsertTimeHolder;
import org.web4thejob.orm.annotation.UpdateTimeHolder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ContentComments extends AbstractHibernateEntity {
    private long id;
    @NotNull
    private Content content;
    @NotBlank
    @HtmlHolder
    private String comments;
    @InsertTimeHolder
    private Date bookDate;
    @UpdateTimeHolder
    private Date updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    @Override
    public String toString() {
        if (content != null) {
            return ContextUtil.getMRS().deproxyEntity(content).toString();
        }
        return "Note: <Blank>";
    }

}
