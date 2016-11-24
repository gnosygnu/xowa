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
package gplx.core.btries; import gplx.*; import gplx.core.*;
public interface Btrie_mgr {
	int Match_pos();
	Object Match_at(Btrie_rv rv, byte[] src, int bgn_pos, int end_pos);
	Object Match_bgn(byte[] src, int bgn_pos, int end_pos);
	Btrie_mgr Add_obj(String key, Object val);
	Btrie_mgr Add_obj(byte[] key, Object val);
}
