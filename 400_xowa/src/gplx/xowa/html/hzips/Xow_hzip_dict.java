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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
class Xow_hzip_dict {// SERIALIZED
	public static final byte Escape = Byte_.By_int(255);
	public static final byte[] Escape_bry = Bry_.ints_(255);
	public static final byte
	  Tid_a_rhs					= 0
	, Tid_lnki_text_n			= 1
	, Tid_lnki_text_y			= 2
	, Tid_lnke_txt				= 3
	, Tid_lnke_brk_text_n		= 4
	, Tid_lnke_brk_text_y		= 5
	, Tid_img_full				= 6
	, Tid_hdr_lhs				= 7
	, Tid_hdr_rhs				= 8
	;
	public static final byte[]
	  Bry_a_rhs					= Bry_.ints_(Escape, Tid_a_rhs)
	, Bry_lnki_text_n			= Bry_.ints_(Escape, Tid_lnki_text_n)
	, Bry_lnki_text_y			= Bry_.ints_(Escape, Tid_lnki_text_y)
	, Bry_lnke_txt				= Bry_.ints_(Escape, Tid_lnke_txt)
	, Bry_lnke_brk_text_n		= Bry_.ints_(Escape, Tid_lnke_brk_text_n)
	, Bry_lnke_brk_text_y		= Bry_.ints_(Escape, Tid_lnke_brk_text_y)
	, Bry_img_full				= Bry_.ints_(Escape, Tid_img_full)
	, Bry_hdr_lhs				= Bry_.ints_(Escape, Tid_hdr_lhs)
	, Bry_hdr_rhs				= Bry_.ints_(Escape, Tid_hdr_rhs)
	;
}
