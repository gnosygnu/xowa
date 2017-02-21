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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tblw_wkr__dangling_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Dangling_tb_in_xnde() {// PURPOSE: dangling tblw incorrectly auto-closed by </xnde>; PAGE:en.w:Atlanta_Olympics; DATE:2014-03-18
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"<div align='center'>"
		,	"{|"
		,	"|-"
		,	"|"
		,	"{|"
		,	"|-"
		,	"|a"
		,	"|}"
		,	"</div>"
		,	"b"
		)		
		, String_.Concat_lines_nl
		(	"<div align='center'>"
		,	"<table>"
		,	"  <tr>"
		,	"    <td>"
		,	"      <table>"
		,	"        <tr>"
		,	"          <td>a"
		,	"          </td>"
		,	"        </tr>"
		,	"      </table>"
		,	"</div>"	// TIDY.dangling: tidy will correct dangling node; DATE:2014-07-22
		,	""
		,	"<p>b"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"</div>"
		,	"</p>"
		));
	}
}
