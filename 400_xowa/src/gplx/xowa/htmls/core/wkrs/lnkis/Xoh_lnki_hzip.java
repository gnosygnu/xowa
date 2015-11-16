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
	private final int[] flag_ary;
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ ( 1, 2, 3);
	public String Key() {return Xoh_hzip_dict_.Key__lnki;}
	public Xoh_lnki_hzip() {this.flag_ary = flag_bldr.Val_ary();}
	public Xoh_lnki_hzip Encode(Bry_bfr bfr, Xoh_hdoc_ctx hctx, Hzip_stat_itm stat_itm, byte[] src, Xoh_lnki_parser arg) {
		byte capt_type = arg.Capt_type();
		Xoh_anch_href_parser anch_href_parser = arg.Anch_href_parser();
		Xoa_ttl page_ttl = anch_href_parser.Page_ttl();
		int page_ns_id = page_ttl.Ns().Id();
		boolean page_ns_id_is_main = page_ns_id == Xow_ns_.Tid__main;
		int href_type = anch_href_parser.Tid();
		flag_ary[ 0] = page_ns_id_is_main ? 1 : 0;
		flag_ary[ 1] = href_type;
		flag_ary[ 2] = capt_type;

		bfr.Add(Xoh_hzip_dict_.Bry__lnki);
		Xoh_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		if (!page_ns_id_is_main)
			Xoh_lnki_dict_.Ns_encode(bfr, page_ns_id);
		switch (href_type) {
			case Xoh_anch_href_parser.Tid__site:
				bfr.Add_mid(src, anch_href_parser.Site_bgn(), anch_href_parser.Site_end()).Add_byte(Xoh_hzip_dict_.Escape);
				break;
		}
		switch (capt_type) {
			case Xoh_lnki_dict_.Capt__same:
				stat_itm.Lnki_text_n_add();
				byte[] ttl_bry = page_ns_id_is_main
					? arg.Capt_bry()				// main ns should write html_text; handles [[a]] with html of '<a href="A">a</a>'
					: page_ttl.Page_db();			// non-main ns should write page_db only; EX: "Template:A" should write "A" since "Template" will be inferred by ns_id
				bfr.Add(ttl_bry).Add_byte(Xoh_hzip_dict_.Escape);
				break;
			case Xoh_lnki_dict_.Capt__diff:
				stat_itm.Lnki_text_y_add();
				bfr.Add(arg.Anch_href_parser().Page_bry());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				bfr.Add(arg.Capt_bry());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				break;
			case Xoh_lnki_dict_.Capt__trail:
				bfr.Add(arg.Capt_bry());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				bfr.Add(arg.Trail_bry());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				break;
			case Xoh_lnki_dict_.Capt__head:
				bfr.Add(arg.Capt_bry());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				bfr.Add(arg.Trail_bry());
				bfr.Add_byte(Xoh_hzip_dict_.Escape);
				break;
		}
		return this;
	}
	public int Decode(Bry_bfr bfr, boolean write_to_bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int hook_bgn) {
		int flag = rdr.Read_int_by_base85(1);
		flag_bldr.Decode(flag);
		boolean page_ns_id_is_main = flag_bldr.Get_as_bool(0);
		byte href_type = flag_bldr.Get_as_byte(1);
		byte capt_type = flag_bldr.Get_as_byte(2);

		int ns_id = Xow_ns_.Tid__main;
		if (!page_ns_id_is_main)
			ns_id = Xoh_lnki_dict_.Ns_decode(rdr);
		int site_bgn = -1, site_end = -1;
		switch (href_type) {
			case Xoh_anch_href_parser.Tid__site:
				site_bgn = rdr.Pos();
				site_end = rdr.Find_fwd_lr();
				break;
		}
		int page_bgn = rdr.Pos();
		int page_end = rdr.Find_fwd_lr();
		int capt_bgn = -1, capt_end = -1;
		if (capt_type != Xoh_lnki_dict_.Capt__same) {
			capt_bgn = rdr.Pos();
			capt_end = rdr.Find_fwd_lr();
		}

		byte[] page_bry = null;
		if (capt_type == Xoh_lnki_dict_.Capt__head)
			page_bry = Bry_.Add(Bry_.Mid(src, page_bgn, page_end), Bry_.Mid(src, capt_bgn, capt_end));
		else
			page_bry = Bry_.Mid(src, page_bgn, page_end);
		byte[] title_bry = null, href_bry = null;
		if (href_type == Xoh_anch_href_parser.Tid__anch)
			href_bry = page_bry;
		else {
			Xow_ns ns = hctx.Wiki__ttl_parser().Ns_mgr().Ids_get_or_null(ns_id);
			Xoa_ttl ttl = hctx.Wiki__ttl_parser().Ttl_parse(ns.Id(), page_bry); if (ttl == null) rdr.Fail("invalid ttl", String_.Empty, String_.new_u8(page_bry)); // TODO: parse title based on site
			href_bry = ttl.Full_db();
			title_bry = ttl.Full_txt();
			if (href_type == Xoh_anch_href_parser.Tid__site) {
				href_bry = ttl.Page_db();	// for xwiki, use page, not full alias; EX: "wikt:A" -> "A" x> "wikt:A"
			}
		}

		// gen html
		bfr.Add(Html_bldr_.Bry__a_lhs_w_href);
		switch (href_type) {
			case Xoh_anch_href_parser.Tid__site:
				bfr.Add(Xoh_href_.Bry__wiki).Add_mid(src, site_bgn, site_end);
				bfr.Add(Xoh_href_.Bry__wiki);							// "/wiki/"
				break;
			case Xoh_anch_href_parser.Tid__anch:
				bfr.Add_byte(Byte_ascii.Hash);							// "#"
				break;
			case Xoh_anch_href_parser.Tid__wiki:
				bfr.Add(Xoh_href_.Bry__wiki);							// "/wiki/"
				break;
		}
		bfr.Add(href_bry);
		bfr.Add_str_a7("\" id=\"").Add_str_a7(gplx.xowa.parsers.lnkis.redlinks.Xopg_redlink_lnki_list.Lnki_id_prefix).Add_int_variable(hctx.Lnki__uid__nxt());
		if (href_type != Xoh_anch_href_parser.Tid__anch) {
			bfr.Add_str_a7("\" title=\"");
			bfr.Add(Html_utl.Escape_html_as_bry(title_bry));
		}
		bfr.Add_str_a7("\">");
		if (	href_type == Xoh_anch_href_parser.Tid__anch
			&&	capt_type != Xoh_lnki_dict_.Capt__diff )
			bfr.Add_byte(Byte_ascii.Hash);
		switch (capt_type) {
			case Xoh_lnki_dict_.Capt__same:
				if (ns_id == Xow_ns_.Tid__main)
					bfr.Add_mid(src, page_bgn, page_end);
				else
					bfr.Add(title_bry);
				break;
			case Xoh_lnki_dict_.Capt__diff:
				bfr.Add_mid(src, capt_bgn, capt_end);
				break;
			case Xoh_lnki_dict_.Capt__trail:
				bfr.Add_mid(src, page_bgn, page_end);
				bfr.Add_mid(src, capt_bgn, capt_end);
				break;
			case Xoh_lnki_dict_.Capt__head:
				bfr.Add_mid(src, page_bgn, page_end);
				break;
		}
		bfr.Add_str_a7("</a>");
		return rdr.Pos();
	}
	public int				Pool__idx() {return pool_idx;} private int pool_idx;
	public void				Pool__clear (Object[] args) {}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnki_hzip rv = new Xoh_lnki_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
