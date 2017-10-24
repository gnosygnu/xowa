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
package gplx.xowa.addons.bldrs.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.*;
import gplx.langs.mustaches.*;
import gplx.xowa.wikis.pages.*;
public class Html__dump_to_fsys__cmd extends Xob_cmd__base {
	private Io_url template_url, fsys_root;
	private byte[] http_root, page_root;
	private boolean skip_unchanged = true;
	public Html__dump_to_fsys__cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		// init mustache
		Mustache_tkn_parser parser = new Mustache_tkn_parser();
		Mustache_tkn_itm root = parser.Parse(Io_mgr.Instance.LoadFilBry(template_url));
		Mustache_render_ctx ctx = new Mustache_render_ctx();
		Bry_bfr bfr = Bry_bfr_.New();
		Mustache_bfr mbfr = new Mustache_bfr(bfr);
		Html_page_itm page_itm = new Html_page_itm();

		// load rdr
		Xoh_wtr_ctx hctx = Xoh_wtr_ctx.File_dump(page_root, Bry_.new_a7(".html"));
		wiki.Init_assert();
		gplx.xowa.wikis.data.tbls.Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		Db_conn conn = page_tbl.Conn();
		Db_rdr rdr = conn.Exec_rdr("SELECT page_id, page_title, page_touched FROM page WHERE page_namespace = 0;");
		while (rdr.Move_next()) {
			String page_ttl_str = rdr.Read_str("page_title");
			try {
				// load page
				Xoa_ttl page_ttl = wiki.Ttl_parse(Bry_.new_u8(page_ttl_str));
				DateAdp page_modified_on = DateAdp_.parse_fmt(rdr.Read_str("page_touched"), gplx.xowa.wikis.data.tbls.Xowd_page_tbl.Page_touched_fmt);
				Io_url dump_fil_url = Io_url_.new_fil_(fsys_root.Gen_sub_path_for_os(page_ttl_str) + ".html");
				if (skip_unchanged && Io_mgr.Instance.QueryFil(dump_fil_url).ModifiedTime().Eq(page_modified_on)) continue;

				// parse page
				Xoae_page page = wiki.Data_mgr().Load_page_and_parse(wiki.Utl__url_parser().Parse(page_ttl.Page_db()), page_ttl);
				wiki.Parser_mgr().Parse(page, true);
				page.Wikie().Html_mgr().Page_wtr_mgr().Page_read_fmtr().Fmt_("~{page_data}");
				page.Wikie().Html_mgr().Page_wtr_mgr().Wkr(gplx.xowa.wikis.pages.Xopg_page_.Tid_read).Write_body(bfr, wiki.Parser_mgr().Ctx(), hctx, page);
				byte[] html_src = bfr.To_bry_and_clear();//page.Wikie().Html_mgr().Page_wtr_mgr().Gen(page, gplx.xowa.wikis.pages.Xopg_page_.Tid_read);	// NOTE: must use wiki of page, not of owner tab; DATE:2015-03-05
				byte[] html_head = page.Html_data().Custom_head_tags().To_html__style(bfr);

				// fmt with mustache; write to file
				page_itm.Init(http_root, page_root, page_ttl.Page_txt(), html_head, Bry_.Empty, html_src);
				root.Render(mbfr, ctx.Init(page_itm));
				Io_mgr.Instance.SaveFilBry(dump_fil_url, mbfr.To_bry_and_clear());
				Io_mgr.Instance.UpdateFilModifiedTime(dump_fil_url, page_modified_on);
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "err: ~{0}", Err_.Message_gplx_log(e));
			}
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__template_url_))			this.template_url = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__fsys_root_))			this.fsys_root = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__http_root_))			this.http_root = m.ReadBry("v");
		else if	(ctx.Match(k, Invk__page_root_))			this.page_root = m.ReadBry("v");
		else if	(ctx.Match(k, Invk__skip_unchanged_))		this.skip_unchanged = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__template_url_ = "template_url_", Invk__fsys_root_ = "fsys_root_"
		, Invk__http_root_ = "http_root_", Invk__page_root_ = "page_root_"
		, Invk__skip_unchanged_ = "skip_unchanged_";

	public static final String BLDR_CMD_KEY = "html.dump_to_file";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Html__dump_to_fsys__cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Html__dump_to_fsys__cmd(bldr, wiki);}
}
