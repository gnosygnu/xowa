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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_apos_tkn_ {
	public static final int 
	  Cmd_nil = 0
	, Cmd_i_bgn = 1, Cmd_i_end = 2, Cmd_b_bgn = 3, Cmd_b_end = 4
	, Cmd_bi_bgn = 5, Cmd_ib_bgn = 6, Cmd_ib_end = 7, Cmd_bi_end = 8
	, Cmd_bi_end__b_bgn = 9, Cmd_ib_end__i_bgn = 10, Cmd_b_end__i_bgn = 11, Cmd_i_end__b_bgn = 12;
	public static final byte[][] Cmds 
	= new byte[][] 
	{ Bry_.new_ascii_("nil")
	, Bry_.new_ascii_("i+"), Bry_.new_ascii_("i-"), Bry_.new_ascii_("b+"), Bry_.new_ascii_("b-")
	, Bry_.new_ascii_("bi+"), Bry_.new_ascii_("ib+"), Bry_.new_ascii_("ib-"), Bry_.new_ascii_("bi-")
	, Bry_.new_ascii_("bi-b+"), Bry_.new_ascii_("ib-i+"), Bry_.new_ascii_("b-i+"), Bry_.new_ascii_("i-b+")
	};
	public static String Cmd_str(int id) {return String_.new_utf8_(Cmds[id]);}
	public static final int Len_ital = 2, Len_bold = 3, Len_dual = 5, Len_apos_bold = 4;
	public static final int Typ_ital = 2, Typ_bold = 3, Typ_dual = 5;
	public static final int State_nil = 0, State_i = 1, State_b = 2, State_bi = 3, State_ib = 4, State_dual = 5;
}
