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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xop_lnki_wkr__video_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Thumbtime() {
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=123]]"		, fxt.tkn_lnki_().Thumbtime_(123));
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=1:23]]"	, fxt.tkn_lnki_().Thumbtime_(83));
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=1:01:01]]"	, fxt.tkn_lnki_().Thumbtime_(3661));
		fxt.Init_log_(Xop_lnki_log.Upright_val_is_invalid).Test_parse_page_wiki("[[File:A.ogv|thumbtime=a]]", fxt.tkn_lnki_().Thumbtime_(-1));
	}
	@Test  public void Size__null() {	// NOTE: make sure that no size defaults to -1; needed for Xof_img_size logic of defaulting -1 to orig_w; DATE:2015-08-07
		fxt.Test_parse_page_wiki("[[File:A.ogv]]"					, fxt.tkn_lnki_().Width_(Xof_img_size.Size__neg1));
	}
}
