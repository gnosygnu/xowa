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
import gplx.core.tests.*; import gplx.core.times.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xobldr_fxt {
	private final    DateAdp_parser dateParser = DateAdp_parser.new_();
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xob_bldr Bldr() {return bldr;} private Xob_bldr bldr;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xobldr_fxt Ctor_mem() {
		Io_mgr.Instance.InitEngine_mem();
		return Ctor(Io_url_.mem_dir_("mem/xowa/"));
	}
	private Xobldr_fxt Ctor(Io_url root_dir) {
		Db_conn_bldr.Instance.Reg_default_sqlite();
		app = Xoa_app_fxt.Make__app__edit("linux", root_dir);
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		bldr = Xoa_app_fxt.bldr_(app);
		return this;
	}
	public Xowd_page_itm New_page_wo_date(int id, String title, String text) {return New_page(id, "2012-01-02 13:14", title, text);}
	public Xowd_page_itm New_page(int id, String date, String title, String text) {
		Xowd_page_itm rv = new Xowd_page_itm().Id_(id).Ttl_(Bry_.new_u8(title), wiki.Ns_mgr()).Text_(Bry_.new_u8(text));
		int[] modified_on = new int[7];
		dateParser.Parse_iso8651_like(modified_on, date);
		rv.Modified_on_(DateAdp_.seg_(modified_on));
		return rv;
	}
	public void Run_page_wkr(Xob_page_wkr wkr, Xowd_page_itm... pages) {
		int len = pages.length;
		wkr.Page_wkr__bgn();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = pages[i];
			wkr.Page_wkr__run(page);
		}
		wkr.Page_wkr__end();
	}
} 
