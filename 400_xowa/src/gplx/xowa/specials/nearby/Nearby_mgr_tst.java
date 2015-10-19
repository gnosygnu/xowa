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
			tmp_bfr = Bry_bfr.new_();
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
