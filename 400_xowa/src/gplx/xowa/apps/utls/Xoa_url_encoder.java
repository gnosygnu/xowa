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
package gplx.xowa.apps.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_url_encoder {	// NOTE: redundant with Gfo_url_encoder, but is simpler; DATE:2016-09-15
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public byte[] Encode(byte[] src) {
		int src_len = src.length;
		boolean dirty = false;
		for (int i = 0; i < src_len; ++i) {
			byte b = src[i];
			byte[] repl = null;
			switch (b) {
				case Byte_ascii.Space:		repl = Bry__underline; break;
				case Byte_ascii.Amp:		repl = Bry__amp; break;
				case Byte_ascii.Apos:		repl = Bry__apos; break;
				case Byte_ascii.Eq:			repl = Bry__eq; break;
				case Byte_ascii.Plus:		repl = Bry__plus; break;
			}

			// not a replacement sequence
			if (repl == null) {
				// if dirty, add to bfr; else, ignore
				if (dirty)
					bfr.Add_byte(b);
			}
			else {
				// if clean, add everything before cur_pos to bfr
				if (!dirty) {
					bfr.Add_mid(src, 0, i);
					dirty = true;
				}
				bfr.Add(repl);
			}
		}
		return dirty ? bfr.To_bry_and_clear() : src;
	}
	private static final    byte[] Bry__amp = Bry_.new_a7("%26"), Bry__eq = Bry_.new_a7("%3D")
	, Bry__plus = Bry_.new_a7("%2B"), Bry__apos = Bry_.new_a7("%27")
	, Bry__underline = new byte[] {Byte_ascii.Underline}
	;
}
