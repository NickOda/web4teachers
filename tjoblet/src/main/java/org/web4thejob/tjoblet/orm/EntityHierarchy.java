package org.web4thejob.tjoblet.orm;

import org.web4thejob.orm.Entity;
import org.web4thejob.orm.query.Query;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public interface EntityHierarchy<T extends EntityHierarchyItem> extends Entity {

    public T getParent();

    public void setParent(T parent);

    public T getChild();

    public void setChild(T child);

    public long getSorting();

    public void setSorting(long order);

    public Query getRootItems();

    public Class<T> getItemsType();


}
