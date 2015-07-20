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
package gplx.xowa.xtns.wdatas.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.json.*;
import gplx.xowa.xtns.pfuncs.*;
public class Wdata_pf_wbreponame extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_wbreponame;}
	@Override public Pf_func New(int id, byte[] name) {return new Wdata_pf_wbreponame().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		bfr.Add(Reponame);	// NOTE: MW has logic to look for message named "wbreponame", and returning it if it exists; only applies to non-WMF Wikidatas; DATE:2014-09-07
	}	private static final byte[] Reponame = Bry_.new_a7("Wikidata");
}
