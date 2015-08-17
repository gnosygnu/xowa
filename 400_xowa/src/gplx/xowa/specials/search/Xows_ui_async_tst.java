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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import org.junit.*; import gplx.xowa.html.wtrs.*; import gplx.xowa.files.gui.*;
public class Xows_ui_async_tst {
	@Before public void init() {fxt.Clear();} private Xows_ui_async_fxt fxt = new Xows_ui_async_fxt();
	@Test   public void Basic() {			
		fxt.Test_add(fxt.Make_rslt(50, "L"), fxt.Make_args_append("xowa_insert_w"	, "w.7CL"));	// insert new
		fxt.Test_add(fxt.Make_rslt(30, "N"), fxt.Make_args_append("xowa_insert_w"	, "w.7CN"));	// insert below last
		fxt.Test_add(fxt.Make_rslt(70, "J"), fxt.Make_args_append("w.7CL"			, "w.7CJ"));	// insert above first
		fxt.Test_add(fxt.Make_rslt(60, "K"), fxt.Make_args_append("w.7CL"			, "w.7CK"));	// insert above mid
		fxt.Test_add(fxt.Make_rslt(40, "M"), fxt.Make_args_append("w.7CN"			, "w.7CM"));	// insert below mid
		fxt.Test_add(fxt.Make_rslt(10, "P"));													// insert noop			
		fxt.Test_add(fxt.Make_rslt(80, "I"), fxt.Make_args_append("w.7CJ"			, "w.7CI") , fxt.Make_args_replace("w.7CN"));	// insert displace all
		fxt.Test_add(fxt.Make_rslt(61, "K1"), fxt.Make_args_append("w.7CK"			, "w.7CK1"), fxt.Make_args_replace("w.7CM"));	// insert displace mid
	}
}
class Xows_ui_async_fxt {
	private Xows_html_row html_row; private static final byte[] Bry_enwiki = Bry_.new_a7("w");
	private Xows_ui_async async;
	private Xog_js_wkr__log js_wkr = new Xog_js_wkr__log();
	private Xowe_wiki wiki;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_(app, "w");
		html_row = new Xows_html_row(wiki.App().Html__lnki_bldr());
		html_row.Fmtr().Fmt_("~{page_key}");
		async = new Xows_ui_async(Cancelable_.Never, html_row, js_wkr, 5, Bry_enwiki);
	}
	public Xows_db_row Make_rslt(int len, String ttl) {
		byte[] ttl_bry = Bry_.new_a7(ttl);
		return new Xows_db_row(Bry_enwiki, wiki.Ttl_parse(ttl_bry), 1, len);
	}
	public Object[] Make_args_append(String uid, String html)	{return Object_.Ary(Xog_js_wkr__log.Proc_append_above, uid, html);}
	public Object[] Make_args_replace(String uid)				{return Object_.Ary(Xog_js_wkr__log.Proc_replace_html, uid, "");}
	public void Test_add(Xows_db_row row, Object[]... expd) {
		async.Add(row);
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
