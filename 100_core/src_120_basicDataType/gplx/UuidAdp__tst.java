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
package gplx;
import org.junit.*;
public class UuidAdp__tst {
	@Test  public void parse_() {
		tst_parse_("467ffb41-cdfe-402f-b22b-be855425784b");
	}
	void tst_parse_(String s) {
		UuidAdp uuid = UuidAdp_.parse_(s);
		Tfds.Eq(uuid.XtoStr(), s);
	}
}
