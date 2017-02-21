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
package gplx.xowa.wikis.tdbs.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.primitives.*;
public class Xof_meta_thumb_parser extends Obj_ary_parser_base {
	private final    Gfo_number_parser number_parser = new Gfo_number_parser(); 
	private final    Int_ary_parser int_ary_parser = new Int_ary_parser();
	public Xof_meta_thumb[] Ary() {return ary;} private Xof_meta_thumb[] ary = new Xof_meta_thumb[Ary_max]; static final int Ary_max = 16;
	public int Len() {return ary_idx;} private int ary_idx;
	public void Parse_ary(byte[] bry, int bgn, int end) {super.Parse_core(bry, bgn, end, Byte_ascii.Semic, Byte_ascii.Null);}
	public Xof_meta_thumb Parse_one(byte[] bry, int bgn, int end) {
		super.Parse_core(bry, bgn, end, Byte_ascii.Semic, Byte_ascii.Null);
		return ary[0];
	}
	public void Clear() {if (ary.length > Ary_max) ary = new Xof_meta_thumb[Ary_max];}
	@Override protected void Ary_len_(int v) {
		ary_idx = 0;
		if	(v > Ary_max) ary = new Xof_meta_thumb[v];
	}
	@Override protected void Parse_itm(byte[] bry, int bgn, int end) {	// EX: "1:45,40"; "1:45,40:3,4"
		Xof_meta_thumb itm = new Xof_meta_thumb(); boolean height_found = false;
		if (end - 2 < bgn)	throw Err_.new_wo_type("itm must be at least 2 bytes long", "itm", String_.new_u8(bry, bgn, end)); // EX: 4,6
		int pos = bgn;
		byte exists_byte = bry[pos];
		switch (exists_byte) {
			case Byte_ascii.Num_0: itm.Exists_(Xof_meta_itm.Exists_n); break;
			case Byte_ascii.Num_1: itm.Exists_(Xof_meta_itm.Exists_y); break;
			case Byte_ascii.Num_2: itm.Exists_(Xof_meta_itm.Exists_unknown); break;
			default: throw Err_.new_wo_type("exists must be 0,1,2", "exists", exists_byte, "itm", String_.new_u8(bry, bgn, end));
		}
		if (bry[pos + 1] != Dlm_exists) throw Err_.new_wo_type("question must follow exists", "bad_char", bry[pos + 1], "itm", String_.new_u8(bry, bgn, end));
		pos += 2;
		int num_bgn = pos;
		while (pos < end) {
			byte b = bry[pos];
			switch (b) {
				case Dlm_width:	// "," found; assume width; note that seek commas will be handled by seek
					itm.Width_(number_parser.Parse(bry, num_bgn, pos).Rv_as_int());
					num_bgn = pos + Int_.Const_dlm_len;
					break;
				case Dlm_seek:
					itm.Height_(number_parser.Parse(bry, num_bgn, pos).Rv_as_int());
					num_bgn = pos + Int_.Const_dlm_len;
					height_found = true;
					itm.Seeks_(int_ary_parser.Parse_ary(bry, num_bgn, end, Byte_ascii.Comma));
					pos = end;
					break;
			}
			++pos;
		}
		if (!height_found)	// handle '1:2,3' as opposed to '1:2,3@4'
			itm.Height_(number_parser.Parse(bry, num_bgn, end).Rv_as_int());
		ary[ary_idx++] = itm;
	}
	static final String GRP_KEY = "xowa.meta.itm.file";
	public static final byte Dlm_exists = Byte_ascii.Question, Dlm_seek = Byte_ascii.At, Dlm_width = Byte_ascii.Comma;
	public static final String Dlm_seek_str = "@";
}
