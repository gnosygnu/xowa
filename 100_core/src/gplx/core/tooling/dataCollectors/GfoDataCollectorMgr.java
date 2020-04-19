package gplx.core.tooling.dataCollectors;

import java.util.LinkedHashMap;

public class GfoDataCollectorMgr {
    private final LinkedHashMap<String, GfoDataCollectorGrp> hash = new LinkedHashMap<>();
    public GfoDataCollectorGrp GetGrp(String grpKey) {return hash.get(grpKey);}
    public GfoDataCollectorGrp AddGrp(String grpKey) {
        GfoDataCollectorGrp grp = new GfoDataCollectorGrp(grpKey);
        hash.put(grpKey, grp);
        return grp;
    }
}
