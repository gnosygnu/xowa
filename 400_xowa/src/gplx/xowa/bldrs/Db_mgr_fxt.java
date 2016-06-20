/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.strings.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.ctgs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.infos.*;
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
		DateAdp modified_on = Tfds.Now_time0_add_min(0);
		Xowd_page_tbl tbl_page = wiki.Db_mgr_as_sql().Core_data_mgr().Tbl__page();
		tbl_page.Insert_bgn();
		for (int i = 0; i < len; i++) {
			String ttl = ttls[i];
			int page_id = page_id_next.Val();
			tbl_page.Insert_cmd_by_batch(page_id, ns_id, Bry_.new_u8(ttl), false, modified_on, 0, page_id, 0, 0);
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
	public void Test_category_v1(String ctg_name_str, int... expd) {
		Xowe_wiki wiki = bldr_fxt.Wiki();
		byte[] ctg_name_bry = Bry_.new_a7(ctg_name_str);
		Xoctg_view_ctg ctg = new Xoctg_view_ctg();
		wiki.Db_mgr_as_sql().Load_mgr().Load_ctg_v1(ctg, ctg_name_bry);
		Tfds.Eq_ary(expd, Xto_int_ary(ctg));
	}
	int[] Xto_int_ary(Xoctg_view_ctg ctg) {
		List_adp list = List_adp_.New();
		byte tid_max = Xoa_ctg_mgr.Tid__max;
		for (byte tid = 0; tid < tid_max; tid++) {
			Xoctg_view_grp grp = ctg.Grp_by_tid(tid); if (grp == null) continue;
			int len = grp.Itms_list().Count();
			for (int i = 0; i < len; i++) {
				Xoctg_view_itm itm = (Xoctg_view_itm)grp.Itms_list().Get_at(i);
				list.Add(itm.Page_id());
			}
		}
		return (int[])list.To_ary_and_clear(int.class);
	}
	public void Test_category_v2(String ctg_name_str, int... expd) {
		Xowe_wiki wiki = bldr_fxt.Wiki();
		byte[] ctg_name_bry = Bry_.new_a7(ctg_name_str);
		Xoctg_data_ctg ctg = new Xoctg_data_ctg(ctg_name_bry);
		wiki.Db_mgr_as_sql().Load_mgr().Load_ctg_v2(ctg, ctg_name_bry);
		Tfds.Eq_ary(expd, Xto_int_ary(ctg));
	}
	public void Test_file(String url, String expd) {
		String actl = Io_mgr.Instance.LoadFilStr(url);
		Tfds.Eq_str_lines(expd, actl);
	}
	int[] Xto_int_ary(Xoctg_data_ctg ctg) {
		List_adp list = List_adp_.New();
		byte tid_max = Xoa_ctg_mgr.Tid__max;
		for (byte tid = 0; tid < tid_max; tid++) {
			Xoctg_idx_mgr grp = ctg.Grp_by_tid(tid); if (grp == null) continue;
			int len = grp.Itms_len();
			for (int i = 0; i < len; i++) {
				Xoctg_idx_itm itm = grp.Itms_get_at(i);
				list.Add(itm.Id());
			}
		}
		return (int[])list.To_ary_and_clear(int.class);
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
