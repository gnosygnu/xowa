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
package gplx.gfml; import gplx.*;
public class GfmlObjList extends List_adp_base {
	@gplx.New public GfmlObj Get_at(int idx) {return (GfmlObj)Get_at_base(idx);}
	public void Add(GfmlObj tkn) {Add_base(tkn);}
	public void Add_at(GfmlObj tkn, int idx) {super.AddAt_base(idx, tkn);}
	public void Del(GfmlObj tkn) {Del_base(tkn);}
	public static GfmlObjList new_() {return new GfmlObjList();} GfmlObjList() {}
}
