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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Int_ary_parser extends Obj_ary_parser_base {
	private final    Gfo_number_parser parser = new Gfo_number_parser(); 
	private int[] ary; private int ary_idx;
	public int[] Parse_ary(String str, byte dlm) {byte[] bry = Bry_.new_u8(str); return Parse_ary(bry, 0, bry.length, dlm);}
	public int[] Parse_ary(byte[] bry, int bgn, int end, byte dlm) {
		Parse_core(bry, bgn, end, dlm, Byte_ascii.Null);
		return ary;
	}
	@Override protected void Ary_len_(int v) {
		if (v == 0)
			ary = Int_.Ary_empty;
		else {
			ary = new int[v];	// NOTE: always create new array; never reuse;
			ary_idx = 0;
		}
	}
	@Override protected void Parse_itm(byte[] bry, int bgn, int end) {
		parser.Parse(bry, bgn, end); if (parser.Has_err() || parser.Has_frac()) throw Err_.new_wo_type("failed to parse number", "val", String_.new_u8(bry, bgn, end));
		ary[ary_idx++] = parser.Rv_as_int();
	}
}
