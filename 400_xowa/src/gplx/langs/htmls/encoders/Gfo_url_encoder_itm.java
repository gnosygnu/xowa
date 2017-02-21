/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.htmls.encoders; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*;
import gplx.langs.htmls.entitys.*;
public interface Gfo_url_encoder_itm {
	int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b);
	int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid);
}
class Gfo_url_encoder_itm_same implements Gfo_url_encoder_itm {
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {bfr.Add_byte(b); return 0;}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {bfr.Add_byte(b); return 0;}
	public static final    Gfo_url_encoder_itm Instance = new Gfo_url_encoder_itm_same();	// TS.static
} 
class Gfo_url_encoder_itm_diff implements Gfo_url_encoder_itm {
	private final    byte orig, repl;
	public Gfo_url_encoder_itm_diff(byte orig, byte repl) {this.orig = orig; this.repl = repl;}
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {bfr.Add_byte(repl); return 0;}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {bfr.Add_byte(orig); return 0;}
} 
class Gfo_url_encoder_itm_hex implements Gfo_url_encoder_itm {
	private final    byte encode_marker;
	public Gfo_url_encoder_itm_hex(byte encode_marker) {this.encode_marker = encode_marker;}
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
		int hex_val = Int_.To_int_hex(src[idx + 1]);
		if (hex_val == -1) {	// invalid hex byte; EX: %GC; DATE:2014-04-10
			bfr.Add_byte(b);
			return 0;
		}
		int v_0 = hex_val * 16;
		if (v_0 != -1) {
			int v_1 = Int_.To_int_hex(src[idx + 2]);
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
	public static final    byte[] HexBytes = new byte[] 
	{	Byte_ascii.Num_0, Byte_ascii.Num_1, Byte_ascii.Num_2, Byte_ascii.Num_3, Byte_ascii.Num_4, Byte_ascii.Num_5, Byte_ascii.Num_6, Byte_ascii.Num_7
	,	Byte_ascii.Num_8, Byte_ascii.Num_9, Byte_ascii.Ltr_A, Byte_ascii.Ltr_B, Byte_ascii.Ltr_C, Byte_ascii.Ltr_D, Byte_ascii.Ltr_E, Byte_ascii.Ltr_F
	};
} 
class Gfo_url_encoder_itm_html_ent implements Gfo_url_encoder_itm {
	private final    Btrie_slim_mgr amp_trie;
	public Gfo_url_encoder_itm_html_ent(Btrie_slim_mgr amp_trie) {this.amp_trie = amp_trie;}
	public int Encode(Bry_bfr bfr, byte[] src, int end, int idx, byte b) {
		++idx;					// b is &; get next character afterwards
		if (idx == end) {		// & is last char; return
			Gfo_url_encoder_itm_hex.Encode_byte(Byte_ascii.Amp, bfr, Byte_ascii.Dot);
			return 0;
		}
		b = src[idx];
		Object o = amp_trie.Match_bgn_w_byte(b, src, idx, end);
		if (o == null) {	// unknown entity (EX:&unknown;); return &;
			Gfo_url_encoder_itm_hex.Encode_byte(Byte_ascii.Amp, bfr, Byte_ascii.Dot);
			return 0;
		}
		else {
			Gfh_entity_itm itm = (Gfh_entity_itm)o;
			byte[] bry_u8 = itm.U8_bry();	// NOTE: must utf8 encode val; EX: &nbsp; is 160 but must become 192,160
			for (int i = 0; i < bry_u8.length; i++)
				Gfo_url_encoder_itm_hex.Encode_byte(bry_u8[i], bfr, Byte_ascii.Dot);
			return itm.Xml_name_bry().length - 1;	// -1 to ignore & in XmlEntityName
		}			
	}
	public int Decode(Bry_bfr bfr, byte[] src, int end, int idx, byte b, boolean fail_when_invalid) {
		bfr.Add_byte(b); return 0;
	}
} 
