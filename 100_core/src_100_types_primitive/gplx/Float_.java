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
public class Float_ {
	public static final String Cls_val_name = "float";
	public static final Class<?> Cls_ref_type = Float.class;
	public static final float NaN = Float.NaN;;					
	public static boolean IsNaN(float v) {return Float.isNaN(v);}		
	public static float cast(Object obj)	{try {return (Float)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, float.class, obj);}}
	public static float parse(String raw)	{try {return Float.parseFloat(raw);} catch(Exception exc) {throw Err_.new_parse_exc(exc, float.class, raw);}} 
	public static String Xto_str(float v) {
				int v_int = (int)v;
		return v - v_int == 0 ? Int_.Xto_str(v_int) : Float.toString(v);
			}
	public static float Div(int val, int divisor) {return (float)val / (float)divisor;}
	public static float Div(long val, long divisor) {return (float)val / (float)divisor;}
	public static int RoundUp(float val) {
		int rv = (int)val;
		return (rv == val) ? rv : rv + 1;
	}
}
