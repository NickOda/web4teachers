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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        long rawId = getDocId(request);
        if (rawId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        DocAttachment docAttachment = ContextUtil.getDRS().findById(DocAttachment.class, rawId);
        if (docAttachment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] raw = MediaUtil.getMediaBytes(docAttachment.getAttachment());
        String type = MediaUtil.getMediaFormat(docAttachment.getAttachment());

        if (MediaUtil.isImage(type)) {
            response.setContentType("image/" + (type.equals("jpg") ? "jpeg" : type));
        } else {
            response.setContentType("application/" + type);
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + "\"" + docAttachment.getTitle() + "." +
                type + "\"");
        response.setContentLength(raw.length);
        response.getOutputStream().write(raw);
        response.getOutputStream().close();
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
