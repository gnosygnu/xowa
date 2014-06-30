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
package gplx.html; import gplx.*;
public class Html_tags {
	public static final String 
	  A_str = "a"
	, Img_str = "img"
	;
	public static final byte[]
	  Body_lhs					= Bry_.new_ascii_("<body>")
	, Body_rhs					= Bry_.new_ascii_("</body>")
	, Html_rhs					= Bry_.new_ascii_("</html>")
	, Head_lhs_bgn				= Bry_.new_ascii_("<head")
	, Head_rhs					= Bry_.new_ascii_("</head>")
	, Style_lhs_w_type			= Bry_.new_ascii_("<style type=\"text/css\">")
	, Style_rhs					= Bry_.new_ascii_("</style>")
	, Script_lhs				= Bry_.new_ascii_("<script>")
	, Script_lhs_w_type			= Bry_.new_ascii_("<script type='text/javascript'>")
	, Script_rhs				= Bry_.new_ascii_("</script>")
	;
}
