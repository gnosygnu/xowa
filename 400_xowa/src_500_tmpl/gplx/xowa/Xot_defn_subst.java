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
package gplx.xowa; import gplx.*;
public class Xot_defn_subst implements Xot_defn {
	public Xot_defn_subst(byte tid, byte[] name) {this.tid = tid; this.name = name;}
	public byte Defn_tid() {return tid;} private byte tid;
	public byte[] Name() {return name;} private byte[] name;
	public Xot_defn Clone(int id, byte[] name) {return new Xot_defn_subst(tid, name);}
	public boolean Defn_require_colon_arg() {return true;}
	public void Rls() {name = null;}
	public int Cache_size() {return 1024;}	// arbitrary size
}
