package org.web4thejob.servlet;

import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.Entity;
import org.web4thejob.tjoblet.orm.ContentComments;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class CommentServlet extends AbstractServlet {

    @Override
    protected String getDefaultTemplate() {
        return "plain_comment";
    }

    @Override
    protected String getTitle(Entity entity) {
        return ((ContentComments) entity).getContent().getName();
    }

    @Override
    protected String getBody(Entity entity) {
        return ((ContentComments) entity).getComments();
    }

    @Override
    protected Entity getEntity(long docId) {
        return ContextUtil.getDRS().findById(ContentComments.class, docId);
    }
}
