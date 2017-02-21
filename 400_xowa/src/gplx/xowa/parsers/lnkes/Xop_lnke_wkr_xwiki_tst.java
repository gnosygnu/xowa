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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_lnke_wkr_xwiki_tst {
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Xwiki() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test__parse__wtxt_to_html("[http://en.wikipedia.org/wiki/A a]", "<a href='/site/en.wikipedia.org/wiki/A'>a</a>");
	}
	@Test  public void Xwiki_relative() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test__parse__wtxt_to_html("[//en.wikipedia.org/ a]", "<a href='/site/en.wikipedia.org/wiki/'>a</a>");
	}
	@Test  public void Xwiki_qarg() {// DATE:2013-02-02
		fxt.Init_xwiki_add_user_("en.wikipedia.org");
		fxt.Test__parse__wtxt_to_html("http://en.wikipedia.org/wiki/Special:Allpages?from=Earth", "<a href='/site/en.wikipedia.org/wiki/Special:Allpages?from=Earth'>http://en.wikipedia.org/wiki/Special:Allpages?from=Earth</a>");
	}
	@Test  public void Lang_prefix() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("fr"), Bry_.new_a7("fr.wikipedia.org"));
		fxt.Test__parse__wtxt_to_html("[http://en.wikipedia.org/wiki/fr:A a]", "<a href='/site/fr.wikipedia.org/wiki/A' rel='nofollow' class='external text'>a</a>");
	}
	@Test  public void Xwiki_query_arg() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test__parse__wtxt_to_html("[http://en.wikipedia.org/wiki/A?action=edit a]", "<a href='/site/en.wikipedia.org/wiki/A?action=edit'>a</a>");
	}
	@Test  public void Xwiki__history() {	// PURPOSE: handle xwiki lnke's to history page else null ref; EX:[http://ru.wikipedia.org/w/index.php?title&diff=19103464&oldid=18910980 извещен]; PAGE:ru.w:Project:Заявки_на_снятие_флагов/Архив/Патрулирующие/2009 DATE:2016-11-24
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test__parse__wtxt_to_html("[http://en.wikipedia.org/w/index.php?title&diff=1&oldid=2 abc]", "<a href='http://en.wikipedia.org/w/index.php?title&diff=1&oldid=2' rel='nofollow' class='external text'>abc</a>");
	}
	@Test  public void Ignore_proto() {	// PURPOSE: handle other protocols; PAGE:uk.w:Маскалі; DATE:2015-07-28
		fxt.Test__parse__wtxt_to_html("[mailto:a b]", "<a href='mailto:a' rel='nofollow' class='external text'>b</a>");// should be /w/, not /en.wikipedia.org
	}
	@Test  public void Ignore_alias() {	// PURPOSE: fictitious example to make sure aliases are not subbed for domains; DATE:2015-07-28
		fxt.Init_xwiki_add_user_("w", "en.wikipedia.org");
		fxt.Test__parse__wtxt_to_html("[https://w/b c]", "<a href='https://w/b' rel='nofollow' class='external text'>c</a>");// should be /w/, not /en.wikipedia.org
	}
	@Test  public void Xwiki__qargs() {	// PURPOSE: fix null ref error; PAGE:en.w:Wikipedia:Template_standardisation/demometa DATE:2015-08-02
		fxt.Init_xwiki_add_user_("en.wikipedia.org");
		fxt.Test__parse__wtxt_to_html
		( "[http://en.wikipedia.org/w/index.php?action&#61;edit&preload&#61;Template:Afd2+starter&editintro&#61;Template:Afd3+starter&title&#61;Wikipedia:Articles+for+deletion/Template_standardisation/demometa]"
		// CHANGED: lnke_now decodes html_entities; DATE:2016-10-10
		//, "<a href='/site/en.wikipedia.org/wiki/index.php?action=&#61;edit=&preload=&#61;Template:Afd2+starter=&editintro=&#61;Template:Afd3+starter=&title=&='>[1]</a>"
		, "<a href='/site/en.wikipedia.org/wiki/Wikipedia:Articles+for+deletion/Template_standardisation/demometa?action=edit&preload=Template:Afd2+starter&editintro=Template:Afd3+starter&title=Wikipedia:Articles+for+deletion/Template_standardisation/demometa'>[1]</a>"
		);
	}
}
