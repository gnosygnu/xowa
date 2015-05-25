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
package gplx.core.regxs; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public interface Gfo_pattern_itm {
	byte Tid();
	void Compile(byte[] src, int bgn, int end);
	int Match(Gfo_pattern_ctx ctx, byte[] src, int src_len, int pos);
	void Xto_str(String_bldr sb);
}
class Gfo_pattern_itm_text implements Gfo_pattern_itm {
	public Gfo_pattern_itm_text() {}
	public byte Tid() {return Gfo_pattern_itm_.Tid_text;}
	public byte[] Text() {return text;} private byte[] text; private int text_len;
	public void Xto_str(String_bldr sb) {sb.Add(this.Tid()).Add("|" + String_.new_u8(text));}
	public void Compile(byte[] src, int bgn, int end) {
		this.text = Bry_.Mid(src, bgn, end);
		this.text_len = end - bgn;
	}
	public int Match(Gfo_pattern_ctx ctx, byte[] src, int src_len, int pos) {
		boolean pass = false;
		int text_end = pos + text_len;
		if (text_end > src_len) text_end = src_len;
		if (ctx.Prv_was_wild()) {
			int text_bgn = Bry_finder.Find_fwd(src, text, pos);
			pass = text_bgn != Bry_finder.Not_found;
			if (pass)
				pos = text_bgn + text_len;
		}
		else {
			pass = Bry_.Match(src, pos, text_end, text);
			if (pass)
				pos = text_end;
		}
		if (!pass) ctx.Rslt_fail_();
		ctx.Prv_was_wild_(false);
		return pos;
	}
}
class Gfo_pattern_itm_wild implements Gfo_pattern_itm {
	public byte Tid() {return Gfo_pattern_itm_.Tid_wild;}
	public void Compile(byte[] src, int bgn, int end) {}
	public int Match(Gfo_pattern_ctx ctx, byte[] src, int src_len, int pos) {
		ctx.Prv_was_wild_(true);
		return ctx.Itm_idx_is_last() ? src_len : pos;
	}
	public void Xto_str(String_bldr sb) {sb.Add(this.Tid()).Add("|*");}
	public static final Gfo_pattern_itm_wild _ = new Gfo_pattern_itm_wild(); Gfo_pattern_itm_wild() {}
}
