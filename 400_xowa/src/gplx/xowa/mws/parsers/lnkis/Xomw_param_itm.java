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
package gplx.xowa.mws.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
public class Xomw_param_itm {
	public final    byte[] magic;
	public final    byte[] type;
	public final    byte[] name;
	public final    int name_type;
	public Xomw_param_itm(byte[] magic, byte[] type, byte[] name) {
		this.magic = magic;
		this.type = type;
		this.name = name;
		this.name_type = name_types.Get_as_int_or(name, -1);
	}
	public static final int 
	  Name__width             = 0
	, Name__manual_thumb      = 1
	, Name__alt               = 2
	, Name__class             = 3
	, Name__link              = 4
	, Name__frameless         = 5
	, Name__framed            = 6
	, Name__thumbnail         = 7
	;
	private static final    Hash_adp_bry name_types = Hash_adp_bry.cs()
	.Add_str_int("thumbnail", Name__thumbnail);
}
