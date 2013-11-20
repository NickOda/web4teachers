package org.web4thejob.servlet;

import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.Entity;
import org.web4thejob.tjoblet.orm.ContentNotes;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class NoteServlet extends AbstractServlet {

    @Override
    protected String getDefaultTemplate() {
        return "plain_note";
    }

    @Override
    protected String getTitle(Entity entity) {
        return ((ContentNotes) entity).getContent().getName();
    }

    @Override
    protected String getBody(Entity entity) {
        return ((ContentNotes) entity).getNotes();
    }

    @Override
    protected Entity getEntity(long docId) {
        return ContextUtil.getDRS().findById(ContentNotes.class, docId);
    }
}
