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
package gplx.xowa.apps.utls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Xoa_url_encoder {	// NOTE: redundant with Gfo_url_encoder, but is simpler; DATE:2016-09-15
	private final BryWtr bfr = BryWtr.New();
	public byte[] Encode(byte[] src) {
		int src_len = src.length;
		boolean dirty = false;
		for (int i = 0; i < src_len; ++i) {
			byte b = src[i];
			byte[] repl = null;
			switch (b) {
				case AsciiByte.Space:		repl = Bry__underline; break;
				case AsciiByte.Amp:		repl = Bry__amp; break;
				case AsciiByte.Apos:		repl = Bry__apos; break;
				case AsciiByte.Eq:			repl = Bry__eq; break;
				case AsciiByte.Plus:		repl = Bry__plus; break;
			}

			// not a replacement sequence
			if (repl == null) {
				// if dirty, add to bfr; else, ignore
				if (dirty)
					bfr.AddByte(b);
			}
			else {
				// if clean, add everything before cur_pos to bfr
				if (!dirty) {
					bfr.AddMid(src, 0, i);
					dirty = true;
				}
				bfr.Add(repl);
			}
		}
		return dirty ? bfr.ToBryAndClear() : src;
	}
	private static final byte[] Bry__amp = BryUtl.NewA7("%26"), Bry__eq = BryUtl.NewA7("%3D")
	, Bry__plus = BryUtl.NewA7("%2B"), Bry__apos = BryUtl.NewA7("%27")
	, Bry__underline = new byte[] {AsciiByte.Underline}
	;
}
