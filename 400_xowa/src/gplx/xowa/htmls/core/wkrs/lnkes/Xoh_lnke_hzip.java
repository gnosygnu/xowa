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
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, Bry_parser parser, byte[] src, int hook_bgn) {
		Parse(bfr, stat_itm, parser, src, hook_bgn);
	}
	public void Parse(Bry_bfr bfr, Hzip_stat_itm stat_itm, Bry_parser parser, byte[] src, int hook_bgn) {// EX: '<a data-xotype='lnke_bgn' data-xolnke='1' href="http://a.org" class="external text" rel="nofollow">http://a.org<!--xo.hdr--></a>'
		// NOTE: not serializing caption b/c (a) caption not repeated as title and (b) finding </a> can be tricky, especially with tidy; EX: "[https://a.org b [[A]] c]"; note that lnkis can't be nested; EX: "[https://a.org b [https://b.org] c]"
		int rng_bgn = hook_bgn - 2;			// -2 to skip "<a"
		byte lnke_type = parser.Read_byte();
		parser.Chk(Byte_ascii.Apos);
		int href_bgn = parser.Fwd_end(Bry__href);
		int href_end = parser.Fwd_bgn(Byte_ascii.Quote);
		int rng_end = parser.Fwd_end(Byte_ascii.Angle_end);
		int lnke_id = 0;
		switch (lnke_type) {
			case Xoh_lnke_dict_.Type__free:				stat_itm.Lnke_txt_add();
				rng_end = parser.Fwd_end(Html_tag_.A_rhs);
				break;
			case Xoh_lnke_dict_.Type__text:	stat_itm.Lnke_brk_text_n_add(); break;
			case Xoh_lnke_dict_.Type__auto:	stat_itm.Lnke_brk_text_n_add();                    
				if (parser.Is(Byte_ascii.Brack_bgn)) {		// HTML tidy can reparent lnkes in strange ways; DATE:2015-08-25
					lnke_id = parser.Read_int_to(Byte_ascii.Brack_end);	// extract int; EX: "<a ...>[123]</a>"
					rng_end = parser.Fwd_end(Html_tag_.A_rhs);
				}
				break;
		}
		Encode_exec(bfr, src, rng_bgn, rng_end, lnke_type, href_bgn, href_end, lnke_id);
	}
	public void Encode_exec(Bry_bfr bfr, byte[] src, int rng_bgn, int rng_end, byte lnke_type, int href_bgn, int href_end, int lnke_id) {
		bfr.Del_by(2);										// delete "<h"
		bfr.Add(Xoh_hzip_dict_.Bry__lnke);					// add hook
		bfr.Add_byte(lnke_type);							// add type
		bfr.Add_mid(src, href_bgn, href_end);				// add href
		bfr.Add_byte(Xoh_hzip_dict_.Escape);
		if (lnke_type == Xoh_lnke_dict_.Type__auto)
			Xoh_hzip_int_.Encode(1, bfr, lnke_id);
	}
	public int Decode(Bry_bfr bfr, Bry_parser parser, byte[] src, int hook_bgn) {
		byte lnke_type = parser.Read_byte();
		int href_bgn = parser.Pos();
		int href_end = parser.Fwd_bgn(Xoh_hzip_dict_.Escape);
		bfr.Add(Xoh_html_dict_.Hook__lnke).Add_byte(lnke_type).Add_str_a7("' href=\"");
		bfr.Add_mid(src, href_bgn, href_end);
		bfr.Add(Xoh_lnke_dict_.Html__atr__0).Add(Xoh_lnke_dict_.To_html_class(lnke_type)).Add(Xoh_lnke_dict_.Html__rhs_end);
		switch (lnke_type)  {
			case Xoh_lnke_dict_.Type__free:
				bfr.Add_mid(src, href_bgn, href_end).Add(Html_tag_.A_rhs);
				break;
			case Xoh_lnke_dict_.Type__auto:
				int lnke_id = parser.Read_int_by_base85(1);
				if (lnke_id != 0)	// will be 0 when reparented by tidy
					bfr.Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(lnke_id).Add_byte(Byte_ascii.Brack_end).Add(Html_tag_.A_rhs);
				break;
			case Xoh_lnke_dict_.Type__text: break;	// caption not serialized
		}
		return parser.Pos();
	}
	private static final byte[] Bry__href = Bry_.new_a7(" href=\"");
}
