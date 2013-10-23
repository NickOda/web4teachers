package org.web4thejob.servlet;

import org.apache.commons.io.Charsets;
import org.springframework.web.context.support.ServletContextResource;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.tjoblet.orm.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class DocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // read parameter
        String paramId = request.getParameter("id");
        if (paramId == null || paramId.length() == 0) {
            return;
        }
        long id;
        try {
            id = Long.valueOf(paramId);
        } catch (NumberFormatException e) {
            return;
        }
        if (id <= 0) {
            return;
        }


        Document doc = ContextUtil.getDRS().findById(Document.class, id);
        if (doc == null) {
            return;
        }

        response.setContentType("text/html; charset=utf-8");
        ServletContextResource template = (ServletContextResource) ContextUtil.getResource("math/math.html");
        PrintWriter out = response.getWriter();
        for (String line : Files.readAllLines(template.getFile().toPath(), Charsets.UTF_8)) {
            if (line.contains("${basref}")) {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(request.getServerName());
                sb.append(":");
                sb.append(request.getServerPort());
                sb.append(request.getContextPath());
                sb.append("/");
                line = line.replace("${basref}", sb.toString());
            } else if (line.contains("${body}")) {
                line = line.replace("${body}", doc.getBody());
            }
            out.println(line);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return new Date().getTime();
    }

    @Override
    public void init() throws ServletException {
        super.init();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        super.destroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
