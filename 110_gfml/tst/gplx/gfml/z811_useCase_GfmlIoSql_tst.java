/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z811_useCase_GfmlIoSql_tst {
	@Test public void Basic() {
		tst_Doc("fld1=val1", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val1"));
		tst_Doc("fld1 = val1", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val1"));
		tst_Doc("fld1='val1'", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val1"));
		tst_Doc("fld1='val 1'", nde_("fld").Atrs_add_("word", "fld1").Atrs_add_("op", "=").Atrs_add_("word", "val 1"));
	}
	@Test public void Basic2() {
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
		Tfds.Eq_ary_str(XtoStr(expd.Nde()), XtoStr(actl));
	}
	String[] XtoStr(GfmlNde nde) {
		ListAdp list = ListAdp_.new_();
		for (int i = 0; i < nde.SubObjs_Count(); i++) {
			GfmlAtr atr = (GfmlAtr)nde.SubObjs_GetAt(i);
			list.Add(atr.Key() +  "=" + atr.DatTkn().Raw());
		}
		return list.XtoStrAry();
	}
	GfmlNdeWrapper nde_(String name) {return GfmlNdeWrapper.new_().Name_(name);}
}
