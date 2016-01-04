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
package gplx.core.brys.args; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bfr_arg__decimal_int implements Bfr_arg {
	public int Val() {return val;} public Bfr_arg__decimal_int Val_(int v) {val = v; return this;} int val;
	public Bfr_arg__decimal_int Places_(int v) {places = v; multiple = (int)Math_.Pow(10, v); return this;} int multiple = 1000, places = 3;
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_int_variable(val / multiple).Add_byte(Byte_ascii.Dot).Add_int_fixed(val % multiple, places);
	}
}
