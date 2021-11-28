package gplx.objects.lists;

import java.util.List;

public class ListUtil {
    public static byte[] ToArray(List<Byte> list) {
        int len = list.size();
        byte[] rv = new byte[len];
        for (int i = 0; i < len; i++) {
            rv[i] = list.get(i);
        }
        return rv;
   }
}
