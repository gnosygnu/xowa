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
public class Php_tkn_comment extends Php_tkn_base {
	public Php_tkn_comment(int src_bgn, int src_end, byte comment_tid) {this.Src_rng_(src_bgn, src_end); this.comment_tid = comment_tid;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_comment;}
	public byte Comment_tid() {return comment_tid;} private byte comment_tid;

	public void To_bfr(Bry_bfr bfr, byte[] src, boolean trim) {
		int bgn = this.Src_bgn();
		int end = this.Src_end();
		switch (comment_tid) {
			case Tid_mult:	// EX: /* comment */
				bgn += 2;
				end -= 2;
				break;
			case Tid_slash:	// EX: // comment\n
				bgn += 2;
				end -= 1;
				break;
			case Tid_hash:	// EX: # comment\n
				bgn += 1;
				end -= 1;
				break;
		}
		if (trim) {
			bgn = Bry_find_.Find_fwd_while_not_ws(src, bgn, end);
			end = Bry_find_.Find_bwd__skip_ws(src, end, bgn);
		}
		bfr.Add_mid(src, bgn, end);
	}
	public static final byte Tid_null = 0, Tid_mult = 1, Tid_slash = 2, Tid_hash = 3;
}
