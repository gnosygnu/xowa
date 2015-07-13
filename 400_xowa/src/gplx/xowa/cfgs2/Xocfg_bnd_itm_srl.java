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
package gplx.xowa.cfgs2; import gplx.*; import gplx.xowa.*;
import gplx.gfui.*; import gplx.xowa.gui.bnds.*;
import gplx.gfs.*; import gplx.xowa.apps.*;
public class Xocfg_bnd_itm_srl implements GfoInvkAble {
	private Xoae_app app;
	public Xocfg_bnd_itm_srl(Xoae_app app, String key) {
		this.app = app;
		this.key = key;
	}
	public String	Key() {return key;} private String key;
	public int		Box() {return box;} private int box;
	public IptArg	Ipt() {return ipt;} private IptArg ipt;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_))			Src_(app, this, m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_box_))			box = Xog_bnd_box_.Xto_sys_int(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_ipt_))			ipt = IptArg_.parse_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_src_ = "src_", Invk_box_ = "box_", Invk_ipt_ = "ipt_";
	public static String Src(Xoae_app app, int box, IptArg ipt) {	// box_('browser').ipt_('mod.c+key.q');
		Gfs_wtr wtr = app.Gfs_mgr().Wtr();
		wtr.Add_set_eq(Key_box, Bry_.new_a7(Xog_bnd_box_.Xto_sys_str(box)));
		wtr.Add_set_eq(Key_ipt, Bry_.new_a7(ipt.Key()));
		return wtr.Bfr().Xto_str_and_clear();			
	}	private static final byte[] Key_box = Bry_.new_a7("box"), Key_ipt = Bry_.new_a7("ipt");
	public static void Src_(Xoae_app app, Xocfg_bnd_itm_srl itm, String v) {
		Xoa_gfs_mgr gfs_mgr = app.Gfs_mgr();
		gfs_mgr.Run_str_for(itm, v);
		Xog_bnd_itm bnd = app.Gui_mgr().Bnd_mgr().Get_or_null(itm.Key());
		if (bnd != null)	// should not happen, but guard against backward compatibility issues (deprecating old bindings)
			app.Gui_mgr().Bnd_mgr().Set(bnd, itm.Box(), itm.Ipt());
	}
}
