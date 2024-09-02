package com.posidex.crud_task6.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PrMasterRecordService {
    ResponseEntity<Object> getAllRecords();

    ResponseEntity<Object> addRecord(Map<String, Object> record);

    ResponseEntity<Object> updateRecord(Map<String, Object> record);

    ResponseEntity<Object> deleteRecord(Map<String, Object> record);
}
