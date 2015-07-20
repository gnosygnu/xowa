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
package gplx.xowa.html.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_lnki_consts {
	public static final byte
	  Tid_a_cls_none = 0	, Tid_a_cls_image = 1
	, Tid_a_rel_none = 0	, Tid_a_rel_nofollow = 1
	, Tid_img_cls_none = 0	, Tid_img_cls_thumbimage = 2, Tid_img_cls_thumbborder = 3
	;
	public static final String Str_img_cls_thumbimage = "thumbimage";
	private static final byte[] 
	  Bry_anchor_class_image	= Bry_.new_a7(" class=\"image\"")
	, Bry_anchor_rel_nofollow	= Bry_.new_a7(" rel=\"nofollow\"")
	, Bry_img_cls_thumbborder	= Bry_.new_a7(" class=\"thumbborder\"")
	, Bry_img_cls_prefix		= Bry_.new_a7(" class=\"")
	;
	public static final byte[] Bry_img_cls_thumbimage	= Bry_.new_a7(" class=\"thumbimage\"");
	public static final byte[] Bry_none = Bry_.Empty;
	public static byte[] A_cls_to_bry(byte tid) {return tid == Tid_a_cls_none ? Bry_.Empty : Bry_anchor_class_image;}
	public static byte[] A_rel_to_bry(byte tid) {return tid == Tid_a_rel_none ? Bry_.Empty : Bry_anchor_rel_nofollow;}
	public static byte[] Img_cls_to_bry(byte tid, byte[] other) {
		boolean other_is_empty = Bry_.Len_eq_0(other);
		byte[] rv = null;
		switch (tid) {
			case Tid_img_cls_none:			return other_is_empty ? Bry_.Empty : Bry_.Add(Bry_img_cls_prefix, other, Byte_ascii.Quote_bry);
			case Tid_img_cls_thumbimage:	rv = Bry_img_cls_thumbimage; break;
			case Tid_img_cls_thumbborder:	rv = Bry_img_cls_thumbborder; break;
			default:						throw Err_.new_unhandled(tid);
		}
		if (other_is_empty) return rv;
		rv = Bry_.Copy(rv);									// copy for replace below
		rv[rv.length - 1] = Byte_ascii.Space;				// replace " with space
		return Bry_.Add(rv, other, Byte_ascii.Quote_bry);	// add custom cls
	}
}
