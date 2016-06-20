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
package gplx.xowa.xtns.imaps.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
public class Imap_part_ {
	public static final byte 
	  Tid_invalid		= 0
	, Tid_img			= 1
	, Tid_desc			= 2
	, Tid_comment		= 3
	, Tid_dflt			= 4
	, Tid_shape_rect	= 5
	, Tid_shape_circle	= 6
	, Tid_shape_poly	= 7
	;
	public static final    byte[] 
	  Key_dflt				= Bry_.new_a7("default")
	, Key_shape_rect		= Bry_.new_a7("rect")
	, Key_shape_circle		= Bry_.new_a7("circle")
	, Key_shape_poly		= Bry_.new_a7("poly")
	;

	public static byte[] To_shape_key(byte v) {
		switch (v) {
			case Tid_shape_rect		: return Key_shape_rect;
			case Tid_shape_circle	: return Key_shape_circle;
			case Tid_shape_poly		: return Key_shape_poly;
			default					: throw Err_.new_unhandled(v);
		}
	}
}
