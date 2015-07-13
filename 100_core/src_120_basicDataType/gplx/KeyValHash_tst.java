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
public class KeyValHash_tst {
	@Test  public void AryVals() {
		tst_AryVals(ary_());
		tst_AryVals(ary_("key1", "1"), kv_("key1", "1"));
		tst_AryVals(ary_("key1", "1", "key2", "2"), kv_("key1", "1"), kv_("key2", "2"));
	}
	@Test  public void Fail_lengthMustBeEven() {
		try {
			tst_AryVals(ary_("key1"), kv_("key1", "1"));
			Tfds.Fail_expdError();
		}
		catch (Exception e) {Exc_.Noop(e);}
	}
	void tst_AryVals(String[] ary, KeyVal... expd) {Tfds.Eq_ary_str(expd, KeyValHash.strAry_(ary).Xto_bry());}
	KeyVal kv_(String key, Object val) {return KeyVal_.new_(key, val);}
	String[] ary_(String... ary) {return ary;}
}
