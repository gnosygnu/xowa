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
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_type_cfg implements Gfo_invk {
	public Split_type_cfg(String key, int db_idx) {this.key = key; this.db_idx = db_idx;}
	public String			Key() {return key;} private final    String key;	// NOTE: used for layout
	public int				Db_idx() {return db_idx;} private int db_idx = 1000;
	public long				Db_max() {return db_max;} private long db_max = 1500 * Io_mgr.Len_mb;
	public String			Layout() {return layout;} private String layout;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__db_max_))			this.db_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk__db_idx_))			this.db_idx = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__layout_))			this.layout = m.ReadStr("v");
		else											return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__db_max_ = "db_max_", Invk__db_idx_ = "db_idx_", Invk__layout_ = "layout_";
}
