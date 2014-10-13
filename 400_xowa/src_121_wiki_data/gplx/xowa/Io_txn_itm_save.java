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
interface Io_txn_itm {
	byte Txn_tid();
	Io_url Txn_url();
	boolean Txn_init(Io_url tmp_dir);
	boolean Txn_exec(Io_url tmp_dir);
	boolean Txn_term(Io_url tmp_dir);
}
class Io_txn_itm_save {
	public byte Txn_tid() {return 0;}
	public Io_url Txn_url() {return null;}
	public boolean Txn_init(Io_url tmp_dir) {
		// save to tmp_dir
		return true;
	}
	public boolean Txn_exec(Io_url tmp_dir) {
		// move temp to actl
		// move actl to temp
		return true;
	}
	public boolean Txn_term(Io_url tmp_dir) {
		// delete temp
		return true;
	}	
}
class Io_txn_mgr {
	public Io_txn_mgr(Io_url tmp_dir) {this.tmp_dir = tmp_dir;}
	public Io_url Tmp_dir() {return tmp_dir;} Io_url tmp_dir;
	public void Add(Io_txn_itm itm) {
		list.Add(itm);
	}
	public boolean Commit() {
		int len = list.Count();
		Io_txn_itm[] itms = (Io_txn_itm[])list.Xto_ary(Io_txn_itm.class);
		for (int i = 0; i < len; i++) {
			Io_txn_itm itm = itms[i];
			itm.Txn_init(tmp_dir);
		}
		for (int i = 0; i < len; i++) {
			Io_txn_itm itm = itms[i];
			itm.Txn_exec(tmp_dir);
		}
		for (int i = 0; i < len; i++) {
			Io_txn_itm itm = itms[i];
			itm.Txn_term(tmp_dir);
		}
		return true;
	}
	ListAdp list = ListAdp_.new_();
}
