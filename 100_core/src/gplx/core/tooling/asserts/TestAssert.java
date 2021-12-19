/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.tooling.asserts;
import gplx.core.tooling.dataCollectors.GfoDataCollectorGrp;
import gplx.core.tooling.dataCollectors.GfoDataCollectorMgr;
import gplx.langs.java.util.List_;
import gplx.frameworks.tests.GfoTstr;
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
			GfoTstr.Eq(expdVal, actlVal, note);
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
			GfoTstr.EqBoolY(actlVal.contains(expdVal), note);
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
			GfoTstr.EqAryObjAry(expdList.toArray(), actlList.toArray(), note);
		}
	}
}
