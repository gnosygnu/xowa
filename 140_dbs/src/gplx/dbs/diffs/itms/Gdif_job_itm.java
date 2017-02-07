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
package gplx.dbs.diffs.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
public class Gdif_job_itm {
	public Gdif_job_itm(int id, String name, String made_by, DateAdp made_on) {
		this.Id = id; this.Name = name; this.Made_by = made_by; this.Made_on = made_on;
	}
	public final int Id;
	public final String Name;
	public final String Made_by;
	public final DateAdp Made_on;
	public String Data;
}
