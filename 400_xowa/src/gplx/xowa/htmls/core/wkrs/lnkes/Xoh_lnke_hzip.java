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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.xowa.wikis.ttls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.hzips.stats.*; 	
public class Xoh_lnke_hzip implements Xoh_hzip_wkr {
	public String Key() {return Xoh_hzip_dict_.Key__lnke;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, int rng_bgn, int rng_end, byte lnke_type, int href_bgn, int href_end, int lnke_id) {
		switch (lnke_type) {
			case Xoh_lnke_dict_.Type__free:		stat_itm.Lnke_txt_add();break;
			case Xoh_lnke_dict_.Type__text:		stat_itm.Lnke_brk_text_y_add(); break;
			case Xoh_lnke_dict_.Type__auto:		stat_itm.Lnke_brk_text_n_add(); break;
		}
		bfr.Add(Xoh_hzip_dict_.Bry__lnke);					// add hook
		bfr.Add_byte(lnke_type);							// add type
		bfr.Add_mid(src, href_bgn, href_end);				// add href
		bfr.Add_byte(Xoh_hzip_dict_.Escape);
		if (lnke_type == Xoh_lnke_dict_.Type__auto)
			Xoh_hzip_int_.Encode(1, bfr, lnke_id);
	}
	public int Decode(Bry_bfr bfr, Xoh_decode_ctx ctx, Bry_rdr rdr, byte[] src, int hook_bgn) {
		byte lnke_type = rdr.Read_byte();
		int href_bgn = rdr.Pos();
		int href_end = rdr.Find_fwd_lr(Xoh_hzip_dict_.Escape);
		bfr.Add(Html_bldr_.Bry__a_lhs_w_href);
		bfr.Add_mid(src, href_bgn, href_end);
		bfr.Add(Xoh_lnke_dict_.Html__atr__0).Add(Xoh_lnke_dict_.To_html_class(lnke_type)).Add(Xoh_lnke_dict_.Html__rhs_end);
		switch (lnke_type)  {
			case Xoh_lnke_dict_.Type__free:
				bfr.Add_mid(src, href_bgn, href_end).Add(Html_bldr_.Bry__a_rhs);
				break;
			case Xoh_lnke_dict_.Type__auto:
				int lnke_id = rdr.Read_int_by_base85(1);
				if (lnke_id != 0)	// will be 0 when reparented by tidy
					bfr.Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(lnke_id).Add_byte(Byte_ascii.Brack_end).Add(Html_bldr_.Bry__a_rhs);
				break;
			case Xoh_lnke_dict_.Type__text:
				break;
		}
		return rdr.Pos();
	}
}
