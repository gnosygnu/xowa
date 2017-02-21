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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*; import gplx.xowa.mediawiki.includes.filerepo.*; import gplx.xowa.mediawiki.includes.filerepo.file.*;
public class Xomw_lnki_wkr__text__tst {
	private final    Xomw_lnki_wkr__fxt fxt = new Xomw_lnki_wkr__fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Text()                             {fxt.Test__parse("a [[A]] z"         , "a <!--LINK 0--> z");}
	@Test   public void Capt()                             {fxt.Test__parse("a [[A|a]] z"       , "a <!--LINK 0--> z");}
	@Test   public void Invalid__char()                    {fxt.Test__parse("a [[<A>]] z"       , "a [[<A>]] z");}
	@Test   public void Html__self()                       {fxt.Test__to_html("[[Page_1]]"      , "<strong class='selflink'>Page_1</strong>");}
	@Test   public void Html__text()                       {fxt.Test__to_html("[[A]]"           , "<a href='/wiki/A' title='A'>A</a>");}
	@Test   public void Html__capt()                       {fxt.Test__to_html("[[A|a]]"         , "<a href='/wiki/A' title='A'>a</a>");}
}
