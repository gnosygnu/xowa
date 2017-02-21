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
package gplx.xowa.addons.wikis.searchs.specials.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.specials.*;
import org.junit.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.guis.cbks.js.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
public class Srch_rslt_cbk_tst {
	@Before public void init() {fxt.Clear();} private Srch_rslt_cbk_fxt fxt = new Srch_rslt_cbk_fxt();
	@Test   public void Basic() {			
		fxt.Test_add(fxt.Make_rslt(50, "L"), fxt.Make_args_append("xowa_insert_w"	, "w.7CL"));	// insert new
		fxt.Test_add(fxt.Make_rslt(30, "N"), fxt.Make_args_append("xowa_insert_w"	, "w.7CN"));	// insert below last
		fxt.Test_add(fxt.Make_rslt(70, "J"), fxt.Make_args_append("w.7CL"			, "w.7CJ"));	// insert above first
		fxt.Test_add(fxt.Make_rslt(60, "K"), fxt.Make_args_append("w.7CL"			, "w.7CK"));	// insert above mid
		fxt.Test_add(fxt.Make_rslt(40, "M"), fxt.Make_args_append("w.7CN"			, "w.7CM"));	// insert below mid
		fxt.Test_add(fxt.Make_rslt(10, "P"));														// insert noop			
		fxt.Test_add(fxt.Make_rslt(80, "I"), fxt.Make_args_append("w.7CJ"			, "w.7CI") , fxt.Make_args_replace("w.7CN"));	// insert displace all
		fxt.Test_add(fxt.Make_rslt(61, "K1"), fxt.Make_args_append("w.7CK"			, "w.7CK1"), fxt.Make_args_replace("w.7CM"));	// insert displace mid
	}
}
class Srch_rslt_cbk_fxt {
	private Srch_html_row_bldr html_row; private static final    byte[] Bry_enwiki = Bry_.new_a7("w");
	private Srch_html_row_wkr async;
	private Xog_js_wkr__log js_wkr = new Xog_js_wkr__log();
	private Xowe_wiki wiki;
	private int page_id;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app, "w");
		html_row = new Srch_html_row_bldr(wiki.Html__lnki_bldr());
		html_row.Fmtr().Fmt_("~{page_key}");
		async = new Srch_html_row_wkr(html_row, js_wkr, 5, Bry_enwiki);
		page_id = 0;
	}
	public Srch_rslt_row Make_rslt(int len, String ttl) {
		byte[] ttl_bry = Bry_.new_a7(ttl);
		++page_id;
		byte[] key = Bry_.Add(Bry_enwiki, Byte_ascii.Pipe_bry, ttl_bry);	// NOTE: deliberately changing key to use ttl instead of id to make tests more readable
		return new Srch_rslt_row(key, Bry_enwiki, wiki.Ttl_parse(ttl_bry), gplx.xowa.wikis.nss.Xow_ns_.Tid__main, ttl_bry, page_id, len, len, Srch_rslt_row.Page_redirect_id_null);
	}
	public Object[] Make_args_append(String uid, String html)	{return Object_.Ary(Xog_js_wkr__log.Proc_append_above, uid, html);}
	public Object[] Make_args_replace(String uid)				{return Object_.Ary(Xog_js_wkr__log.Proc_replace_html, uid, "");}
	public void Test_add(Srch_rslt_row row, Object[]... expd) {
		async.On_rslt_found(row);
		int expd_len = expd.length;
		Tfds.Eq(expd_len, js_wkr.Log__len());
		for (int i = 0; i < expd_len; ++i) {
			String expd_str = String_.Concat_with_obj("\n", expd[i]);
			String actl_str = String_.Concat_with_obj("\n", js_wkr.Log__get_at(i));
			Tfds.Eq_str_lines(expd_str, actl_str);
		}			
		js_wkr.Log__clear();
	}
}
