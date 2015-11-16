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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import gplx.core.brys.args.*; 	import gplx.core.brys.fmtrs.*;
public class Bfr_arg_ {
	public static Bfr_arg__int			New_int(int v)			{return new Bfr_arg__int(v);}
	public static Bfr_arg__byte			New_byte(byte v)		{return new Bfr_arg__byte(v);}
	public static Bfr_arg__bry			New_bry(String v)		{return new Bfr_arg__bry(Bry_.new_u8(v));}
	public static Bfr_arg__bry			New_bry(byte[] v)		{return new Bfr_arg__bry(v);}
	public static Bfr_arg__bry_fmtr		New_bry_fmtr__null()	{return new Bfr_arg__bry_fmtr(null, null);}
	public static Bfr_arg__bry_fmtr		New_bry_fmtr(Bry_fmtr v, Bfr_arg... arg_ary) {return new Bfr_arg__bry_fmtr(v, arg_ary);}
	public static final Bfr_arg Noop = new Bfr_arg___noop();
	public static void Clear(Bfr_arg... ary) {
		for (Bfr_arg arg : ary)
			arg.Bfr_arg__clear();
	}
}
class Bfr_arg___noop implements gplx.core.brys.Bfr_arg {
	public void Bfr_arg__clear() {}
	public boolean Bfr_arg__exists() {return false;}
	public void Bfr_arg__add(Bry_bfr bfr) {}
}
