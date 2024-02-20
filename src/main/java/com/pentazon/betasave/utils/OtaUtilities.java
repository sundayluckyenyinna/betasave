package com.pentazon.betasave.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class OtaUtilities
{

    public static String generateUniqueReference(){
        String timeString = String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        String uuid = UUID.randomUUID().toString();
        return timeString.concat(uuid);
    }
}
