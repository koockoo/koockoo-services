package com.koockoo.chat.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class DateUtil {
    private static final DateFormat df = new ISO8601DateFormat(); 
    
    public static String timeUUID2ISOString(UUID uuid) {
        long time = UUIDs.unixTimestamp(uuid);
        return df.format(new Date(time));
    }
    
}
