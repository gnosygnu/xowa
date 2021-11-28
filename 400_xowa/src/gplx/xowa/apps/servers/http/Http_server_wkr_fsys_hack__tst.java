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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import org.junit.*; import gplx.core.tests.*;
public class Http_server_wkr_fsys_hack__tst {
	private final Http_server_wkr_fsys_hack__fxt fxt = new Http_server_wkr_fsys_hack__fxt();
	@Test  public void File_bgn_missing() { // "file:" missing
		fxt.Test__Replace_fsys_hack("src='file////home/lnxusr/xowa/file/'");
	}
	@Test  public void File_bgn_at_bos() { // "file:" at start of page
		fxt.Test__Replace_fsys_hack("file:////home/lnxusr/xowa/file/");
	}
	@Test  public void Quote_bgn_missing() {
		fxt.Test__Replace_fsys_hack("<file:////home/lnxusr/xowa/file/>");
	}
	@Test  public void Quote_end_missing() {
		fxt.Test__Replace_fsys_hack("a'file:////home/lnxusr/xowa/file/");
	}
	@Test  public void Too_long() { // skip if too long
		fxt.Test__Replace_fsys_hack("'file:" + String_.Repeat("a", 301) + "'");
	}
	@Test  public void File_mid_missing() { // skip if no /file/
		fxt.Test__Replace_fsys_hack("'file:////home/lnxusr/xowa/file_missing/'");
	}
	@Test  public void File__one() {
		fxt.Test__Replace_fsys_hack("'file:////home/lnxusr/xowa/file/A.png'", "'/fsys/file/A.png'");
	}
	@Test  public void File__many() {
		fxt.Test__Replace_fsys_hack("a 'file:////home/lnxusr/xowa/file/A.png' b 'file:////home/lnxusr/xowa/file/B.png' c", "a '/fsys/file/A.png' b '/fsys/file/B.png' c");
	}
	@Test  public void Quote() {
		fxt.Test__Replace_fsys_hack("a \"file:////home/lnxusr/xowa/file/A.png\" b", "a \"/fsys/file/A.png\" b");
	}
	@Test  public void Ws() {
		fxt.Test__Replace_fsys_hack("a file:////home/lnxusr/xowa/file/A.png\nb", "a /fsys/file/A.png\nb");
	}
	@Test  public void Bin_any() {
		fxt.Test__Replace_fsys_hack("a 'file:///C:/xowa/bin/any/xowa/file/app.window/app_icon.png' b", "a '/fsys/bin/any/xowa/file/app.window/app_icon.png' b");
	}
	@Test  public void Url() {
		fxt.Test__Replace_fsys_hack("url(file:///C:/xowa/user/anonymous/wiki/en.wikipedia.org/html/logo.png)", "url(/fsys/user/anonymous/wiki/en.wikipedia.org/html/logo.png)");
	}
}
class Http_server_wkr_fsys_hack__fxt {
	public void Test__Replace_fsys_hack(String html) {Test__Replace_fsys_hack(html, html);}
	public void Test__Replace_fsys_hack(String html, String expd) {
		byte[] actl = Http_server_wkr_fsys_hack_.Replace(Bry_.new_u8(html));
		Gftest.Eq__ary__lines(expd, actl);
	}
}
