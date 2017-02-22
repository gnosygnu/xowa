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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.strings.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.infos.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
public class Db_mgr_fxt {
	public Db_mgr_fxt Ctor_fsys()	{bldr_fxt = new Xob_fxt().Ctor(Xoa_test_.Url_root().GenSubDir("root")); return this;} 
	public Db_mgr_fxt Ctor_mem()	{bldr_fxt = new Xob_fxt().Ctor_mem(); return this;} private Xob_fxt bldr_fxt;
	public Xowd_page_itm page_(int id, String modified_on, boolean type_redirect, int text_len) {return new Xowd_page_itm().Id_(id).Modified_on_(DateAdp_.parse_gplx(modified_on)).Redirected_(type_redirect).Text_len_(text_len);}
	public Xowe_wiki Wiki() {return bldr_fxt.Wiki();}
	public Xob_bldr Bldr() {return bldr_fxt.Bldr();}
	public Db_mgr_fxt doc_ary_(Xowd_page_itm... v) {bldr_fxt.doc_ary_(v); return this;}
	public Xowd_page_itm doc_(int id, String date, String title, String text) {return bldr_fxt.doc_(id, date, title, text);}
	public Xowd_page_itm doc_wo_date_(int id, String title, String text) {return bldr_fxt.doc_(id, "2012-01-02 03:04", title, text);}
	public Xowd_page_itm doc_ttl_(int id, String title) {return bldr_fxt.doc_(id, "2012-01-02 03:04", title, "IGNORE");}
	public Db_mgr_fxt Init_fil(String url, String raw) {return Init_fil(Io_url_.new_fil_(url), raw);}
	public Db_mgr_fxt Init_fil(Io_url url, String raw) {Io_mgr.Instance.SaveFilStr(url, raw); return this;}
	public Db_mgr_fxt Exec_run(Xob_page_wkr wkr)		{bldr_fxt.Run(wkr); return this;}
	public Db_mgr_fxt Exec_run(Xob_cmd cmd)			{bldr_fxt.Run_cmds(cmd); return this;}
	public Db_mgr_fxt Exec_run(Xobd_parser_wkr wkr) {bldr_fxt.Run(wkr); return this;}
	public void Init_page_insert(Int_obj_ref page_id_next, int ns_id, String[] ttls) {
		Xowe_wiki wiki = this.Wiki();
		int len = ttls.length;
		DateAdp modified_on = Datetime_now.Dflt_add_min_(0);
		Xowd_page_tbl tbl_page = wiki.Db_mgr_as_sql().Core_data_mgr().Tbl__page();
		tbl_page.Insert_bgn();
		for (int i = 0; i < len; i++) {
			String ttl = ttls[i];
			int page_id = page_id_next.Val();
			tbl_page.Insert_cmd_by_batch(page_id, ns_id, Bry_.new_u8(ttl), false, modified_on, 0, page_id, 0, 0, -1);
			page_id_next.Val_add(1);
		}
		tbl_page.Insert_end();
	}
	public void Test_load_ttl(int ns_id, String ttl_str, Xowd_page_itm expd) {
		Xowe_wiki wiki = bldr_fxt.Wiki();
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		byte[] ttl_bry = Bry_.new_a7(ttl_str);
		wiki.Db_mgr_as_sql().Load_mgr().Load_by_ttl(actl, ns, ttl_bry);
		Tfds.Eq(expd.Id(), actl.Id());
		Tfds.Eq_date(expd.Modified_on(), actl.Modified_on());
		Tfds.Eq(expd.Redirected(), actl.Redirected());
		Tfds.Eq(expd.Text_len(), actl.Text_len());
	}	private Xowd_page_itm actl = new Xowd_page_itm();
	public void Test_load_page(int ns_id, int page_id, String expd) {
		Xowe_wiki wiki = bldr_fxt.Wiki();
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		wiki.Db_mgr_as_sql().Load_mgr().Load_page(actl.Id_(page_id), ns);
		Tfds.Eq(expd, String_.new_a7(actl.Text()));
	}
	int[] Xto_int_ary(List_adp rslts) {
		int len = rslts.Count();
		int[] rv = new int[len];
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = (Xowd_page_itm)rslts.Get_at(i);
			rv[i] = page.Id();
		}
		return rv;
	}
	public void Test_file(String url, String expd) {
		String actl = Io_mgr.Instance.LoadFilStr(url);
		Tfds.Eq_str_lines(expd, actl);
	}
	public void Init_db_sqlite() {
		Xowe_wiki wiki = this.Wiki();
		Db_conn_pool.Instance.Rls_all();
		Db_conn_bldr.Instance.Reg_default_sqlite();
		Io_mgr.Instance.DeleteDir_cmd(wiki.Fsys_mgr().Root_dir()).MissingIgnored_().Exec();
		wiki.Db_mgr_create_as_sql().Core_data_mgr().Init_by_make(Xowd_core_db_props.Test, Xob_info_session.Test);
		Io_mgr.Instance.SaveFilStr(wiki.Import_cfg().Src_dir().GenSubFil("a.xml"), "<test/>");
	}
	public void Rls() {
		this.Wiki().Db_mgr_as_sql().Core_data_mgr().Rls();
	}
}
