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
package gplx.xowa.langs.cnvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.intl.*;
public class Xol_cnv_grp implements GfoInvkAble {
	private OrderedHash hash = OrderedHash_.new_bry_();
	public Xol_cnv_grp(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private byte[] key;
	public int Len() {return hash.Count();}
	public Xol_cnv_itm Get_at(int i) {return (Xol_cnv_itm)hash.FetchAt(i);}
	public void Add(byte[] src, byte[] trg) {
		hash.AddReplace(src, new Xol_cnv_itm(src, trg));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add_bulk))			Add_bulk(hash, m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_add_bulk = "add_bulk";
	public static void Add_bulk(OrderedHash hash, byte[] raw) {	// COPY:add_bulk
		int len = raw.length;
		int pos = 0, fld_bgn = 0, fld_idx = 0;
		byte[] src = Bry_.Empty, trg = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser._;
		while (true) {
			boolean last = pos == len;
			byte b = last ? Byte_ascii.NewLine : raw[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					switch (fld_idx) {
						case 0:		src = csv_parser.Load(raw, fld_bgn, pos); break;
						default:	throw Err_.unhandled(fld_idx);
					}
					fld_bgn = pos + 1;
					++fld_idx;
					break;
				case Byte_ascii.NewLine:
					if (fld_bgn < pos) {	// guard against trailing new lines
						trg = csv_parser.Load(raw, fld_bgn, pos);
						hash.AddReplace(src, new Xol_cnv_itm(src, trg));
					}
					fld_bgn = pos + 1;
					fld_idx = 0;
					break;
			}
			if (last) break;
			++pos;
		}
	}
}
