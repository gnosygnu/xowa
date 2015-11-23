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
package gplx;
public class KeyVal implements To_str_able {
	@gplx.Internal protected KeyVal(int key_tid, Object key, Object val) {this.key_tid = key_tid; this.key = key; this.val = val;}
	public String Key() {return Object_.Xto_str_strict_or_null(key);}
	public int Key_tid() {return key_tid;} private int key_tid;
	public Object Key_as_obj() {return key;} private Object key;
	public Object Val() {return val;} private Object val;
	public String Val_to_str_or_empty() {return Object_.Xto_str_strict_or_empty(val);}
	public String Val_to_str_or_null() {return Object_.Xto_str_strict_or_null(val);}
	public byte[] Val_to_bry() {return Bry_.new_u8(Object_.Xto_str_strict_or_null(val));}
	public KeyVal Key_(Object v) {this.key = v; return this;}
	public KeyVal Val_(Object v) {this.val = v; return this;} 
	@Override public String toString() {return To_str();}
	public String To_str() {return Key() + "=" + Object_.Xto_str_strict_or_null_mark(val);}
}
