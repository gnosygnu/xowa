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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.xowa.bldrs.*; import gplx.xowa.wikis.ctgs.*; import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.nss.*;
public class Xodb_load_mgr_sql_tst {
	@Before public void init() {if (Xoa_test_.Db_skip()) return; fxt.Clear();} private Xodb_load_mgr_sql_fxt fxt = new Xodb_load_mgr_sql_fxt();
	@After public void term() {if (Xoa_test_.Db_skip()) return; fxt.Rls();}
	@Test  public void Load_ctg_ttls() {
		if (Xoa_test_.Db_skip()) return;
		Xowd_page_itm[] ctgs = fxt.pages_
		(	fxt.ctg_(1, "Ctg_1", Bool_.Y, 10, 11, 12)
		,	fxt.ctg_(2, "Ctg_2", Bool_.N, 20, 21, 22)
		,	fxt.ctg_(3, "Ctg_3", Bool_.Y, 30, 31, 32)
		);
		fxt.Init_save_ctgs(ctgs);
		fxt.Test_load_ctg_list(ctgs);	
	}
}
class Xoctg_url_mok extends Xoctg_url {	public Xoctg_url_mok Page_bgn_(String v) {return Grp(Xoa_ctg_mgr.Tid_page, Bool_.Y, v);}
	public Xoctg_url_mok Page_end_(String v) {return Grp(Xoa_ctg_mgr.Tid_page, Bool_.N, v);}
	Xoctg_url_mok Grp(byte tid, boolean v, String bmk) {
		this.Grp_fwds()[tid] = v ? Bool_.Y_byte : Bool_.N_byte;
		this.Grp_idxs()[tid] = Bry_.new_a7(bmk);
		return this;
	}	
}
class Xodb_load_mgr_sql_fxt {
	Db_mgr_fxt fxt; Int_obj_ref next_id = Int_obj_ref.New(1); Xoae_app app; Xowe_wiki wiki;
	public void Clear() {
		if (fxt == null) {
			fxt = new Db_mgr_fxt();
			fxt.Ctor_fsys();
			fxt.Init_db_sqlite();
			wiki = fxt.Wiki();
			app = wiki.Appe();
		}
	}
	public void Rls() {fxt.Rls();}
	public Xowd_page_itm[] pages_(Xowd_page_itm... ary) {return ary;}
	public Xowd_page_itm ctg_(int id, String ttl, boolean hidden, int count_subcs, int count_files, int count_pages) {
		Xowd_page_itm rv = new Xowd_page_itm().Ns_id_(Xow_ns_.Tid__category).Id_(id).Ttl_page_db_(Bry_.new_a7(ttl));
		Xowd_category_itm ctg = Xowd_category_itm.load_(id, 0, hidden, count_subcs, count_files, count_pages);
		rv.Xtn_(ctg);
		return rv;
	}
	public void Init_save_ctgs(Xowd_page_itm[] ary) {
		int len = ary.length;
		Xodb_mgr_sql db_mgr = wiki.Db_mgr_as_sql();
		Xowd_cat_core_tbl cat_core_tbl = db_mgr.Core_data_mgr().Db__cat_core().Tbl__cat_core().Create_tbl();
		DateAdp modified = Datetime_now.Get();
		Xowd_page_tbl tbl_page = wiki.Db_mgr_as_sql().Core_data_mgr().Tbl__page();
		tbl_page.Insert_bgn();
		cat_core_tbl.Insert_bgn();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = ary[i];
			tbl_page.Insert_cmd_by_batch(page.Id(), page.Ns_id(), page.Ttl_page_db(), false, modified, 10, page.Id(), 0, 0);
			Xowd_category_itm ctg_itm = (Xowd_category_itm)page.Xtn(); 
			cat_core_tbl.Insert_cmd_by_batch(ctg_itm.Id(), ctg_itm.Count_pages(), ctg_itm.Count_subcs(), ctg_itm.Count_files(), Bool_.To_byte(ctg_itm.Hidden()), 0);
		}
		cat_core_tbl.Insert_end();
		tbl_page.Insert_end();
	}
	public void Test_load_ctg_list(Xowd_page_itm[] ary) {
		int len = ary.length;
		byte[][] ttls = new byte[len][];
		for (int i = 0; i < len; i++) {
			ttls[i] = ary[i].Ttl_page_db();
		}
		Xowd_page_itm[] actl = wiki.Db_mgr_as_sql().Load_mgr().Load_ctg_list(ttls);
		Tfds.Eq_str_lines(Xto_str(ary), Xto_str(actl));
	}
	private static String Xto_str(Xowd_page_itm[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = ary[i];
			Xowd_category_itm ctg_itm = (Xowd_category_itm)page.Xtn();
			bfr.Add_int_variable(page.Id()).Add_byte_pipe();
			bfr.Add(page.Ttl_page_db()).Add_byte_pipe();
			bfr.Add_byte(Bool_.To_byte(ctg_itm.Hidden())).Add_byte_nl();
		}
		return bfr.To_str_and_clear();
	}
	public Xoctg_url_mok ctg_url_() {return new Xoctg_url_mok();}
	public Xodb_load_mgr_sql_fxt Init_limit_(int v) {limit = v; return this;} private int limit = 3;
	public void Test_select(Xoctg_url ctg_url, Xoctg_mok_ctg expd) {
		Xoctg_view_ctg view_ctg = new Xoctg_view_ctg();
		wiki.Db_mgr_as_sql().Load_mgr().Load_ctg_v2a(view_ctg, ctg_url, expd.Ttl(), limit, false);
		for (byte i = 0; i < Xoa_ctg_mgr.Tid__max; i++) {
			Xoctg_view_grp view_grp = view_ctg.Grp_by_tid(i);
			Xoctg_mok_grp mok_grp = expd.Grps_get_or_new(i);
			Tfds.Eq_ary_str(Xto_str(mok_grp), Xto_str(view_grp));
			Tfds.Eq(String_.new_a7(mok_grp.Last_plus_one_sortkey()), String_.new_a7(view_grp.Itms_last_sortkey()));
		}
	}
	String[] Xto_str(Xoctg_view_grp grp) {
		Xoctg_view_itm[] ary = grp.Itms();
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i< len; i++) {
			Xoctg_view_itm itm = ary[i];
			rv[i] = itm.Ttl().Page_db_as_str();
		}
		return rv;
	}
	String[] Xto_str(Xoctg_mok_grp grp) {
		List_adp list = grp.Itms();
		int len = list.Count();
		String[] rv = new String[len];
		for (int i = 0; i< len; i++) {
			Xowd_page_itm itm = (Xowd_page_itm)list.Get_at(i);
			rv[i] = String_.new_a7(itm.Ttl_page_db());
		}
		return rv;
	}
	public Xoctg_mok_ctg ctg_() {
		Xoctg_mok_ctg rv = new Xoctg_mok_ctg(next_id);
		return rv;
	}
}
class Xoctg_mok_grp {
	public byte Tid() {return tid;} public Xoctg_mok_grp Tid_(byte v) {this.tid = v; return this;} private byte tid;
	public byte[] Last_plus_one_sortkey() {return last_plus_one_sortkey;} public Xoctg_mok_grp Last_plus_one_sortkey_(byte[] v) {this.last_plus_one_sortkey = v; return this;} private byte[] last_plus_one_sortkey;
	public List_adp Itms() {return itms;} List_adp itms = List_adp_.New();
}
class Xoctg_mok_ctg {
	public Xoctg_mok_ctg(Int_obj_ref next_id) {this.next_id = next_id;} Int_obj_ref next_id;
	public byte[] Ttl() {return ttl;}
	public Xoctg_mok_ctg Ttl_(String v) {return Ttl_(Bry_.new_a7(v));}
	public Xoctg_mok_ctg Ttl_(byte[] v) {this.ttl = v; return this;} private byte[] ttl;
	public Xoctg_mok_grp[] Grps() {return grps;} private Xoctg_mok_grp[] grps = new Xoctg_mok_grp[3];
	public Xoctg_mok_grp Grps_get_or_new(byte tid) {
		Xoctg_mok_grp rv = grps[tid];
		if (rv == null) {
			rv = new Xoctg_mok_grp().Tid_(tid);
			grps[tid] = rv;
		}
		return rv;
	}
	public Xoctg_mok_ctg Grp_pages_(int count) {return Grp_pages_(0, count, null);}
	public Xoctg_mok_ctg Grp_pages_(int bgn, int end, String last_itm_plus_one_sortkey) {
		Xoctg_mok_grp grp = Grps_get_or_new(Xoa_ctg_mgr.Tid_page);
		byte[] ttl_prefix = Bry_.new_a7("Page_");
		int ns_id = Xow_ns_.Tid__main;
		byte ctg_tid = Xoa_ctg_mgr.Tid_page;
		for (int i = bgn; i < end; i++) {
			byte[] ttl = Bry_.Add(ttl_prefix, Bry_.new_a7(Int_.To_str_pad_bgn_zero(i, 3)));
			Xoctg_page_xtn db_ctg = new Xoctg_page_xtn(ctg_tid, ttl);
			Xowd_page_itm page = new Xowd_page_itm();
			int page_id = next_id.Val_add_post();
			page.Id_(page_id).Ns_id_(ns_id).Ttl_page_db_(ttl).Xtn_(db_ctg);
			grp.Itms().Add(page);
		}
		grp.Last_plus_one_sortkey_(Bry_.new_a7(last_itm_plus_one_sortkey));
		return this;
	}
}
