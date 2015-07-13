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
package gplx.xowa.xtns.scribunto.errs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public interface Gfo_val extends CompareAble {
	boolean Eq(Gfo_val rhs);
	boolean Match_1(Gfo_comp_op_1 op, Gfo_val comp);
}
class Gfo_val_mok implements Gfo_val {
	public String s = "";
	public boolean Eq(Gfo_val rhs) {return true;}
	public int compareTo(Object obj) {return 0;}
	public boolean Match_1(Gfo_comp_op_1 op, Gfo_val comp_obj) {
		Gfo_val_mok comp = (Gfo_val_mok)comp_obj;
		return op.Comp_str(s, comp.s);
	}
}
