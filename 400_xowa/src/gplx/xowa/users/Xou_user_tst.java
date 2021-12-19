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
package gplx.xowa.users;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import gplx.Yn;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoae_app;
import gplx.xowa.wikis.xwikis.Xow_xwiki_itm;
import gplx.xowa.wikis.xwikis.Xow_xwiki_mgr;
import org.junit.Test;
public class Xou_user_tst {
	private final Xou_user_fxt fxt = new Xou_user_fxt();
	@Test public void Available_from_fsys() {
		Io_mgr.Instance.CreateDir(fxt.App().Fsys_mgr().Wiki_dir().GenSubDir("en.wikipedia.org"));
		fxt.App().Usere().Available_from_fsys();
		fxt.Test_xwikis
		( fxt.Make_xwiki(BoolUtl.N, "home")
		, fxt.Make_xwiki(BoolUtl.Y, "en.wikipedia.org")	// available_from_fsys should mark as offline=y
		);
	}
}
class Xou_user_fxt {
	public Xoae_app App() {return app;} private Xoae_app app = Xoa_app_fxt.Make__app__edit();
	public String Make_xwiki(boolean offline, String name) {return StringUtl.ConcatWith("|", Yn.To_str(offline), name);}
	public void Test_xwikis(String... expd) {
		Xow_xwiki_mgr xwiki_mgr = app.Usere().Wiki().Xwiki_mgr();
		int len = xwiki_mgr.Len();
		String[] actl = new String[len];
		for (int i = 0; i < len; ++i) {
			Xow_xwiki_itm xwiki_itm = xwiki_mgr.Get_at(i);
			actl[i] = Make_xwiki(xwiki_itm.Offline(), StringUtl.NewU8(xwiki_itm.Domain_name()));
		}
            GfoTstr.EqLines(expd, actl);
	}
}
