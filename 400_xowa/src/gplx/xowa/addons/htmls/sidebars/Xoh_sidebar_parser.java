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
package gplx.xowa.addons.htmls.sidebars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.core.btries.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.parsers.lnkis.*;
class Xoh_sidebar_parser {	// TS:
	public static void Parse(Bry_bfr tmp_bfr, Xowe_wiki wiki, List_adp grps, byte[] src) {
		// split MediaWiki:Sidebar into lines
		byte[][] lines = Bry_split_.Split(src, Byte_ascii.Nl);

		// init
		Xop_link_parser link_parser = new Xop_link_parser();
		Xoa_url tmp_url = Xoa_url.blank();
		Xoh_sidebar_itm cur_grp = null;

		// loop lines
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i];
			int line_len = line.length;
			if	(line_len == 0) continue;					// skip blank lines
			if	(line[0] != Byte_ascii.Star) continue;		// skip non-list items; note that all items must begin with "*"

			// if **, then itm; else * is grp
			boolean tid_is_itm = line[1] == Byte_ascii.Star;

			// trim ws; note that tid indicates # of asterisks; EX: '** a' ->  'a'
			byte[] raw = Bry_.Trim(line, tid_is_itm ? 2 : 1, line_len);

			// strip comments; DATE:2014-03-08
			raw = gplx.langs.htmls.Gfh_utl.Del_comments(tmp_bfr, raw);

			Xoh_sidebar_itm cur_itm = null;
			// parse itm
			if (tid_is_itm) {
				cur_itm = Parse_itm_or_null(wiki, raw, link_parser, tmp_url, tmp_bfr); if (cur_itm == null) continue;
				if (cur_grp == null)	// handle null_ref; should only occur for tests
					grps.Add(cur_itm);
				else
					cur_grp.Subs__add(cur_itm);
			}
			// parse grp
			else {
				cur_itm = Parse_grp_or_null(wiki, raw); if (cur_itm == null) continue;
				cur_grp = cur_itm;
				grps.Add(cur_grp);
			}
			wiki.Msg_mgr().Val_html_accesskey_and_title(cur_itm.Id(), tmp_bfr, cur_itm);
		}
	}
	private static Xoh_sidebar_itm Parse_grp_or_null(Xowe_wiki wiki, byte[] raw) {
		// ignore SEARCH, TOOLBOX, LANGUAGES
		if (ignore_trie.Match_bgn(raw, 0, raw.length) != null) return null;

		byte[] text_key = raw;
		byte[] text_val = Resolve_key(wiki, text_key);
		return new Xoh_sidebar_itm(Bool_.N, text_key, text_val, null);
	}
	private static Xoh_sidebar_itm Parse_itm_or_null(Xowe_wiki wiki, byte[] raw, Xop_link_parser link_parser, Xoa_url tmp_url, Bry_bfr bfr) {
		// separate into key|val; note that grp uses entire raw for key while itm uses raw after "|"
		int pipe_pos = Bry_find_.Find_fwd(raw, Byte_ascii.Pipe);
		
		// if no pipe, warn and return; EX: should be "href|main", but only "href"
		if (pipe_pos == Bry_find_.Not_found) {
			// don't bother warning if es.wikisource.org and special:Random/Pagina; occurs in 2014-02-03 dump and still present as of 2016-09; note this sidebar item does not show on WMF either
			if	(Bry_.Eq(wiki.Domain_bry(), Ignore_wiki_ess) && Bry_.Eq(raw, Ignore_item_ess_random)) {}
			else
				wiki.Appe().Usr_dlg().Warn_many("", "", "sidebar item is missing pipe; only href is available; item will be hidden: item=~{0}", String_.new_u8(raw));			
			return null;
		}

		// get text
		byte[] text_key = Bry_.Mid(raw, pipe_pos + 1, raw.length);
		byte[] text_val = Resolve_key(wiki, text_key);

		// get href
		byte[] href_key = Bry_.Mid(raw, 0, pipe_pos);
		byte[] href_val = Resolve_key(wiki, href_key);
		href_val = link_parser.Parse(bfr, tmp_url, wiki, href_val, Bry_.Empty);

		return new Xoh_sidebar_itm(Bool_.Y, text_key, text_val, href_val);
	}
	private static byte[] Resolve_key(Xowe_wiki wiki, byte[] key) {
		byte[] val = wiki.Msg_mgr().Val_by_key_obj(key);
		if (Bry_.Len_eq_0(val)) val = key;	// if key is not found, default to val
		return wiki.Parser_mgr().Main().Expand_tmpl(val);
	}

	private static byte[] Ignore_wiki_ess = Bry_.new_a7("es.wikisource.org"), Ignore_item_ess_random = Bry_.new_u8("special:Random/Página djvu");
	private static final byte Ignore__search = 1, Ignore__toolbox = 2, Ignore__toolbox_end = 3, Ignore__languages = 4;
	private static final    Btrie_slim_mgr ignore_trie = Btrie_slim_mgr.ci_a7()
	.Add_str_byte("SEARCH"		, Ignore__search)
	.Add_str_byte("TOOLBOX"		, Ignore__toolbox)
	.Add_str_byte("TOOLBOXEND"	, Ignore__toolbox_end)
	.Add_str_byte("LANGUAGES"	, Ignore__languages)
	;
}
