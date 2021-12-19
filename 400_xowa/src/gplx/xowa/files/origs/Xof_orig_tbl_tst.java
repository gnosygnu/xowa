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
package gplx.xowa.files.origs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_bldr;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.files.Xof_exec_tid;
import gplx.xowa.files.Xof_ext_;
import gplx.xowa.files.Xof_fsdb_itm;
import gplx.xowa.files.Xof_img_size;
import gplx.xowa.files.Xof_lnki_page;
import gplx.xowa.files.Xof_lnki_time;
import gplx.xowa.files.Xof_patch_upright_tid_;
import gplx.xowa.parsers.lnkis.Xop_lnki_type;
import org.junit.Before;
import org.junit.Test;
public class Xof_orig_tbl_tst {
	@Before public void init() {fxt.Clear();} private Xof_orig_tbl_fxt fxt = new Xof_orig_tbl_fxt();
	@Test public void Select_in() {
		Xof_orig_itm itm_1 = fxt.Exec_insert("A.png", 220, 330);
		fxt.Exec_insert("B.png", 220, 330);
		Xof_orig_itm itm_3 = fxt.Exec_insert("C.png", 220, 330);
		fxt.Test_select_in(StringUtl.Ary("A.png", "C.png"), itm_1, itm_3);
	}
}
class Xof_orig_tbl_fxt {
	private Xof_orig_tbl tbl;
	public void Clear() {
		Io_url test_url = Io_url_.mem_fil_("mem/file/en.wikipedia.org/file/orig_regy");
		Db_conn_bldr.Instance.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.Instance.New(test_url);
		tbl = new Xof_orig_tbl(conn, BoolUtl.Y);
		tbl.Create_tbl();
	}
	public Xof_orig_itm Exec_insert(String ttl, int w, int h) {
		byte[] ttl_bry = BryUtl.NewU8(ttl);
		Xof_orig_itm rv = new Xof_orig_itm(Xof_orig_itm.Repo_comm, ttl_bry, Xof_ext_.new_by_ttl_(ttl_bry).Id(), w, h, BryUtl.Empty);
		tbl.Insert(rv.Repo(), rv.Ttl(), rv.Ext_id(), rv.W(), rv.H(), rv.Redirect());
		return rv;
	}
	public void Test_select_in(String[] itms, Xof_orig_itm... expd) {
		Ordered_hash rv = Ordered_hash_.New();
		List_adp list = List_adp_.New();
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; ++i) {
			String itm = itms[i];
			Xof_fsdb_itm fsdb_itm = new Xof_fsdb_itm();
			fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, BryUtl.NewA7("en.w"), BryUtl.NewU8(itm), Xop_lnki_type.Id_none, Xof_img_size.Upright_null, Xof_img_size.Null, Xof_img_size.Null, Xof_lnki_time.Null, Xof_lnki_page.Null, Xof_patch_upright_tid_.Tid_all);
			list.Add(fsdb_itm);
		}
		tbl.Select_by_list(rv, list);
		GfoTstr.EqLines(To_str_ary(expd), To_str_ary((Xof_orig_itm[])rv.ToAry(Xof_orig_itm.class)));
	}
	private static String To_str_ary(Xof_orig_itm... ary) {
		BryWtr bfr = BryWtr.NewAndReset(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xof_orig_itm itm = ary[i];
			bfr	.AddByte(itm.Repo()).AddBytePipe()
				.Add(itm.Ttl()).AddBytePipe()
				.AddIntVariable(itm.Ext_id()).AddBytePipe()
				.AddIntVariable(itm.W()).AddBytePipe()
				.AddIntVariable(itm.H()).AddBytePipe()
				.Add(itm.Redirect()).AddBytePipe()
				;
			bfr.AddByteNl();
		}
		return bfr.ToStrAndClear();
	}
}
