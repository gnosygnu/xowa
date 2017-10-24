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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class XomwMagicWordSynonym {
	public final    byte[] magic_name;
	public final    boolean case_match;
	public final    byte[] text;
	public final    byte[] text_wo_arg1;
	public final    byte arg1_tid;
	public XomwMagicWordSynonym(byte[] magic_name, boolean case_match, byte[] text) {
		this.magic_name = magic_name;
		this.case_match = case_match;
		this.text = text;
		this.arg1_tid = Get_arg1_tid(text);
		switch (arg1_tid) {
			case Arg1__bgn:
				text_wo_arg1 = Bry_.Mid(text, 2);
				break;
			case Arg1__end:
				text_wo_arg1 = Bry_.Mid(text, 0, text.length - 2);
				break;
			default:
				text_wo_arg1 = text;
				break;
		}
	}

	private static byte Get_arg1_tid(byte[] src) {
		int len = src.length;
		byte rv = Arg1__nil;
		int cur = 0;
		while (true) {
			if (cur == len) break;
			byte b = src[cur];
			// "$" matched
			if (b == Byte_ascii.Dollar) {
				// "1" matched?
				int nxt_pos = cur + 1;
				if (nxt_pos < len && src[nxt_pos] == Byte_ascii.Num_1) {
					// "$1" matched
					if (cur == 0) {
						rv = Arg1__bgn;
					}
					else if (cur == len - 2) {							
						rv = rv == Arg1__nil ? Arg1__end : Arg1__mix;
					}
					else {
						if (rv == Arg1__nil)
							rv = Arg1__mid;
						else if (rv == Arg1__mid)
							rv = Arg1__mix;
					}
					cur += 2;
					continue;
				}
				else {
					cur += 1;
					continue;
				}
			}
			else {
				cur += 1;
				continue;
			}
		}
		return rv;
	}

	public static final byte 
	  Arg1__nil = 0 // EX: "thumb"
	, Arg1__bgn = 1 // EX: "$1px"
	, Arg1__end = 2 // EX: "thumb=$1"
	, Arg1__mid = 3 // EX: "a$1b"
	, Arg1__mix = 4 // EX: "a$1b$cc"
	;
}
