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
class Xoa_site_cfg_loader__fallback implements Xoa_site_cfg_loader {
	public int			Tid() {return Xoa_site_cfg_loader_.Tid__fallback;}
	public void			Load_csv__bgn(Xoa_site_cfg_mgr mgr, Xow_wiki wiki) {}
	public byte[]		Load_csv(Xoa_site_cfg_mgr mgr, Xow_wiki wiki, Xoa_site_cfg_itm__base itm) {return Bry_.Empty;}	// return non-null so "data != null" check works
}
