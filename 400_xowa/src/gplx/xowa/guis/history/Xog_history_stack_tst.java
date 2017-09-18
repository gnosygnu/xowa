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
package gplx.xowa.guis.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
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
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
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
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		Xoae_page page = Xoae_page.New_test(wiki, ttl);
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
