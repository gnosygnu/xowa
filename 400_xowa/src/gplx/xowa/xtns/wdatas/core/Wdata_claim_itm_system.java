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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Wdata_claim_itm_system extends Wdata_claim_itm_core { 	public Wdata_claim_itm_system(int pid, byte val_tid, byte snak_tid) {
		this.Ctor(pid, snak_tid);
		this.val_tid = val_tid;
	}
	@Override public byte Val_tid() {return val_tid;} private byte val_tid;
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wdata_dict_snak_tid.Xto_str(this.Snak_tid()), Wdata_dict_val_tid.Xto_str(this.Val_tid()));
	}
	public static Wdata_claim_itm_system new_novalue(int pid)		{return new Wdata_claim_itm_system(pid, Wdata_dict_val_tid.Tid_unknown	, Wdata_dict_snak_tid.Tid_novalue);}
	public static Wdata_claim_itm_system new_somevalue(int pid)		{return new Wdata_claim_itm_system(pid, Wdata_dict_val_tid.Tid_unknown	, Wdata_dict_snak_tid.Tid_somevalue);}
}
