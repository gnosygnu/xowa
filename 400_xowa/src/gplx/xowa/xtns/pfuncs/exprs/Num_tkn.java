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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*;
class Num_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_number;}		
	public byte[] Val_ary()	{return val_ary;} private byte[] val_ary;
	public String Val_str()	{return String_.new_u8(val_ary);}
	public Num_tkn(int val_int) {
		this.val_int = val_int;
		this.val_ary = new byte[] {Byte_.By_int(val_int + Byte_ascii.Num_0)};
	}	int val_int;
}
