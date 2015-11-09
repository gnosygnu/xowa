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
//namespace gplx.langs.htmls.parsers {
//	public class Html_doc_log {
//		private byte[] src; private byte[] page_url; private String wkr_name; private int src_bgn; private int src_end;
//		public Html_doc_log Init_by_page(byte[] src, byte[] page_url) {
//			this.src = src; this.page_url = page_url;
//			return this;
//		}
//		public Html_doc_log Init_by_wkr(String wkr_name, int src_bgn, int src_end) {
//			this.wkr_name = wkr_name; this.src_bgn = src_bgn; this.src_end = src_end;
//			return this;
//		}
//		public Err Fail_w_args(String fail_msg, params Object[] custom_args) {return Fail_w_excerpt(fail_msg, src_bgn, src_end + 255, custom_args);}
//		public Err Fail_w_excerpt(String fail_msg, int excerpt_bgn, int excerpt_end, params Object[] custom_args) {
//			Object[] dflt_args = Object_.Ary("page", page_url, "wkr", wkr_name, "excerpt", Bry_.Mid_safe(src, excerpt_bgn, excerpt_end));
//			Object[] fail_args = Object_.Ary_add(custom_args, dflt_args);
//			String msg = Err_msg.To_str(fail_msg, fail_args);
//			Gfo_usr_dlg_.Instance.Warn_many("", "", msg);
//			return Err_.new_("Xoh_hdoc_err", msg);
//		}
//	}
//}
