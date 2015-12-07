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
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.encoders.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*; import gplx.langs.htmls.encoders.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_lnki_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(32);
	public String Key() {return Xoh_hzip_dict_.Key__lnki;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_lnki_parser data = (Xoh_lnki_parser)data_obj;
		Xoh_anch_href_itm href = data.Href_itm();
		int ns_id = href.Ttl_ns_id(); ;
								  flag_bldr.Set_as_bool(Flag__title_missing_ns	, data.Title_missing_ns());
								  flag_bldr.Set_as_bool(Flag__ttl_is_main_page	, href.Ttl_is_main_page());
		boolean ns_custom_exists= flag_bldr.Set_as_bool(Flag__ns_custom_exists	, href.Ttl_ns_custom() != null);
		int title_tid			= flag_bldr.Set_as_int(Flag__title_tid			, href.Tid() == Xoh_anch_href_itm.Tid__anch ? Xoh_lnki_parser.Title__href : data.Title_tid());	// anchs never have title, so don't bother setting flag;
								  flag_bldr.Set_as_bool(Flag__capt_has_ns		, data.Capt_has_ns());
		boolean ns_is_not_main	= flag_bldr.Set_as_bool(Flag__ns_is_not_main	, ns_id != Xow_ns_.Tid__main);
		int href_type			= flag_bldr.Set_as_int(Flag__href_type			, href.Tid());
								  flag_bldr.Set_as_int(Flag__capt_cs0_tid		, data.Capt_itm().Cs0_tid());
		byte text_type			= flag_bldr.Set_as_byte(Flag__text_type			, data.Text_tid());
		// Tfds.Dbg(flag_bldr.Encode(), Array_.To_str(flag_bldr.Val_ary()), text_type);

		int bfr_bgn = bfr.Len();
		int flag = flag_bldr.Encode();
		bfr.Add(hook);
		bfr.Add_hzip_int(1, flag);
		if (href_type == Xoh_anch_href_itm.Tid__site)	bfr.Add_hzip_mid(src, href.Site_bgn(), href.Site_end());
		if (ns_is_not_main)								Xoh_lnki_dict_.Ns_encode(bfr, ns_id);
		if (ns_custom_exists)							bfr.Add_hzip_bry(href.Ttl_ns_custom());
		switch (text_type) {
			case Xoh_anch_capt_itm.Tid__same:
				bfr.Add_hzip_mid(data.Href_src(), data.Href_bgn(), data.Href_end());
				break;
			case Xoh_anch_capt_itm.Tid__diff:
			case Xoh_anch_capt_itm.Tid__more:
			case Xoh_anch_capt_itm.Tid__less:
				bfr.Add_hzip_mid(data.Text_0_src(), data.Text_0_bgn(), data.Text_0_end());
				bfr.Add_hzip_mid(data.Text_1_src(), data.Text_1_bgn(), data.Text_1_end());
				break;
		}
		if (title_tid == Xoh_lnki_parser.Title__diff)	bfr.Add_hzip_mid(src, data.Title_bgn(), data.Title_end());

		hctx.Hzip__stat().Lnki_add(data.Src_end() - data.Src_bgn(), bfr.Len() - bfr_bgn, flag);
		return this;
	}
	public int Decode(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, Bry_rdr rdr, byte[] src, int src_bgn, int src_end) {
		int flag = rdr.Read_int_by_base85(1); flag_bldr.Decode(flag);
		boolean title_missing_ns			= flag_bldr.Get_as_bool(Flag__title_missing_ns);
		boolean ttl_is_main_page			= flag_bldr.Get_as_bool(Flag__ttl_is_main_page);
		boolean ns_custom_exists			= flag_bldr.Get_as_bool(Flag__ns_custom_exists);
		byte title_tid					= flag_bldr.Get_as_byte(Flag__title_tid);
		boolean capt_has_ns				= flag_bldr.Get_as_bool(Flag__capt_has_ns);
		boolean ns_is_not_main				= flag_bldr.Get_as_bool(Flag__ns_is_not_main);
		byte href_type					= flag_bldr.Get_as_byte(Flag__href_type);
		int capt_cs0_tid				= flag_bldr.Get_as_int(Flag__capt_cs0_tid);
		byte text_type					= flag_bldr.Get_as_byte(Flag__text_type);

		int site_bgn = -1, site_end = -1; if (href_type == Xoh_anch_href_itm.Tid__site) {site_bgn = rdr.Pos(); site_end = rdr.Find_fwd_lr();}
		int ns_id = ns_is_not_main ? Xoh_lnki_dict_.Ns_decode(rdr) : Xow_ns_.Tid__main;
		byte[] ns_custom_bry = ns_custom_exists ? rdr.Read_bry_to() : null;
		int text_0_bgn = rdr.Pos(); int text_0_end = rdr.Find_fwd_lr();
		int text_1_bgn = -1, text_1_end = -1;
		switch (text_type) {
			case Xoh_anch_capt_itm.Tid__diff: case Xoh_anch_capt_itm.Tid__less: case Xoh_anch_capt_itm.Tid__more:
				text_1_bgn = rdr.Pos(); text_1_end = rdr.Find_fwd_lr();
				break;
		}
		byte[] title_bry = title_tid == Xoh_lnki_parser.Title__diff ? rdr.Read_bry_to() : null;
		byte[] href_bry = text_type == Xoh_anch_capt_itm.Tid__less 
						? tmp_bfr.Add_mid(src, text_0_bgn, text_0_end).Add_mid(src, text_1_bgn, text_1_end).To_bry_and_clear()
						: Bry_.Mid(src, text_0_bgn, text_0_end);
		byte[] ns_bry = null;
		switch (href_type) {
			case Xoh_anch_href_itm.Tid__anch: break;
			case Xoh_anch_href_itm.Tid__inet: break; //href_bry = Gfo_url_encoder_.Href_qarg.Encode(href_bry); break;
			case Xoh_anch_href_itm.Tid__wiki:					
			case Xoh_anch_href_itm.Tid__site:
				if (ns_custom_exists) {
					ns_bry = ns_custom_bry;
					tmp_bfr.Add(Xoa_ttl.Replace_spaces(ns_bry)).Add_byte_colon();	// NOTE: Replace_space to handle ns_custom_bry like "Image talk"
				}
				else {
					if (ns_id == Xow_ns_.Tid__main) {
						if (ttl_is_main_page)
							href_bry = Bry_.Empty;
					}
					else {
						Xow_ns ns = hctx.Wiki__ttl_parser().Ns_mgr().Ids_get_or_null(ns_id); if (ns == null) rdr.Err_wkr().Fail("invalid ns_id", "ns_id", ns_id);
						ns_bry = ns.Name_ui();
						tmp_bfr.Add(ns.Name_db()).Add_byte_colon();
					}
				}
				Gfo_url_encoder encoder = href_type == Xoh_anch_href_itm.Tid__wiki ? Gfo_url_encoder_.Href : Gfo_url_encoder_.Href_qarg;
				encoder.Encode(tmp_bfr, href_bry);	// encode for href; EX: "/wiki/A's" -> "/wiki/A&27s"
				href_bry = tmp_bfr.To_bry_and_clear();
				break;
		}
		byte[] capt_bry = Xoh_lnki_hzip_.Bld_capt(tmp_bfr, href_type, text_type, capt_has_ns, capt_cs0_tid, ns_bry, src, text_0_bgn, text_0_end, src, text_1_bgn, text_1_end);
		if (href_type != Xoh_anch_href_itm.Tid__anch) {
			switch (title_tid) {
				case Xoh_lnki_parser.Title__missing:	title_bry = null; break;
				case Xoh_lnki_parser.Title__diff:		break;
				case Xoh_lnki_parser.Title__href:		title_bry = Gfo_url_encoder_.Href.Decode(href_bry); break;
				case Xoh_lnki_parser.Title__capt:		title_bry = !capt_has_ns && !title_missing_ns && ns_bry != null ? Bry_.Add(ns_bry, Byte_ascii.Colon_bry, capt_bry) : capt_bry; break;
			}
		}

		// gen html
		bfr.Add(Html_bldr_.Bry__a_lhs_w_href);
		switch (href_type) {
			case Xoh_anch_href_itm.Tid__anch:
				bfr.Add_byte(Byte_ascii.Hash);							// "#"
				break;
			case Xoh_anch_href_itm.Tid__site:
				bfr.Add(Xoh_href_.Bry__site).Add_mid(src, site_bgn, site_end);
				bfr.Add(Xoh_href_.Bry__wiki);
				break;
			case Xoh_anch_href_itm.Tid__wiki:
				bfr.Add(Xoh_href_.Bry__wiki);
				break;
		}
		bfr.Add(href_bry);
		if (!hctx.Mode_is_diff())
			bfr.Add(Html_bldr_.Bry__id__nth).Add_str_a7(gplx.xowa.parsers.lnkis.redlinks.Xopg_redlink_lnki_list.Lnki_id_prefix).Add_int_variable(hctx.Lnki__uid__nxt());
		if (	href_type != Xoh_anch_href_itm.Tid__anch) {	// anchs never have title;
			if (title_bry != null) {
				bfr.Add(Html_bldr_.Bry__title__nth);
				Html_utl.Escape_html_to_bfr(bfr, title_bry, 0, title_bry.length, Bool_.N, Bool_.N, Bool_.N, Bool_.Y, Bool_.N);
			}
		}
		bfr.Add(Html_bldr_.Bry__lhs_end_head_w_quote);
		bfr.Add(capt_bry);
		bfr.Add(Html_bldr_.Bry__a_rhs);
		return rdr.Pos();
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnki_hzip rv = new Xoh_lnki_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ (1		, 1, 1, 2, 1	, 1, 2, 2, 2);
	private static final int // SERIALIZED
	  Flag__title_missing_ns	=  0	// [[c:]]			-> "/site/commons.wikimedia.org/wiki/"
	, Flag__ttl_is_main_page	=  1	// [[c:]]			-> "/site/commons.wikimedia.org/wiki/"
	, Flag__ns_custom_exists	=  2	// [[c:category:a]]	-> "/site/commons.wikimedia.org/wiki/category:a"
	, Flag__title_tid			=  3	// href, capt, diff, empty; [//en.wikipedia.org] where en.w is local
	, Flag__capt_has_ns			=  4	// "A" vs "Help:A"
	, Flag__ns_is_not_main		=  5
	, Flag__href_type			=  6	// "wiki", "site", "anch", "inet"
	, Flag__capt_cs0_tid		=  7	// exact, lower, upper
	, Flag__text_type			=  8	// "same", "diff", "more", "less"
	;
}
class Xoh_lnki_hzip_ {
	public static byte[] Bld_capt(Bry_bfr tmp_bfr, byte href_type, byte text_type, boolean capt_has_ns, int capt_cs0, byte[] ns_bry, byte[] text_0_src, int text_0_bgn, int text_0_end, byte[] capt_src, int text_1_bgn, int text_1_end) {
		if (	href_type == Xoh_anch_href_itm.Tid__anch
			&&	text_type != Xoh_anch_capt_itm.Tid__diff )
			tmp_bfr.Add_byte(Byte_ascii.Hash);
		if (capt_has_ns && ns_bry != null)
			tmp_bfr.Add(ns_bry).Add_byte_colon();
		switch (text_type) {
			case Xoh_anch_capt_itm.Tid__diff: break;
			default:
				switch (capt_cs0) {
					case Xoh_anch_capt_itm.Cs0__exact: break;
					case Xoh_anch_capt_itm.Cs0__lower: tmp_bfr.Add_byte(Byte_ascii.Case_lower(text_0_src[text_0_bgn++]));break;
					case Xoh_anch_capt_itm.Cs0__upper: tmp_bfr.Add_byte(Byte_ascii.Case_upper(text_0_src[text_0_bgn++]));break;
				}
				break;
		}
		switch (text_type) {
			case Xoh_anch_capt_itm.Tid__same:
			case Xoh_anch_capt_itm.Tid__less:
				tmp_bfr.Add_mid(text_0_src, text_0_bgn, text_0_end);
				break;
			case Xoh_anch_capt_itm.Tid__diff:
				tmp_bfr.Add_mid(capt_src, text_1_bgn, text_1_end);
				break;
			case Xoh_anch_capt_itm.Tid__more:
				tmp_bfr.Add_mid(text_0_src, text_0_bgn, text_0_end);
				tmp_bfr.Add_mid(capt_src, text_1_bgn, text_1_end);
				break;
		}
		return tmp_bfr.To_bry_and_clear();
	}
}
