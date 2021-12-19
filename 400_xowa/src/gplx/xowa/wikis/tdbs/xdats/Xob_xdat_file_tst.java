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
package gplx.xowa.wikis.tdbs.xdats;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import org.junit.*;
import gplx.xowa.wikis.tdbs.hives.*;
public class Xob_xdat_file_tst {
	@Test public void Find() {
		Xob_xdat_file rdr = rdr_("!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|", "0|b", "1|d", "2|f", "3|h", "4|j");
		tst_ReadAt(rdr, 0, "0|b");
		tst_ReadAt(rdr, 1, "1|d");
		tst_ReadAt(rdr, 2, "2|f");
		tst_ReadAt(rdr, 3, "3|h");
		tst_ReadAt(rdr, 4, "4|j");
		tst_Find(rdr, "b", 0, false);
		tst_Find(rdr, "j", 4, false);
		tst_Find(rdr, "a", 0, false);
		tst_Find(rdr, "c", 1, false);
		tst_Find(rdr, "k", 4, false);
	}
	@Test public void Update() {
		Xob_xdat_file rdr = rdr_("!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|", "0|b", "1|d", "2|f", "3|h", "4|j");
		tst_Update(rdr, 3, "3|h1\n", StringUtl.ConcatLinesNlSkipLast
		(	"!!!!%|!!!!%|!!!!%|!!!!&|!!!!%|"
		,	"0|b"
		,	"1|d"
		,	"2|f"
		,	"3|h1"
		,	"4|j"
		,	""
		));
	}
	@Test public void Insert() {
		Xob_xdat_file rdr = rdr_("!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|", "0|b", "1|d", "2|f", "3|h", "4|j");
		tst_Insert(rdr, "5|k\n", StringUtl.ConcatLinesNlSkipLast
		(	"!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|"
		,	"0|b"
		,	"1|d"
		,	"2|f"
		,	"3|h"
		,	"4|j"
		,	"5|k"
		,	""
		));
	}
	@Test public void Sort() {
		Xob_xdat_file rdr = rdr_("!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|", "4|j", "2|f", "0|b", "1|d", "3|h");
		Bry_comparer_bgn_eos comparer = new Bry_comparer_bgn_eos(2);
		tst_Sort(rdr, comparer, StringUtl.ConcatLinesNlSkipLast
		(	"!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|"
		,	"0|b"
		,	"1|d"
		,	"2|f"
		,	"3|h"
		,	"4|j"
		,	""
		));
	}
	@Test public void Rebuild_header() {
		String orig = StringUtl.ConcatLinesNl(""								, "4|j", "2|f", "0|b", "1|d", "3|h");
		String expd = StringUtl.ConcatLinesNl("!!!!%|!!!!%|!!!!%|!!!!%|!!!!%|"	, "4|j", "2|f", "0|b", "1|d", "3|h");
		Rebuild_header_tst(orig, expd);
	}
	private void Rebuild_header_tst(String orig, String expd) {		
		GfoTstr.EqLines(expd, StringUtl.NewA7(Xob_xdat_file.Rebuid_header(BryUtl.NewA7(orig), BryUtl.NewA7("\n"))));
	}
	BryWtr tmp = BryWtr.New();
	private void tst_Sort(Xob_xdat_file rdr, ComparerAble comparer, String expd) {
		rdr.Sort(tmp, comparer);
		Chk_file(rdr, expd);
	}
	private void tst_Insert(Xob_xdat_file rdr, String new_val, String expd) {
		rdr.Insert(tmp, BryUtl.NewU8(new_val));
		Chk_file(rdr, expd);
	}
	private void tst_Update(Xob_xdat_file rdr, int idx, String new_val, String expd) {
		Xob_xdat_itm itm = new Xob_xdat_itm(); 
		rdr.GetAt(itm, idx);
		rdr.Update(tmp, itm, BryUtl.NewU8(new_val));
		Chk_file(rdr, expd);
	}
	private void Chk_file(Xob_xdat_file rdr, String expd) {
		Io_url url = Io_url_.new_fil_("mem/test.xdat");
		rdr.Save(url);
		String actl = Io_mgr.Instance.LoadFilStr(url);
		GfoTstr.EqLines(expd, actl);
	}
	private void tst_Find(Xob_xdat_file rdr, String find, int expd, boolean exact) {
		rdr.Find(itm, BryUtl.NewU8(find), 2, AsciiByte.Nl, exact);
		int id = BryUtl.ToIntOr(BryLni.Mid(itm.Itm_bry(), 0, 1), -1);
			GfoTstr.EqObj(expd, id);
	}
	private void tst_ReadAt(Xob_xdat_file rdr, int i, String expd) {rdr.GetAt(itm, i); GfoTstr.EqObj(expd, StringUtl.NewU8(itm.Itm_bry()));}
	Xob_xdat_itm itm = new Xob_xdat_itm();
	Xob_xdat_file rdr_(String... lines) {
		String_bldr sb = String_bldr_.new_();
		int len = lines.length;
		for (int i = 0; i < len; i++) {
			String line = lines[i];
			sb.Add(line).AddCharNl();
		}
		byte[] bry = BryUtl.NewU8(sb.ToStr());
		return new Xob_xdat_file().Parse(bry, bry.length, Io_url_.Empty);
	}
}
