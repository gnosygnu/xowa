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
import gplx.gfui.*;
import gplx.core.threads.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.utils.*;
import gplx.xowa.bldrs.wms.dumps.*;
class Xoi_cmd_wiki_download extends Gfo_thread_cmd_download implements Gfo_thread_cmd {	private Xoi_setup_mgr install_mgr; private String wiki_key, dump_date, dump_type;
	public Xoi_cmd_wiki_download Ctor_download_(Xoi_setup_mgr install_mgr, String wiki_key, String dump_date, String dump_type) {
		this.install_mgr = install_mgr;
		this.wiki_key = wiki_key;
		this.dump_date = dump_date;
		this.dump_type = dump_type;
		this.Owner_(install_mgr);
		return this;
	}
	@gplx.Virtual public String Download_file_ext() {return ".xml.bz2";}	// wiki.download is primarily used for dump files; default to .xml.bz2; NOTE: changed from ".xml"; DATE:2013-11-07
	@Override public String Async_key() {return Key_wiki_download;}  public static final String Key_wiki_download = "wiki.download";
	@Override public byte Async_init() {
		Xoae_app app = install_mgr.App();
		Xowm_dump_file dump_file = new Xowm_dump_file(wiki_key, dump_date, dump_type);
		String[] server_urls = gplx.xowa.bldrs.installs.Xoi_dump_mgr.Server_urls(app);
		boolean connected = Xowm_dump_file_.Connect_first(dump_file, server_urls);
		if (connected)
			app.Usr_dlg().Note_many("", "", "url: ~{0}", dump_file.File_url());
		else {
			if (!Dump_servers_offline_msg_shown) {
				app.Gui_mgr().Kit().Ask_ok("", "", "all dump servers are offline: ~{0}", String_.AryXtoStr(server_urls));
				Dump_servers_offline_msg_shown = true;
			}
		}
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(dump_file.Domain_itm().Domain_bry());
		Io_url root_dir = wiki.Fsys_mgr().Root_dir();
		Io_url[] trg_fil_ary = Io_mgr.Instance.QueryDir_args(root_dir).FilPath_("*." + dump_type + Download_file_ext() + "*").ExecAsUrlAry();
		Io_url trg = trg_fil_ary.length == 0 ? root_dir.GenSubFil(dump_file.File_name()) : trg_fil_ary[0];
		this.Ctor(app.Usr_dlg(), app.Gui_mgr().Kit());
		this.Init("download", dump_file.File_url(), trg);
		return super.Async_init();
	}
	private static boolean Dump_servers_offline_msg_shown = false;
}
