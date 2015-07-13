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
public class Double__tst {
	private Double__fxt fxt = new Double__fxt();
	@Test  public void Xto_str_loose() {			
		fxt.Test_Xto_str_loose(2449.6000000d		, "2449.6");
		fxt.Test_Xto_str_loose(623.700d				, "623.7");
	}
}
class Double__fxt {
	public void Test_Xto_str_loose(double v, String expd) {Tfds.Eq(expd, Double_.Xto_str_loose(v));}
}
