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
public abstract class Wdata_claim_itm_base implements CompareAble {
	public Wdata_claim_itm_base Ctor(int pid, byte snak_tid) {
		this.pid = pid;
		this.snak_tid = snak_tid;
		return this;
	}
	public int Pid() {return pid;} private int pid;
	public abstract byte Val_tid();
	public byte Snak_tid() {return snak_tid;} private byte snak_tid = Wdata_dict_snak_tid.Tid_value;
	public byte Rank_tid() {return rank_tid;} public void Rank_tid_(byte v) {this.rank_tid = v;} private byte rank_tid;
	public int compareTo(Object obj) {
		Wdata_claim_itm_base comp = (Wdata_claim_itm_base)obj;
		return Int_.Compare(pid, comp.pid);
	}
}
