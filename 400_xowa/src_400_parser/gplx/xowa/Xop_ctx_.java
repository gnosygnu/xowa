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
package gplx.xowa; import gplx.*;
public class Xop_ctx_ {
	public static String Page_as_str(Xop_ctx ctx) {return String_.new_u8(ctx.Cur_page().Ttl().Full_db());}
	public static String Src_limit_and_escape_nl(byte[] src, int bgn, int limit) {
		int end = bgn + limit;
		int src_len = src.length;
		if (end > src_len) end = src_len;
		byte[] rv = Bry_.Mid(src, bgn, end);
		rv = Bry_.Replace(rv, Byte_ascii.NewLine, Byte_ascii.Tab); // change nl to tab so text editor will show one warning per line
		return String_.new_u8(rv);
	}
}