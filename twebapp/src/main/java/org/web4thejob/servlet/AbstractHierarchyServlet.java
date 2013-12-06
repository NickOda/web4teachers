package org.web4thejob.servlet;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.util.StringUtils;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.Entity;
import org.web4thejob.orm.EntityHierarchy;
import org.web4thejob.orm.EntityHierarchyItem;
import org.web4thejob.orm.EntityHierarchyParent;
import org.web4thejob.tjoblet.orm.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public abstract class AbstractHierarchyServlet extends AbstractServlet {
    @Override
    protected String getDefaultTemplate() {
        return "plain_doc";
    }

    @Override
    protected String getTitle(Entity entity) {
        return entity.toString();
    }

    @Override
    protected String getBody(Entity entity) {

        final EntityHierarchyItem ehi = (EntityHierarchyItem) entity;
        if (ehi instanceof EntityHierarchyParent) {

            return ContextUtil.getTransactionWrapper().execute(new TransactionCallback<String>() {
                @Override
                public String doInTransaction(TransactionStatus status) {
                    StringBuffer body = new StringBuffer();

                    List<Integer> level = new ArrayList<>();
                    body.append(traverseChildren((EntityHierarchyParent) ehi, level));

                    return body.toString();
                }
            });


        } else {
            return ((Document) ehi).getBody();
        }

    }

    protected StringBuffer traverseChildren(EntityHierarchyParent parent, List<Integer> level) {
        StringBuffer body = new StringBuffer();

        parent = (EntityHierarchyParent) ContextUtil.getDRS().get(parent.getEntityType(), parent.getIdentifierValue());

        level.add(0);
        int count = 0;
        for (Object child : parent.getChildren()) {
            level.set(level.size() - 1, ++count);

            EntityHierarchy hierarchy = (EntityHierarchy) child;
            body.append(getTitle(hierarchy.getChild().toString(), level));
            if (hierarchy.getChild() instanceof EntityHierarchyParent) {
                body.append(traverseChildren((EntityHierarchyParent) hierarchy.getChild(), new ArrayList<>(level)));
            } else if (hierarchy.getChild() instanceof Document) {
                body.append(((Document) hierarchy.getChild()).getBody());
            }
        }

        return body;
    }

    protected String getTitle(String title, List<Integer> level) {
        StringBuffer sb = new StringBuffer();
        int depth = level.size() + 1;
        sb.append("<h");
        sb.append(depth);
        sb.append(">");

        sb.append(StringUtils.collectionToDelimitedString(level, "."));
        sb.append(".&nbsp;&nbsp;");
        sb.append(title);

        sb.append("</h");
        sb.append(depth);
        sb.append(">");

        return sb.toString();
    }
}
