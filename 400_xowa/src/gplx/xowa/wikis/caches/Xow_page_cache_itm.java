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
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_page_cache_itm {
	public Xow_page_cache_itm(Xoa_ttl ttl, byte[] src) {this.ttl = ttl; this.src = src;}
	public Xoa_ttl Ttl() {return ttl;} private Xoa_ttl ttl;
	public byte[] Src() {return src;} private byte[] src;
	public static final Xow_page_cache_itm Null = null;
}
