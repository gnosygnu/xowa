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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public interface Xoa_site_cfg_loader {
	int			Tid();
	void		Load_csv__bgn(Xoa_site_cfg_mgr mgr, Xow_wiki wiki);
	byte[]		Load_csv(Xoa_site_cfg_mgr mgr, Xow_wiki wiki, Xoa_site_cfg_itm__base itm);
}
class Xoa_site_cfg_loader_ {
	public static final int Tid__null = -1, Tid__cfg = 0, Tid__fsys = 1, Tid__inet = 2, Tid__fallback = 3;
	private static final String Key__cfg = "cfg", Key__fsys = "fsys", Key__inet = "inet", Key__fallback = "itm";
	public static String Get_key(int tid) {
		switch (tid) {
			case Tid__cfg:			return Key__cfg;
			case Tid__fsys:			return Key__fsys;
			case Tid__inet:			return Key__inet;
			case Tid__fallback:		return Key__fallback;
			default:				throw Err_.new_unhandled(tid);
		}
	}
}
