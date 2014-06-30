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
package gplx.gfui; import gplx.*;
public class PointAdp_ {
	public static final PointAdp Null = new PointAdp(Int_.MinValue, Int_.MinValue);
	public static final PointAdp Zero = new PointAdp(0, 0);
	public static PointAdp as_(Object obj) {return obj instanceof PointAdp ? (PointAdp)obj : null;}
	public static PointAdp cast_(Object obj) {try {return (PointAdp)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, PointAdp.class, obj);}}
	public static PointAdp new_(int x, int y) {return new PointAdp(x, y);}
	public static PointAdp coerce_(Object o) {PointAdp rv = PointAdp_.as_(o); return (rv == null) ? parse_((String)o) : rv;}
	public static PointAdp parse_(String raw) {
		try {
			String[] ary = String_.Split(raw, ",");
			return new PointAdp(Int_.parse_(ary[0]), Int_.parse_(ary[1]));
		}	catch (Exception exc) {throw Err_.parse_type_exc_(exc, PointAdp.class, raw);}
	}
}
