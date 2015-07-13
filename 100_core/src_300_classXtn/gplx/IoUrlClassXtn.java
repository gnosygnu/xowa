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
public class IoUrlClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "ioPath";
	@Override public Class<?> UnderClass()					{return Io_url.class;}
	public Object DefaultValue()								{return Io_url_.Empty;}
	@Override public Object ParseOrNull(String raw)				{return Io_url_.new_any_(raw);}
	@Override public Object XtoDb(Object obj)					{return Io_url_.cast_(obj).Raw();}
	@Override public String XtoUi(Object obj, String fmt)		{return Io_url_.cast_(obj).Raw();}
	@Override public boolean MatchesClass(Object obj)				{return Io_url_.as_(obj) != null;}
	public boolean Eq(Object lhs, Object rhs) {try {return Io_url_.cast_(lhs).Eq(Io_url_.cast_(rhs));} catch (Exception e) {Exc_.Noop(e); return false;}}
	public static final IoUrlClassXtn _ =  new IoUrlClassXtn(); IoUrlClassXtn() {} // added to ClassXtnPool by default
}
