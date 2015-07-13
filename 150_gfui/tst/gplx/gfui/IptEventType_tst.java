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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class IptEventType_tst {
	@Test  public void Has() {
		tst_Has(IptEventType_.KeyDown, IptEventType_.KeyDown, true);
		tst_Has(IptEventType_.KeyUp, IptEventType_.KeyDown, false);
		tst_Has(IptEventType_.None, IptEventType_.KeyDown, false);
		tst_Has(IptEventType_.KeyDown, IptEventType_.None, false);
		tst_Has(IptEventType_.KeyDown.Add(IptEventType_.KeyUp), IptEventType_.KeyDown, true);
		tst_Has(IptEventType_.MouseDown.Add(IptEventType_.MouseUp), IptEventType_.KeyDown, false);
		tst_Has(IptEventType_.KeyDown.Add(IptEventType_.KeyUp), IptEventType_.None, false);
	}	void tst_Has(IptEventType val, IptEventType find, boolean expd) {Tfds.Eq(expd, IptEventType_.Has(val, find));}
	@Test  public void add_() {
		tst_add(IptEventType_.KeyDown, IptEventType_.KeyDown, IptEventType_.KeyDown.Val());
		tst_add(IptEventType_.KeyDown, IptEventType_.KeyUp, IptEventType_.KeyDown.Val() + IptEventType_.KeyUp.Val());
	}	void tst_add(IptEventType lhs, IptEventType rhs, int expd) {Tfds.Eq(expd, IptEventType_.add_(lhs, rhs).Val());}
}
