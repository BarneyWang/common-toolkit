package me.toolkit.java.util;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;
import me.toolkit.java.constant.EmptyObjectConstant;
import me.toolkit.java.constant.SymbolConstant;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

import static me.toolkit.java.constant.EmptyObjectConstant.EMPTY_STRING;

/**
 * Created by wangdi5 on 2015/7/27.
 */
public class JsonUtil {
    /**
     *
     */
    public static JSONObject paraseFromStringWithOutException(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject paraseFromString(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }

    /**
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static String getStringFromJSONObjectWithOutException(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            return EMPTY_STRING;
        }
    }

    /**
     *
     * @param jsonObject
     * @param key
     * @return
     * @throws JSONException
     */
    public static String getStringFromJSONObject(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.getString(key);
    }


    /**

     */
    public static String convertVO2String(Object object) throws Exception {
        try {
            return JSONMapper.toJSON(object).render(false);
        } catch (MapperException e) {
            throw new Exception("occur an exception when parse object :"+object, e);
        }
    }

    /**
     *
     * @param jsonStr
     * @param destClass
     * @return
     * @throws Exception
     */
    public static Object convertString2VO(String jsonStr, Class<?> destClass) throws Exception {
        try {
            // 先解释字符串为一个JSONValue
            JSONValue value = new JSONParser(new StringReader(jsonStr)).nextValue();
            return JSONMapper.toJava(value, destClass);
        } catch (Exception e) {
            throw new Exception("occur an exception when parse object :"+jsonStr+"to class"+ destClass, e);
        }
    }

    /**
     * check json string, and clean ext char, return like: []
     *
     * @param originalStr
     */
    public static String checkJsonContent(String originalStr) {

        if (StringUtil.isBlank(originalStr))
            return EmptyObjectConstant.EMPTY_STRING;

        //if [ and ] is in right order
        if (-1 == originalStr.indexOf(SymbolConstant.SQUARE_BRACKETS_LEFT) ||

                -1 == originalStr.indexOf(SymbolConstant.SQUARE_BRACKETS_RIGHT) || originalStr.indexOf(SymbolConstant.SQUARE_BRACKETS_LEFT) >= originalStr.indexOf(SymbolConstant.SQUARE_BRACKETS_RIGHT))
            return EmptyObjectConstant.EMPTY_STRING;

        originalStr = originalStr.substring(originalStr.indexOf(SymbolConstant.SQUARE_BRACKETS_LEFT), originalStr.lastIndexOf(SymbolConstant.SQUARE_BRACKETS_RIGHT) + 1);
        return originalStr;
    }
}
