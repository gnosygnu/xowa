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
package gplx;
import gplx.brys.*;
public class Bry_fmtr_arg_ {
	public static Bry_fmtr_arg_bry bry_(String v)				{return new Bry_fmtr_arg_bry(Bry_.new_u8(v));}
	public static Bry_fmtr_arg_bry bry_(byte[] v)				{return new Bry_fmtr_arg_bry(v);}
	public static Bry_fmtr_arg_byt byt_(byte v)				{return new Bry_fmtr_arg_byt(v);}
	public static Bry_fmtr_arg_int int_(int v)				{return new Bry_fmtr_arg_int(v);}
	public static Bry_fmtr_arg_bfr bfr_(Bry_bfr v)			{return new Bry_fmtr_arg_bfr(v);}
	public static Bry_fmtr_arg_bfr_preserve bfr_retain_(Bry_bfr v)		{return new Bry_fmtr_arg_bfr_preserve(v);}
	public static Bry_fmtr_arg fmtr_(Bry_fmtr v, Bry_fmtr_arg... arg_ary) {return new Bry_fmtr_arg_fmtr(v, arg_ary);}
	public static Bry_fmtr_arg_fmtr_objs fmtr_null_() {return new Bry_fmtr_arg_fmtr_objs(null, null);}
	public static final Bry_fmtr_arg Noop = new Bry_fmtr_arg__noop();
}
class Bry_fmtr_arg__noop implements Bry_fmtr_arg {
	public void XferAry(Bry_bfr trg, int idx) {}
}
