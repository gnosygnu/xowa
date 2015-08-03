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
package gplx.xowa.gui.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import org.junit.*;
public class Xog_history_stack_tst {		
	@Before public void init() {fxt.Clear();} private Xog_history_stack_fxt fxt = new Xog_history_stack_fxt();
	@Test  public void Init()				{fxt.Test_cur(null);}
	@Test  public void Add_1()				{fxt.Exec_add_many("A").Test_cur("A").Test_len(1).Test_pos(0);}
	@Test  public void Add_same()			{fxt.Exec_add_many("A", "A").Test_cur("A").Test_len(1).Test_pos(0);}
	@Test  public void Add_3()				{fxt.Exec_add_many("A", "B", "C").Test_cur("C").Test_len(3).Test_pos(2);}
	@Test  public void Add_3_bwd()			{fxt.Exec_add_many("A", "B", "C").Exec_go_bwd().Test_cur("B").Test_pos(1);}
	@Test  public void Add_3_bwd_fwd()		{fxt.Exec_add_many("A", "B", "C").Exec_go_bwd().Exec_go_fwd().Test_cur("C").Test_pos(2);}
	@Test  public void Add_3_bwd_add()		{fxt.Exec_add_many("A", "B", "C").Exec_go_bwd().Exec_add_many("D").Test_len(3).Test_cur("D").Test_pos(2);}
	@Test  public void Add_3_bwd_bwd_add()	{fxt.Exec_add_many("A", "B", "C").Exec_go_bwd().Exec_go_bwd().Exec_add_many("D").Test_len(2).Test_cur("D").Test_pos(1);}
	@Test  public void Add_dif_ns()			{fxt.Exec_add_many("A", "Help:A").Test_cur("Help:A");}	// PURPOSE.fix: page_stack was only differtiating by Page_db, not Full; EX: Unicode -> Category:Unicode
	@Test  public void Add_qargs() {// PURPOSE.fix: page_stack was only differentiating by qtxt args
		fxt	.Exec_add_one("Special:AllPages", "?from=A")
			.Exec_add_one("Special:AllPages", "?from=B")
			.Exec_add_many("B")
			.Exec_go_bwd()
			.Test_cur("Special:AllPages")
			.Test_cur_qargs("?from=B")
			;
	}
}
class Xog_history_stack_fxt {
	public Xog_history_stack_fxt Clear() {
		stack.Clear();
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
		}
		return this;
	}	private Xoae_app app; private Xowe_wiki wiki; private Xog_history_stack stack = new Xog_history_stack();
	public Xog_history_stack_fxt Test_cur(String expd) {
		Xog_history_itm page = stack.Cur_itm();
		String actl = page == null ? null : String_.new_u8(page.Page());
		Tfds.Eq(expd, actl, "cur");
		return this;
	}
	public Xog_history_stack_fxt Test_cur_qargs(String expd) {
		Xog_history_itm page = stack.Cur_itm();
		String actl = page == null ? null : String_.new_u8(page.Qarg());
		Tfds.Eq(expd, actl, "cur_qargs");
		return this;
	}
	public Xog_history_stack_fxt Exec_go_bwd() {stack.Go_bwd(); return this;}
	public Xog_history_stack_fxt Exec_go_fwd() {stack.Go_fwd(); return this;}
	public Xog_history_stack_fxt Exec_add_many(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			String ttl = ary[i];
			Exec_add_one(ttl, null);
		}
		return this;
	}
	public Xog_history_stack_fxt Exec_add_one(String ttl_str, String arg_str) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ttl_bry);
		Xoae_page page = Xoae_page.test_(wiki, ttl);
		byte[] url_bry = ttl_bry;
		if (arg_str != null) url_bry = Bry_.Add(url_bry, Bry_.new_u8(arg_str));			
		Xoa_url url = app.User().Wikii().Utl__url_parser().Parse(url_bry);
		page.Url_(url);  // set url b/c history_mgr.Add uses url
		stack.Add(Xog_history_mgr.new_(page));
		return this;
	}
	public Xog_history_stack_fxt Test_pos(int expd) {Tfds.Eq(expd, stack.Cur_pos(), "pos"); return this;}
	public Xog_history_stack_fxt Test_len(int expd) {Tfds.Eq(expd, stack.Len(), "len"); return this;}
}
