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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_ttl__ws_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Space()					{fxt.Init_ttl("  a  b  ")			.Expd_page_url("A_b").Expd_page_txt("A b").Test();}
	@Test   public void Space_w_ns()			{fxt.Init_ttl("  Help  :  a  b  ")	.Expd_page_url("A_b").Expd_page_txt("A b").Test();}
	@Test   public void Nl()					{fxt.Init_ttl("\n\na\n\n")			.Expd_page_txt("A").Test();}
	@Test   public void Nl_end()				{fxt.Init_ttl("a\nb")				.Expd_page_txt("A b").Test();}
	@Test   public void Tab()					{fxt.Init_ttl("\ta\t")				.Expd_page_txt("A").Test();}
	@Test   public void Nbsp()					{fxt.Init_ttl("A&nbsp;bc")			.Expd_page_url("A_bc").Expd_page_txt("A bc").Test();}	// PURPOSE:convert "&nbsp;" to " "; DATE:2014-09-25
	@Test   public void Nbsp_mix()				{fxt.Init_ttl("A &nbsp; bc")		.Expd_page_url("A_bc").Expd_page_txt("A bc").Test();}	// PURPOSE:convert multiple "&nbsp;" to " "; PAGE:en.w:Greek_government-debt_crisis; DATE:2014-09-25
}
