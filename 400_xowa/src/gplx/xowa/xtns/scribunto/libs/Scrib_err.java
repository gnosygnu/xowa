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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
class Scrib_err {
	public static Err Make__err__arg(String proc, int arg_idx, String actl, String expd) {
		String rv = String_.Format("bad argument #{0} to {1} ({2} expected, got {3})", arg_idx, proc, expd, actl);	// "bad argument #$argIdx to '$name' ($expectType expected, got $type)"
		return Err_.new_("scribunto", rv);
	}
}
