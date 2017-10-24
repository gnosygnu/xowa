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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.core.ios.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_parse_dump_templates_cmd extends Xob_itm_dump_base implements Xob_page_wkr, Gfo_invk {
	public Xob_parse_dump_templates_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Page_wkr__key() {return KEY;} public static final    String KEY = "parse.dump_templates";
	public static final int FixedLen_page = 1 + 5 + 1 + 5 + 1 + 1 + 1;	// \tid|date|title|text\n
	public void Page_wkr__bgn() {
		Init_dump(KEY);
	}
	public void Page_wkr__run(Xowd_page_itm page) {
		if (page.Ns_id() != Xow_ns_.Tid__template) return;
		int id = page.Id(); byte[] title = page.Ttl_page_db(), text = page.Text(); int title_len = title.length, text_len = text.length;
		if (FixedLen_page + title_len + text_len + dump_bfr.Len() > dump_fil_len) super.Flush_dump();
		Xotdb_page_itm_.Txt_page_save(dump_bfr, id, page.Modified_on(), title, text, true);
	}
	public void Page_wkr__run_cleanup() {}
	public void Page_wkr__end() {super.Flush_dump();}
}
