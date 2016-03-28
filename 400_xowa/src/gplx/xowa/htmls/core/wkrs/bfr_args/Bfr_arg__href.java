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
//namespace gplx.xowa.htmls.core.wkrs.bfr_args {
//	using gplx.xowa.htmls.hrefs; using gplx.xowa.wikis.nss;
//	public class Bfr_arg__href : gplx.core.brys.Bfr_arg_clearable {
//		private byte[] val; private int val_bgn, val_end;
//		private int href_type;
////		public void Clear() {
////			href_type = Tid__null;
////		}
//		public void Set_by_atr(gplx.langs.htmls.docs.Gfh_atr atr) {Set_by_mid(atr.Src(), atr.Val_bgn(), atr.Val_end());}
//		public void Set_by_mid(byte[] v, int bgn, int end)	{this.val = v; this.href_type = Tid__mid; this.val_bgn = bgn; this.val_end = end;}
//		public void Set_by_raw(byte[] v)					{this.val = v; this.href_type = Tid__raw;}
//		public void Set_by_page(byte[] v)					{this.val = v; this.href_type = Tid__page;}
//		public void Set_by_file(byte[] v)					{this.val = v; this.href_type = Tid__file;}
//		public void Bfr_arg__add(Bry_bfr bfr) {
//			if (val == null) return;
//			switch (href_type) {
//				case Tid__raw:
//					bfr.Add(val);
//					break;
//				case Tid__mid:
//					bfr.Add_mid(val, val_bgn, val_end);
//					break;
//				case Tid__page:
//					bfr.Add(Xoh_href_.Bry__wiki);	// '/wiki/'
//					bfr.Add(val);					// 'File:A.png'
//					break;
//				case Tid__file:
//					bfr.Add(Xoh_href_.Bry__wiki).Add(Xow_ns_.Bry__file).Add_byte_colon();
//					bfr.Add(val);
//					break;
//			}
//		}
//		private static final int Tid__null = 0, Tid__raw = 1, Tid__page = 2, Tid__file = 3, Tid__mid = 4;
//	}
//}
