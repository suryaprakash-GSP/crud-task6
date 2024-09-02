package com.posidex.crud_task6.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
@CrossOrigin(value = "*")
public class EndPointsController {
    @GetMapping
    public Map<String,String> allServicesHandler()
    {
        Map<String,String> services=new LinkedHashMap<>();
        services.put("fm_automapper_dictionary-getRecords","http://192.168.3.51:8080/dictionary/delete");
        services.put("fm_automapper_dictionary-addRecord","http://192.168.3.51:8086/table1/records");
        services.put("fm_automapper_dictionary-updateRecord","http://192.168.3.51:8086/table1/update/{id}");
        services.put("fm_automapper_dictionary-deleteRecord","http://192.168.3.51:8086/table1/delete/{id}");
        services.put("pr_master_record_data_sources-getRecords","http://192.168.3.51:8086/table2/records");
        services.put("pr_master_record_data_sources-addRecord","http://192.168.3.51:8086/table2/records");
        services.put("pr_master_record_data_sources-updateRecord","http://192.168.3.51:8086/table2/update/{id}");
        services.put("pr_master_record_data_sources-deleteRecord","http://192.168.3.51:8086/table2/delete/{id}");
return services;
    }
}
