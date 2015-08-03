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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
class Gallery_mgr_base_ {
	public static byte Get_or_traditional(byte[] bry) {
		Byte_obj_val rv = (Byte_obj_val)Hash.Get_by(bry);
		return rv == null ? Traditional_tid : rv.Val();
	}
	public static Gallery_mgr_base New_by_mode(byte mode) {
		switch (mode) {
			default:
			case Traditional_tid:		return new Gallery_mgr_traditional();
			case Nolines_tid:			return new Gallery_mgr_nolines();
			case Packed_tid:			return new Gallery_mgr_packed_base();
			case Packed_hover_tid:		return new Gallery_mgr_packed_hover();
			case Packed_overlay_tid:	return new Gallery_mgr_packed_overlay();
		}
	}
	public static boolean Mode_is_packed(byte v) {
		switch (v) {
			case Packed_tid:
			case Packed_hover_tid:
			case Packed_overlay_tid:	return true;
			default:					return false;
		}
	}
	public static final byte
	  Traditional_tid		= 0
	, Nolines_tid			= 1
	, Packed_tid			= 2
	, Packed_hover_tid		= 3
	, Packed_overlay_tid	= 4
	;
	public static final byte[]
	  Traditional_bry		= Bry_.new_a7("traditional")
	, Nolines_bry			= Bry_.new_a7("nolines")
	, Packed_bry			= Bry_.new_a7("packed")
	, Packed_hover_bry		= Bry_.new_a7("packed-hover")
	, Packed_overlay_bry	= Bry_.new_a7("packed-overlay")
	;
	private static final Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Traditional_bry		, Traditional_tid)
	.Add_bry_byte(Nolines_bry			, Nolines_tid)
	.Add_bry_byte(Packed_bry			, Packed_tid)
	.Add_bry_byte(Packed_hover_bry		, Packed_hover_tid)
	.Add_bry_byte(Packed_overlay_bry	, Packed_overlay_tid)
	;
}
