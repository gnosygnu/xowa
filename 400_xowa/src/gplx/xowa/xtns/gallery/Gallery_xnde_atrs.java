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
class Gallery_xnde_atrs {
	public static final byte 
	  Tid__mode			= 0
	, Tid__perrow		= 1
	, Tid__widths		= 2
	, Tid__heights		= 3
	, Tid__caption		= 4
	, Tid__showfilename	= 5
	, Tid__style		= 6
	, Tid__class		= 7
	;
	public static final    Hash_adp_bry Key_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("mode"			, Tid__mode)
	.Add_str_byte("perrow"			, Tid__perrow)
	.Add_str_byte("widths"			, Tid__widths)
	.Add_str_byte("heights"			, Tid__heights)
	.Add_str_byte("caption"			, Tid__caption)
	.Add_str_byte("showfilename"	, Tid__showfilename)
	.Add_str_byte("style"			, Tid__style)
	.Add_str_byte("class"			, Tid__class)
	;
}
