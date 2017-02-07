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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_amp_mgr__parse_tkn__tst {
	@Before public void init() {} private final    Xop_amp_mgr_fxt fxt = new Xop_amp_mgr_fxt();
	@Test  public void Ent()					{fxt.Test__parse_tkn__ent("&amp;"		, "&amp;");}	// check for html_ref 
	@Test  public void Ent__fail()				{fxt.Test__parse_tkn__txt("&nil;"		, 1);}
	@Test  public void Num__nex()				{fxt.Test__parse_tkn__ncr("&#x3A3;"		, 931);}		// check for html_ncr; Σ: http://en.wikipedia.org/wiki/Numeric_character_reference
	@Test  public void Num__dec()				{fxt.Test__parse_tkn__ncr("&#931;"		, 931);}
	@Test  public void Num__fail()				{fxt.Test__parse_tkn__txt("&#"			, 1);}
}
