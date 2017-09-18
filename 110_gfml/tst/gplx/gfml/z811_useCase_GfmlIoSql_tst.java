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
import org.junit.*;
public class z811_useCase_GfmlIoSql_tst {
	@Test  public void Basic() {
		tst_Doc("fld1=val1", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val1"));
		tst_Doc("fld1 = val1", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val1"));
		tst_Doc("fld1='val1'", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val1"));
		tst_Doc("fld1='val 1'", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val 1"));
	}
	@Test  public void Basic2() {
		tst_Doc("1=1 OR 2=2", nde_("fld").Atrs_add_("word", "1").Atrs_add_("op", "=").Atrs_add_("word", "1")
			.Atrs_add_("word", "OR")
			.Atrs_add_("word", "2").Atrs_add_("op", "=").Atrs_add_("word", "2")
			);
		tst_Doc("(1=1)", nde_("fld").Atrs_add_("op", "(").Atrs_add_("word", "1").Atrs_add_("op", "=").Atrs_add_("word", "1")
			.Atrs_add_("op", ")")
			);
	}
	void tst_Doc(String raw, GfmlNdeWrapper expd) {
		GfmlDoc doc = SqlDoc.XtoDoc(raw);
		GfmlNde actl = (GfmlNde)doc.RootNde();
		Tfds.Eq_ary_str(To_str(expd.Nde()), To_str(actl));
	}
	String[] To_str(GfmlNde nde) {
		List_adp list = List_adp_.New();
		for (int i = 0; i < nde.SubObjs_Count(); i++) {
			GfmlAtr atr = (GfmlAtr)nde.SubObjs_GetAt(i);
			list.Add(atr.Key() +  "=" + atr.DatTkn().Raw());
		}
		return list.To_str_ary();
	}
	GfmlNdeWrapper nde_(String name) {return GfmlNdeWrapper.new_().Name_(name);}
}
