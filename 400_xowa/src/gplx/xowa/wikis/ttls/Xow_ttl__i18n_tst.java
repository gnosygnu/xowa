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
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xow_ttl__i18n_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Bidi() {	// PURPOSE: handle bidirectional characters; DATE:2015-07-28; DATE:2015-08-24
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0x8E)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0x8F)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0xAA)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0xAB)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0xAC)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0xAD)) + "B").Expd_page_txt("AB").Test();
		fxt.Init_ttl("A" + String_.new_u8(Bry_.New_by_ints(0xE2, 0x80, 0xAE)) + "B").Expd_page_txt("AB").Test();
	}
	@Test   public void Multi_byte_char2() { // PURPOSE: multi-byte HTML entity causes array out of index error; EX: w:List_of_Unicode_characters; DATE:2013-12-25
		fxt.Init_ttl("&#x2c65;").Expd_full_txt("ⱥ").Test();
	}
	@Test   public void First_char_is_multi_byte() {	// PURPOSE: if multi-byte, uppercasing is complicated; EX: µ -> Μ; DATE:2013-11-27
		fxt.Wiki().Lang().Case_mgr_u8_();
		fxt.Init_ttl("µ").Expd_full_txt("Μ").Test();						// NOTE: this is not an ASCII "Μ"
		fxt.Init_ttl("µab").Expd_full_txt("Μab").Test();					// check that rest of title works fine
		fxt.Init_ttl("Help:µab").Expd_full_txt("Help:Μab").Test();		// check ns
		fxt.Init_ttl("Ι").Expd_full_txt("Ι").Test();						// check that Ι is not upper-cased to COMBINING GREEK YPOGEGRAMMENI; DATE:2014-02-24
	}
	@Test   public void First_char_is_multi_byte_assymetrical() { // PURPOSE: test multi-byte asymmetry (lc is 3 bytes; uc is 2 bytes)
		fxt.Wiki().Lang().Case_mgr_u8_();
		fxt.Init_ttl("ⱥ").Expd_full_txt("Ⱥ").Test();
		fxt.Init_ttl("ⱥab").Expd_full_txt("Ⱥab").Test();				// check that rest of title works fine
		fxt.Init_ttl("Help:ⱥab").Expd_full_txt("Help:Ⱥab").Test();	// check ns
	}
	@Test   public void Ws__basic() { // PURPOSE: replace other whitespace with underscore; PAGE:ja.w:Template:Location_map_USA　New_York; DATE:2015-07-28
		fxt.Init_ttl("A　B").Expd_full_txt("A B").Test();
	}
	@Test   public void Ws__many() { // PURPOSE: replace other whitespace with underscore; PAGE:ja.w:Template:Location_map_USA　New_York; DATE:2015-07-28
		fxt.Init_ttl("A\u00A0\u1680\u180E\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u2028\u2029\u202F\u205F\u3000B").Expd_full_txt("A B").Test();
	}
}
