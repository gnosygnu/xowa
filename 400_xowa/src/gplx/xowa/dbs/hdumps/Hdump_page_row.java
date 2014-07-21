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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.ios.*;
public class Hdump_page_row {
	public int Id() {return id;} private int id;
	public byte[] Html() {return html;} private byte[] html;
	public int Frags_len() {return frags_len;} private int frags_len;
	public int Make_id() {return make_id;} private int make_id;
	public Hdump_page_row Ctor(int id, byte[] html, int frags_len, int make_id) {
		this.id				= id;
		this.html			= html;
		this.frags_len		= frags_len;
		this.make_id		= make_id;
		return this;
	}
}
