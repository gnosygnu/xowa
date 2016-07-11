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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_protection {
	public byte[]	User() {return user;} public Xopg_db_protection User_(byte[] v) {user = v; return this;} private byte[] user = Bry_.Empty;
	public byte[]	Protection_level() {return protection_level;} public Xopg_db_protection Protection_level_(byte[] v) {protection_level = v; return this;} private byte[] protection_level = Bry_.Empty;
	public byte[]	Protection_expiry() {return protection_expiry;} private byte[] protection_expiry = Bry__protection_expiry__infinite;

	public static final    byte[] Bry__protection_expiry__infinite = Bry_.new_a7("infinite");
}
