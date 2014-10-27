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
package gplx.xowa.hdumps.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
class Xoa_hzip_dict {// SERIALIZED
	public static final byte Escape = Byte_.int_(255);
	public static final byte[] Escape_bry = Bry_.ints_(255);
	public static final byte
	  Tid_a_end				= 0
	, Tid_lnki_ttl			= 1
	, Tid_lnki_capt			= 2
	, Tid_lnke				= 3	// <a href="http://xowa.sourceforge.net/blog.html" class="external text" rel="nofollow">blog</a> ->  255,4,h,xowa.sourceforge/blog.html,255,blog,255,0
	;
	public static final byte[]
	  Bry_a_end				= Bry_.ints_(Escape, Tid_a_end)
	, Bry_lnki_ttl			= Bry_.ints_(Escape, Tid_lnki_ttl)
	, Bry_lnki_capt			= Bry_.ints_(Escape, Tid_lnki_capt)
	, Bry_lnke				= Bry_.ints_(Escape, Tid_lnke)	// <a href="http://xowa.sourceforge.net/blog.html" class="external text" rel="nofollow">blog</a> ->  255,4,h,xowa.sourceforge/blog.html,255,blog,255,0
	;
}
