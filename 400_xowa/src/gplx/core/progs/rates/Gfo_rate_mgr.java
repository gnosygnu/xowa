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
package gplx.core.progs.rates; import gplx.*; import gplx.core.*; import gplx.core.progs.*;
public class Gfo_rate_mgr {
	private final    Hash_adp hash = Hash_adp_.New();
	private final    int dflt_size;
	public Gfo_rate_mgr(int dflt_size) {this.dflt_size = dflt_size;}
	public void Clear() {hash.Clear();}
	public int Len() {return hash.Count();}
	public Gfo_rate_list Get_or_new(String k) {
		Gfo_rate_list rv = (Gfo_rate_list)hash.Get_by(k);
		if (rv == null) {
			rv = new Gfo_rate_list(dflt_size);
			rv.Add(1024 * 1024, 1);	// add default rate of 1 MB per second
			hash.Add(k, rv);
		}
		return rv;
	}
	public Gfo_rate_mgr Add_new(String key) {
		Gfo_rate_list rv = new Gfo_rate_list(dflt_size);
		hash.Add(key, rv);
		return this;
	}
}
