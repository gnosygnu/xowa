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
package gplx.xowa.wikis.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.*; import gplx.core.encoders.*; import gplx.xowa.wikis.data.tbls.*;
public class Xotdb_page_itm_ {
	static final int Txt_len_id = 5, Txt_len_fil_idx = 5, Txt_len_row_idx = 5, Txt_len_type = 1, Txt_len_text_len = 5;
	public static final int Txt_ttl_pos		= Txt_len_id + Txt_len_fil_idx + Txt_len_row_idx + Txt_len_type + Txt_len_text_len + 5;
	public static final int Txt_ttl_len__fixed = Txt_len_id + Txt_len_fil_idx + Txt_len_row_idx + Txt_len_type + Txt_len_text_len + 5 + 1;	// 5=| 1=\n
	public static Xowd_page_itm Txt_ttl_load(byte[] bry) {
		Xowd_page_itm rv = new Xowd_page_itm();
		Txt_ttl_load(rv, bry, 0, bry.length);
		return rv;
	}
	public static void Txt_ttl_load(Xowd_page_itm page, byte[] bry) {Txt_ttl_load(page, bry, 0, bry.length);}
	private static void Txt_ttl_load(Xowd_page_itm page, byte[] bry, int bgn, int end) {
		try {
			page.Init_by_tdb
			( Base85_.To_int_by_bry	(bry, bgn +  0, bgn +  4)
			, Base85_.To_int_by_bry	(bry, bgn +  6, bgn + 10)
			, Base85_.To_int_by_bry	(bry, bgn + 12, bgn + 16)
			,							 bry[18] == Byte_ascii.Num_1
			, Base85_.To_int_by_bry	(bry, bgn + 20, bgn + 24)
			, page.Ns_id()
			, Bry_.Mid					(bry, bgn + 26, end)
			);
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "parse_by_ttl failed", "ttl", String_.new_u8(bry, bgn, end));}
	}
	public static void Txt_ttl_save(Bry_bfr bfr, Xowd_page_itm page) {Txt_ttl_save(bfr, page.Id(), page.Text_db_id(), page.Tdb_row_idx(), page.Redirected(), page.Text_len(), page.Ttl_page_db());}
	public static void Txt_ttl_save(Bry_bfr bfr, int id, int file_idx, int row_idx, boolean redirect, int text_len, byte[] ttl_wo_ns) {
		bfr	.Add_base85_len_5(id)					.Add_byte_pipe()
			.Add_base85_len_5(file_idx)				.Add_byte_pipe()
			.Add_base85_len_5(row_idx)				.Add_byte_pipe()
			.Add_byte(redirect ? Byte_ascii.Num_1 : Byte_ascii.Num_0).Add_byte_pipe()
			.Add_base85_len_5(text_len)				.Add_byte_pipe()
			.Add(ttl_wo_ns)							.Add_byte_nl()
			;
	}
	public static void Txt_id_load(Xowd_page_itm page, byte[] bry) {Txt_id_load(page, bry, 0, bry.length);}
	private static void Txt_id_load(Xowd_page_itm page, byte[] bry, int bgn, int end) {
		try {
			page.Clear();
			page.Init_by_tdb
			( Base85_.To_int_by_bry	(bry, bgn +  0, bgn +  4)
			, Base85_.To_int_by_bry	(bry, bgn +  6, bgn + 10)
			, Base85_.To_int_by_bry	(bry, bgn + 12, bgn + 16)
			,							 bry[18] == Byte_ascii.Num_1
			, Base85_.To_int_by_bry	(bry, bgn + 20, bgn + 24)
			, Base85_.To_int_by_bry	(bry, bgn + 26, bgn + 30)
			, Bry_.Mid					(bry, bgn + 32, end)
			);
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "parse_by_id failed", "id", String_.new_u8(bry, bgn, end));}
	}
	public static void Txt_id_save(Bry_bfr bfr, Xowd_page_itm page) {
		bfr	.Add_base85_len_5(page.Id())					.Add_byte_pipe()
			.Add_base85_len_5(page.Text_db_id())			.Add_byte_pipe()
			.Add_base85_len_5(page.Tdb_row_idx())			.Add_byte_pipe()
			.Add_byte(page.Redirected() ? Byte_ascii.Num_1 : Byte_ascii.Num_0).Add_byte_pipe()
			.Add_base85_len_5(page.Text_len())				.Add_byte_pipe()
			.Add_base85_len_5(page.Ns_id())					.Add_byte_pipe()
			.Add(page.Ttl_page_db())						.Add_byte_nl();
	}
	public static void Txt_page_save(Bry_bfr bfr, int id, DateAdp modified_on, byte[] title, byte[] text, boolean add_nl) {
		int ts = Int_flag_bldr_.To_int_date_short(modified_on.XtoSegAry());
		bfr	.Add_base85(id	, Base85_.Len_int)			.Add_byte(Txt_page_dlm)			// needed for mass template load
			.Add_base85(ts	, Base85_.Len_int)			.Add_byte(Txt_page_dlm)
			.Add(title)										.Add_byte(Txt_page_dlm)			// needed for rebuilding ttl files
			.Add(text)										.Add_byte(Txt_page_dlm);
		if (add_nl)
			bfr.Add_byte_nl(); // NOTE: each page row is separated by \t\n
	}
	public static final byte Txt_page_dlm = Byte_ascii.Tab;
	public static final int Txt_page_len__fixed = 1 + 5 + 1 + 5 + 1 + 1 + 1;	// \tid|date|title|text\n
}
