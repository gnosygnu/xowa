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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
public class DsvDataWtr_csv_tst {
	@Test  public void Dat_Val_0() {
		root = fx_nde.csv_dat_(); this.AddCsvRow(root);
		expd = String_.Concat_lines_crlf("");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Val_1() {
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a");
		expd = String_.Concat_lines_crlf("a");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Val_N() {
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", "b", "c");
		expd = String_.Concat_lines_crlf("a,b,c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Row_N() {
		root = fx_nde.csv_dat_();
		this.AddCsvRow(root, "a", "b", "c");
		this.AddCsvRow(root, "d", "e", "f");
		expd = String_.Concat_lines_crlf
			(	"a,b,c"
			,	"d,e,f"
			);
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Escape_FldSpr() {	// ,
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", ",", "c");
		expd = String_.Concat_lines_crlf("a,\",\",c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Escape_RcdSpr() {	// NewLine
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", String_.CrLf, "c");
		expd = String_.Concat_lines_crlf("a,\"" + String_.CrLf + "\",c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Escape_Quote() {		// " -> ""
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", "\"", "c");
		expd = String_.Concat_lines_crlf("a,\"\"\"\",c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Whitespace() {
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", " b\t", "c");
		expd = String_.Concat_lines_crlf("a, b\t,c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_Null() {
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", null, "c");
		expd = String_.Concat_lines_crlf("a,,c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Dat_EmptyString() {
		root = fx_nde.csv_dat_(); this.AddCsvRow(root, "a", "", "c");
		expd = String_.Concat_lines_crlf("a,\"\",c");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Hdr_Flds() {
		wtr = DsvDataWtr_.csv_hdr_();
		GfoFldList flds = GfoFldList_.new_().Add("id", StringClassXtn.Instance).Add("name", StringClassXtn.Instance);
		root = fx_nde.csv_hdr_(flds); this.AddCsvRow(root, "0", "me");
		expd = String_.Concat_lines_crlf
			(	"id,name"
			,	"0,me"
			);
		fx.tst_XtoStr(wtr, root, expd);
	}
	void AddCsvRow(GfoNde root, String... ary) {
		GfoNde sub = GfoNde_.vals_(root.SubFlds(), ary);
		root.Subs().Add(sub);
	}
	GfoNde root; String expd; DsvDataWtr wtr = DsvDataWtr_.csv_dat_(); DsvDataWtr_fxt fx = DsvDataWtr_fxt.new_(); GfoNdeFxt fx_nde = GfoNdeFxt.new_(); 
}
class DsvDataWtr_fxt {
	public void tst_XtoStr(DsvDataWtr wtr, GfoNde root, String expd) {
		wtr.Clear();
		root.XtoStr_wtr(wtr);
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);
	}
	public static DsvDataWtr_fxt new_() {return new DsvDataWtr_fxt();} DsvDataWtr_fxt() {}
}
