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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xow_url_parser__proto_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Relative() {
		tstr.Exec__parse("//en.wikipedia.org/wiki/A").Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void Http__basic() {
		tstr.Exec__parse("http://en.wikipedia.org/wiki/A").Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void Upload__basic() { 
		tstr.Prep_add_xwiki_to_user("commons.wikimedia.org");	// NOTE: need to add xwiki to be able to resolve "/commons/"
		tstr.Exec__parse("http://upload.wikimedia.org/wikipedia/commons/a/ab/C.svg").Test__wiki("commons.wikimedia.org").Test__page("File:C.svg");	// orig
		tstr.Exec__parse("http://upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220px-A.png").Test__wiki("commons.wikimedia.org").Test__page("File:A.png"); // thum
	}
	@Test  public void File__basic() {
		tstr.Exec__parse("file:///C:/a/b/c").Test__tid(Xoa_url_.Tid_file);
	}
	@Test  public void Ftp__basic() {
		tstr.Exec__parse("ftp://en.wikipedia.org/wiki/A").Test__tid(Xoa_url_.Tid_inet);
	}
	@Test  public void Extended() {
		tstr.Exec__parse("http://en.wikipedia.org/w/index.php?A=B").Test__wiki("en.wikipedia.org").Test__page("index.php").Test__qargs("?A=B").Test__anch(null);
	}
}
