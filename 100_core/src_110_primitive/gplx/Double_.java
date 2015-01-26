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
public class Double_ {
	public static final Class<?> Cls_ref_type = Double.class; 
	public static final String Cls_val_name = "double";
	public static final double Inf_pos = Double.POSITIVE_INFINITY;;
	public static final double NaN = Double.NaN;;					
	public static final byte[] NaN_bry = Bry_.new_ascii_("NaN");
	public static boolean IsNaN(double v) {return Double.isNaN(v);}	
	public static int Compare(double lhs, double rhs) {
		if		(lhs == rhs) 	return CompareAble_.Same;
		else if (lhs < rhs)		return CompareAble_.Less;
		else 					return CompareAble_.More;
	}
	public static double coerce_(Object v) {
		try {String s = String_.as_(v); return s == null ? Double_.cast_(v) : Double_.parse_(s);}
		catch (Exception e) {throw Err_.cast_(e, double.class, v);}
	}
	public static String Xto_str(double v) {
				int v_int = (int)v;
		return v - v_int == 0 ? Int_.Xto_str(v_int) : Double.toString(v);
			}
	public static String Xto_str_loose(double v) {
		int v_as_int = (int)v;			
		return v == v_as_int
			? Int_.Xto_str(v_as_int)		// convert to int, and call print String to eliminate any trailing decimal places
			: Float_.Xto_str((float)v);	// calling ((float)v).toString is better at removing trailing 0s than String.format("%g", v). note that .net .toString() handles it better; EX:2449.600000000000d; DATE:2014-07-29
	}
	public static double cast_(Object o) {try {return (Double)o;} catch(Exception e) {throw Err_.type_mismatch_exc_(e, double.class, o);}}
	public static double parse_(String raw) {try {return Double.parseDouble(raw);} catch(Exception e) {throw Err_.parse_type_exc_(e, double.class, raw);}}
	public static double parseOr_(String raw, double v) {try {return Double.parseDouble(raw);} catch(Exception e) {Err_.Noop(e); return v;}}
	public static final double MinValue = Double.MIN_VALUE; 
}
