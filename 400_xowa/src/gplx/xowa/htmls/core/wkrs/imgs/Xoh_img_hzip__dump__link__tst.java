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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.nss.*;
public class Xoh_img_hzip__dump__link__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
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
	@Test   public void Link__wm__n__encoded() {	// PURPOSE: do not double-encode A%C3%A9b.org; [[File:A.png|link=http://Aéb.org|abc]]
		fxt.Test__bicode
		( "~%!Dhttp://A%C3%A9b.org~A.png~)#Sabc~"
		, "<a href='http://A%C3%A9b.org' rel='nofollow' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
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
	@Test   public void Link__xwiki_under() {// EX: [[File:A.png|link=User talk:B]]; PAGE:de.b:Wikibooks:Benutzersperrung/_InselFahrer DATE:2016-06-24
		fxt.Test__bicode
		( "~%/,meta.wikimedia.org|B~A.png~&User Talk~!", String_.Concat
		( "<a href='/site/meta.wikimedia.org/wiki/User_Talk:B' class='image' xowa_title='A.png'>"
		,   "<img data-xowa-title='A.png' data-xoimg='0|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''>"
		, "</a>"
		));
	}
	@Test   public void Link__xwiki_foreign() {	// PURPOSE:ns in linked wikis should use canonical name, not current wiki's name PAGE:pl.w:Terytoria_Północno-Zachodnie DATE:2016-10-28
		fxt.Wiki().Ns_mgr().Ids_get_or_null(gplx.xowa.wikis.nss.Xow_ns_.Tid__help).Name_bry_(Bry_.new_a7("Aide"));	// simulate non-English wiki with non-english names
		fxt.Test__bicode	// fails if "Aide:" instead of "Help:"
		( "~%.qen.wiktionary.org|~A.png~/Help~)#Sabc~"
		, "<a href='/site/en.wiktionary.org/wiki/Help:' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
		fxt.Wiki().Ns_mgr().Ids_get_or_null(gplx.xowa.wikis.nss.Xow_ns_.Tid__help).Name_bry_(Bry_.new_a7("Help"));	// revert
	}
}
