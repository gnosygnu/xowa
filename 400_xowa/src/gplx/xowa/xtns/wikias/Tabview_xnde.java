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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Tabview_xnde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte[] id;
	private Tabber_tab_itm[] tab_itms_ary;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Xatr__id:			id = xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		Xox_xnde_.Parse_xatrs(wiki, this, xatrs_hash, src, xnde);

		// get id
		id = Keep_alphanum(id);
		if (Bry_.Len_eq_0(id)) id = Int_.To_bry(global_id++);

		// parse src
		byte[] tabs_src = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		tabs_src = Bry_.Trim(tabs_src);	// if(isset($tabs[0]) && $tabs[0] == "") {unset($tabs[0]);} if($tabs[count($tabs)] == "") {unset($tabs[count($tabs)]);}
		byte[][] tabs_ary = Bry_split_.Split_lines(tabs_src);
		List_adp tabs_list = List_adp_.New();
		int tabs_len = tabs_ary.length;
		for (int i = 0; i < tabs_len; ++i) {
			Tabview_tab_itm itm = Tabview_tab_itm.Parse(wiki, ctx, tabs_ary[i]);
			if (itm != null)
				tabs_list.Add(itm);
		}
		ctx.Page().Html_data().Head_mgr().Itm__tabber().Enabled_y_();
		Tabview_tab_itm[] ary = (Tabview_tab_itm[])tabs_list.To_ary_and_clear(Tabview_tab_itm.class);
		tab_itms_ary = Tabview_tab_itm.To_tabber_ary(ary);

		ctx.Page().Html_data().Head_mgr().Itm__tabber().Enabled_y_();
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (tab_itms_ary != null) Tabber_tab_itm.Write(bfr, id, tab_itms_ary);
		// write html
		//Bry_bfr bfr = Bry_bfr_.New();
		//bfr.Add_str_a7("<div id=\"flytabs_");
		//bfr.Add(id);
		//bfr.Add_str_a7("\">\n<ul>\n");
		//int ary_len = ary.length;
		//for (int i = 0; i < ary_len; ++i) {
		//	Tabview_tab_itm itm = ary[i];
		//	bfr.Add_str_a7("<li><a href=");
		//	// href
		//	bfr.Add_str_a7("><span>");
		//	bfr.Add(itm.Tab_name);
		//	bfr.Add_str_a7("</span></a></li>");
		//}
		//bfr.Add_str_a7("</ul>\n</div>\n");
	}

	private int global_id = 0;
	public static final byte Xatr__id = 0;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7().Add_str_byte("id", Xatr__id);
	private static byte[] Keep_alphanum(byte[] src) {
		if (src == null) return null;
		int src_len = src.length; if (src_len == 0) return src;
		Bry_bfr bfr = null;
		for (int i = 0; i < src_len; ++i) {
			byte b = src[i];
			if (	Byte_ascii.Is_ltr(b)
				||	Byte_ascii.Is_num(b)) {
				if (bfr != null) bfr.Add_byte(b);
			}
			else {	// not alphanum;
				if (bfr == null) {	// 1st occurrence; create bfr and add initial to it
					bfr = Bry_bfr_.New();
					bfr.Add_mid(src, 0, i);
				}
			}
		}
		return bfr == null ? src : bfr.To_bry_and_clear();
	}
}
class Tabview_tab_itm {
	public Tabview_tab_itm(boolean active, boolean cache, byte[] tab_name, byte[] page_ttl_bry, byte[] page_body) {
		this.Active = active;
		this.Cache = cache;
		this.Tab_name = tab_name;
		this.Page_ttl_bry = page_ttl_bry;
		this.Page_body = page_body;
	}
	public final    boolean Active;
	public final    boolean Cache;
	public final    byte[] Tab_name;
	public final    byte[] Page_ttl_bry;
	public final    byte[] Page_body;

	public static Tabview_tab_itm Parse(Xowe_wiki wiki, Xop_ctx ctx, byte[] src) {
		byte[][] args_ary = Bry_split_.Split(src, Byte_ascii.Pipe);
		int args_len = args_ary.length;
		
		boolean cache = false, active = false;
		byte[] tab_name = null, page_ttl_bry = null, page_body = null;
		for (int i = 0; i < args_len; ++i) {
			byte[] args_itm = args_ary[i];
			switch (i) {
				case 0:
					page_ttl_bry = args_itm;
					if (Bry_.Has_at_bgn(page_ttl_bry, Byte_ascii.Angle_bgn) || Bry_.Has_at_end(page_ttl_bry, Byte_ascii.Angle_end)) return null;
					Xoa_ttl page_ttl = wiki.Ttl_parse(page_ttl_bry);
					if (page_ttl == null) return null;
					gplx.xowa.wikis.caches.Xow_page_cache_itm page_itm = wiki.Cache_mgr().Page_cache().Get_or_load_as_itm(page_ttl);
					if (page_itm == null) return null;
					page_body = page_itm.Wtxt__redirect_or_direct();
					page_body = Xop_parser_.Parse_text_to_html(wiki, ctx, ctx.Page(), ctx.Page().Ttl(), page_body, false);
					break;
				case 1:
					tab_name = args_itm;
					if (Bry_.Has_at_bgn(tab_name, Byte_ascii.Angle_bgn) || Bry_.Has_at_end(tab_name, Byte_ascii.Angle_end)) return null;
					break;
				case 2:
					cache = Bry_.To_bool_or(args_itm, false);
					break;
				case 3:
					active = Bry_.To_bool_or(args_itm, false);
					break;
			}
		}
		return new Tabview_tab_itm(active, cache, tab_name, page_ttl_bry, page_body);
	}
	public static Tabber_tab_itm[] To_tabber_ary(Tabview_tab_itm[] ary) {
		int len = ary.length;
		Tabber_tab_itm[] rv = new Tabber_tab_itm[len];
		for (int i = 0; i < len; ++i) {
			Tabview_tab_itm src = ary[i];
			rv[i] = new Tabber_tab_itm(src.Active, src.Tab_name, src.Page_body);
		}
		return rv;
	}
}
