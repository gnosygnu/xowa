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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_url_fixer_tst {
	@Before public void init() {fxt.Clear();} private Xob_url_fixer_fxt fxt = new Xob_url_fixer_fxt();
	@Test   public void Slash2()			{fxt.Test_fix("//site/a.png"				, "site/a.png");}
	@Test   public void Http()				{fxt.Test_fix("http://site/a.png"			, "site/a.png");}
	@Test   public void Https()				{fxt.Test_fix("https://site/a.png"			, "site/a.png");}
	@Test   public void Qarg()				{fxt.Test_fix("//site/a.png?key=val"		, "site/a.png");}
	@Test   public void Qarg_dir()			{fxt.Test_fix("//site/a/b/c.png?key=val"	, "site/a/b/c.png");}
	@Test   public void Root()				{fxt.Test_fix("/a/b.png"					, "site/a/b.png");}		// EX:/static/images/project-logos/wikivoyage.png; DATE:2015-05-09
	@Test   public void Rel_dot2()			{fxt.Test_fix("//site/a/../b/c.png"			, "site/b/c.png");}		// DATE:2015-05-09
	@Test   public void Rel_dot2_mult()		{fxt.Test_fix("//site/a/../b/../c/d.png"	, "site/c/d.png");}		// DATE:2015-05-09
	@Test   public void Rel_dot1()			{fxt.Test_fix("//site/a/./b/c.png"			, "site/a/b/c.png");}	// DATE:2015-05-09
	@Test   public void Site_only()			{fxt.Test_fix("//site"						, null);}
}
class Xob_url_fixer_fxt {
	public void Site_(String v) {site_bry = Bry_.new_utf8_(v);} private byte[] site_bry;
	public void Clear() {
		this.Site_("site");
	}
	public void Test_fix(String raw, String expd) {
		byte[] raw_bry = Bry_.new_utf8_(raw);
		Tfds.Eq(expd, String_.new_utf8_(Xob_url_fixer.Fix(site_bry, raw_bry, raw_bry.length)));
	}
}	
