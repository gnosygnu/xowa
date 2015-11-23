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
	private byte[] val_as_bry; private int val_as_bry_bgn, val_as_bry_end;
	private byte[][] val_as_ary;
	private Bfr_arg val_as_arg;
	public Bfr_arg__html_atr(byte[] key) {this.atr_bgn = Bld_atr_bgn(key);}
	public void Clear() {
		val_type = Tid__null;
		val_as_arg = null;
		val_as_int = Int_.Min_value;
		val_as_bry = null;
		val_as_ary = null;
	}
	public byte[] Val_as_bry() {return val_as_bry;}
	public int Val_as_bry_bgn() {return val_as_bry_bgn;}
	public int Val_as_bry_end() {return val_as_bry_end;}
	public Bfr_arg__html_atr Set_by_arg(Bfr_arg v)						{val_type = Tid__arg;		val_as_arg = v; return this;}
	public Bfr_arg__html_atr Set_by_int(int v)							{val_type = Tid__int;		val_as_int = v; return this;}
	public Bfr_arg__html_atr Set_by_ary(byte[]... ary)			{val_type = Tid__bry__ary;	val_as_ary = ary; return this;}
	public Bfr_arg__html_atr Set_by_bry(byte[] bry)						{val_type = Tid__bry__val;	val_as_bry = bry; return this;}
	public Bfr_arg__html_atr Set_by_mid(byte[] bry, int bgn, int end)	{val_type = Tid__bry__mid;	val_as_bry = bry; val_as_bry_bgn = bgn; val_as_bry_end = end; return this;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__exists() {
		switch (val_type) {
			case Tid__null:			return false;
			case Tid__arg:			return val_as_arg.Bfr_arg__exists();
			case Tid__int:			return val_as_int != Int_.Min_value;
			case Tid__bry__ary:		return val_as_ary != null;
			case Tid__bry__val:		return val_as_bry != null;
			case Tid__bry__mid:		return val_as_bry != null;
			default:				throw Err_.new_unhandled(val_type);
		}
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (!Bfr_arg__exists()) return;
		bfr.Add(atr_bgn);
		switch (val_type) {
			case Tid__arg:			val_as_arg.Bfr_arg__add(bfr); break;
			case Tid__int:			bfr.Add_int_variable(val_as_int); break;
			case Tid__bry__val:		bfr.Add(val_as_bry); break;
			case Tid__bry__mid:		bfr.Add_mid(val_as_bry, val_as_bry_bgn, val_as_bry_end); break;
			case Tid__bry__ary:
				int len = val_as_ary.length;
				for (int i = 0; i < len; ++i) {
					if (i != 0) bfr.Add_byte_space();
					byte[] v = val_as_ary[i];
					bfr.Add(v);
				}
				break;
		}
		bfr.Add_byte_quote();
	}
	public static byte[] Bld_atr_bgn(byte[] key) {return Bry_.Add(Byte_ascii.Space_bry, key, Byte_ascii.Eq_bry, Byte_ascii.Quote_bry);}	// ' key="'
	private static final int Tid__null = 0, Tid__arg = 1, Tid__int = 2, Tid__bry__mid = 3, Tid__bry__val = 4, Tid__bry__ary = 5;
}
