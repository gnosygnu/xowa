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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xoi_mirror_parser_tst {
	@Test  public void Basic() {
		Tst_parse(String_.Concat_lines_nl
		(	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		,	"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">"
		,	"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">"
		,	"<head>"
		,	"<title>Index of /simplewiki/</title>"
		,	"<link rel=\"stylesheet\" type=\"text/css\" href=\"/pub/misc/lighttpd-white-dir.css\" />"
		,	"</head>"
		,	"<body>"
		,	"<h2>Index of /simplewiki/</h2>"
		,	"<div class=\"list\">"
		,	"<table summary=\"Directory Listing\" cellpadding=\"0\" cellspacing=\"0\">"
		,	"<thead><tr><th class=\"n\">Name</th><th class=\"m\">Last Modified</th><th class=\"s\">Size</th><th class=\"t\">Type</th></tr></thead>"
		,	"<tbody>"
		,	"<tr><td class=\"n\"><a href=\"../\">Parent Directory</a>/</td><td class=\"m\">&nbsp;</td><td class=\"s\">- &nbsp;</td><td class=\"t\">Directory</td></tr>"
		,	"<tr><td class=\"n\"><a href=\"20120516/\">20120516</a>/</td><td class=\"m\">2012-May-17 01:04:39</td><td class=\"s\">- &nbsp;</td><td class=\"t\">Directory</td></tr>"
		,	"<tr><td class=\"n\"><a href=\"20121220/\">20121220</a>/</td><td class=\"m\">2012-Dec-20 20:15:55</td><td class=\"s\">- &nbsp;</td><td class=\"t\">Directory</td></tr>"
		,	"<tr><td class=\"n\"><a href=\"20130214/\">20130214</a>/</td><td class=\"m\">2013-Feb-14 06:28:41</td><td class=\"s\">- &nbsp;</td><td class=\"t\">Directory</td></tr>"
		,	"<tr><td class=\"n\"><a href=\"latest/\">latest</a>/</td><td class=\"m\">2013-Feb-14 06:28:41</td><td class=\"s\">- &nbsp;</td><td class=\"t\">Directory</td></tr>"
		,	"</tbody>"
		,	"</table>"
		,	"</div>"
		,	"<div class=\"foot\">lighttpd</div>"
		,	"</body>"
		,	"</html>"
		), String_.Ary("20120516", "20121220", "20130214", "latest"));
	}
	@Test  public void Find_last_lte() {
		Tst_find_last_lte(String_.Ary("20120516", "20121220", "20130214", "latest"), "20130101", "20121220");
		Tst_find_last_lte(String_.Ary("20120516", "20121220", "20130214", "latest"), "20120101", "");
	}
	private void Tst_parse(String raw, String[] expd) {
		Xoi_mirror_parser parser = new Xoi_mirror_parser();
		Tfds.Eq_ary_str(expd, parser.Parse(raw));
	}
	private void Tst_find_last_lte(String[] ary, String comp, String expd) {
		Tfds.Eq(expd, Xoi_mirror_parser.Find_last_lte(ary, comp));
	}
}
