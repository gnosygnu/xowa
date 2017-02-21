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
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.log_msgs.*;
import gplx.langs.phps.*; import gplx.xowa.langs.bldrs.*;
class Xob_subpage_parser {
	public Xob_subpage_wiki[] Parse(byte[] src) {
		src = Bry_.Add(Bry_.new_a7("$a = array("), src, Bry_.new_a7(");"));
		List_adp wikis_list = List_adp_.New();
		try {
			Php_parser php_parser = new Php_parser();
			Php_evaluator eval = new Php_evaluator(new Gfo_msg_log("test"));
			php_parser.Parse_tkns(src, eval);
			Php_line[] lines = (Php_line[])eval.List().To_ary(Php_line.class);
			Php_line_assign line = (Php_line_assign)lines[0];
			Php_itm_ary root_ary = (Php_itm_ary)line.Val();
			Php_itm_kv root_kv = (Php_itm_kv)root_ary.Subs_get(0);
			Php_itm_ary wiki_tkns = (Php_itm_ary)root_kv.Val();
			int wiki_tkns_len = wiki_tkns.Subs_len();
			for (int i = 0; i < wiki_tkns_len; i++) {
				Xob_subpage_wiki wiki_itm = new Xob_subpage_wiki();
				Php_itm_kv wiki_tkn = (Php_itm_kv)wiki_tkns.Subs_get(i);
				Parse_wiki(wiki_tkn, wiki_itm);
				wikis_list.Add(wiki_itm);
			}
		}
		catch (Exception e) {
			throw Err_.new_exc(e, "xo", "parse failed", "src", String_.new_u8(src));
		}
		return (Xob_subpage_wiki[])wikis_list.To_ary(Xob_subpage_wiki.class);
	}
	private void Parse_wiki(Php_itm_kv wiki_tkn, Xob_subpage_wiki wiki_itm) {
		wiki_itm.Name_(wiki_tkn.Key().Val_obj_bry());
		Php_itm_ary ns_ary_tkns = (Php_itm_ary)wiki_tkn.Val();
		int ns_ary_tkns_len = ns_ary_tkns.Subs_len();
		for (int i = 0; i < ns_ary_tkns_len; i++) {
			Php_itm_kv ns_tkn = (Php_itm_kv)ns_ary_tkns.Subs_get(i);
			Xob_subpage_ns ns_itm = new Xob_subpage_ns();
			ns_itm.Id_(Parse_ns_id(ns_tkn.Key()));
			ns_itm.Enabled_(Parse_ns_enabled(ns_tkn.Val()));
			wiki_itm.Ns_list().Add(ns_itm);
		}
	}
	private int Parse_ns_id(Php_itm itm) {
		switch (itm.Itm_tid()) {
			case Php_itm_.Tid_int:
				return ((Php_itm_int)itm).Val_obj_int();
			case Php_itm_.Tid_var:
				return Xol_mw_lang_parser.Id_by_mw_name(((Php_itm)itm).Val_obj_bry());
			default:
				throw Err_.new_unhandled(itm.Itm_tid());
		}
	}
	private boolean Parse_ns_enabled(Php_itm itm) {
		switch (itm.Itm_tid()) {
			case Php_itm_.Tid_int:
				return ((Php_itm_int)itm).Val_obj_int() == Bool_.Y_int;
			case Php_itm_.Tid_bool_false:
				return false;
			case Php_itm_.Tid_bool_true:
				return true;
			default:
				throw Err_.new_unhandled(itm.Itm_tid());
		}
	}
}
class Xob_subpage_ns {
	public int Id() {return id;} public Xob_subpage_ns Id_(int v) {id = v; return this;} private int id;
	public boolean Enabled() {return enabled;} public Xob_subpage_ns Enabled_(boolean v) {enabled = v; return this;} private boolean enabled;
}
class Xob_subpage_wiki {
	public byte[] Name() {return name;} public Xob_subpage_wiki Name_(byte[] v) {this.name = v; return this;} private byte[] name;
	public List_adp Ns_list() {return ns_list;} private List_adp ns_list = List_adp_.New();
}
