package gplx.core.tooling.asserts;

import gplx.core.tests.Gftest;
import gplx.core.tooling.dataCollectors.GfoDataCollectorGrp;
import gplx.core.tooling.dataCollectors.GfoDataCollectorMgr;
import gplx.langs.java.util.List_;

import java.util.List;

public interface TestAssert {
    void Test(GfoDataCollectorGrp actlItm);
    void NotePrepend(String s);
    static void Test(String note, GfoDataCollectorMgr mgr, TestAssert.Grp[] itms) {
        for (TestAssert.Grp itm : itms) {
            itm.NotePrepend(note);
            itm.Test(mgr.GetGrp(itm.Key()));
        }
    }
    class Grp implements TestAssert {
        private String key;
        private TestAssert[] rules;
        public Grp(String key, TestAssert... rules) {
            this.key = key;
            this.rules = rules;
        }
        public String Key() {return key;}
        public void NotePrepend(String s) {
            note = s;
            for (TestAssert rule : rules) {
                rule.NotePrepend(s);
            }
        }   private String note;
        public void Test(GfoDataCollectorGrp grp) {
            for (TestAssert rule : rules) {
                rule.Test(grp);
            }
        }
    }
    class StringEq implements TestAssert {
        private String key;
        private String expdVal;
        public StringEq(String key, String expd) {
            this.key = key;
            this.expdVal = expd;
        }
        public void NotePrepend(String s) {note = s + "." + key;} private String note;
        public void Test(GfoDataCollectorGrp grp) {
            String actlVal = (String)grp.Get(key);
            Gftest.Eq__str(expdVal, actlVal, note);
        }
    }
    class StringHas implements TestAssert {
        private String key;
        private String expdVal;
        public StringHas(String key, String expd) {
            this.key = key;
            this.expdVal = expd;
        }
        public void NotePrepend(String s) {note = s + "." + key;} private String note;
        public void Test(GfoDataCollectorGrp grp) {
            String actlVal = (String)grp.Get(key);
            Gftest.Eq__bool_y(actlVal.contains(expdVal), note);
        }
    }
    class ListEq implements TestAssert {
        private String key;
        private List<?> expdList;
        public ListEq(String key, Object... expd) {
            this.key = key;
            this.expdList = List_.NewByAry(expd);
        }
        public void NotePrepend(String s) {note = s + "." + key;} private String note;
        public void Test(GfoDataCollectorGrp grp) {
            List<?> actlList = (List<?>)grp.Get(key);
            Gftest.Eq__ary(expdList.toArray(), actlList.toArray(), note);
        }
    }
}
