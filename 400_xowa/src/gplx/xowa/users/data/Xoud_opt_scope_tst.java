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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import org.junit.*; import gplx.xowa.langs.*;
import gplx.xowa.wikis.domains.*;
public class Xoud_opt_scope_tst {
	private Xoud_opt_scope_fxt fxt = new Xoud_opt_scope_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Test_parse("en.w"		, fxt.Make(Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikipedia));
		fxt.Test_parse("en.*"		, fxt.Make(Xol_lang_stub_.Id_en, Xoud_opt_scope.Type_id_wildcard));
		fxt.Test_parse("*.w"		, fxt.Make(Xoud_opt_scope.Lang_id_wildcard, Xow_domain_tid_.Tid__wikipedia));
		fxt.Test_parse("<any>"		, Xoud_opt_scope.App);
		fxt.Test_parse("en.w,fr.d"	, fxt.Make(Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikipedia), fxt.Make(Xol_lang_stub_.Id_fr, Xow_domain_tid_.Tid__wiktionary));
	}
}
class Xoud_opt_scope_fxt {
	private final    Xoud_opt_scope_parser parser = new Xoud_opt_scope_parser();
	public void Clear() {
		// Gfo_usr_dlg_.I = Xoa_app_.New__usr_dlg__console();
	}
	public Xoud_opt_scope Make(int lang_id, int type_id) {return new Xoud_opt_scope(lang_id, type_id);}
	public void Test_parse(String raw, Xoud_opt_scope... expd) {
		Xoud_opt_scope[] actl = parser.Parse(Bry_.new_u8(raw));
		Tfds.Eq(To_str(expd), To_str(actl));
	}
	private static String To_str(Xoud_opt_scope[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_opt_scope itm = ary[i];
			if (i != 0) bfr.Add_str_a7(",");
			bfr.Add_str_a7(itm.To_str());
		}
		return bfr.To_str_and_clear();
	}
}
