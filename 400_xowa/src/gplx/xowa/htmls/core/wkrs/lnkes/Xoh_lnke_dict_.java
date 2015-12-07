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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
public class Xoh_lnke_dict_ {
	public static final byte	// SERIALIZED
	  Type__free					= 0
	, Type__auto					= 1
	, Type__text					= 2
	;
	public static final byte[]
	  Html__atr__0					= Bry_.new_a7("\" rel=\"nofollow\" class=\"external ")
	, Html__class__free				= Bry_.new_a7("free")
	, Html__class__auto				= Bry_.new_a7("autonumber")
	, Html__class__text				= Bry_.new_a7("text")
	, Html__rhs_end					= Bry_.new_a7("\">")
	;
	public static byte[] 
	  Html__rel__nofollow		= Bry_.new_a7("nofollow")
	, Html__cls__external		= Bry_.new_a7("external")
	;
	public static final Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Html__class__free, Type__free)
	.Add_bry_byte(Html__class__auto, Type__auto)
	.Add_bry_byte(Html__class__text, Type__text)
	;
	public static byte[] To_html_class(byte tid) {
		switch (tid) {
			case Xoh_lnke_dict_.Type__free:	return Xoh_lnke_dict_.Html__class__free;
			case Xoh_lnke_dict_.Type__auto:	return Xoh_lnke_dict_.Html__class__auto;
			case Xoh_lnke_dict_.Type__text:	return Xoh_lnke_dict_.Html__class__text;
			default:						throw Err_.new_unhandled(tid);
		}
	}
}
