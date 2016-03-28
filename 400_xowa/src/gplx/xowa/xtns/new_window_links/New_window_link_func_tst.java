/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
