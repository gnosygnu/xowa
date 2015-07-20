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
package gplx;
import gplx.core.primitives.*;
public class Bry_bfr {
	private Bry_bfr_mkr_mgr mkr_mgr; private int reset;
	public byte[] Bfr() {return bfr;} private byte[] bfr;
	public int Len() {return bfr_len;} private int bfr_len;
	public boolean Len_eq_0() {return bfr_len == 0;}
	public boolean Len_gt_0() {return bfr_len > 0;}
	public void Bfr_init(byte[] bfr, int bfr_len) {
		synchronized (this) {
			this.bfr = bfr;
			this.bfr_len = bfr_len;
			this.bfr_max = bfr.length;	// NOTE: must sync bfr_max, else will fail later during add; bfr will think bfr has .length of bfr_max, when it actually has .length of bfr_len; DATE:2014-03-09
		}
	}
	public Bry_bfr Mkr_rls() {
		if (mkr_mgr != null) {
			synchronized (mkr_mgr) {
				mkr_mgr.Rls(mkr_idx);
			}
			synchronized (this) {
				this.mkr_mgr = null;
				this.mkr_idx = -1;
			}
		}
		return this;
	}
	public void Clear_and_rls() {
		this.Clear();
		this.Mkr_rls();
	}
	public String To_str_and_rls() {return String_.new_u8(To_bry_and_rls());}
	public byte[] To_bry_and_rls() {
		byte[] rv = null;
		synchronized (bfr) {
			rv = Xto_bry();
			this.Clear();
			if (reset > 0) Reset_if_gt(reset);
			synchronized (mkr_mgr) {
				mkr_mgr.Rls(mkr_idx);
			}
			mkr_mgr = null;
			mkr_idx = -1;
		}
		return rv;
	}
	private Bry_bfr Reset_(int v) {reset = v; return this;}
	public Bry_bfr Reset_if_gt(int limit) {
		if (bfr_max > limit) {
			this.bfr_max = limit;
			this.bfr = new byte[limit];
		}
		bfr_len = 0;
		return this;
	}
	public Bry_bfr Clear() {
		synchronized (this) {
			this.bfr_len = 0;
		}
		return this;
	}
	public Bry_bfr ClearAndReset() {bfr_len = 0; if (reset > 0) Reset_if_gt(reset); return this;}
	public byte Get_at_last_or_nil_if_empty() {return bfr_len == 0 ? Byte_ascii.Null : bfr[bfr_len - 1];}
	public Bry_bfr Add_safe(byte[] val) {return val == null ? this : Add(val);}
	public Bry_bfr Add(byte[] val) {
		int val_len = val.length;
		if (bfr_len + val_len > bfr_max) Resize((bfr_max + val_len) * 2);
		Bry_.Copy_by_pos(val, 0, val_len, bfr, bfr_len);
		// Array_.CopyTo(val, 0, bfr, bfr_len, val_len);
		bfr_len += val_len;
		return this;
	}
	public Bry_bfr Add_mid(byte[] val, int bgn, int end) {
		int len = end - bgn;
		if (len < 0) throw Err_.new_wo_type("negative len", "bgn", bgn, "end", end, "excerpt", String_.new_u8_by_len(val, bgn, bgn + 16));	// NOTE: check for invalid end < bgn, else difficult to debug errors later; DATE:2014-05-11
		if (bfr_len + len > bfr_max) Resize((bfr_max + len) * 2);
		Bry_.Copy_by_pos(val, bgn, end, bfr, bfr_len);
		// Array_.CopyTo(val, bgn, bfr, bfr_len, len);
		bfr_len += len;
		return this;
	}
	public Bry_bfr Add_bfr_and_preserve(Bry_bfr src) {
		int len = src.bfr_len;
		if (bfr_len + len > bfr_max) Resize((bfr_max + len) * 2);
		Bry_.Copy_by_pos(src.bfr, 0, len, bfr, bfr_len);
		// Array_.CopyTo(src.bfr, 0, bfr, bfr_len, len);
		bfr_len += len;
		return this;
	}
	public Bry_bfr Add_bfr_and_clear(Bry_bfr src) {
		Add_bfr_and_preserve(src);
		src.ClearAndReset();
		return this;
	}
	public Bry_bfr Add_bfr_or_mid(boolean escaped, Bry_bfr tmp_bfr, byte[] src, int src_bgn, int src_end) {
		return escaped
			? this.Add_bfr_and_clear(tmp_bfr)
			: this.Add_mid(src, src_bgn, src_end);
	}
	public Bry_bfr Add_bfr_trim_and_clear(Bry_bfr src, boolean trim_bgn, boolean trim_end) {return Add_bfr_trim_and_clear(src, trim_bgn, trim_end, Bry_.Trim_ary_ws);}
	public Bry_bfr Add_bfr_trim_and_clear(Bry_bfr src, boolean trim_bgn, boolean trim_end, byte[] trim_ary) {
		int src_len = src.bfr_len;
		if (bfr_len + src_len > bfr_max) Resize((bfr_max + src_len) * 2);
		byte[] src_bry = src.Bfr();
		int src_bgn = 0, src_end = src_len;
		boolean all_ws = true;
		if (trim_bgn) {
			for (int i = 0; i < src_len; i++) {
				byte b = src_bry[i];
				if (trim_ary[b & 0xFF] == Byte_ascii.Null) {
					src_bgn = i;
					i = src_len;
					all_ws = false;
				}
			}
			if (all_ws) return this;
		}
		if (trim_end) {
			for (int i = src_len - 1; i > -1; i--) {
				byte b = src_bry[i];
				if (trim_ary[b & 0xFF] == Byte_ascii.Null) {
					src_end = i + 1;
					i = -1;
					all_ws = false;
				}
			}
			if (all_ws) return this;
		}
		src_len = src_end - src_bgn;
		Bry_.Copy_by_pos(src.bfr, src_bgn, src_end, bfr, bfr_len);
		// Array_.CopyTo(src.bfr, src_bgn, bfr, bfr_len, src_len);
		bfr_len += src_len;
		src.Clear();
		return this;
	}
	public Bry_bfr Add_byte_eq()			{return Add_byte(Byte_ascii.Eq);}
	public Bry_bfr Add_byte_pipe()			{return Add_byte(Byte_ascii.Pipe);}
	public Bry_bfr Add_byte_comma()			{return Add_byte(Byte_ascii.Comma);}
	public Bry_bfr Add_byte_semic()			{return Add_byte(Byte_ascii.Semic);}
	public Bry_bfr Add_byte_apos()			{return Add_byte(Byte_ascii.Apos);}
	public Bry_bfr Add_byte_slash()			{return Add_byte(Byte_ascii.Slash);}
	public Bry_bfr Add_byte_backslash()		{return Add_byte(Byte_ascii.Backslash);}
	public Bry_bfr Add_byte_quote()			{return Add_byte(Byte_ascii.Quote);}
	public Bry_bfr Add_byte_space()			{return Add_byte(Byte_ascii.Space);}
	public Bry_bfr Add_byte_nl()			{return Add_byte(Byte_ascii.Nl);}
	public Bry_bfr Add_byte_dot()			{return Add_byte(Byte_ascii.Dot);}
	public Bry_bfr Add_byte_colon()			{return Add_byte(Byte_ascii.Colon);}
	public Bry_bfr Add_byte(byte val) {
		int new_pos = bfr_len + 1;
		if (new_pos > bfr_max) Resize(bfr_len * 2);
		bfr[bfr_len] = val;
		bfr_len = new_pos;
		return this;
	}
	public Bry_bfr Add_byte_repeat(byte b, int len) {
		if (bfr_len + len > bfr_max) Resize((bfr_max + len) * 2);
		for (int i = 0; i < len; i++)
			bfr[i + bfr_len] = b;
		bfr_len += len;
		return this;
	}
	public Bry_bfr Add_byte_if_not_last(byte b) {
		if (bfr_len == 0 || (bfr_len > 0 && bfr[bfr_len - 1] == b)) return this;
		this.Add_byte(b);
		return this;
	}
	public Bry_bfr Add_u8_int(int val) {
		if (bfr_len + 4 > bfr_max) Resize((bfr_max + 4) * 2);
		int utf8_len = gplx.intl.Utf16_.Encode_int(val, bfr, bfr_len);
		bfr_len += utf8_len;
		return this;
	}
	public Bry_bfr Add_bool(boolean v) {return Add(v ? Const_bool_true : Const_bool_false);} public static final byte[] Const_bool_true = Bry_.new_a7("true"), Const_bool_false = Bry_.new_a7("false");
	public Bry_bfr Add_int_bool(boolean v) {return Add_int_fixed(v ? 1 : 0, 1);}
	public Bry_bfr Add_int_variable(int val) {
		int log10 = Int_.Log10(val);
		int slots = val > -1 ? log10 + 1 : log10 * -1 + 2;
		return Add_int(val, log10, slots);
	}
	public Bry_bfr Add_int_pad_bgn(byte pad_byte, int str_len, int val) {
		int digit_len	= Int_.DigitCount(val);
		int pad_len		= str_len - digit_len;
		if (pad_len > 0)	// note that this skips pad_len == 0, as well as guarding against negative pad_len; EX: pad(" ", 3, 1234) -> "1234"
			Add_byte_repeat(pad_byte, pad_len);
		Add_int_fixed(val, digit_len);
		return this;
	}
	public Bry_bfr Add_int_digits(int digits, int val)	{return Add_int(val, Int_.Log10(val), digits);}
	public Bry_bfr Add_int_fixed(int val, int digits) {return Add_int(val, Int_.Log10(val), digits);}
	public Bry_bfr Add_int(int val, int valLog, int arySlots) {
		int aryBgn = bfr_len, aryEnd = bfr_len + arySlots;
		if (aryEnd > bfr_max) Resize((aryEnd) * 2);
		if (val < 0) {
			bfr[aryBgn++] = Byte_ascii.Dash;
			val *= -1;		// make positive
			valLog *= -1;	// valLog will be negative; make positive
			arySlots -= 1;	// reduce slot by 1
		}
		if (valLog >= arySlots) {
			val %= Int_.Log10Ary[arySlots];
		}
		for (int i = 0; i < arySlots; i++) {
			int logIdx = arySlots - i - 1;
			int div = logIdx < Int_.Log10AryLen ? Int_.Log10Ary[logIdx] : Int_.MaxValue;
			bfr[aryBgn + i] = (byte)((val / div) + 48);
			val %= div;
		}
		bfr_len = aryEnd;
		return this;
	}
	public Bry_bfr Add_long_variable(long v) {int digitCount = Long_.DigitCount(v); return Add_long(v, digitCount, digitCount);}
	public Bry_bfr Add_long_fixed(long val, int digits) {return Add_long(val, Long_.DigitCount(val), digits);}
	protected Bry_bfr Add_long(long val, int digitCount, int arySlots) {
		int aryBgn = bfr_len, aryEnd = bfr_len + arySlots;
		if (aryEnd > bfr_max) Resize((aryEnd) * 2);
		if (val < 0) {
			bfr[aryBgn++] = Byte_ascii.Dash;
			val *= -1;		// make positive
			arySlots -= 1;	// reduce slot by 1
		}
		if (digitCount >= arySlots) {
			val %= Long_.Log10Ary[arySlots];
		}
		for (int i = 0; i < arySlots; i++) {
			int logIdx = arySlots - i - 1;
			long div = logIdx < Long_.Log10Ary_len ? Long_.Log10Ary[logIdx] : Long_.MaxValue;
			bfr[aryBgn + i] = (byte)((val / div) + 48);
			val %= div;
		}
		bfr_len = aryEnd;
		return this;
	}
	public Bry_bfr Add_bry_comma(byte[] v) {return Add_bry(Byte_ascii.Comma, v);}
	public Bry_bfr Add_bry(byte dlm, byte[] v) {
		if (v == null) return this;
		int v_len = v.length;
		for (int i = 0; i < v_len; i++) {
			if (i != 0) this.Add_byte(dlm);
			this.Add_int_variable(v[i]);
		}
		return this;
	}
	public Bry_bfr Add_bry_escape(byte quote_byte, byte[] escape, byte[] val, int bgn, int end) {	// used for xml_wtr; DATE:2015-04-09
		boolean clean = true;	// add with chunks of bytes instead of one-by-one
		for (int i = bgn; i < end; ++i) {
			byte b = val[i];
			if (clean) {
				if	(b == quote_byte) {
					clean = false;
					this.Add_mid(val, bgn, i);
					this.Add(escape);
				}
				else {}
			}
			else {
				if (b == quote_byte)	this.Add(escape);
				else					this.Add_byte(b);
			}
		}
		if (clean)
			Add(val);
		return this;
	}
	public Bry_bfr Add_str(String v) {return Add_str_u8(v);}
	public Bry_bfr Add_str_u8(String str) {
		try {
			int str_len = str.length();							
			int bry_len = Bry_.new_u8_by_len(str, str_len);
			if (bfr_len + bry_len > bfr_max) Resize((bfr_max + bry_len) * 2);
			Bry_.new_u8_write(str, str_len, bfr, bfr_len);
			bfr_len += bry_len;
			return this;
		}
		catch (Exception e) {throw Err_.new_exc(e, "core", "invalid UTF-8 sequence", "s", str);}
	}
	public Bry_bfr Add_str_a7(String str) {
		try {
			int bry_len = str.length();						
			if (bfr_len + bry_len > bfr_max) Resize((bfr_max + bry_len) * 2);
			for (int i = 0; i < bry_len; ++i) {
				char c = str.charAt(i);							
				if (c > 128) c = '?';
				bfr[i + bfr_len] = (byte)c;
			}
			bfr_len += bry_len;
			return this;
		}
		catch (Exception e) {throw Err_.new_exc(e, "core", "invalid UTF-8 sequence", "s", str);}
	}
	public Bry_bfr Add_kv_dlm(boolean line, String key, Object val) {
		this.Add_str_a7(key).Add_byte_colon().Add_byte_space();
		this.Add(Bry_.new_u8(Object_.Xto_str_strict_or_null_mark(val)));
		this.Add_byte(line ? Byte_ascii.Nl : Byte_ascii.Tab);
		return this;
	}
	public Bry_bfr Add_float(float f) {Add_str(Float_.Xto_str(f)); return this;}
	public Bry_bfr Add_double(double v) {Add_str(Double_.Xto_str(v)); return this;}
	public Bry_bfr Add_dte(DateAdp val) {return Add_dte_segs(val.Year(), val.Month(),val.Day(), val.Hour(), val.Minute(), val.Second(), val.Frac());}
	public Bry_bfr Add_dte_segs(int y, int M, int d, int H, int m, int s, int f) {		// yyyyMMdd HHmmss.fff
		if (bfr_len + 19      > bfr_max) Resize((bfr_len + 19) * 2);
		bfr[bfr_len +  0] = (byte)((y / 1000) + Bry_.Ascii_zero); y %= 1000;
		bfr[bfr_len +  1] = (byte)((y /  100) + Bry_.Ascii_zero); y %=  100;
		bfr[bfr_len +  2] = (byte)((y /   10) + Bry_.Ascii_zero); y %=   10;
		bfr[bfr_len +  3] = (byte)( y		  + Bry_.Ascii_zero);
		bfr[bfr_len +  4] = (byte)((M /   10) + Bry_.Ascii_zero); M %=  10;
		bfr[bfr_len +  5] = (byte)( M		  + Bry_.Ascii_zero);
		bfr[bfr_len +  6] = (byte)((d /   10) + Bry_.Ascii_zero); d %=  10;
		bfr[bfr_len +  7] = (byte)( d		  + Bry_.Ascii_zero);
		bfr[bfr_len +  8] = Byte_ascii.Space;
		bfr[bfr_len +  9] = (byte)((H /   10) + Bry_.Ascii_zero); H %=  10;
		bfr[bfr_len + 10] = (byte)( H		  + Bry_.Ascii_zero);
		bfr[bfr_len + 11] = (byte)((m /   10) + Bry_.Ascii_zero); m %=  10;
		bfr[bfr_len + 12] = (byte)( m		  + Bry_.Ascii_zero);
		bfr[bfr_len + 13] = (byte)((s /   10) + Bry_.Ascii_zero); s %=  10;
		bfr[bfr_len + 14] = (byte)( s		  + Bry_.Ascii_zero);
		bfr[bfr_len + 15] = Byte_ascii.Dot;
		bfr[bfr_len + 16] = (byte)((f /  100) + Bry_.Ascii_zero); f %= 100;
		bfr[bfr_len + 17] = (byte)((f /   10) + Bry_.Ascii_zero); f %=  10;
		bfr[bfr_len + 18] = (byte)( f		  + Bry_.Ascii_zero);
		bfr_len += 19;
		return this;
	}
	public Bry_bfr Add_dte_utc(int y, int M, int d, int H, int m, int s, int f) {	// yyyy-MM-ddTHH:mm:ssZ
		if (bfr_len + 20      > bfr_max) Resize((bfr_len + 20) * 2);
		bfr[bfr_len +  0] = (byte)((y / 1000) + Bry_.Ascii_zero); y %= 1000;
		bfr[bfr_len +  1] = (byte)((y /  100) + Bry_.Ascii_zero); y %=  100;
		bfr[bfr_len +  2] = (byte)((y /   10) + Bry_.Ascii_zero); y %=   10;
		bfr[bfr_len +  3] = (byte)( y		  + Bry_.Ascii_zero);
		bfr[bfr_len +  4] = Byte_ascii.Dash;
		bfr[bfr_len +  5] = (byte)((M /   10) + Bry_.Ascii_zero); M %=  10;
		bfr[bfr_len +  6] = (byte)( M		  + Bry_.Ascii_zero);
		bfr[bfr_len +  7] = Byte_ascii.Dash;
		bfr[bfr_len +  8] = (byte)((d /   10) + Bry_.Ascii_zero); d %=  10;
		bfr[bfr_len +  9] = (byte)( d		  + Bry_.Ascii_zero);
		bfr[bfr_len + 10] = Byte_ascii.Ltr_T;
		bfr[bfr_len + 11] = (byte)((H /   10) + Bry_.Ascii_zero); H %=  10;
		bfr[bfr_len + 12] = (byte)( H		  + Bry_.Ascii_zero);
		bfr[bfr_len + 13] = Byte_ascii.Colon;
		bfr[bfr_len + 14] = (byte)((m /   10) + Bry_.Ascii_zero); m %=  10;
		bfr[bfr_len + 15] = (byte)( m		  + Bry_.Ascii_zero);
		bfr[bfr_len + 16] = Byte_ascii.Colon;
		bfr[bfr_len + 17] = (byte)((s /   10) + Bry_.Ascii_zero); s %=  10;
		bfr[bfr_len + 18] = (byte)( s		  + Bry_.Ascii_zero);
		bfr[bfr_len + 19] = Byte_ascii.Ltr_Z;
		bfr_len += 20;
		return this;
	}
	public Bry_bfr Add_swap_ws(byte[] src) {return Add_swap_ws(src, 0, src.length);}
	public Bry_bfr Add_swap_ws(byte[] src, int bgn, int end) {
		int len = end - bgn;
		if (bfr_len + (len * 2) > bfr_max) Resize((bfr_max + (len * 2)) * 2);		
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Nl: 	bfr[bfr_len] = Byte_ascii.Backslash; bfr[bfr_len + 1] = Byte_ascii.Ltr_n;		bfr_len += 2; break;
				case Byte_ascii.Tab: 		bfr[bfr_len] = Byte_ascii.Backslash; bfr[bfr_len + 1] = Byte_ascii.Ltr_t;		bfr_len += 2; break;
				case Byte_ascii.Backslash: 	bfr[bfr_len] = Byte_ascii.Backslash; bfr[bfr_len + 1] = Byte_ascii.Backslash; bfr_len += 2; break;
				default:					bfr[bfr_len] = b; ++bfr_len; break;
			}
		}
		return this;
	}
	public Bry_bfr Add_str_pad_space_bgn(String v, int pad_max) {return Add_str_pad_space(v, pad_max, Bool_.N);}
	public Bry_bfr Add_str_pad_space_end(String v, int pad_max) {return Add_str_pad_space(v, pad_max, Bool_.Y);}
	Bry_bfr Add_str_pad_space(String v, int pad_max, boolean pad_end) {
		byte[] v_bry = Bry_.new_u8(v); 
		if (pad_end)	Add(v_bry);
		int pad_len = pad_max - v_bry.length;
		if (pad_len > 0) 
			Add_byte_repeat(Byte_ascii.Space, pad_len);
		if (!pad_end)	Add(v_bry);
		return this;
	}

	public Bry_bfr Add_obj(Object o) {
		if (o == null) return this;	// treat null as empty String;
		Class<?> o_type = o.getClass();
		if		(o_type == byte[].class)          Add((byte[])o);
		else if	(o_type == Integer.class)         Add_int_variable(Int_.cast_(o));    
		else if	(o_type == Byte.class)            Add_byte(Byte_.cast_(o));           
		else if	(o_type == Long.class)            Add_long_variable(Long_.cast_(o));  
		else if	(o_type == String.class)          Add_str((String)o);
		else if	(o_type == Bry_bfr.class)			Add_bfr_and_preserve((Bry_bfr)o);
		else if	(o_type == DateAdp.class)         Add_dte((DateAdp)o);
		else if	(o_type == Io_url.class)			Add(((Io_url)o).RawBry());
		else if	(o_type == Boolean.class)			Add_yn(Bool_.cast_(o));				
		else if	(o_type == Double.class)			Add_double(Double_.cast_(o));		
		else if	(o_type == Float.class)			Add_float(Float_.cast_(o));			
		else										((Bry_fmtr_arg)o).XferAry(this, 0);
		return this;
	}
	public Bry_bfr Add_obj_strict(Object o) {
		if (o == null) return this;	// treat null as empty String;
		Class<?> o_type = o.getClass();
		if		(o_type == byte[].class)          Add((byte[])o);
		else if	(o_type == Integer.class)         Add_int_variable(Int_.cast_(o));    
		else if	(o_type == Byte.class)            Add_byte(Byte_.cast_(o));           
		else if	(o_type == Long.class)            Add_long_variable(Long_.cast_(o));  
		else if	(o_type == String.class)          Add_str((String)o);
		else if	(o_type == Bry_bfr.class)			Add_bfr_and_preserve((Bry_bfr)o);
		else if	(o_type == DateAdp.class)         Add_dte((DateAdp)o);
		else if	(o_type == Io_url.class)			Add(((Io_url)o).RawBry());
		else if	(o_type == Boolean.class)			Add_bool(Bool_.cast_(o));			
		else if	(o_type == Double.class)			Add_double(Double_.cast_(o));		
		else if	(o_type == Float.class)			Add_float(Float_.cast_(o));			
		else										((Bry_fmtr_arg)o).XferAry(this, 0);
		return this;
	}
	public Bry_bfr Add_yn(boolean v) {Add_byte(v ? Byte_ascii.Ltr_y : Byte_ascii.Ltr_n); return this;}
	public Bry_bfr Add_base85_len_5(int v) {return Add_base85(v, 5);}
	public Bry_bfr Add_base85(int v, int pad)	{
		int new_len = bfr_len + pad;
		if (new_len > bfr_max) Resize((new_len) * 2);
		Base85_utl.XtoStrByAry(v, bfr, bfr_len, pad);
		bfr_len = new_len;
		return this;
	}
	public boolean Match_end_byt(byte b)		{return bfr_len == 0 ? false : bfr[bfr_len - 1] == b;}
	public boolean Match_end_byt_nl_or_bos()	{return bfr_len == 0 ? true : bfr[bfr_len - 1] == Byte_ascii.Nl;}
	public boolean Match_end_ary(byte[] ary)	{return Bry_.Match(bfr, bfr_len - ary.length, bfr_len, ary);}
	public Bry_bfr Insert_at(int add_pos, byte[] add_bry) {return Insert_at(add_pos, add_bry, 0, add_bry.length);}
	public Bry_bfr Insert_at(int add_pos, byte[] add_bry, int add_bgn, int add_end) {
		int add_len = add_end - add_bgn;
		int new_max = bfr_max + add_len;
		byte[] new_bfr = new byte[new_max];
		if (add_pos > 0)
			Bry_.Copy_by_pos	(bfr	,		0, add_pos, new_bfr, 0);
		Bry_.Copy_by_pos		(add_bry, add_bgn, add_end, new_bfr, add_pos);
		Bry_.Copy_by_pos		(bfr	, add_pos, bfr_len, new_bfr, add_pos + add_len);
		bfr = new_bfr;
		bfr_len += add_len;
		bfr_max = new_max;
		return this;
	}
	public Bry_bfr Delete_rng_to_bgn(int pos) {return Delete_rng(0, pos);}
	public Bry_bfr Delete_rng_to_end(int pos) {return Delete_rng(pos, bfr_len);}
	public Bry_bfr Delete_rng(int rng_bgn, int rng_end) {
		int rng_len = rng_end - rng_bgn;
		Bry_.Copy_by_pos(bfr, rng_end, bfr_len, bfr, rng_bgn);
		bfr_len -= rng_len;
		return this;
	}
	public Bry_bfr Del_by_1() {
		bfr_len -= 1; bfr[bfr_len] = 0; return this;
	}
	public Bry_bfr Del_by(int count) {
		int new_len = bfr_len - count;
		if (new_len > -1) bfr_len = new_len;
		return this;
	}
	public Bry_bfr Trim_end(byte trim_byte) {
		if (bfr_len == 0) return this;
		int count = 0;
		for (int i = bfr_len - 1; i > -1; --i) {
			byte b = bfr[i];
			if (b == trim_byte)
				++count;
			else
				break;
		}
		if (count > 0)
			this.Del_by(count);
		return this;
	}
	public Bry_bfr Concat_skip_empty(byte[] dlm, byte[]... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			byte[] itm = ary[i];
			boolean itm_has_bytes = Bry_.Len_gt_0(itm);
			if (	i != 0
				&&	itm_has_bytes
				&&	bfr_len > 0
				)
				this.Add(dlm);
			if (itm_has_bytes)
				this.Add(itm);
		}
		return this;
	}
	public boolean Eq(byte b) {return bfr_len == 1 && bfr[0] == b;}
	public byte[] Xto_bry() {return bfr_len == 0 ? Bry_.Empty : Bry_.Mid(bfr, 0, bfr_len);}
	public byte[] Xto_bry_and_reset(int v) {
		byte[] rv = Xto_bry();
		this.Clear().Reset_if_gt(v);
		return rv;
	}
	public byte[] Xto_bry_and_clear_and_trim() {return Xto_bry_and_clear_and_trim(true, true, Bry_.Trim_ary_ws);}
	public byte[] Xto_bry_and_clear_and_trim(boolean trim_bgn, boolean trim_end, byte[] trim_bry) {
		byte[] rv = Bry_.Trim(bfr, 0, bfr_len, trim_bgn, trim_end, trim_bry);
		this.Clear();
		return rv;
	}
	public byte[] Xto_bry_and_clear() {
		byte[] rv = Xto_bry();
		this.Clear();
		if (reset > 0) Reset_if_gt(reset);
		return rv;
	}
	public String Xto_str()								{return String_.new_u8(Xto_bry());}
	public String Xto_str_by_pos(int bgn, int end)		{return String_.new_u8(Xto_bry(), bgn, end);}
	public String Xto_str_and_clear()					{return String_.new_u8(Xto_bry_and_clear());}
	public String Xto_str_and_clear_and_trim()			{return String_.new_u8(Xto_bry_and_clear_and_trim());}
	public int XtoIntAndClear(int or) {int rv = XtoInt(or); this.Clear(); return rv;}
	public int XtoInt(int or) {
		switch (bfr_len) {
			case 0: return or;
			case 1: {
				byte b = bfr[0];
				return Byte_ascii.Is_num(b) ? b - Byte_ascii.Num_0 : or;
			}
			default:
				long rv = 0, mult = 1;
				for (int i = bfr_len - 1; i > -1; i--) {
					byte b = bfr[i];
					if (!Byte_ascii.Is_num(b)) return or;
					long dif = (b - Byte_ascii.Num_0 ) * mult;
					long new_val = rv + dif;
					if (new_val > Int_.MaxValue) return or;	// if number is > 2^32 consider error (int overflow); return or; DATE:2014-06-10
					rv = new_val;
					mult *= 10;
				}
				return (int)rv;
		}
	}
	public void Rls() {
		bfr = null;
		this.Mkr_rls();
	}
	@Override public int hashCode() {return Bry_obj_ref.CalcHashCode(bfr, 0, bfr_len);}
	@Override public boolean equals(Object obj) {return obj == null ? false : Bry_.Match(bfr, 0, bfr_len, ((Bry_obj_ref)obj).Val());}	// NOTE: strange, but null check needed; throws null error; PAGE:c:File:Eug�ne_Delacroix_-_La_libert�_guidant_le_peuple.jpg
	public void Resize(int v) {
		bfr_max = v;
		bfr = Bry_.Resize(bfr, 0, v);
	}
	@gplx.Internal protected int		Mkr_idx()			{return mkr_idx;} private int mkr_idx = -1;
	@gplx.Internal protected boolean		Mkr_idx_is_null()	{return mkr_idx == -1;}
	@gplx.Internal protected int		Bfr_max()			{return bfr_max;} private int bfr_max;
	@gplx.Internal protected Bry_bfr Mkr_init(Bry_bfr_mkr_mgr mkr_mgr, int itm) {
		synchronized (this) {
			this.mkr_mgr = mkr_mgr; this.mkr_idx = itm;
		}
		return this;
	} 
        public static Bry_bfr new_()			{return new Bry_bfr(16);}
        public static Bry_bfr new_(int v)		{return new Bry_bfr(v);}
        public static Bry_bfr reset_(int v)		{return new Bry_bfr(16).Reset_(v);}	// PERF: set initial size to 16, not reset val; allows for faster "startup"; DATE:2014-06-14
	Bry_bfr(int bfr_max) {
		this.bfr_max = bfr_max;
		this.bfr = new byte[bfr_max];
	}
}
