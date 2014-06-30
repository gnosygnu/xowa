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
public class SolidBrushAdp_ {
	public static SolidBrushAdp as_(Object obj) {return obj instanceof SolidBrushAdp ? (SolidBrushAdp)obj : null;}
	public static SolidBrushAdp cast_(Object obj) {try {return (SolidBrushAdp)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, SolidBrushAdp.class, obj);}}
	public static final SolidBrushAdp Black = new_(ColorAdp_.Black);
	public static final SolidBrushAdp White = new_(ColorAdp_.White);
	public static final SolidBrushAdp Null = new_(ColorAdp_.Null);
	public static SolidBrushAdp new_(ColorAdp color) {return SolidBrushAdpCache._.Fetch(color);}
}
class SolidBrushAdpCache {
	public SolidBrushAdp Fetch(ColorAdp color) {
		SolidBrushAdp rv = (SolidBrushAdp)hash.Fetch(color.Value());
		if (rv == null) {
			rv = SolidBrushAdp.new_(color);
			hash.Add(color.Value(), rv);
		}
		return rv;
	}
	HashAdp hash = HashAdp_.new_();
	public static final SolidBrushAdpCache _ = new SolidBrushAdpCache(); SolidBrushAdpCache() {}
}
