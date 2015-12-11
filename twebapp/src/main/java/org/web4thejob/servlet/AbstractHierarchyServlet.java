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
import org.web4thejob.web.panel.PrintParameter;

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
        if ((PrintParameter.para == 1) || (PrintParameter.para == 14)) {
            return "Εκφωνήσεις";
        } else if ((PrintParameter.para == 2) || (PrintParameter.para == 24)) {
            return "Απαντήσεις";
        } else if ((PrintParameter.para == 3) || (PrintParameter.para == 34)) {
            return "Σχόλια";
        } else if (PrintParameter.para == 4) {
            return "Πηγή";
        } else if ((PrintParameter.para == 12) || (PrintParameter.para == 124)) {
            return "Εκφωνήσεις - Απαντήσεις";
        } else if ((PrintParameter.para == 13) || (PrintParameter.para == 134)) {
            return "Εκφωνήσεις - Σχόλια";
        } else if ((PrintParameter.para == 23) || (PrintParameter.para == 234)) {
            return ("Απαντήσεις - Σχόλια");
        } else return ("Εκφωνήσεις - Απαντήσεις - Σχόλια");



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


        } else
            switch (PrintParameter.para) {
                case 1:
                    return ((Document) ehi).getBody();
                case 2:
                    return ((Document) ehi).getNotes().getNotes();
                case 3:
                    return ((Document) ehi).getComments().getComments();
                case 4:
                    return "Πηγή: " + ((Document) ehi).getSource();
                case 12:
                    return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getNotes().getNotes();
                case 23:
                    return ((Document) ehi).getNotes().getNotes() + "<hr>" +
                            ((Document) ehi).getComments().getComments();
                case 13:
                    return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getComments().getComments();
                case 14:
                    return ((Document) ehi).getBody() + "<hr>" + "Πηγή: " + ((Document) ehi).getSource();
                case 24:
                    return ((Document) ehi).getNotes().getNotes() + "<hr>" + "Πηγή: " + ((Document) ehi).getSource();
                case 34:
                    return ((Document) ehi).getComments().getComments() + "<hr>" + "Πηγή: " +
                            ((Document) ehi).getSource();
                case 123:
                    return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getNotes().getNotes()
                            + "<hr>" + ((Document) ehi).getComments().getComments();
                case 124:
                    return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getNotes().getNotes()
                            + "<hr>" + "Πηγή: " + ((Document) ehi).getSource();
                case 134:
                    return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getComments().getComments()
                            + "<hr>" + "Πηγή: " + ((Document) ehi).getSource();
                case 234:
                    return ((Document) ehi).getNotes().getNotes() + "<hr>" +
                            ((Document) ehi).getComments().getComments() + "<hr>" +
                            "Πηγή: " + ((Document) ehi).getSource();
                default:
                    return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getNotes().getNotes()
                            + "<hr>" + ((Document) ehi).getComments().getComments()
                            + "<hr>" + "Πηγή: " + ((Document) ehi).getSource();


            }
            /*if (PrintParameter.para == 1) {
                return ((Document) ehi).getBody();
            }else if (PrintParameter.para == 2) {
                return ((Document) ehi).getNotes().getNotes();
            }else if (PrintParameter.para == 3) {
                return ((Document) ehi).getComments().getComments();
            }else if (PrintParameter.para == 12) {
                return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getNotes().getNotes();
            }else if (PrintParameter.para == 23) {
                return ((Document) ehi).getNotes().getNotes() +((Document) ehi).getBody() + "<hr>" +
                        ((Document) ehi).getNotes().getNotes();
            }else if (PrintParameter.para == 13) {
                return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getComments().getComments();
            }else return ((Document) ehi).getBody() + "<hr>" + ((Document) ehi).getNotes().getNotes() +
                    ((Document) ehi).getComments().getComments() ;
*/
    }

    protected StringBuffer traverseChildren(EntityHierarchyParent parent, List<Integer> level) {
        StringBuffer body = new StringBuffer();

        parent = (EntityHierarchyParent) ContextUtil.getDRS().get(parent.getEntityType(), parent.getIdentifierValue());

        level.add(0);
        int count = 0;
        for (Object child : parent.getChildren()) {
            level.set(level.size() - 1, ++count);

            EntityHierarchy hierarchy = (EntityHierarchy) child;
            // System.out.println(hierarchy.getClass() + " " + hierarchy.getIdentifierValue());
            // System.out.println(ContextUtil.getDRS().findById(Document, ));
            body.append(getTitle(hierarchy.getChild().toString(), level));
            if (hierarchy.getChild() instanceof EntityHierarchyParent) {
                body.append(traverseChildren((EntityHierarchyParent) hierarchy.getChild(), new ArrayList<>(level)));
            } else if (hierarchy.getChild() instanceof Document) {
                switch (PrintParameter.para) {
                    case 1:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        break;
                    case 2:
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        break;
                    case 3:
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        break;
                    case 4:
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    case 12:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        break;
                    case 23:
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        break;
                    case 13:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        break;
                    case 14:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    case 24:
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    case 34:
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    case 123:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        break;
                    case 124:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    case 134:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    case 234:
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;
                    default:
                        body.append(((Document) hierarchy.getChild()).getBody());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                        body.append("<hr>");
                        body.append(((Document) hierarchy.getChild()).getComments().getComments());
                        body.append("<hr>");
                        body.append("Πηγή: " + ((Document) hierarchy.getChild()).getSource());
                        break;

                }

               /* if (PrintParameter.para == 1) {
                   body.append(((Document) hierarchy.getChild()).getBody());
               }else if (PrintParameter.para == 2) {
                    body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
               }else if (PrintParameter.para == 3) {
                    body.append(((Document) hierarchy.getChild()).getComments().getComments());
                }else if (PrintParameter.para == 12) {
                    body.append(((Document) hierarchy.getChild()).getBody());
                    body.append("<hr>");
                    body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                }else if (PrintParameter.para == 23) {
                    body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                    body.append("<hr>");
                    body.append(((Document) hierarchy.getChild()).getComments().getComments());
                }else if (PrintParameter.para == 13) {
                    body.append(((Document) hierarchy.getChild()).getBody());
                    body.append("<hr>");
                    body.append(((Document) hierarchy.getChild()).getComments().getComments());
                }else if (PrintParameter.para == 123) {
                    body.append(((Document) hierarchy.getChild()).getBody());
                    body.append("<hr>");
                    body.append(((Document) hierarchy.getChild()).getNotes().getNotes());
                    body.append("<hr>");
                    body.append(((Document) hierarchy.getChild()).getComments().getComments());
                }*/
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
        //sb.append(title);

        sb.append("</h");
        sb.append(depth);
        sb.append(">");

        return sb.toString();
    }
}
