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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_url_obj_ref {
	public Io_url Val() {return val;} public Io_url_obj_ref Val_(Io_url v) {val = v; return this;} private Io_url val;
	public String Val_as_str() {return val.Raw();}
	@Override public String toString() {return val.Raw();}
	@Override public int hashCode() {return val.hashCode();}
	@Override public boolean equals(Object obj) {return String_.Eq(val.Raw(), ((Io_url_obj_ref)obj).val.Raw());}
        public static Io_url_obj_ref new_(Io_url val) {
		Io_url_obj_ref rv = new Io_url_obj_ref();
		rv.val = val;
		return rv;
	}	Io_url_obj_ref() {}
}
