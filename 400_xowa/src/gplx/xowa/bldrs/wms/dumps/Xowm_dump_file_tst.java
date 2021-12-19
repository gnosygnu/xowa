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
package gplx.xowa.bldrs.wms.dumps;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xowm_dump_file_tst {
	private final Xowm_dump_file_fxt fxt = new Xowm_dump_file_fxt();
	@Test public void Parse()	{fxt.Test_parse("enwiki-20121201-pages-articles.xml.bz2", "en.wikipedia.org", "20121201", Xowm_dump_type_.Int__pages_articles);}
	@Test public void Bld_dump_dir_url() {
		fxt.Test_bld_dump_dir_url("simplewiki", "latest", "http://dumps.wikimedia.your.org/simplewiki/latest/");
	}
	@Test public void Bld_dump_file_name() {
		fxt.Test_bld_dump_file_name("simplewiki", "latest", Xowm_dump_type_.Str__pages_articles, "simplewiki-latest-pages-articles.xml.bz2");
	}
}
class Xowm_dump_file_fxt {
	public void Test_parse(String name, String domain, String date, int tid) {
		Xowm_dump_file dump_file = Xowm_dump_file_.parse(BryUtl.NewU8(name));
		GfoTstr.EqObj(domain	, dump_file.Domain_itm().Domain_str());
		GfoTstr.EqObj(date	, dump_file.Dump_date());
		GfoTstr.EqObj(tid		, Xowm_dump_type_.parse_by_file(BryUtl.NewU8(dump_file.Dump_type_str())));
	}
	public void Test_bld_dump_dir_url(String dump_file, String date, String expd) {
		byte[] actl = Xowm_dump_file_.Bld_dump_dir_url(BryUtl.NewA7(Xowm_dump_file_.Server_your_org), BryUtl.NewA7(dump_file), BryUtl.NewA7(date));
		GfoTstr.EqObj(expd, StringUtl.NewA7(actl));
	}
	public void Test_bld_dump_file_name(String dump_file, String date, String dump_type, String expd) {
		byte[] actl = Xowm_dump_file_.Bld_dump_file_name(BryUtl.NewA7(dump_file), BryUtl.NewA7(date), BryUtl.NewA7(dump_type), Xowm_dump_file_.Ext_xml_bz2);
		GfoTstr.EqObj(expd, StringUtl.NewA7(actl));
	}
}
