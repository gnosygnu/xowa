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
	;
	public static final byte[] 
	  __end = Bry_.new_ascii_(">")
	, __end_quote = Bry_.new_ascii_("\">")
	, __inline_quote = Bry_.new_ascii_("\"/>")
	, Space_2 = Bry_.new_ascii_("  ")

	, A_bgn = Bry_.new_ascii_("<a href=\""), A_bgn_lnki_0 = Bry_.new_ascii_("\" title=\""), A_mid_xowa_title = Bry_.new_ascii_("\" xowa_title=\"")
	, A_mid_id = Bry_.new_ascii_("\" id=\"xowa_lnki_")
	, A_end = Bry_.new_ascii_("</a>")

	, Div_bgn_open = Bry_.new_ascii_("<div ")
	, Div_end = Bry_.new_ascii_("</div>")

	, Img_bgn			= Bry_.new_ascii_("<img src=\"")
	, Span_bgn_open		= Bry_.new_ascii_("<span")
	, Span_end			= Bry_.new_ascii_("</span>")
	, Span_bgn			= Bry_.new_ascii_("<span>")

	, Pre_bgn = Bry_.new_ascii_("<pre>"), Pre_end = Bry_.new_ascii_("</pre>")
	, Pre_bgn_open = Bry_.new_ascii_("<pre")
	, Pre_style_overflow_auto = Bry_.new_ascii_("overflow:auto;")
	, Pre_bgn_overflow = Bry_.new_ascii_("<pre style=\"overflow:auto\">")

	, Code_bgn_closed = Bry_.new_ascii_("<code>")
	, Code_bgn_open = Bry_.new_ascii_("<code")
	, Code_end = Bry_.new_ascii_("</code>")
	, Title_atr = Bry_.new_ascii_("\" title=\"")
	, Id_atr = Bry_.new_ascii_(" id=\"")
	, Style_atr = Bry_.new_ascii_(" style=\"")
	, Atr_xowa_title_bry				= Bry_.new_ascii_(Atr_xowa_title_str)
	;

	public static final int Nbsp_int = 160;
}
