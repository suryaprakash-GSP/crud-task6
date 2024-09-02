package com.posidex.crud_task6.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.posidex.crud_task6.exception.handler.ResourceNotFoundException;
import com.posidex.crud_task6.utility.InitialDataModel;

@Configuration
public class WebMvcConfig implements Filter, WebMvcConfigurer {

    @Autowired
    private InitialDataModel initialDataModel;


    public static final Logger logger = LogManager.getLogger(WebMvcConfig.class.getName());

    private static final String HEADER_INFO = "Access-Control-Expose-Headers";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        String corsorigin;

        try {

            Map<String, Object> appPropConfigKeyValues = initialDataModel.getAppPropConfigKeyValues();
            corsorigin = (String) appPropConfigKeyValues.get("cors.origin");

            if (StringUtils.isEmpty(corsorigin)) {
                logger.info("cors are empty {}", corsorigin);
                throw new ResourceNotFoundException("CORS are not available");
            }
            String[] split = corsorigin.split(",");
            List<String> corsList = Arrays.asList(split);
            HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest) req;
            String reqOrigin = request.getHeader("origin");
            String reqHost = request.getHeader("host");
            int serverPort = request.getServerPort();
            String host = request.getServerName();
            if (serverPort != 80) {
                host = request.getServerName() + ":" + serverPort;
            }
            logger.info("Request Host {} , Host {}", reqHost, host);
            if (reqHost != null && !StringUtils.equalsIgnoreCase(reqHost, host)) {
                throw new ResourceNotFoundException("Request Host is Not valid");
            }
            logger.info("request origin {}", reqOrigin);

            if (reqOrigin != null && !corsList.contains(reqOrigin)) {
                throw new ResourceNotFoundException("Cors are Not valid");
            }
            response.setHeader("Access-Control-Allow-Origin", reqOrigin);

            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET");
            response.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader(HEADER_INFO, "Authorization");
            response.addHeader(HEADER_INFO, "responseType");
            response.addHeader(HEADER_INFO, "observe");
            response.setHeader("Cache-Control", "no-store, no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
            response.setHeader("Referrer-Policy", "no-referrer");

            response.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
            response.setHeader("X-XSS-Protection", "1; mode=block");
            response.setHeader("X-Content-Type-Options", "nosniff");

            response.setHeader("Content-Security-Policy", "script-src 'self'");
            response.setHeader("Permissions-Policy", "unsized-media 'self'");

            response.setHeader("Custom-Filter-Header", "Write Header using Filter");

            if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
                chain.doFilter(req, res);
            } else {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } catch (Exception e) {
            logger.error("Exception at security level {}", e.getMessage());
        }
    }


    @Override
    public void destroy() {
        logger.info("destroy service");
    }

}