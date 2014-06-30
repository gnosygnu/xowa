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
public class Xow_dir_info {
	public Xow_dir_info(boolean ns_root, byte id, String name) {this.ns_root = ns_root; this.id = id; this.name = name;}
	public byte Id() {return id;} private byte id;
	public String Name() {return name;} private String name;
	public boolean Ns_root() {return ns_root;} private boolean ns_root;
	public String Ext() {return ext_str;} private String ext_str = Xow_fsys_mgr.Wtr_xdat_str;
	public byte[] Ext_bry() {return ext_bry;} private byte[] ext_bry = Xow_fsys_mgr.Wtr_xdat_bry;
	public byte Ext_tid() {return ext_tid;}
	public Xow_dir_info Ext_tid_(byte v) {
		ext_tid = v; 
		ext_bry = Xow_fsys_mgr.Wtr_ext(v);
		ext_str = String_.new_ascii_(ext_bry);
		return this;
	}	byte ext_tid = gplx.ios.Io_stream_.Tid_file;
}
