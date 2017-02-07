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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
public class Dbmeta_idx_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int				Len()					{return hash.Count();}
	public boolean				Has(String name)		{return hash.Has(name);}
	public Dbmeta_idx_itm	Get_at(int idx)			{return (Dbmeta_idx_itm)hash.Get_at(idx);}
	public Dbmeta_idx_itm	Get_by(String name)		{return (Dbmeta_idx_itm)hash.Get_by(name);}
	public void				Add(Dbmeta_idx_itm itm)	{hash.Add(itm.Name(), itm);}
	public void				Clear()					{hash.Clear();}
	public Dbmeta_idx_itm[]	To_ary()				{return (Dbmeta_idx_itm[])hash.To_ary(Dbmeta_idx_itm.class);}
}
