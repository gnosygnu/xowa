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
package gplx.xowa.xtns.wbases.claims.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
public class Wbase_claim_type {
	public Wbase_claim_type(byte tid, String key_str, String key_for_scrib) {
		this.tid = tid;
		this.key_str = key_str;
		this.key_bry = Bry_.new_u8(key_str);
		this.key_for_scrib = key_for_scrib;
	}
	public byte Tid()				{return tid;} private final    byte tid;
	public String Key_str()			{return key_str;} private final    String key_str;
	public byte[] Key_bry()			{return key_bry;} private final    byte[] key_bry;
	public String Key_for_scrib()	{return key_for_scrib;} private final    String key_for_scrib;
}
