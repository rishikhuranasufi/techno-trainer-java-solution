package com.technotrainer.training.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WebPageController {

    public WebPageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private static final Logger logger = LoggerFactory.getLogger(WebPageController.class);

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/")
    public String index() {
        return "<html> <b>This is TechnoTrainer Java based Application for HandsOn Demo.</b></html>";
    }

    @GetMapping(value = {"/welcome", "/welcome/{user}"})
    public String welcome(@PathVariable(required = false) String user) throws IOException, URISyntaxException {
        String htmlString = null;
        Resource resource1 = loadWelComeHtmlFileWithResourceLoader();
        InputStream inputStream = resource1.getInputStream();
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            htmlString = new String(bdata, StandardCharsets.UTF_8);
            logger.info(htmlString);
        } catch (IOException e) {
            logger.error("IOException", e);
        }

        validateHTMLString(htmlString);
        htmlString = getString(user, htmlString);

        return htmlString;

    }

    public void validateHTMLString(String htmlString) {
        if(StringUtils.isEmpty(htmlString)){
            logger.info("File content is : "+ htmlString);
            logger.error("File content is NULL");
            throw new NullPointerException();
        }
    }

    public String getString(String user, String htmlString) throws UnknownHostException {
        String userName = StringUtils.isEmpty(user) ? "Participant" : user;
        htmlString = htmlString.replace("$userName", userName);
        htmlString = htmlString.replace("$dateTime", java.time.LocalDateTime.now().toString());
        htmlString = htmlString.replace("$hostName", InetAddress.getLocalHost().getHostName());
        return htmlString;
    }

    public Resource loadWelComeHtmlFileWithResourceLoader() {
        return resourceLoader.getResource(
                "classpath:welcome.html");
    }
}
