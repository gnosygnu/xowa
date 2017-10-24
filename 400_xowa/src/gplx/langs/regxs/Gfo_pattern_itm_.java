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
public class Gfo_pattern_itm_ {
	public static final byte Tid_text = 0, Tid_wild = 1;
	public static Gfo_pattern_itm[] Compile(byte[] raw) {
		List_adp rv = List_adp_.New();
		int raw_len = raw.length;
		int itm_bgn = -1;
		Gfo_pattern_itm itm = null;
		int pos = 0;
		while (true) {
			boolean last = pos == raw_len;
			byte b = last ? Byte_ascii.Null : raw[pos];
			switch (b) {
				case Byte_ascii.Null:
					if (itm != null) {itm.Compile(raw, itm_bgn, pos); itm = null; itm_bgn = -1;}
					break;
				case Byte_ascii.Star:
					if (itm != null) {itm.Compile(raw, itm_bgn, pos); itm = null; itm_bgn = -1;}
					rv.Add(Gfo_pattern_itm_wild.Instance);
					break;
				default:
					if (itm_bgn == -1) {
						itm_bgn = pos;
						itm = new Gfo_pattern_itm_text();
						rv.Add(itm);
					}
					break;
			}
			++pos;
			if (last) break;
		}
		return (Gfo_pattern_itm[])rv.To_ary_and_clear(Gfo_pattern_itm.class);
	}
}
