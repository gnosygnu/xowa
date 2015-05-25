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
import gplx.xowa.apps.*;
public class Xoa_cfg_db_txt implements Xoa_cfg_db {
	private Bry_fmtr fmtr = Bry_fmtr.new_("app.cfgs.get('~{msg}', '~{wiki}').val = '~{val}';\n", "msg", "wiki", "val");
	public void Cfg_reset_all(Xoa_cfg_mgr cfg_mgr) {
		Io_url src_url = this.Cfg_url(cfg_mgr);
		Io_url trg_url = src_url.GenNewNameAndExt("xowa_user_cfg." + DateAdp_.Now().XtoStr_fmt_yyyyMMdd_HHmmss() + ".gfs");
		Io_mgr.I.MoveFil_args(src_url, trg_url, true).Exec();
		cfg_mgr.App().Gui_mgr().Kit().Ask_ok("", "", "Options cleared. Please restart XOWA.");
	}
	public void Cfg_load_run(Xoa_cfg_mgr cfg_mgr) {
		String load = Io_mgr.I.LoadFilStr(Cfg_url(cfg_mgr));
		cfg_mgr.App().Gfs_mgr().Run_str(load);
	}
	public void Cfg_save_bgn(Xoa_cfg_mgr cfg_mgr) {
		bfr.ClearAndReset();
	} 	private Bry_bfr bfr = Bry_bfr.new_();
	public void Cfg_save_end(Xoa_cfg_mgr cfg_mgr) {
		cfg_mgr.App().Usr_dlg().Log_many("", "", "shutting down app; saving cfg: len=~{0}", bfr.Len());
		Io_mgr.I.SaveFilBfr(Cfg_url(cfg_mgr), bfr);
	}
	public void Cfg_save_run(Xoa_cfg_mgr cfg_mgr, Xoa_cfg_grp cfg_grp, Xoa_cfg_itm cfg_itm) {
		fmtr.Bld_bfr_many(bfr, Xoa_gfs_mgr.Cfg_save_escape(cfg_grp.Key_bry()), Xoa_gfs_mgr.Cfg_save_escape(cfg_itm.Key()), Xoa_gfs_mgr.Cfg_save_escape(cfg_itm.Val()));
	}
	public Io_url Cfg_url(Xoa_cfg_mgr cfg_mgr) {return cfg_mgr.App().Usere().Fsys_mgr().App_data_cfg_dir().GenSubFil(File_name);}	
	public static final String File_name = "xowa_user_cfg.gfs";
}
