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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Pb_xtn extends Xox_mgr_base implements GfoInvkAble {
	@Override public boolean Enabled_default() {return true;}
	@Override public byte[] Xtn_key() {return Xtn_key_static;} public static final byte[] Xtn_key_static = Bry_.new_a7("graph");
	@Override public Xox_mgr Clone_new() {return new Pb_xtn();}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {}
	public void Xtn_init_assert(Xowe_wiki wiki) {}
}
