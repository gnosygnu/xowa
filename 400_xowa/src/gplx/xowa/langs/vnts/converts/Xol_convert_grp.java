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
package gplx.xowa.langs.vnts.converts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
public class Xol_convert_grp implements GfoInvkAble {// group of convert_itm by vnt; EX:  zh-hant {A -> A1; B -> B1}
	private final Ordered_hash hash = Ordered_hash_.new_bry_();
	public Xol_convert_grp(byte[] key) {this.key = key;}
	public byte[]				Key()							{return key;} private final byte[] key;
	public int					Len()							{return hash.Count();}
	public Xol_convert_itm		Get_at(int i)					{return (Xol_convert_itm)hash.Get_at(i);}
	public void					Add(byte[] src, byte[] trg)		{hash.Add_if_dupe_use_nth(src, new Xol_convert_itm(src, trg));}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add_bulk))			Add_bulk(this, m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_add_bulk = "add_bulk";
	private static void Add_bulk(Xol_convert_grp grp, byte[] raw) {
		int len = raw.length;
		int pos = 0, fld_bgn = 0, fld_idx = 0;
		byte[] src = Bry_.Empty, trg = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser._;
		while (true) {
			boolean last = pos == len;
			byte b = last ? Byte_ascii.Nl : raw[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					switch (fld_idx) {
						case 0:		src = csv_parser.Load(raw, fld_bgn, pos); break;
						default:	throw Err_.new_unhandled(fld_idx);
					}
					fld_bgn = pos + 1;
					++fld_idx;
					break;
				case Byte_ascii.Nl:
					if (fld_bgn < pos) {	// guard against trailing new lines
						trg = csv_parser.Load(raw, fld_bgn, pos);
						grp.Add(src, trg);
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
