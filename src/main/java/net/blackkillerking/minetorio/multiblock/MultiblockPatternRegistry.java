package net.blackkillerking.minetorio.multiblock;

import java.util.HashMap;
import java.util.Map;

public class MultiblockPatternRegistry {
    private static final Map<String, MultiblockPattern> PATTERNS = new HashMap<>();

    public static void register(String id, MultiblockPattern pattern){
        PATTERNS.put(id, pattern);
    }

    public static MultiblockPattern get(String id){
        return PATTERNS.get(id);
    }
}
