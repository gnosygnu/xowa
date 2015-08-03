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
import gplx.core.btries.*;
import gplx.xowa.parsers.amps.*;
public class Url_encoder implements Url_encoder_interface {
	private Url_encoder_itm[] encode_ary = new Url_encoder_itm[256], decode_ary = new Url_encoder_itm[256];
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private Url_encoder anchor_encoder = null;
	private Object thread_lock = new Object();
	public void Itms_ini(byte primary_encode_marker) {
		Url_encoder_itm_hex hex = new Url_encoder_itm_hex(primary_encode_marker);
		for (int i = 0; i < 256; i++) {
			encode_ary[i] = hex;						// default encode to hex
			decode_ary[i] = Url_encoder_itm_same._;		// default decode to same; needed for files; EX: A!%21.png -> A!!.png;
		}
		decode_ary[primary_encode_marker] = hex;
	}
	public void Itms_raw_diff_many(byte primary_encode_marker, int... ary) {
		Url_encoder_itm_hex hex = new Url_encoder_itm_hex(primary_encode_marker);
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			encode_ary[ary[i]] = hex;
			decode_ary[ary[i]] = hex;
		}
		decode_ary[primary_encode_marker] = hex;
	}
	public void Itms_decode_marker(byte decode_marker) {
		Url_encoder_itm_hex hex = new Url_encoder_itm_hex(decode_marker);
		decode_ary[decode_marker & 0xff] = hex;// PATCH.JAVA:need to convert to unsigned byte
	}
	public void Itms_decode_diff(byte orig, byte repl) {
		decode_ary[orig & 0xff] = new Url_encoder_itm_diff(orig, repl);// PATCH.JAVA:need to convert to unsigned byte
	}
	public void Itms_raw_same_rng(int bgn, int end) {
		for (int i = bgn; i <= end; i++) {
			encode_ary[i] = Url_encoder_itm_same._;
			decode_ary[i] = Url_encoder_itm_same._;
		}
	}
	public Url_encoder Itms_raw_same_many(int... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			encode_ary[ary[i]] = Url_encoder_itm_same._;
			decode_ary[ary[i]] = Url_encoder_itm_same._;
		}
		return this;
	}
	public void Itms_raw_html_ent(byte src, Btrie_slim_mgr trie) {
		Url_encoder_itm_html_ent itm = new Url_encoder_itm_html_ent(trie);
		encode_ary[src] = itm;
	}
	public Url_encoder Itms_raw_diff(byte src, byte trg) {
		Url_encoder_itm_diff itm = new Url_encoder_itm_diff(src, trg);
		encode_ary[src] = itm;
		decode_ary[trg] = itm;
		return this;
	}
	public byte[] Encode_http(Io_url url) {
		synchronized (thread_lock) {
			tmp_bfr.Add(Io_url.Http_file_bry);
			Encode(tmp_bfr, url.RawBry());
			return tmp_bfr.Xto_bry_and_clear();
		}
	}
	public String Encode_str(String str)					{
		synchronized (thread_lock) {
			byte[] bry = Bry_.new_u8(str); Encode(tmp_bfr, bry, 0, bry.length); return tmp_bfr.Xto_str_and_clear();
		}
	}
	public byte[] Encode_bry(String str)					{
		synchronized (thread_lock) {
			byte[] bry = Bry_.new_u8(str); Encode(tmp_bfr, bry, 0, bry.length); return tmp_bfr.Xto_bry_and_clear();
		}
	}
	public byte[] Encode(byte[]	bry)						{Encode(tmp_bfr, bry, 0, bry.length); return tmp_bfr.Xto_bry_and_clear();}
	public Bry_bfr Encode(Bry_bfr bfr, byte[] bry)	{Encode(bfr,	 bry, 0, bry.length); return bfr;}
	public void Encode(Bry_bfr bfr, byte[] bry, int bgn, int end) {
		synchronized (thread_lock) {
			for (int i = bgn; i < end; i++) {
				byte b = bry[i];
				if (anchor_encoder != null && b == Byte_ascii.Hash) {
					bfr.Add_byte(Byte_ascii.Hash);
					anchor_encoder.Encode(bfr, bry, i + 1, end);
					break;
				}
				Url_encoder_itm itm = encode_ary[b & 0xff];// PATCH.JAVA:need to convert to unsigned byte
				i += itm.Encode(bfr, bry, end, i, b);
			}
		}
	}
	public String Decode_str(String str) {
		synchronized (thread_lock) {
			byte[] bry = Bry_.new_u8(str); Decode(bry, 0, bry.length, tmp_bfr, true); return tmp_bfr.Xto_str_and_clear();
		}
	}
	public byte[] Decode(byte[] bry)								{return Decode(tmp_bfr, bry,   0, bry.length);}
	public byte[] Decode(byte[] bry, int bgn, int end)				{return Decode(tmp_bfr, bry, bgn,        end);}
	public byte[] Decode(Bry_bfr bfr, byte[] bry, int bgn, int end)	{Decode(bry, bgn,        end, bfr    , false); return bfr.Xto_bry_and_clear();}
	public byte[] Decode_lax(byte[] bry) {
		synchronized (thread_lock) {
			Decode(bry, 0, bry.length, tmp_bfr, false); return tmp_bfr.Xto_bry_and_clear();
		}
	}
	public void Decode(byte[] bry, int bgn, int end, Bry_bfr bfr, boolean fail_when_invalid) {
		synchronized (thread_lock) {
			for (int i = bgn; i < end; i++) {
				byte b = bry[i];
				if (anchor_encoder != null && b == Byte_ascii.Hash) {
					bfr.Add_byte(Byte_ascii.Hash);
					anchor_encoder.Decode(bry, i + 1, end, bfr, false);
					break;
				}
				Url_encoder_itm itm = decode_ary[b & 0xff];// PATCH.JAVA:need to convert to unsigned byte
				i += itm.Decode(bfr, bry, end, i, b, fail_when_invalid);
			}
		}
	}
	private static void mediawiki_base(Url_encoder rv, boolean encode_colon) {
		rv.Itms_raw_same_rng(Byte_ascii.Num_0, Byte_ascii.Num_9);
		rv.Itms_raw_same_rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z);
		rv.Itms_raw_same_rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z);
		rv.Itms_raw_same_many(Byte_ascii.Dash, Byte_ascii.Dot, Byte_ascii.Underline);
		if (encode_colon)
			rv.Itms_raw_same_many(Byte_ascii.Colon);
	}
	public static Url_encoder new_html_id_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Dot);
		mediawiki_base(rv, true);
		rv.Itms_decode_marker(Byte_ascii.Dot);
		rv.Itms_raw_diff(Byte_ascii.Space, Byte_ascii.Underline);
		rv.Itms_raw_html_ent(Byte_ascii.Amp, Xop_amp_trie._);
		return rv;
	}
	public static Url_encoder new_http_url_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		mediawiki_base(rv, false);
		rv.Itms_raw_diff(Byte_ascii.Space, Byte_ascii.Plus);
		return rv;
	}
	public static Url_encoder new_http_url_ttl_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		mediawiki_base(rv, true);
		return rv;
	}
	public static Url_encoder new_http_url_space_is_space() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		mediawiki_base(rv, true);
		return rv;
	}
	public static Url_encoder new_fsys_lnx_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		mediawiki_base(rv, true);
		rv.Itms_raw_same_many(Byte_ascii.Slash);
		rv.Itms_raw_diff(Byte_ascii.Backslash, Byte_ascii.Slash);
		return rv;
	}
	public static Url_encoder new_fsys_wnt_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		rv.Itms_raw_same_rng(Byte_ascii.Num_0, Byte_ascii.Num_9);
		rv.Itms_raw_same_rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z);
		rv.Itms_raw_same_rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z);
		rv.Itms_raw_same_many
		(	Byte_ascii.Bang, Byte_ascii.At, Byte_ascii.Hash, Byte_ascii.Dollar, Byte_ascii.Percent, Byte_ascii.Pow, Byte_ascii.Amp
		,	Byte_ascii.Plus, Byte_ascii.Eq, Byte_ascii.Underline, Byte_ascii.Dash
		,	Byte_ascii.Dot, Byte_ascii.Comma
		,	Byte_ascii.Tick, Byte_ascii.Tilde, Byte_ascii.Brack_bgn, Byte_ascii.Brack_end, Byte_ascii.Curly_bgn, Byte_ascii.Curly_end);
		return rv;
	}
	public static Url_encoder new_file_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		mediawiki_base(rv, true);
		return rv;
	}
	public static Url_encoder new_gfs_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		rv.Itms_raw_same_many(Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Apos, Byte_ascii.Semic);
		mediawiki_base(rv, true);
		return rv;
	}
	public static Url_encoder new_html_href_mw_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		mediawiki_base(rv, true);
		rv.Itms_raw_diff(Byte_ascii.Space, Byte_ascii.Underline);
		rv.Itms_raw_same_many(Byte_ascii.Semic, Byte_ascii.At, Byte_ascii.Dollar, Byte_ascii.Bang, Byte_ascii.Star
				, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Comma, Byte_ascii.Slash, Byte_ascii.Colon
				, Byte_ascii.Hash// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
				);
		rv.anchor_encoder = new_html_id_();
		return rv;
	}
	public static Url_encoder new_html_href_quotes_() {
		Url_encoder rv = new Url_encoder();
		rv.Itms_ini(Byte_ascii.Percent);
		rv.Itms_raw_same_rng(0, 255);													// default everything to same;
		rv.Itms_raw_diff_many(Byte_ascii.Percent
		, Byte_ascii.Apos, Byte_ascii.Quote, Byte_ascii.Lt, Byte_ascii.Gt);				// encode ', ", <, >
		rv.Itms_raw_diff(Byte_ascii.Space, Byte_ascii.Underline);						// convert " " to "_"
		return rv;
	}
}
interface Url_encoder_itm {
	int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b);
	int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid);
}
class Url_encoder_itm_same implements Url_encoder_itm {
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {bfr.Add_byte(b); return 0;}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {bfr.Add_byte(b); return 0;}
	public static final Url_encoder_itm _ = new Url_encoder_itm_same();
} 
class Url_encoder_itm_diff implements Url_encoder_itm {
	public Url_encoder_itm_diff(byte orig, byte repl) {this.orig = orig; this.repl = repl;} private byte orig, repl;
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {bfr.Add_byte(repl); return 0;}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {bfr.Add_byte(orig); return 0;}
} 
class Url_encoder_itm_hex implements Url_encoder_itm {
	public Url_encoder_itm_hex(byte encode_marker) {this.encode_marker = encode_marker;} private byte encode_marker;
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {Encode_byte(b, bfr, encode_marker); return 0;}
	public static void Encode_byte(byte b, Bry_bfr bfr, byte encode_marker) {
		int b_int = b & 0xFF;// PATCH.JAVA:need to convert to unsigned byte
		bfr.Add_byte(encode_marker);
		bfr.Add_byte(HexBytes[b_int >> 4]);
		bfr.Add_byte(HexBytes[b_int & 15]);
	}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {
		if (idx + 2 >= end) {
			if (fail_when_invalid) throw Err_.new_wo_type("decode needs 3 bytes", "idx", idx, "len", end, "snip", String_.new_u8(Bry_.Mid_by_len_safe(src, idx, 3)));
			else {
				bfr.Add_byte(b);
				return 0;
			}
		}
		int hex_val = Int_.Xto_int_hex(src[idx + 1]);
		if (hex_val == -1) {	// invalid hex byte; EX: %GC; DATE:2014-04-10
			bfr.Add_byte(b);
			return 0;
		}
		int v_0 = hex_val * 16;
		if (v_0 != -1) {
			int v_1 = Int_.Xto_int_hex(src[idx + 2]);
			if (v_1 != -1) {
				bfr.Add_byte((byte)(v_0 + v_1));
				return 2;
			}
		}
		if (fail_when_invalid)
			throw Err_.new_wo_type("decode is invalid", "idx", idx, "snip", String_.new_u8(Bry_.Mid_by_len_safe(src, idx, 3)));
		else {
			bfr.Add_byte(b);
			return 0;
		}
	}
	public static final byte[] HexBytes = new byte[] 
	{	Byte_ascii.Num_0, Byte_ascii.Num_1, Byte_ascii.Num_2, Byte_ascii.Num_3, Byte_ascii.Num_4, Byte_ascii.Num_5, Byte_ascii.Num_6, Byte_ascii.Num_7
	,	Byte_ascii.Num_8, Byte_ascii.Num_9, Byte_ascii.Ltr_A, Byte_ascii.Ltr_B, Byte_ascii.Ltr_C, Byte_ascii.Ltr_D, Byte_ascii.Ltr_E, Byte_ascii.Ltr_F
	};
} 
class Url_encoder_itm_html_ent implements Url_encoder_itm {
	public Url_encoder_itm_html_ent(Btrie_slim_mgr amp_trie) {this.amp_trie = amp_trie;} Btrie_slim_mgr amp_trie;
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {
		++idx;					// b is &; get next character afterwards
		if (idx == end) {		// & is last char; return
			Url_encoder_itm_hex.Encode_byte(Byte_ascii.Amp, bfr, Byte_ascii.Dot);
			return 0;
		}
		b = src[idx];
		Object o = amp_trie.Match_bgn_w_byte(b, src, idx, end);
		if (o == null) {	// unknown entity (EX:&unknown;); return &;
			Url_encoder_itm_hex.Encode_byte(Byte_ascii.Amp, bfr, Byte_ascii.Dot);
			return 0;
		}
		else {
			Xop_amp_trie_itm itm = (Xop_amp_trie_itm)o;
			byte[] bry_u8 = itm.U8_bry();	// NOTE: must utf8 encode val; EX: &nbsp; is 160 but must become 192,160
			for (int i = 0; i < bry_u8.length; i++)
				Url_encoder_itm_hex.Encode_byte(bry_u8[i], bfr, Byte_ascii.Dot);
			return itm.Xml_name_bry().length - 1;	// -1 to ignore & in XmlEntityName
		}			
	}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {
		bfr.Add_byte(b); return 0;
	}
} 
