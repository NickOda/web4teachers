package org.web4thejob.web.panel;

import org.springframework.context.annotation.Scope;
import org.web4thejob.command.Command;
import org.web4thejob.command.CommandEnum;
import org.web4thejob.command.ToolbarbuttonCommandDecorator;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.message.Message;
import org.web4thejob.message.MessageArgEnum;
import org.web4thejob.message.MessageEnum;
import org.web4thejob.orm.Entity;
import org.web4thejob.web.util.ZkUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */

@org.springframework.stereotype.Component
@Scope("prototype")
public class DefaultPrintableHierarchyPanel extends DefaultEntityHierarchyPanel implements PrintableHierarchyPanel {

    @Override
    public Set<CommandEnum> getSupportedCommands() {
        Set<CommandEnum> supported = new HashSet<CommandEnum>(super.getSupportedCommands());
        supported.add(CommandEnum.PRINT);
        return Collections.unmodifiableSet(supported);
    }

    @Override
    protected void arrangeForTargetType() {
        super.arrangeForTargetType();
        if (!isReadOnly()) {
            registerCommand(ContextUtil.getDefaultCommand(CommandEnum.PRINT, this));
        }
    }

    @Override
    public void dispatchMessage(Message message) {
        if (message.getId().equals(MessageEnum.ENTITY_SELECTED)) {
            Command command = getCommand(CommandEnum.PRINT);
            if (command != null) {
                List<ToolbarbuttonCommandDecorator> decorators = ZkUtil.getCommandDecorators(command,
                        ToolbarbuttonCommandDecorator.class);
                for (ToolbarbuttonCommandDecorator button : decorators) {
                    button.setHref("book?id=" + message.getArg(MessageArgEnum.ARG_ITEM,
                            Entity.class).getIdentifierValue());
                    button.setTarget("_blank");
                }
            }
        }
        super.dispatchMessage(message);
    }
}