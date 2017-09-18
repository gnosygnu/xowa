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
import org.junit.*;
public class Xop_lnki_wkr__frame_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final Xop_fxt fxt = new Xop_fxt();
	// PURPOSE:use 1st imgtype param; changed between mw1.22.2 and mw1.25.2  PAGE:he.w:מספן_המודיעין EX: [[File:Osa-I class Project205 DN-SN-84-01770.jpg|thumb|frame|abcde]]
	@Test  public void Use_1st__thumb()			{fxt.Test_parse_page_wiki("[[File:A.png|thumb|frame]]", fxt.tkn_lnki_().ImgType_(Xop_lnki_type.Id_thumb));}
	@Test  public void Use_1st__frame()			{fxt.Test_parse_page_wiki("[[File:A.png|frame|thumb]]", fxt.tkn_lnki_().ImgType_(Xop_lnki_type.Id_frame));}
}
