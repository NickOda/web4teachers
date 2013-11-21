package org.web4thejob.web.panel;

import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.web4thejob.command.Command;
import org.web4thejob.command.CommandEnum;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.message.Message;
import org.web4thejob.message.MessageArgEnum;
import org.web4thejob.message.MessageEnum;
import org.web4thejob.message.MessageListener;
import org.web4thejob.orm.Entity;
import org.web4thejob.orm.PropertyMetadata;
import org.web4thejob.setting.SettingEnum;
import org.web4thejob.web.dialog.HtmlDialog;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */

@org.springframework.stereotype.Component
@Scope("prototype")
public class DefaultDocumentPanel extends DefaultFramePanel implements DocumentPanel {
    protected PropertyMetadata htmlProperty;

    public DefaultDocumentPanel() {
        super();
        iframe.addEventListener(Events.ON_USER, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                Clients.clearBusy(iframe);
                iframe.setSrc(event.getData().toString());
            }
        });
    }

    @Override
    protected void arrangeForNullTargetType() {
        super.arrangeForNullTargetType();
        htmlProperty = null;
    }

    @Override
    protected void arrangeForTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
        if (this.targetEntity == null) {
            iframe.setSrc(getBlank());
            arrangeForState(PanelState.READY);
            refresh();
        } else if (isBound()) {
            iframe.setSrc(getSettingValue(SettingEnum.TARGET_URL, "") + targetEntity.getIdentifierValue().toString());
            arrangeForState(PanelState.FOCUSED);
            refresh();
        }
    }

    @Override
    protected boolean isBound() {
        return hasTargetType() && StringUtils.hasText(getSettingValue(SettingEnum.TARGET_URL, ""));
    }

    @Override
    public Set<CommandEnum> getSupportedCommands() {
        Set<CommandEnum> supported = new HashSet<CommandEnum>(super.getSupportedCommands());
        supported.add(CommandEnum.REFRESH);
        supported.add(CommandEnum.UPDATE);
        return Collections.unmodifiableSet(supported);
    }

    @Override
    protected void processValidCommand(Command command) {
        if (command.getId().equals(CommandEnum.REFRESH)) {
            refresh();
        } else if (command.getId().equals(CommandEnum.UPDATE)) {
            if (hasTargetEntity() && htmlProperty != null) {
                HtmlDialog dialog = ContextUtil.getDefaultDialog(HtmlDialog.class,
                        htmlProperty.getValue(getTargetEntity()).toString());
                dialog.show(new MessageListener() {
                    @Override
                    public void processMessage(Message message) {
                        if (message.getId() == MessageEnum.AFFIRMATIVE_RESPONSE) {
                            htmlProperty.setValue(getTargetEntity(), message.getArg(MessageArgEnum.ARG_ITEM,
                                    String.class));
                            ContextUtil.getDWS().save(getTargetEntity());
                            refresh();

                            Message updMessage = ContextUtil.getMessage(MessageEnum.ENTITY_UPDATED,
                                    DefaultDocumentPanel.this);
                            dispatchMessage(updMessage);
                        }
                    }
                });

            }
        } else {
            super.processValidCommand(command);
        }
    }

    public void refresh() {
        Events.echoEvent(Events.ON_USER, iframe, iframe.getSrc());
        iframe.setSrc(null);
        Clients.showBusy(iframe, null);
    }

    @Override
    protected void arrangeForTargetType() {
        super.arrangeForTargetType();
        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.REFRESH, this));
        registerCommand(ContextUtil.getDefaultCommand(CommandEnum.UPDATE, this));
        htmlProperty = ContextUtil.getMRS().getPropertyMetadata(getTargetType(),
                getSettingValue(SettingEnum.HTML_PROPERTY, ""));
    }

    @Override
    public void render() {
        super.render();
        if (!hasTargetEntity()) {
            iframe.setSrc(getBlank());
        }
    }

    @Override
    protected void registerSettings() {
        super.registerSettings();
        registerSetting(SettingEnum.HTML_PROPERTY, null);
    }

    @Override
    protected boolean processEntityUpdate(Entity entity) {
        refresh();
        return super.processEntityUpdate(entity);
    }

    protected String getBlank() {
        return "templ/blank_doc.html";
    }
}
