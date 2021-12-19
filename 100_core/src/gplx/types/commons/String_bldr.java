/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import gplx.core.envs.*;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
public interface String_bldr {
	boolean HasSome();
	String_bldr AddMany(String... array);
	String_bldr AddFmt(String format, Object... args);
	String_bldr AddFmtLine(String format, Object... args);
	String_bldr AddKv(String hdr, String val);
	String_bldr AddKvObj(String k, Object v);
	String_bldr AddCharPipe();
	String_bldr AddCharNl();
	String_bldr AddCharCrlf();
	String_bldr AddStrWithCrlf(String v);
	String_bldr AddSprUnlessFirst(String s, String spr, int i);
	String_bldr Clear();
	String ToStrAndClear();
	String ToStr();
	int Len();
	String_bldr Add(byte[] v);
	String_bldr Add(String s);
	String_bldr Add(char c);
	String_bldr Add(int i);
	String_bldr AddObj(Object o);
	String_bldr AddMid(String str, int bgn, int end);
	String_bldr AddMidLen(char[] ary, int bgn, int count);
	String_bldr AddMidLen(String str, int bgn, int count);
	String_bldr AddAt(int idx, String s);
	String_bldr Del(int bgn, int len);
}
abstract class String_bldr_base implements String_bldr {
	public boolean HasSome() {return this.Len() > 0;}
	public String_bldr AddMany(String... array) {for (String s : array) Add(s); return this;}
	public String_bldr AddFmt(String format, Object... args) {Add(StringUtl.Format(format, args)); return this;}
	public String_bldr AddFmtLine(String format, Object... args) {AddStrWithCrlf(StringUtl.Format(format, args)); return this;}
	public String_bldr AddKvObj(String k, Object v) {
		if (this.Len() != 0) this.Add(" ");
		this.AddFmt("{0}={1}", k, ObjectUtl.ToStrOrNullMark(v));
		return this;
	}
	public String_bldr AddCharPipe()    {return Add("|");}
	public String_bldr AddCharNl()    {Add(Op_sys.Lnx.Nl_str()); return this;}
	public String_bldr AddCharCrlf()    {Add(Op_sys.Wnt.Nl_str()); return this;}
	public String_bldr AddStrWithCrlf(String line) {Add(line); Add(StringUtl.CrLf); return this;}
	public String_bldr AddSprUnlessFirst(String s, String spr, int i) {
		if (i != 0) Add(spr);
		Add(s);
		return this;
	}
	public String_bldr AddKv(String hdr, String val) {
		if (StringUtl.IsNullOrEmpty(val)) return this;
		if (this.Len() != 0) this.Add(' ');
		this.Add(hdr);
		this.Add(val);
		return this;
	}
	public String_bldr Clear() {Del(0, Len()); return this;}
	public String ToStrAndClear() {
		String rv = ToStr();
		Clear();
		return rv;
	}
	@Override public String toString() {return ToStr();}
	public abstract String ToStr();
	public abstract int Len();
	public abstract String_bldr AddAt(int idx, String s);
	public abstract String_bldr Add(byte[] v);
	public abstract String_bldr Add(String s);
	public abstract String_bldr Add(char c);
	public abstract String_bldr Add(int i);
	public abstract String_bldr AddMid(String str, int bgn, int end);
	public abstract String_bldr AddMidLen(char[] ary, int bgn, int count);
	public abstract String_bldr AddMidLen(String str, int bgn, int count);
	public abstract String_bldr AddObj(Object o);
	public abstract String_bldr Del(int bgn, int len);
}
class String_bldr_thread_single extends String_bldr_base {
	private java.lang.StringBuilder sb = new java.lang.StringBuilder();
	@Override public String ToStr() {return sb.toString();}
	@Override public int Len() {return sb.length();}
	@Override public String_bldr AddAt(int idx, String s) {sb.insert(idx, s); return this;}
	@Override public String_bldr Add(byte[] v) {sb.append(StringUtl.NewU8(v)); return this;}
	@Override public String_bldr Add(String s) {sb.append(s); return this;}                        
	@Override public String_bldr Add(char c) {sb.append(c); return this;}                        
	@Override public String_bldr Add(int i) {sb.append(i); return this;}                            
	@Override public String_bldr AddMid(String str, int bgn, int end) {sb.append(str, bgn, end); return this;}
	@Override public String_bldr AddMidLen(char[] ary, int bgn, int count) {sb.append(ary, bgn, count); return this;}
	@Override public String_bldr AddMidLen(String str, int bgn, int count) {sb.append(str, bgn, count); return this;}
	@Override public String_bldr AddObj(Object o) {sb.append(o); return this;}
	@Override public String_bldr Del(int bgn, int len) {sb.delete(bgn, len); return this;}        
}
class String_bldr_thread_multiple extends String_bldr_base {
	private java.lang.StringBuffer sb = new java.lang.StringBuffer();
	@Override public String ToStr() {return sb.toString();}
	@Override public int Len() {return sb.length();}
	@Override public String_bldr AddAt(int idx, String s) {sb.insert(idx, s); return this;}
	@Override public String_bldr Add(byte[] v) {sb.append(StringUtl.NewU8(v)); return this;}
	@Override public String_bldr Add(String s) {sb.append(s); return this;}                        
	@Override public String_bldr Add(char c) {sb.append(c); return this;}                        
	@Override public String_bldr Add(int i) {sb.append(i); return this;}                            
	@Override public String_bldr AddMid(String str, int bgn, int end) {sb.append(str, bgn, end); return this;}
	@Override public String_bldr AddMidLen(char[] ary, int bgn, int count) {sb.append(ary, bgn, count); return this;}
	@Override public String_bldr AddMidLen(String str, int bgn, int count) {sb.append(str, bgn, count); return this;}
	@Override public String_bldr AddObj(Object o) {sb.append(o); return this;}
	@Override public String_bldr Del(int bgn, int len) {sb.delete(bgn, len); return this;}        
}
