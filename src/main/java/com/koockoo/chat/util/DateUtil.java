package com.koockoo.chat.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class DateUtil {
    private static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;
    private static final DateFormat df = new ISO8601DateFormat(); 
    
    public static String timeUUID2ISOString(UUID uuid) {
        long time = (uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000;
        return df.format(new Date(time));
    }
    

}
