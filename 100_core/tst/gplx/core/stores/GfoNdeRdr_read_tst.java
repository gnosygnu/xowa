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
package gplx.core.stores; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
public class GfoNdeRdr_read_tst {
	@Test  public void ReadInt() {
		rdr = rdr_(IntClassXtn.Instance, "id", 1);
		Tfds.Eq(rdr.ReadInt("id"), 1);
	}
	@Test  public void ReadIntOr() {
		rdr = rdr_(IntClassXtn.Instance, "id", 1);
		Tfds.Eq(rdr.ReadIntOr("id", -1), 1);
	}
	@Test  public void ReadIntElse_minus1() {
		rdr = rdr_(IntClassXtn.Instance, "id", null);
		Tfds.Eq(rdr.ReadIntOr("id", -1), -1);
	}
	@Test  public void ReadInt_parse() {
		rdr = rdr_(StringClassXtn.Instance, "id", "1");
		Tfds.Eq(rdr.ReadInt("id"), 1);
	}
	@Test  public void ReadIntElse_parse() {
		rdr = rdr_(StringClassXtn.Instance, "id", "2");
		Tfds.Eq(rdr.ReadIntOr("id", -1), 2);
	}
	GfoNdeRdr rdr_(ClassXtn type, String key, Object val) {	// makes rdr with one row and one val
		GfoFldList flds = GfoFldList_.new_().Add(key, type);
		GfoNde row = GfoNde_.vals_(flds, new Object[] {val});
		boolean parse = type == StringClassXtn.Instance;	// assumes type is either StringClassXtn or IntClassXtn
		return GfoNdeRdr_.leaf_(row, parse);
	}
	GfoNdeRdr rdr;
}
