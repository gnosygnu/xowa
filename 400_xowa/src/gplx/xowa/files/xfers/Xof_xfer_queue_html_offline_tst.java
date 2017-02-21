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
package gplx.xowa.files.xfers; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import org.junit.*; import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.*;
public class Xof_xfer_queue_html_offline_tst {
	Xof_xfer_queue_html_fxt fxt = new Xof_xfer_queue_html_fxt();
	@Before public void init()		{fxt.Clear(true); fxt.Src_commons_repo().Tarball_(true); fxt.Src_en_wiki_repo().Tarball_(true);}
	@Test  public void Missing() {	// PURPOSE.fix: missing image was not being marked as missing; DATE:20121227
		fxt	.Lnki_("A.png", true, 220, -1, Xop_lnki_tkn.Upright_null, Xof_lnki_time.Null_as_int)
			.Src()
			.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|x||0?0,0|")
				)
			.tst();
	}
}
