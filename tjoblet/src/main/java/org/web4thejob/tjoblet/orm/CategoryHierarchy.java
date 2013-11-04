package org.web4thejob.tjoblet.orm;

import org.web4thejob.orm.AbstractHibernateEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class CategoryHierarchy extends AbstractHibernateEntity implements EntityHierarchy<Category> {
    private long id;
    @NotNull
    private Category parent;
    @NotNull
    private Category child;
    private long order;
    @SuppressWarnings("unused")
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Category getParent() {
        return parent;
    }

    @Override
    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public Category getChild() {
        return child;
    }

    @Override
    public void setChild(Category child) {
        this.child = child;
    }

    @Override
    public long getOrder() {
        return order;
    }

    @Override
    public void setOrder(long order) {
        this.order = order;
    }

    @Override
    public Serializable getIdentifierValue() {
        return id;
    }

    @Override
    public void setAsNew() {
        id = 0;
    }

}
