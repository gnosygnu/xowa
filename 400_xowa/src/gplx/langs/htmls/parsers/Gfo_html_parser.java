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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.*;
import gplx.xowa.parsers.xndes.*;
class Gfo_html_parser {
	private final Gfo_msg_log msg_log = Gfo_msg_log.Test();
	private final Xop_xatr_parser xatr_parser = new Xop_xatr_parser();
	public void Parse(Gfo_html_wkr handler, byte[] src, int bgn, int end) {
//			int src_len = src.length;
//			int prv_pos = 0; 
//			int css_find_bgn_len = Css_find_bgn.length;
//			byte[] protocol_prefix_bry = Bry_.new_u8(protocol_prefix);
//			while (true) {
//				int url_bgn = Bry_find_.Find_fwd(src, Css_find_bgn, prv_pos);	 				if (url_bgn == Bry_.NotFound) break;	// nothing left; stop
//				url_bgn += css_find_bgn_len;
//				int url_end = Bry_find_.Find_fwd(src, Byte_ascii.Quote, url_bgn, src_len); 	if (url_end == Bry_.NotFound) {usr_dlg.Warn_many("", "main_page.css_parse", "could not find css; pos='~{0}' text='~{1}'", url_bgn, String_.new_u8__by_len(src, url_bgn, url_bgn + 32)); break;}
//				byte[] css_url_bry = Bry_.Mid(src, url_bgn, url_end);
//				css_url_bry = Bry_.Replace(css_url_bry, Css_amp_find, Css_amp_repl);		// &amp; -> &
//				css_url_bry = url_encoder.Decode(css_url_bry);								// %2C ->		%7C -> |
//				css_url_bry = Bry_.Add(protocol_prefix_bry, css_url_bry);
//				rv.Add(String_.new_u8(css_url_bry));
//				prv_pos = url_end;
//			}
//			return rv.XtoStrAry();
		int src_len = src.length; int pos = 0;
		while (pos < src_len) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Angle_bgn:
					pos = Parse_node(handler, src, end, pos, pos + 1);
					break;
				default:
					++pos;
					break;
			}
		}
	}
	private int Parse_node(Gfo_html_wkr handler, byte[] src, int end, int tkn_bgn, int tkn_end) {
		int name_bgn = tkn_end;
		int name_end = Bry_find_.Find_fwd_until_ws(src, name_bgn, end);
		if (name_end == Bry_find_.Not_found) return end;	// EOS; EX: "<abcEOS"
		if (name_bgn == name_end) return tkn_end;			// ws; EX: "< "
		Object o = handler.Get_or_null(src, name_bgn, name_end);
		if (o == null) return name_end;						// unknown name: EX: "<unknown >"
		int node_end = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, name_end, end);
		if (node_end == Bry_find_.Not_found) return end;	// EOS; EX: "<name lots_of_text_but_no_gt EOS"
		Xop_xatr_itm[] xatr_ary = xatr_parser.Parse(msg_log, src, name_end, node_end);
		Gfo_html_tkn tkn = (Gfo_html_tkn)o;
		tkn.Process(src, Xop_xatr_hash.new_ary(src, xatr_ary));
		return node_end;
	}
}
