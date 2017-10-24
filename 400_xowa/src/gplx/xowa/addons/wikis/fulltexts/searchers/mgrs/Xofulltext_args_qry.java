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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.caches.*;
public class Xofulltext_args_qry {
	public int qry_id;
	public String page_guid;
	public byte[] search_text;
	public boolean expand_options;
	public Xofulltext_cache_mgr cache_mgr;
	public Xofulltext_args_wiki[] wikis_ary;

	public boolean case_match;
	public boolean auto_wildcard_bgn;
	public boolean auto_wildcard_end;
	private boolean canceled;

	public byte[] Qry_key(byte[] wiki, byte[] ns_ids) {
		return Bry_.Add_w_dlm(Byte_ascii.Nl, wiki, ns_ids, search_text); // EX: "en.wikipedia.org\n0|4\nearth"
	}
	public void Cancel() {
		synchronized (this) {
			canceled = true;
		}
	}
	public boolean Canceled() {return canceled;}

	public static Xofulltext_args_qry New_by_json(Json_nde args) {
		Xofulltext_args_qry rv = new Xofulltext_args_qry();
		rv.search_text = args.Get_as_bry("search");
		rv.page_guid = args.Get_as_str("page_guid");
		rv.expand_options = args.Get_as_bool_or("expand_options", false);

		// create wikis
		byte[] wikis_bry = args.Get_as_bry("qarg_wikis");
		byte[][] wikis_ary = Bry_split_.Split(wikis_bry, Byte_ascii.Pipe, true);
		int wikis_len = wikis_ary.length;
		Xofulltext_args_wiki[] wiki_args = new Xofulltext_args_wiki[wikis_len];
		rv.wikis_ary = wiki_args;
		for (int i = 0; i < wikis_len; i++) {
			wiki_args[i] = new Xofulltext_args_wiki(wikis_ary[i]);
		}
		Set_prop(wiki_args, wikis_len, args, "ns_ids");
		Set_prop(wiki_args, wikis_len, args, "offsets");
		Set_prop(wiki_args, wikis_len, args, "limits");
		Set_prop(wiki_args, wikis_len, args, "expand_pages");
		Set_prop(wiki_args, wikis_len, args, "expand_snips");
		Set_prop(wiki_args, wikis_len, args, "show_all_snips");

		rv.case_match = args.Get_as_bool_or("case_match", false);
		rv.auto_wildcard_bgn = args.Get_as_bool_or("auto_wildcard_bgn", false);
		rv.auto_wildcard_end = args.Get_as_bool_or("auto_wildcard_end", false);
		return rv;
	}
	private static void Set_prop(Xofulltext_args_wiki[] wikis, int wikis_len, Json_nde args, String key) {
		// set ns_ids
		byte[] json_val = args.Get_as_bry("qarg_" + key);
		byte[][] ary = Bry_split_.Split(json_val, Byte_ascii.Pipe, true);
		int ary_len = ary.length;
		for (int i = 0; i < wikis_len; i++) {
			byte[] val = i < ary_len ? ary[i] : ary[ary_len - 1];
			wikis[i].Init_by_json(key, val);
		}
	}
}
