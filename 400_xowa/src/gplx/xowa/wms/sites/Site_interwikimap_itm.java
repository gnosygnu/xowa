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
package gplx.xowa.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
class Site_interwikimap_itm implements To_str_able {
	public Site_interwikimap_itm(byte[] prefix, boolean local, byte[] language, boolean localinterwiki, byte[] url, boolean protorel) {
		this.prefix = prefix;
		this.local = local;
		this.language = language;
		this.localinterwiki = localinterwiki;
		this.url = url;
		this.protorel = protorel;
	}
	public byte[] Prefix() {return prefix;} private final byte[] prefix;
	public boolean Local() {return local;} private final boolean local;
	public byte[] Language() {return language;} private final byte[] language;
	public boolean Localinterwiki() {return localinterwiki;} private final boolean localinterwiki;
	public byte[] Url() {return url;} private final byte[] url;
	public boolean Protorel() {return protorel;} private final boolean protorel;
	public String To_str() {return String_.Concat_with_obj("|", prefix, local, language, url, protorel);}
}
