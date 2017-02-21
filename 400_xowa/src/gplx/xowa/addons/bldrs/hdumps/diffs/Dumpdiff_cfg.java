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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
class Dumpdiff_cfg implements Gfo_invk {
	public Io_url Prv_dir() {return prv_dir;} private Io_url prv_dir;
	public Io_url Cur_dir() {return cur_dir;} private Io_url cur_dir;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__cur_dir_))			this.cur_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__prv_dir_))			this.prv_dir = m.ReadIoUrl("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}		
	public static final String Invk__cur_dir_ = "cur_dir_", Invk__prv_dir_ = "prv_dir_";
}
