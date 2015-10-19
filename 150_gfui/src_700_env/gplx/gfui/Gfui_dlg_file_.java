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
package gplx.gfui; import gplx.*;
public class Gfui_dlg_file_ {
	public static final Gfui_dlg_file Noop = new Gfui_dlg_file_noop();
}
class Gfui_dlg_file_noop implements Gfui_dlg_file {
	public Gfui_dlg_file Init_msg_(String v) {return this;}
	public Gfui_dlg_file Init_file_(String v) {return this;}
	public Gfui_dlg_file Init_dir_(Io_url v) {return this;}
	public Gfui_dlg_file Init_exts_(String... v) {return this;}
	public String Ask() {return "";}
}
