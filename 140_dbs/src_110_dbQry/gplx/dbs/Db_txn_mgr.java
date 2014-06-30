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
package gplx.dbs; import gplx.*;
public interface Db_txn_mgr {
	int Txn_depth();
	void Txn_bgn_if_none();
	void Txn_bgn();
	void Txn_end();
	void Txn_end_all();
	void Txn_end_all_bgn_if_none();
	int Txn_count(); void Txn_count_(int v);
}
class Db_txn_mgr_base implements Db_txn_mgr {
	public Db_txn_mgr_base(Db_engine engine) {this.engine = engine;} Db_engine engine;
	public int Txn_depth() {return txn_depth;} int txn_depth;	// NOTE: only support 1 level for now;
	public void Txn_bgn_if_none() {if (txn_depth == 0) this.Txn_bgn();}
	public void Txn_bgn() {
		engine.Txn_bgn();
		++txn_depth;
	}
	public void Txn_end_all() {this.Txn_end();}
	public void Txn_end() {
		if (txn_depth == 0) return;
		engine.Txn_end();
		--txn_depth;
		txn_count = 0;
	}
	public void Txn_end_all_bgn_if_none() {
		this.Txn_end_all();
		this.Txn_bgn_if_none();
	}
	public int Txn_count() {return txn_count;} public void Txn_count_(int v) {txn_count = v;} int txn_count;		
}
