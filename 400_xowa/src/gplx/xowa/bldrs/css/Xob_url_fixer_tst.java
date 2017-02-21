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
	public void Site_(String v) {site_bry = Bry_.new_u8(v);} private byte[] site_bry;
	public void Clear() {
		this.Site_("site");
	}
	public void Test_fix(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Tfds.Eq(expd, String_.new_u8(Xob_url_fixer.Fix(site_bry, raw_bry, raw_bry.length)));
	}
}	
