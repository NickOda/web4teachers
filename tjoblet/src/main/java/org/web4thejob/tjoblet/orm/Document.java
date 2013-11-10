/*
 * Copyright (c) 2013 Veniamin Isaias
 *
 * This file is part of web4thejob-sandbox.
 *
 * Web4thejob-sandbox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Web4thejob-sandbox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with web4thejob-sandbox.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.web4thejob.tjoblet.orm;

import org.hibernate.validator.constraints.NotBlank;
import org.web4thejob.orm.AbstractHibernateEntity;
import org.web4thejob.orm.annotation.*;
import org.web4thejob.security.UserIdentity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class Document extends AbstractHibernateEntity {
    public static final String FLD_CODE = "name";
    public static final String FLD_BODY = "body";
    private long id;
    @SuppressWarnings("unused")
    private int version;
    @NotBlank
    private String name;
    @NotNull
    @EntityHierarchyHolder(hierarchyType = CategoryHierarchy.class)
    private Category category;
    @NotBlank
    @HtmlHolder
    private String body;
    @InsertTimeHolder
    private Date bookDate;
    @UpdateTimeHolder
    private Date updateDate;
    @UserIdHolder
    private UserIdentity owner;
    private Set<BinderItem> binderItems = new HashSet<BinderItem>();

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Set<BinderItem> getBinderItems() {
        return binderItems;
    }

    public void setBinderItems(Set<BinderItem> binderItems) {
        this.binderItems = binderItems;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserIdentity getOwner() {
        return owner;
    }

    public void setOwner(UserIdentity owner) {
        this.owner = owner;
    }


}
