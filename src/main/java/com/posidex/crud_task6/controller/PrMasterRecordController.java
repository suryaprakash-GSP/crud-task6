package com.posidex.crud_task6.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posidex.crud_task6.service.PrMasterRecordService;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/prmaster")
public class PrMasterRecordController {

    @Autowired
    private PrMasterRecordService prMasterRecordService;

 
    
    @GetMapping("/records")
    public ResponseEntity<Object> getAllRecordHAndler()
    {
        return prMasterRecordService.getAllRecords();
    }
    @PostMapping("/addrecord")
    public ResponseEntity<Object> addRecordAHandler(@RequestBody Map<String, Object> record)
    {
        return prMasterRecordService.addRecord(record);
    }
    @PostMapping("/update")
    public  ResponseEntity<Object> updateRecordHandler(@RequestBody Map<String, Object> record)
    {
        return prMasterRecordService.updateRecord(record);
    }
    @PostMapping("delete")
    public ResponseEntity<Object> deleteRecordHandler(@RequestBody Map<String, Object> record)
    {
        return prMasterRecordService.deleteRecord(record);
    }



}
