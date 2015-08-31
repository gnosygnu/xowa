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
package gplx.brys; import gplx.*;
public class Bry_fmtr_arg_bry implements Bry_fmtr_arg {
	public Bry_fmtr_arg_bry Data_(byte[] v) {ary = v; return this;}
	public void XferAry(Bry_bfr trg, int idx) {trg.Add(ary);}
	public Bry_fmtr_arg_bry(byte[] v) {this.ary = v;}	byte[] ary;
}
