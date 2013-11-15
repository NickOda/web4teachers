package org.web4thejob.tjoblet.orm;

import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.AbstractHibernateEntity;
import org.web4thejob.orm.EntityHierarchy;
import org.web4thejob.orm.Path;
import org.web4thejob.orm.annotation.EntityHierarchyHolder;
import org.web4thejob.orm.query.Condition;
import org.web4thejob.orm.query.Query;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ContentHierarchy extends AbstractHibernateEntity implements EntityHierarchy<Binder, Content> {
    private long id;
    @NotNull
    @EntityHierarchyHolder(hierarchyType = ContentHierarchy.class)
    private Binder parent;
    @NotNull
    @EntityHierarchyHolder(hierarchyType = ContentHierarchy.class, showChildren = true)
    private Content child;
    private long sorting;

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

    @Override
    public Binder getParent() {
        return parent;
    }

    @Override
    public void setParent(Binder parent) {
        this.parent = parent;
    }

    @Override
    public Content getChild() {
        return child;
    }

    @Override
    public void setChild(Content child) {
        this.child = child;
    }

    public long getSorting() {
        return sorting;
    }

    public void setSorting(long sorting) {
        this.sorting = sorting;
    }

    @Override
    public Query getRootItems() {
        Query query = ContextUtil.getEntityFactory().buildQuery(Binder.class);
        query.addCriterion(new Path("parents"), Condition.NEX, null);
        query.addOrderBy(new Path("name"));
        return query;
    }

    @Override
    public Class<Binder> getParentType() {
        return Binder.class;
    }

    @Override
    public Class<Content> getChildType() {
        return Content.class;
    }

    @Override
    public String toString() {
        return "Content Hierarchy: " + id;
    }
}
