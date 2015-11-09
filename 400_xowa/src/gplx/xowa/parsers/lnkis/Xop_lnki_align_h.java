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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_lnki_align_h {
	public static final byte Null = 0, None = 1, Left = 2, Center = 3, Right = 4;
	public static final byte[][] Html_names = new byte[][]
	{ Object_.Bry__null
	, Bry_.new_a7("none")
	, Bry_.new_a7("left")
	, Bry_.new_a7("center")
	, Bry_.new_a7("right")
	};
	public static final Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("tnone"	, None)
	.Add_str_byte("tleft"	, Left)
	.Add_str_byte("tcenter"	, Center)
	.Add_str_byte("tright"	, Right)
	;
}
class Xop_lnki_align_v {
	public static final byte None = 0, Top		= 1, Middle		= 2, Bottom		 = 4, Super		= 8, Sub        = 16, TextTop = 32, TextBottom = 64, Baseline = 127;
}
