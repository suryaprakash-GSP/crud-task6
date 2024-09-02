package com.posidex.crud_task6.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAsJson<T, H> {

    private T data;
    private H status;
    private String message;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public H getStatus() {
		return status;
	}
	public void setStatus(H status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
