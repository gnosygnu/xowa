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
package gplx.dbs.schemas; import gplx.*; import gplx.dbs.*;
public class Schema_tbl_mgr {
	private final Ordered_hash hash = Ordered_hash_.new_();
	public void Add(Schema_tbl_itm itm) {hash.Add(itm.Name(), itm);}
	public boolean Has(String name) {return hash.Has(name);}
	public Schema_tbl_itm Get(String name) {return (Schema_tbl_itm)hash.Get_by(name);}
}
