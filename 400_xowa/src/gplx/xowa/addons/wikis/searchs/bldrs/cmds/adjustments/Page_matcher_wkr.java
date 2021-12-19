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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.logs.Gfo_log_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.*;
import gplx.core.lists.hashs.*;
import gplx.dbs.*;
class Page_matcher_wkr implements Gfo_invk {// NOTE: tries would use less memory, but would be slower, especially for Has*()
	private final Xow_wiki wiki;
	private final List_adp rule_list = List_adp_.New();
	private final Hash_adp__int page_hash = new Hash_adp__int();
	public Page_matcher_wkr(Xow_wiki wiki, int ns_id) {
		this.wiki = wiki;
		this.ns_id = ns_id;
	}
	public int Ns_id() {return ns_id;} private final int ns_id;
	public Page_matcher_itm Get_by_or_null(int page_id) {return (Page_matcher_itm)page_hash.Get_by_or_null(page_id);}
	public Page_matcher_wkr Load_all() {
		int len = rule_list.Len();
		for (int i = 0; i < len; ++i) {
			Load((Page_matcher_itm)rule_list.GetAt(i));
		}
		return this;
	}
	private void Load(Page_matcher_itm itm) {
		Gfo_log_.Instance.Prog("loading filter", "filter", itm.Page_filter);
		List_adp page_ids = List_adp_.New();
		String sql = "", filter_arg = itm.Page_filter;
		switch (itm.Match_type) {
			case Page_matcher__match_type.Type__bgn: sql = "SELECT page_id FROM page WHERE page_namespace = ? AND page_title LIKE ?"; filter_arg = filter_arg + "%"; break;	// ANSI.Y
			case Page_matcher__match_type.Type__end: sql = "SELECT page_id FROM page WHERE page_namespace = ? AND page_title LIKE ?"; filter_arg = "%" + filter_arg; break;	// ANSI.Y
			case Page_matcher__match_type.Type__all: sql = "SELECT page_id FROM page WHERE page_namespace = ? AND page_title = ?"; break;		// ANSI.Y
		}			
		Db_stmt stmt = wiki.Data__core_mgr().Db__core().Tbl__page().Conn().Stmt_sql(sql);
		Db_rdr rdr = stmt.Clear().Crt_int("page_namespace", ns_id).Crt_str("page_title", filter_arg).Exec_select__rls_manual();
		try {
			while (rdr.Move_next()) {
				IntRef page_id = IntRef.New(rdr.Read_int("page_id"));
				page_ids.Add(page_id);
				page_hash.Add_if_dupe_use_nth(page_id, itm);
			}
		} finally {rdr.Rls();}
		itm.Page_ids = (IntRef[])page_ids.ToAryAndClear(IntRef.class);
		stmt.Rls();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__add))		Add_by_msg(m);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__add = "add";
	private void Add_by_msg(GfoMsg m) {
		byte match_type		= Page_matcher__match_type.To_tid(m.Args_getAt(0).ValToStrOrEmpty());
		byte calc_type		= Page_matcher__calc_type.To_tid(m.Args_getAt(1).ValToStrOrEmpty());
		double val			= DoubleUtl.Parse(m.Args_getAt(2).ValToStrOrEmpty());
		int args_len = m.Args_count();
		for (int i = 3; i < args_len; ++i) {
			rule_list.Add(new Page_matcher_itm(match_type, calc_type, val, m.Args_getAt(i).ValToStrOrEmpty()));
		}
	}
}
