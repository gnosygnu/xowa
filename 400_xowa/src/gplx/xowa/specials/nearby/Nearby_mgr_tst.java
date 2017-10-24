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
package gplx.xowa.specials.nearby; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import org.junit.*;
public class Nearby_mgr_tst {
	@Before public void init() {fxt.Clear();} Nearby_mgr_fxt fxt = new Nearby_mgr_fxt(); 
	@Test  public void Basic() {
		fxt.Init_page("A", "[[B]]");
		fxt.Test_find("A", "B", "A|B");
	}
	@Test  public void Shortest() {
		fxt.Init_page("A", "[[B]]");
		fxt.Init_page("B", "[[C]] [[D]]");
		fxt.Init_page("C", "[[D]]");
		fxt.Test_find("A", "D", "A|B|D");
		fxt.Init_results_max(2).Test_find("A", "D", "A|B|D", "A|B|C|D");
	}
	@Test  public void Circular() {
		fxt.Init_page("A", "[[B]]");
		fxt.Init_page("B", "[[C]]");
		fxt.Init_page("C", "[[A]]");
		fxt.Test_find("A", "D", "");
	}
	@Test  public void Page_doesnt_exist() {
		fxt.Init_page("A", "[[B]]");
		fxt.Test_find("A", "C", "");
	}
}
class Nearby_mgr_fxt {
	public Nearby_mgr_fxt Clear() {
		if (fxt == null) {
			fxt = new Xop_fxt();
			nearby_mgr = new Nearby_mgr();
			excluded = Hash_adp_bry.ci_a7();
			tmp_bfr = Bry_bfr_.New();
		}
		fxt.Reset();
		Io_mgr.Instance.InitEngine_mem();
		nearby_mgr.Results_max_(1);
		return this;
	} 	private Xop_fxt fxt; Nearby_mgr nearby_mgr; Hash_adp_bry excluded; Bry_bfr tmp_bfr;
	public void Init_page(String ttl, String text) {fxt.Init_page_create(ttl, text);}
	public Nearby_mgr_fxt Init_results_max(int v) {nearby_mgr.Results_max_(v); return this;}
	public void Test_find(String src, String trg, String... expd) {
		List_adp actl = nearby_mgr.Find_from_to(fxt.Wiki(), Bry_.new_a7(src), Bry_.new_a7(trg), excluded);
		Tfds.Eq_ary(String_.SplitLines_nl(Xto_str(actl)), expd);
	}
	String Xto_str(List_adp list) {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Nearby_rslt rslt = (Nearby_rslt)list.Get_at(i);
			int ttls = rslt.Len();
			if (i != 0) tmp_bfr.Add_byte_nl();
			for (int j = 0; j < ttls; j++) {
				Xoa_ttl ttl = rslt.Get_at(j);
				if (j != 0) tmp_bfr.Add_byte(Byte_ascii.Pipe);
				tmp_bfr.Add(ttl.Page_db());
			}
		}
		return tmp_bfr.To_str_and_clear();
	}
}
