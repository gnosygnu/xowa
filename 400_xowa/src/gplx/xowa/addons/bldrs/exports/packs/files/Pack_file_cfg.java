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
package gplx.xowa.addons.bldrs.exports.packs.files;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.ios.IoConsts;
import gplx.types.commons.GfoDate;
import gplx.libs.files.Io_url;
public class Pack_file_cfg implements Gfo_invk {
	public Io_url Deploy_dir() {return deploy_dir;} private Io_url deploy_dir;
	public boolean Pack_text() {return pack_text;} private boolean pack_text = false;
	public boolean Pack_html() {return pack_html;} private boolean pack_html = true;
	public boolean Pack_file() {return pack_file;} private boolean pack_file = true;
	public boolean Pack_lucene() {return pack_lucene;} private boolean pack_lucene;
	public long Lucene_max() {return lucene_max;} private long lucene_max = IoConsts.LenMB * 1500;
	public boolean Pack_fsdb_delete() {return pack_fsdb_delete;} private boolean pack_fsdb_delete;
	public boolean Pack_custom() {return pack_custom_files != null;}
	public String Pack_custom_files() {return pack_custom_files;} private String pack_custom_files;
	public String Pack_custom_name() {return pack_custom_name;} private String pack_custom_name;
	public GfoDate Pack_file_cutoff() {return pack_file_cutoff;} private GfoDate pack_file_cutoff = null;
	public String Wiki_date(GfoDate wiki_last_modified) {
		return wiki_date == null ? wiki_last_modified.ToStrFmt("yyyy.MM") : wiki_date;
	}	private String wiki_date = null;

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, "deploy_dir_"))				deploy_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, "pack_text_"))				pack_text = m.ReadYn("v");
		else if	(ctx.Match(k, "pack_html_"))				pack_html = m.ReadYn("v");
		else if	(ctx.Match(k, "pack_file_"))				pack_file = m.ReadYn("v");
		else if	(ctx.Match(k, "pack_file_cutoff_"))			pack_file_cutoff = m.ReadDate("v");
		else if	(ctx.Match(k, "pack_fsdb_delete_"))			pack_fsdb_delete = m.ReadYn("v");
		else if	(ctx.Match(k, "pack_custom_name_"))			pack_custom_name = m.ReadStr("v");
		else if	(ctx.Match(k, "pack_custom_files_"))		pack_custom_files = m.ReadStr("v");	// pack_custom {files='en.wikipedia.org-core.xowa|en.wikipedia.org-html-ns.008.xowa'}}
		else if	(ctx.Match(k, "wiki_date_"))				wiki_date = m.ReadStr("v");
		else if	(ctx.Match(k, "pack_lucene_"))				pack_lucene = m.ReadYn("v");
		else if	(ctx.Match(k, "lucene_max_"))				lucene_max = m.ReadLong("v") * IoConsts.LenMB;
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}
}
