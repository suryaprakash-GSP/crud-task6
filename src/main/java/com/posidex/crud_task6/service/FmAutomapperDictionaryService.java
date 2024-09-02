package com.posidex.crud_task6.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface FmAutomapperDictionaryService {

    ResponseEntity<Object> getAllRecords();
    ResponseEntity<Object>addRecord(Map<String, Object> record);
    ResponseEntity<Object>updateRecord(Map<String, Object> record);
    ResponseEntity<Object>deleteRecord(Map<String, Object> record);
}
