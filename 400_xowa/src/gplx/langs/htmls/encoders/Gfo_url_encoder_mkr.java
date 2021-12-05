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
package gplx.langs.htmls.encoders;
import gplx.core.btries.*;
import gplx.objects.strings.AsciiByte;
public class Gfo_url_encoder_mkr {
	private Gfo_url_encoder_itm[] encode_ary, decode_ary;
	private Gfo_url_encoder anchor_encoder;
	private Gfo_url_encoder_itm_hex encoder_hex;
	private byte bicode_mark;
	public Gfo_url_encoder_mkr Init(byte bicode_mark) {
		this.bicode_mark = bicode_mark;
		encode_ary = new Gfo_url_encoder_itm[256]; decode_ary = new Gfo_url_encoder_itm[256];
		encoder_hex = new Gfo_url_encoder_itm_hex(bicode_mark);
		for (int i = 0; i < 256; ++i) {
			encode_ary[i] = encoder_hex;                       // default encode to hex
			decode_ary[i] = Gfo_url_encoder_itm_same.Instance; // default decode to same; needed for files; EX: A!%21.png -> A!!.png;
		}
		decode_ary[bicode_mark] = encoder_hex;
		return this;
	}
	public Gfo_url_encoder_mkr Init__encode_hex(int... ary) {
		for (int i = 0; i < ary.length; i++) {
			int idx = ary[i];
			encode_ary[idx] = encoder_hex;
		}
		return this;
	}
	public Gfo_url_encoder_mkr Init__same__rng(int bgn, int end) {
		for (int i = bgn; i <= end; ++i)
			encode_ary[i] = decode_ary[i] = Gfo_url_encoder_itm_same.Instance;
		return this;
	}
	public Gfo_url_encoder_mkr Init__same__many(int... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			int idx = ary[i];
			encode_ary[idx] = decode_ary[idx] = Gfo_url_encoder_itm_same.Instance;
		}
		return this;
	}
	public Gfo_url_encoder_mkr Init_common(boolean encode_colon) {
		Init__same__rng(AsciiByte.Num0, AsciiByte.Num9);
		Init__same__rng(AsciiByte.Ltr_A, AsciiByte.Ltr_Z);
		Init__same__rng(AsciiByte.Ltr_a, AsciiByte.Ltr_z);
		Init__same__many(AsciiByte.Dash, AsciiByte.Dot, AsciiByte.Underline);
		if (encode_colon) Init__same__many(AsciiByte.Colon);
		return this;
	}
	public Gfo_url_encoder_mkr Init__decode_mark(byte decode_mark) {
		decode_ary[decode_mark & 0xff] = encoder_hex;// PATCH.JAVA:need to convert to unsigned byte
		return this;
	}
	public Gfo_url_encoder_mkr Init__diff__one(byte src, byte trg) {
		Gfo_url_encoder_itm_diff itm = new Gfo_url_encoder_itm_diff(src, trg);
		encode_ary[src] = decode_ary[trg] = itm;
		return this;
	}
	public Gfo_url_encoder_mkr Init__diff__many(int... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			int idx = ary[i];
			encode_ary[idx] = decode_ary[idx] = encoder_hex;
		}
		decode_ary[bicode_mark] = encoder_hex;
		return this;
	}
	public Gfo_url_encoder_mkr Init__html_ent(byte src, Btrie_slim_mgr trie, boolean encode_unknown_amp) {
		Gfo_url_encoder_itm_html_ent itm = new Gfo_url_encoder_itm_html_ent(trie, encode_unknown_amp);
		encode_ary[src] = itm;
		return this;
	}
	public Gfo_url_encoder_mkr Init__anchor_encoder(Gfo_url_encoder v) {this.anchor_encoder = v; return this;}
	public Gfo_url_encoder Make() {
		Gfo_url_encoder rv = new Gfo_url_encoder(encode_ary, decode_ary, anchor_encoder);
		encode_ary = decode_ary = null; anchor_encoder = null;
		return rv;
	}
}
