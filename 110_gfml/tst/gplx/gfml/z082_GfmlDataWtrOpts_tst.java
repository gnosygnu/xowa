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
public class z082_GfmlDataWtrOpts_tst {
	@Before public void setup() {
		wtr = GfmlDataWtr.new_();
		wtr.WriteNodeBgn("root");
	}	DataWtr wtr;
	@Test  public void KeyedSpr() {
		wtr.InitWtr(GfmlDataWtrOpts.Key_const, GfmlDataWtrOpts.new_().KeyedSeparator_("\t"));
		wtr.WriteData("key1", "data1");
		wtr.WriteData("key2", "data2");
		tst_XtoStr(wtr, "root:key1='data1'\tkey2='data2';");
	}
	@Test  public void IndentNamesOn() {
		wtr.InitWtr(GfmlDataWtrOpts.Key_const, GfmlDataWtrOpts.new_().IndentNodesOn_());
		wtr.WriteNodeBgn("nde1");
		wtr.WriteNodeBgn("nde2");
		wtr.WriteNodeEnd();
		tst_XtoStr(wtr, String_.Concat_lines_crlf
			(	"root:{"
			,	"	nde1:{"
			,	"		nde2:;"
			,	"	}"
			,	"}"
			));
	}
	@Test  public void IgnoreNullNamesOn() {
		wtr.InitWtr(GfmlDataWtrOpts.Key_const, GfmlDataWtrOpts.new_().IgnoreNullNamesOn_());
		wtr.WriteNodeBgn("");
		wtr.WriteData("key1", "data1");
		tst_XtoStr(wtr, String_.Concat("root:{key1='data1';}"));			
	}
	void tst_XtoStr(DataWtr wtr, String expd) {
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);
	}
}
