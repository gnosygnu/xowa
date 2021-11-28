package gplx.objects.lists;

import gplx.tests.Gftest_fxt;
import org.junit.Test;

public class GfoIndexedListEntryTest {
    private GfoIndexedList<String, String> list = new GfoIndexedList<>();
    @Test public void Add() {
        list.Add("A", "a");
        list.Add("B", "b");
        list.Add("C", "c");

        testGetAt(0, "a");
        testGetAt(1, "b");
        testGetAt(2, "c");
        testGetByOrFail("A", "a");
        testGetByOrFail("B", "b");
        testGetByOrFail("C", "c");
        testIterate("a", "b", "c");
    }
    @Test public void DelBy() {
        list.Add("A", "a");
        list.Add("B", "b");
        list.Add("C", "c");

        list.DelBy("A");

        testIterate("b", "c");

        list.DelBy("B");

        testIterate("c");

        list.DelBy("C");

        testIterate();
    }
    @Test public void DelBy_SameVal() {
        list.Add("A", "a");
        list.Add("B", "b");
        list.Add("C", "a");

        list.DelBy("C");

        testIterate("a", "b"); // fails if "b", "a"
    }
    @Test public void Set() {
        list.Add("A", "a");
        list.Add("B", "b");
        list.Add("C", "c");

        list.Set("B", "bb");
        testGetByOrFail("B", "bb");
        testIterate("a", "bb", "c");
    }
    private void testGetByOrFail(String key, String expd) {
        Gftest_fxt.Eq__str(expd, list.GetByOrFail(key));
    }
    private void testGetAt(int idx, String expd) {
        Gftest_fxt.Eq__str(expd, list.GetAt(idx));
    }
    private void testIterate(String... expd) {
        String[] actl = new String[expd.length];
        int i = 0;
        for (String itm : list) {
            actl[i++] = itm;
        }
        Gftest_fxt.Eq__ary(expd, actl);
    }
}