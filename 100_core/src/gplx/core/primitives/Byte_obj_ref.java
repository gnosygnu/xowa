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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Byte_obj_ref {
	public byte Val() {return val;} private byte val;
	public Byte_obj_ref Val_(byte v) {val = v; return this;}
	@Override public int hashCode() {return val;}
	@Override public boolean equals(Object obj) {return obj == null ? false : val == ((Byte_obj_ref)obj).Val();}
	@Override public String toString() {return Int_.To_str(val);}
        public static Byte_obj_ref zero_() {return new_(Byte_.Zero);}
        public static Byte_obj_ref new_(byte val) {
		Byte_obj_ref rv = new Byte_obj_ref();
		rv.val = val;
		return rv;
	}	private Byte_obj_ref() {}
}
