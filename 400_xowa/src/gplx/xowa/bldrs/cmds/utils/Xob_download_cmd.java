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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.core.ios.*; import gplx.core.envs.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.wms.dumps.*;
public class Xob_download_cmd extends Xob_cmd__base implements Xob_cmd {
	private String dump_date = "latest", dump_type = null, dump_src = null;
	private Io_url dump_trg_zip = null, dump_trg_bin = null;
	private boolean unzip = true;
	public Xob_download_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Xob_download_cmd Dump_type_(String v) {dump_type = v; return this;}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_util_download;}
	@Override public void Cmd_run() {
		// init vars; if no explicit values, calc defaults;
		if (dump_type == null) throw Err_.new_("bldr", "dump_type must be specified");
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return;
		Xowm_dump_file dump_file = new Xowm_dump_file(wiki.Domain_str(), dump_date, dump_type);
		if (dump_src == null) {
			dump_file.Server_url_(gplx.xowa.bldrs.installs.Xoi_dump_mgr.Server_urls(app)[0]);
			dump_src = dump_file.File_url();
		}
		if (dump_trg_zip == null)
			dump_trg_zip = wiki.Fsys_mgr().Root_dir().GenSubFil(dump_file.File_name());
		if (dump_trg_bin == null && unzip)
			dump_trg_bin = dump_trg_zip.GenNewNameAndExt(dump_trg_zip.NameOnly());	// convert a.sql.gz -> a.sql

		// download
		usr_dlg.Note_many("", "", "downloading file: now=~{0} src=~{1} trg=~{2}", Datetime_now.Get().XtoStr_fmt_yyyyMMdd_HHmmss(), dump_src, dump_trg_zip.OwnerDir());
		IoEngine_xrg_downloadFil download_wkr = app.Wmf_mgr().Download_wkr().Download_xrg();
		download_wkr.Src_last_modified_query_(false).Init(dump_src, dump_trg_zip);
		if (!download_wkr.Exec())
			usr_dlg.Warn_many("", "", "download failed: src=~{0} trg=~{1} err=~{2}", dump_src, dump_trg_zip.Raw(), Err_.Message_gplx_full(download_wkr.Rslt_err()));
		if (unzip) {	// parsing unzipped file is faster, but takes up more storage space
			usr_dlg.Note_many("", "", "unzipping file: now=~{0} trg=~{1}", Datetime_now.Get().XtoStr_fmt_yyyyMMdd_HHmmss(), dump_trg_bin.Raw());
			Xob_unzip_wkr unzip_wkr = new Xob_unzip_wkr().Init(app).Process_run_mode_(Process_adp.Run_mode_sync_block);
			unzip_wkr.Decompress(dump_trg_zip, dump_trg_bin);
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Invk_dump_date_))			dump_date = m.ReadStr("v");
		else if	(String_.Eq(k, Invk_dump_type_))			dump_type = m.ReadStr("v");
		else if	(String_.Eq(k, Invk_dump_src_))				dump_src = m.ReadStr("v");
		else if	(String_.Eq(k, Invk_dump_trg_zip_))			dump_trg_zip = m.ReadIoUrl("v");
		else if	(String_.Eq(k, Invk_dump_trg_bin_))			dump_trg_bin = m.ReadIoUrl("v");
		else if	(String_.Eq(k, Invk_unzip_))				unzip = m.ReadYn("v");
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_dump_date_ = "dump_date_", Invk_dump_type_ = "dump_type_", Invk_unzip_ = "unzip_"
	, Invk_dump_src_ = "dump_src_", Invk_dump_trg_zip_ = "dump_trg_zip_", Invk_dump_trg_bin_ = "dump_trg_bin_";

	public static void Add_if_not_found_many(Xob_bldr bldr, Xowe_wiki wiki, String... dump_types) {
		IoItmHash itm_hash = Io_mgr.Instance.QueryDir_args(wiki.Fsys_mgr().Root_dir()).ExecAsItmHash();
		for (String dump_type : dump_types)
			Add_if_not_found(bldr, wiki, itm_hash, dump_type);
	}
	private static void Add_if_not_found(Xob_bldr bldr, Xowe_wiki wiki, IoItmHash itm_hash, String dump_type) {
		if (!Found(itm_hash, dump_type))
			bldr.Cmd_mgr().Add(new Xob_download_cmd(bldr, wiki).Dump_type_(dump_type));
	}
	private static boolean Found(IoItmHash hash, String dump_type) {
		String match = String_.Format("{0}.sql", dump_type); // EX: "page_props.sql"
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			IoItm_base fil = (IoItm_base)hash.Get_at(i);
			if (String_.Has(fil.Url().NameAndExt(), match))
				return true;
		}
		return false;
	}
}
