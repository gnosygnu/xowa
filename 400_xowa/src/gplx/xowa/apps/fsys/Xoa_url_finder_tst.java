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
package gplx.xowa.apps.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.users.*;
public class Xoa_url_finder_tst {
	private final    Xoa_url_finder_fxt fxt = new Xoa_url_finder_fxt();
	@Test  public void Find_by_css_or_null() {
		// init
		String wiki = "en.wikipedia.org";
		String file = "logo_night.png";
		String[] subs = String_.Ary("bin", "any", "xowa", "html", "css", "nightmode");
		String expd = null;

		// null case
		expd = null;
		fxt.Test__Find_by_css_or_null(expd, wiki, file, subs);

		// app_bin
		expd = "mem/xowa/bin/any/xowa/html/css/nightmode/logo_night.png";
		fxt.Init__Fsys__save(expd);
		fxt.Test__Find_by_css_or_null(expd, wiki, file, subs);

		// usr_bin
		expd = "mem/xowa/user/anonymous/app/overrides/bin/any/xowa/html/css/nightmode/logo_night.png";
		fxt.Init__Fsys__save(expd);
		fxt.Test__Find_by_css_or_null(expd, wiki, file, subs);

		// wiki_css
		expd = "mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/logo_night.png";
		fxt.Init__Fsys__save(expd);
		fxt.Test__Find_by_css_or_null(expd, wiki, file, subs);
	}
}
class Xoa_url_finder_fxt {
	private final    Xoa_url_finder finder;
	public Xoa_url_finder_fxt() {
		Io_url root_dir = Io_url_.mem_dir_("mem/xowa/");
		Xoa_fsys_mgr app_fsys_mgr = Xoa_fsys_mgr.New_by_plat("lnx", root_dir);
		Xou_fsys_mgr usr_fsys_mgr = new Xou_fsys_mgr(root_dir.GenSubDir_nest("user", "anonymous"));
		this.finder = new Xoa_url_finder(app_fsys_mgr);
		finder.Init_by_user(usr_fsys_mgr);
	}
	public void Init__Fsys__save(String url) {
		Io_mgr.Instance.SaveFilStr(url, "");
	}
	public void Test__Find_by_css_or_null(String expd, String wiki, String file, String[] dir_parts) {
		Io_url actl = finder.Find_by_css_or(wiki, file, dir_parts, false);
		Gftest.Eq__str(expd, actl == null ? null : actl.Raw());
	}
}
