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
package gplx.core.progs.rates; import gplx.*; import gplx.core.*; import gplx.core.progs.*;
public class Gfo_rate_mgr {
	private final    Hash_adp hash = Hash_adp_.New();
	private final    int dflt_size;
	public Gfo_rate_mgr(int dflt_size) {this.dflt_size = dflt_size;}
	public void Clear() {hash.Clear();}
	public int Len() {return hash.Count();}
	public Gfo_rate_list Get_or_new(String k) {
		Gfo_rate_list rv = (Gfo_rate_list)hash.Get_by(k);
		if (rv == null) {
			rv = new Gfo_rate_list(dflt_size);
			rv.Add(1024 * 1024, 1);	// add default rate of 1 MB per second
			hash.Add(k, rv);
		}
		return rv;
	}
	public Gfo_rate_mgr Add_new(String key) {
		Gfo_rate_list rv = new Gfo_rate_list(dflt_size);
		hash.Add(key, rv);
		return this;
	}
}
