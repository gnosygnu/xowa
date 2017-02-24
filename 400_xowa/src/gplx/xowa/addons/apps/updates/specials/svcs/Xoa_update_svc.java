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
package gplx.xowa.addons.apps.updates.specials.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*; import gplx.xowa.addons.apps.updates.specials.*;
import gplx.xowa.guis.cbks.*;
import gplx.core.gfobjs.*;
import gplx.xowa.addons.apps.updates.dbs.*; import gplx.xowa.addons.apps.updates.js.*;
import gplx.xowa.addons.apps.updates.apps.*;
import gplx.core.envs.*;
class Xoa_update_svc implements Gfo_invk {
	private Xoa_app app;
	private Io_url app_root_dir, update_dir, update_jar_fil, version_root;
	private Xoa_app_version_itm version_itm;
	private Xoa_update_db_mgr db_mgr;
	public Xoa_update_svc(Xoa_app app) {this.app = app;}
	private Xoa_update_db_mgr Init_db() {
		this.app_root_dir = app.Fsys_mgr().Root_dir();
		this.update_dir = app_root_dir.GenSubDir_nest("user", "install", "update");
		Io_url update_db_fil = update_dir.GenSubFil_nest("xoa_update.sqlite3");
		return new Xoa_update_db_mgr(update_db_fil);
	}
	public void Install(String version_name) {
		// get app_version from db
		this.db_mgr = Init_db();
		this.version_itm = db_mgr.Tbl__app_version().Select_by_name_or_null(version_name);

		// get src, trg, etc..
		String src = version_itm.Package_url();
		Io_url trg = update_dir.GenSubFil_nest("temp", version_itm.Name(), version_itm.Name() + ".zip");	// EX: "/xowa/user/app/install/update/temp/4.1.0/4.1.0.zip"
		this.version_root = trg.OwnerDir();
		Io_mgr.Instance.DeleteDirDeep(version_root);
		long src_len = -1;

		// start download
		Xojs_wkr__download download_wkr = new Xojs_wkr__download
		( app.Gui__cbk_mgr(), Xog_cbk_trg.New(Xoa_update_special.Prototype.Special__meta().Ttl_bry())
		, "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__download_done), Gfo_invk_cmd.New_by_key(this, Invk__download_fail), src, trg, src_len);
		download_wkr.Exec_async("app_updater");
	}
	public void Skip(String version_name) {
		Xoa_update_db_mgr db_mgr = Init_db();
		Xoa_update_startup.Version_cutoff_(app, db_mgr.Tbl__app_version().Select_by_name_or_null(version_name).Id());
	}
	private void On_download_done(GfoMsg m) {
		Xojs_wkr__download download_wkr = (Xojs_wkr__download)m.ReadObj("v");
		Io_url src = download_wkr.Trg();
		Io_url trg = src.OwnerDir().GenSubDir(src.NameOnly() + "_unzip");
		Xojs_wkr__unzip unzip_wkr = new Xojs_wkr__unzip(download_wkr.Cbk_mgr(), download_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__unzip_done), src, trg, -1);
		unzip_wkr.Exec_async("app_updater");
	}
	private void On_download_fail(GfoMsg m) {
		// in case of bad urls or withdrawn patches, delete update_db and force redownload
		db_mgr.Conn().Rls_conn();
		Io_mgr.Instance.DeleteFil(db_mgr.Url());
		Xoa_update_db_mgr_.Download_from_inet(app, Bool_.N, db_mgr.Url());
	}
	private void On_unzip_done(GfoMsg m) {
		Xojs_wkr__unzip unzip_wkr = (Xojs_wkr__unzip)m.ReadObj("v");
		Io_url src = unzip_wkr.Trg();
		Io_url trg = app_root_dir;

		// delete zip
		Io_mgr.Instance.DeleteFil(unzip_wkr.Src());

		// copy update_jar
		Io_url src_jar_fil = src.GenSubFil_nest("bin", "any", "xowa", "addon", "app", "update", "xoa_update.jar");
		this.update_jar_fil = app_root_dir.GenSubFil_nest("user", "install", "update", "xoa_update.jar");
		Io_mgr.Instance.MoveFil_args(src_jar_fil, update_jar_fil, true).Exec();

		// run replace_wkr
		Xojs_wkr__replace replace_wkr = new Xojs_wkr__replace(unzip_wkr.Cbk_mgr(), unzip_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__replace_done), src, trg);
		replace_wkr.Exec_async("app_updater");
	}
	private void On_replace_done(GfoMsg m) {
		Xojs_wkr__replace replace_wkr = (Xojs_wkr__replace)m.ReadObj("v");

		// get failed
		Xoa_manifest_list list = new Xoa_manifest_list();
		Keyval[] failed_ary = replace_wkr.Failed();
		int len = failed_ary.length;
		for (int i = 0; i < len; i++) {
			Keyval failed = failed_ary[i];
			list.Add(Io_url_.new_fil_(failed.Key()), Io_url_.new_fil_(failed.Val_to_str_or_empty()));
		}

		// write failed
		Bry_bfr bfr = Bry_bfr_.New();
		String app_update_cfg = app.Cfg().Get_str_app_or("xowa.app.update.restart_cmd", "");	// CFG:Cfg__
		bfr.Add_str_u8(App__update__restart_cmd(app_update_cfg, Env_.AppUrl(), Op_sys.Cur().Tid(), Op_sys.Cur().Bitness()) + "\n");
		list.Save(bfr);
		Io_url manifest_url = update_dir.GenSubFil("xoa_update_manifest.txt");
		Io_mgr.Instance.SaveFilBfr(manifest_url, bfr);

		// update prog as finished
		replace_wkr.Cbk_mgr().Send_json(replace_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfobj_nde.New().Add_bool("done", true));
		Xoa_update_startup.Version_cutoff_(app, version_itm.Id());

		// run standalone app
		Runtime_.Exec("java -jar " + update_jar_fil.Raw()+ " " + manifest_url.Raw() + " " + version_root.Raw());
		System_.Exit();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__download_done))		On_download_done(m);
		else if	(ctx.Match(k, Invk__download_fail))		On_download_fail(m);
		else if	(ctx.Match(k, Invk__unzip_done))		On_unzip_done(m);
		else if	(ctx.Match(k, Invk__replace_done))		On_replace_done(m);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__download_done = "download_done", Invk__download_fail = "download_fail", Invk__unzip_done = "unzip_done", Invk__replace_done = "replace_done";
	public static String App__update__restart_cmd(String current, Io_url app_url, byte op_sys_tid, byte bitness) {
		String rv = current;
		if (!String_.Eq(rv, String_.Empty)) return rv; // something specified; return it
		String bitness_str = bitness == Op_sys.Bitness_32 ? "" : "_64";
		Io_url root_dir = app_url.OwnerDir();

		String prepend_sh = op_sys_tid == Op_sys.Tid_wnt ? "" : "sh ";
		String file_fmt = "";
		switch (op_sys_tid) {
			case Op_sys.Tid_lnx:
				file_fmt = "xowa_linux{0}.sh";
				break;
			case Op_sys.Tid_osx:
				file_fmt = "xowa_macosx{0}";
				break;
			case Op_sys.Tid_wnt:
				file_fmt = "xowa{0}.exe";
				break;
			default:
				throw Err_.new_unhandled_default(op_sys_tid);
		}
		return prepend_sh + root_dir.GenSubFil(String_.Format(file_fmt, bitness_str)).Raw();
	}
}
