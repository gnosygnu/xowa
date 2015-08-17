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
import gplx.lists.*;
public class ClassXtnPool extends Hash_adp_base {
	public void Add(ClassXtn typx) {Add_base(typx.Key(), typx);}
	public ClassXtn Get_by_or_fail(String key) {return (ClassXtn)Get_by_or_fail_base(key);}

	public static final ClassXtnPool _ =  new ClassXtnPool();
	public static final String Format_null = "";
	public static ClassXtnPool new_() {return new ClassXtnPool();}
	ClassXtnPool() {
		Add(ObjectClassXtn._);
		Add(StringClassXtn._);
		Add(IntClassXtn._);
		Add(BoolClassXtn._);
		Add(ByteClassXtn._);
		Add(DateAdpClassXtn._);
		Add(TimeSpanAdpClassXtn._);
		Add(IoUrlClassXtn._);
		Add(DecimalAdpClassXtn._);
		Add(FloatClassXtn._);
	}
}
