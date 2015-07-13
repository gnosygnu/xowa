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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_num_grp {
	public Xol_num_grp(byte[] dlm, int digits, boolean repeat) {this.dlm = dlm; this.digits = digits; this.repeat = repeat;}
	public byte[] Dlm() {return dlm;} private byte[] dlm;
	public int Digits() {return digits;} private int digits;
	public boolean Repeat() {return repeat;} private boolean repeat;
	public static final Xol_num_grp[] Ary_empty = new Xol_num_grp[0];
	public static final Xol_num_grp Default = new Xol_num_grp(new byte[] {Byte_ascii.Comma}, 3, true);
}
