package me.toolkit.java.util.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author wangdi0410@gmail.com
 */
public class JsonUtil<T extends Object> {
	

	public TypeToken< T > getGsonTypeToken() {
		TypeToken< T > type = new TypeToken< T >() {
		};
		return type;
	}

	public T fromJsonString(String jsonString) throws Exception{
		try {
			TypeToken< T > type = new TypeToken< T >() {
			};
			return (T)new Gson().fromJson( jsonString, type.getType() );
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new Exception( e.getMessage() );
		}
	}

}
