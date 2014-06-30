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
public class String_obj_val implements CompareAble {
	public String Val() {return val;} private String val;
	@Override public String toString() {return val;}
	public int compareTo(Object obj) {
		String_obj_val comp = (String_obj_val)obj;
		return String_.Compare_strict(val, comp.val);
	}	
	public static String_obj_val new_(String val) {
		String_obj_val rv = new String_obj_val();
		rv.val = val;
		return rv;
	}	String_obj_val() {}
}
