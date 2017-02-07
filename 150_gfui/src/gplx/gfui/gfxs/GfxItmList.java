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
package gplx.gfui.gfxs; import gplx.*; import gplx.gfui.*;
public class GfxItmList extends List_adp_base {
	@gplx.New public GfxItm Get_at(int i) {return (GfxItm)Get_at_base(i);}
	public void Add(GfxItm gfxItm) {Add_base(gfxItm);}
}
class GfxItmListFxt {
	public void tst_SubItm_count(GfxAdpMok gfx, int expd) {Tfds.Eq(expd, gfx.SubItms().Count());}
	public void tst_SubItm(GfxAdpMok gfx, int i, GfxItm expd) {
		GfxItm actl = gfx.SubItms().Get_at(i);
		Tfds.Eq(expd, actl);
	}
	public static GfxItmListFxt new_() {return new GfxItmListFxt();} GfxItmListFxt() {}
}
