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
//namespace gplx.xowa {
//	public class Xoa_url_alias_mgr : GfoInvkAble {
//		private Hash_adp_bry hash = Hash_adp_bry.cs_(); private Bry_fmtr fmtr = Bry_fmtr.new_("", "");
//		public Xoa_url_alias_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
//		public byte[] Fmt_or_null(byte[] raw) {
//			int colon_pos = Bry_finder.Find_fwd(raw, Byte_ascii.Colon); if (colon_pos == Bry_.NotFound) return null;
//			byte[] fmt = (byte[])hash.Get_by_mid(raw, 0, colon_pos); if (fmt == null) return null;
//			Bry_bfr tmp_bfr = app.Utl__bfr_mkr().Get_b512();
//			fmtr.Fmt_(fmt).Bld_bfr_many(tmp_bfr, Bry_.Mid(raw, colon_pos + Int_.Const_dlm_len, raw.length));
//			return tmp_bfr.Mkr_rls().Xto_bry_and_clear();
//		}
//		public void Clear() {hash.Clear();}
//		public void Add_one(byte[] alias, byte[] wiki_key) {hash.Add_bry_obj(alias, wiki_key);}
//		public void Add_bulk(byte[] src) {	// COPY:add_bulk
//			int len = src.length;
//			int pos = 0, fld_bgn = 0, fld_idx = 0;
//			byte[] alias = Bry_.Empty, wiki = Bry_.Empty;
//			Xol_csv_parser csv_parser = Xol_csv_parser._;
//			while (true) {
//				boolean last = pos == len;
//				byte b = last ? Byte_ascii.NewLine : src[pos];
//				switch (b) {
//					case Byte_ascii.Pipe:
//						switch (fld_idx) {
//							case 0:		alias = csv_parser.Load(src, fld_bgn, pos); break;
//							default:	throw Err_.unhandled(fld_idx);
//						}
//						fld_bgn = pos + 1;
//						++fld_idx;
//						break;
//					case Byte_ascii.NewLine:
//						if (fld_bgn < pos) {	// guard against trailing new lines
//							wiki = csv_parser.Load(src, fld_bgn, pos);
//							hash.Add_bry_obj(alias, wiki);
//						}
//						fld_bgn = pos + 1;
//						fld_idx = 0;
//						break;
//				}
//				if (last) break;
//				++pos;
//			}
//		}
//		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
//			if		(ctx.Match(k, Invk_add_bulk))						Add_bulk(m.ReadBry("v"));
//			else return GfoInvkAble_.Rv_unhandled;
//			return this;
//		}
//		private static final String Invk_add_bulk = "add_bulk";
//	}
//}
