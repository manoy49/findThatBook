package com.testvagrant.booknamechallenge.findthatbook.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@Component
public class XmlParser {

    private ArrayList nestedKeys = new ArrayList();
    private ArrayList nestedKeyValues = new ArrayList();
    private ArrayList key = new ArrayList();
    private ArrayList values = new ArrayList();

    public JSONObject parseXmlToJson(String xml) {
        JSONObject jsonObject = XML.toJSONObject(xml);
        return jsonObject;
    }

    public HashMap getNestedJSONs(JSONObject jsonObject) {
        HashMap keyValue = new HashMap();
        nestedKeys.clear();
        nestedKeyValues.clear();
        jsonStore(jsonObject);
        for(int i =0; i<nestedKeys.size(); i++) {
            keyValue.put(nestedKeys.get(i), nestedKeyValues.get(i));
        }
//        System.out.println(keyValue.size());
        return keyValue;
    }

    public HashMap getNestedJSONs(String xml) {
        JSONObject jsonObject = parseXmlToJson(xml);
        return getNestedJSONs(jsonObject);
    }

    private void jsonStore(Object o)
    {
        if(checkWhetherJSONObject(o))
        {
            JSONObject jo = (JSONObject) o;
            Set<String> keys = jo.keySet();
            Iterator i = keys.iterator();
            while(i.hasNext())
            {
                String temp = (String) i.next();
                if(!(checkWhetherJSONObject(jo.get(temp)) || checkWhetherJSONArray(jo.get(temp))))
                {
                    key.add(temp);
                    values.add(jo.get(temp));
                }

                else
                {
                    nestedKeys.add(temp);
                    nestedKeyValues.add(jo.get(temp));
                    jsonStore(jo.get(temp));
                }
            }
        }
        else if(checkWhetherJSONArray(o)){
            JSONArray ja = (JSONArray) o;
            Iterator i = ja.iterator();
            while(i.hasNext())
            {
                Object o1 = i.next();
                jsonStore(o1);
            }
        }
    }

    private boolean checkWhetherJSONObject(Object object) {
        return object instanceof JSONObject;
    }

    private boolean checkWhetherJSONArray(Object object) {
        return object instanceof JSONArray;
    }
}
