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
public class Hdump_skin_row {
	public int Skin_id() {return skin_id;} private int skin_id;
	public byte[] Skin_html() {return skin_html;} private byte[] skin_html;
	public Hdump_skin_row Ctor(int skin_id, byte[] skin_html) {
		this.skin_id		= skin_id;
		this.skin_html		= skin_html;
		return this;
	}
}
