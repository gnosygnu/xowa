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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.tests.*;	
import gplx.xowa.files.caches.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.makes.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.pages.lnkis.*;
public class Xoh_htxt_fxt {
	private final    Xowe_wiki wiki;
	private final    Xop_fxt parser_fxt = new Xop_fxt();
	private final    Xoh_page hpg = new Xoh_page();
	private final    Xoh_make_mgr make_mgr = Xoh_make_mgr.New_make();
	public Xoh_htxt_fxt() {
		this.wiki = parser_fxt.Wiki();
		Xoa_app_fxt.repo2_(parser_fxt.App(), wiki);	// needed else will be old "mem/wiki/repo/trg/thumb/" instead of standard "mem/file/en.wikipedia.org/thumb/"
		wiki.Html__hdump_mgr().Init_by_db(parser_fxt.Wiki());
		parser_fxt.Hctx_(Xoh_wtr_ctx.Hdump_by_hzip_tid(Xoh_hzip_dict_.Hzip__none));
		hpg.Ctor_by_hview(wiki, Xoa_url.blank(), parser_fxt.Wiki().Ttl_parse(Xoa_page_.Main_page_bry), 1);
	}
	public Xow_wiki Wiki() {return wiki;}
	public Xoa_page Page() {return hpg;}
	public void Clear() {hpg.Clear();}
	public void Test__decode(String htxt) {Test__decode(htxt, htxt);}
	public void Test__decode(String htxt, String html) {
		htxt = Gfh_utl.Replace_apos(htxt);
		html = Gfh_utl.Replace_apos(html);
		Test__decode__raw(htxt, html);
	}
	public void Test__decode__raw(String htxt, String expd) {
		hpg.Section_mgr().Clear();
		byte[] actl = make_mgr.Parse(Bry_.new_u8(htxt), hpg.Wiki(), hpg);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public void Test__hpg__redlinks(String... expd_ttls) {
		Xopg_lnki_list actl_list = hpg.Html_data().Redlink_list();
		int len = actl_list.Len();
		String[] actl_ttls = new String[len];
		for (int i = 0; i < len; i++) {
			Xopg_lnki_itm actl_itm = actl_list.Get_at(i);
			actl_ttls[i] = actl_itm.Ttl().Full_db_as_str();
		}
		Gftest.Eq__ary(expd_ttls, actl_ttls);
	}
	public static String Escape(String v) {return String_.Replace(v, "~", "");}
}
