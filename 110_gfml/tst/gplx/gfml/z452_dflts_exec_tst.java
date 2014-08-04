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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z452_dflts_exec_tst {
	@Before public void setup() {
		type = make_("gfml.point", "point", "x");
	}	GfmlDefaultItem item; GfmlType type; GfmlDefaultItem_fxt fx_item = GfmlDefaultItem_fxt.new_();
	@Test  public void Basic() {
		tst_SubFldDefault(type, "x", null);

		item = fx_item.make_("gfml.point", "x", "10");
		item.Exec_bgn(type);
		tst_SubFldDefault(type, "x", "10");

		item.Exec_end(type);
		tst_SubFldDefault(type, "x", null);
	}
	@Test  public void Overwrite() {
		ini_SubFldDefault_add(type, "x", "0");
		tst_SubFldDefault(type, "x", "0");

		item = fx_item.make_("gfml.point", "x", "10");
		item.Exec_bgn(type);
		tst_SubFldDefault(type, "x", "10");

		item.Exec_end(type);
		tst_SubFldDefault(type, "x", "0");
	}
	@Test  public void CreateDefault() {
		tst_SubFldExists(type, "y", false);

		item = fx_item.make_("gfml.point", "y", "10");
		item.Exec_bgn(type);

		tst_SubFldDefault(type, "y", "10");

		item.Exec_end(type);
		tst_SubFldExists(type, "y", false);
	}
	@Test  public void DefaultTkn() {
		Object[] ary = ini_eval_("0");
		GfmlTkn varTkn = (GfmlTkn)ary[0];
		GfmlVarItm varItem = (GfmlVarItm)ary[1];
		item = fx_item.make_("gfml.point", "x", varTkn);
		varItem.Tkn_set(GfmlTkn_.raw_("10"));
		item.Exec_bgn(type);

		tst_SubFldDefault(type, "x", "10");
	}
	Object[] ini_eval_(String text) {
		GfmlVarCtx evalContext = GfmlVarCtx.new_("testContext");
		GfmlTkn valTkn = GfmlTkn_.raw_(text);
		GfmlVarItm varItem = GfmlVarItm.new_("varKey", valTkn, "testContext");
		evalContext.AddReplace(varItem);
		GfmlVarTkn rv = new GfmlVarTkn("eval", GfmlTknAry_.ary_(valTkn), evalContext, "varKey");
		return new Object[] {rv, varItem};
	}
	void ini_SubFldDefault_add(GfmlType type, String subFldKey, String defaultVal) {
		GfmlFld subFld = type.SubFlds().Fetch(subFldKey);
		GfmlTkn defaultTkn = GfmlTkn_.raw_(defaultVal);;
		subFld.DefaultTkn_(defaultTkn);
	}
	void tst_SubFldDefault(GfmlType type, String subFldKey, String expdDefaultVal) {
		GfmlFld subFld = type.SubFlds().Fetch(subFldKey);
		GfmlTkn defaultTkn = GfmlTkn_.as_(subFld.DefaultTkn());
		String actlDefaultVal = defaultTkn == null || defaultTkn == GfmlTkn_.Null ? null : defaultTkn.Val();
		Tfds.Eq(expdDefaultVal, actlDefaultVal);
	}
	void tst_SubFldExists(GfmlType type, String subFldKey, boolean expd) {
		GfmlFld subFld = type.SubFlds().Fetch(subFldKey);
		Tfds.Eq(expd, subFld != null);
	}
	GfmlType make_(String key, String name, String... atrs) {
		GfmlTypeMakr typeMakr = GfmlTypeMakr.new_();
		GfmlType rv = typeMakr.MakeRootType(key, name);
		for (String atr : atrs)
			typeMakr.AddSubFld(GfmlFld.new_(true, atr, GfmlType_.StringKey));
		return rv;
	}
}
