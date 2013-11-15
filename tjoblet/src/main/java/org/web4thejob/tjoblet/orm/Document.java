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
import org.web4thejob.orm.annotation.EntityHierarchyHolder;
import org.web4thejob.orm.annotation.HtmlHolder;

import javax.validation.constraints.NotNull;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class Document extends Content {
    @NotNull
    @EntityHierarchyHolder(hierarchyType = CategoryHierarchy.class)
    private Category category;
    @NotBlank
    @HtmlHolder
    private String body;

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

}
