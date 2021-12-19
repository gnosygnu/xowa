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
package gplx.xowa.users.history;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateNow;
import gplx.types.errs.ErrUtl;
public class Xou_history_itm {
	private Xou_history_itm() {}
	public Xou_history_itm(byte[] wiki, byte[] page) {
		// remove "\n" from page for Category b/c it breaks the csv_parser; DATE:2016-10-12
		int nl_pos = BryFind.FindFwd(page, AsciiByte.Nl);
		if (nl_pos != BryFind.NotFound) {
			page = BryLni.Mid(page, 0, nl_pos);
		}

		this.wiki = wiki;
		this.page = page;
		this.key = key_(wiki, page);
		this.view_bgn = GfoDateNow.Get();
	}
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Wiki() {return wiki;} private byte[] wiki;
	public byte[] Page() {return page;} private byte[] page;
	public int View_count() {return view_count;} private int view_count;
	public GfoDate View_bgn() {return view_bgn;} GfoDate view_bgn;
	public GfoDate View_end() {return view_end;} GfoDate view_end;
	public Object Fld(int idx) {
		switch (idx) {
			case Fld_key		: return key;
			case Fld_wiki		: return wiki;
			case Fld_page		: return page;
			case Fld_view_count	: return view_count;
			case Fld_view_bgn	: return view_bgn;
			case Fld_view_end	: return view_end;
			default				: throw ErrUtl.NewUnhandled(idx);
		}
	}
	public void Tally() {
		view_end = view_count == 0 ? view_bgn : GfoDateNow.Get();
		view_count++;
	}
	public void Merge(Xou_history_itm merge) {
		view_count += merge.View_count();
		if (merge.View_bgn().compareTo(view_bgn) < CompareAbleUtl.Same) view_bgn = merge.View_bgn();
		if (merge.View_end().compareTo(view_end) > CompareAbleUtl.Same) view_end = merge.View_end();
	}
	public static Xou_history_itm csv_(byte[] ary, IntRef pos) {
		Xou_history_itm rv = new Xou_history_itm();
		rv.view_bgn		= BryUtlByWtr.ReadCsvDte(ary, pos, BryUtl.DlmFld);
		rv.view_end		= BryUtlByWtr.ReadCsvDte(ary, pos, BryUtl.DlmFld);
		rv.view_count	= BryUtlByWtr.ReadCsvInt(ary, pos, BryUtl.DlmFld);
		rv.wiki			= BryUtlByWtr.ReadCsvBry(ary, pos, BryUtl.DlmFld);
		rv.page			= BryUtlByWtr.ReadCsvBry(ary, pos, BryUtl.DlmRow);
		rv.key			= key_(rv.wiki, rv.page);
		return rv;
	}
	public void Save(BryWtr bb) {
		bb	.AddDate(view_bgn)				.AddByte(BryUtl.DlmFld)
			.AddDate(view_end)				.AddByte(BryUtl.DlmFld)
			.AddIntVariable(view_count)	.AddByte(BryUtl.DlmFld)
			.Add(wiki)						.AddByte(BryUtl.DlmFld)
			.Add(page)						.AddByte(BryUtl.DlmRow);
	}
	public static byte[] key_(byte[] wiki, byte[] page) {return BryUtl.Add(wiki, Key_dlm, page);} private static final byte[] Key_dlm = AsciiByte.PipeBry;
	public static final byte Fld_key = 0, Fld_wiki = 1, Fld_page = 2, Fld_view_count = 3, Fld_view_bgn = 4, Fld_view_end = 5;
}
