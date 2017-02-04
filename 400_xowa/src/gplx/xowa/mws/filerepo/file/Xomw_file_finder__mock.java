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
package gplx.xowa.mws.filerepo.file; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.filerepo.*;
public class Xomw_file_finder__mock implements Xomw_file_finder {
	private final    Hash_adp hash = Hash_adp_.New();
	public Xomw_File Find_file(Xoa_ttl ttl) {
		return (Xomw_File)hash.Get_by(ttl.Page_db_as_str());
	}
	public void Add(String title, Xomw_FileRepo repo, int w, int h) {
		Xomw_LocalFile file = new Xomw_LocalFile(Bry_.new_u8(title), repo, w, h);
		hash.Add(title, file);
	}
}
