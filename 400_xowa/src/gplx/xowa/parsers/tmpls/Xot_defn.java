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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Xot_defn extends RlsAble {
	byte Defn_tid();
	byte[] Name();
	int Cache_size();
	boolean Defn_require_colon_arg();
	Xot_defn Clone(int id, byte[] name);
}
class Xot_defn_null implements Xot_defn {
	public byte Defn_tid() {return Xot_defn_.Tid_null;}
	public boolean Defn_require_colon_arg() {return false;}
	public byte[] Name() {return Bry_.Empty;}
	public Xot_defn Clone(int id, byte[] name) {return this;}
	public int Cache_size() {return 0;}
	public void Rls() {}
	public static final Xot_defn_null _ = new Xot_defn_null(); Xot_defn_null() {}
}
