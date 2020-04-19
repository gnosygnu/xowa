package gplx.core.tooling.dataCollectors;

import java.util.LinkedHashMap;
import java.util.List;

public class GfoDataCollectorGrp {
    private final String key;
    private final LinkedHashMap<String, Object> hash = new LinkedHashMap<>();
    public GfoDataCollectorGrp(String key) {
        this.key = key;
    }
    public String Key() {return key;}
    public GfoDataCollectorGrp Add(String dataKey, String dataVal) {
        hash.put(dataKey, dataVal);
        return this;
    }
    public GfoDataCollectorGrp Add(String dataKey, List<?> dataVal) {
        hash.put(dataKey, dataVal);
        return this;
    }
    public Object Get(String dataKey) {
        return hash.get(dataKey);
    }
}
