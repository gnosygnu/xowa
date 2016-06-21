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
package gplx.core.threads; import gplx.*; import gplx.core.*;
public class Gfo_thread_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Gfo_thread_grp Get_by_or_new(String k) {
		Gfo_thread_grp rv = (Gfo_thread_grp)hash.Get_by(k);
		if (rv == null) {
			rv = new Gfo_thread_grp(k);
			hash.Add(k, rv);
		}
		return rv;
	}
	public void Stop_all() {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Gfo_thread_grp grp = (Gfo_thread_grp)hash.Get_at(i);
			grp.Stop_all();
		}
		hash.Clear();
	}
}