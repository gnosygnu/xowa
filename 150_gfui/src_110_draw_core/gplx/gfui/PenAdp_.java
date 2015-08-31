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
public class PenAdp_ {
	public static PenAdp as_(Object obj) {return obj instanceof PenAdp ? (PenAdp)obj : null;}
	public static PenAdp cast(Object obj) {try {return (PenAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, PenAdp.class, obj);}}
	public static PenAdp black_() {return new_(ColorAdp_.Black, 1);}
	public static PenAdp new_(ColorAdp color) {return new_(color, 1);}
	public static PenAdp new_(ColorAdp color, float width) {return new PenAdp(color, width);}
}
