/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfml; import gplx.*;
import org.junit.*; import gplx.core.stores.*;
public class z081_GfmlDataWtr_tst {
	@Before public void setup() {
		wtr = GfmlDataWtr.new_();
		wtr.WriteNodeBgn("root");
	}	DataWtr wtr;
	@Test  public void Basic() {
		tst_XtoStr(wtr, "root:;");
	}
	@Test  public void Atr_one() {
		wtr.WriteData("key", "data");;
		tst_XtoStr(wtr, "root:key='data';");
	}
	@Test  public void Atr_many() {
		wtr.WriteData("key1", "data1");
		wtr.WriteData("key2", "data2");
		tst_XtoStr(wtr, "root:key1='data1' key2='data2';");
	}
	@Test  public void Nde_one() {
		wtr.WriteNodeBgn("sub0");
		tst_XtoStr(wtr, "root:{sub0:;}");
	}
	@Test  public void Nde_many() {
		wtr.WriteNodeBgn("sub0");
		wtr.WriteNodeEnd();
		wtr.WriteNodeBgn("sub1");
		tst_XtoStr(wtr, "root:{sub0:;sub1:;}");
	}
	@Test  public void Nde_nested() {
		wtr.WriteNodeBgn("sub0");
		wtr.WriteNodeBgn("sub1");
		tst_XtoStr(wtr, "root:{sub0:{sub1:;}}");
	}
	@Test  public void OneAtrOneNde() {
		wtr.WriteData("key1", "data1");
		wtr.WriteNodeBgn("sub0");
		tst_XtoStr(wtr, "root:key1='data1'{sub0:;}");
	}
	@Test  public void OneAtrOneNdeOneAtr() {
		wtr.WriteData("key1", "data1");
		wtr.WriteNodeBgn("sub0");
		wtr.WriteData("key2", "data2");
		tst_XtoStr(wtr, "root:key1='data1'{sub0:key2='data2';}");
	}
	@Test  public void EscapeQuote() {
		wtr.WriteData("key", "data's");;
		tst_XtoStr(wtr, "root:key='data''s';");
	}
	void tst_XtoStr(DataWtr wtr, String expd) {
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);
	}
}
