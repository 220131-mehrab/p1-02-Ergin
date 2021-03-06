package com.revature.music;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        Tomcat server = new Tomcat();
        server.getConnector();
        server.addContext("",null);
        server.addServlet("", "defaultServlet", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String filename = req.getPathInfo();
                String resourcesDir = "static";
                InputStream file =  getClass().getClassLoader().getResourceAsStream(resourcesDir + filename);
                String mimetype = getServletContext().getMimeType(filename);
                resp.setContentType(mimetype);
                IOUtils.copy(file,resp.getOutputStream());
            }
        }).addMapping("/*");
        try {
            server.start();
        } catch (LifecycleException e) {
            System.err.println("Failed to start Tomcat Server: " + e.getMessage());
        }

    }
}
