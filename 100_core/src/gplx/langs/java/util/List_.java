package gplx.langs.java.util;

import java.util.ArrayList;
import java.util.List;

public class List_ {
    public static List<Object> NewByAry(Object[] ary) {
        List<Object> rv = new ArrayList<>();
        for (Object o : ary)
            rv.add(o);
        return rv;
    }
}
