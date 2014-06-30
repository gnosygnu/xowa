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
public class DateAdpClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "datetime";
	public boolean Eq(Object lhs, Object rhs) {try {return DateAdp_.cast_(lhs).Eq(DateAdp_.cast_(rhs));} catch (Exception e) {Err_.Noop(e); return false;}}
	@Override public Class<?> UnderClass()					{return DateAdp.class;}
	public Object DefaultValue()								{return DateAdp_.MinValue;}
	@Override public Object ParseOrNull(String raw)				{return DateAdp_.parse_gplx(raw);}
	@Override public Object XtoDb(Object obj)					{return DateAdp_.cast_(obj).XtoStr_gplx_long();}
	@Override public String XtoUi(Object obj, String fmt)		{return DateAdp_.cast_(obj).XtoStr_fmt(fmt);}
	public static final DateAdpClassXtn _ =  new DateAdpClassXtn(); DateAdpClassXtn() {} // added to ClassXtnPool by default
}
