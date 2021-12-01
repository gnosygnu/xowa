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
package gplx.xowa.addons.bldrs.app_cfgs.wm_server_cfgs; import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Bry_find_;
import gplx.Byte_ascii;
import gplx.DateAdp;
import gplx.Datetime_now;
import gplx.Err_;
import gplx.Io_mgr;
import gplx.Io_url;
import gplx.core.ios.IoItmFil;
import gplx.langs.phps.Php_evaluator;
import gplx.langs.phps.Php_itm_ary;
import gplx.langs.phps.Php_itm_kv;
import gplx.langs.phps.Php_line;
import gplx.langs.phps.Php_line_assign;
import gplx.langs.phps.Php_parser;
import gplx.xowa.Xoa_app;
import gplx.xowa.wikis.domains.Xow_abrv_wm_;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
public class Xowm_server_cfg_mgr {
	public void Exec(Xoa_app app) {
		// get local file
		Io_url tmp_url = app.Fsys_mgr().Bin_xowa_dir().GenSubFil_nest("cfg", "wiki", "InitialiseSettings.php.txt");
		Assert_recent_or_download(tmp_url, "https://noc.wikimedia.org/conf/InitialiseSettings.php.txt", 1440);
		byte[] src = Io_mgr.Instance.LoadFilBry(tmp_url);
		Parse_cat_collation(src);
	}
	private void Parse_cat_collation(byte[] all) {
		// extract cat_collation
		int bgn_pos = Bry_find_.Find_fwd(all, Bry_.new_a7("wgCategoryCollation"));
		if (bgn_pos == Bry_find_.Not_found) throw Err_.new_wo_type("could not find wgCategoryCollation bgn");
		bgn_pos = Bry_find_.Move_fwd(all, Byte_ascii.Nl_bry, bgn_pos);

		int end_pos = Bry_find_.Find_fwd(all, Bry_.new_a7("\n],"), bgn_pos);
		if (end_pos == Bry_find_.Not_found) throw Err_.new_wo_type("could not find wgCategoryCollation end");
		byte[] src = Bry_.Mid(all, bgn_pos, end_pos + 2);	// +2="],"
		src = Bry_.Add(Bry_.new_a7("$test=["), src, Bry_.new_a7("];"));

		// parse php
		Php_parser php_parser = new Php_parser();
		Php_evaluator eval = new Php_evaluator(new gplx.core.log_msgs.Gfo_msg_log("test"));
		php_parser.Parse_tkns(src, eval);
		Php_line[] lines = (Php_line[])eval.List().ToAry(Php_line.class);
		Php_line_assign line = (Php_line_assign)lines[0];
		Php_itm_ary root_ary = (Php_itm_ary)line.Val();

		// loop tkns and build String
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_byte_nl();
		int len = root_ary.Subs_len();
		byte[] wiki_abrv__default = Bry_.new_a7("default");
		for (int i = 0; i < len; ++i) {
			Php_itm_kv kv = (Php_itm_kv)root_ary.Subs_get(i);
			byte[] wiki_abrv = kv.Key().Val_obj_bry();
			byte[] collation = kv.Val().Val_obj_bry();

			if (Bry_.Eq(wiki_abrv, wiki_abrv__default)) continue;	// skip "'default' => 'uppercase',"
			try {
				byte[] wiki_domain = Xow_abrv_wm_.Parse_to_domain_bry(wiki_abrv);

				Xow_domain_itm itm = Xow_domain_itm_.parse(wiki_domain);
				bfr.Add_str_u8_fmt("app.bldr.wiki_cfg_bldr.get('{0}').new_cmd_('wiki.ctgs.collations', \"catpage_mgr.collation_('{1}');\");\n", itm.Domain_bry(), collation);
			} catch (Exception e) {throw Err_.new_("failed to parse line", "wiki", wiki_abrv, "collation", collation, "err", Err_.Message_lang(e));}
		}
		// Tfds.Write(bfr.To_str_and_clear());
	}
	private static void Assert_recent_or_download(Io_url trg, String src, int min) {
		// get file
		IoItmFil trg_fil = Io_mgr.Instance.QueryFil(trg);

		// exit if file exists and is recent
		if (trg_fil != null) {
			DateAdp now = Datetime_now.Get();
			if (now.Diff(trg_fil.ModifiedTime()).Total_mins().To_int() < min) return;
		}

		// load b/c file doesn't exist, or is not recent
		Io_mgr.Instance.DownloadFil(src, trg);			
	}
}
