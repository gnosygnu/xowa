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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import gplx.core.threads.poolables.*;
public class Btrie_rv implements Gfo_poolable_itm {
	public Object Obj() {return obj;} private Object obj;
	public int Pos() {return pos;} private int pos;
	public Btrie_rv Init(int pos, Object obj) {
		this.obj = obj;
		this.pos = pos;
		return this;
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Btrie_rv rv = new Btrie_rv(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
