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
package gplx.xowa.hdumps.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
public class Xopg_hdump_data {
	public ListAdp Imgs() {return imgs;} private final ListAdp imgs = ListAdp_.new_();
	public byte[] Body() {return body;} public void Body_(byte[] v) {body = v;} private byte[] body;
	public void Clear() {
		imgs.Clear();
		body = null;
	}
}
