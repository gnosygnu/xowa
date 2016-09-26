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
package gplx.xowa.files.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_img_mode_ {
	public static final byte 
	  Tid__orig = 0
	, Tid__thumb = 1
	;
	public static byte By_bool(boolean is_thumb) {return is_thumb ? Tid__thumb : Tid__orig;}
	public static final    byte[][] Names_ary = new byte[][] {Bry_.new_a7("orig"), Bry_.new_a7("thumb")};
}
