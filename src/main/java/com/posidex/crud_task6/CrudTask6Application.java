package com.posidex.crud_task6;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.posidex.crud_task6.utility.InitialDataModel;

@SpringBootApplication
public class CrudTask6Application {

    public static void main(String[] args) {
        SpringApplication.run(CrudTask6Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${service.name}")
    private String solutionName;

    @PostConstruct
    InitialDataModel initialData() {
        String appPropConfigQuery = "select propertyname ,propertyvalue  from appprop_config ";
        String appPropConfigServiceQuery = "select propertyname ,propvalue  from appprop_config_services where solutionname = '"
                + solutionName + "'";
        List<Map<String, Object>> appPropConfig = jdbcTemplate.queryForList(appPropConfigQuery);
        List<Map<String, Object>> appPropConfigService = jdbcTemplate.queryForList(appPropConfigServiceQuery);
        Map<String, Object> data = new LinkedHashMap<>();

        for (Map<String, Object> individualRecord : appPropConfig) {
            String propertyName = (String) individualRecord.get("propertyname");
            String propertyValue = (String) individualRecord.get("propertyvalue");
            data.put(propertyName, propertyValue);
        }

        for (Map<String, Object> individualServiceRecord : appPropConfigService) {
            String propertyName = (String) individualServiceRecord.get("propertyname");
            String propertyValue = (String) individualServiceRecord.get("propvalue");
            data.put(propertyName, propertyValue);
        }

        InitialDataModel dataModel = new InitialDataModel();
        dataModel.setAppPropConfigKeyValues(data);
        return dataModel;
    }

    @Bean
    InitialDataModel initialDataModel() {
        return initialData();
    }

}
