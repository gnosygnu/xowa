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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Mem_row implements GfoInvkAble {
	private final Ordered_hash hash = Ordered_hash_.New();
	public Object	Get_by(String key) {return hash.Get_by(key);}
	public Object	Get_at(int i) {return hash.Get_at(i);}
	public void		Set_by(String key, Object val)		{hash.Add_if_dupe_use_nth(key, val);}
	public void		Add(String key, Object val)			{hash.Add(key, val);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Object rv = Get_by(k);
		if (rv == null) return GfoInvkAble_.Rv_unhandled;
		return rv;
	}
}
