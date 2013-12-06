package org.web4thejob.servlet;

import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.Entity;
import org.web4thejob.tjoblet.orm.Content;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class BinderServlet extends AbstractHierarchyServlet {
    @Override
    protected Entity getEntity(long docId) {
        return ContextUtil.getDRS().findById(Content.class, docId);
    }
}
