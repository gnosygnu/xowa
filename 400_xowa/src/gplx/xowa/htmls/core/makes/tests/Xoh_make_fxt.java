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
package gplx.xowa.htmls.core.makes.tests; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.makes.*;
import gplx.core.tests.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.htmls.sections.*;
import gplx.xowa.htmls.core.wkrs.lnkis.*;
public class Xoh_make_fxt {
	private final    Xowe_wiki wiki;
	private final    Xoh_make_mgr make_mgr;
	private final    Gfo_test_list_base expd_redlinks = new Gfo_test_list_base();
	private Xoh_page actl;
	public Xoh_make_fxt() {
		// set member reference
		this.wiki = parser_fxt.Wiki();
		this.make_mgr = wiki.Html__hdump_mgr().Load_mgr().Make_mgr();

		// init parser_fxt
		Xoa_app_fxt.repo2_(parser_fxt.App(), wiki);	// needed else will be old "mem/wiki/repo/trg/thumb/" instead of standard "mem/file/en.wikipedia.org/thumb/"
		wiki.Html__hdump_mgr().Init_by_db(wiki);
		wiki.Html_mgr().Html_wtr().Cfg().Lnki__id_(Bool_.Y).Lnki__title_(Bool_.Y);
	}
	public void Clear() {
		parser_fxt.Reset();
		page_chkr.Clear();
		expd_redlinks.Clear();
	}
	public Xoh_page_chkr Page_chkr() {return page_chkr;} private final    Xoh_page_chkr page_chkr = new Xoh_page_chkr();
	public Xop_fxt Parser_fxt() {return parser_fxt;} private final    Xop_fxt parser_fxt = new Xop_fxt();
	public Xou_cache_finder_mem Init__usr_cache(Xof_fsdb_itm... ary) {
		Xou_cache_finder_mem rv = Xou_cache_finder_.New_mem();
		parser_fxt.Wiki().Html__hdump_mgr().Load_mgr().Make_mgr().Hctx().Test__cache__mgr_(rv);
		for (Xof_fsdb_itm itm : ary)
			rv.Add(itm);
		return rv;
	}

	public Xof_fsdb_itm Init__fsdb_itm(String wiki_abrv, String lnki_ttl, int lnki_w, int lnki_h, int img_w, int img_h, String url) {
		return Init__fsdb_itm(wiki_abrv, lnki_ttl, Xop_lnki_type.Id_none, -1, lnki_w, lnki_h, img_w, img_h, -1, -1, Io_url_.mem_fil_(url));
	}
	public Xof_fsdb_itm Init__fsdb_itm(String wiki_abrv, String lnki_ttl, byte lnki_type, double lnki_upright, int lnki_w, int lnki_h, int img_w, int img_h, double lnki_time, int lnki_page, Io_url url) {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, Bry_.new_a7(wiki_abrv), Bry_.new_a7(lnki_ttl), lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page, 0);
		itm.Init_at_cache(true, img_w, img_h, url);
		return itm;
	}
	public void Expd__redlinks(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xopg_lnki_itm__hdump itm = new Xopg_lnki_itm__hdump(wiki.Ttl_parse(Bry_.new_u8(ary[i])));
			expd_redlinks.Add(itm);
		}
	}
	public void Test__html(String wtxt, String expd) {Test__html(wtxt, expd, true);}
	public void Test__html(String wtxt, String expd, boolean escape_apos) {
		if (escape_apos) expd = String_.Replace(expd, "'", "\"");
            String actl = parser_fxt.Exec__parse_to_hdump(wtxt);
		Tfds.Eq_str_lines(expd, actl);
	}
	public void Test__make(String html) {Test__make(true, html, null);}
	public void Test__make(String html, Xoh_page_chkr chkr) {Test__make(true, html, chkr);}
	public void Test__make(boolean print_to_console, String html, Xoh_page_chkr chkr) {
		html = String_.Replace(html, "'", "\"");

		// init hpg
		actl = new Xoh_page();
		actl.Ctor_by_hview(parser_fxt.Wiki(), Xoa_url.blank(), parser_fxt.Wiki().Ttl_parse(Xoa_page_.Main_page_bry), 1);

		// run make
		if (print_to_console) Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Test_console();
		byte[] actl_body = make_mgr.Parse(Bry_.new_u8(html), parser_fxt.Wiki(), actl);
		if (print_to_console) Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;

		// check html
		if (chkr != null) {
			actl.Db().Html().Html_bry_(actl_body);
			chkr.Check(actl);
		}

		// check redlinks
		expd_redlinks.Test(wiki.Html__hdump_mgr().Load_mgr().Make_mgr().Hctx().Page().Html_data().Redlink_list());
	}
	public void Exec__Fill_page() {
		wiki.Html__hdump_mgr().Load_mgr().Fill_page(parser_fxt.Page(), actl);
	}
}
