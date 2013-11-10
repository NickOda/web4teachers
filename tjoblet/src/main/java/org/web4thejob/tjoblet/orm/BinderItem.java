package org.web4thejob.tjoblet.orm;

import org.web4thejob.orm.AbstractHibernateEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BinderItem extends AbstractHibernateEntity {
    private long id;
    @NotNull
    private Binder binder;
    @NotNull
    private Document document;
    private long sorting;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public Serializable getIdentifierValue() {
        return id;
    }

    @Override
    public void setAsNew() {
        id = 0;
    }

    public long getSorting() {
        return sorting;
    }

    public void setSorting(long sorting) {
        this.sorting = sorting;
    }
}
