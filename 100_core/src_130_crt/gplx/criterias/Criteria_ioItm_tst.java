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
package gplx.criterias; import gplx.*;
import org.junit.*;
import gplx.ios.*;
public class Criteria_ioItm_tst {
	IoItmFil fil; Criteria crt; IoItm_fxt fx = IoItm_fxt.new_();
	@Test  public void IoType() {
		crt = crt_(IoItm_base_.Prop_Type, Criteria_.eq_(IoItmFil.Type_Fil));
		tst_Match(true, crt, fx.fil_wnt_("C:\\fil.txt"));
		tst_Match(false, crt, fx.dir_wnt_("C:\\dir"));
	}
	@Test  public void Ext() {
		crt = crt_(IoItm_base_.Prop_Ext, Criteria_.eq_(".txt"));
		tst_Match(true, crt, fx.fil_wnt_("C:\\fil.txt"));
		tst_Match(false, crt, fx.fil_wnt_("C:\\fil.xml"), fx.fil_wnt_("C:\\fil.txt1"), fx.fil_wnt_("C:\\fil1.txt.xml"), fx.dir_wnt_("C:\\.txt"));
	}
	@Test  public void Modified() {
		fil = fx.fil_wnt_("C:\\fil.txt");
		crt = crt_(IoItmFil_.Prop_Modified, Criteria_.mte_(DateAdp_.parse_gplx("2001-01-01")));
		tst_Match(true, crt, fil.ModifiedTime_(DateAdp_.parse_gplx("2001-01-02")), fil.ModifiedTime_(DateAdp_.parse_gplx("2001-01-01")));
		tst_Match(false, crt, fil.ModifiedTime_(DateAdp_.parse_gplx("2000-12-31")));
	}
	@Test  public void IoMatch() {
		Criteria crt = Criteria_ioMatch.parse_(true, "*.txt", false);
		CriteriaFxt fx_crt = new CriteriaFxt();
		fx_crt.tst_Matches(crt, Io_url_.new_any_("file.txt"));
		fx_crt.tst_MatchesNot(crt, Io_url_.new_any_("file.xml"));
	}
	Criteria crt_(String fld, Criteria crt) {return Criteria_wrapper.new_(fld, crt);}
	void tst_Match(boolean expt, Criteria fieldCrt, IoItm_base... ary) {
		for (IoItm_base itm : ary)
			Tfds.Eq(expt, fieldCrt.Matches(itm));
	}
}
