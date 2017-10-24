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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
interface Php_text_itm {
	byte Tid();
	int Src_bgn();
	int Src_end();
	void Bld(Bry_bfr bfr, byte[] src);
}
class Php_text_itm_ {
	public static final byte Tid_text = 0, Tid_escaped = 1, Tid_arg = 2, Tid_utf16 = 3;
}
class Php_text_itm_text implements Php_text_itm {
	public Php_text_itm_text(int src_bgn, int src_end) {this.src_bgn = src_bgn; this.src_end = src_end;}
	public byte Tid() {return Php_text_itm_.Tid_text;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public void Bld(Bry_bfr bfr, byte[] src) {bfr.Add_mid(src, src_bgn, src_end);}
}
class Php_text_itm_escaped implements Php_text_itm {
	public Php_text_itm_escaped(int src_bgn, int src_end, byte literal) {this.src_bgn = src_bgn; this.src_end = src_end; this.literal = literal;}
	public byte Tid() {return Php_text_itm_.Tid_escaped;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public byte Literal() {return literal;} private byte literal;
	public void Bld(Bry_bfr bfr, byte[] src) {bfr.Add_byte(literal);}
}
class Php_text_itm_utf16 implements Php_text_itm {
	public Php_text_itm_utf16(int src_bgn, int src_end, byte[] literal) {this.src_bgn = src_bgn; this.src_end = src_end; this.literal = literal;}
	public byte Tid() {return Php_text_itm_.Tid_utf16;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public byte[] Literal() {return literal;} private byte[] literal;
	public void Bld(Bry_bfr bfr, byte[] src) {bfr.Add(literal);}
}
class Php_text_itm_arg implements Php_text_itm {
	public Php_text_itm_arg(int src_bgn, int src_end, int idx) {this.src_bgn = src_bgn; this.src_end = src_end; this.idx = idx;}
	public byte Tid() {return Php_text_itm_.Tid_escaped;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Idx() {return idx;} private int idx;
	public void Bld(Bry_bfr bfr, byte[] src) {
		bfr.Add_byte(Byte_ascii.Tilde).Add_byte(Byte_ascii.Curly_bgn)
		.Add_int_variable(idx - List_adp_.Base1) // php is super 1
		.Add_byte(Byte_ascii.Curly_end);
	}
}
