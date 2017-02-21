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
public class Xow_ttl__basic_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Ns()						{fxt.Init_ttl("Help:Test")		.Expd_ns_id(Xow_ns_.Tid__help).Expd_page_txt("Test").Test();}
	@Test   public void Ns_false()					{fxt.Init_ttl("Helpx:Test")		.Expd_ns_id(Xow_ns_.Tid__main).Expd_page_txt("Helpx:Test").Test();}
	@Test   public void Ns_multiple()				{fxt.Init_ttl("Help:Talk:test")	.Expd_page_txt("Talk:test").Test();}
	@Test   public void Full_txt()					{fxt.Init_ttl("Help:a & b")		.Expd_full_txt("Help:A & b").Test();}					// preserve
	@Test   public void Full_url()					{fxt.Init_ttl("Help:a & b")		.Expd_full_url("Help:A_%26_b").Test();}					// escape
	@Test   public void Page_txt()					{fxt.Init_ttl("a & b")			.Expd_page_txt("A & b").Test();}						// preserve;
	@Test   public void Page_txt_underline()		{fxt.Init_ttl("a_b")			.Expd_page_txt("A b").Expd_page_url("A_b").Test();}		// NOTE: raw is "a_b" but txt is "a b"
	@Test   public void Page_url()					{fxt.Init_ttl("a & b")			.Expd_page_url("A_%26_b").Test();}						// escape
	@Test   public void Page_url_w_ns()				{fxt.Init_ttl("Help:a & b")		.Expd_page_url("A_%26_b").Test();}						// remove ns
	@Test   public void Page_url_symbols()			{fxt.Init_ttl(";:@&=$ -_.+!*'(),/").Expd_page_url(";:@%26%3D$_-_.%2B!*%27(),/").Test();}// symbols + space
	@Test   public void Leaf_txt()					{fxt.Init_ttl("root/mid/leaf")	.Expd_leaf_txt("leaf").Test();}
	@Test   public void Leaf_txt_slash_is_last()	{fxt.Init_ttl("root/mid/leaf/")	.Expd_leaf_txt("").Test();}
	@Test   public void Leaf_txt_no_slash()			{fxt.Init_ttl("root")			.Expd_leaf_txt("Root").Test();}
	@Test   public void Leaf_url()					{fxt.Init_ttl("root/mid/a & b")	.Expd_leaf_url("a_%26_b").Test();}						// NOTE: a not capitalized ("root" would be)
	@Test   public void Base_txt()					{fxt.Init_ttl("a & b/mid/leaf")	.Expd_base_txt("A & b/mid").Test();}
	@Test   public void Base_url()					{fxt.Init_ttl("a & b/mid/leaf")	.Expd_base_url("A_%26_b/mid").Test();}
	@Test   public void Root_txt()					{fxt.Init_ttl("root/mid/leaf")	.Expd_root_txt("Root").Test();}
	@Test   public void Rest_txt()					{fxt.Init_ttl("root/mid/leaf")	.Expd_rest_txt("mid/leaf").Test();}
	@Test   public void Talk_txt()					{fxt.Init_ttl("Help:test")		.Expd_talk_txt("Help talk:Test").Test();}
	@Test   public void Talk_txt_identity()			{fxt.Init_ttl("Help talk:test")	.Expd_talk_txt("Help talk:Test").Test();}
	@Test   public void Talk_url()					{fxt.Init_ttl("Help:a & b")		.Expd_talk_url("Help_talk:A_%26_b").Test();}
	@Test   public void Talk_txt_main()				{fxt.Init_ttl("test")			.Expd_talk_txt("Talk:Test").Test();}
	@Test   public void Subj_txt()					{fxt.Init_ttl("Help talk:test")	.Expd_subj_txt("Help:Test").Test();}
	@Test   public void Subj_txt_identity()			{fxt.Init_ttl("Help:test")		.Expd_subj_txt("Help:Test").Test();}
	@Test   public void Subj_url()					{fxt.Init_ttl("Help talk:a & b").Expd_subj_url("Help:A_%26_b").Test();}
	@Test   public void Subj_txt_main()				{fxt.Init_ttl("Talk:test")		.Expd_subj_txt("Test").Test();}
	@Test   public void Force_literal_link_y()		{fxt.Init_ttl(":Help:test")		.Expd_force_literal_link(1).Expd_page_txt("Test").Test();}
	@Test   public void Force_literal_link_n()		{fxt.Init_ttl( "Help:test")		.Expd_force_literal_link(0).Expd_page_txt("Test").Test();}
	@Test   public void Force_literal_link_y_2()	{fxt.Init_ttl("::Help:test")	.Expd_force_literal_link(1).Expd_page_txt("Test").Test();}	// PURPOSE: 2nd initial colon should be ignored; EX:mw:MediaWiki; [[::MediaWiki]]; DATE:2013-12-14
	@Test   public void All_page()					{fxt.Init_ttl("test")			.Expd_xwik_txt("").Expd_ns_id(Xow_ns_.Tid__main).Expd_page_txt("Test").Expd_leaf_txt("Test").Expd_anch_txt("").Test();}
	@Test   public void All_ns()					{fxt.Init_ttl("Help:test")		.Expd_xwik_txt("").Expd_ns_id(Xow_ns_.Tid__help).Expd_page_txt("Test").Expd_leaf_txt("Test").Expd_anch_txt("").Test();}
	@Test   public void Special()					{fxt.Init_ttl("Special:Random").Expd_ns_id(Xow_ns_.Tid__special).Expd_page_txt("Random").Test();}
	@Test   public void Special_xowa()				{fxt.Init_ttl("Special:xowa/Search/Ttl").Expd_ns_id(Xow_ns_.Tid__special).Expd_page_txt("Xowa/Search/Ttl").Test();}
	@Test   public void Comment()					{fxt.Init_ttl("Ab<!--b-->").Expd_page_txt("Ab").Test();}
	@Test   public void Comment_eos()				{fxt.Init_ttl("Ab<!--b--").Expd_page_txt(null).Test();}
	@Test   public void Ns_case() {// PURPOSE: lowercase ns should be converted to proper case; EX: fr.w:Project:Sandbox (redirect link); en.w:Periclimenes imperator; [[commons:category:Periclimenes imperator|''Periclimenes imperator'']]; DATE: 2013-01-27
		fxt.Init_ttl("help:A").Expd_full_txt("Help:A").Test();
		fxt.Init_ttl("help talk:A").Expd_full_txt("Help talk:A").Test();
	}
}
