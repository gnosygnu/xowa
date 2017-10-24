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
import org.junit.*; import gplx.xowa.parsers.xndes.*;
public class Xop_lnki_wkr__size_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Width__w__ws() {
		fxt.Test_parse_page_wiki("[[Image:a| 123 px]]"		, fxt.tkn_lnki_().Width_(123));
	}
	@Test  public void Height() {
		fxt.Test_parse_page_wiki("[[Image:a|x40px]]"		, fxt.tkn_lnki_().Height_(40).Width_(-1));
	}
	@Test  public void Invalid_px__accept_double() {
		fxt.Test_parse_page_wiki("[[Image:a|40pxpx]]"		, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test  public void Invalid_px__accept_double__w_ws() {
		fxt.Test_parse_page_wiki("[[Image:a|40pxpx  ]]"		, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test  public void Invalid_px__accept_double__w_ws_2() {// PURPOSE: handle ws between px's; EX:sv.w:Drottningholms_slott; DATE:2014-03-01
		fxt.Test_parse_page_wiki("[[Image:a|40px px]]"		, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test  public void Invalid_px__ignore_if_w() {
		fxt.Test_parse_page_wiki("[[Image:a|40px20px]]"		, fxt.tkn_lnki_().Width_(-1).Height_(-1));	// -1 b/c "40px"
	}
	@Test  public void Large_number() {	// PURPOSE: perf code identified large sizes as caption; DATE:2014-02-15
		fxt.Test_parse_page_wiki("[[Image:a|1234567890x1234567890px]]"	, fxt.tkn_lnki_().Width_(1234567890).Height_(1234567890));
	}
	@Test  public void Large_number__discard_if_gt_int() {	// PURPOSE: size larger than int should be discarded, not be Int_.Max_value: PAGE:id.w:Baho; DATE:2014-06-10
		fxt.Test_html_wiki_frag("[[File:A.png|9999999999x30px]]", " width=\"0\" height=\"30\"");	// width should not be Int_.Max_value
	}
	@Test  public void Dangling_xnde() {	// PURPOSE: dangling xnde should not eat rest of lnki; PAGE:sr.w:Сићевачка_клисура DATE:2014-07-03
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde).Test_parse_page_wiki("[[Image:a.png|<b>c|40px]]"	, fxt.tkn_lnki_().Width_(40).Height_(-1));
	}
	@Test   public void Ws_para() {	// PURPOSE: <p> in arg_bldr causes parse to fail; EX: w:Supreme_Court_of_the_United_States; DATE:2014-04-05; updated test; DATE:2015-03-31
		fxt.Init_para_y_();
		fxt.Test_parse_page_all("[[File:A.png| \n 40px]]"
		, fxt.tkn_para_bgn_para_(0)
		, fxt.tkn_lnki_().Width_(40).Height_(-1)
		, fxt.tkn_para_end_para_(22));
		fxt.Init_para_n_();
	}
	@Test  public void Invalid_size()	{	// PURPOSE: handle invalid sizes
		fxt.Test_parse_page_wiki("[[File:A.png|1234xSomeTextpx]]"	, fxt.tkn_lnki_().Width_(-1).Height_(-1));	// PAGE:es.b:Alimentación_infantil; DATE:2015-07-10
		fxt.Test_parse_page_wiki("[[File:A.png|100 KBpx]]"			, fxt.tkn_lnki_().Width_(-1).Height_(-1));	// PAGE:en.w:Bahamas; DATE:2015-08-05
		fxt.Test_parse_page_wiki("[[File:A.png|size100px]]"			, fxt.tkn_lnki_().Width_(-1).Height_(-1));	// PAGE:en.w:Data_compression; DATE:2015-08-05
		fxt.Test_parse_page_wiki("[[File:A.png|20\n0px]]"			, fxt.tkn_lnki_().Width_(-1).Height_(-1));	// PAGE:en.w:Double_bass; DATE:2015-08-05
	}
}
