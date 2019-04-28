/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.strings; import gplx.*; import gplx.objects.*;
import java.lang.*;
import gplx.objects.errs.*;
import gplx.objects.strings.bfrs.*;
import gplx.objects.arrays.*; import gplx.objects.primitives.*;
public class String_ {
	public static final    Class<?> Cls_ref_type = String.class;
	public static final String Cls_val_name = "str" + "ing";
	public static final int Find_none = -1, Pos_neg1 = -1;
	public static final String Empty = "", Null_mark = "<<NULL>>", Tab = "\t", Lf = "\n", CrLf = "\r\n";

	public static boolean Eq(String lhs, String rhs) {return lhs == null ? rhs == null : lhs.equals(rhs);} 
	public static int Len(String s) {return s.length();} 
	public static char Char_at(String s, int i) {return s.charAt(i);} 

	// use C# flavor ("a {0}") rather than Java format ("a %s"); also: (a) don't fail on format errors; (b) escape brackets by doubling
	private static final char FORMAT_ITM_LHS = '{', FORMAT_ITM_RHS = '}';
	public static String Format(String fmt, Object... args) {
		// method vars
		int args_len = Array_.Len_obj(args); 
		if (args_len == 0) return fmt; // nothing to format
		int fmt_len = Len(fmt); 

		// loop vars
		int pos = 0; String arg_idx_str = ""; boolean inside_brackets = false;
		String_bfr bfr = new String_bfr();
		while (pos < fmt_len) { // loop over every char; NOTE: UT8-SAFE b/c only checking for "{"; "}"
			char c = Char_at(fmt, pos);
			if (inside_brackets) {
				if (c == FORMAT_ITM_LHS) { // first FORMAT_ITM_LHS is fake; add FORMAT_ITM_LHS and whatever is in arg_idx_str
					bfr.Add_char(FORMAT_ITM_LHS).Add(arg_idx_str);
					arg_idx_str = "";
				}
				else if (c == FORMAT_ITM_RHS) { // itm completed
					int args_idx = Int_.Parse_or(arg_idx_str, Int_.Min_value);
					String itm = args_idx != Int_.Min_value && Int_.Between(args_idx, 0, args_len - 1) // check (a) args_idx is num; (b) args_idx is in bounds
						? Object_.To_str_or_null_mark(args[args_idx])    // valid; add itm   
						: FORMAT_ITM_LHS + arg_idx_str + FORMAT_ITM_RHS; // not valid; just add String
					bfr.Add(itm);
					inside_brackets = false;
					arg_idx_str = "";
				}
				else
					arg_idx_str += c;
			}
			else {
				if (c == FORMAT_ITM_LHS || c == FORMAT_ITM_RHS) {
					boolean pos_is_end = pos == fmt_len - 1;
					if (pos_is_end) // last char is "{" or "}" (and not inside_brackets); ignore and just ad
						bfr.Add_char(c);
					else {
						char next = Char_at(fmt, pos + 1);
						if (next == c) {	// "{{" or "}}": escape by doubling
							bfr.Add_char(c);
							pos++;
						}
						else
							inside_brackets = true;
					}
				}
				else
					bfr.Add_char(c);
			}
			pos++;
		}
		if (Len(arg_idx_str) > 0)	// unclosed bracket; add FORMAT_ITM_LHS and whatever is in arg_idx_str; ex: "{0"
			bfr.Add_char(FORMAT_ITM_LHS).Add(arg_idx_str);
		return bfr.To_str();
	}

	public static String New_bry_utf8(byte[] v) {return v == null ? null : New_bry_utf8(v, 0, v.length);}
	public static String New_bry_utf8(byte[] v, int bgn, int end) {
		try {
			return v == null
				? null
				: new String(v, bgn, end - bgn, "UTF-8"); 
		}
		catch (Exception e) {throw Err_.New_fmt(e, "unsupported encoding; bgn={0} end={1}", bgn, end);}
	}
}
