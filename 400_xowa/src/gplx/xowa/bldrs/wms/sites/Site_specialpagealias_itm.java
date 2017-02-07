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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
class Site_specialpagealias_itm implements To_str_able {
	public Site_specialpagealias_itm(byte[] realname, byte[][] aliases) {this.realname = realname; this.aliases = aliases;}
	public byte[] Realname() {return realname;} private final byte[] realname;
	public byte[][] Aliases() {return aliases;} private final byte[][] aliases;
	public String To_str() {return String_.Concat_with_obj("|", realname, String_.Concat_with_obj(";", (Object[])aliases));}
}
