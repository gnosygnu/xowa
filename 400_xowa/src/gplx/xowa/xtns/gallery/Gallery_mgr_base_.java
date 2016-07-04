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
public class Gallery_mgr_base_ {
	public static final byte
	  Tid__traditional		= 0
	, Tid__nolines			= 1
	, Tid__packed			= 2
	, Tid__packed__hover	= 3
	, Tid__packed__overlay	= 4
	;
	private static final    byte[]
	  Bry__traditional		= Bry_.new_a7("traditional")
	, Bry__nolines			= Bry_.new_a7("nolines")
	, Bry__packed			= Bry_.new_a7("packed")
	, Bry__packed__hover	= Bry_.new_a7("packed-hover")
	, Bry__packed__overlay	= Bry_.new_a7("packed-overlay")
	;
	private static final    Hash_adp_bry bry_hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Bry__traditional		, Tid__traditional)
	.Add_bry_byte(Bry__nolines			, Tid__nolines)
	.Add_bry_byte(Bry__packed			, Tid__packed)
	.Add_bry_byte(Bry__packed__hover	, Tid__packed__hover)
	.Add_bry_byte(Bry__packed__overlay	, Tid__packed__overlay)
	;
	public static byte To_tid_or_traditional(byte[] bry) {
		return bry_hash.Get_as_byte_or(bry, Tid__traditional);
	}
	public static byte To_tid_or(byte[] src, int bgn, int end, byte or) {
		return bry_hash.Get_as_byte_or(src, bgn, end, or);
	}
	public static byte[] To_bry(byte tid) {
		switch (tid) {
			case Tid__traditional:		return Bry__traditional;
			case Tid__nolines:			return Bry__nolines;
			case Tid__packed:			return Bry__packed;
			case Tid__packed__hover:	return Bry__packed__hover;
			case Tid__packed__overlay:	return Bry__packed__overlay;
			default:					throw Err_.new_unhandled(tid);
		}
	}
	public static Gallery_mgr_base New(byte mode) {
		switch (mode) {
			default:
			case Tid__traditional:		return new Gallery_mgr_traditional();
			case Tid__nolines:			return new Gallery_mgr_nolines();
			case Tid__packed:			return new Gallery_mgr_packed_base();
			case Tid__packed__hover:	return new Gallery_mgr_packed_hover();
			case Tid__packed__overlay:	return new Gallery_mgr_packed_overlay();
		}
	}
	public static boolean Mode_is_packed(byte tid) {
		switch (tid) {
			case Tid__packed:
			case Tid__packed__hover:
			case Tid__packed__overlay:	return true;
			default:					return false;
		}
	}
}
