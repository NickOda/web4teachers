package org.web4thejob.servlet;

import org.web4thejob.context.ContextUtil;
import org.web4thejob.tjoblet.orm.DocAttachment;
import org.web4thejob.web.util.MediaUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AttachmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        DocAttachment docAttachment = ContextUtil.getDRS().findById(DocAttachment.class, getDocId(req));
        byte[] raw = MediaUtil.getMediaBytes(docAttachment.getAttachment());
        String type = MediaUtil.getMediaFormat(docAttachment.getAttachment());
        resp.setContentType("image/jpeg");
        resp.setContentLength(raw.length);
        resp.getOutputStream().write(raw);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private long getDocId(HttpServletRequest request) {
        String paramId = request.getParameter("id");
        if (paramId == null || paramId.length() == 0) {
            return -1;
        }

        try {
            return Long.valueOf(paramId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
