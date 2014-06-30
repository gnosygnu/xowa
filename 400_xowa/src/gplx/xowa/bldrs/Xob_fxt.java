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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.ios.*;
public class Xob_fxt {
	public Xob_fxt Ctor_mem() {
		Io_mgr._.InitEngine_mem();
		return Ctor(Io_url_.mem_dir_("mem/xowa/"));
	}
	public Xob_fxt Ctor(Io_url root_dir) {
		app = Xoa_app_fxt.app_(root_dir, "linux");
		wiki = Xoa_app_fxt.wiki_tst_(app);
		bldr = Xoa_app_fxt.bldr_(app);
		return this;
	}
	public Xoa_app App() {return app;} private Xoa_app app;
	public Xob_bldr Bldr() {return bldr;} private Xob_bldr bldr;
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Io_url fil_ns_title(int ns_id, int idx)	{return wiki.Fsys_mgr().Url_ns_fil(Xow_dir_info_.Tid_ttl, ns_id, idx);}
	public Io_url fil_ns_page(int ns_id, int idx)	{return wiki.Fsys_mgr().Url_ns_fil(Xow_dir_info_.Tid_page, ns_id, idx);}
	public Io_url fil_ns_sttl(int ns_id, int idx)	{return wiki.Fsys_mgr().Url_ns_fil(Xow_dir_info_.Tid_search_ttl, ns_id, idx);}
	public Io_url fil_site(byte tid, int idx)		{return wiki.Fsys_mgr().Url_site_fil(tid, idx);}
	public Io_url fil_site_ctg(int idx)				{return wiki.Fsys_mgr().Url_site_fil(Xow_dir_info_.Tid_category, idx);}
	public Io_url fil_site_id(int idx)				{return wiki.Fsys_mgr().Url_site_fil(Xow_dir_info_.Tid_id, idx);}
	public Io_url fil_reg(byte tid) 				{return wiki.Fsys_mgr().Url_site_reg(tid);}
	public Io_url fil_reg(int ns_id, byte tid) 		{return wiki.Fsys_mgr().Url_ns_reg(Int_.XtoStr_PadBgn(ns_id, 3), tid);}
	public Xob_fxt Fil_expd(Io_url url, String... expd) {
		String text = String_.Concat_lines_nl_skip_last(expd);	// skipLast b/c if trailing line wanted, easier to pass in extra argument for ""
		expd_list.Add(new Io_fil_chkr(url, text));
		return this;
	} ListAdp expd_list = ListAdp_.new_();
	public Xob_fxt Fil_skip(Io_url... urls) {
		for (int i = 0; i < urls.length; i++)
			skip_list.Add(urls[i]);
		return this;
	} 	ListAdp skip_list = ListAdp_.new_();
	public Xob_fxt doc_ary_(Xodb_page... v) {doc_ary = v; return this;} private Xodb_page[] doc_ary;
	public Xodb_page doc_wo_date_(int id, String title, String text) {return doc_(id, "2012-01-02 13:14", title, text);}
	public Xodb_page doc_(int id, String date, String title, String text) {
		Xodb_page rv = new Xodb_page().Id_(id).Ttl_(Bry_.new_utf8_(title), wiki.Ns_mgr()).Text_(Bry_.new_utf8_(text));
		int[] modified_on = new int[7];
		dateParser.Parse_iso8651_like(modified_on, date);
		rv.Modified_on_(DateAdp_.seg_(modified_on));
		return rv;
	}
	public Xob_fxt Run_ctg() {
		Xobd_parser parser = new Xobd_parser();
		gplx.xowa.bldrs.imports.ctgs.Xob_ctg_v1_base ctg_wkr = new gplx.xowa.bldrs.imports.ctgs.Xob_ctg_v1_txt().Ctor(bldr, wiki);
		byte[] bry = Bry_.new_utf8_("[[Category:");
		ctg_wkr.Wkr_hooks().Add(bry, bry);
		parser.Wkr_add(ctg_wkr);
		return Run(parser);
	}
	public Xob_fxt Run_id() {
		gplx.xowa.bldrs.imports.Xobc_core_make_id wkr = new gplx.xowa.bldrs.imports.Xobc_core_make_id(bldr, wiki);
		Run(wkr);
		return this;
	}
	public Xob_fxt Run_img(String expd) {
		Xobc_parse_run wkr = new Xobc_parse_run(bldr, wiki).Load_len_(Io_mgr.Len_kb);
		wkr.Tmpl_on_(true).Cmd_bgn(bldr);
		for (int i = 0; i < doc_ary.length; i++) {
			Xodb_page page = doc_ary[i];
			wkr.Parse_page(page);
		}
		wkr.Cmd_end();
		String actl = Io_mgr._.LoadFilStr(Io_url_.mem_fil_("mem/xowa/wiki/en.wikipedia.org/tmp/img.dump_link/make/0000000000.csv"));
		Tfds.Eq_str_lines(expd, actl);
		return this;
	}
	public Xob_fxt Run_math(String expd) {
		Xobc_parse_run wkr = new Xobc_parse_run(bldr, wiki).Load_len_(Io_mgr.Len_kb);
		wkr.Tmpl_on_(true).Cmd_bgn(bldr);
		for (int i = 0; i < doc_ary.length; i++) {
			Xodb_page page = doc_ary[i];
			wkr.Parse_page(page);
		}
		wkr.Cmd_end();
		String actl = Io_mgr._.LoadFilStr(Io_url_.mem_fil_("mem/xowa/wiki/en.wikipedia.org/tmp/math.text/make/0000000000.csv"));
		Tfds.Eq_str_lines(expd, actl);
		return this;
	}
	private void Run_wkr(Xobd_wkr wkr) {
		wkr.Wkr_bgn(bldr);
		for (int i = 0; i < doc_ary.length; i++) {
			Xodb_page page = doc_ary[i];
			wkr.Wkr_run(page);
		}
		wkr.Wkr_end();		
	}
	private void tst_fils(Io_url[] ary) {
		Io_fil[] actls = Get_actl(ary);
		Io_fil_chkr[] expds = (Io_fil_chkr[])expd_list.XtoAry(Io_fil_chkr.class);
		tst_mgr.Tst_ary("all", expds, actls);		
	}
	Io_fil[] Get_actl(Io_url[] ary) {
		int len = ary.length;
		Io_fil[] rv = new Io_fil[len];
		for (int i = 0; i < len; i++) {
			Io_url url = ary[i];
			String data = Io_mgr._.LoadFilStr(url);
			rv[i] = new Io_fil(url, data);
		}
		return rv;
	}
	public Xob_fxt Run_tmpl_dump() {
		Xobc_parse_dump_templates wkr = new Xobc_parse_dump_templates(bldr, wiki);
		Run_wkr(wkr);
		tst_fils(wkr.Dump_url_gen().Prv_urls());
		return this;
	}
	public Xob_fxt Run_page_title() {return Run(new gplx.xowa.bldrs.imports.Xob_page_txt(bldr, wiki));}
	public Xob_fxt Run(Xobd_parser_wkr... wkrs) {
		Xobd_parser parser_wkr = new Xobd_parser();
		int len = wkrs.length;
		for (int i = 0; i < len; i++)
			parser_wkr.Wkr_add(wkrs[i]);
		Run(parser_wkr);
		return this;
	}
	public Xob_fxt Run(Xobd_wkr... wkrs) {
		int doc_ary_len = doc_ary.length;
		for (int j = 0; j < wkrs.length; j++) {
			Xobd_wkr wkr = wkrs[j];
			wkr.Wkr_bgn(bldr);
			for (int i = 0; i < doc_ary_len; i++) {
				Xodb_page page = doc_ary[i];
				wkr.Wkr_run(page);
			}
			wkr.Wkr_end();
		}
		Test_expd_files();
		return this;
	}
	public Xob_fxt Run_cmds(Xob_cmd... cmds) {
		for (int j = 0; j < cmds.length; j++) {
			Xob_cmd cmd = cmds[j];
			cmd.Cmd_bgn(bldr);
			cmd.Cmd_run();
			cmd.Cmd_end();
		}
		Test_expd_files();
		return this;
	}
	private void Test_expd_files() {
		if (expd_list.Count() > 0) {
			Io_fil_chkr[] expd = (Io_fil_chkr[])expd_list.XtoAry(Io_fil_chkr.class);
			Io_fil[] actl = wiki_();
			tst_mgr.Tst_ary("all", expd, actl);
		}
	}
	Io_fil[] wiki_() {
		ListAdp rv = ListAdp_.new_();
		wiki_fil_add(rv, wiki.Fsys_mgr().Ns_dir());
		wiki_fil_add(rv, wiki.Fsys_mgr().Site_dir());
		rv.Sort();
		return (Io_fil[])rv.XtoAry(Io_fil.class);
	}
	private void wiki_fil_add(ListAdp list, Io_url root_dir) {
		Io_url[] ary = Io_mgr._.QueryDir_args(root_dir).Recur_().ExecAsUrlAry();
		for (int i = 0; i < ary.length; i++) {
			Io_url url = ary[i]; 
			Io_fil fil = new Io_fil(url, Io_mgr._.LoadFilStr_args(url).MissingIgnored_().Exec());
			list.Add(fil);
		}		
	}
	Tst_mgr tst_mgr = new Tst_mgr();
	DateAdp_parser dateParser = DateAdp_parser.new_();
} 
