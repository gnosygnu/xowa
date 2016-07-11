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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Xoh_hzip_dict_ {
	public static final    byte Escape = Byte_.By_int(27);				// SERIALIZED: 27=escape byte
	public static final    byte[] Escape_bry = Bry_.New_by_ints(27);	// SERIALIZED
	public static final int
	  Tid__timeline			= -10
	, Tid__gallery			= -11
	, Tid__root				=  0
	, Tid__escape			=  1
	, Tid__xnde				=  2
	, Tid__lnke				=  3
	, Tid__lnki				=  4
	, Tid__hdr				=  5
	, Tid__img				=  6
	, Tid__thm				=  7
	, Tid__gly				=  8
	, Tid__img_bare			=  9
	, Tid__toc				= 10
	, Tid__pgbnr			= 11
	;
	public static final    String
	  Key__timeline				= "timeline"
	, Key__gallery				= "gallery"
	, Key__escape				= "escape"
	, Key__hdr					= "hdr"
	, Key__lnke					= "lnke"
	, Key__lnki					= "lnki"
	, Key__img					= "img"
	, Key__thm					= "thm"
	, Key__gly					= "gly"
	, Key__xnde					= "xnde"
	, Key__img_bare				= "img_bare"
	, Key__toc					= "toc"
	, Key__pgbnr				= "pgbnr"
	;
	public static final int Hzip__none = 0, Hzip__v1 = 1;
}
