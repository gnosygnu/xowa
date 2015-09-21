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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Html_tag_ {
	public static final byte[]
	  Ul_name_bry		= Bry_.new_a7("ul")
	, A_name_bry		= Bry_.new_a7("a")
	, Code_name_bry		= Bry_.new_a7("code")
	, Tr_name_bry		= Bry_.new_a7("tr")
	, Td_name_bry		= Bry_.new_a7("td")
	, Table_name_bry	= Bry_.new_a7("table")
	;
	public static final byte[]
	  Br_inl					= Bry_.new_a7("<br/>")
	, Hr_inl					= Bry_.new_a7("<hr/>")
	, Body_lhs					= Bry_.new_a7("<body>")			, Body_rhs					= Bry_.new_a7("</body>")
	, B_lhs						= Bry_.new_a7("<b>")			, B_rhs						= Bry_.new_a7("</b>")
	, I_lhs						= Bry_.new_a7("<i>")			, I_rhs						= Bry_.new_a7("</i>")
	, P_lhs						= Bry_.new_a7("<p>")			, P_rhs						= Bry_.new_a7("</p>")
	, Div_lhs					= Bry_.new_a7("<div>")			, Div_rhs					= Bry_.new_a7("</div>")
	, Html_rhs					= Bry_.new_a7("</html>")
	, Head_lhs_bgn				= Bry_.new_a7("<head")
	, Head_rhs					= Bry_.new_a7("</head>")
	, Style_lhs_w_type			= Bry_.new_a7("<style type=\"text/css\">")
	, Style_rhs					= Bry_.new_a7("</style>")
	, Script_lhs				= Bry_.new_a7("<script>")
	, Script_lhs_w_type			= Bry_.new_a7("<script type='text/javascript'>")
	, Script_rhs				= Bry_.new_a7("</script>")
	, Span_rhs					= Bry_.new_a7("</span>")
	;

	public static final String 
	  Comm_bgn_str				= "<!--"
	, Comm_end_str				= "-->"
	, Anchor_str				= "#"
	;
	public static final byte[]
	  Comm_bgn = Bry_.new_a7(Comm_bgn_str), Comm_end = Bry_.new_a7(Comm_end_str)
	;
	public static final int
	  Comm_bgn_len = Comm_bgn.length
	, Comm_end_len = Comm_end.length
	;
}
