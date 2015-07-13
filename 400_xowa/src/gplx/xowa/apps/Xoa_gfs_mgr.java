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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.gfs.*;
import gplx.xowa.users.*; import gplx.xowa.apps.fsys.*;
public class Xoa_gfs_mgr implements GfoInvkAble, GfoInvkRootWkr {
	private final GfoInvkAble root_invk; private final Xoa_fsys_mgr app_fsys_mgr; private final Xou_fsys_mgr usr_fsys_mgr;
	public Xoa_gfs_mgr(GfoInvkAble root_invk, Xoa_fsys_mgr app_fsys_mgr, Xou_fsys_mgr usr_fsys_mgr) {
		this.root_invk = root_invk; this.app_fsys_mgr = app_fsys_mgr; this.usr_fsys_mgr = usr_fsys_mgr;
		GfsCore._.AddCmd(root_invk, Xoae_app.Invk_app);
		GfsCore._.AddCmd(root_invk, Xoae_app.Invk_xowa);
	}
	public GfoInvkAble Root_invk() {return root_invk;}
	public Xoa_fsys_mgr App_fsys_mgr() {return app_fsys_mgr;}
	public Xoa_app_eval Eval_mgr() {return eval_mgr;} private final Xoa_app_eval eval_mgr = new Xoa_app_eval();
	private void Run_url_by_type(String type) {
		Io_url app_data_dir = usr_fsys_mgr.App_data_dir();
		Io_url url = null;
		if		(String_.Eq(type, "user_system_cfg"))	url = app_data_dir.GenSubFil_nest("cfg", "user_system_cfg.gfs");
		else if	(String_.Eq(type, "xowa_cfg_custom"))	url = usr_fsys_mgr.App_data_cfg_custom_fil();
		else if	(String_.Eq(type, "xowa_cfg_user"))		url = usr_fsys_mgr.App_data_cfg_user_fil();
		else if	(String_.Eq(type, "xowa_cfg_os"))		{url = app_fsys_mgr.Bin_data_os_cfg_fil(); Xoa_gfs_mgr_.Cfg_os_assert(url);}
		else if	(String_.Eq(type, "xowa_cfg_app"))		url = app_fsys_mgr.Root_dir().GenSubFil("xowa.gfs");
		else											throw Exc_.new_("invalid gfs type", "type", type);
		try {Run_url(url);}
		catch (Exception e) {				// gfs is corrupt; may happen if multiple XOWAs opened, and "Close all" chosen in OS; DATE:2014-07-01
			if	(!String_.Eq(type, "xowa"))			// check if user.gfs
				Io_mgr.I.MoveFil(url, url.GenNewNameOnly(url.NameOnly() + "-" + DateAdp_.Now().XtoStr_fmt_yyyyMMdd_HHmmss()));	// move file
			Gfo_usr_dlg_.I.Warn_many("", "", "invalid gfs; obsoleting: src=~{0} err=~{1}", url.Raw(), Err_.Message_gplx(e));
		}
	}
	public GfoMsg Parse_root_msg(String v) {return gplx.gfs.Gfs_msg_bldr._.ParseToMsg(v);}
	public Gfs_wtr Wtr() {return wtr;} private Gfs_wtr wtr = new Gfs_wtr();
	public void Run_url(Io_url url) {
		Run_url_for(GfsCore._.Root(), url);
		Gfo_usr_dlg_.I.Log_wkr().Log_to_session_fmt("gfs.done: ~{0}", url.Raw());
	}
	public void Run_url_for(GfoInvkAble invk, Io_url url) {
		String raw = Io_mgr.I.LoadFilStr_args(url).MissingIgnored_().Exec(); if (String_.Len_eq_0(raw)) return;
		Run_str_for(invk, raw);
	}
	public Object Run_str(String raw) {return Run_str_for(GfsCore._.Root(), raw);}
	public Object Run_str_for(GfoInvkAble invk, String raw) {return Run_str_for(invk, Parse_root_msg(raw));}
	public Object Run_str_for(GfoInvkAble invk, GfoMsg root_msg) {
		try {
			int sub_msgs_len = root_msg.Subs_count();
			GfsCtx ctx = GfsCtx.new_().Fail_if_unhandled_(Fail_if_unhandled).Usr_dlg_(Gfo_usr_dlg_.I);
			Object rv = null;
			for (int i = 0; i < sub_msgs_len; i++) {
				GfoMsg sub_msg = root_msg.Subs_getAt(i);
				rv = GfsCore._.ExecOne_to(ctx, invk, sub_msg);
			}
			return rv;
		} catch (Exception e) {
			Gfo_usr_dlg_.I.Warn_many("", "", "error while executing script: err=~{0}", Err_.Message_gplx(e));
			return GfoInvkAble_.Rv_error;
		}
	}
	public GfoEvObj Get_owner_as_event_obj(String cmd) {
		GfoMsg cur_msg = Parse_root_msg(cmd).Subs_getAt(0);	// ignore root_msg which is ""
		GfoInvkAble cur_invk = root_invk;
		while (true) {
			if (cur_msg.Subs_count() == 0) return (GfoEvObj)cur_invk;	// terminal msg; return cur_invk
			cur_invk = (GfoInvkAble)GfoInvkAble_.InvkCmd(cur_invk, cur_msg.Key());
			cur_msg = cur_msg.Subs_getAt(0);
		}
	}
	public byte[] Build_prop_set_to_bry(Bry_bfr bfr, byte[] prop, byte[] val) {Build_prop_set(bfr, prop, val); return bfr.Xto_bry_and_clear();}
	public void Build_prop_set(Bry_bfr bfr, byte[] prop, byte[] val) {
		fmtr_eval_set.Bld_bfr_many(bfr, prop, Xoa_gfs_mgr.Cfg_save_escape(val));
	}	private Bry_fmtr fmtr_eval_set = Bry_fmtr.new_("~{prop}_('~{val}');\n", "prop", "val");
	public byte[] Build_prop_get_to_bry(Bry_bfr bfr, byte[] prop) {Build_prop_get(bfr, prop); return bfr.Xto_bry_and_clear();}
	public void Build_prop_get(Bry_bfr bfr, byte[] prop) {
		fmtr_eval_get.Bld_bfr_many(bfr, prop);
	}	private Bry_fmtr fmtr_eval_get = Bry_fmtr.new_("~{prop};\n", "prop");
	static final String GRP_KEY = "Xoa_gfs_mgr";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run_file_by_type))		Run_url_by_type(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_fail_if_unhandled_))		{Fail_if_unhandled = m.ReadYn("v"); ctx.Fail_if_unhandled_(Fail_if_unhandled);}
		else if	(ctx.Match(k, Invk_txns))					{return GfoInvkAble_.Null;}	// FUTURE: handle version for upgrades
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_run_file_by_type = "run_file_by_type", Invk_fail_if_unhandled_ = "fail_if_unhandled_", Invk_txns = "txns";
	public static void Msg_parser_init() {
		GfsCore._.MsgParser_(gplx.gfs.Gfs_msg_bldr._);
	}
	public static byte[] Cfg_save_escape(String v) {return Cfg_save_escape(Bry_.new_u8(v));}
	public static byte[] Cfg_save_escape(byte[] v) {
		return Bry_finder.Find_fwd(v, Byte_ascii.Apos) == Bry_.NotFound ? v : Bry_.Replace(v, Bry_apos_1, Bry_apos_2);
	}	static final byte[] Bry_apos_1 = Bry_.new_a7("'"), Bry_apos_2 = Bry_.new_a7("''");
	public static String Build_code(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) Build_code_bfr.Add_byte(Byte_ascii.Dot);
			Build_code_bfr.Add_str(ary[i]);				
		}
		return Build_code_bfr.Xto_str_and_clear();
	}	static final Bry_bfr Build_code_bfr = Bry_bfr.new_();
	public static final String Cfg_user_file = "xowa_user_cfg.gfs", Cfg_user_custom_file = "user_custom_cfg.gfs", Cfg_os = "xowa_cfg_os.gfs";
	public static boolean Fail_if_unhandled = false;
}
class Xoa_gfs_mgr_ {
	public static void Cfg_os_assert(Io_url orig_url) {
		Io_url dflt_url = orig_url.GenNewNameOnly(orig_url.NameOnly() + "_default");
		if (!Io_mgr.I.ExistsFil(dflt_url)) return;	// no dflt
		if (!Io_mgr.I.ExistsFil(orig_url)) {
			Io_mgr.I.CopyFil(dflt_url, orig_url, true);
			Gfo_usr_dlg_.I.Log_many("", "", "xowa_cfg_os generated; url=~{0}", orig_url.Raw());
		}
	}
}
