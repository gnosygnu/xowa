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
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
import org.junit.*;
public class Xoh_tidy_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_tidy_mgr_fxt fxt = new Xoh_tidy_mgr_fxt();
	@Test   public void Wrap() {
		fxt.Test_wrap("<b>a</b>"
		, "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
		+ "<html>"
		+   "<head>"
		+     "<title>test</title>"
		+   "</head>"
		+   "<body><b>a</b>"
		+   "</body>"
		+ "</html>"
		);
	}
	@Test   public void Unwrap_pass() {
		fxt.Test_unwrap
		( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
		+ "<html>"
		+   "<head>"
		+     "<title>test</title>"
		+   "</head>"
		+   "<body><b>a</b>"
		+   "</body>"
		+ "</html>"
		, Bool_.Y, "<b>a</b>"
		);
	}
	@Test   public void Unwrap_fail_bgn() {
		fxt.Test_unwrap
		( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
		+ "<html>"
		+   "<head>"
		+     "<title>test</title>"
		+   "</head>"
		+   "<bodyx><b>a</b>"
		+   "</body>"
		+ "</html>"
		, Bool_.N, ""
		);
	}
	@Test   public void Unwrap_fail_end() {
		fxt.Test_unwrap
		( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
		+ "<html>"
		+   "<head>"
		+     "<title>test</title>"
		+   "</head>"
		+   "<body><b>a</b>"
		+   "</bodyx>"
		+ "</html>"
		, Bool_.N, ""
		);
	}
}
class Xoh_tidy_mgr_fxt {
	private Bry_bfr bfr = Bry_bfr_.Reset(255);
	public void Clear() {
		bfr.Clear();
	}
	public void Test_wrap(String val, String expd) {
		bfr.Add_str_u8(val);
		Xow_tidy_mgr.Tidy_wrap(bfr);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
	public void Test_unwrap(String val, boolean expd_pass, String expd) {
		bfr.Add_str_u8(val);
		boolean actl_pass = Xow_tidy_mgr.Tidy_unwrap(bfr);
		if (actl_pass != expd_pass) Tfds.Fail("expd={0} actl={1}", expd_pass, actl_pass);
		else if (expd_pass) {
			Tfds.Eq(expd, bfr.To_str_and_clear());
		}
	}
}
