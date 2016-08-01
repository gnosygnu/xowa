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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.core.primitives.*;
public class Xoctg_idx_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoctg_idx_mgr_fxt fxt = new Xoctg_idx_mgr_fxt();
	@Test   public void Index() {
		fxt .Init_block_len(Itm_len * 2);
		fxt .Test_index(fxt.Make_src(5), "A02", "A04");
	}
	@Test   public void Binary_search_small() {
		fxt .Init_itms(Itm_len * 2, fxt.Make_src(5));
		fxt .Test_itms_binary_search("A02", "A02", CompareAble_.Same);	// exact match
		fxt .Test_itms_binary_search("A03", "A02", CompareAble_.More);	// >
		fxt .Test_itms_binary_search("A01", "A02", CompareAble_.Less);	// <
	}
	@Test   public void Binary_search_large() {
		fxt .Init_itms(Itm_len * 5, fxt.Make_src(99));
		fxt .Test_itms_binary_search("A50"	, "A50", CompareAble_.Same);
		fxt .Test_itms_binary_search("A49"	, "A50", CompareAble_.Less);
		fxt .Test_itms_binary_search("A51"	, "A50", CompareAble_.More);
		fxt .Test_itms_binary_search("A44"	, "A45", CompareAble_.Less);
		fxt .Test_itms_binary_search("A56"	, "A55", CompareAble_.More);
		fxt .Test_itms_binary_search("A00"	, "A05", CompareAble_.Less);
		fxt .Test_itms_binary_search("A99"	, "A98", CompareAble_.More);
	}
	@Test   public void Find_itm_near_bmk() {
		fxt .Init_src(fxt.Make_src(7));
		fxt .Test_find_itm_near_bmk(3, "A05A", CompareAble_.More, Bool_.Y, "A06");
		fxt .Test_find_itm_near_bmk(3, "A02A", CompareAble_.Less, Bool_.Y, "A03");
		fxt .Test_find_itm_near_bmk(3, "A05A", CompareAble_.More, Bool_.N, "A05");
		fxt .Test_find_itm_near_bmk(3, "A02A", CompareAble_.Less, Bool_.N, "A02");
		fxt .Test_find_itm_near_bmk(3, "A"   , CompareAble_.Less, Bool_.Y, "A00");
		fxt .Test_find_itm_near_bmk(3, "A"   , CompareAble_.Less, Bool_.N, null);
		fxt .Test_find_itm_near_bmk(3, "A99A", CompareAble_.More, Bool_.Y, null);
		fxt .Test_find_itm_near_bmk(3, "A05" , CompareAble_.More, Bool_.N, "A05");	// PURPOSE: if exact item found, return it
	}
	@Test   public void Find() {
		fxt .Init_src(fxt.Make_src(99)).Init_block_len(100); // 100 bytes is roughly 7 items
		fxt .Test_find("A50" , Bool_.Y, String_.Ary("A50", "A51", "A52"), "A53");
		fxt .Test_find("A50" , Bool_.N, String_.Ary("A47", "A48", "A49"), "A50");	// check that "until" returns 49 (should not include 50)
		fxt .Test_find("A50A", Bool_.Y, String_.Ary("A51", "A52", "A53"), "A54");
		fxt .Test_find("A50A", Bool_.N, String_.Ary("A48", "A49", "A50"), "A51");	// check that "until" returns 50
		fxt .Test_find("A99A", Bool_.N, String_.Ary("A96", "A97", "A98"), null);
		fxt .Test_find("A99A", Bool_.Y, String_.Ary_empty, null);
		fxt .Test_find("A"	 , Bool_.Y, String_.Ary("A00", "A01", "A02"), "A03");
		fxt .Test_find("A"	 , Bool_.N, String_.Ary_empty, null);
		fxt .Test_find("A49A", Bool_.N, String_.Ary("A47", "A48", "A49"), "A50");	// check that "until" returns 49
		fxt .Test_find("A00" , Bool_.Y, String_.Ary("A00", "A01", "A02"), "A03");	// PURPOSE: special code for 1st item (which doesn't have a preceding pipe)
		fxt .Test_find("A98" , Bool_.Y, String_.Ary("A98"), null);					// PURPOSE: handle premature end
	}
	public static final int Itm_len = 16;	// 6(id|) + 6(timestamp|) + 4(A01|)
}
class Xoctg_idx_mgr_fxt {
	public Xoctg_idx_mgr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			idx_mgr = new Xoctg_idx_mgr();
		}
		return this;
	}	private Xoae_app app; Xowe_wiki wiki; Xoctg_idx_mgr idx_mgr; static final    byte[] Ctg_name = Bry_.new_a7("Ctg_test");
	public byte[] Make_src(int len) {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_byte_pipe(); // always have leading pipe
		for (int i = 0; i < len; i++) {
			bfr.Add_base85_len_5(i).Add_byte(Byte_ascii.Semic);	// idx.Id() = i
			bfr.Add_base85_len_5(0).Add_byte(Byte_ascii.Semic);	// idx.Timestamp() = ignore;
			bfr.Add_byte(Byte_ascii.Ltr_A);
			if (i < 10) bfr.Add_byte(Byte_ascii.Num_0);
			bfr.Add_int_variable(i).Add_byte_pipe();
		}
		return bfr.To_bry_and_clear();
	}
	public Xoctg_idx_mgr_fxt Init_itms(int block_len, byte[] src) {idx_mgr.Block_len_(block_len); idx_mgr.Index(Gfo_usr_dlg_.Test(), Ctg_name, src); return this;}
	public Xoctg_idx_mgr_fxt Init_block_len(int block_len) {idx_mgr.Block_len_(block_len); return this;}
	public Xoctg_idx_mgr_fxt Init_src(byte[] v) {src = v; src_len = v.length; return this;} private byte[] src; int src_len;
	public Xoctg_idx_mgr_fxt Test_index(byte[] src, String... expd) {
		idx_mgr.Index(Gfo_usr_dlg_.Test(), Ctg_name, src);
		Tfds.Eq_ary_str(expd, Idx_mgr_itms(idx_mgr));
		return this;
	}
	String[] Idx_mgr_itms(Xoctg_idx_mgr idx_mgr) {
		int len = idx_mgr.Itms_len();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++)
			rv[i] = String_.new_a7(idx_mgr.Itms_get_at(i).Sortkey());
		return rv;
	}
	public Xoctg_idx_mgr_fxt Test_itms_binary_search(String find, String expd, int expd_comp) {
		comp_rslt.Val_zero_();
		Xoctg_idx_itm rslt = idx_mgr.Itms_binary_search(Bry_.new_a7(find), comp_rslt);
		Tfds.Eq(expd, String_.new_a7(rslt.Sortkey()));
		Tfds.Eq(expd_comp, comp_rslt.Val());
		return this;		
	}	Int_obj_ref comp_rslt = Int_obj_ref.New_zero();
	public Xoctg_idx_mgr_fxt Test_find_itm_near_bmk(int idx_bgn, String find, int bmk_comp, boolean find_dir, String expd) {
		int idx_pos = (idx_bgn * Xoctg_idx_mgr_tst.Itm_len) + (bmk_comp == CompareAble_.Less ? 1 : 0);
		Xoctg_idx_itm rslt = idx_mgr.Find_itm_near_bmk(src, src_len, Bry_.new_a7(find), find_dir, bmk_comp, idx_pos);
		String actl = rslt == null ? null : String_.new_a7(rslt.Sortkey());
		Tfds.Eq(expd, actl);
		return this;		
	}
	public Xoctg_idx_mgr_fxt Test_find(String find, boolean fill_at_bgn, String[] expd_ary, String last_plus_one) {
		if (tmp_list == null) tmp_list = List_adp_.New();
		idx_mgr.Index(Gfo_usr_dlg_.Test(), Bry_.Empty, src);
		tmp_list.Clear();
		idx_mgr.Find(tmp_list, src, fill_at_bgn, Bry_.new_a7(find), 3, tmp_last_plus_one);
		Tfds.Eq_ary(expd_ary, To_str_ary(tmp_list));
		Tfds.Eq(last_plus_one, String_.new_a7(tmp_last_plus_one.Sort_key()));
		return this;
	}	List_adp tmp_list; Xoctg_view_itm tmp_last_plus_one = new Xoctg_view_itm();
	String[] To_str_ary(List_adp list) {
		int len = list.Count();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			rv[i] = String_.new_a7(((Xoctg_view_itm)list.Get_at(i)).Sort_key());
		}
		return rv;
	}
}
