package com.posidex.crud_task6.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialDataModel {

    private Map<String, Object> appPropConfigKeyValues;

	public Map<String, Object> getAppPropConfigKeyValues() {
		return appPropConfigKeyValues;
	}

	public void setAppPropConfigKeyValues(Map<String, Object> appPropConfigKeyValues) {
		this.appPropConfigKeyValues = appPropConfigKeyValues;
	}
    
    

}
