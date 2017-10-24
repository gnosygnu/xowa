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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
import gplx.core.strings.*;
public interface Gfo_pattern_itm {
	byte Tid();
	void Compile(byte[] src, int bgn, int end);
	int Match(Gfo_pattern_ctx ctx, byte[] src, int src_len, int pos);
	void Xto_str(String_bldr sb);
}
class Gfo_pattern_itm_text implements Gfo_pattern_itm {
	public Gfo_pattern_itm_text() {}
	public byte Tid() {return Gfo_pattern_itm_.Tid_text;}
	public byte[] Text() {return text;} private byte[] text; private int text_len;
	public void Xto_str(String_bldr sb) {sb.Add(this.Tid()).Add("|" + String_.new_u8(text));}
	public void Compile(byte[] src, int bgn, int end) {
		this.text = Bry_.Mid(src, bgn, end);
		this.text_len = end - bgn;
	}
	public int Match(Gfo_pattern_ctx ctx, byte[] src, int src_len, int pos) {
		boolean pass = false;
		int text_end = pos + text_len;
		if (text_end > src_len) text_end = src_len;
		if (ctx.Prv_was_wild()) {
			int text_bgn = Bry_find_.Find_fwd(src, text, pos);
			pass = text_bgn != Bry_find_.Not_found;
			if (pass)
				pos = text_bgn + text_len;
		}
		else {
			pass = Bry_.Match(src, pos, text_end, text);
			if (pass)
				pos = text_end;
		}
		if (!pass) ctx.Rslt_fail_();
		ctx.Prv_was_wild_(false);
		return pos;
	}
}
class Gfo_pattern_itm_wild implements Gfo_pattern_itm {
	public byte Tid() {return Gfo_pattern_itm_.Tid_wild;}
	public void Compile(byte[] src, int bgn, int end) {}
	public int Match(Gfo_pattern_ctx ctx, byte[] src, int src_len, int pos) {
		ctx.Prv_was_wild_(true);
		return ctx.Itm_idx_is_last() ? src_len : pos;
	}
	public void Xto_str(String_bldr sb) {sb.Add(this.Tid()).Add("|*");}
	public static final Gfo_pattern_itm_wild Instance = new Gfo_pattern_itm_wild(); Gfo_pattern_itm_wild() {}
}
