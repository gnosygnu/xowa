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
public class SizeAdpF_ {
	public static final    SizeAdpF Null = new_(Int_.Min_value, Int_.Min_value);
	public static final    SizeAdpF Zero = new_(0, 0);
	public static final    SizeAdpF Parser = new SizeAdpF(0, 0);
	public static SizeAdpF as_(Object obj) {return obj instanceof SizeAdpF ? (SizeAdpF)obj : null;}
	public static SizeAdpF new_(float width, float height) {return new SizeAdpF(width, height);}
	public static SizeAdpF coerce_(Object obj) {SizeAdpF rv = as_(obj); return rv == null ? parse((String)obj) : rv;}
	public static SizeAdpF parse(String s) {
		try {
			String[] ary = String_.Split(s, ","); if (ary.length != 2) throw Err_.new_wo_type("SizeAdf should only have 2 numbers separated by 1 comma");
			float val1 = Float_.parse(ary[0]);
			float val2 = Float_.parse(ary[1]);
			return new_(val1, val2);
		}	catch (Exception e) {throw Err_.new_parse_exc(e, SizeAdpF.class, s);}
	}
}
