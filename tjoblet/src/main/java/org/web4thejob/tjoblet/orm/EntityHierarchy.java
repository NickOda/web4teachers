package org.web4thejob.tjoblet.orm;

import org.web4thejob.orm.Entity;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public interface EntityHierarchy<T extends Entity> extends Entity {

    public T getParent();

    public void setParent(T parent);

    public T getChild();

    public void setChild(T child);

    public long getOrder();

    public void setOrder(long order);


}
