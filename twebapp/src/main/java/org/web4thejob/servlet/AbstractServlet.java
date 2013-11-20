package org.web4thejob.servlet;

import org.apache.commons.io.Charsets;
import org.springframework.web.context.support.ServletContextResource;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.orm.Entity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public abstract class AbstractServlet extends HttpServlet {
    private URL baseRef;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long docId = getDocId(request);
        if (docId <= 0) {
            return;
        }
        String templId = getTemplId(request);
        if (templId == null) {
            templId = getDefaultTemplate();
        }

        Entity entity = getEntity(docId);
        if (entity == null) {
            return;
        }

        if (baseRef == null) {
            baseRef = getBaseURL(request);
        }

        response.setContentType("text/html; charset=utf-8");
        ServletContextResource template = (ServletContextResource) ContextUtil.getResource("templ/" + templId + "" +
                ".html");
        PrintWriter out = response.getWriter();
        for (String line : Files.readAllLines(template.getFile().toPath(), Charsets.UTF_8)) {
            if (line.contains("${basref}")) {
                line = line.replace("${basref}", baseRef.toString());
            } else if (line.contains("${body}")) {
                line = line.replace("${body}", getBody(entity));
            } else if (line.contains("${title}")) {
                line = line.replace("${title}", getTitle(entity));
            }
            out.println(line);
        }

    }

    protected abstract String getDefaultTemplate();

    protected abstract String getTitle(Entity entity);

    protected abstract String getBody(Entity entity);

    protected abstract Entity getEntity(long docId);

    private URL getBaseURL(HttpServletRequest request) throws MalformedURLException {
        return new URL("http", request.getServerName(), request.getServerPort(), request.getContextPath() + "/");
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

    private String getTemplId(HttpServletRequest request) {
        String paramId = request.getParameter("templ");
        if (paramId == null
                || paramId.trim().length() == 0
                || !ContextUtil.resourceExists("templ/" + paramId + ".html")) {
            return null;
        }
        return paramId.trim();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
