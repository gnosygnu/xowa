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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.nss.*;
public class Xob_xml_dumper_tst {
	private final Xob_xml_dumper_fxt fxt = new Xob_xml_dumper_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Test_page(fxt.Make_ary(fxt.Make_page(1, Xow_ns_.Tid__main, "A", "A_text")), String_.Concat_lines_nl_skip_last
		( "<mediawiki xmlns='http://www.mediawiki.org/xml/export-0.10/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.mediawiki.org/xml/export-0.10/ http://www.mediawiki.org/xml/export-0.10.xsd' version='0.10' xml:lang='en'>"
		, "  <siteinfo>"
		, "    <sitename>other</sitename>"
		, "    <dbname></dbname>"
		, "    <base>Main_Page</base>"
		, "    <generator>XOWA 2.5.2.2</generator>"
		, "    <case>first-letter</case>"
		, "  </siteinfo>"
		, "  <namespaces>"
		, "    <namespace key='-2' case='first-letter'>Media</namespace>"
		, "    <namespace key='-1' case='first-letter'>Special</namespace>"
		, "    <namespace key='0' case='first-letter'></namespace>"
		, "    <namespace key='1' case='first-letter'>Talk</namespace>"
		, "    <namespace key='2' case='first-letter'>User</namespace>"
		, "    <namespace key='3' case='first-letter'>User_talk</namespace>"
		, "    <namespace key='4' case='first-letter'>Wikipedia</namespace>"
		, "    <namespace key='5' case='first-letter'>Wikipedia_talk</namespace>"
		, "    <namespace key='6' case='first-letter'>File</namespace>"
		, "    <namespace key='7' case='first-letter'>File_talk</namespace>"
		, "    <namespace key='8' case='first-letter'>MediaWiki</namespace>"
		, "    <namespace key='9' case='first-letter'>MediaWiki_talk</namespace>"
		, "    <namespace key='10' case='first-letter'>Template</namespace>"
		, "    <namespace key='11' case='first-letter'>Template_talk</namespace>"
		, "    <namespace key='12' case='first-letter'>Help</namespace>"
		, "    <namespace key='13' case='first-letter'>Help_talk</namespace>"
		, "    <namespace key='14' case='first-letter'>Category</namespace>"
		, "    <namespace key='15' case='first-letter'>Category_talk</namespace>"
		, "    <namespace key='100' case='first-letter'>Portal</namespace>"
		, "    <namespace key='101' case='first-letter'>Portal_talk</namespace>"
		, "    <namespace key='108' case='first-letter'>Book</namespace>"
		, "    <namespace key='109' case='first-letter'>Book_talk</namespace>"
		, "    <namespace key='828' case='first-letter'>Module</namespace>"
		, "    <namespace key='829' case='first-letter'>Module_talk</namespace>"
		, "  </namespaces>"
		, "  <page>"
		, "    <title>A</title>"
		, "    <id>1</id>"
		, "    <revision>"
		, "      <id>-1</id>"
		, "      <parent>-1</parent>"
		, "      <timestamp>0001-01-01 00:00:00</timestamp>"
		, "      <contributor>"
		, "        <username></username>"
		, "        <id>-1</id>"
		, "      </contributor>"
		, "      <comment></comment>"
		, "      <model>wikitext</model>"
		, "      <format>text/x-wiki</format>"
		, "      <text xml:space='preserve'>A_text</text>"
		, "      <sha1></sha1>"
		, "    </revision>"
		, "  </page>"
		, "</mediawiki>"
		));
	}
}
class Xob_xml_dumper_fxt {
	private Xowe_wiki wiki;
	private final Xob_xml_dumper export_wtr = new Xob_xml_dumper();
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app, "enwiki");
	}
	public Xowd_page_itm[] Make_ary(Xowd_page_itm... ary) {return ary;}
	public Xowd_page_itm Make_page(int id, int ns_id, String ttl_str, String text) {
		Xoa_ttl ttl = wiki.Ttl_parse(ns_id, Bry_.new_u8(ttl_str));
		return new Xowd_page_itm().Id_(id).Ns_id_(ns_id).Ttl_(ttl).Text_(Bry_.new_u8(text));
	}
	public void Test_page(Xowd_page_itm[] ary, String expd) {
		export_wtr.Write_root_bgn(wiki.Ns_mgr(), wiki.Domain_itm(), "", String_.new_u8(wiki.Props().Main_page()), "first-letter", "XOWA 2.5.2.2");
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			export_wtr.Write_page(ary[i]);
		export_wtr.Write_root_end();
		String actl = export_wtr.Bld_str();
		Tfds.Eq_str_lines(expd, actl);
	}
}
