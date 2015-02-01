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
package gplx.ios; import gplx.*;
import org.junit.*; import gplx.core.strings.*;
public class Io_sort_tst {
	Io_sort_fxt fxt = new Io_sort_fxt();
	@Test  public void ExternalSort() {
		fxt.Clear().Memory_max_(12).Src_(fxt.GenRandom(6, 4)).Sorted_(fxt.GenOrdered(6, 4)).tst();
		fxt.Clear().Memory_max_(64).Src_(fxt.GenRandom(50, 4)).Sorted_(fxt.GenOrdered(50, 4)).tst();
	}
}
class Io_sort_fxt {
	Io_sort externalSort = new Io_sort().Memory_max_(Io_mgr.Len_kb);
	String_bldr sb = String_bldr_.new_();
	public Io_sort_fxt Clear() {Io_mgr._.InitEngine_mem(); return this;}
	public Io_sort_fxt Memory_max_(int v) {externalSort.Memory_max_(v); return this;}
	public Io_sort_fxt Src_(String v) {src = v; return this;} private String src;
	public Io_sort_fxt Sorted_(String v) {sorted = v; return this;} private String sorted;
	public void tst() {
		Io_url src_url = Io_url_.mem_fil_("mem/src.txt");
		Io_url trg_url = Io_url_.mem_fil_("mem/trg.txt");
		Io_mgr._.DeleteFil(src_url); Io_mgr._.DeleteFil(trg_url);
		
		Io_mgr._.SaveFilStr(src_url, src);

		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_base.test_();
		Io_url_gen src_fil_gen = Io_url_gen_.fil_(src_url);
		Io_url[] tmp_url_ary = externalSort.Split(usr_dlg, src_fil_gen, Io_url_gen_.dir_(src_url.OwnerDir()), Io_line_rdr_key_gen_.first_pipe);
		Io_sort_fil_basic cmd = new Io_sort_fil_basic(usr_dlg, Io_url_gen_.fil_(trg_url), Io_mgr.Len_kb);
		externalSort.Merge(usr_dlg, tmp_url_ary, Io_sort_split_itm_sorter._, Io_line_rdr_key_gen_.first_pipe, cmd);

		String actl = Io_mgr._.LoadFilStr(trg_url);
		Tfds.Eq_ary_str(String_.SplitLines_nl(sorted), String_.SplitLines_nl(actl));
	}
	public String GenRandom(int rows, int pad) {
		ListAdp list = ListAdp_.new_();
		for (int i = 0; i < rows; i++)
			list.Add(Int_.Xto_str_pad_bgn(i, pad) + "|");
		list.Shuffle();
		for (int i = 0; i < rows; i++) {
			String itm = (String)list.FetchAt(i);
			sb.Add(itm).Add_char_nl();
		}		
		return sb.Xto_str_and_clear();
	}
	public String GenOrdered(int rows, int pad) {
		for (int i = 0; i < rows; i++)
			sb.Add(Int_.Xto_str_pad_bgn(i, pad) + "|" + "\n");
		return sb.Xto_str_and_clear();
	}	
}
