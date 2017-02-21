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
package gplx.core.interfaces; import gplx.*; import gplx.core.*;
import org.junit.*;
public class SrlAble__tst {
	@Test  public void Basic() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1)
			,	"itm:key='a' val='1';"
			);

	}
	@Test  public void Depth1_1() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1).Subs_
			(		GfoMsg_.new_cast_("itm").Add("key", "aa").Add("val", 11)
			)
			,	String_.Concat_lines_crlf_skipLast
			(	"itm:key='a' val='1'{itm:key='aa' val='11';}"
			)
			);
	}
	@Test  public void Depth1_2() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1).Subs_
			(		GfoMsg_.new_cast_("itm").Add("key", "aa").Add("val", 11)
			,		GfoMsg_.new_cast_("itm").Add("key", "ab").Add("val", 12)
			)
			,	String_.Concat_lines_crlf_skipLast
			(	"itm:key='a' val='1'{"
			,	"    itm:key='aa' val='11';"
			,	"    itm:key='ab' val='12';"
			,	"}"
			)
			);
	}
	@Test  public void Depth1_1_2() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1).Subs_
			(		GfoMsg_.new_cast_("itm").Add("key", "aa").Add("val", 11).Subs_(
						GfoMsg_.new_cast_("itm").Add("key", "aab").Add("val", 112)
			)
			)
			,	String_.Concat_lines_crlf_skipLast
			(	"itm:key='a' val='1'{itm:key='aa' val='11'{itm:key='aab' val='112';}}"
			)
			);
	}
	void tst_Srl_(GfoMsg m, String expd) {Tfds.Eq(expd, SrlAble_.To_str(m));}
}
//class SrlAble__tst
