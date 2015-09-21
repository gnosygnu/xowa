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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public interface Json_grp extends Json_itm {
	void Src_end_(int v);
	int Len();
	Json_itm Get_at(int i);
	void Add(Json_itm itm);
}
class Json_grp_ {
	public static final Json_grp[] Ary_empty = new Json_grp[0];  
	public static void Print_nl(Bry_bfr bfr) {								// \n\n can be caused by nested groups (EX: "[[]]"); only print 1
		if (bfr.Bfr()[bfr.Len() - 1] != Byte_ascii.Nl)
			bfr.Add_byte_nl();
	}
	public static void Print_indent(Bry_bfr bfr, int depth) {
		if (depth > 0) bfr.Add_byte_repeat(Byte_ascii.Space, depth * 2);	// indent
	}
}
