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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.encoders.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.hzips.stats.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_thm_hzip implements Xoh_hzip_wkr {
	private Xow_ttl_parser ttl_parser; private int uid;
	public void Ttl_parser_(Xow_ttl_parser ttl_parser) {this.ttl_parser = ttl_parser; this.uid = 1;}  // NOTE: should be 0, but for historical reasons, 1st lnki starts at 2; EX: id='xowa_lnki_2'
	public String Key() {return Xoh_hzip_dict_.Key__lnki;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, int rng_bgn, int rng_end) {
		bfr.Add_mid(src, rng_bgn, rng_end);
	}
	public int Decode(Bry_bfr bfr, Xoh_decode_ctx ctx, Bry_rdr rdr, byte[] src, int hook_bgn) {
		byte lnki_type = rdr.Read_byte();
		int ns_id = rdr.Read_int_by_base85(1);
		int site_bgn = -1, site_end = -1;
		if (rdr.Is(Xoh_hzip_dict_.Escape)) {
			site_bgn = rdr.Pos();
			site_end = rdr.Find_fwd_lr(Xoh_hzip_dict_.Escape);
		}
		boolean site_exists = site_end - site_bgn > 0;
		int page_bgn = rdr.Pos();
		int page_end = rdr.Find_fwd_lr(Xoh_hzip_dict_.Escape);
		int capt_bgn = -1, capt_end = -1;
		if (lnki_type != Xoh_lnki_dict_.Type__same) {
			capt_bgn = rdr.Pos();
			capt_end = rdr.Find_fwd_lr(Xoh_hzip_dict_.Escape);
		}

		byte[] page_bry = Bry_.Mid(src, page_bgn, page_end);
		Xoa_ttl ttl = ttl_parser.Ttl_parse(ns_id, page_bry); if (ttl == null) rdr.Fail("invalid ttl", "mid", String_.new_u8(page_bry)); // TODO: parse title based on site
		byte[] ttl__full_db = ttl.Full_db();

		// gen html
		bfr.Add(Html_bldr_.Bry__a_lhs_w_href);
		if (site_exists) bfr.Add_str_a7("/site/").Add_mid(src, site_bgn, site_end);
		bfr.Add(Xoh_href_.Bry__wiki);							// "/wiki/"
		bfr.Add(ttl.Full_db());
		bfr.Add_str_a7("\" id=\"").Add_str_a7(gplx.xowa.parsers.lnkis.redlinks.Xopg_redlink_lnki_list.Lnki_id_prefix).Add_int_variable(++uid);
		bfr.Add_str_a7("\" title=\"");
		byte[] title_bry = site_exists ? ttl.Page_db() : ttl__full_db;	// for xwiki, use page, not full alias; EX: "wikt:A" -> "A" x> "wikt:A"
		bfr.Add(Html_utl.Escape_html_as_bry(title_bry)).Add_str_a7("\">");
		switch (lnki_type) {
			case Xoh_lnki_dict_.Type__same:
				if (ttl.Ns().Id_is_main())
					bfr.Add_mid(src, page_bgn, page_end);
				else
					bfr.Add(ttl.Full_txt());
				break;
			case Xoh_lnki_dict_.Type__diff:
				bfr.Add_mid(src, capt_bgn, capt_end);
				break;
			case Xoh_lnki_dict_.Type__trail:
				bfr.Add_mid(src, page_bgn, page_end);
				bfr.Add_mid(src, capt_bgn, capt_end);
				break;
		}
		bfr.Add_str_a7("</a>");
		return rdr.Pos();
	}
//		, Find_img_xatrs		= Bry_.new_a7("xatrs='")
//		private static int[] Save_img_full_pow = new int[] {0, 1, 2};
//		private int Save_img_full(Bry_bfr bfr, Bry_rdr_old rdr, Hzip_stat_itm stat_itm, byte[] src, int src_len, int bgn, int pos) {
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
