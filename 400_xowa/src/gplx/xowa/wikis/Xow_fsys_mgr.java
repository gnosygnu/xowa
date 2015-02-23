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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
public class Xow_fsys_mgr {
	public Xow_fsys_mgr(Io_url root_dir, Io_url file_dir) {
		this.root_dir = root_dir; this.file_dir	= file_dir; this.tmp_dir = root_dir.GenSubDir("tmp");			
	}
	public Io_url Root_dir()				{return root_dir;}		private final Io_url root_dir;
	public Io_url File_dir()				{return file_dir;}		private final Io_url file_dir;
	public Io_url Tmp_dir()					{return tmp_dir;}		private final Io_url tmp_dir;
}
