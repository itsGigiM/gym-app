package com.example.taskspring.utils;

import java.util.UUID;

public class IDGenerator {
    public static long generate(){
        UUID uuid = UUID.randomUUID();
        long id = uuid.getMostSignificantBits();
        if (id < 0) return -id;
        return id;
    }
}
