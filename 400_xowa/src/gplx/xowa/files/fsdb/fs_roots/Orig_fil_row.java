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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
class Orig_fil_row {
	Orig_fil_row(int uid, byte[] name, int ext_id, int w, int h, Io_url url) {
		this.uid = uid;
		this.name = name;
		this.ext_id = ext_id;
		this.w = w;
		this.h = h;
		this.url = url;
	}
	public int            Uid()        {return uid;}      private int uid;
	public byte[]         Name()       {return name;}     private final    byte[] name;
	public int            Ext_id()     {return ext_id;}   private final    int ext_id;
	public int            W()          {return w;}        private int w;
	public int            H()          {return h;}        private int h;
	public Io_url         Url()        {return url;}      private final    Io_url url;

	public Orig_fil_row Init_by_fs(int uid, int w, int h) {
		this.uid = uid; this.w = w; this.h = h;
		return this;
	}

	public static final    Orig_fil_row Null = null;
	public static Orig_fil_row New_by_db(int uid, byte[] name, int ext_id, int w, int h, Io_url dir) {
		return new Orig_fil_row(uid, name, ext_id, w, h, dir.GenSubFil(String_.new_u8(name)));
	}
	public static Orig_fil_row New_by_fs(Io_url url, byte[] name, int ext_id) {
		return new Orig_fil_row(0, name, ext_id, 0, 0, url);
	}
}
