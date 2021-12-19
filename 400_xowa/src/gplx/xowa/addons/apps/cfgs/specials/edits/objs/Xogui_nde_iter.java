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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Xogui_nde_iter {
	private final Xoedit_nde_hash hash;
	private int bgn, max;
	public Xogui_nde_iter(Xoedit_nde_hash hash, int max) {
		this.hash = hash;
		this.max = max;
	}
	public boolean Move_next() {
		return bgn < hash.Len();
	}
	public String To_sql_in() {
		BryWtr bfr = BryWtr.New();
		int end = bgn + max;
		if (end > hash.Len()) end = hash.Len();
		for (int i = bgn; i < end; i++) {
			Xoedit_nde nde = hash.Get_at(i);
			if (i != bgn) bfr.AddByteComma();
			bfr.AddIntVariable(nde.Id());
		}
		bgn = end;
		return bfr.ToStrAndClear();
	}
	public String To_sql_in_key() {
		BryWtr bfr = BryWtr.New();
		int end = bgn + max;
		if (end > hash.Len()) end = hash.Len();
		for (int i = bgn; i < end; i++) {
			Xoedit_nde nde = (Xoedit_nde)hash.Get_at(i);
			if (i != bgn) bfr.AddByteComma();
			bfr.AddByteApos();
			bfr.AddStrU8(nde.Key());
			bfr.AddByteApos();
		}
		bgn = end;
		return bfr.ToStrAndClear();
	}
	public static Xogui_nde_iter New_sql(Xoedit_nde_hash hash) {
		return new Xogui_nde_iter(hash, 255);
	}
}
