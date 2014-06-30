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
import gplx.gfui.*; import gplx.xowa.gui.bnds.*; import gplx.xowa.gui.cmds.*;
import gplx.xowa.fmtrs.*;
public class Xocfg_bnd_mgr implements GfoInvkAble, Gfo_sort_able {
	private Xog_bnd_mgr_srl bnd_mgr_srl; private Xog_cmd_mgr cmd_mgr;
	private Xoa_fmtr_sort_mgr sorter;
	public Xocfg_bnd_mgr(Xoa_app app) {
		this.app = app; this.bnd_mgr = app.Gui_mgr().Bnd_mgr(); this.cmd_mgr = app.Gui_mgr().Cmd_mgr();
		bnd_mgr_srl = new Xog_bnd_mgr_srl(app, bnd_mgr);
		sorter = new Xoa_fmtr_sort_mgr(this);
	}
	public Xoa_app App() {return app;} private Xoa_app app;
	public Xog_bnd_mgr Bnd_mgr() {return bnd_mgr;} private Xog_bnd_mgr bnd_mgr;
	private OrderedHash regy;
	public void Init() {
		regy = OrderedHash_.new_();
		int len = bnd_mgr.Len();
		for (int i = 0; i < len; i++) {
			Xog_bnd_itm bnd = bnd_mgr.Get_at(i);
			Xog_cmd_itm cmd = cmd_mgr.Get_or_null(bnd.Cmd()); if (cmd == null) throw Err_.unhandled(bnd.Cmd());
			Xocfg_bnd_itm cfg_itm = new Xocfg_bnd_itm(this, cmd, bnd);
			regy.Add(bnd.Key(), cfg_itm);
		}
	}
	private Xocfg_bnd_itm_srl Init(String key) {return new Xocfg_bnd_itm_srl(app, key);}
	public Xocfg_bnd_itm Get_at(int i)		{return (Xocfg_bnd_itm)regy.FetchAt(i);}
	public int Len() {return regy.Count();}
	public void Sort(gplx.lists.ComparerAble comparer) {regy.SortBy(comparer);}
	private void Set_bulk(byte[] src) {
		try {
			bnd_mgr_srl.Load_by_bry(src);
		}
		catch (Exception e) {	// catch errors, so that next cmd (which is page.reload) can still execute
			app.Usr_dlg().Warn_many("", "", "failed to set bnds; src=~{0} err=~{1}", String_.new_utf8_(src), Err_.Message_gplx_brief(e));
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Xoa_fmtr_itm.Invk_get_at))			return this.Get_at(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoa_fmtr_itm.Invk_len))				return this.Len();
		else if	(ctx.Match(k, Xoa_fmtr_itm.Invk_sorter))			return sorter;
		else if	(ctx.Match(k, Invk_set_bulk))						Set_bulk(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_init))							return Init(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_set_bulk = "set_bulk", Invk_init = "init";
}
