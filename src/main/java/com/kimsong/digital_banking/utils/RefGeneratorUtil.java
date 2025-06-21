package com.kimsong.digital_banking.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class RefGeneratorUtil {

    public static String generateTransactionRef() {
        LocalDateTime now = LocalDateTime.now();

        String datePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        int random = ThreadLocalRandom.current().nextInt(100, 999);

        return String.format("TXN%s-%s-%d", datePart, timePart, random);
    }

}