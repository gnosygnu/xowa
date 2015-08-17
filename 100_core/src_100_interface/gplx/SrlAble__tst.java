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
package gplx;
import org.junit.*;
public class SrlAble__tst {
	@Test  public void Basic() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1)
			,	"itm:key='a' val='1';"
			);

	}
	@Test  public void Depth1_1() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1).Subs_
			(		GfoMsg_.new_cast_("itm").Add("key", "aa").Add("val", 11)
			)
			,	String_.Concat_lines_crlf_skipLast
			(	"itm:key='a' val='1'{itm:key='aa' val='11';}"
			)
			);
	}
	@Test  public void Depth1_2() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1).Subs_
			(		GfoMsg_.new_cast_("itm").Add("key", "aa").Add("val", 11)
			,		GfoMsg_.new_cast_("itm").Add("key", "ab").Add("val", 12)
			)
			,	String_.Concat_lines_crlf_skipLast
			(	"itm:key='a' val='1'{"
			,	"    itm:key='aa' val='11';"
			,	"    itm:key='ab' val='12';"
			,	"}"
			)
			);
	}
	@Test  public void Depth1_1_2() {
		tst_Srl_
			(	GfoMsg_.new_cast_("itm").Add("key", "a").Add("val", 1).Subs_
			(		GfoMsg_.new_cast_("itm").Add("key", "aa").Add("val", 11).Subs_(
						GfoMsg_.new_cast_("itm").Add("key", "aab").Add("val", 112)
			)
			)
			,	String_.Concat_lines_crlf_skipLast
			(	"itm:key='a' val='1'{itm:key='aa' val='11'{itm:key='aab' val='112';}}"
			)
			);
	}
	void tst_Srl_(GfoMsg m, String expd) {Tfds.Eq(expd, SrlAble_.To_str(m));}
}
//class SrlAble__tst
