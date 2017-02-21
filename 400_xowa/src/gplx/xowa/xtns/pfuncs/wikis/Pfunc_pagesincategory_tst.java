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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
public class Pfunc_pagesincategory_tst {
	private final    Pfunc_pagesincategory_tstr tstr = new Pfunc_pagesincategory_tstr();
	@Before	public void setup()	{tstr.Init(); tstr.Init_category_counts("A", 1000, 2000, 3000);}
	@Test   public void Type__none()				{tstr.Test_parse("{{PAGESINCATEGORY:A}}"			, "6,000");}
	@Test   public void Type__empty()				{tstr.Test_parse("{{PAGESINCATEGORY:A|}}"			, "6,000");}	// FIX:throws null error; PAGE: DATE:2016-04-21
	@Test   public void Type__none__fmt()			{tstr.Test_parse("{{PAGESINCATEGORY:A|R}}"			, "6000");}
	@Test   public void Type__page__1st()			{tstr.Test_parse("{{PAGESINCATEGORY:A|pages}}"		, "1,000");}
	@Test   public void Type__subc__1st()			{tstr.Test_parse("{{PAGESINCATEGORY:A|subcats}}"	, "2,000");}
	@Test   public void Type__file__1st()			{tstr.Test_parse("{{PAGESINCATEGORY:A|files}}"		, "3,000");}
	@Test   public void Type__page__2nd()			{tstr.Test_parse("{{PAGESINCATEGORY:A|R|pages}}"	, "1000");}
	@Test   public void Type__subc__2nd()			{tstr.Test_parse("{{PAGESINCATEGORY:A|R|subcats}}"	, "2000");}
	@Test   public void Type__file__2nd()			{tstr.Test_parse("{{PAGESINCATEGORY:A|R|files}}"	, "3000");}
	@Test   public void Zero__no_title()			{tstr.Test_parse("{{PAGESINCATEGORY:}}"				, "0");}
	@Test   public void Zero__missing_title()		{tstr.Test_parse("{{PAGESINCATEGORY:Missing}}"		, "0");}
	@Test   public void Wrong_args()				{tstr.Test_parse("{{PAGESINCATEGORY:A|invalid|x}}"	, "6,000");}	// defaults to all,fmt
}
class Pfunc_pagesincategory_tstr {
	private final    Xop_fxt parser_tstr;
	private final    Xoae_app app; private final    Xowe_wiki wiki;
	private final    Xow_db_mgr core_data_mgr;
	private final    Xowd_page_tbl page_tbl; private final    Xowd_cat_core_tbl cat_core_tbl;
	public Pfunc_pagesincategory_tstr() {
		Xoa_test_.Inet__init();
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_test_.Init__db__edit(wiki);
		this.parser_tstr = new Xop_fxt(app, wiki);
		this.core_data_mgr = wiki.Data__core_mgr();
		this.page_tbl = core_data_mgr.Tbl__page();
		this.cat_core_tbl = gplx.xowa.addons.wikis.ctgs.dbs.Xodb_cat_db_.Get_cat_core_or_fail(core_data_mgr);
	}
	public void Init() {
		parser_tstr.Reset();
	}
	public void Init_category_counts(String category_title, int pages, int subcs, int files) {
		int page_id = 1;
		page_tbl.Insert_bgn();
		page_tbl.Insert_cmd_by_batch(page_id, Xow_ns_.Tid__category, Bry_.new_u8(category_title), Bool_.N, Datetime_now.Get(), 1, 1, 1, 1, -1);
		page_tbl.Insert_end();
		cat_core_tbl.Insert_bgn();
		cat_core_tbl.Insert_cmd_by_batch(page_id, pages, subcs, files, Byte_.Zero, 1);
		cat_core_tbl.Insert_end();
	}
	public void Test_parse(String raw, String expd) {
		parser_tstr.Test_html_full_str(raw, expd);
	}
}
