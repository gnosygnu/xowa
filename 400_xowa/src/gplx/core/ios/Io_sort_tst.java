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
package gplx.core.ios;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import org.junit.*;
public class Io_sort_tst {
	Io_sort_fxt fxt = new Io_sort_fxt();
	@Test public void ExternalSort() {
		fxt.Clear().Memory_max_(12).Src_(fxt.GenRandom(6, 4)).Sorted_(fxt.GenOrdered(6, 4)).tst();
		fxt.Clear().Memory_max_(64).Src_(fxt.GenRandom(50, 4)).Sorted_(fxt.GenOrdered(50, 4)).tst();
	}
}
class Io_sort_fxt {
	Io_sort externalSort = new Io_sort().Memory_max_(IoConsts.LenKB);
	String_bldr sb = String_bldr_.new_();
	public Io_sort_fxt Clear() {Io_mgr.Instance.InitEngine_mem(); return this;}
	public Io_sort_fxt Memory_max_(int v) {externalSort.Memory_max_(v); return this;}
	public Io_sort_fxt Src_(String v) {src = v; return this;} private String src;
	public Io_sort_fxt Sorted_(String v) {sorted = v; return this;} private String sorted;
	public void tst() {
		Io_url src_url = Io_url_.mem_fil_("mem/src.txt");
		Io_url trg_url = Io_url_.mem_fil_("mem/trg.txt");
		Io_mgr.Instance.DeleteFil(src_url); Io_mgr.Instance.DeleteFil(trg_url);
		
		Io_mgr.Instance.SaveFilStr(src_url, src);

		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Test();
		Io_url_gen src_fil_gen = Io_url_gen_.fil_(src_url);
		Io_url[] tmp_url_ary = externalSort.Split(usr_dlg, src_fil_gen, Io_url_gen_.dir_(src_url.OwnerDir()), Io_line_rdr_key_gen_.first_pipe);
		Io_sort_fil_basic cmd = new Io_sort_fil_basic(usr_dlg, Io_url_gen_.fil_(trg_url), IoConsts.LenKB);
		externalSort.Merge(usr_dlg, tmp_url_ary, Io_sort_split_itm_sorter.Instance, Io_line_rdr_key_gen_.first_pipe, cmd);

		String actl = Io_mgr.Instance.LoadFilStr(trg_url);
		GfoTstr.EqLines(StringUtl.SplitLinesNl(sorted), StringUtl.SplitLinesNl(actl));
	}
	public String GenRandom(int rows, int pad) {
		List_adp list = List_adp_.New();
		for (int i = 0; i < rows; i++)
			list.Add(IntUtl.ToStrPadBgnZero(i, pad) + "|");
		list.Shuffle();
		for (int i = 0; i < rows; i++) {
			String itm = (String)list.GetAt(i);
			sb.Add(itm).AddCharNl();
		}		
		return sb.ToStrAndClear();
	}
	public String GenOrdered(int rows, int pad) {
		for (int i = 0; i < rows; i++)
			sb.Add(IntUtl.ToStrPadBgnZero(i, pad) + "|" + "\n");
		return sb.ToStrAndClear();
	}	
}
