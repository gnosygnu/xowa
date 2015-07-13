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
package gplx.xowa; import gplx.*;
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
