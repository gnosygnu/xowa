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
package gplx.xowa.htmls.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
public class Xoh_ttl_matcher {
	public int Trail_bgn() {return trail_bgn;} private int trail_bgn;
	public byte Match(Xow_ttl_parser ttl_parser, byte[] page_bry, int page_bgn, int page_end, byte[] capt_bry, int capt_bgn, int capt_end) {
		trail_bgn = -1;
		int page_len = page_end - page_bgn;
		Xoa_ttl page = ttl_parser.Ttl_parse(page_bry, page_bgn, page_end); if (page == null) throw Err_.new_("", "invalid page", "page", page_bry);
		Xow_ns ns = page.Ns();
		if (ns.Id() != Xow_ns_.Id_main) {
			byte[] ns_name_txt = ns.Name_txt_w_colon();			// EX: 11="Template talk:"
			int ns_name_txt_len = ns_name_txt.length;
			int ns_name_txt_end = capt_bgn + ns_name_txt_len;
			if (Bry_.Match(capt_bry, capt_bgn, ns_name_txt_end, ns_name_txt, 0, ns_name_txt_len))
				capt_bgn = page_bgn = ns_name_txt_end;
			else
				return Tid__diff;
		}
		int capt_len = capt_end - capt_bgn;
		for (int i = 0; i < capt_len; ++i) {
			byte capt_byte = capt_bry[i + capt_bgn];
			if (i == page_len) {
				trail_bgn = i + capt_bgn;
				return Tid__trail;
			}
			byte page_byte = page_bry[i + page_bgn];
			if (page_byte == capt_byte) continue;
			if (	i == 0
				&&	ns.Case_match() == Xow_ns_case_.Tid__1st
				&&	capt_byte >= Byte_ascii.Ltr_a && capt_byte <= Byte_ascii.Ltr_z
				&&	(capt_byte - page_byte) == 32
				)
				continue;
			if (	capt_byte == Byte_ascii.Space
				&&	page_byte == Byte_ascii.Underline
				)
				continue;
			return Tid__diff;
		}
		return Tid__same;
	}
	public static final byte
	  Tid__same		= Byte_ascii.Num_1	// "A", "A"; "1|A"
	, Tid__diff		= Byte_ascii.Num_2	// "A", "B"; "2|A|B"
	, Tid__trail	= Byte_ascii.Num_3	// "A", "As"; "3|A|s"
	;
}
