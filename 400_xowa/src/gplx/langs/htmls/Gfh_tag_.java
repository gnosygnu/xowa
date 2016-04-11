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
public class Gfh_tag_ {
	public static final int 
	  Id__comment	= -3
	, Id__eos		= -2
	, Id__any		= -1
	, Id__unknown	=  0
	, Id__h2		=  2
	, Id__h3		=  3
	, Id__h4		=  4
	, Id__h5		=  5
	, Id__h6		=  6
	, Id__a			=  7
	, Id__span		=  8
	, Id__div		=  9
	, Id__img		= 10
	, Id__ul		= 11
	, Id__li		= 12
	, Id__p			= 13
	, Id__br		= 14
	, Id__hr		= 15
	, Id__td		= 16
	;
	public static final    byte[]
	  Bry__a			= Bry_.new_a7("a")
	, Bry__ul			= Bry_.new_a7("ul")
	, Bry__td			= Bry_.new_a7("td")
	, Bry__th			= Bry_.new_a7("th")
	;
	public static final    Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_bry_int(Bry__a			, Id__a)
	.Add_str_int("h2"			, Id__h2)
	.Add_str_int("h3"			, Id__h3)
	.Add_str_int("h4"			, Id__h4)
	.Add_str_int("h5"			, Id__h5)
	.Add_str_int("h6"			, Id__h6)
	.Add_str_int("span"			, Id__span)
	.Add_str_int("div"			, Id__div)
	.Add_str_int("img"			, Id__img)
	.Add_str_int("br"			, Id__br)
	.Add_str_int("hr"			, Id__hr)
	.Add_str_int("ul"			, Id__ul)
	.Add_str_int("li"			, Id__li)
	.Add_str_int("td"			, Id__td)
	.Add_str_int("p"			, Id__p)
	;
	public static String To_str(int tid) {
		switch (tid) {
			case Id__eos:			return "EOS";
			case Id__any:			return "any";
			case Id__unknown:		return "unknown";
			case Id__comment:		return "comment";
			case Id__h2:			return "h2";
			case Id__h3:			return "h2";
			case Id__h4:			return "h2";
			case Id__h5:			return "h2";
			case Id__h6:			return "h2";
			case Id__a:				return "a";
			case Id__span:			return "span";
			case Id__div:			return "div";
			case Id__img:			return "img";
			case Id__p:				return "p";
			case Id__br:			return "br";
			case Id__hr:			return "hr";
			case Id__td:			return "td";
			case Id__ul:			return "ul";
			case Id__li:			return "li";
			default:				throw Err_.new_unhandled(tid);
		}
	}
	public static final    byte[]
	  Br_inl					= Bry_.new_a7("<br/>")
	, Br_lhs					= Bry_.new_a7("<br>")
	, Hr_inl					= Bry_.new_a7("<hr/>")
	, Body_lhs					= Bry_.new_a7("<body>")			, Body_rhs					= Bry_.new_a7("</body>")
	, B_lhs						= Bry_.new_a7("<b>")			, B_rhs						= Bry_.new_a7("</b>")
	, I_lhs						= Bry_.new_a7("<i>")			, I_rhs						= Bry_.new_a7("</i>")
	, P_lhs						= Bry_.new_a7("<p>")			, P_rhs						= Bry_.new_a7("</p>")
	, Pre_lhs					= Bry_.new_a7("<pre>")			, Pre_rhs					= Bry_.new_a7("</pre>")
	, Div_lhs					= Bry_.new_a7("<div>")			, Div_rhs					= Bry_.new_a7("</div>")
	, Html_rhs					= Bry_.new_a7("</html>")
	, Head_lhs_bgn				= Bry_.new_a7("<head")
	, Head_rhs					= Bry_.new_a7("</head>")
	, Style_lhs_w_type			= Bry_.new_a7("<style type=\"text/css\">")
	, Style_rhs					= Bry_.new_a7("</style>")
	, Script_lhs				= Bry_.new_a7("<script>")
	, Script_lhs_w_type			= Bry_.new_a7("<script type='text/javascript'>")
	, Script_rhs				= Bry_.new_a7("</script>")
	, Span_lhs					= Bry_.new_a7("<span")
	, Span_rhs					= Bry_.new_a7("</span>")
	, Strong_lhs				= Bry_.new_a7("<strong>")
	, Strong_rhs				= Bry_.new_a7("</strong>")
	;
	public static final String 
	  Comm_bgn_str				= "<!--"
	, Comm_end_str				= "-->"
	, Anchor_str				= "#"
	;
	public static final    byte[]
	  Comm_bgn = Bry_.new_a7(Comm_bgn_str), Comm_end = Bry_.new_a7(Comm_end_str)
	;
	public static final    int
	  Comm_bgn_len = Comm_bgn.length
	, Comm_end_len = Comm_end.length
	;
}
