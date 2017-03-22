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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
public class Xoh_page_html_source_ {
	public static Xoh_page_html_source
	  Wtr  = new Xoh_page_html_source__wtr()
	, Noop = new Xoh_page_html_source__noop()
	;
}
class Xoh_page_html_source__wtr implements Xoh_page_html_source {
	public byte[] Get_page_html() {return null;}
}
class Xoh_page_html_source__noop implements Xoh_page_html_source {
	public byte[] Get_page_html() {return null;}
}
