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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.log_msgs.*;
public class Xop_lnki_log {		
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "lnki");
	public static final Gfo_msg_itm
	  Upright_val_is_invalid			= Gfo_msg_itm_.new_warn_(owner, "upright_val_is_invalid")
	, Escaped_lnki						= Gfo_msg_itm_.new_warn_(owner, "escaped_lnki")
	, Key_is_empty						= Gfo_msg_itm_.new_warn_(owner, "key_is_empty")
	, Ext_is_missing					= Gfo_msg_itm_.new_warn_(owner, "ext_is_missing")
	, Invalid_ttl						= Gfo_msg_itm_.new_warn_(owner, "invalid_ttl")
	;
}
