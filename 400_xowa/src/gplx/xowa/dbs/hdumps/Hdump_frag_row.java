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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.ios.*;
public class Hdump_frag_row {
	public int Frag_id() {return frag_id;} private int frag_id;
	public int Page_id() {return page_id;} private int page_id;
	public int Frag_tid() {return frag_tid;} private int frag_tid;
	public byte[] Frag_key() {return frag_key;} private byte[] frag_key;
	public byte[] Frag_text() {return frag_text;} private byte[] frag_text;
	public Hdump_frag_row Ctor(int frag_id, int page_id, int frag_tid, byte[] frag_key, byte[] frag_text) {
		this.frag_id		= frag_id;
		this.page_id		= page_id;
		this.frag_tid		= frag_tid;
		this.frag_key		= frag_key;
		this.frag_text		= frag_text;
		return this;
	}
}
class Hdump_frag_tid {
	public static final int Tid_file = 1, Tid_title = 2, Tid_sidebar = 3;
	public static final String Key_file = "file", Key_title = "title", Key_sidebar = "sidebar";
	public static String Xto_key(int v) {
		switch (v) {
			case Tid_file		: return Key_file;
			case Tid_title		: return Key_title;
			case Tid_sidebar	: return Key_sidebar;
			default				: throw Err_.unhandled(v);
		}
	}
	public static byte Xto_tid(String v) {
		if		(String_.Eq(v, Key_file))		return Tid_file;
		else if	(String_.Eq(v, Key_title))		return Tid_title;
		else if	(String_.Eq(v, Key_sidebar))	return Tid_sidebar;
		else									throw Err_.unhandled("v");
	}
}
