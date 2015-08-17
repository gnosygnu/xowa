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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
class Site_showhook_itm implements To_str_able {
	public Site_showhook_itm(byte[] name, byte[] scribunto, byte[][] subscribers) {
		this.name = name; this.scribunto = scribunto; this.subscribers = subscribers;
	}
	public byte[] Name() {return name;} private final byte[] name;
	public byte[] Scribunto() {return scribunto;} private final byte[] scribunto;
	public byte[][] Subscribers() {return subscribers;} private final byte[][] subscribers;
	public String To_str() {return String_.Concat_with_obj("|", name, scribunto, String_.Concat_with_obj(";", (Object[])subscribers));}
}
