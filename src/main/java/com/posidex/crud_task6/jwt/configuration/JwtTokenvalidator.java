package com.posidex.crud_task6.jwt.configuration;

import com.posidex.crud_task6.utility.InitialDataModel;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtTokenvalidator {

    @Autowired
    private InitialDataModel initialDataModel;

    private static final Logger log = LogManager.getFormatterLogger(JwtTokenvalidator.class);

    public String getUsernameFromToken(String token) {
        Map<String, Object> appPropConfigKeyValues = initialDataModel.getAppPropConfigKeyValues();
        String secretKey = (String) appPropConfigKeyValues.get("jwt.validator.secret");
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Map<String, Object> appPropConfigKeyValues = initialDataModel.getAppPropConfigKeyValues();
            String secretKey = (String) appPropConfigKeyValues.get("jwt.validator.secret");
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            claims.getSubject();
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
                 | IllegalArgumentException e) {
            log.error("token is invalid");
            return false;
        }
    }

}
