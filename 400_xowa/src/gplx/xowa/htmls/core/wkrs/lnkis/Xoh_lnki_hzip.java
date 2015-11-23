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
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_lnki_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public String Key() {return Xoh_hzip_dict_.Key__lnki;}
	public Xoh_lnki_hzip Encode(Bry_bfr bfr, Xoh_hdoc_ctx hctx, Hzip_stat_itm stat_itm, byte[] src, Xoh_lnki_parser arg) {
		byte text_type = arg.Text_type();
		Xoh_anch_href_parser anch_href_parser = arg.Href_parser();
		int page_ns_id = anch_href_parser.Page_ns_id();
		boolean page_ns_id_is_not_main = page_ns_id != Xow_ns_.Tid__main;
		int href_type = anch_href_parser.Tid();
		flag_bldr.Set(Flag__ns_is_not_main			, page_ns_id_is_not_main);
		flag_bldr.Set(Flag__href_type				, href_type);
		flag_bldr.Set(Flag__text_type				, text_type);

		bfr.Add(Xoh_hzip_dict_.Bry__lnki);
		Xoh_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		if (page_ns_id_is_not_main)
			Xoh_lnki_dict_.Ns_encode(bfr, page_ns_id);
		if (href_type == Xoh_anch_href_parser.Tid__site)
			bfr.Add_mid(src, anch_href_parser.Site_bgn(), anch_href_parser.Site_end()).Add_byte(Xoh_hzip_dict_.Escape);
		switch (text_type) {
			case Xoh_anch_capt_parser.Tid__href:
			case Xoh_anch_capt_parser.Tid__href_pipe:
				stat_itm.Lnki_text_n_add();
				bfr.Add_mid(arg.Href_bry(), arg.Href_bgn(), arg.Href_end());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				break;
			case Xoh_anch_capt_parser.Tid__capt:
			case Xoh_anch_capt_parser.Tid__href_trail:
			case Xoh_anch_capt_parser.Tid__capt_short:
				stat_itm.Lnki_text_y_add();
				bfr.Add_mid(arg.Href_bry(), arg.Href_bgn(), arg.Href_end());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				bfr.Add_mid(arg.Capt_bry(), arg.Capt_bgn(), arg.Capt_end());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				break;
		}
		return this;
	}
	public int Decode(Bry_bfr bfr, boolean write_to_bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int hook_bgn) {
		int flag = rdr.Read_int_by_base85(1);
		flag_bldr.Decode(flag);
		boolean page_ns_id_is_not_main		= flag_bldr.Get_as_bool(Flag__ns_is_not_main);
		byte href_type					= flag_bldr.Get_as_byte(Flag__href_type);
		byte text_type					= flag_bldr.Get_as_byte(Flag__text_type);

		int ns_id = page_ns_id_is_not_main ? Xoh_lnki_dict_.Ns_decode(rdr) : Xow_ns_.Tid__main;
		int site_bgn = -1, site_end = -1;
		if (href_type == Xoh_anch_href_parser.Tid__site) {
			site_bgn = rdr.Pos();
			site_end = rdr.Find_fwd_lr();
		}
		int href_bgn = rdr.Pos();
		int href_end = rdr.Find_fwd_lr();
		int capt_bgn = -1, capt_end = -1;
		switch (text_type) {
			case Xoh_anch_capt_parser.Tid__capt:
			case Xoh_anch_capt_parser.Tid__capt_short:
			case Xoh_anch_capt_parser.Tid__href_trail:
				capt_bgn = rdr.Pos();
				capt_end = rdr.Find_fwd_lr();
				break;
		}
		byte[] href_bry = null;
		if (text_type == Xoh_anch_capt_parser.Tid__capt_short)
			href_bry = Bry_.Add(Bry_.Mid(src, href_bgn, href_end), Bry_.Mid(src, capt_bgn, capt_end));
		else
			href_bry = Bry_.Mid(src, href_bgn, href_end);
		byte[] title_bry = null;
		Xoa_ttl ttl = null;
		if (href_type != Xoh_anch_href_parser.Tid__anch) {
			switch (href_type) {
				case Xoh_anch_href_parser.Tid__site:
					Xow_ttl_parser ttl_parser = hctx.App().Wiki_mgri().Get_by_key_or_make_init_n(Bry_.Mid(src, site_bgn, site_end));
					ttl = ttl_parser.Ttl_parse(ns_id, href_bry);
					href_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href_qarg.Encode(ttl.Full_db());
					title_bry = ttl.Full_txt();
					break;
				case Xoh_anch_href_parser.Tid__wiki:
					ttl = hctx.Wiki__ttl_parser().Ttl_parse(ns_id, href_bry); if (ttl == null) rdr.Fail("invalid ttl", String_.Empty, String_.new_u8(href_bry));
					href_bry = ttl.Full_db_w_anch();
					href_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(href_bry);	// encode for href; EX: "/wiki/A's" -> "/wiki/A&27s"
					title_bry = ttl.Full_txt();
					break;
				case Xoh_anch_href_parser.Tid__inet:
					title_bry = href_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href_qarg.Encode(href_bry);
					break;
			}
		}

		// gen html
		bfr.Add(Html_bldr_.Bry__a_lhs_w_href);
		switch (href_type) {
			case Xoh_anch_href_parser.Tid__anch:
				bfr.Add_byte(Byte_ascii.Hash);							// "#"
				break;
			case Xoh_anch_href_parser.Tid__site:
				bfr.Add(Xoh_href_.Bry__site).Add_mid(src, site_bgn, site_end);
				bfr.Add(Xoh_href_.Bry__wiki);
				break;
			case Xoh_anch_href_parser.Tid__wiki:
				bfr.Add(Xoh_href_.Bry__wiki);
				break;
		}
		bfr.Add(href_bry);
		bfr.Add(Html_bldr_.Bry__id__nth).Add_str_a7(gplx.xowa.parsers.lnkis.redlinks.Xopg_redlink_lnki_list.Lnki_id_prefix).Add_int_variable(hctx.Lnki__uid__nxt());
		if (href_type != Xoh_anch_href_parser.Tid__anch) {
			bfr.Add(Html_bldr_.Bry__title__nth);
			Html_utl.Escape_html_to_bfr(bfr, title_bry, 0, title_bry.length, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.N);
		}
		bfr.Add(Html_bldr_.Bry__lhs_end_head_w_quote);
		if (	href_type == Xoh_anch_href_parser.Tid__anch
			&&	text_type != Xoh_anch_capt_parser.Tid__capt )
			bfr.Add_byte(Byte_ascii.Hash);
		switch (text_type) {
			case Xoh_anch_capt_parser.Tid__href:
				if (ns_id == Xow_ns_.Tid__main)
					bfr.Add_mid(src, href_bgn, href_end);
				else
					bfr.Add(ttl.Full_txt());
				break;
			case Xoh_anch_capt_parser.Tid__href_pipe:
				bfr.Add_mid(src, href_bgn, href_end);
				break;
			case Xoh_anch_capt_parser.Tid__capt:
				bfr.Add_mid(src, capt_bgn, capt_end);
				break;
			case Xoh_anch_capt_parser.Tid__href_trail:
				bfr.Add_mid(src, href_bgn, href_end);
				bfr.Add_mid(src, capt_bgn, capt_end);
				break;
			case Xoh_anch_capt_parser.Tid__capt_short:
				bfr.Add_mid(src, href_bgn, href_end);
				break;
		}
		bfr.Add(Html_bldr_.Bry__a_rhs);
		return rdr.Pos();
	}
	public int				Pool__idx() {return pool_idx;} private int pool_idx;
	public void				Pool__clear (Object[] args) {}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnki_hzip rv = new Xoh_lnki_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ (1, 2, 3);
	private static final int // SERIALIZED
	  Flag__ns_is_not_main		=  0
	, Flag__href_type			=  1	// "wiki", "site", "anch", "inet"
	, Flag__text_type			=  2	// "href", "capt", "href_trail", "capt_short", "href_pipe"
	;
}
