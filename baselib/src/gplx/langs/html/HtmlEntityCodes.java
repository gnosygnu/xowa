/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.html;
import gplx.types.basics.utls.BryUtl;
public class HtmlEntityCodes {
	public static final String
		NlStr = "&#10;"
	;
	public static final byte[]
		LtBry = BryUtl.NewA7("&lt;"),
		GtBry = BryUtl.NewA7("&gt;"),
		AmpBry = BryUtl.NewA7("&amp;"),
		QuoteBry = BryUtl.NewA7("&quot;"),
		AposNumBry = BryUtl.NewA7("&#39;"),
		EqBry = BryUtl.NewA7("&#61;"),
		NlBry = BryUtl.NewA7(NlStr),
		CrBry = BryUtl.NewA7("&#13;"),
		TabBry = BryUtl.NewA7("&#9;"),
		SpaceBry = BryUtl.NewA7("&#32;"),
		NbspNumBry = BryUtl.NewA7("&#160;");
}
