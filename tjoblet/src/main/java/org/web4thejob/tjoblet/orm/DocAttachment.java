package org.web4thejob.tjoblet.orm;

import org.hibernate.validator.constraints.NotBlank;
import org.web4thejob.orm.AbstractHibernateEntity;
import org.web4thejob.orm.annotation.ImageHolder;
import org.web4thejob.orm.annotation.MediaHolder;
import org.web4thejob.orm.annotation.UrlHolder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class DocAttachment extends AbstractHibernateEntity {
    private long id;
    @NotBlank
    private String title;
    @NotNull
    private Document document;
    @UrlHolder
    private String url;

    @MediaHolder
    @ImageHolder
    @NotNull
    private byte[] attachment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Serializable getIdentifierValue() {
        return id;
    }

    @Override
    public void setAsNew() {
        id = 0;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }


    public String getUrl() {
        return "raw?id=" + id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
