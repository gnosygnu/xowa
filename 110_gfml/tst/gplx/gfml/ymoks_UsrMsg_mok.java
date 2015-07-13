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
package gplx.gfml; import gplx.*;
class UsrMsg_mok {
	public String Main() {return main;} public UsrMsg_mok Main_(String v) {main = v; return this;} private String main;
	public UsrMsg_mok Add_(String k, Object o) {hash.Add(k, KeyVal_.new_(k, o)); return this;}
	public UsrMsg_mok Require_(String k) {required.Add(k, k); return this;}
	public Ordered_hash Args() {return hash;} Ordered_hash hash = Ordered_hash_.new_();
	public Ordered_hash Required() {return required;} Ordered_hash required = Ordered_hash_.new_();
	public static UsrMsg_mok new_(UsrMsg um) {
		UsrMsg_mok rv = new UsrMsg_mok();
		if (um != null) {
			rv.main = um.Hdr();
			for (int i = 0; i < um.Args().Count(); i++) {
				KeyVal kv = (KeyVal)um.Args().Get_at(i);
				rv.Add_(kv.Key(), kv.Val());
			}
		}
		return rv;
	}	UsrMsg_mok() {}
}
