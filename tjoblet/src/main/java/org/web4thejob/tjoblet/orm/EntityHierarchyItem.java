package org.web4thejob.tjoblet.orm;

import org.web4thejob.orm.Entity;

import java.util.Set;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public interface EntityHierarchyItem extends Entity {

    public <T extends EntityHierarchy> Set<T> getChildren();
}
