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
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.files.caches.*;
public class Xoh_hzip_fxt {
	private final    Xowe_wiki wiki;
	private final    Xop_fxt parser_fxt = new Xop_fxt();
	private final    Xoh_hzip_bfr bfr = Xoh_hzip_bfr.New_txt(32);
	private final    Xoh_hzip_mgr hzip_mgr;
	private final    Xoh_page hpg = new Xoh_page();
	private boolean mode_is_b256;
	public Xoh_hzip_fxt() {
		this.wiki = parser_fxt.Wiki();
		Xoa_app_fxt.repo2_(parser_fxt.App(), wiki);	// needed else will be old "mem/wiki/repo/trg/thumb/" instead of standard "mem/file/en.wikipedia.org/thumb/"
		wiki.Html__hdump_mgr().Init_by_db(parser_fxt.Wiki());
		this.hzip_mgr = parser_fxt.Wiki().Html__hdump_mgr().Hzip_mgr();
		hpg.Ctor_by_hview(wiki, Xoa_url.blank(), parser_fxt.Wiki().Ttl_parse(Xoa_page_.Main_page_bry), 1);
	}
	public Xow_wiki Wiki() {return wiki;}
	public Xoa_page Page() {return hpg;}
	public Xoh_hzip_fxt Init_mode_is_b256_(boolean v) {bfr.Mode_is_b256_(v); mode_is_b256 = v; return this;}
	public Xoh_hzip_fxt Init_mode_diff_y_() {hzip_mgr.Hctx().Mode_is_diff_(Bool_.Y); return this;}
	public void Clear() {hpg.Clear();}
	public void Init_wiki_installed(String domain) {parser_fxt.Init_xwiki_add_user_(domain);}
	public Xou_cache_finder_mem Init_file_mgr__mem() {
		Xou_cache_finder_mem rv = Xou_cache_finder_.New_mem();
		hzip_mgr.Hctx().Test__cache__mgr_(rv);
		return rv;
	}
	public void Init_file_mgr__noop() {
		hzip_mgr.Hctx().Test__cache__mgr_(Xou_cache_finder_.Noop);
	}
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
	public void Test__decode__raw(String hzip, String html) {
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
