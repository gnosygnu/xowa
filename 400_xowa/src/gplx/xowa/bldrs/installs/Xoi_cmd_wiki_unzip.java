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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*;
import gplx.core.threads.*;
class Xoi_cmd_wiki_unzip extends Gfo_thread_cmd_unzip implements Gfo_thread_cmd {	public static final String KEY_dump = "wiki.unzip";
	public Xoi_cmd_wiki_unzip(Xoi_setup_mgr install_mgr, String wiki_key, String dump_date, String dump_type) {this.install_mgr = install_mgr; this.Owner_(install_mgr); this.wiki_key = wiki_key; this.dump_date = dump_date; this.dump_type = dump_type;} private Xoi_setup_mgr install_mgr; String wiki_key, dump_date, dump_type;
	@Override public String Async_key() {return KEY_dump;}
	@Override public byte Async_init() {
		Xoae_app app = install_mgr.App(); Gfui_kit kit = app.Gui_mgr().Kit();
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(Bry_.new_u8(wiki_key));
		Io_url wiki_dir = wiki.Import_cfg().Src_dir();
		Io_url[] urls = Io_mgr.Instance.QueryDir_args(wiki_dir).Recur_(false).FilPath_("*.xml.bz2").ExecAsUrlAry();
		if (urls.length == 0) {
			kit.Ask_ok(GRP_KEY, "dump.unzip_latest.file_missing", "Could not find a dump file for ~{0} in ~{1}", wiki_key, wiki_dir.Raw());
			return Gfo_thread_cmd_.Init_cancel_step;
		}
		Io_url src = urls[urls.length - 1];
		Io_url trg = app.Fsys_mgr().Wiki_dir().GenSubFil_nest(wiki_key, src.NameOnly());	// NOTE: NameOnly() will strip trailing .bz2; EX: a.xml.bz2 -> a.xml
		super.Init(app.Usr_dlg(), app.Gui_mgr().Kit(), app.Prog_mgr().App_decompress_bz2(), app.Prog_mgr().App_decompress_zip(), app.Prog_mgr().App_decompress_gz(), src, trg);
		this.Term_cmd_for_src_(Term_cmd_for_src_move);
		this.Term_cmd_for_src_url_(app.Fsys_mgr().Wiki_dir().GenSubFil_nest("#dump", "done", src.NameAndExt()));
		if (Io_mgr.Instance.ExistsFil(trg)) {
			int rslt = kit.Ask_yes_no_cancel(GRP_KEY, "target_exists", "Target file already exists: '~{0}'.\nDo you want to delete it?", trg.Raw());
			switch (rslt) {
				case Gfui_dlg_msg_.Btn_yes:		Io_mgr.Instance.DeleteFil(trg); break;
				case Gfui_dlg_msg_.Btn_no:		return Gfo_thread_cmd_.Init_cancel_step;
				case Gfui_dlg_msg_.Btn_cancel:	return Gfo_thread_cmd_.Init_cancel_all;
				default:						throw Err_.new_unhandled(rslt);
			}
		}
		return Gfo_thread_cmd_.Init_ok;
	}
	static final String GRP_KEY = "xowa.thread.dump.unzip";
}
