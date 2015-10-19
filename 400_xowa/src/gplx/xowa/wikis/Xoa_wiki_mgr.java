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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
public interface Xoa_wiki_mgr {
	boolean		Has(byte[] key);
	int			Count();
	Xow_wiki	Get_at_i(int i);
	Xow_wiki	Get_by_key_or_null_i(byte[] key);
	Xow_wiki	Get_by_key_or_make_init_y(byte[] key);
	Xow_wiki	Get_by_key_or_make_init_n(byte[] key);
}
