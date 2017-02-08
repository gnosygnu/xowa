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
