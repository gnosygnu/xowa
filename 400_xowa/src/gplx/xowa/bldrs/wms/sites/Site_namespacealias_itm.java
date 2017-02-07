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
class Site_namespacealias_itm implements To_str_able {
	public Site_namespacealias_itm(int id, byte[] alias) {
		this.id = id; this.alias = alias;
		this.key = Bry_.Add_w_dlm(Byte_ascii.Pipe, Int_.To_bry(id), alias);
	}
	public byte[] Key() {return key;} private final byte[] key;
	public int Id() {return id;} private final int id;
	public byte[] Alias() {return alias;} private final byte[] alias;
	public String To_str() {return String_.Concat_with_obj("|", id, alias);}
}
