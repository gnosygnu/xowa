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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wdata_prop_itm_base {
	public Wdata_prop_itm_base Ctor(byte snak_tid, int pid, byte val_tid, byte[] val) {
		this.snak_tid = snak_tid;
		this.pid = pid;
		this.val_tid_byte = val_tid;
		this.val = val;
		return this;
	}
	public int Pid() {return pid;} private int pid;
	public byte Val_tid_byte() {return val_tid_byte;} private byte val_tid_byte;
	public String Val_tid_str() {return Wdata_prop_itm_base_.Val_tid_to_string(val_tid_byte);}
	public byte[] Val() {return val;} private byte[] val;
	public byte[] Snak_bry() {return Wdata_prop_itm_base_.Snak_tid_bry(snak_tid);}
	public byte Snak_tid() {return snak_tid;} private byte snak_tid = Wdata_prop_itm_base_.Snak_tid_value;
	public String Snak_str() {return Wdata_prop_itm_base_.Snak_tid_string(snak_tid);}
}
