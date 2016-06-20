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
package gplx.xowa.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_cfg_itm implements Gfo_invk {
	public Xoa_cfg_itm(Xoa_cfg_grp grp, byte[] key) {this.grp = grp; this.key = key;}
	public Xoa_cfg_grp Grp() {return grp;} private final    Xoa_cfg_grp grp;
	public byte[] Key() {return key;} private final    byte[] key;
	public String Val() {return val;} private String val;
	public boolean Val_is_dirty() {return val_is_dirty;} private boolean val_is_dirty;
	public boolean Val_is_customized() {return val_is_customized;} private boolean val_is_customized;	// false if value is system default; true if changed by user
	public void Val_(String v) {
		this.val = v;
		if (grp.Notify(this)) {
			val_is_customized = true;
			val_is_dirty = true;
		}
	} 
	public void Val_load_done() {
		val_is_dirty = false;
		val_is_customized = true;
	} 
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_val)) 		return val;
		else if	(ctx.Match(k, Invk_val_)) 		Val_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_val = "val", Invk_val_ = "val_";
}
