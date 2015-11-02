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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.encoders.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.htmls.core.hzips.stats.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.lnkes.*;
public class Xoh_lnki_hzip implements Xoh_hzip_wkr {
	private Xow_ttl_parser ttl_parser;
	public void Ttl_parser_(Xow_ttl_parser ttl_parser) {this.ttl_parser = ttl_parser;} 
	public String Key() {return Xoh_hzip_dict_.Key__lnki;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, Bry_parser parser, byte[] src, int hook_bgn) {Parse(bfr, stat_itm, parser, src, hook_bgn);}
	public int Parse(Bry_bfr bfr, Hzip_stat_itm stat_itm, Bry_parser parser, byte[] src, int hook_bgn) {
		int rng_bgn = hook_bgn - 2;								// -2 to skip "<a"
		byte lnki_type = parser.Read_byte();
		int page_bgn = parser.Fwd_end(Bry__href);
		int site_bgn = -1, site_end = -1;
		Object href_tid_obj = btrie_href.Match_bgn(src, parser.Pos(), parser.Src_len());	// if (href_tid_obj == null) return rdr.Warn("lnki.decode:unknown href format", bgn, pos);	// not "/wiki/" or "/site/"
		if (((Byte_obj_val)href_tid_obj).Val() == Href_tid_site) {							// site; EX:"/site/en.wiktionary.org/"
			site_bgn = parser.Pos_add(Xoh_href_.Len__site);
			site_end = parser.Fwd_bgn(Byte_ascii.Slash);
		}
		else {																				// page; EX: "/wiki/Page"
			page_bgn = parser.Chk(Xoh_href_.Bry__wiki);
		}
		int page_end = parser.Fwd_bgn(Byte_ascii.Quote);
		parser.Fwd_end(Bry__id);
		int uid = parser.Read_int_to(Byte_ascii.Quote);
		int title_bgn = parser.Fwd_end(Bry__title);
		int title_end = parser.Fwd_bgn(Byte_ascii.Quote);
		int caption_bgn = -1, caption_end = -1, rng_end = -1;
		if (lnki_type == Xoh_lnki_dict_.Type__caption_n) {
			caption_bgn = parser.Fwd_end(Byte_ascii.Angle_end);
			caption_end = parser.Fwd_bgn(Html_tag_.A_rhs);
			rng_end = parser.Pos();
		}
		else
			rng_end = parser.Chk(Byte_ascii.Angle_end);
		// if (id_bgn > rng_end) return Xoh_hzip_dict_.Unhandled;	// TODO: handle "jumping" over one "<a" to another
		boolean site_exists = site_end - site_bgn > 0;
		if (	lnki_type == Xoh_lnki_dict_.Type__caption_n				// lnki_text_n; EX: [[A]] not [[A|A1]]
			&&	!site_exists											// not xwiki; EX: [[wikt:A]]
			&&	(page_end - page_bgn) != (caption_end - caption_bgn)	// note that in 99% of lnki_text_n cases, html_text_len == text_len; however, tidy sometimes relocates html inside html_text; PAGE:en.w:Abyssal_plain; EX:<font color='green'>[[A]]</font>; DATE:2015-06-02
			) {	
			lnki_type = Xoh_lnki_dict_.Type__caption_y;					// change to lnki_text_y
			parser.Pos_(caption_bgn);
		}
		Parse_done(bfr, parser, null, ttl_parser, stat_itm, src, lnki_type, rng_bgn, rng_end, site_exists, site_bgn, site_end, page_bgn, page_end, uid, title_bgn, title_end, caption_bgn, caption_end);
		return parser.Pos();
	}
	public void Parse_done(Bry_bfr bfr, Bry_parser parser, Bry_rdr rdr, Xow_ttl_parser ttl_parser, Hzip_stat_itm stat_itm, byte[] src, byte lnki_type, int rng_bgn, int rng_end, boolean site_exists, int site_bgn, int site_end, int page_bgn, int page_end, int uid, int title_bgn, int title_end, int caption_bgn, int caption_end) {
		Xoa_ttl ttl = ttl_parser.Ttl_parse(Bry_.Mid(src, page_bgn, page_end));	if (ttl == null) throw parser.Fail("lnki.decode:invalid ttl", String_.new_u8(src, page_bgn, page_end));
		bfr.Add(Xoh_hzip_dict_.Bry__lnki);
		bfr.Add_byte(lnki_type);
		Xoh_hzip_int_.Encode(1, bfr, ttl.Ns().Id());
		Xoh_hzip_int_.Encode(1, bfr, uid);
		if (site_exists)
			bfr.Add_byte(Xoh_hzip_dict_.Escape).Add_mid(src, site_bgn, site_end).Add_byte(Xoh_hzip_dict_.Escape);
		if (lnki_type == Xoh_lnki_dict_.Type__caption_y) {
			bfr.Add(ttl.Page_db());
			bfr.Add_byte(Xoh_hzip_dict_.Escape);
			stat_itm.Lnki_text_y_add();
		}
		else {
			if (!ttl.Ns().Id_main())		// non-main ns should write page_db only; EX: "Template:A" should write "A" since "Template" will be inferred by ns_id
				bfr.Add(ttl.Page_db());
			else							// main ns should write html_text; handles [[a]] which has <a href="A">a</a>
				bfr.Add_mid(src, caption_bgn, caption_end);
			bfr.Add_byte(Xoh_hzip_dict_.Escape);
			stat_itm.Lnki_text_n_add();
		}
	}
	public int Decode(Bry_bfr bfr, Bry_parser parser, byte[] src, int hook_bgn) {
		// parse vars
		byte lnki_type = parser.Read_byte();
		int ns_id = parser.Read_int_by_base85(1);
		int uid = parser.Read_int_by_base85(1);
		int site_bgn = -1, site_end = -1;
		if (parser.Is(Xoh_hzip_dict_.Escape)) {
			site_bgn = parser.Pos();
			site_end = parser.Fwd_bgn(Xoh_hzip_dict_.Escape);
		}
		boolean site_exists = site_end - site_bgn > 0;
		int page_bgn = parser.Pos();
		int page_end = parser.Fwd_bgn(Xoh_hzip_dict_.Escape);
		byte[] page_bry = Bry_.Mid(src, page_bgn, page_end);
		Xoa_ttl ttl = ttl_parser.Ttl_parse(ns_id, page_bry); if (ttl == null) throw parser.Fail("invalid ttl", String_.new_u8(page_bry)); // TODO: parse title based on site
		byte[] ttl__full_db = ttl.Full_db();

		// gen html
		bfr.Add(Xoh_html_dict_.Hook__lnki);
		bfr.Add_byte(lnki_type);
		bfr.Add_str_a7("' href=\"");
		if (site_exists) bfr.Add_str_a7("/site/").Add_mid(src, site_bgn, site_end);
		bfr.Add(Xoh_href_.Bry__wiki);							// "/wiki/"
		bfr.Add(Html_utl.Escape_html_as_bry(ttl__full_db));
		bfr.Add_str_a7("\" id=\"").Add_str_a7(gplx.xowa.parsers.lnkis.redlinks.Xopg_redlink_lnki_list.Lnki_id_prefix).Add_int_variable(uid);
		bfr.Add_str_a7("\" title=\"");
		if (lnki_type == Xoh_lnki_dict_.Type__caption_n) {
			byte[] title_bry = site_exists ? ttl.Page_db() : ttl__full_db;	// for xwiki, use page, not full alias; EX: "wikt:A" -> "A" x> "wikt:A"
			bfr.Add(Html_utl.Escape_html_as_bry(title_bry)).Add_str_a7("\">");
			if (ns_id != 0) page_bry = ttl.Full_db();
			bfr.Add(page_bry);
			bfr.Add(Html_tag_.A_rhs);							// "</a>"
		}
		else {
			bfr.Add(Html_utl.Escape_html_as_bry(page_bry));
			bfr.Add_str_a7("\">");
		}
		return parser.Pos();
	}
	private static final byte[]
	  Bry__href				= Bry_.new_a7(" href=\"")
	, Bry__id				= Bry_.new_a7(" id=\"xowa_lnki_")
	, Bry__title			= Bry_.new_a7(" title=\"")
	;
	private static final byte Href_tid_wiki = 1, Href_tid_site = 2;
	private static final Btrie_fast_mgr btrie_href = Btrie_fast_mgr.cs().Add_bry_byte(Xoh_href_.Bry__wiki, Href_tid_wiki).Add_bry_byte(Xoh_href_.Bry__site, Href_tid_site);
//		, Find_img_xatrs		= Bry_.new_a7("xatrs='")
//		private static int[] Save_img_full_pow = new int[] {0, 1, 2};
//		private int Save_img_full(Bry_bfr bfr, Bry_rdr rdr, Hzip_stat_itm stat_itm, byte[] src, int src_len, int bgn, int pos) {
//			bfr.Add(Xoh_hzip_dict_.Bry__img_full);
//			int xatrs_bgn = Bry_find_.Move_fwd(src, Find_img_xatrs, pos, src_len);				if (xatrs_bgn == Bry_find_.Not_found) return rdr.Warn("a.img_xatrs_missing", bgn, pos);
//			bry_rdr.Init(src, xatrs_bgn);
//			int a_cls		= bry_rdr.Read_int_to_pipe();
//			int a_rel		= bry_rdr.Read_int_to_pipe();
//			int img_rel		= bry_rdr.Read_int_to_pipe();
//			byte meta		= (byte)Bit_.Shift_lhs_to_int(Save_img_full_pow, a_cls, a_rel, img_rel);
//			bfr.Add_byte(meta);														// meta
//			Xoh_hzip_int_.Encode(bfr, bry_rdr.Read_int_to_pipe());					// uid
//			bfr.Add(bry_rdr.Read_bry_to_pipe()).Add_byte_pipe();					// img_cls_other
//			bfr.Add(bry_rdr.Read_bry_to_apos());									// alt
//			bfr.Add_byte(Xoh_hzip_dict_.Escape);
//			return bry_rdr.Pos() + 2;												// +2=/>
//		}
}
