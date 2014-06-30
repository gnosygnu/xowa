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
public class GfoRegy_basic_tst {
	@Before public void setup() {
		regy = GfoRegy.new_();
	}	GfoRegy regy;
	@Test  public void RegObjByType() {
		regy.Parsers().Add("Io_url", Io_url_.Parser);
		Io_url expd = Io_url_.new_any_("C:\\fil.txt");
		regy.RegObjByType("test", expd.Xto_api(), "Io_url");
		Io_url actl = (Io_url)regy.FetchValOr("test", Io_url_.Null);
		Tfds.Eq(expd.Xto_api(), actl.Xto_api());
	}
}
