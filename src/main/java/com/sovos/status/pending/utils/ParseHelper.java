package com.sovos.status.pending.utils;

import org.springframework.stereotype.Component;

@Component
public class ParseHelper {
	
	public boolean isPresent(Object obj) {
		
		if (obj != null && (obj.getClass() == String.class || obj.getClass() == Integer.class)) {
			return obj != null && !obj.toString().isEmpty() ? true : false;
		} else {
			return obj != null ? true : false;
		}
	}

}
