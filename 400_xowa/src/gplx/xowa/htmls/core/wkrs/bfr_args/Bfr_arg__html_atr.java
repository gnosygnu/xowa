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
package gplx.xowa.htmls.core.wkrs.bfr_args; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
public class Bfr_arg__html_atr implements Bfr_arg, Clear_able {
	private final byte[] atr_bgn;
	private int val_type;
	private int val_as_int;
	private Bfr_arg val_as_arg;
	public Bfr_arg__html_atr(byte[] key) {this.atr_bgn = Bld_atr_bgn(key);}
	public void Clear() {
		val_type = Type__null;
		val_as_arg = null;
		val_as_int = Int_.Min_value;
	}
	public Bfr_arg__html_atr Set_by_arg(Bfr_arg v)	{val_type = Type__arg;		val_as_arg = v; return this;}
	public Bfr_arg__html_atr Set_by_int(int v)		{val_type = Type__int;		val_as_int = v; return this;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__exists() {
		switch (val_type) {
			case Type__null:	return false;
			case Type__arg:		return val_as_arg.Bfr_arg__exists();
			case Type__int:		return val_as_int != Int_.Min_value;
			default:			throw Err_.new_unhandled(val_type);
		}
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (!Bfr_arg__exists()) return;
		bfr.Add(atr_bgn);
		switch (val_type) {
			case Type__arg:		val_as_arg.Bfr_arg__add(bfr); break;
			case Type__int:		bfr.Add_int_variable(val_as_int); break;
		}
		bfr.Add_byte_quote();
	}
	public static byte[] Bld_atr_bgn(byte[] key) {return Bry_.Add(Byte_ascii.Space_bry, key, Byte_ascii.Eq_bry, Byte_ascii.Quote_bry);}	// ' key="'
	private static final int Type__null = 0, Type__arg = 1, Type__int = 2;
}
