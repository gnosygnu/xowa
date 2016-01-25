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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.nss.*;
public class Xoh_img_hzip__dump__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%!!A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Anch() {	// [[File:A.png#b|abc]]
		fxt.Test__bicode
		( "~%\"<A.png#file~A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png#file' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding_foreign() {	// [[File:Aéb.png|abc]]
		fxt.Test__bicode
		( "~%\"<A%C3%A9b.png~Aéb.png~)#Sabc~"
		, "<a href='/wiki/File:A%C3%A9b.png' class='image' xowa_title='Aéb.png'><img data-xowa-title='Aéb.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding_question() {	// [[File:A?.png|abc]]; PAGE:en.w:Voiceless_alveolar_affricate; DATE:2016-01-04
		fxt.Test__bicode
		( "~%!qA?.png~)#Sabc~"
		, "<a href='/wiki/File:A?.png' class='image' xowa_title='A?.png'><img data-xowa-title='A?.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding__link() {	// [[File:Aéb.png|abc|link=Aéb]]
		fxt.Test__bicode
		( "~%#gA%C3%A9b~Aéb.png~#)#Sabc~"
		, "<a href='/wiki/A%C3%A9b' class='image' xowa_title='Aéb.png'><img data-xowa-title='Aéb.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding_percent() {
		fxt.Test__bicode
		( "~%!q%24%3F%3D%27.png~)#Sabc~"
		, "<a href='/wiki/File:%24%3F%3D%27.png' class='image' xowa_title='%24%3F%3D%27.png'><img data-xowa-title='%24%3F%3D%27.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__apos() {		// [[File:A'b.png|border|link=A'b_link|A'b_capt]]
		String html = "<a href=\"/wiki/A%27b_link\" class=\"image\" xowa_title=\"A'b.png\"><img data-xowa-title=\"A'b.png\" data-xoimg=\"0|220|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" class=\"thumbborder\" alt=\"A'b_capt\"></a>";
		fxt.Test__bicode_raw("~%#oA%27b_link~A'b.png~#)#SA'b_capt~", html, html);
	}
	@Test   public void Href__image() {	// [[Image:A.png|abc]]
		fxt.Test__bicode
		( "~%-%A.png~Image~)#Sabc~"
		, "<a href='/wiki/Image:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Ns__cs() {	// [[image:a.png|abc]]; PAGE:en.d:freedom_of_speech DATE:2016-01-21
		fxt.Wiki().Ns_mgr().Ns_file().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__bicode
		( "~%-%a.png~image~)#Sabc~"
		, "<a href='/wiki/image:a.png' class='image' title='abc' xowa_title='a.png'><img data-xowa-title='a.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
		fxt.Wiki().Ns_mgr().Ns_file().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__1st);
	}
	@Test   public void Ns__foreign() {	// [[Fichier:a.png|abc]]; PAGE:fr.w: DATE:2016-01-21
		Xow_ns_mgr ns_mgr = fxt.Wiki().Ns_mgr();
		ns_mgr.Ns_file().Name_bry_(Bry_.new_u8("Fichier")); ns_mgr.Init_w_defaults();
		fxt.Test__bicode
		( "~%!!a.png~)#Sabc~"
		, "<a href='/wiki/Fichier:a.png' class='image' title='abc' xowa_title='a.png'><img data-xowa-title='a.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
		ns_mgr.Ns_file().Name_bry_(Bry_.new_u8("File")); ns_mgr.Init_w_defaults();
	}
	@Test   public void Link__cs() {	// [[File:A.png|link=File:a.ogg|abc]]
		fxt.Test__bicode
		( "~%!Aa.ogg~A.png~)#Sabc~"
		, "<a href='/wiki/File:a.ogg' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__anchor() {	// [[File:A.png|link=A#B_C|abc]]; PAGE:en.w:Arabic; DATE:2016-01-06
		fxt.Test__bicode
		( "~%#'A#B_C~D.png~#9!I!I"
		, "<a href='/wiki/A#B_C' class='image' xowa_title='D.png'><img data-xowa-title='D.png' data-xoimg='0|40|40|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
		);
	}
	@Test   public void Link__wm__n() {	// [[File:A.png|link=http://a.org|abc]]
		fxt.Test__bicode
		( "~%!Dhttp://a.org~A.png~)#Sabc~"
		, "<a href='http://a.org' rel='nofollow' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__wm__y() {	// [[File:A.png|link=http://en.wikitionary.org/wiki/Special:Search/A|abc]]
		fxt.Test__bicode
		( "~%\"men.wiktionary.org|Search/A~A.png~\")#Sabc~"
		, "<a href='/site/en.wiktionary.org/wiki/Special:Search/A' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__wm__n_2() {	// [[File:A.png|link=creativecommons:by/2.5]]
		fxt.Test__bicode
		( "~%#(creativecommons.org|by/2.5/~CC-BY-icon-80x15.png~#)!q"
		, "<a href='/site/creativecommons.org/wiki/by/2.5/' class='image' xowa_title='CC-BY-icon-80x15.png'><img data-xowa-title='CC-BY-icon-80x15.png' data-xoimg='0|80|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
		);
	}
	@Test   public void Link__media() {	// [[File:A.png|link=file:///C:/A.ogg]]
		fxt.Test__bicode
		( "~%!D~A.ogg~)!,B~"
		, "<a href='' class='image' title='B' xowa_title='A.ogg'><img data-xowa-title='A.ogg' data-xoimg='0|11|-1|-1|-1|-1' src='' width='0' height='0' alt='B'></a>"
		);
	}
	@Test   public void Link__encoding() {	// [[File:A.svg|24px|text-top|link=wikt:𬖾|𬖾]]; PAGE:en.w:Pho; DATE:2016-01-04
		fxt.Test__bicode
		( "~%#Xen.wiktionary.org|%F0%AC%96%BE~A.png~#)!,abc~B~"
		, "<a href='/site/en.wiktionary.org/wiki/%F0%AC%96%BE' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|11|-1|-1|-1|-1' src='' width='0' height='0' alt='B'></a>"
		);
	}
	@Test   public void Link__invalid() {	// handle invalid titles in link arg; EX:[[File:A.png|link=wikt:]]; PAGE:en.w:List_of_Saint_Petersburg_Metro_stations; DATE:2016-01-04
		fxt.Test__bicode
		( "~%\"mcommons.wikimedia.org|~A.png~1!Abc~"
		, "<a href='/site/commons.wikimedia.org/wiki/Category:' class='image' title='Abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|-1|-1|-1|-1|-1' src='' width='0' height='0' alt='Abc'></a>"
		);
	}
	@Test  public void Link__empty() {	// empty link should not create anchor; EX:[[File:A.png|link=|abc]]; PAGE:en.w:List_of_counties_in_New_York; DATE:2016-01-10
		fxt.Test__bicode
		( "~%|iVPA.png~#9#S\":abc~"
		, "<img data-xowa-title='A.png' data-xoimg='0|220|110|-1|-1|-1' src='' width='0' height='0' alt='abc'>"
		);
	}
	@Test  public void Link__empty__tidy() {// <a><font><img> should not trigger empty link code; PAGE:en.w:Wikipedia:Reference_Desk_archive_unanswered_2005 ; DATE:2016-01-13
		fxt.Test__bicode
		( "<a href=\"/wiki/Image:A.png\" class=\"image\" title=\"\" xowa_title=\"A.png\"><font style=\"color:red\">~%|iVPA.png~#!abc~</font></a>"
		, "<a href='/wiki/Image:A.png' class='image' title='' xowa_title='A.png'><font style='color:red'><img data-xowa-title='A.png' data-xoimg='0|-1|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></font></a>"
		);
	}
	@Test   public void Link__ns_alias() {	// [[File:A.png|link=WP:MCB]]; PAGE:en.w:Wikipedia:WikiProject_Molecular_and_Cell_Biology; DATE:2016-01-11
		fxt.Init__ns_alias__add("WP", Xow_ns_.Tid__project);
		fxt.Test__bicode
		( "~%/+MCB~A.png~'WP~)!q"
		, "<a href='/wiki/WP:MCB' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|80|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
		);
		fxt.Init__ns_alias__del("WP");
	}
	@Test   public void Link__xwiki_lc() {	// [[File:A.png|link=wikt:Category:en:A]]; PAGE:en.w:Portal:Trucks/Wikimedia; DATE:2016-01-11
		fxt.Test__bicode
		( "~%#(en.wiktionary.org|en:A~A.png~1)!q"
		, "<a href='/site/en.wiktionary.org/wiki/Category:en:A' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|80|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
		);
	}
	@Test   public void Missing() { // PURPOSE: bad dump shouldn't write corrupt data
		fxt.Test__bicode
		( "%|\"\\QA.png!!!!A"
		, "<a href='/wiki/File:A.png' class='image' title='A' xowa_title='A.png'><img data-xowa-title='A.png' alt='A'></a>"
		);
	}
	@Test   public void Manual_img_cls() {	// PURPOSE: handle manual class; EX: [[File:A.png|class=noviewer]] en.w:ASCII; DATE:2015-12-21
		fxt.Test__bicode
		( "~%95A.png~)#Sabc~cls1~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='cls1' alt='abc'></a>"
		);
	}
	@Test   public void Video() {	// [[File:A.ogv]]
		fxt.Test__bicode
		( "%|E9eA.ogv~!A.ogv~~", String_.Concat_lines_nl_skip_last
		( "<div class='xowa_media_div'>"
		, "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img data-xowa-title='A.ogv' data-xoimg='0|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "<div><a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
		, "</div>"
		));
	}
	@Test   public void Imap() {
		fxt.Test__bicode
		( "~%}#Pa$A.png~#:#S#+\""
		, "<img data-xowa-title='A.png' data-xoimg='1|220|180|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt='' usemap='#imagemap_1_1'>"
		);
	}
}
