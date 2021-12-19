/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.tdbs;
import gplx.core.brys.Int_flag_bldr_;
import gplx.core.encoders.Base85_;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
public class Xotdb_page_itm_ {
	static final int Txt_len_id = 5, Txt_len_fil_idx = 5, Txt_len_row_idx = 5, Txt_len_type = 1, Txt_len_text_len = 5;
	public static final int Txt_ttl_pos        = Txt_len_id + Txt_len_fil_idx + Txt_len_row_idx + Txt_len_type + Txt_len_text_len + 5;
	public static final int Txt_ttl_len__fixed = Txt_len_id + Txt_len_fil_idx + Txt_len_row_idx + Txt_len_type + Txt_len_text_len + 5 + 1;    // 5=| 1=\n
	public static Xowd_page_itm Txt_ttl_load(byte[] bry) {
		Xowd_page_itm rv = new Xowd_page_itm();
		Txt_ttl_load(rv, bry, 0, bry.length);
		return rv;
	}
	public static void Txt_ttl_load(Xowd_page_itm page, byte[] bry) {Txt_ttl_load(page, bry, 0, bry.length);}
	private static void Txt_ttl_load(Xowd_page_itm page, byte[] bry, int bgn, int end) {
		try {
			page.Init_by_tdb
			( Base85_.To_int_by_bry    (bry, bgn +  0, bgn +  4)
			, Base85_.To_int_by_bry    (bry, bgn +  6, bgn + 10)
			, Base85_.To_int_by_bry    (bry, bgn + 12, bgn + 16)
			,                             bry[18] == AsciiByte.Num1
			, Base85_.To_int_by_bry    (bry, bgn + 20, bgn + 24)
			, page.Ns_id()
			, BryLni.Mid                    (bry, bgn + 26, end)
			);
		} catch (Exception e) {throw ErrUtl.NewArgs(e, "parse_by_ttl failed", "ttl", StringUtl.NewU8(bry, bgn, end));}
	}
	public static void Txt_ttl_save(BryWtr bfr, Xowd_page_itm page) {Txt_ttl_save(bfr, page.Id(), page.Text_db_id(), page.Tdb_row_idx(), page.Redirected(), page.Text_len(), page.Ttl_page_db());}
	public static void Txt_ttl_save(BryWtr bfr, int id, int file_idx, int row_idx, boolean redirect, int text_len, byte[] ttl_wo_ns) {
		BryBfrBase85.AddBase85Len5(bfr, id)      .AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, file_idx).AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, row_idx) .AddBytePipe();
		bfr.AddByte(redirect ? AsciiByte.Num1 : AsciiByte.Num0).AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, text_len).AddBytePipe();
		bfr.Add(ttl_wo_ns)          .AddByteNl();
	}
	public static void Txt_id_load(Xowd_page_itm page, byte[] bry) {Txt_id_load(page, bry, 0, bry.length);}
	private static void Txt_id_load(Xowd_page_itm page, byte[] bry, int bgn, int end) {
		try {
			page.Clear();
			page.Init_by_tdb
			( Base85_.To_int_by_bry    (bry, bgn +  0, bgn +  4)
			, Base85_.To_int_by_bry    (bry, bgn +  6, bgn + 10)
			, Base85_.To_int_by_bry    (bry, bgn + 12, bgn + 16)
			,                             bry[18] == AsciiByte.Num1
			, Base85_.To_int_by_bry    (bry, bgn + 20, bgn + 24)
			, Base85_.To_int_by_bry    (bry, bgn + 26, bgn + 30)
			, BryLni.Mid                    (bry, bgn + 32, end)
			);
		} catch (Exception e) {throw ErrUtl.NewArgs(e, "parse_by_id failed", "id", StringUtl.NewU8(bry, bgn, end));}
	}
	public static void Txt_id_save(BryWtr bfr, Xowd_page_itm page) {
		BryBfrBase85.AddBase85Len5(bfr, page.Id())          .AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, page.Text_db_id())  .AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, page.Tdb_row_idx()) .AddBytePipe();
		bfr.AddByte(page.Redirected() ? AsciiByte.Num1 : AsciiByte.Num0).AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, page.Text_len())    .AddBytePipe();
		BryBfrBase85.AddBase85Len5(bfr, page.Ns_id())       .AddBytePipe();
		bfr.Add(page.Ttl_page_db())            .AddByteNl();
	}
	public static void Txt_page_save(BryWtr bfr, int id, GfoDate modified_on, byte[] title, byte[] text, boolean add_nl) {
		int ts = Int_flag_bldr_.To_int_date_short(modified_on.ToSegAry());
		BryBfrBase85.AddBase85(bfr, id    , Base85_.Len_int).AddByte(Txt_page_dlm);            // needed for mass template load
		BryBfrBase85.AddBase85(bfr, ts    , Base85_.Len_int).AddByte(Txt_page_dlm);
		bfr.Add(title)                         .AddByte(Txt_page_dlm);            // needed for rebuilding ttl files
		bfr.Add(text)                          .AddByte(Txt_page_dlm);
		if (add_nl)
			bfr.AddByteNl(); // NOTE: each page row is separated by \t\n
	}
	public static final byte Txt_page_dlm = AsciiByte.Tab;
	public static final int Txt_page_len__fixed = 1 + 5 + 1 + 5 + 1 + 1 + 1;    // \tid|date|title|text\n
}
