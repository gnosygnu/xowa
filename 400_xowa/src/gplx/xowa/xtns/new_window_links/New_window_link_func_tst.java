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
package gplx.xowa.xtns.new_window_links; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.brys.*; import gplx.xowa.wikis.pages.skins.*;
public class New_window_link_func_tst {
	@Before public void init()						{fxt.Reset();} private final Xop_fxt fxt = Xop_fxt.new_nonwmf();
	@Test  public void Lnki__none()					{fxt.Test__parse__tmpl_to_html("{{#NewWindowLink:A}}"					, "<a href='/wiki/A'>A</a>");}
	@Test  public void Lnki__caption()				{fxt.Test__parse__tmpl_to_html("{{#NewWindowLink:A|B}}"					, "<a href='/wiki/A'>B</a>");}
	@Test  public void Lnke__none()					{fxt.Test__parse__tmpl_to_html("{{#NewWindowLink:https://a.org}}"		, "<a href='https://a.org' rel='nofollow' class='external free'>https://a.org</a>");}
	@Test  public void Lnke__caption()				{fxt.Test__parse__tmpl_to_html("{{#NewWindowLink:https://a.org|A}}"		, "<a href='https://a.org' rel='nofollow' class='external text'>A</a>");}
}
