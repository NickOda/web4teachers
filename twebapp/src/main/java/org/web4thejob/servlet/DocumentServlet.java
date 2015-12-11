package org.web4thejob.servlet;

import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.Entity;
import org.web4thejob.tjoblet.orm.Document;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class DocumentServlet extends AbstractServlet {

    @Override
    protected String getDefaultTemplate() {
        return "plain_doc";
    }

    @Override
    protected String getTitle(Entity entity) {
        return "Εκφώνηση";
    }

    @Override
    protected String getBody(Entity entity) {
        return ((Document) entity).getBody();
    }

    protected String getSource(Entity entity) {
        return ((Document) entity).getSource();
    }

    @Override
    protected Entity getEntity(long docId) {
        return ContextUtil.getDRS().findById(Document.class, docId);
    }
}
