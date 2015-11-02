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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.core.ios.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.bldrs.wms.dumps.*;
public class Xob_download_wkr extends Xob_itm_basic_base implements Xob_cmd {
	private String dump_date = "latest";
	private String dump_type = null;
	private String dump_src = null;
	private Io_url dump_trg_zip = null, dump_trg_bin = null;
	private boolean unzip = true;
	public Xob_download_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_util_download;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		if (dump_type == null) throw Err_.new_wo_type("dump_type must be specified");
		Xowm_dump_file dump_file = new Xowm_dump_file(wiki.Domain_str(), dump_date, dump_type);
		if (dump_src == null) {
			dump_file.Server_url_(app.Setup_mgr().Dump_mgr().Server_urls()[0]);
			dump_src = dump_file.File_url();
		}
		if (dump_trg_zip == null)
			dump_trg_zip = wiki.Fsys_mgr().Root_dir().GenSubFil(dump_file.File_name());
		if (dump_trg_bin == null && unzip)
			dump_trg_bin = dump_trg_zip.GenNewNameAndExt(dump_trg_zip.NameOnly());	// convert a.sql.gz -> a.sql
	}
	public void Cmd_run() {
		usr_dlg.Note_many("", "", "downloading file: now=~{0} src=~{1} trg=~{2}", DateAdp_.Now().XtoStr_fmt_yyyyMMdd_HHmmss(), dump_src, dump_trg_zip.OwnerDir());
		IoEngine_xrg_downloadFil download_wkr = app.Wmf_mgr().Download_wkr().Download_xrg();
		download_wkr.Src_last_modified_query_(false).Init(dump_src, dump_trg_zip);
		if (!download_wkr.Exec())
			usr_dlg.Warn_many("", "", "download failed: src=~{0} trg=~{1} err=~{2}", dump_src, dump_trg_zip.Raw(), Err_.Message_gplx_full(download_wkr.Rslt_err()));
		if (unzip) {
			usr_dlg.Note_many("", "", "unzipping file: now=~{0} trg=~{1}", DateAdp_.Now().XtoStr_fmt_yyyyMMdd_HHmmss(), dump_trg_bin.Raw());
			Xob_unzip_wkr unzip_wkr = new Xob_unzip_wkr().Init(app).Process_run_mode_(ProcessAdp.Run_mode_sync_block);
			unzip_wkr.Decompress(dump_trg_zip, dump_trg_bin);
		}
	}
	public void Cmd_end() {}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Invk_dump_date_))			dump_date = m.ReadStr("v");
		else if	(String_.Eq(k, Invk_dump_type_))			dump_type = m.ReadStr("v");
		else if	(String_.Eq(k, Invk_unzip_))				unzip = m.ReadYn("v");
		else if	(String_.Eq(k, Invk_dump_src_))				dump_src = m.ReadStr("v");
		else if	(String_.Eq(k, Invk_dump_trg_zip_))			dump_trg_zip = m.ReadIoUrl("v");
		else if	(String_.Eq(k, Invk_dump_trg_bin_))			dump_trg_bin = m.ReadIoUrl("v");
		else												return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_dump_date_ = "dump_date_", Invk_dump_type_ = "dump_type_", Invk_unzip_ = "unzip_"
	, Invk_dump_src_ = "dump_src_", Invk_dump_trg_zip_ = "dump_trg_zip_", Invk_dump_trg_bin_ = "dump_trg_bin_"
	;
}
