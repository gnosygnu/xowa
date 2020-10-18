/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Bry_bfr;
import gplx.Bry_bfr_;

public abstract class Json_itm_base implements Json_itm {
	public abstract byte Tid();
	public abstract Object Data();
	public abstract byte[] Data_bry();
	public String Print_as_json() {
		Bry_bfr bfr = Bry_bfr_.New();
		Print_as_json(bfr, 0);
		return bfr.To_str_and_clear();
	}
	public abstract void Print_as_json(Bry_bfr bfr, int depth);
	@gplx.Virtual public boolean Data_eq(byte[] comp) {return false;}
}
