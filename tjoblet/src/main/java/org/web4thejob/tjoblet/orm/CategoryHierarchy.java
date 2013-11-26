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

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class CategoryHierarchy extends AbstractHibernateEntity implements EntityHierarchy<Category, Category> {
    private long id;
    @NotNull
    @EntityHierarchyHolder(hierarchyType = CategoryHierarchy.class)
    private Category parent;
    @NotNull
    @EntityHierarchyHolder(hierarchyType = CategoryHierarchy.class)
    private Category child;
    private long sorting;
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
    public long getSorting() {
        return sorting;
    }

    @Override
    public void setSorting(long order) {
        this.sorting = order;
    }

    @Override
    public Query getRootItems() {
        Query query = ContextUtil.getEntityFactory().buildQuery(Category.class);
        query.addCriterion(new Path("parents"), Condition.NEX, null);
        query.addOrderBy(new Path("name"));
        return query;
    }

    @Override
    public Class<Category> getParentType() {
        return Category.class;
    }

    @Override
    public String getParentPropertyName() {
        return "parent";
    }

    @Override
    public String getChildPropertyName() {
        return "child";
    }

    @Override
    public Class<Category> getChildType() {
        return Category.class;
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
