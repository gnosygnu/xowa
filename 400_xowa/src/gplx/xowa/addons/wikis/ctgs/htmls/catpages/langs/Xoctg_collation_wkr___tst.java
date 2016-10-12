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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoctg_collation_wkr___tst {
	private final    Xoctg_collation_wkr___fxt fxt = new Xoctg_collation_wkr___fxt();
	@Test   public void Uppercase()							{fxt.Test__make("uppercase"	, "uppercase");}
	@Test   public void Identity()							{fxt.Test__make("identity"	, "identity");}
	@Test   public void Unknown()							{fxt.Test__make("unknown"	, "uppercase");}
	@Test   public void Uca__uca_default()					{fxt.Test__make__uca("uca-default"					, "en", false);}
	@Test   public void Uca__xx_uca_ckb()					{fxt.Test__make__uca("xx-uca-ckb"					, "fa", false);}
	@Test   public void Uca__xx_uca_et()					{fxt.Test__make__uca("xx-uca-et"					, "et", false);}
	@Test   public void Uca__uca_default_u_kn()				{fxt.Test__make__uca("uca-default-u-kn"				, "en", true);}
	@Test   public void Uca__uca_at_logic()					{fxt.Test__make__uca("uca-sv@collation=standard"	, "sv", false);}
}
class Xoctg_collation_wkr___fxt {
	public void Test__make(String wm_name, String expd_type) {
		Xoctg_collation_wkr actl = Xoctg_collation_wkr_.Make(null, wm_name);
		Gftest.Eq__str(expd_type, actl.Type_name());
	}
	public void Test__make__uca(String wm_name, String expd_locale, boolean expd_numeric_sorting) {
		Xoctg_collation_wkr__uca actl = (Xoctg_collation_wkr__uca)Xoctg_collation_wkr_.Make(null, wm_name);
		Gftest.Eq__str(expd_locale, actl.Icu_locale());
		Gftest.Eq__bool(expd_numeric_sorting, actl.Numeric_sorting());
	}
}
