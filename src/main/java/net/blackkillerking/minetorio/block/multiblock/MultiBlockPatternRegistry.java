package net.blackkillerking.minetorio.block.multiblock;

import java.util.HashMap;
import java.util.Map;

public class MultiBlockPatternRegistry {
    private static final Map<String, MultiBlockPattern> PATTERNS = new HashMap<>();

    public static void register(String id, MultiBlockPattern pattern){
        PATTERNS.put(id, pattern);
    }

    public static MultiBlockPattern get(String id){
        return PATTERNS.get(id);
    }
}
