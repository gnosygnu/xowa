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
public class IntClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "int";
	@Override public Class<?> UnderClass()					{return Integer.class;}	
	public Object DefaultValue()								{return 0;}
	@Override public Object ParseOrNull(String raw)				{return raw == null ? (Object)null : Int_.parse_(raw);}
	public boolean Eq(Object lhs, Object rhs) {try {return Int_.cast_(lhs) == Int_.cast_(rhs);} catch (Exception e) {Err_.Noop(e); return false;}}
	@Override public Object XtoDb(Object obj)					{return Int_.cast_(obj);}	// necessary for enums

	public static final IntClassXtn _ = new IntClassXtn(); IntClassXtn() {} // added to ClassXtnPool by default
}
