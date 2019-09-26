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
package gplx.xowa.xtns.graphs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.tests.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.files.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.hdumps.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.parsers.lnkis.*;
class Graph_json_load_mgr implements Xoh_hdump_wkr {
	public byte[] Key() {return KEY;}
	public int Process(Bry_bfr bfr, Xoh_hdoc_ctx hctx, Xoh_hdoc_wkr hdoc_wkr, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag tag) {
		// add opening <div>
		bfr.Add_mid(src, tag.Src_bgn(), tag.Src_end());

		// find </div>
		int src_bgn = tag.Src_end();
		Gfh_tag div_end = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);

		// declare json vars
		int src_end = div_end.Src_bgn();
		int bfr_prv = src_bgn;

		// init err_wkr
		Bry_err_wkr err_wkr = new Bry_err_wkr();
		err_wkr.Init_by_page(String_.new_u8(hctx.Page__url()), src);
		err_wkr.Init_by_sect("graph", src_bgn);

		Xow_domain_itm domain_itm = hctx.Wiki__domain_itm();
		Xoh_img_src_data img_src_parser = new Xoh_img_src_data();

		int pos = src_bgn;
		while (true) {
			// find {XOWA_ROOT}
			int find_bgn = Bry_find_.Find_fwd(src, Graph_json_save_mgr.Bry__xowa_root, pos);

			// not found, or outside JSON range
			if (find_bgn == Bry_find_.Not_found || find_bgn > src_end)
				break;

			// {XOWA_ROOT} found; add everything up to it
			bfr.Add_mid(src, bfr_prv, find_bgn);

			// check for {XOWA_ROOT}{XOWA_ROOT}
			int find_end = find_bgn + Graph_json_save_mgr.Bry__xowa_root.length;
			int double_end = find_end + Graph_json_save_mgr.Bry__xowa_root.length;
			if (double_end < src_end
				&& Bry_.Match(src, find_end, double_end, Graph_json_save_mgr.Bry__xowa_root)) {
				bfr.Add(Graph_json_save_mgr.Bry__xowa_root);
				bfr_prv = pos = double_end;
				continue;
			}

			// get url_end by searching for '\"'
			int url_bgn = find_bgn + Graph_json_save_mgr.Bry__xowa_root.length;
			int url_end = Bry_find_.Find_fwd(src, Byte_ascii.Quote, find_bgn, src_end);
			if (url_end == Bry_find_.Not_found) {
				err_wkr.Warn("Graph_json_save_mgr: missing endquote");
				bfr.Add_mid(src, find_bgn, find_end);
				bfr_prv = pos = find_end;
				continue;
			}

			// parse everything between url_bgn and url_end
			img_src_parser.Clear();
			if (!img_src_parser.Parse(err_wkr, hctx.Wiki__domain_bry(), src, url_bgn, url_end)) { // -1 b/c find_bgn starts at "file/commons" and parser needs to start at "/file/commons"
				err_wkr.Warn("", "", "Graph_json_save_mgr: invalid file_path");
				bfr.Add_mid(src, find_bgn, find_end);
				bfr_prv = pos = find_end;
				continue;
			}

			// register image for show
			Xoh_page hpg = ((Xoh_page)hctx.Page());
			Xof_fsdb_itm fsdb_itm = hpg.Img_mgr().Make_img(false);
			fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, domain_itm.Abrv_xo(), img_src_parser.File_ttl_bry(), Xop_lnki_type.Id_none
				, Xop_lnki_tkn.Upright_null, img_src_parser.File_w(), Xop_lnki_tkn.Height_null, Xof_lnki_time.Null, Xof_lnki_page.Null, Xof_patch_upright_tid_.Tid_all);
			hctx.Cache_mgr().Find(hpg.Wiki(), hpg.Url_bry_safe(), fsdb_itm);

			if (test_lnr != null)
				test_lnr.Add_actl_args(img_src_parser.Repo_is_commons(), img_src_parser.File_is_orig(), img_src_parser.File_ttl_bry());

			// add root_dir
			bfr.Add(hctx.App().Fsys_mgr().Root_dir().To_http_file_bry());
			bfr.Add_mid(src, url_bgn + 1, url_end); // + 1 to skip slash in "/file/"
			bfr_prv = pos = url_end;
		}

		// add rest of json
		bfr.Add_mid(src, bfr_prv, src_end);

		// add </div>
		bfr.Add_mid(src, div_end.Src_bgn(), div_end.Src_end());
		return div_end.Src_end();
	}
	public void Test_lnr_(Gfo_test_lnr_base v) {this.test_lnr = v;} private Gfo_test_lnr_base test_lnr;

	public static byte[] KEY = Bry_.new_a7("graph-json");
	public static byte[] HDUMP_ATR = Xoh_hdump_wkr_utl.Build_hdump_atr(KEY);
}
