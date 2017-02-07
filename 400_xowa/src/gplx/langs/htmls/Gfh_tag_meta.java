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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Gfh_tag_meta {
	public Gfh_tag_meta(int id, String key_str) {
		this.id = id;
		this.key_str = key_str;
		this.key_bry = Bry_.new_u8(key_str);
	}
	public int Id() {return id;} private final    int id;
	public String Key_str() {return key_str;} private final    String key_str;
	public byte[] Key_bry() {return key_bry;} private final    byte[] key_bry;
}