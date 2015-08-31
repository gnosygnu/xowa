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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
class Math_func_ml_parser {
	private byte[] src; public int src_len;
	private Btrie_slim_mgr prop_trie;
	public void Parse(byte[] src) {
		this.prop_trie = Math_prop_itm.Trie;
		this.src = src; this.src_len = src.length;
		int pos = Bry_finder.Find_fwd(src, Bry__parse_bgn, 0);			if (pos == Bry_finder.Not_found) throw Err_.new_("ml_parser", "unable to find beginning of ml file");
		pos = Bry_finder.Find_fwd(src, Byte_ascii.Quote, pos, src_len);	if (pos == Bry_finder.Not_found) throw Err_.new_("ml_parser", "unable to find first func");
		while (true) {
			pos = Parse_itm(pos);
			if (pos == Bry_finder.Not_found) break;
		}
	}
	private int Parse_itm(int pos) {	// pos should start at quote
		int end_quote = Bry_finder.Find_fwd(src, Byte_ascii.Quote, pos + 1);	if (pos == Bry_finder.Not_found) throw Err_.new_("ml_parser", "unable to find end quote", "excerpt", Excerpt(pos));
		byte[] key = Bry_.Mid_by_len_safe(src, pos + 1, end_quote);
		Math_func_itm func_itm = new Math_func_itm(key);
		pos = end_quote + 1;
		pos = Bry_finder.Find_fwd(src, Bry__kv_spr, pos);	if (pos == Bry_finder.Not_found) throw Err_.new_("ml_parser", "unable to find kv spr", "excerpt", Excerpt(pos));
		while (true) {
			pos = Bry_finder.Find_fwd_while_ws(src, pos, src_len);
			if (pos == src_len) return -1;
			byte b = src[pos];
			Object o = prop_trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o == null) {
				// throw error
				break;
			}
			else {
				Int_obj_val prop_obj = (Int_obj_val)o;					
				func_itm.Props__add(prop_obj.Val());
			}
		}
		return pos;
	}
	private byte[] Excerpt(int bgn) {return Bry_.Mid_by_len_safe(src, bgn, bgn + 25);}
	private static final byte[]
		Bry__parse_bgn		= Bry_.new_a7("let find = function")
	,	Bry__kv_spr			= Bry_.new_a7("->")
	;
}
/*
let find = function
      "\\alpha"            -> LITERAL (HTMLABLEC (FONT_UF,  "\\alpha ", "&alpha;"))
    | "\\Alpha"            -> (tex_use_ams (); LITERAL (HTMLABLEC (FONT_UF,
    "\\mathrm{A}", "&Alpha;")))
    | "\\beta"             -> LITERAL (HTMLABLEC (FONT_UF,  "\\beta ",  "&beta;"))
    | "\\Beta"             -> (tex_use_ams (); LITERAL (HTMLABLEC (FONT_UF,
    "\\mathrm{B}",  "&Beta;")))
    | "\\gamma"            -> LITERAL (HTMLABLEC (FONT_UF,  "\\gamma ", "&gamma;"))
    | "\\text"             -> raise (Failure "malformatted \\text")
    | "\\frac"             -> FUN_AR2h ("\\frac ", fun num den -> Html.html_render [num], "<hr style=\"{background: black}\"/>", Html.html_render [den])
*/

