package com.hcmus.fit.customer_apps.utils;

import java.util.Map;

public class QueryUtil {
    public static String createQuery(String query, Map<String, String> params) {
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value != null) {
                query = query.replace("{" + key + "}", value);
            }
        }

        return query;
    }
}
