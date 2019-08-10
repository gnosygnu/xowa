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
public class Http_server_wkr_tst {
	private final    Http_server_wkr_fxt fxt = new Http_server_wkr_fxt();
	@Test   public void File_bgn_missing() { // "file:" missing
		fxt.Test__Replace_fsys_hack("src='file////home/lnxusr/xowa/file/'");
	}
	@Test   public void File_bgn_at_bos() { // "file:" at start of page
		fxt.Test__Replace_fsys_hack("file:////home/lnxusr/xowa/file/");
	}
	@Test   public void Quote_bgn_missing() {
		fxt.Test__Replace_fsys_hack("(file:////home/lnxusr/xowa/file/)");
	}
	@Test   public void Quote_end_missing() {
		fxt.Test__Replace_fsys_hack("a'file:////home/lnxusr/xowa/file/");
	}
	@Test   public void Too_long() { // skip if too long
		fxt.Test__Replace_fsys_hack("'file:" + String_.Repeat("a", 301) + "'");
	}
	@Test   public void File_mid_missing() { // skip if no /file/
		fxt.Test__Replace_fsys_hack("'file:////home/lnxusr/xowa/file_missing/'");
	}
	@Test   public void One() {
		fxt.Test__Replace_fsys_hack("'file:////home/lnxusr/xowa/file/A.png'", "'/fsys/file/A.png'");
	}
	@Test   public void Many() {
		fxt.Test__Replace_fsys_hack("a 'file:////home/lnxusr/xowa/file/A.png' b \"file:////home/lnxusr/xowa/file/B.png\" c", "a '/fsys/file/A.png' b \"/fsys/file/B.png\" c");
	}
}
class Http_server_wkr_fxt {
	public void Test__Replace_fsys_hack(String html)              {Test__Replace_fsys_hack(html, html);}
	public void Test__Replace_fsys_hack(String html, String expd) {
		String actl = Http_server_wkr.Replace_fsys_hack(html);
		Gftest.Eq__ary__lines(expd, actl);
	}
}
