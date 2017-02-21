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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
import org.junit.*;
import gplx.core.ios.*;
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
		Criteria crt = Criteria_ioMatch.parse(true, "*.txt", false);
		CriteriaFxt fx_crt = new CriteriaFxt();
		fx_crt.tst_Matches(crt, Io_url_.new_any_("file.txt"));
		fx_crt.tst_MatchesNot(crt, Io_url_.new_any_("file.xml"));
	}
	Criteria crt_(String fld, Criteria crt) {return Criteria_fld.new_(fld, crt);}
	void tst_Match(boolean expt, Criteria fieldCrt, IoItm_base... ary) {
		for (IoItm_base itm : ary)
			Tfds.Eq(expt, fieldCrt.Matches(itm));
	}
}
