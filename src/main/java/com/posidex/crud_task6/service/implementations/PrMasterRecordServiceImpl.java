package com.posidex.crud_task6.service.implementations;

import com.posidex.crud_task6.Response;
import com.posidex.crud_task6.service.PrMasterRecordService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;

@Service
@Slf4j
public class PrMasterRecordServiceImpl implements PrMasterRecordService {
    private static final String FETCH_RECORDS_QUERY="Select * FROM pr_master_record_data_sources";


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public ResponseEntity<Object> getAllRecords()
    {
        try{
            List<Map<String, Object>> records=jdbcTemplate.queryForList(FETCH_RECORDS_QUERY);
            Object headers=records.get(0).keySet();
            Response response=new Response();
            response.setRecords(records);
            response.setHeaders(headers);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(DataAccessException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","failed","status",HttpStatus.INTERNAL_SERVER_ERROR, "error",e.getMessage()));
        }
    }



    @Override
    public ResponseEntity<Object> addRecord(Map<String, Object> record) {

        if (record.containsKey("akid")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not pass key akid. Your record-> \n " + record);

        }


        record.put("creation_date",getCurrentTime());



        Set<String> keys = record.keySet();
        Collection<Object> values = record.values();

        String commaSeparatedValues = values.stream()
                .map(value -> value instanceof String ? "'" + value + "'" : value.toString())
                .collect(Collectors.joining(", "));

        String columns = keys.stream()
                .collect(Collectors.joining(", "));

        String insertQuery = "INSERT INTO  pr_master_record_data_sources " + " (" + columns + ") VALUES (" + commaSeparatedValues + ")";

        try {
            jdbcTemplate.update(insertQuery);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", HttpStatus.CREATED.value(), "message", "success", "record", record));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR, "message", "failed", "error", e.getMessage()));
        }



    }

    @Override
    public ResponseEntity<Object> updateRecord(Map<String, Object> record) {
          Object id=record.get("akid");
        if (!isRecordExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found with id to update " + id);
        }

       

        record.put("creation_date",getCurrentTime());

        Set<String> columns = record.keySet();
        String setClause;
        setClause = columns.stream()
                .map(column -> column + " = ?")
                .collect(Collectors.joining(", "));

        String UpdateQuery = String.format("UPDATE pr_master_record_data_sources SET %s WHERE akid = %d", setClause, id);

        try {

            jdbcTemplate.update(UpdateQuery, record.values().toArray());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", HttpStatus.OK.value(), "message", "success", "record", record));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", HttpStatus.OK.value(), "message", "failed", "error", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Object> deleteRecord(Map<String, Object> record) {
        Object id=record.get("akid");
        if (!isRecordExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found to delete with id " + id);
        }

        String deleteQuery = "DELETE FROM pr_master_record_data_sources WHERE akid=" + id + ";";
        try {

            jdbcTemplate.execute(deleteQuery);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", HttpStatus.CREATED.value(), "message", "success"));
        } catch (DataAccessException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status",  HttpStatus.INTERNAL_SERVER_ERROR , "message", "failed", "error", e.getMessage()));
        }
    }


    private String getCurrentTime()
    {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTimeMillis);
        return sdf.format(date);
    }

    private boolean isRecordExists(Object id) {
        String sql = String.format("SELECT COUNT(*) FROM pr_master_record_data_sources WHERE akid=" + id);
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }
}
