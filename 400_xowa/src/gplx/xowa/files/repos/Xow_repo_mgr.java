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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public interface Xow_repo_mgr {
	Xof_repo_pair		Repos_get_at(int i);
	Xof_repo_pair		Repos_get_by_wiki(byte[] wiki);
	Xof_repo_pair[]		Repos_ary();
	Xof_repo_itm		Get_trg_by_id_or_null(int id, byte[] lnki_ttl, byte[] page_url);
	Xof_repo_itm		Get_src_by_id_or_null(int id, byte[] lnki_ttl, byte[] page_url);		
}
