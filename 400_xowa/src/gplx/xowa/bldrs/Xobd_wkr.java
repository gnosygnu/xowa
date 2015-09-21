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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.data.tbls.*;
public interface Xobd_wkr extends GfoInvkAble {
	String Wkr_key();
	void Wkr_ini(Xob_bldr bldr);
	void Wkr_bgn(Xob_bldr bldr);
	void Wkr_run(Xowd_page_itm page);
	void Wkr_end();
	void Wkr_print();
}
