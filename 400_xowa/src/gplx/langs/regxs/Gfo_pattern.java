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
public class Gfo_pattern {
	private final Gfo_pattern_itm[] itms; private final int itms_len;
	private final Gfo_pattern_ctx ctx = new Gfo_pattern_ctx();
	public Gfo_pattern(byte[] raw) {
		this.raw = raw;
		itms = Gfo_pattern_itm_.Compile(raw);
		itms_len = itms.length;
	}
	public byte[] Raw() {return raw;} private byte[] raw;
	public boolean Match(byte[] val) {
		int val_len = val.length;
		int val_pos = 0;
		ctx.Init(itms_len);
		for (int i = 0; i < itms_len; ++i) {
			Gfo_pattern_itm itm = itms[i];
			ctx.Itm_idx_(i);
			val_pos = itm.Match(ctx, val, val_len, val_pos);
			if (!ctx.Rslt_pass()) return false;
		}
		return ctx.Rslt_pass() && val_pos == val_len;
	}
	public static Gfo_pattern[] Parse_to_ary(byte[] raw) {
		byte[][] patterns = Bry_split_.Split(raw, Byte_ascii.Semic, true);
		int patterns_len = patterns.length;
		Gfo_pattern[] rv = new Gfo_pattern[patterns_len];
		for (int i = 0; i < patterns_len; ++i) {
			byte[] pattern = patterns[i];
			rv[i] = new Gfo_pattern(pattern);
		}
		return rv;
	}
}
