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
public class ByteClassXtn extends ClassXtn_base implements ClassXtn {
	public static final String Key_const = "byte";
	public String Key() {return Key_const;}
	@Override public Class<?> UnderClass()					{return byte.class;}
	public Object DefaultValue()								{return 0;}
	public boolean Eq(Object lhs, Object rhs) {try {return Byte_.cast(lhs) == Byte_.cast(rhs);} catch (Exception e) {Err_.Noop(e); return false;}}
	@Override public Object ParseOrNull(String raw)				{return raw == null ? (Object)null : Byte_.parse(raw);}
	@Override public Object XtoDb(Object obj)					{return Byte_.cast(obj);}
	public static final ByteClassXtn _ =  new ByteClassXtn(); ByteClassXtn() {} // added to ClassXtnPool by default
}