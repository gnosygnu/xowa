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
package gplx.xowa.xtns.scores; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Score_xnde_tst {
	@Test  public void Version() {
		Tfds.Eq_bry(Bry_.new_ascii_("2.16.2"), Score_xnde.Get_lilypond_version("GNU LilyPond 2.16.2\nline1\nline2"));
		Tfds.Eq_bry(Bry_.new_ascii_("2.16.2"), Score_xnde.Get_lilypond_version("GNU LilyPond 2.16.2\r\nline1\r\nline2"));
	}
}
