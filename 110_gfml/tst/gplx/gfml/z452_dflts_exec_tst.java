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
		evalContext.Add_if_dupe_use_nth(varItem);
		GfmlVarTkn rv = new GfmlVarTkn("eval", GfmlTknAry_.ary_(valTkn), evalContext, "varKey");
		return new Object[] {rv, varItem};
	}
	void ini_SubFldDefault_add(GfmlType type, String subFldKey, String defaultVal) {
		GfmlFld subFld = type.SubFlds().Get_by(subFldKey);
		GfmlTkn defaultTkn = GfmlTkn_.raw_(defaultVal);;
		subFld.DefaultTkn_(defaultTkn);
	}
	void tst_SubFldDefault(GfmlType type, String subFldKey, String expdDefaultVal) {
		GfmlFld subFld = type.SubFlds().Get_by(subFldKey);
		GfmlTkn defaultTkn = GfmlTkn_.as_(subFld.DefaultTkn());
		String actlDefaultVal = defaultTkn == null || defaultTkn == GfmlTkn_.Null ? null : defaultTkn.Val();
		Tfds.Eq(expdDefaultVal, actlDefaultVal);
	}
	void tst_SubFldExists(GfmlType type, String subFldKey, boolean expd) {
		GfmlFld subFld = type.SubFlds().Get_by(subFldKey);
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
