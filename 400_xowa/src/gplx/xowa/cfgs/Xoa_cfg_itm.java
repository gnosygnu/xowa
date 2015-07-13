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
package gplx.xowa.cfgs; import gplx.*; import gplx.xowa.*;
public class Xoa_cfg_itm implements GfoInvkAble {
	public Xoa_cfg_itm(Xoa_cfg_grp grp, byte[] key) {this.grp = grp; this.key = key;}
	public Xoa_cfg_grp Grp() {return grp;} private Xoa_cfg_grp grp;
	public byte[] Key() {return key;} private byte[] key;
	public String Val() {return val;} private String val;
	public Xoa_cfg_itm Val_(String v) {
		val = v;
		if (grp.Notify(this)) {
			db_customized = true;
			db_dirty = true;
		}
		return this;
	} 
	public boolean Db_customized() {return db_customized;} public Xoa_cfg_itm Db_customized_(boolean v) {db_customized = v; return this;} private boolean db_customized;
	public boolean Db_dirty() {return db_dirty;} public Xoa_cfg_itm Db_dirty_(boolean v) {db_dirty = v; return this;} private boolean db_dirty;
	public void Clear() {
		grp = null;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_val)) 		return val;
		else if	(ctx.Match(k, Invk_val_)) 		Val_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_val = "val", Invk_val_ = "val_";
}
