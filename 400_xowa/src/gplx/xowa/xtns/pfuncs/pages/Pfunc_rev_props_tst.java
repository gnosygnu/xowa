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
package gplx.xowa.xtns.pfuncs.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.wikis.ttls.*;
public class Pfunc_rev_props_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset(); fxt.Page().Db().Protection().User_(Bry_.new_a7("user")).Protection_level_(Bry_.new_a7("normal"));}
	@Test  public void Revision_id()				{fxt.Page().Db().Page().Id_(1); fxt.Test_parse_tmpl_str_test("{{REVISIONID}}"		, "{{test}}", "1");}
	@Test  public void Page_id()					{fxt.Page().Db().Page().Id_(1); fxt.Test_parse_tmpl_str_test("{{PAGEID}}"			, "{{test}}", "1");}
	@Test  public void Revision_user()				{fxt.Test_parse_tmpl_str_test("{{REVISIONUSER}}"									, "{{test}}", "user");}
	@Test  public void Page_size()					{fxt.Test_parse_tmpl_str_test("{{PAGESIZE:Test page}}"								, "{{test}}", "0");}
	@Test  public void Revision_size()				{fxt.Test_parse_tmpl_str_test("{{REVISIONSIZE}}"									, "{{test}}", "8");}
	@Test  public void Protection_level()			{fxt.Test_parse_tmpl_str_test("{{PROTECTIONLEVEL}}"									, "{{test}}", "normal");}
	@Test  public void Protection_expiry()			{fxt.Test_parse_tmpl_str_test("{{PROTECTIONEXPIRY}}"								, "{{test}}", "infinity");}
	@Test  public void PageSize_invalid_ttl()		{fxt.Test_parse_tmpl_str_test("{{PAGESIZE:{{{100}}}|R}}"							, "{{test}}", "0");}
}
