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
package gplx.xowa.mediawiki.includes.filerepo.file; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.filerepo.*;
import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_file_finder__mock implements Xomw_file_finder {
	private final    Xomw_parser_env env;
	public Xomw_file_finder__mock(Xomw_parser_env env) {this.env = env;}
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Clear() {hash.Clear();}
	public Xomw_File Find_file(Xomw_Title ttl) {
		return (Xomw_File)hash.Get_by(ttl.getPrefixedDBkey());
	}
	public void Add(String title, Xomw_FileRepo repo, int w, int h, byte[] mime) {
		byte[] title_bry = Bry_.new_u8(title);
		Xomw_LocalFile file = new Xomw_LocalFile(env, title_bry, repo, w, h, mime);
		hash.Add_if_dupe_use_nth(title_bry, file);
	}
}
