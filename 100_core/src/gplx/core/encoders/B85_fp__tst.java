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
package gplx.core.encoders; import gplx.*; import gplx.core.*;
import org.junit.*;
public class B85_fp__tst {
	private final B85_fp__fxt fxt = new B85_fp__fxt();
	@Test  public void Double_to_str() {
		fxt.Test__to_str(.1d, "/\"");
	}
}
class B85_fp__fxt {
	public void Test__to_str(double val, String expd) {
		byte[] actl = B85_fp_.To_bry(val);
		Tfds.Eq_str(expd, String_.new_a7(actl));
	}
}
