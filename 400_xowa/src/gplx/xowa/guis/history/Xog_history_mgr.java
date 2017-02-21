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
public class Xog_history_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New_bry(); private final    Xog_history_stack stack = new Xog_history_stack();
	public int Count() {return hash.Count();}
	public Xoae_page Cur_page(Xowe_wiki wiki) {return Get_or_fetch(wiki, stack.Cur_itm());}
	public Xoae_page Go_bwd(Xowe_wiki wiki) {return Go_by_dir(wiki, Bool_.N);}
	public Xoae_page Go_fwd(Xowe_wiki wiki) {return Go_by_dir(wiki, Bool_.Y);}
	public Xoae_page Go_by_dir(Xowe_wiki wiki, boolean fwd) {
		Xog_history_itm itm = fwd ? stack.Go_fwd() : stack.Go_bwd();
		if (itm == Xog_history_itm.Null) return Xoae_page.Empty;
		Xoae_page rv = Get_or_fetch(wiki, itm);
		byte[] anch_key = itm.Anch();
		rv.Url().Anch_bry_(anch_key); // must override anchor as it may be different for cached page
		rv.Html_data().Bmk_pos_(itm.Bmk_pos());
		return rv;
	}
	public void Add(Xoae_page page) {
		Xog_history_itm new_itm = Xog_history_mgr.new_(page);
		stack.Add(new_itm);
		byte[] page_key = Build_page_key(page);
		if (!hash.Has(page_key))
			hash.Add(page_key, page);
	}
	public void Update_html_doc_pos(Xoae_page page, byte history_nav_type) {
		Xog_history_itm itm = Get_recent(page, history_nav_type);
		if (itm != null) itm.Bmk_pos_(page.Html_data().Bmk_pos());
	}
	private Xog_history_itm Get_recent(Xoae_page page, byte history_nav_type) {
		int pos = -1;
		int list_pos = stack.Cur_pos();
		switch (history_nav_type) {
			case Xog_history_stack.Nav_fwd:			pos = list_pos - 1; break;
			case Xog_history_stack.Nav_bwd:			pos = list_pos + 1; break;
			case Xog_history_stack.Nav_by_anchor:	pos = list_pos; break;
		}
		if (pos < 0 || pos >= stack.Len()) return null;
		Xog_history_itm recent = stack.Get_at(pos);
		Xog_history_itm page_itm = Xog_history_mgr.new_(page);
		return page_itm.Eq_wo_bmk_pos(recent) ? recent : null;	// check that recent page actually matches current; DATE:2014-05-10
	}
	private Xoae_page Get_or_fetch(Xowe_wiki wiki, Xog_history_itm itm) {
		byte[] page_key = Build_page_key(itm.Wiki(), itm.Page(), itm.Qarg());
		Xoae_page rv = (Xoae_page)hash.Get_by(page_key);
		if (rv != null) return rv;
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, itm.Page());
		return wiki.Data_mgr().Load_page_by_ttl(ttl);
	}
	private static byte[] Build_page_key(Xoae_page page) {return Build_page_key(page.Wiki().Domain_bry(), page.Ttl().Full_url(), page.Url().Qargs_mgr().To_bry());}
	private static byte[] Build_page_key(byte[] wiki_key, byte[] page_key, byte[] args_key) {return Bry_.Add_w_dlm(Byte_ascii.Pipe, wiki_key, page_key, args_key);}
	public static Xog_history_itm new_(Xoae_page pg) {
		byte[] wiki = pg.Wiki().Domain_bry();
		byte[] page = pg.Ttl().Full_url();		// get page_name only (no anchor; no query args)
		byte[] anch = pg.Url().Anch_bry();
		byte[] qarg = pg.Url().Qargs_mgr().To_bry();
		boolean redirect_force = pg.Url().Qargs_mgr().Match(Xoa_url_.Qarg__redirect, Xoa_url_.Qarg__redirect__no);
		String bmk_pos = pg.Html_data().Bmk_pos();
		if (bmk_pos == null) bmk_pos = Xog_history_itm.Html_doc_pos_toc;	// never allow null doc_pos; set to top
		return new Xog_history_itm(wiki, page, anch, qarg, redirect_force, bmk_pos);
	}
}
