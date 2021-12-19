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
package gplx.xowa.addons.bldrs.exports.splits.mgrs;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.ios.IoConsts;
public class Split_type_cfg implements Gfo_invk {
	public Split_type_cfg(String key, int db_idx) {this.key = key; this.db_idx = db_idx;}
	public String			Key() {return key;} private final String key;	// NOTE: used for layout
	public int				Db_idx() {return db_idx;} private int db_idx = 1000;
	public long				Db_max() {return db_max;} private long db_max = 1500 * IoConsts.LenMB;
	public String			Layout() {return layout;} private String layout;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__db_max_))			this.db_max = m.ReadLong("v") * IoConsts.LenMB;
		else if	(ctx.Match(k, Invk__db_idx_))			this.db_idx = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__layout_))			this.layout = m.ReadStr("v");
		else											return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__db_max_ = "db_max_", Invk__db_idx_ = "db_idx_", Invk__layout_ = "layout_";
}
