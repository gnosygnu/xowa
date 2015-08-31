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
public class Bool_obj_ref {
	public boolean Val() {return val;} private boolean val;
	public boolean Val_y() {return val;}
	public boolean Val_n() {return !val;}
	public String Val_as_str_yn() {return Yn.Xto_str(val);}
	public Bool_obj_ref Val_y_() {val = true; return this;}
	public Bool_obj_ref Val_n_() {val = false; return this;}
	public Bool_obj_ref Val_(boolean v) {val = v; return this;}
	public Bool_obj_ref Val_toggle_() {val = !val; return this;}
	@Override public String toString() {return Bool_.To_str_lower(val);}
        public static Bool_obj_ref n_() {return new_(false);}
        public static Bool_obj_ref y_() {return new_(true);}
        public static Bool_obj_ref new_(boolean val) {
		Bool_obj_ref rv = new Bool_obj_ref();
		rv.val = val;
		return rv;
	}	Bool_obj_ref() {}
}
