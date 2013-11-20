package org.web4thejob.web.panel;

import org.springframework.context.annotation.Scope;
import org.web4thejob.command.Command;
import org.web4thejob.command.CommandEnum;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.message.Message;
import org.web4thejob.message.MessageArgEnum;
import org.web4thejob.message.MessageEnum;
import org.web4thejob.message.MessageListener;
import org.web4thejob.orm.Entity;
import org.web4thejob.setting.SettingEnum;
import org.web4thejob.tjoblet.orm.Content;
import org.web4thejob.tjoblet.orm.ContentNotes;
import org.web4thejob.web.dialog.HtmlDialog;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */

@org.springframework.stereotype.Component
@Scope("prototype")
public class DocumentNotesPanel extends DefaultDocumentPanel {

    public DocumentNotesPanel() {
        super();
        setSettingValue(SettingEnum.TARGET_TYPE, ContentNotes.class);
        setSettingValue(SettingEnum.MASTER_TYPE, Content.class);
        setSettingValue(SettingEnum.BIND_PROPERTY, "content");
        setSettingValue(SettingEnum.HTML_PROPERTY, "notes");
    }

    @Override
    protected void processValidCommand(Command command) {
        if (command.getId().equals(CommandEnum.UPDATE)) {
            if (hasTargetEntity()) {
                super.processValidCommand(command);
            } else if (hasMasterEntity()) {

                try {

                    final Entity entity = getTargetType().newInstance();

                    ContextUtil.getMRS().getPropertyMetadata(getTargetType(), getBindProperty()).setValue(entity,
                            getMasterEntity());
                    HtmlDialog dialog = ContextUtil.getDefaultDialog(HtmlDialog.class, "");
                    dialog.show(new MessageListener() {
                        @Override
                        public void processMessage(Message message) {
                            if (message.getId() == MessageEnum.AFFIRMATIVE_RESPONSE) {
                                htmlProperty.setValue(entity, message.getArg(MessageArgEnum.ARG_ITEM,
                                        String.class));
                                ContextUtil.getDWS().save(entity);
                                bind(entity);

                                Message insMessage = ContextUtil.getMessage(MessageEnum.ENTITY_INSERTED,
                                        DocumentNotesPanel.this);
                                dispatchMessage(insMessage);
                            }
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            super.processValidCommand(command);
        }
    }

    @Override
    protected String getBlank() {
        return "templ/blank_note.html";
    }

    @Override
    protected void arrangeForState(PanelState newState) {
        super.arrangeForState(newState);
        if (hasCommand(CommandEnum.UPDATE)) {
            getCommand(CommandEnum.UPDATE).setActivated(hasMasterEntity());
        }
    }
}
