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
package gplx.xowa.xtns.listings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Listing_xnde implements Xox_xnde, Mwh_atr_itm_owner1 {
	private final    Bry_fmtr tmp_fmtr = Bry_fmtr.New__tmp();
	public Listing_xnde(int tag_id) {}
	private byte[] xatr_name, xatr_alt, xatr_address, xatr_directions, xatr_phone, xatr_tollfree, xatr_email, xatr_fax, xatr_url, xatr_hours, xatr_price, xatr_checkin, xatr_checkout;
	private int xatr_lat = Xatr_meridian_null, xatr_long = Xatr_meridian_null;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		Byte_obj_val xatr_id = (Byte_obj_val)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Listing_xatrs.Tid_name:		xatr_name = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_alt:			xatr_alt = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_address:		xatr_address = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_directions:	xatr_directions = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_phone:		xatr_phone = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_tollfree:	xatr_tollfree = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_email:		xatr_email = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_fax:			xatr_fax = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_url:			xatr_url = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_hours:		xatr_hours = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_price:		xatr_price = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_checkin:		xatr_checkin = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_checkout:	xatr_checkout = xatr.Val_as_bry__blank_to_null(); break;
			case Listing_xatrs.Tid_lat:			xatr_lat = xatr.Val_as_int_or(Xatr_meridian_null); break;
			case Listing_xatrs.Tid_long:		xatr_long = xatr.Val_as_int_or(Xatr_meridian_null); break;
		}
	}
	private void Init_args(Xop_ctx ctx) {
		if (xatr_name == null) {
			xatr_name = msg_mgr.Itm_by_key_or_new(Bry_.new_a7("listings-unknown")).Val();
		}
		xatr_alt			= Parse_wikitext(ctx, xatr_alt);
		xatr_address		= Parse_wikitext(ctx, xatr_address);
		xatr_directions		= Parse_wikitext(ctx, xatr_directions);
	}		
	private Xowe_wiki wiki; private Xop_parser parser; private Xop_ctx sub_ctx; private Xol_msg_mgr msg_mgr;
	private byte[] Parse_wikitext(Xop_ctx ctx, byte[] v) {
		if (Bry_.Len_eq_0(v)) return v;	// ignore null, ""
		if (sub_ctx == null) sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		return parser.Parse_text_to_html(sub_ctx, v);
	}
	private Listing_xtn_mgr xtn_mgr;
	private byte[] html_output = Bry_.Empty;
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		this.wiki = wiki; this.parser = wiki.Parser_mgr().Main(); this.msg_mgr = wiki.Lang().Msg_mgr();
		xtn_mgr = (Listing_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Listing_xtn_mgr.Xtn_key_static);
		if (xtn_mgr == null || !xtn_mgr.Enabled()) return;
		Mwh_atr_itm[] atrs = Xox_xnde_.Xatr__set(wiki, this, Listing_xatrs.Key_hash, src, xnde);
		Init_args(ctx);
		Gfh_wtr hwtr = xtn_mgr.Hwtr();
		if (!Bld_by_template(ctx, xnde, atrs, src)) {
			Bld_by_args(xtn_mgr, ctx, hwtr, xnde, src);
			html_output = hwtr.To_bry_and_clear();
		}
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (xtn_mgr == null || !xtn_mgr.Enabled()) 
			Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde);
		else
			bfr.Add(html_output);
	}
	private boolean Bld_by_template(Xop_ctx ctx, Xop_xnde_tkn xnde, Mwh_atr_itm[] atrs, byte[] src) {
		byte[] listings_template = xtn_mgr.Listings_template();
		if (listings_template == null) return false;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		bfr.Add(Bry__invk_bgn);					// "{{"
		bfr.Add(listings_template);				// name
		int atrs_len = atrs.length;
		for (int i = 0; i < atrs_len; i++) {
			Mwh_atr_itm atr = atrs[i];
			bfr.Add_byte_pipe();				// "|"
			byte[] atr_key = atr.Key_bry(); if (atr_key == null) continue;	// skip keyless atrs; PAGE:nl.v:Rome;EX:<sleep phone='' "abc"/> DATE:2014-06-04
			bfr.Add(atr_key);					// key
			bfr.Add_byte(Byte_ascii.Eq);		// "="
			bfr.Add(atr.Val_as_bry());			// val; NOTE: must use Val_as_bry(src), not Val_bry, else int or "" will not be captured; DATE:2014-05-21
		}
		if (xnde.CloseMode() == Xop_xnde_tkn.CloseMode_pair) {
			bfr.Add_byte_pipe();				// "|"
			bfr.Add_mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		}
		bfr.Add(Bry__invk_end);					// "}}"
		Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		html_output = wiki.Parser_mgr().Main().Parse_text_to_html(sub_ctx, bfr.To_bry_and_rls());			
		return true;
	}
	private void Bld_by_args(Listing_xtn_mgr xtn_mgr, Xop_ctx ctx, Gfh_wtr wtr, Xop_xnde_tkn xnde, byte[] src) {
		wtr.Nde_full(Tag_strong, xatr_name);							// <strong>name</strong>
		if (xatr_url != null)
			wtr.Nde_full_atrs(Tag_a, wtr.To_bry_and_clear(), false
			, Atr_a_href		, xatr_url
			, Atr_a_rel			, Atr_a_rel_nofollow
			, Atr_a_class		, Atr_a_class_external_text
			, Atr_a_title		, xatr_name
			);
		if (xatr_alt != null) {
			wtr.Txt(Txt_space_paren);									// " ("
			wtr.Nde_full_atrs(Tag_em, xatr_alt, false);					// alt
			wtr.Txt_byte(Byte_ascii.Paren_end);							// ")"
		}
		byte[] position = Bld_position(ctx);
		if (xatr_address != null || xatr_directions != null || position != null) {
			if (xatr_address != null)
				wtr.Txt(Txt_comma_space).Txt(xatr_address);				// , address
			if (xatr_directions != null || position != null) {
				wtr.Txt(Txt_space_paren);								// " ("
				wtr.Nde_bgn_hdr(Tag_em).Txt(xatr_directions);			// <em>directions
				if (xatr_directions != null &&  position != null)
					wtr.Txt(Txt_comma_space);							// ,
				wtr.Txt(position);										// position
				wtr.Nde_end();											// </em>
				wtr.Txt_byte(Byte_ascii.Paren_end);						// ")"
			}
		}
		
		if (xatr_phone != null || xatr_tollfree != null) {
			wtr	.Txt(Txt_comma_space)
				.Txt_raw(xtn_mgr.Phone_symbol())
				.Txt_byte(Byte_ascii.Space)
				.Txt(xatr_phone)
			;
			if (xatr_tollfree != null) {
				wtr	.Txt(Txt_space_paren)
					.Txt_raw(xtn_mgr.Tollfree_symbol())
					.Txt(Txt_colon_space)
					.Txt(xatr_tollfree)
					.Txt_byte(Byte_ascii.Paren_end)
					;
			}
		}
		if (xatr_fax != null) {
			wtr	.Txt(Txt_comma_space)
				.Txt_raw(xtn_mgr.Fax_symbol())
				.Txt(Txt_colon_space)
				.Txt(xatr_fax)
				;
		}
		if (xatr_email != null) {
			wtr	.Txt(Txt_comma_space)
				.Txt_raw(xtn_mgr.Email_symbol())
				.Txt(Txt_colon_space)
				;
			wtr	.Nde_full_atrs(Tag_a, xatr_email, true
			, Atr_a_href	, Bry_.Add(Txt_mailto, xatr_email)	// NOTE: switched from "class,href" to "href,class" for hzip; PAGE:de.v:Bansin; DATE:2016-04-21
			, Atr_a_class	, Atr_a_class_email
			);
		}
		wtr.Txt(Txt_dot_space);
		if (xatr_hours != null)
			wtr.Txt(xatr_hours).Txt(Txt_dot_space);

		if (xatr_checkin != null || xatr_checkout != null) {
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b128();
			if (xatr_checkin != null) {
				byte[] checkin_val = xtn_mgr.Checkin_msg().Fmt(tmp_bfr, tmp_fmtr, xatr_checkin);
				wtr.Txt_raw(checkin_val);
				if (xatr_checkout != null)
					wtr.Txt(Txt_comma_space);
				
			}
			if (xatr_checkout != null) {
				byte[] checkout_val = xtn_mgr.Checkout_msg().Fmt(tmp_bfr, tmp_fmtr, xatr_checkout);
				wtr.Txt_raw(checkout_val);
			}
			wtr.Txt(Txt_dot_space);
			tmp_bfr.Mkr_rls();
		}
		if (xatr_price != null)
			wtr.Txt(xatr_price).Txt(Txt_dot_space);

		if (xnde.CloseMode() == Xop_xnde_tkn.CloseMode_pair)
			wtr.Txt(Bry_.Trim(Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn())));
	}
	private byte[] Bld_position(Xop_ctx ctx) {
		if (xatr_lat >= Xatr_meridian_null || xatr_long >= Xatr_meridian_null) return null;		// check that lat and long are valid
		Xol_msg_itm position_template = xtn_mgr.Position_template();
		if (position_template == null) return null;
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b128();
		try {
			byte[] rv = position_template.Fmt(tmp_bfr, tmp_fmtr, xatr_lat, xatr_long);
			tmp_bfr.Add(Bry__invk_bgn);					// "{{"
			tmp_bfr.Add(rv);							// rv is not message, but actually template precursor
			tmp_bfr.Add(Bry__invk_end);					// "}}"
			Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
			rv = wiki.Parser_mgr().Main().Parse_text_to_html(sub_ctx, tmp_bfr.To_bry_and_clear());
			Xol_msg_itm position_text = xtn_mgr.Position_text();
			if (Bry_.Len_eq_0(position_text.Val())) return rv;
			return position_text.Fmt(tmp_bfr, tmp_fmtr, rv);
		} finally {tmp_bfr.Mkr_rls();}
	}
	private static final int Xatr_meridian_null = 361;
	public static final    byte[]
	  Tag_strong					= Bry_.new_a7("strong")
	, Tag_a							= Bry_.new_a7("a")
	, Tag_em						= Bry_.new_a7("em")
	, Tag_abbr						= Bry_.new_a7("abbr")
	, Atr_a_href					= Bry_.new_a7("href")
	, Atr_a_class					= Bry_.new_a7("class")
	, Atr_a_class_external_text		= Bry_.new_a7("external text")
	, Atr_a_class_email				= Bry_.new_a7("email")
	, Atr_a_rel						= Bry_.new_a7("rel")
	, Atr_a_rel_nofollow			= Bry_.new_a7("nofollow")
	, Atr_a_title					= Bry_.new_a7("title")
	, Txt_comma_space				= Bry_.new_a7(", ")
	, Txt_space_paren				= Bry_.new_a7(" (")
	, Txt_colon_space				= Bry_.new_a7(": ")
	, Txt_dot_space					= Bry_.new_a7(". ")
	, Txt_mailto					= Bry_.new_a7("mailto:")
	;
	private static final    byte[]
	  Bry__invk_bgn					= gplx.xowa.parsers.tmpls.Xop_curly_bgn_lxr.Hook
	, Bry__invk_end					= gplx.xowa.parsers.tmpls.Xop_curly_end_lxr.Hook
	;
}
class Listing_xatrs {
	public static final byte Tid_name = 0, Tid_alt = 1, Tid_address = 2, Tid_directions = 3, Tid_phone = 4, Tid_tollfree = 5, Tid_email = 6, Tid_fax = 7, Tid_url = 8
	, Tid_hours = 9, Tid_price = 10, Tid_checkin = 11, Tid_checkout = 12, Tid_lat = 13, Tid_long = 14
	;
	public static Hash_adp_bry Key_hash = Hash_adp_bry.cs()
	.Add_str_byte("name"		, Tid_name)
	.Add_str_byte("alt"			, Tid_alt)
	.Add_str_byte("address"		, Tid_address)
	.Add_str_byte("directions"	, Tid_directions)
	.Add_str_byte("phone"		, Tid_phone)
	.Add_str_byte("tollfree"	, Tid_tollfree)
	.Add_str_byte("email"		, Tid_email)
	.Add_str_byte("fax"			, Tid_fax)
	.Add_str_byte("url"			, Tid_url)
	.Add_str_byte("hours"		, Tid_hours)
	.Add_str_byte("price"		, Tid_price)
	.Add_str_byte("checkin"		, Tid_checkin)
	.Add_str_byte("checkout"	, Tid_checkout)
	.Add_str_byte("lat"			, Tid_lat)
	.Add_str_byte("long"		, Tid_long)
	;
	public static Xop_xnde_tag Tag_by_tid(int tid) {
		Xop_xnde_tag rv = null;
		switch (tid) {
			case Xop_xnde_tag_.Tid__listing_buy:		rv = Xop_xnde_tag_.Tag__listing_buy; break;
			case Xop_xnde_tag_.Tid__listing_do:			rv = Xop_xnde_tag_.Tag__listing_do; break;
			case Xop_xnde_tag_.Tid__listing_drink:		rv = Xop_xnde_tag_.Tag__listing_drink; break;
			case Xop_xnde_tag_.Tid__listing_eat:		rv = Xop_xnde_tag_.Tag__listing_eat; break;
			case Xop_xnde_tag_.Tid__listing_listing:	rv = Xop_xnde_tag_.Tag__listing_listing; break;
			case Xop_xnde_tag_.Tid__listing_see:		rv = Xop_xnde_tag_.Tag__listing_see; break;
			case Xop_xnde_tag_.Tid__listing_sleep:		rv = Xop_xnde_tag_.Tag__listing_sleep; break;
			default: throw Err_.new_unhandled(tid);
		}
		return rv;
	}
}
