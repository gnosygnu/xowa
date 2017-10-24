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
public interface Php_tkn {
	byte Tkn_tid();
	int Src_bgn();
	int Src_end();
}
class Php_tkn_ {
	public static final byte Tid_txt = 1, Tid_declaration = 2, Tid_ws = 3, Tid_comment = 4, Tid_var = 5, Tid_eq = 6, Tid_eq_kv = 7, Tid_semic = 8, Tid_comma = 9, Tid_paren_bgn = 10, Tid_paren_end = 11, Tid_null = 12, Tid_false = 13, Tid_true = 14, Tid_ary = 15, Tid_num = 16, Tid_quote = 17, Tid_brack_bgn = 18, Tid_brack_end = 19;
	public static String Xto_str(byte tid) {return Byte_.To_str(tid);}
}
abstract class Php_tkn_base implements Php_tkn {
	public abstract byte Tkn_tid();
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} public void Src_end_(int v) {this.src_end = v;} private int src_end;
	public void Src_rng_(int src_bgn, int src_end) {this.src_bgn = src_bgn; this.src_end = src_end;}
}
class Php_tkn_generic extends Php_tkn_base {
	public Php_tkn_generic(int src_bgn, int src_end, byte tid) {this.Src_rng_(src_bgn, src_end); this.tid = tid;}
	@Override public byte Tkn_tid() {return tid;} private byte tid;
}
class Php_tkn_txt extends Php_tkn_base {
	public Php_tkn_txt(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_txt;}
}
class Php_tkn_ws extends Php_tkn_base {
	public Php_tkn_ws(int src_bgn, int src_end, byte ws_tid) {this.Src_rng_(src_bgn, src_end); this.ws_tid = ws_tid;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_ws;}
	public byte Ws_tid() {return ws_tid;} private byte ws_tid;
	public static final byte Tid_space = 0, Tid_nl = 1, Tid_tab = 2, Tid_cr = 3;
}
class Php_tkn_comment extends Php_tkn_base {
	public Php_tkn_comment(int src_bgn, int src_end, byte comment_tid) {this.Src_rng_(src_bgn, src_end); this.comment_tid = comment_tid;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_comment;}
	public byte Comment_tid() {return comment_tid;} private byte comment_tid;
	public static final byte Tid_null = 0, Tid_mult = 1, Tid_slash = 2, Tid_hash = 3;
}
class Php_tkn_var extends Php_tkn_base {
	public Php_tkn_var(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_var;}
	public byte[] Var_name(byte[] src) {return Bry_.Mid(src, this.Src_bgn() + 1, this.Src_end());}	// NOTE: assume vars are of form $abc; +1 to skip first $
}
class Php_tkn_num extends Php_tkn_base {
	public Php_tkn_num(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_num;}
	public int Num_val_int(byte[] src) {return Bry_.To_int_or(src, this.Src_bgn(), this.Src_end(), Int_.Min_value);}
}
class Php_tkn_quote extends Php_tkn_base {
	public Php_tkn_quote(int src_bgn, int src_end, byte quote_tid) {this.Src_rng_(src_bgn, src_end); this.quote_tid = quote_tid;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_quote;}
	public byte Quote_tid() {return quote_tid;} private byte quote_tid;
	public byte[] Quote_text(byte[] src) {return Bry_.Mid(src, this.Src_bgn() + 1, this.Src_end() - 1);}	// NOTE: assume quote are of form 'abc'; +1, -1 to skip flanking chars
	public static final byte Tid_null = 0, Tid_mult = 1, Tid_slash = 2, Tid_hash = 3;
}
class Php_tkn_declaration extends Php_tkn_base {
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_declaration;}
	public static final Php_tkn_declaration Instance = new Php_tkn_declaration();	
}
