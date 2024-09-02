package com.posidex.crud_task6;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {

    private Object records;
    private Object headers;
	public Object getRecords() {
		return records;
	}
	public void setRecords(Object records) {
		this.records = records;
	}
	public Object getHeaders() {
		return headers;
	}
	public void setHeaders(Object headers) {
		this.headers = headers;
	}
    
    



}
