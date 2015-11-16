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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.gfo_ndes.*;
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
