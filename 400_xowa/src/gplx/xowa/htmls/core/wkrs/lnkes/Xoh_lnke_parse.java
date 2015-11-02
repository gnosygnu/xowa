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
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.htmls.core.parsers.*;
public class Xoh_lnke_parse {
	public int Parse(Xoh_wkr wkr, Html_tag_rdr rdr, byte[] src, Html_tag lnke) {		// <a rel="nofollow" class="external autonumber_id" href="http://a.org">[1]</a>
		int tag_bgn = lnke.Src_bgn(), tag_end = lnke.Src_end();
		byte[] href = lnke.Atrs__get_by(Html_atr_.Bry__href).Val();
		byte[] cls	= lnke.Atrs__get_by(Html_atr_.Bry__class).Val();
		byte lnke_type = Parse_lnke_type(cls, 0, cls.length); if (lnke_type == Byte_ascii.Max_7_bit) return tag_end;
		int autonumber_id = 0;
		switch (lnke_type) {
			case Xoh_lnke_dict_.Type__free:
				tag_end = rdr.Tag__move_fwd_tail(Html_tag_.Id__a).Src_end();		// find '</a>'; note that free is not recursive; EX: "https://a.org"
				break;
			case Xoh_lnke_dict_.Type__text:
				break;
			case Xoh_lnke_dict_.Type__auto:
				if (rdr.Read_and_move(Byte_ascii.Brack_bgn)) {						// HTML tidy can reparent lnkes in strange ways; DATE:2015-08-25
					autonumber_id = rdr.Read_int_to(Byte_ascii.Brack_end, 0);		// extract int; EX: "<a ...>[123]</a>"
					tag_end = rdr.Tag__move_fwd_tail(Html_tag_.Id__a).Src_end();	// find '</a>'; note that auto is not recursive; EX: "[https://a.org]"
				}
				break;
		}
		wkr.On_lnke(tag_bgn, tag_end, lnke_type, autonumber_id, href);
		return tag_end;
	}
	private static byte Parse_lnke_type(byte[] src, int cls_bgn, int cls_end) { // "external autonumber"
		int space = Bry_find_.Find_fwd(src, Byte_ascii.Space, cls_bgn, cls_end);
		return Xoh_lnke_dict_.Hash.Get_as_byte_or(src, space + 1, cls_end, Byte_ascii.Max_7_bit);
	}
}
