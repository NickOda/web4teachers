package org.web4thejob.tjoblet.orm;

import org.hibernate.validator.constraints.NotBlank;
import org.web4thejob.orm.AbstractHibernateEntity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class Category extends AbstractHibernateEntity {
    private long id;
    @NotBlank
    private String name;
    @SuppressWarnings("unused")
    private int version;
    private Set<Document> documents = new HashSet<Document>();
    private Set<Category> parents = new HashSet<Category>();
    private Set<Category> children = new HashSet<Category>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
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
    public String toString() {
        return name;
    }

    public Set<Category> getParents() {
        return parents;
    }

    public void setParents(Set<Category> parents) {
        this.parents = parents;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

}
