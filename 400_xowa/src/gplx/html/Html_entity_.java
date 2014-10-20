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
public class Html_entity_ {
	public static final String 
	  Nl_str = "&#10;"
	;
	public static final byte[]
	  Lt_bry = Bry_.new_ascii_("&lt;"), Gt_bry = Bry_.new_ascii_("&gt;")
	, Amp_bry = Bry_.new_ascii_("&amp;"), Quote_bry = Bry_.new_ascii_("&quot;")
	, Apos_num_bry = Bry_.new_ascii_("&#39;")
	, Apos_key_bry = Bry_.new_ascii_("&apos;")
	, Eq_bry = Bry_.new_ascii_("&#61;")
	, Nl_bry = Bry_.new_ascii_(Nl_str), Space_bry = Bry_.new_ascii_("&#32;")
	, Pipe_bry = Bry_.new_ascii_("&#124;")
	, Colon_bry = Bry_.new_ascii_("&#58;"), Underline_bry = Bry_.new_ascii_("&#95;"), Asterisk_bry = Bry_.new_ascii_("&#42;")
	, Brack_bgn_bry = Bry_.new_ascii_("&#91;"), Brack_end_bry = Bry_.new_ascii_("&#93;")
	, Nbsp_num_bry = Bry_.new_ascii_("&#160;")
	;
}
