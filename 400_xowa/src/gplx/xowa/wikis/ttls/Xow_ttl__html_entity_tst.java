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