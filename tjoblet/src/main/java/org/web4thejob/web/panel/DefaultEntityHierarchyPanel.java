package org.web4thejob.web.panel;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.web4thejob.command.Command;
import org.web4thejob.command.CommandEnum;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.tjoblet.orm.EntityHierarchy;
import org.web4thejob.tjoblet.orm.EntityHierarchyItem;
import org.web4thejob.web.panel.base.zk.AbstractZkTargetTypeAwarePanel;
import org.web4thejob.web.util.ZkUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import java.util.*;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class DefaultEntityHierarchyPanel extends AbstractZkTargetTypeAwarePanel implements EntityHierarchyPanel,
        EventListener<Event> {
    private static final String ATTRIB_HIERARCHY = "ATTRIB_HIERARCHY";
    private static final String ATTRIB_ITEM = "ATTRIB_ITEM";
    private static final String ON_OPEN_ECHO = Events.ON_OPEN + "Echo";
    private final Tree tree = new Tree();
    private EntityHierarchy entityHierarchy;

    public DefaultEntityHierarchyPanel() {
        ZkUtil.setParentOfChild((Component) base, tree);
        tree.setVflex("true");
        tree.setWidth("100%");
        tree.addEventListener(Events.ON_SELECT, this);
        new Treechildren().setParent(tree);
    }

    @Override
    public Set<CommandEnum> getSupportedCommands() {
        Set<CommandEnum> supported = new HashSet<CommandEnum>(super.getSupportedCommands());
        supported.add(CommandEnum.REFRESH);
        supported.add(CommandEnum.ADD);
        supported.add(CommandEnum.REMOVE);
        supported.add(CommandEnum.MOVE_UP);
        supported.add(CommandEnum.MOVE_DOWN);
        return Collections.unmodifiableSet(supported);
    }

    @Override
    protected void arrangeForTargetType() {

        try {
            entityHierarchy = (EntityHierarchy) getTargetType().newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }

        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.REFRESH, this));
        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.ADD, this));
        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.REMOVE, this));
        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.MOVE_UP, this));
        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.MOVE_DOWN, this));
        renderRootItem();
    }

    @Override
    protected void arrangeForNullTargetType() {
        super.arrangeForNullTargetType();
        entityHierarchy = null;
    }

    private void renderRootItem() {
        tree.getTreechildren().getChildren().clear();
        if (hasTargetType() && entityHierarchy != null) {
            List<EntityHierarchyItem> roots = ContextUtil.getDRS().findByQuery(entityHierarchy.getRootItems());
            for (EntityHierarchyItem root : roots) {
                Treeitem rootItem = new Treeitem(root.toRichString());
                rootItem.setParent(tree.getTreechildren());
                rootItem.setDraggable("true");
                rootItem.setDroppable("true");
                rootItem.setAttribute(ATTRIB_ITEM, root);
                rootItem.addEventListener(Events.ON_DROP, this);
                renderChildren(rootItem, root);
            }
            arrangeForState(PanelState.READY);
        } else {
            arrangeForState(PanelState.UNDEFINED);
        }
    }

    private void renderChildren(final Treeitem parent, final EntityHierarchyItem item) {

        ContextUtil.getTransactionWrapper().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                ContextUtil.getDRS().refresh(item);

                Set<EntityHierarchy> children = item.getChildren();
                if (!children.isEmpty()) {
                    if (parent.getTreechildren() == null) {
                        new Treechildren().setParent(parent);
                    }
                    parent.getTreechildren().getChildren().clear();
                    for (EntityHierarchy hierarchy : item.getChildren()) {
                        Treeitem treeitem = new Treeitem(hierarchy.getChild().toRichString());
                        treeitem.setDraggable("true");
                        treeitem.setDroppable("true");
                        treeitem.addEventListener(Events.ON_DROP, DefaultEntityHierarchyPanel.this);
                        treeitem.setParent(parent.getTreechildren());
                        treeitem.setAttribute(ATTRIB_ITEM, hierarchy.getChild());
                        treeitem.setAttribute(ATTRIB_HIERARCHY, hierarchy);

                        treeitem.addEventListener(Events.ON_DOUBLE_CLICK, DefaultEntityHierarchyPanel.this);

                        if (!hierarchy.getChild().getChildren().isEmpty()) {
                            new Treechildren().setParent(treeitem);
                            treeitem.setOpen(false);
                            treeitem.addEventListener(Events.ON_OPEN, DefaultEntityHierarchyPanel.this);
                            treeitem.addEventListener(ON_OPEN_ECHO, DefaultEntityHierarchyPanel.this);
                        }
                    }
                }
            }
        });

    }


    @Override
    protected void arrangeForState(PanelState newState) {
        super.arrangeForState(newState);
        if (newState == PanelState.FOCUSED) {
            boolean selected = tree.getSelectedItem() != null;
            getCommand(CommandEnum.MOVE_UP).setActivated(selected);
            getCommand(CommandEnum.MOVE_DOWN).setActivated(selected);
            getCommand(CommandEnum.ADD).setActivated(selected);
            getCommand(CommandEnum.REMOVE).setActivated(selected);
        }
    }

    @Override
    public void onEvent(Event event) throws Exception {
        if (Events.ON_SELECT.equals(event.getName())) {
            arrangeForState(PanelState.FOCUSED);
        } else if (Events.ON_OPEN.equals(event.getName())) {
            showBusy();
            event.getTarget().removeEventListener(Events.ON_OPEN, this);
            Events.echoEvent(new OpenEvent(ON_OPEN_ECHO, event.getTarget(), ((OpenEvent) event).isOpen()));
        } else if (ON_OPEN_ECHO.equals(event.getName())) {
            clearBusy();
            event.getTarget().removeEventListener(ON_OPEN_ECHO, this);
            renderChildren((Treeitem) event.getTarget(), ((EntityHierarchy) event.getTarget().getAttribute
                    (ATTRIB_HIERARCHY)).getChild());
/*
        } else if (Events.ON_DOUBLE_CLICK.equals(event.getName())) {
            dispatchPropertyPath();
*/
        } else if (Events.ON_DROP.equals(event.getName())) {
            DropEvent dropEvent = (DropEvent) event;
            Treeitem target = ((Treeitem) dropEvent.getTarget());
            Treeitem dragged = ((Treeitem) dropEvent.getDragged());
            Treechildren dragged_source = (Treechildren) dragged.getParent();

            if (dragged.getParent() != null && dragged.getParent().equals(target.getTreechildren())) {
                return;
            }

            EntityHierarchyItem parent = (EntityHierarchyItem) target.getAttribute(ATTRIB_ITEM);
            EntityHierarchyItem child = (EntityHierarchyItem) dragged.getAttribute(ATTRIB_ITEM);

            if (parent != null && child != null) {
                if (target.getTreechildren() == null) {
                    new Treechildren().setParent(dropEvent.getTarget());
                }

                EntityHierarchy eh = (EntityHierarchy) dragged.getAttribute(ATTRIB_HIERARCHY);
                if (eh == null) {
                    eh = (EntityHierarchy) getTargetType().newInstance();
                }

                eh.setParent(parent);
                eh.setChild(child);
                eh.setSorting(target.getTreechildren().getItemCount() + 1);
                ContextUtil.getDWS().save(eh);

                dragged.setParent(target.getTreechildren());
                dragged.setAttribute(ATTRIB_HIERARCHY, eh);
                dragged.setSelected(true);

                if (dragged_source != null && dragged_source.getChildren() != null && dragged_source.getChildren()
                        .isEmpty()) {
                    dragged_source.setParent(null);
                }
            }
        }

    }

    @Override
    protected void processValidCommand(Command command) {
        if (CommandEnum.REFRESH.equals(command.getId())) {
            if (hasTargetType()) {
                renderRootItem();
            }
        } else if (CommandEnum.MOVE_UP.equals(command.getId())) {
            if (tree.getSelectedItem() != null) {
                Treeitem item = tree.getSelectedItem();
                Treeitem sibling = (Treeitem) item.getPreviousSibling();
                swap(sibling, item);
                tree.setSelectedItem(item);
            }
        } else if (CommandEnum.MOVE_DOWN.equals(command.getId())) {
            if (tree.getSelectedItem() != null) {
                Treeitem item = tree.getSelectedItem();
                Treeitem sibling = (Treeitem) item.getNextSibling();
                Treeitem newItem = swap(item, sibling);
                if (newItem != null) {
                    tree.setSelectedItem(newItem);
                }
            }
        } else if (CommandEnum.REMOVE.equals(command.getId())) {
            if (tree.getSelectedItem() != null) {
                Treeitem item = tree.getSelectedItem();
                EntityHierarchy eh = ((EntityHierarchy) item.getAttribute(ATTRIB_HIERARCHY));
                if (eh != null) {
                    ContextUtil.getDWS().delete(eh);
                    item.setParent(null);
                    renderRootItem();
                }
            }
        }
        super.processValidCommand(command);
    }

    private Treeitem swap(Treeitem item1, Treeitem item2) {
        if (item1 != null && item2 != null) {

            EntityHierarchy eh1 = ((EntityHierarchy) item1.getAttribute(ATTRIB_HIERARCHY));
            EntityHierarchy eh2 = ((EntityHierarchy) item2.getAttribute(ATTRIB_HIERARCHY));

            if (eh1 == null || eh2 == null) return null;

            long tmp = eh1.getSorting();
            eh1.setSorting(eh2.getSorting());
            eh2.setSorting(tmp);
            List<EntityHierarchy> toSave = new ArrayList<EntityHierarchy>();
            Collections.addAll(toSave, eh1, eh2);
            ContextUtil.getDWS().save(toSave);

            item2.detach();
            item1.getParentItem().getTreechildren().insertBefore(item2, item1);
            return item2;
        }
        return null;
    }

}

