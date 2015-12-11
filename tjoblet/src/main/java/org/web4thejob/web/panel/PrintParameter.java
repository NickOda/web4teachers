package org.web4thejob.web.panel;

import org.web4thejob.message.MessageListener;
import org.web4thejob.util.L10nString;
import org.web4thejob.web.dialog.AbstractDialog;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Vbox;

import java.io.Serializable;


public class PrintParameter extends AbstractDialog {

    public static final L10nString L10N_BUTTON_DOC = new L10nString(org.zkoss.zul.Checkbox.class, "button_document", "Εκφώνηση");
    public static final L10nString L10N_BUTTON_NOTES = new L10nString(org.zkoss.zul.Checkbox.class, "button_notes", "Απάντηση");
    public static final L10nString L10N_BUTTON_COMMENTS = new L10nString(org.zkoss.zul.Checkbox.class, "button_comments", "Σχόλια");
    public static final L10nString L10N_BUTTON_SOURCE = new L10nString(org.zkoss.zul.Checkbox.class, "button_source", "Πηγή");
    public static int para = 0;

    protected org.zkoss.zul.Checkbox docButton;
    protected org.zkoss.zul.Checkbox notesButton;
    protected org.zkoss.zul.Checkbox commButton;
    protected org.zkoss.zul.Checkbox sourceButton;

    protected boolean OKready = false;
    private String link;
    private Serializable id;

    public PrintParameter(Serializable id, String link) {
        this.id = id;
        this.link = link;
    }


    @Override
    protected void prepareButtons() {
        super.prepareButtons();
        docButton = new org.zkoss.zul.Checkbox(L10N_BUTTON_DOC.toString());
        docButton.setParent(dialogContent.getPanelchildren());
        docButton.addEventListener(Events.ON_CHECK, this);
        notesButton = new org.zkoss.zul.Checkbox(L10N_BUTTON_NOTES.toString());
        notesButton.setParent(dialogContent.getPanelchildren());
        notesButton.addEventListener(Events.ON_CHECK, this);
        commButton = new org.zkoss.zul.Checkbox(L10N_BUTTON_COMMENTS.toString());
        commButton.setParent(dialogContent.getPanelchildren());
        commButton.addEventListener(Events.ON_CHECK, this);
        sourceButton = new org.zkoss.zul.Checkbox(L10N_BUTTON_SOURCE.toString());
        sourceButton.setParent(dialogContent.getPanelchildren());
        sourceButton.addEventListener(Events.ON_CHECK, this);


    }

    @Override
    protected void prepareContentLayout() {
        super.prepareContentLayout();
        window.setHeight("150px");
        window.setWidth("400px");
    }

    @Override
    protected String prepareTitle() {
        return "Εκφώνηση";
    }


    @Override
    protected void prepareContent() {
        dialogContent.getPanelchildren().setStyle("overflow: auto;");
        Vbox vbox = new Vbox();
        vbox.setParent(dialogContent.getPanelchildren());
        vbox.setHflex("true");
        vbox.setVflex("true");
        vbox.setAlign("center");
        vbox.setPack("center");
        vbox.setSpacing("10px");


    }


    @Override
    public void onEvent(Event event) throws Exception {
        if (Events.ON_CHECK.equals(event.getName())) {
            if (docButton.isChecked()) {
                link = "book?id=" + id;
                para = 1;
                if (notesButton.isChecked()) {
                    para = 12;
                    if (commButton.isChecked()) {
                        para = 123;
                        if (sourceButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (commButton.isChecked()) {
                    para = 13;
                    if (notesButton.isChecked()) {
                        para = 123;
                        if (sourceButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }

                if (sourceButton.isChecked()) {
                    para = 14;
                    if (notesButton.isChecked()) {
                        para = 124;
                        if (commButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }

                // btnOK.setHref(link);
                //  btnOK.setTarget("_blank");
            } else if (notesButton.isChecked()) {
                para = 2;
                if (commButton.isChecked()) {
                    para = 23;
                    if (docButton.isChecked()) {
                        para = 123;
                        if (sourceButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (docButton.isChecked()) {
                    para = 12;
                    if (commButton.isChecked()) {
                        para = 123;
                        if (sourceButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (sourceButton.isChecked()) {
                    para = 24;
                    if (docButton.isChecked()) {
                        para = 124;
                        if (commButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                link = "note?templ=note&id=" + id;
                // btnOK.setHref(link);
                // btnOK.setTarget("_blank");
            } else if (commButton.isChecked()) {
                para = 3;
                if (docButton.isChecked()) {
                    para = 13;
                    if (notesButton.isChecked()) {
                        para = 123;
                        if (sourceButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (notesButton.isChecked()) {
                    para = 23;
                    if (docButton.isChecked()) {
                        para = 123;
                        if (sourceButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (sourceButton.isChecked()) {
                    para = 34;
                    if (docButton.isChecked()) {
                        para = 134;
                        if (notesButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                link = "comment?templ=comment&id=" + id;


                //btnOK.setHref(link);
                // btnOK.setTarget("_blank");
            }
            if (sourceButton.isChecked()) {
                para = 4;
                if (docButton.isChecked()) {
                    para = 14;
                    if (notesButton.isChecked()) {
                        para = 124;
                        if (commButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (notesButton.isChecked()) {
                    para = 24;
                    if (docButton.isChecked()) {
                        para = 124;
                        if (commButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
                if (commButton.isChecked()) {
                    para = 34;
                    if (docButton.isChecked()) {
                        para = 134;
                        if (notesButton.isChecked()) {
                            para = 1234;
                        }
                    }
                }
            }
        } else {
            super.onEvent(event);
        }
    }

    @Override
    protected void doCancel() {
        window.detach();
    }

    @Override
    protected void onBeforeShow() {
        btnOK.setHref(link);
        btnOK.setTarget("_blank");


    }


    protected void setOK() {
        this.OKready = true;
    }

    @Override
    protected void prepareForOK() {
        this.setOK();
//        if (docButton.isChecked()) {
//            btnOK.setHref(link);
//
//            btnOK.setTarget("_blank");
//        }

    }

    protected boolean isOKready() {
        return this.OKready;
    }

    @Override
    public void show(MessageListener listener) {
        this.OKready = false;
        super.show(listener);
    }


}
