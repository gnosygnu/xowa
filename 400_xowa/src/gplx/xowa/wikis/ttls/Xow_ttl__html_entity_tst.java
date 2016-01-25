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
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xow_ttl__html_entity_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Eacute()		{fxt.Init_ttl("&eacute;").Expd_page_txt("é").Test();}	//É
	@Test   public void Amp_at_end()	{fxt.Init_ttl("Bisc &").Expd_page_txt("Bisc &").Test();}
	@Test   public void Ncr_dec()		{fxt.Init_ttl("A&#98;").Expd_page_txt("Ab").Test();}
	@Test   public void Ncr_hex()		{fxt.Init_ttl("A&#x62;").Expd_page_txt("Ab").Test();}
	// @Test   public void Ncr_hex_ns()	{fxt.Init_ttl("Help&#x3a;A").Expd_ns_id(Xow_ns_.Tid__help).Expd_page_txt("A").Test();}
	@Test   public void Nbsp()			{fxt.Init_ttl("A&nbsp;b").Expd_page_txt("A b").Test();}	// NOTE: &nbsp must convert to space; EX:w:United States [[Image:Dust Bowl&nbsp;- Dallas, South Dakota 1936.jpg|220px|alt=]]
	@Test   public void Amp()			{fxt.Init_ttl("A&amp;b").Expd_page_txt("A&b").Test();}	// PURPOSE: A&amp;B -> A&B; PAGE:en.w:Amadou Bagayoko?redirect=n; DATE:2014-09-23
}