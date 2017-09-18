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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
public class Xow_wiki_tst {
	@Before public void init() {fxt.Clear();} private Xow_wiki_fxt fxt = new Xow_wiki_fxt();
	@Test   public void Load_page_and_parse() {	// PURPOSE.fix: unknown page causes null reference error in scribunto; DATE:2013-08-27
		fxt.Fxt().Wiki().Parser_mgr().Scrib().Core_init(fxt.Fxt().Ctx());
		fxt.Test_getPageByTtl("Does_not_exist", null);
	}
}
class Xow_wiki_fxt {
	public void Clear() {
		fxt = new Xop_fxt();
	}
	public Xop_fxt Fxt() {return fxt;} private Xop_fxt fxt;
	public void Test_getPageByTtl(String ttl_str, String expd) {
		Xowe_wiki wiki = fxt.Wiki();
		byte[] ttl_bry = Bry_.new_a7(ttl_str);
		Xoa_url url = Xoa_url.New(wiki.Domain_bry(), ttl_bry);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		Xoae_page actl = fxt.Wiki().Data_mgr().Load_page_and_parse(url, ttl);
		if (expd == null) Tfds.Eq_true(actl.Db().Page().Exists_n());
		else Tfds.Eq(expd, String_.new_u8(actl.Ttl().Raw()));
	}
}
