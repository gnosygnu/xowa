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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.tests.*; import gplx.core.ios.*; import gplx.core.times.*;
import gplx.dbs.*; import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.cmds.texts.tdbs.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_fxt {
	public Xob_fxt Ctor_mem() {
		Io_mgr.Instance.InitEngine_mem();
		return Ctor(Io_url_.mem_dir_("mem/xowa/"));
	}
	public Xob_fxt Ctor(Io_url root_dir) {
		Db_conn_bldr.Instance.Reg_default_sqlite();
		app = Xoa_app_fxt.Make__app__edit("linux", root_dir);
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		bldr = Xoa_app_fxt.bldr_(app);
		return this;
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xob_bldr Bldr() {return bldr;} private Xob_bldr bldr;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Io_url fil_ns_title(int ns_id, int idx)	{return wiki.Tdb_fsys_mgr().Url_ns_fil(Xotdb_dir_info_.Tid_ttl, ns_id, idx);}
	public Io_url fil_ns_page(int ns_id, int idx)	{return wiki.Tdb_fsys_mgr().Url_ns_fil(Xotdb_dir_info_.Tid_page, ns_id, idx);}
	public Io_url fil_ns_sttl(int ns_id, int idx)	{return wiki.Tdb_fsys_mgr().Url_ns_fil(Xotdb_dir_info_.Tid_search_ttl, ns_id, idx);}
	public Io_url fil_site(byte tid, int idx)		{return wiki.Tdb_fsys_mgr().Url_site_fil(tid, idx);}
	public Io_url fil_site_ctg(int idx)				{return wiki.Tdb_fsys_mgr().Url_site_fil(Xotdb_dir_info_.Tid_category, idx);}
	public Io_url fil_site_id(int idx)				{return wiki.Tdb_fsys_mgr().Url_site_fil(Xotdb_dir_info_.Tid_id, idx);}
	public Io_url fil_reg(byte tid) 				{return wiki.Tdb_fsys_mgr().Url_site_reg(tid);}
	public Io_url fil_reg(int ns_id, byte tid) 		{return wiki.Tdb_fsys_mgr().Url_ns_reg(Int_.To_str_pad_bgn_zero(ns_id, 3), tid);}
	public Xob_fxt Fil_expd(Io_url url, String... expd) {
		String text = String_.Concat_lines_nl_skip_last(expd);	// skipLast b/c if trailing line wanted, easier to pass in extra argument for ""
		expd_list.Add(new Io_fil_chkr(url, text));
		return this;
	} List_adp expd_list = List_adp_.New();
	public Xob_fxt Fil_skip(Io_url... urls) {
		for (int i = 0; i < urls.length; i++)
			skip_list.Add(urls[i]);
		return this;
	} 	List_adp skip_list = List_adp_.New();
	public Xob_fxt doc_ary_(Xowd_page_itm... v) {doc_ary = v; return this;} private Xowd_page_itm[] doc_ary;
	public Xowd_page_itm doc_wo_date_(int id, String title, String text) {return doc_(id, "2012-01-02 13:14", title, text);}
	public Xowd_page_itm doc_(int id, String date, String title, String text) {
		Xowd_page_itm rv = new Xowd_page_itm().Id_(id).Ttl_(Bry_.new_u8(title), wiki.Ns_mgr()).Text_(Bry_.new_u8(text));
		int[] modified_on = new int[7];
		dateParser.Parse_iso8651_like(modified_on, date);
		rv.Modified_on_(DateAdp_.seg_(modified_on));
		return rv;
	}
	public Xob_fxt Run_id() {
		Xob_make_id_wkr wkr = new Xob_make_id_wkr(bldr, wiki);
		Run(wkr);
		return this;
	}
	private void Run_wkr(Xob_page_wkr wkr) {
		wkr.Page_wkr__bgn();
		for (int i = 0; i < doc_ary.length; i++) {
			Xowd_page_itm page = doc_ary[i];
			wkr.Page_wkr__run(page);
		}
		wkr.Page_wkr__end();		
	}
	private void tst_fils(Io_url[] ary) {
		Io_fil[] actls = Get_actl(ary);
		Io_fil_chkr[] expds = (Io_fil_chkr[])expd_list.To_ary(Io_fil_chkr.class);
		tst_mgr.Tst_ary("all", expds, actls);		
	}
	Io_fil[] Get_actl(Io_url[] ary) {
		int len = ary.length;
		Io_fil[] rv = new Io_fil[len];
		for (int i = 0; i < len; i++) {
			Io_url url = ary[i];
			String data = Io_mgr.Instance.LoadFilStr(url);
			rv[i] = new Io_fil(url, data);
		}
		return rv;
	}
	public Xob_fxt Run_tmpl_dump() {
		Xob_parse_dump_templates_cmd wkr = new Xob_parse_dump_templates_cmd(bldr, wiki);
		Run_wkr(wkr);
		tst_fils(wkr.Dump_url_gen().Prv_urls());
		return this;
	}
	public Xob_fxt Run(Xobd_parser_wkr... wkrs) {
		Xobd_parser parser_wkr = new Xobd_parser(bldr);
		int len = wkrs.length;
		for (int i = 0; i < len; i++)
			parser_wkr.Wkr_add(wkrs[i]);
		Run(parser_wkr);
		return this;
	}
	public Xob_fxt Run(Xob_page_wkr... wkrs) {
		int doc_ary_len = doc_ary.length;
		for (int j = 0; j < wkrs.length; j++) {
			Xob_page_wkr wkr = wkrs[j];
			wkr.Page_wkr__bgn();
			for (int i = 0; i < doc_ary_len; i++) {
				Xowd_page_itm page = doc_ary[i];
				wkr.Page_wkr__run(page);
			}
			wkr.Page_wkr__end();
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
			Io_fil_chkr[] expd = (Io_fil_chkr[])expd_list.To_ary(Io_fil_chkr.class);
			Io_fil[] actl = wiki_();
			tst_mgr.Tst_ary("all", expd, actl);
		}
	}
	Io_fil[] wiki_() {
		List_adp rv = List_adp_.New();
		wiki_fil_add(rv, wiki.Tdb_fsys_mgr().Ns_dir());
		wiki_fil_add(rv, wiki.Tdb_fsys_mgr().Site_dir());
		rv.Sort();
		return (Io_fil[])rv.To_ary(Io_fil.class);
	}
	private void wiki_fil_add(List_adp list, Io_url root_dir) {
		Io_url[] ary = Io_mgr.Instance.QueryDir_args(root_dir).Recur_().ExecAsUrlAry();
		for (int i = 0; i < ary.length; i++) {
			Io_url url = ary[i]; 
			Io_fil fil = new Io_fil(url, Io_mgr.Instance.LoadFilStr_args(url).MissingIgnored_().Exec());
			list.Add(fil);
		}		
	}
	Tst_mgr tst_mgr = new Tst_mgr();
	DateAdp_parser dateParser = DateAdp_parser.new_();
} 
