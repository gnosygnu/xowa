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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
public class Xoh_consts {
	public static final String
	  Atr_xowa_title_str				= "xowa_title"
	, Img_w_str					= "width"
	, Img_h_str					= "height"
	;
	public static final byte[] 
	  __end = Bry_.new_a7(">")
	, __end_quote = Bry_.new_a7("\">")
	, __inline_quote = Bry_.new_a7("\"/>")
	, Space_2 = Bry_.new_a7("  ")

	, A_bgn = Bry_.new_a7("<a href=\""), A_bgn_lnki_0 = Bry_.new_a7("\" title=\""), A_mid_xowa_title = Bry_.new_a7("\" xowa_title=\"")
	, A_mid_id = Bry_.new_a7("\" id=\"xowa_lnki_")
	, A_end = Bry_.new_a7("</a>")

	, Div_bgn_open = Bry_.new_a7("<div ")
	, Div_end = Bry_.new_a7("</div>")

	, Img_bgn			= Bry_.new_a7("<img src=\"")
	, Span_bgn_open		= Bry_.new_a7("<span")
	, Span_end			= Bry_.new_a7("</span>")
	, Span_bgn			= Bry_.new_a7("<span>")

	, Pre_bgn					= Bry_.new_a7("<pre>"), Pre_end = Bry_.new_a7("</pre>")
	, Pre_bgn_open				= Bry_.new_a7("<pre")
	, Pre_style_overflow_auto	= Bry_.new_a7("overflow:auto;")
	, Pre_bgn_overflow			= Bry_.new_a7("<pre style=\"overflow:auto\">")

	, Code_bgn_closed			= Bry_.new_a7("<code>")
	, Code_bgn_open				= Bry_.new_a7("<code")
	, Code_end					= Bry_.new_a7("</code>")
	, Title_atr					= Bry_.new_a7("\" title=\"")
	, Id_atr					= Bry_.new_a7(" id=\"")
	, Style_atr					= Bry_.new_a7(" style=\"")
	, Atr_xowa_title_bry		= Bry_.new_a7(Atr_xowa_title_str)
	;

	public static final int Nbsp_int = 160;
}
