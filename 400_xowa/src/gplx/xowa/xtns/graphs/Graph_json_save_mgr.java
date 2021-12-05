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
package gplx.xowa.xtns.graphs; import gplx.*;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.*;
import gplx.core.tests.*;
import gplx.core.btries.*; import gplx.core.brys.*;
import gplx.xowa.apps.fsys.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkis.files.*;
import gplx.xowa.wikis.nss.*;
class Graph_json_save_mgr {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs().Add_bry_byte(Bry__xowa_root, Tid__text);
	public Graph_json_save_mgr(Xoa_fsys_mgr fsys_mgr) {
		trie.Add_bry_byte(fsys_mgr.Root_dir().To_http_file_bry(), Tid__root);
	}
	public boolean Root_dir_found() {return root_dir_found;} private boolean root_dir_found;
	public byte[] Save(Xoae_page page, Xop_ctx ctx, byte[] domain_bry, String page_ttl, byte[] src, int src_bgn, int src_end) {
		// init err_wkr
		Bry_err_wkr err_wkr = new Bry_err_wkr();
		err_wkr.Init_by_page(page_ttl, src);
		err_wkr.Init_by_sect("graph", src_bgn);

		// init other helpers
		Xowe_wiki wiki = page.Wikie();
		Bry_bfr bfr = Bry_bfr_.New();
		Btrie_rv trv = new Btrie_rv();
		Xoh_img_src_data img_src_parser = new Xoh_img_src_data();

		// init pos
		int find_bgn = src_bgn;
		int prv_bfr = src_bgn;
		while (find_bgn < src_end) {
			// match byte against trie
			byte tid = trie.Match_byte_or(trv, src, find_bgn, src_end, Byte_.Max_value_127);

			// file:/// or {XOWA_ROOT} not found; look at next byte
			if (tid == Byte_.Max_value_127) {
				find_bgn++;
				continue;
			}

			// file:/// or {XOWA_ROOT} found
			int find_end = trv.Pos();
			switch (tid) {
				// {XOWA_ROOT} found; double up;
				case Tid__text:
					bfr.Add_mid(src, prv_bfr, find_end);
					bfr.Add(Bry__xowa_root);
					prv_bfr = find_bgn = find_end;
					break;
				// root found
				case Tid__root:
					// check for '"wikirawupload:'
					int wikirawupload_bgn = find_bgn - Bry__wikirawupload.length;
					if (wikirawupload_bgn < 0) wikirawupload_bgn = 0;
					if (!Bry_.Match(src, wikirawupload_bgn, find_bgn, Bry__wikirawupload)) {
						err_wkr.Warn("Graph_json_save_mgr: missing wikirawupload");
						find_bgn++;
						continue;
					}

					// get url_end by searching for '\"'
					int url_bgn = find_bgn;
					int url_end = Bry_find_.Find_fwd(src, AsciiByte.Quote, find_bgn, src_end);
					if (url_end == Bry_find_.Not_found) {
						err_wkr.Warn("Graph_json_save_mgr: missing endquote");
						find_bgn++;
						continue;
					}

					// parse everything between url_bgn and url_end
					img_src_parser.Clear();
					if (!img_src_parser.Parse(err_wkr, domain_bry, src, url_bgn - 1, url_end)) { // -1 b/c find_bgn starts at "file/commons" and parser needs to start at "/file/commons"
						err_wkr.Warn("", "", "Graph_json_save_mgr: invalid file_path");
						find_bgn++;
						continue;
					}

					bfr.Add_mid(src, prv_bfr, find_bgn);
					bfr.Add(Bry__xowa_root);
					bfr.Add_byte_slash(); // add / to make load_mgr easier since img_src_parser looks for /file/; EX: "{XOWA_ROOT}file/" vs "{XOWA_ROOT}/file/"

					if (test_lnr != null)
						test_lnr.Add_actl_args(img_src_parser.Repo_is_commons(), img_src_parser.File_is_orig(), img_src_parser.File_ttl_bry());

					// register image for show
					wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, page.File_queue()
						, img_src_parser.File_ttl_bry(), Xop_lnki_type.Id_none, Xop_lnki_tkn.Upright_null, img_src_parser.File_w(), Xop_lnki_tkn.Height_null
						, Xof_lnki_time.Null, Xof_lnki_page.Null, false);

					// register image for fsdb
					Xoa_ttl ttl = wiki.Ttl_parse(Xow_ns_.Tid__file, img_src_parser.File_ttl_bry());
					ctx.Lnki().File_logger().Log_file(Xop_file_logger_.Tid__graph, ctx, ttl, Xow_ns_.Tid__file, Xop_lnki_type.Id_none, img_src_parser.File_w(), Xop_lnki_tkn.Height_null, Xop_lnki_tkn.Upright_null, Xof_lnki_time.Null, Xof_lnki_page.Null);

					prv_bfr = find_bgn = find_end;
					break;
				default:
					throw Err_.new_unhandled_default(tid);
			}
		}

		// unchanged
		if (prv_bfr == src_bgn) {
			return Bry_.Mid(src, src_bgn, src_end);
		}
		// changed
		else {
			root_dir_found = true;
			bfr.Add_mid(src, prv_bfr, src_end);
			return bfr.To_bry_and_clear();
		}
	}
	public void Test_lnr_(Gfo_test_lnr_base v) {this.test_lnr = v;} private Gfo_test_lnr_base test_lnr;
	public static final byte Tid__root = 0, Tid__text = 1;
	public static final byte[]
	  Bry__wikirawupload = Bry_.new_a7("\"wikirawupload:")
	, Bry__xowa_root = Bry_.new_a7("{XOWA_ROOT}")
	;
}
