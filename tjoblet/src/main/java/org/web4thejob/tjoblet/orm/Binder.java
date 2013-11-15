package org.web4thejob.tjoblet.orm;

import org.web4thejob.orm.EntityHierarchyParent;

import java.util.LinkedHashSet;
import java.util.Set;

public class Binder extends Content implements EntityHierarchyParent<Binder, Content, ContentHierarchy> {

    private Set<ContentHierarchy> children = new LinkedHashSet<ContentHierarchy>();

    @Override
    public Set<ContentHierarchy> getChildren() {
        return children;
    }

    public void setChildren(Set<ContentHierarchy> children) {
        this.children = children;
    }

}
