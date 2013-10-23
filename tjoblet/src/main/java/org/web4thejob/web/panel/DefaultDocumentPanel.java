package org.web4thejob.web.panel;

import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.web4thejob.orm.Entity;
import org.web4thejob.setting.SettingEnum;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */

@org.springframework.stereotype.Component
@Scope("prototype")
public class DefaultDocumentPanel extends DefaultFramePanel implements DocumentPanel {

    @Override
    protected void arrangeForTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
        if (this.targetEntity == null) {
            iframe.setSrc(null);
        } else if (isBound()) {
            iframe.setSrc(getSettingValue(SettingEnum.TARGET_URL, "")); //force refresh
            iframe.setSrc(getSettingValue(SettingEnum.TARGET_URL, "") + targetEntity.getIdentifierValue().toString());
        }
    }

    @Override
    protected boolean isBound() {
        return hasTargetType() && StringUtils.hasText(getSettingValue(SettingEnum.TARGET_URL, ""));
    }
}
