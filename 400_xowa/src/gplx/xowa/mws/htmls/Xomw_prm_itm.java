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
package gplx.xowa.mws.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomw_prm_itm {
	public int type = 0;
	public int name_type = 1;
	public byte[] name = null;
	public byte[] val = null;

	public static final int
	  Type__handler           = 0
	;
	public static final int 
	  Name__width             = 0
	, Name__manual_thumb      = 0
	, Name__alt               = 1
	, Name__class             = 2
	, Name__link              = 3
	, Name__frameless         = 4
	, Name__framed            = 5
	, Name__thumbnail         = 6
	;
}
