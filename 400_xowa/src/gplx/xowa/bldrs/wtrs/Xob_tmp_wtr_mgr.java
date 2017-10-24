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
package gplx.xowa.bldrs.wtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.wikis.nss.*;
public class Xob_tmp_wtr_mgr {
	public Xob_tmp_wtr[] Regy() {return regy;} private Xob_tmp_wtr[] regy = new Xob_tmp_wtr[Ns_ordinal_max];
	public Xob_tmp_wtr_mgr(Xob_tmp_wtr_wkr wkr) {this.wkr = wkr;} private Xob_tmp_wtr_wkr wkr;
	public Xob_tmp_wtr Get_or_new(Xow_ns ns) {
		Xob_tmp_wtr rv = regy[ns.Ord()];
		if (rv == null) {
			rv = wkr.Tmp_wtr_new(ns);
			regy[ns.Ord()] = rv;
		}
		return rv;
	}		
	public void Flush_all(Gfo_usr_dlg usr_dlg) {
		for (int i = 0; i < Ns_ordinal_max; i++) {
			Xob_tmp_wtr wtr = regy[i];
			if (wtr != null) {
				wtr.Flush(usr_dlg);
				wtr.Rls();
			}
		}
	}
	public void Rls_all() {
		for (int i = 0; i < Ns_ordinal_max; i++)
			regy[i] = null;
	}
	static final int Ns_ordinal_max = Xow_ns_mgr_.Ordinal_max;	// ASSUME: no more than 128 ns in a wiki
}
