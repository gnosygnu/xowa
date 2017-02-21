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
package gplx.core.texts; import gplx.*; import gplx.core.*;
import org.junit.*;
public class StringTableBldr_tst {
	@Before public void setup() {
		bldr = StringTableBldr.new_();
	}	StringTableBldr bldr;
	@Test  public void TwoCols() {
		bldr.Add("a", "aa")
			.Add("bb", "b");
		tst_XtoStr
			( "a  aa"
			, "bb b "
			, ""
			);
	}
	@Test  public void RightAlign() {
		bldr.Add("a", "aa")
			.Add("bb", "b");
		bldr.FetchAtOrNew(0).Halign_(StringTableColAlign.Right);
		bldr.FetchAtOrNew(1).Halign_(StringTableColAlign.Right);
		tst_XtoStr
			( " a aa"
			, "bb  b"
			, ""
			);
	}
	@Test  public void CenterAlign() {
		bldr.Add("aaaa", "a")
			.Add("b", "bbbb");
		bldr.FetchAtOrNew(0).Halign_(StringTableColAlign.Mid);
		bldr.FetchAtOrNew(1).Halign_(StringTableColAlign.Mid);
		tst_XtoStr
			( "aaaa  a  "
			, " b   bbbb"
			, ""
			);
	}
	void tst_XtoStr(String... expdLines) {
		String expd = String_.Concat_with_obj(String_.CrLf, (Object[])expdLines);
		Tfds.Eq(expd, bldr.To_str());
	}
}
