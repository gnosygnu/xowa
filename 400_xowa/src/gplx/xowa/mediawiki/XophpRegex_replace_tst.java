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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.core.primitives.*;
import gplx.langs.regxs.*;
public class XophpRegex_replace_tst {
	private final    XophpRegex_replace_fxt fxt = new XophpRegex_replace_fxt();
	@Test  public void Basic() {
		// basic
		fxt.Test__preg_replace("0", "1", "0ab0cd0ef", fxt.Expd("1ab1cd1ef").Count_(3));

		// limit
		fxt.Test__preg_replace("0", "1", "0ab0cd0ef", 2, fxt.Expd("1ab1cd0ef").Count_(2));
	}
}
class XophpRegex_replace_fxt {
	public XophpRegex_replace_expd Expd(String rslt) {return new XophpRegex_replace_expd(rslt);}
	public void Test__preg_replace(String pattern, String replacement, String subject, XophpRegex_replace_expd rslt) {Test__preg_replace(pattern, replacement, subject, XophpRegex_.preg_replace_limit_none, rslt);}
	public void Test__preg_replace(String pattern, String replacement, String subject, int limit, XophpRegex_replace_expd expd) {
		Int_obj_ref actl_count = Int_obj_ref.New_zero();
		String actl = XophpRegex_.preg_replace(Regx_adp_.new_(pattern), replacement, subject, limit, actl_count);

		Gftest.Eq__str(expd.Rslt(), actl);
		if (expd.Count() != -1)
			Gftest.Eq__int(expd.Count(), actl_count.Val());
	}
}
class XophpRegex_replace_expd {
	public XophpRegex_replace_expd(String rslt) {
		this.rslt = rslt;
	}
	public String Rslt() {return rslt;} private final    String rslt;
	public int Count() {return count;} private int count = -1;
	public XophpRegex_replace_expd Count_(int v) {count = v; return this;}
}
