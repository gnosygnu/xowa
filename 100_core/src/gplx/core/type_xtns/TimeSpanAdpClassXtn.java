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
package gplx.core.type_xtns; import gplx.*; import gplx.core.*;
public class TimeSpanAdpClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final    String Key_const = "timeSpan";
	@Override public Class<?> UnderClass()					{return Time_span.class;}
	public Object DefaultValue()								{return Time_span_.Zero;}
	@Override public Object ParseOrNull(String raw)				{return Time_span_.parse(raw);}
	@Override public Object XtoDb(Object obj)					{return Time_span_.cast(obj).TotalSecs();}
	@Override public String XtoUi(Object obj, String fmt)		{return Time_span_.cast(obj).To_str(fmt);}
	public boolean Eq(Object lhs, Object rhs) {try {return Time_span_.cast(lhs).Eq(rhs);} catch (Exception e) {Err_.Noop(e); return false;}}
	public static final    TimeSpanAdpClassXtn Instance =  new TimeSpanAdpClassXtn(); TimeSpanAdpClassXtn() {} // added to ClassXtnPool by default
}