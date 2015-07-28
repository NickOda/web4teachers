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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class Document extends Content {
    @NotNull
    @EntityHierarchyHolder(hierarchyType = CategoryHierarchy.class)
    private Category category;
    private Date referenceDate;
    private DocumentType type;
    @NotBlank
    @HtmlHolder
    private String body;
    private Set<DocAttachment> attachments = new HashSet<>();

    public Document() {
        setAsNew();
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
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

    public Set<DocAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<DocAttachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public void setAsNew() {
        super.setAsNew();
        body = "<p>Fill in the document's body by clicking on the <img src=\"img/CMD_UPDATE.png\"></img> toolbar icon" +
                ".</p>";
    }
}
