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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
public class Xoh_lnki_consts {
	public static final byte
	  Tid_a_cls_none = 0	, Tid_a_cls_image = 1
	, Tid_a_rel_none = 0	, Tid_a_rel_nofollow = 1
	;
	private static final byte[] 
	  Bry_anchor_class_image	= Bry_.new_a7(" class=\"image\"")
	, Bry_anchor_rel_nofollow	= Bry_.new_a7(" rel=\"nofollow\"")
	;
	public static byte[] A_cls_to_bry(byte tid) {return tid == Tid_a_cls_none ? Bry_.Empty : Bry_anchor_class_image;}
	public static byte[] A_rel_to_bry(byte tid) {return tid == Tid_a_rel_none ? Bry_.Empty : Bry_anchor_rel_nofollow;}
}
