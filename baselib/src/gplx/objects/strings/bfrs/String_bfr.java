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
package gplx.objects.strings.bfrs; import gplx.*; import gplx.objects.*; import gplx.objects.strings.*;
import java.lang.*;
import gplx.objects.primitives.*;
import gplx.objects.errs.*;
import gplx.objects.strings.unicodes.*;
public class String_bfr {
	private java.lang.StringBuilder sb = new java.lang.StringBuilder();
	public boolean Has_none() {return this.Len() == 0;}
	public boolean Has_some() {return this.Len() > 0;}
	public String_bfr Add_fmt(String format, Object... args) {Add(String_.Format(format, args)); return this;}
	public String_bfr Add_char_pipe()	{return Add_char(Char_code_.Pipe);}
	public String_bfr Add_char_nl()     {return Add_char(Char_code_.New_line);}
	public String_bfr Add_char_space()	{return Add_char(Char_code_.Space);}
	public String_bfr Add_char_colon()	{return Add_char(Char_code_.Colon);}
	public String_bfr Add_char_repeat(char c, int repeat) {
		this.Ensure_capacity(this.Len() + repeat);
		for (int i = 0; i < repeat; i++)
			Add_char(c);
		return this;
	}
	public String_bfr Add_char_by_code(int code) {
		if (code >= Ustring_.Surrogate_cp_bgn && code <= Ustring_.Surrogate_cp_end) {
			sb.append((char)((code - 0x10000) / 0x400 + 0xD800));
			sb.append((char)((code - 0x10000) % 0x400 + 0xDC00));
		}
		else {
			sb.append((char)code);
		}
		return this;
	}
	public String_bfr Add_int_pad_bgn(char pad_char, int str_len, int val) {
		int digit_len   = Int_.Count_digits(val);
		int pad_len     = str_len - digit_len;
		if (pad_len > 0)	// note that this skips pad_len == 0, as well as guarding against negative pad_len; EX: pad(" ", 3, 1234) -> "1234"
			Add_char_repeat(pad_char, pad_len);
		Add_int(val);
		return this;
	}
	public String_bfr Add_bool(boolean val) {
		this.Add(val ? Bool_.True_str : Bool_.False_str);
		return this;
	}
	public String_bfr Add_bool_as_yn(boolean val) {
		this.Add(val ? "y" : "n");
		return this;
	}
	public String_bfr Clear() {Del(0, this.Len()); return this;}
	public String To_str_and_clear() {
		String rv = To_str();
		Clear();
		return rv;
	}
	@Override public String toString() {return To_str();}
	public String To_str() {return sb.toString();}
	public int Len() {return sb.length();}                                          
	public String_bfr Add_at(int idx, String s) {sb.insert(idx, s); return this;} 
	public String_bfr Add(String s) {sb.append(s); return this;}                  
	public String_bfr Add_char(char c) {sb.append(c); return this;}               
	public String_bfr Add_byte(byte i) {sb.append(i); return this;}               
	public String_bfr Add_int(int i) {sb.append(i); return this;}                 
	public String_bfr Add_long(long i) {sb.append(i); return this;}               
	public String_bfr Add_double(double i) {sb.append(i); return this;}           
	public String_bfr Add_mid(char[] ary, int bgn, int count) {sb.append(ary, bgn, count); return this;}
	public String_bfr Add_obj(Object o) {sb.append(o); return this;}              
	public String_bfr Add_bry(byte[] v) {
		if (v != null)
			sb.append(String_.New_bry_utf8(v)); 
		return this;
	}
	private void Ensure_capacity(int capacity) {sb.ensureCapacity(capacity);} 
	public String_bfr Del(int bgn, int len) {sb.delete(bgn, len); return this;}   
}
