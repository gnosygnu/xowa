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
package gplx.langs.htmls.clses; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.langs.htmls.docs.*;
public class Gfh_class_parser_ {
	public static void Parse(Gfh_tag tag, Gfh_class_parser_wkr wkr) {
		Gfh_atr atr = tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__class);
		if (atr.Val_dat_exists())
			Parse(tag.Src(), atr.Val_bgn(), atr.Val_end(), wkr);
	}
	public static void Parse(byte[] src, int src_bgn, int src_end, Gfh_class_parser_wkr wkr) {
		int atr_idx = 0, tmp_bgn = -1, tmp_end = -1;
		int pos = src_bgn;
		while (true) {
			boolean pos_is_last = pos == src_end;
			byte b = pos_is_last ? Byte_ascii.Space : src[pos];
			switch (b) {
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:						
					if (tmp_bgn != -1) { // ignore empty atrs
						if (!wkr.On_cls(src, atr_idx, src_bgn, src_end, tmp_bgn, tmp_end))
							pos_is_last = true;
					}
					++atr_idx; tmp_bgn = -1; tmp_end = -1;
					break;
				default:
					if (tmp_bgn == -1) tmp_bgn = pos;
					tmp_end = pos + 1;
					break;
			}
			if (pos_is_last) break;
			++pos;
		}
	}
}
