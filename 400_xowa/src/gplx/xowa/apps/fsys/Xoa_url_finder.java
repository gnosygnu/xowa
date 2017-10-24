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
import gplx.xowa.users.*;
public class Xoa_url_finder {
	private final    Xoa_fsys_mgr app_fsys_mgr;
	private Xou_fsys_mgr usr_fsys_mgr;
	public Xoa_url_finder(Xoa_fsys_mgr app_fsys_mgr) {
		this.app_fsys_mgr = app_fsys_mgr;
	}
	public void Init_by_user(Xou_fsys_mgr usr_fsys_mgr) {
		this.usr_fsys_mgr = usr_fsys_mgr;
	}
	public Io_url Find_by_css_or_app_bin(String wiki, String file, String[] app_dir_parts) {return Find_by_css_or(wiki, file, app_dir_parts, true);}
	public Io_url Find_by_css_or(String wiki, String file, String[] app_dir_parts, boolean app_bin_if_missing) {
		// check wiki_css dir; EX: /xowa/user/anonymous/wiki/en.wikipedia.org/html/logo.png
		Io_url usr_css_fil = usr_fsys_mgr.Wiki_html_dir(wiki).GenSubFil(file);
		if (Io_mgr.Instance.ExistsFil(usr_css_fil))
			return usr_css_fil;

		// check usr_bin dir; EX: /xowa/user/app/overrides/bin/any/xowa/html/css/nightmode/logo.png
		Io_url usr_bin_fil = usr_fsys_mgr.App_root_dir().GenSubDir("overrides").GenSubDir_nest(app_dir_parts).GenSubFil(file);
		if (Io_mgr.Instance.ExistsFil(usr_bin_fil))
			return usr_bin_fil;

		// check app_bin dir; EX: /xowa/bin/any/xowa/html/css/nightmode/logo.png
		Io_url app_bin_fil = app_fsys_mgr.Root_dir().GenSubDir_nest(app_dir_parts).GenSubFil(file);
		if (Io_mgr.Instance.ExistsFil(app_bin_fil))
			return app_bin_fil;

		// nothing found
		return app_bin_if_missing ? app_bin_fil : null;
	}
}
