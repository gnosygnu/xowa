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
package gplx.xowa.addons.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.*;
import gplx.langs.mustaches.*;
public class Xobldr__deletion_db__temp extends Xob_cmd__base {
	private Io_url template_url, dump_root;
	public Xobldr__deletion_db__temp(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Mustache_tkn_parser parser = new Mustache_tkn_parser();
		Mustache_tkn_itm root = parser.Parse(Io_mgr.Instance.LoadFilBry(template_url));
		Mustache_render_ctx ctx = new Mustache_render_ctx();
		Bry_bfr bfr = Bry_bfr.new_();
		Mustache_bfr mbfr = new Mustache_bfr(bfr);
		Xo_page_dump_to_html page_itm = new Xo_page_dump_to_html();

		wiki.Init_assert();
		gplx.xowa.wikis.data.tbls.Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		Db_conn conn = page_tbl.conn;
		Db_rdr rdr = conn.Exec_rdr("SELECT page_id, page_title, page_touched FROM page WHERE page_namespace = 0;");
		Xoh_wtr_ctx hctx = Xoh_wtr_ctx.File_dump(Bry_.new_a7("/page/"), Bry_.new_a7(".html"));
		while (rdr.Move_next()) {
			String page_ttl = rdr.Read_str("page_title");
			try {
				Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(page_ttl));
				DateAdp page_modified_last = DateAdp_.parse_fmt(rdr.Read_str("page_touched"), gplx.xowa.wikis.data.tbls.Xowd_page_tbl.Page_touched_fmt);
				Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(wiki.Utl__url_parser().Parse(Bry_.new_u8(page_ttl)), ttl);
				wiki.Parser_mgr().Parse(page, true);
				page.Wikie().Html_mgr().Page_wtr_mgr().Page_read_fmtr().Fmt_("~{page_data}");
				page.Wikie().Html_mgr().Page_wtr_mgr().Wkr(gplx.xowa.wikis.pages.Xopg_page_.Tid_read).Write_body(bfr, hctx, page);
				byte[] html_src = bfr.To_bry_and_clear();//page.Wikie().Html_mgr().Page_wtr_mgr().Gen(page, gplx.xowa.wikis.pages.Xopg_page_.Tid_read);	// NOTE: must use wiki of page, not of owner tab; DATE:2015-03-05
				page_itm.Init(page_ttl, Bry_.Empty, Bry_.Empty, html_src);
				root.Render(mbfr, ctx.Init(page_itm));
				Io_url dump_fil_url = Io_url_.new_fil_(dump_root.Gen_sub_path_for_os(page_ttl) + ".html");
				if (Io_mgr.Instance.QueryFil(dump_fil_url).ModifiedTime().Eq(page_modified_last)) continue;
				Io_mgr.Instance.SaveFilBry(dump_fil_url, mbfr.To_bry_and_clear());
				Io_mgr.Instance.UpdateFilModifiedTime(dump_fil_url, page_modified_last);
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "err: ~{0}", Err_.Message_gplx_log(e));
			}
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__template_url_))			this.template_url = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__dump_root_))			this.dump_root = m.ReadIoUrl("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk__template_url_ = "template_url_", Invk__dump_root_ = "dump_root_";

	public static final String BLDR_CMD_KEY = "html.dump_to_file";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__deletion_db__temp(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__deletion_db__temp(bldr, wiki);}
}
class Xo_page_dump_to_html implements Mustache_doc_itm {
	private String page_title;
	private byte[] page_head_extra;
	private byte[] page_caption;
	private byte[] page_body;
	public Xo_page_dump_to_html Init(String page_title, byte[] page_head_extra, byte[] page_caption, byte[] page_body) {
		this.page_title = page_title;
		this.page_head_extra = page_head_extra;
		this.page_caption = page_caption;
		this.page_body = page_body;
		return this;
	}
	public boolean Mustache__write(String key, Mustache_bfr mbfr) {
		if		(String_.Eq(key, "page_title"))			mbfr.Add_str_u8(page_title);
		else if	(String_.Eq(key, "page_head_extra"))	mbfr.Add_bry(page_head_extra);
		else if	(String_.Eq(key, "page_caption"))		mbfr.Add_bry(page_caption);
		else if	(String_.Eq(key, "page_body"))			mbfr.Add_bry(page_body);
		else											return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}
}
