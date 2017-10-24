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
package gplx.xowa.bldrs.wms.dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.xowa.wikis.*;
public class Xowm_dump_type__tst {
	private final Xowm_dump_type__fxt fxt = new Xowm_dump_type__fxt();
	@Test  public void Parse() {
		fxt.Test_parse("pages-articles.xml"				, Xowm_dump_type_.Int__pages_articles);
		fxt.Test_parse("pages-meta-current.xml"			, Xowm_dump_type_.Int__pages_meta_current);
	}
}
class Xowm_dump_type__fxt {
	public void Test_parse(String raw_str, int expd) {Tfds.Eq_int(expd, Xowm_dump_type_.parse_by_file(Bry_.new_u8(raw_str)), "dump_type");}
}
