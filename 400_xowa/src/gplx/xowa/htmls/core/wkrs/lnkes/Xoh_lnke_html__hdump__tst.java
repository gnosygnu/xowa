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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_lnke_html__hdump__tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	public static final String 
	  Html__free		= "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a>"
	, Html__auto		= "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external autonumber\">[1]</a>"
	, Html__text		= "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external text\">a</a>"
	;
	@Test   public void Free()		{fxt.Test__html("http://a.org"				, Html__free);}
	@Test   public void Auto()		{fxt.Test__html("[http://a.org]"			, Html__auto);}
	@Test   public void Text()		{fxt.Test__html("[http://a.org a]"			, Html__text);}
}
