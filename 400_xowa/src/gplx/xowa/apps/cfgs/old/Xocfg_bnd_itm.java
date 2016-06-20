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
package gplx.xowa.apps.cfgs.old; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.cfgs.*;
import gplx.gfui.*; import gplx.xowa.guis.bnds.*; import gplx.xowa.guis.cmds.*;
import gplx.langs.gfs.*; import gplx.xowa.apps.*;
public class Xocfg_bnd_itm implements Gfo_invk {
	private Xocfg_bnd_mgr mgr;
	public Xocfg_bnd_itm(Xocfg_bnd_mgr mgr, Xog_cmd_itm cmd, Xog_bnd_itm bnd) {
		this.mgr = mgr; this.cmd = cmd; this.bnd = bnd;	
	}
	public Xog_bnd_itm Bnd() {return bnd;} private Xog_bnd_itm bnd;
	public Xog_cmd_itm Cmd() {return cmd;} private Xog_cmd_itm cmd;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_cmd_name))		return cmd.Name_or_missing();
		else if	(ctx.Match(k, Invk_cmd_tip))		return cmd.Tip_or_missing();
		else if	(ctx.Match(k, Invk_cmd_uid))		return cmd.Uid();
		else if	(ctx.Match(k, Invk_cmd_ctg_name))	return cmd.Ctg().Name();
		else if	(ctx.Match(k, Invk_cmd_ctg_sort))	return cmd.Ctg().Tid();
		else if	(ctx.Match(k, Invk_bnd_key))		return bnd.Key();
		else if	(ctx.Match(k, Invk_bnd_uid))		return bnd.Uid();
		else if	(ctx.Match(k, Invk_bnd_sys))		return Yn.To_str(bnd.Sys());
		else if	(ctx.Match(k, Invk_bnd_cmd))		return bnd.Cmd();
		else if	(ctx.Match(k, Invk_bnd_cmd_))		bnd.Cmd_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_bnd_box))		return Xog_bnd_box_.Xto_gui_str(bnd.Box());
		else if	(ctx.Match(k, Invk_bnd_box_idx))	return bnd.Box();
		else if	(ctx.Match(k, Invk_bnd_ipt))		return mgr.Bnd_mgr().Bnd_parser().Xto_norm(bnd.Ipt().Key());
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_cmd_uid = "cmd_uid"
	, Invk_cmd_name = "cmd_name"
	, Invk_cmd_tip = "cmd_tip"
	, Invk_cmd_ctg_name = "cmd_ctg_name"
	, Invk_cmd_ctg_sort = "cmd_ctg_sort"
	, Invk_bnd_key = "bnd_key"
	, Invk_bnd_uid = "bnd_uid"
	, Invk_bnd_sys = "bnd_sys"
	, Invk_bnd_box = "bnd_box"
	, Invk_bnd_box_idx = "bnd_box_idx"
	, Invk_bnd_ipt = "bnd_ipt"
	, Invk_bnd_cmd = "bnd_cmd", Invk_bnd_cmd_ = "bnd_cmd_"
	;
}
