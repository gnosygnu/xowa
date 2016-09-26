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
package gplx.xowa.apps.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoa_url_encoder__tst {
	private final    Xoa_url_encoder__fxt fxt = new Xoa_url_encoder__fxt();
	@Test  public void Syms__diff() 	{fxt.Test__encode(" &'=+", "_%26%27%3D%2B");}
	@Test  public void Syms__same() 	{fxt.Test__encode("!\"#$%()*,-./:;<>?@[\\]^_`{|}~", "!\"#$%()*,-./:;<>?@[\\]^_`{|}~");}
	@Test  public void Mixed() 			{fxt.Test__encode("a &'=+b", "a_%26%27%3D%2Bb");}	// PURPOSE: make sure dirty flag is set
}
class Xoa_url_encoder__fxt {
	private final    Xoa_url_encoder encoder = new Xoa_url_encoder();
	public void Test__encode(String raw, String expd) {
		Gftest.Eq__bry(Bry_.new_u8(expd), encoder.Encode(Bry_.new_u8(raw)));
	}
}
