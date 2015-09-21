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
package gplx.xowa.langs; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.primitives.*;
public class Xol_kwd_parse_data_tst {
	@Before public void init() {Clear();}
	@Test  public void Basic()			{Key_("upright" ).Tst_strip("upright");}
	@Test  public void Eq_arg()			{Key_("upright" ).Tst_strip("upright=$1");}
	@Test  public void Space()			{Key_("upright ").Tst_strip("upright $1");}
	@Test  public void Px()				{Key_("px").Tst_strip("$1px");}

	private void Clear() {
		key = null;
	}
	Xol_kwd_parse_data_tst Key_(String v) {this.key = v; return this;} private String key;
	Xol_kwd_parse_data_tst Tst_strip(String v) {
		Bry_bfr tmp = Bry_bfr.new_();
		Byte_obj_ref rslt = Byte_obj_ref.zero_();
		byte[] actl = Xol_kwd_parse_data.Strip(tmp, Bry_.new_a7(v), rslt);
		Tfds.Eq(key, String_.new_a7(actl));
		return this;
	}
}
