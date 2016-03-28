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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hzip_fxt {
	private final Xowe_wiki wiki;
	private final Xop_fxt parser_fxt = new Xop_fxt();
	private final Xoh_hzip_bfr bfr = Xoh_hzip_bfr.New_txt(32);
	private final Xoh_hzip_mgr hzip_mgr;
	private final Xoh_page hpg = new Xoh_page();
	private boolean mode_is_b256;
	public Xoh_hzip_fxt() {
		this.wiki = parser_fxt.Wiki();
		Xoa_app_fxt.repo2_(parser_fxt.App(), wiki);	// needed else will be old "mem/wiki/repo/trg/thumb/" instead of standard "mem/file/en.wikipedia.org/thumb/"
		wiki.Html__hdump_mgr().Init_by_db(parser_fxt.Wiki());
		this.hzip_mgr = parser_fxt.Wiki().Html__hdump_mgr().Hzip_mgr();
		hpg.Init(wiki, Xoa_url.blank(), parser_fxt.Wiki().Ttl_parse(Xoa_page_.Main_page_bry), 1);
	}
	public Xow_wiki Wiki() {return wiki;}
	public Xoh_hzip_fxt Init_mode_is_b256_(boolean v) {bfr.Mode_is_b256_(v); mode_is_b256 = v; return this;}
	public Xoh_hzip_fxt Init_mode_diff_y_() {hzip_mgr.Hctx().Mode_is_diff_(Bool_.Y); return this;}
	public void Clear() {hpg.Clear();}
	public void Init_wiki_installed(String domain) {parser_fxt.Init_xwiki_add_user_(domain);}
	public Xowe_wiki Init_wiki_alias(String alias, String domain) {
		Xowe_wiki rv = Xoa_app_fxt.Make__wiki__edit(parser_fxt.App(), domain);
		parser_fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_u8(alias), Bry_.new_u8(domain), null);
		return rv;
	}
	public void Init__ns_alias__add(String alias, int ns_id) {
		parser_fxt.Wiki().Ns_mgr().Aliases_add(ns_id, alias).Init();
	}
	public void Init__ns_alias__del(String alias) {
		parser_fxt.Wiki().Ns_mgr().Aliases_del(alias);
	}
	public void Test__bicode(String hzip, String html) {Test__bicode(hzip, html, html);}
	public void Test__bicode(String hzip, String html_enc, String html_dec) {
		html_enc = Gfh_utl.Replace_apos(html_enc);
		html_dec = Gfh_utl.Replace_apos(html_dec);
		Test__bicode_raw(hzip, html_enc, html_dec);
	}
	public void Test__bicode_raw(String hzip, String html_enc, String html_dec) {
		hzip = Xoh_hzip_fxt.Escape(hzip); 
		Test__encode__raw(hzip, html_enc);
		Test__decode__raw(hzip, html_dec);
	}
	public void Test__encode(String hzip, String html) {
		hzip = Xoh_hzip_fxt.Escape(hzip); html = Gfh_utl.Replace_apos(html);
		Test__encode__raw(hzip, html);
	}
	public void Test__decode(String hzip, String html) {
		hzip = Xoh_hzip_fxt.Escape(hzip); html = Gfh_utl.Replace_apos(html);
		Test__decode__raw(hzip, html);
	}
	public void Test__encode__fail(String expd, String html) {
		hzip_mgr.Encode(bfr, parser_fxt.Wiki(), hpg, Bry_.new_u8(html));
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
	private void Test__encode__raw(String hzip, String html) {
		Gfo_usr_dlg_.Test__show__init();
		hzip_mgr.Encode(bfr, parser_fxt.Wiki(), hpg, Bry_.new_u8(html));
		Gfo_usr_dlg_.Test__show__term();
		Tfds.Eq_str_lines(hzip, bfr.To_str_and_clear());
	}
	private void Test__decode__raw(String hzip, String html) {
		Gfo_usr_dlg_.Test__show__init();
		hpg.Section_mgr().Clear();
		hzip_mgr.Decode(bfr, parser_fxt.Wiki(), hpg, Bry_.new_u8(hzip));
		Gfo_usr_dlg_.Test__show__term();
		Tfds.Eq_str_lines(html, bfr.To_str_and_clear());
	}
	public void Exec_write_to_fsys(Io_url dir, String fil) {
		try {
			Xoh_hzip_bfr bfr = Xoh_hzip_bfr.New_txt(32).Mode_is_b256_(mode_is_b256);
			Gfo_usr_dlg_.Test__show__init();
			hzip_mgr.Encode(bfr, parser_fxt.Wiki(), hpg, Io_mgr.Instance.LoadFilBry(dir.GenSubFil(fil)));
			Gfo_usr_dlg_.Test__show__term();
			byte[] actl = bfr.To_bry_and_clear();
			Io_mgr.Instance.SaveFilBry(dir.GenSubFil(fil).GenNewExt(".hzip.html"), actl);
			Gfo_usr_dlg_.Test__show__init();
			gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_hzip.Md5_depth = 4;
			hzip_mgr.Hctx().Mode_is_diff_(Bool_.Y);
			hzip_mgr.Decode(bfr, parser_fxt.Wiki(), hpg, actl);
			hzip_mgr.Hctx().Mode_is_diff_(Bool_.N);
			gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_hzip.Md5_depth = 2;
			Gfo_usr_dlg_.Test__show__term();
			Io_mgr.Instance.SaveFilBry(dir.GenSubFil(fil).GenNewExt(".hzip.decode.html"), bfr.To_bry_and_clear());
		} catch (Exception e) {
			Tfds.Dbg(e);
		}
	}
	public static String Escape(String v) {return String_.Replace(v, "~", "");}
}
