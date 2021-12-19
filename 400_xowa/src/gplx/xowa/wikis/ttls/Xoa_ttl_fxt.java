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
package gplx.xowa.wikis.ttls;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
public class Xoa_ttl_fxt {
	private Xowe_wiki wiki;
	private String raw;
	private Xoa_ttl ttl;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
	}
	public Xoa_ttl_fxt Parse(String raw) {
		this.raw = raw;
		this.ttl = Xoa_ttl.Parse(wiki, BryUtl.NewU8(raw));
		return this;
	}
	public void Test__null()					{GfoTstr.EqBoolY(ttl == null, "ttl is not null", "raw", raw);}
	public void Test__page_txt(String expd)		{GfoTstr.Eq(expd, StringUtl.NewU8(ttl.Page_txt()), "page_txt", "raw", raw);}
}
