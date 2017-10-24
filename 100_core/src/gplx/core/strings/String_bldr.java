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
package gplx.core.strings; import gplx.*; import gplx.core.*;
import gplx.core.envs.*;
public interface String_bldr {
	boolean Has_none();
	boolean Has_some();
	String_bldr Add_many(String... array);
	String_bldr Add_fmt(String format, Object... args);
	String_bldr Add_fmt_line(String format, Object... args);
	String_bldr Add_kv(String hdr, String val);
	String_bldr Add_kv_obj(String k, Object v);
	String_bldr Add_char_pipe();
	String_bldr Add_char_nl();
	String_bldr Add_char_crlf();
	String_bldr Add_str_w_crlf(String v);
	String_bldr Add_spr_unless_first(String s, String spr, int i);
	String_bldr Clear();
	String To_str_and_clear();
	String To_str();
	int Count();
	String_bldr Add(byte[] v);
	String_bldr Add(String s);
	String_bldr Add(char c);
	String_bldr Add(int i);
	String_bldr Add_obj(Object o);
	String_bldr Add_mid(char[] ary, int bgn, int count);
	String_bldr Add_at(int idx, String s);
	String_bldr Del(int bgn, int len);
}
abstract class String_bldr_base implements String_bldr {
	public boolean Has_none() {return this.Count() == 0;}
	public boolean Has_some() {return this.Count() > 0;}
	public String_bldr Add_many(String... array) {for (String s : array) Add(s); return this;}
	public String_bldr Add_fmt(String format, Object... args) {Add(String_.Format(format, args)); return this;}
	public String_bldr Add_fmt_line(String format, Object... args) {Add_str_w_crlf(String_.Format(format, args)); return this;}
	public String_bldr Add_kv_obj(String k, Object v) {
		if (this.Count() != 0) this.Add(" ");
		this.Add_fmt("{0}={1}", k, Object_.Xto_str_strict_or_null_mark(v));
		return this;
	}
	public String_bldr Add_char_pipe()	{return Add("|");}
	public String_bldr Add_char_nl()	{Add(Op_sys.Lnx.Nl_str()); return this;}
	public String_bldr Add_char_crlf()	{Add(Op_sys.Wnt.Nl_str()); return this;}
	public String_bldr Add_str_w_crlf(String line) {Add(line); Add(String_.CrLf); return this;}
	public String_bldr Add_spr_unless_first(String s, String spr, int i) {
		if (i != 0) Add(spr);
		Add(s);
		return this;
	}
	public String_bldr Add_kv(String hdr, String val) {
		if (String_.Len_eq_0(val)) return this;
		if (this.Count() != 0) this.Add(' ');
		this.Add(hdr);
		this.Add(val);
		return this;
	}
	public String_bldr Clear() {Del(0, Count()); return this;}
	public String To_str_and_clear() {
		String rv = To_str();
		Clear();
		return rv;
	}
	@Override public String toString() {return To_str();}
	public abstract String To_str();
	public abstract int Count();
	public abstract String_bldr Add_at(int idx, String s);
	public abstract String_bldr Add(byte[] v);
	public abstract String_bldr Add(String s);
	public abstract String_bldr Add(char c);
	public abstract String_bldr Add(int i);
	public abstract String_bldr Add_mid(char[] ary, int bgn, int count);
	public abstract String_bldr Add_obj(Object o);
	public abstract String_bldr Del(int bgn, int len);
}
class String_bldr_thread_single extends String_bldr_base {
	private java.lang.StringBuilder sb = new java.lang.StringBuilder();
	@Override public String To_str() {return sb.toString();}
	@Override public int Count() {return sb.length();}												
	@Override public String_bldr Add_at(int idx, String s) {sb.insert(idx, s); return this;}		
	@Override public String_bldr Add(byte[] v) {sb.append(String_.new_u8(v)); return this;}	
	@Override public String_bldr Add(String s) {sb.append(s); return this;}						
	@Override public String_bldr Add(char c) {sb.append(c); return this;}						
	@Override public String_bldr Add(int i) {sb.append(i); return this;}							
	@Override public String_bldr Add_mid(char[] ary, int bgn, int count) {sb.append(ary, bgn, count); return this;}
	@Override public String_bldr Add_obj(Object o) {sb.append(o); return this;}					
	@Override public String_bldr Del(int bgn, int len) {sb.delete(bgn, len); return this;}		
}
class String_bldr_thread_multiple extends String_bldr_base {
	private java.lang.StringBuffer sb = new java.lang.StringBuffer();
	@Override public String To_str() {return sb.toString();}
	@Override public int Count() {return sb.length();}												
	@Override public String_bldr Add_at(int idx, String s) {sb.insert(idx, s); return this;}		
	@Override public String_bldr Add(byte[] v) {sb.append(String_.new_u8(v)); return this;}	
	@Override public String_bldr Add(String s) {sb.append(s); return this;}						
	@Override public String_bldr Add(char c) {sb.append(c); return this;}						
	@Override public String_bldr Add(int i) {sb.append(i); return this;}							
	@Override public String_bldr Add_mid(char[] ary, int bgn, int count) {sb.append(ary, bgn, count); return this;}
	@Override public String_bldr Add_obj(Object o) {sb.append(o); return this;}					
	@Override public String_bldr Del(int bgn, int len) {sb.delete(bgn, len); return this;}		
}
