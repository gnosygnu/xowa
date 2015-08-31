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
package gplx.ios; import gplx.*;
public class IoItmClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "ioItemType";
	@Override public Class<?> UnderClass()					{return int.class;}
	public Object DefaultValue()								{return IoItmDir.Type_Dir;}
	public boolean Eq(Object lhs, Object rhs) {return ((IoItm_base)lhs).compareTo(rhs) == CompareAble_.Same;}
	@Override public Object ParseOrNull(String raw) {
		String rawLower = String_.Lower(raw);
		if		(String_.Eq(rawLower, "dir")) return IoItmDir.Type_Dir;
		else if (String_.Eq(rawLower, "fil")) return IoItmFil.Type_Fil;
		else	throw Err_.new_unhandled(raw);
	}
	@Override public Object XtoDb(Object obj)					{return Int_.cast(obj);}
	public static final IoItmClassXtn _ = new IoItmClassXtn(); IoItmClassXtn() {}
}
