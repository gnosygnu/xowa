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
public interface Xoa_wiki_mgr extends Gfo_invk {
	int				Count();
	boolean			Has(byte[] key);
	Xow_wiki		Get_at(int idx);
	Xow_wiki		Get_by_or_null(byte[] key);
	Xow_wiki		Get_by_or_make_init_y(byte[] key);
	Xow_wiki		Get_by_or_make_init_n(byte[] key);
	void			Add(Xow_wiki wiki);
	Xow_wiki		Make(byte[] domain_bry, Io_url wiki_root_dir);
	Xow_wiki		Import_by_url(Io_url fil);
}
