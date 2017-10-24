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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
public class Gallery_parser_tst {
	@Before public void init() {fxt.Init();} private Gallery_parser_fxt fxt = new Gallery_parser_fxt();
	@Test   public void All()				{fxt.Test_parse("File:A.png|a|alt=b|link=c"		, fxt.Expd("File:A.png", "a" , "b" , "c"));}
	@Test   public void Ttl_only()			{fxt.Test_parse("File:A.png"					, fxt.Expd("File:A.png", null, null, null));}
	@Test   public void Ttl_url_encoded()	{fxt.Test_parse("File:A%28b%29.png"				, fxt.Expd("File:A(b).png"));}			// PURPOSE: handle url-encoded sequences; DATE:2014-01-01
	@Test   public void Caption_only()		{fxt.Test_parse("File:A.png|a"					, fxt.Expd("File:A.png", "a" , null, null));}
	@Test   public void Caption_many()		{fxt.Test_parse("File:A.png|a|b"				, fxt.Expd("File:A.png", "b"));}			// NOTE: keep last pipe
	@Test   public void Caption_many_lnki1(){fxt.Test_parse("File:A.png|a|[[b|c]]"			, fxt.Expd("File:A.png", "[[b|c]]"));}		// NOTE: ignore pipe in lnki
	@Test   public void Caption_many_lnki2(){fxt.Test_parse("File:A.png|[[b|c]]|d"			, fxt.Expd("File:A.png", "d"));}			// NOTE: ignore pipe in lnki
	@Test   public void Alt_only()			{fxt.Test_parse("File:A.png|alt=a"				, fxt.Expd("File:A.png", null, "a" , null));}
	@Test   public void Link_only()			{fxt.Test_parse("File:A.png|link=a"				, fxt.Expd("File:A.png", null, null, "a"));}
	@Test   public void Caption_alt()		{fxt.Test_parse("File:A.png|a|alt=b"			, fxt.Expd("File:A.png", "a" , "b"));}
	@Test   public void Alt_caption()		{fxt.Test_parse("File:A.png|alt=a|b"			, fxt.Expd("File:A.png", "b" , "a"));}
	@Test   public void Alt_blank()			{fxt.Test_parse("File:A.png|alt=|b"				, fxt.Expd("File:A.png", "b" , ""));}
	@Test   public void Alt_invalid()		{fxt.Test_parse("File:A.png|alta=b"				, fxt.Expd("File:A.png", "alta=b"));}	// NOTE: invalid alt becomes caption
	@Test   public void Ws()				{fxt.Test_parse("File:A.png| alt = b | c"		, fxt.Expd("File:A.png", "c" , "b"));}
	@Test   public void Ws_caption_many()	{fxt.Test_parse("File:A.png| a | b | c "		, fxt.Expd("File:A.png", "c"));}
	@Test   public void Page_pdf()			{fxt.Test_parse("File:A.pdf|page=1 "			, fxt.Expd("File:A.pdf", null, null, null, 1));}	// pdf parses page=1
	@Test   public void Page_png()			{fxt.Test_parse("File:A.png|page=1 "			, fxt.Expd("File:A.png", "page=1", null, null));}	// non-pdf treats page=1 as caption
	@Test   public void Page_invalid()		{fxt.Test_parse("|page=1");}	// check that null title doesn't fail; DATE:2014-03-21
	@Test   public void Skip_blank()		{fxt.Test_parse("");}
	@Test   public void Skip_empty()		{fxt.Test_parse("|File:A.png");}
	@Test   public void Skip_ws()			{fxt.Test_parse(" |File:A.png");}
	@Test   public void Skip_anchor()		{fxt.Test_parse("#a");}	// PURPOSE: anchor-like ttl should not render; ar.d:جَبَّارَة; DATE:2014-03-18			
	@Test   public void Many() {
		fxt.Test_parse("File:A.png\nFile:B.png"					, fxt.Expd("File:A.png"), fxt.Expd("File:B.png"));
	}
	@Test   public void Many_nl() {
		fxt.Test_parse("File:A.png\n\n\nFile:B.png"				, fxt.Expd("File:A.png"), fxt.Expd("File:B.png"));
	}
	@Test   public void Many_nl_w_tab() {
		fxt.Test_parse("File:A.png\n \t \nFile:B.png"			, fxt.Expd("File:A.png"), fxt.Expd("File:B.png"));
	}
	@Test   public void Many_invalid() {
		fxt.Test_parse("File:A.png\n<invalid>\nFile:B.png"		, fxt.Expd("File:A.png"), fxt.Expd("File:B.png"));
	}
	@Test   public void Caption_complicated() {
		fxt.Test_parse("File:A.png|alt=a|b[[c|d]]e ", fxt.Expd("File:A.png", "b[[c|d]]e", "a"));
	}
	@Test   public void Link_null() {	// PURPOSE: null link causes page to fail; EX: ru.w:Гянджа; <gallery>Datei:A.png|link= |</gallery>; DATE:2014-04-11
		fxt.Test_parse("File:A.png|link = |b", fxt.Expd("File:A.png", "b", null, null));
	}
	@Test   public void Caption_empty() {	// PURPOSE: check that empty ws doesn't break caption (based on Link_null); DATE:2014-04-11
		fxt.Test_parse("File:A.png|  |  | ", fxt.Expd("File:A.png", null, null, null));
	}
	@Test   public void Alt__magic_word_has_arg() {	// PURPOSE: img_alt magic_word is of form "alt=$1"; make sure =$1 is stripped for purpose of parser; DATE:2013-09-12
		fxt.Init_kwd_set(Xol_kwd_grp_.Id_img_alt, "alt=$1");
		fxt.Test_parse("File:A.png|alt=a|b", fxt.Expd("File:A.png", "b", "a"));
	}
}
class Gallery_parser_fxt {
	private Xoae_app app; private Xowe_wiki wiki;
	private Gallery_parser parser;
	public Gallery_parser_fxt Init() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		parser = new Gallery_parser();
		parser.Init_by_wiki(wiki);
		return this;
	}
	public String[] Expd(String ttl)													{return new String[] {ttl, null, null, null, null};}
	public String[] Expd(String ttl, String caption)									{return new String[] {ttl, caption, null, null, null};}
	public String[] Expd(String ttl, String caption, String alt)						{return new String[] {ttl, caption, alt, null, null};}
	public String[] Expd(String ttl, String caption, String alt, String link)			{return new String[] {ttl, caption, alt, link, null};}
	public String[] Expd(String ttl, String caption, String alt, String link, int page)	{return new String[] {ttl, caption, alt, link, Int_.To_str(page)};}
	public void Init_kwd_set(int kwd_id, String kwd_val) {
		wiki.Lang().Kwd_mgr().Get_or_new(kwd_id).Itms()[0].Val_(Bry_.new_a7(kwd_val));
		parser.Init_by_wiki(wiki);
	}
	public void Test_parse(String raw, String[]... expd) {
		List_adp actl = List_adp_.New();
		byte[] src = Bry_.new_a7(raw);
		parser.Parse_all(actl, Gallery_mgr_base_.New(Gallery_mgr_base_.Tid__traditional), new Gallery_xnde(), src, 0, src.length);
		Tfds.Eq_ary(String_.Ary_flatten(expd), String_.Ary_flatten(Xto_str_ary(src, actl)));
	}
	private String[][] Xto_str_ary(byte[] src, List_adp list) {
		int len = list.Count();
		String[][] rv = new String[len][];
		for (int i = 0; i < len; i++) {
			Gallery_itm itm = (Gallery_itm)list.Get_at(i);
			String[] ary = new String[5];
			rv[i] = ary;
			ary[0] = String_.new_u8(itm.Ttl().Full_txt_w_ttl_case());
			ary[2] = Xto_str_ary_itm(src, itm.Alt_bgn(), itm.Alt_end());
			ary[3] = Xto_str_ary_itm(src, itm.Link_bgn(), itm.Link_end());
			ary[4] = Xto_str_ary_itm(src, itm.Page_bgn(), itm.Page_end());
			byte[] caption = itm.Caption_bry();
			ary[1] =  caption == null ? null : String_.new_u8(caption);
		}
		return rv;
	}
	private String Xto_str_ary_itm(byte[] src, int bgn, int end) {
		return bgn == Bry_find_.Not_found && end == Bry_find_.Not_found ? null : String_.new_u8(src, bgn, end);
	}
}
