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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public interface Xob_page_wkr extends Gfo_invk {
	String	Page_wkr__key();
	void	Page_wkr__bgn();
	void	Page_wkr__run(gplx.xowa.wikis.data.tbls.Xowd_page_itm page);
	void	Page_wkr__run_cleanup();	// close txns opened during Page_wkr__run
	void	Page_wkr__end();
}
