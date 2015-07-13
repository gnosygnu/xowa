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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Pfunc_gender_tst {	// REF.MW:https://translatewiki.net/wiki/Gender
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void No_args()				{fxt.Test_parse_template("{{gender:}}"					, "");}
	@Test  public void Username_m()				{fxt.Test_parse_template("{{gender:xowa_male|m|f|?}}"	, "m");}	
	@Test  public void Username_f()				{fxt.Test_parse_template("{{gender:xowa_female|m|f|?}}"	, "f");}
	@Test  public void Username_unknown()		{fxt.Test_parse_template("{{gender:wmf_user|m|f|?}}"	, "?");}	// should look up gender of "wmf_user", but since not avaliable, default to unknown
	@Test  public void Username_unknown_m()		{fxt.Test_parse_template("{{gender:wmf_user|m}}"		, "m");}	// if only m is provided, use it
	@Test  public void Username_unknown_f()		{fxt.Test_parse_template("{{gender:wmf_user|m|f}}"		, "m");}	// if only m is provided; same as above, but make sure "f" doesn't change anything
	@Test  public void Default()				{fxt.Test_parse_template("{{gender:.|m|f|?}}"			, "?");}	// "." means use wiki's default gender; default to unknown
	@Test  public void Unknown()				{fxt.Test_parse_template("{{gender:|m|f|?}}"			, "?");}	// "" always is unknown
}
