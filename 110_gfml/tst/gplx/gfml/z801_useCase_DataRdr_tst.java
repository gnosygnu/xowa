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
import gplx.core.stores.*;
public class z801_useCase_DataRdr_tst {
	String raw;
	@Test  public void Subs_byName() {
		raw = String_.Concat
			(	"_type:{"
			,	"	item {"
			,	"		point {"
			,	"			x;"
			,	"			y;"
			,	"		}"
			,	"	}"
			,	"}"
			,	"item:{"
			,	"	1 2;"
			,	"}"
			);
		rdr = GfmlDataRdr.raw_root_(raw);

		subRdr = rdr.Subs_byName_moveFirst("point");
		fx_rdr.tst_Atrs(subRdr, kv_("x", "1"), kv_("y", "2"));
	}
//		@Test 
	public void Subs_byName3() {
		raw = String_.Concat
			(	"_type:{"
			,	"	item {"
			,	"		key"
			,	"		point {"
			,	"			x;"
			,	"			y;"
			,	"		}"
			,	"	}"
			,	"}"
			,	"abc point=(1 2);"
			);
		rdr = GfmlDataRdr.raw_root_(raw);

		subRdr = rdr.Subs_byName_moveFirst("point");
		fx_rdr.tst_Atrs(subRdr, kv_("x", "1"), kv_("y", "2"));
	}
	Keyval kv_(String key, Object val) {return Keyval_.new_(key, val);}
	DataRdr_Fxt fx_rdr = DataRdr_Fxt.Instance;
	DataRdr rdr, subRdr;
}
class DataRdr_Fxt {
	@gplx.Internal protected DataRdr run_Subs_at(DataRdr rdr, int i) {
		DataRdr rv = rdr.Subs();
		int count = -1;
		while (count++ < i) {
			rv.MoveNextPeer();
		}
		return rv;
	}
	@gplx.Internal protected void tst_Atrs(DataRdr rdr, Keyval... expdAry) {
		Keyval[] actlAry = new Keyval[rdr.FieldCount()];
		for (int i = 0; i < actlAry.length; i++)
			actlAry[i] = rdr.KeyValAt(i);
		Tfds.Eq_ary_str(expdAry, actlAry);
	}
	public static final DataRdr_Fxt Instance = new DataRdr_Fxt(); DataRdr_Fxt() {}
}
