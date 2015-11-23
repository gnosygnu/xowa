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
public class ObjAry {
	public Object[] Ary() {return ary;} Object[] ary;
	public Object Get(int i) {return ary[i];}
	public Object Get0() {return ary[0];}
	public Object Get1() {return ary[1];}
        public static ObjAry pair_(Object val0, Object val1) {
		ObjAry rv = new ObjAry();
		rv.ary = new Object[2];
		rv.ary[0] = val0;
		rv.ary[1] = val1;
		return rv;
	}	ObjAry() {}
        public static ObjAry many_(Object... ary) {
		ObjAry rv = new ObjAry();
		rv.ary = ary;
		return rv;
	}
}
