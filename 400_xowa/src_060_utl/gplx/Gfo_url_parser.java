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
import gplx.xowa.net.*;
public class Gfo_url_parser {
	private boolean pass = true;
	private Gfo_url url;
	private ListAdp segs = ListAdp_.new_(), args = ListAdp_.new_();
	private Url_encoder encoder = Url_encoder.new_html_href_mw_().Itms_raw_same_many(Byte_ascii.Underline); private Hash_adp_bry protocols = Hash_adp_bry.ci_ascii_();	// ASCII:url_protocol; EX:"http:", "ftp:", etc
	public Gfo_url_parser() {
		Init_protocols(Xoo_protocol_itm.Ary());
		Init_protocol(Xoo_protocol_itm.Tid_file, Xoo_protocol_itm.Str_file);
	}
	public void Init_protocol(byte tid, String protocol) {Init_protocols(new Xoo_protocol_itm(tid, protocol));}
	public void Init_protocols(Xoo_protocol_itm... itms) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Xoo_protocol_itm itm = itms[i];
			byte[] key = itm.Key_w_colon_bry();
			if (protocols.Has(key)) continue;	// NOTE: must check if protocol exists, else test will fail
			protocols.Add(key, itm);
		}
	}
	public byte Relative_url_protocol() {return relative_url_protocol;} public Gfo_url_parser Relative_url_protocol_(byte v) {relative_url_protocol = v; return this;} private byte relative_url_protocol = Xoo_protocol_itm.Tid_http;
	public byte[] Relative_url_protocol_bry() {return Xoo_protocol_itm.Ary()[relative_url_protocol].Key_w_colon_bry();}
	public void Parse_site_fast(Gfo_url_site_data site_data, byte[] src, int bgn, int end) {
		int pos = bgn; boolean rel = false;
		if (pos + 1 < end && src[pos] == Byte_ascii.Slash && src[pos + 1] == Byte_ascii.Slash) {	// starts with "//"
			pos += 2;
			rel = true;
		}
		if (!rel) {	// search for ":"; NOTE: only search if not rel; i.e.: "//"
			int colon_pos = Bry_finder.Find_fwd(src, Byte_ascii.Colon, pos, end);	// no colon found; EX: "//a.org/b"; "a.org/b"
			if (colon_pos != Bry_.NotFound)									// colon found; EX: "http://" or "https://"
				pos = colon_pos + Int_.Const_dlm_len;
			if (pos < end && src[pos] == Byte_ascii.Slash) {					// skip slash after colon
				pos += 1;
				if (pos < end && src[pos] == Byte_ascii.Slash)					// skip 2nd slash after colon
					pos += 1;
			}
		}
		int slash_pos = Bry_finder.Find_fwd(src, Byte_ascii.Slash, pos, end);
		if (slash_pos == Bry_.NotFound)									// no terminating slash; EX: http://a.org
			slash_pos = end;
		slash_pos = Bry_.Trim_end_pos(src, slash_pos);
		site_data.Atrs_set(rel, pos, slash_pos);
	}
	public boolean Parse(Gfo_url url, byte[] src, int bgn, int end) {
		this.url = url;
		url.Ini_(src);
		pass = true;
		segs.Clear();
		args.Clear();
		int colon_pos = Bry_finder.Find_fwd(src, Byte_ascii.Colon, bgn, end);
		Object protocol_obj = colon_pos == Bry_.NotFound ? null : protocols.Get_by_mid(src, bgn, colon_pos + 1);	// +1 to include colon
		if (protocol_obj != null) {
			Xoo_protocol_itm protocol_itm = (Xoo_protocol_itm)protocol_obj;
			url.Protocol_bry_(protocol_itm.Key_w_colon_bry());
			url.Protocol_tid_(protocol_itm.Tid());
			Parse_site(src, protocol_itm.Key_w_colon_bry().length, end);				
		}
		else {
			int pos = bgn;
			boolean loop = true;
			while (loop) {
				if (pos == end) {
					encoder.Decode(src, bgn, end, tmp_bfr, false);
					url.Site_(tmp_bfr.XtoAryAndClear());
					url.Err_(Gfo_url.Err_protocol_missing); pass = false;
					break;
				}
				byte b = src[pos];
				switch (b) {
					case Byte_ascii.Colon:
//							Segs_add(src, bgn, end);
						Parse_segs(src, bgn, end);
						loop = false;
						url.Err_(Gfo_url.Err_protocol_missing); pass = false;
						loop = false;
						break;
					case Byte_ascii.Question:
						Segs_add(src, bgn, pos);
						Parse_args(src, pos + 1, end);
						loop = false;
						url.Err_(Gfo_url.Err_protocol_missing); pass = false;
						break;
					case Byte_ascii.Hash:
						Segs_add(src, bgn, pos);
						Parse_anchor(src, pos + 1, end);
						slash_prv = pos;
						loop = false;
						url.Err_(Gfo_url.Err_protocol_missing); pass = false;
						break;
					case Byte_ascii.Slash:
						if (pos == 0 && pos + 1 < end && src[pos + 1] == Byte_ascii.Slash) {	// starts with "//"
							encoder.Decode(src, bgn, pos, tmp_bfr, false);
							url.Site_(tmp_bfr.XtoAryAndClear());
							url.Protocol_is_relative_(true);
							url.Protocol_tid_(relative_url_protocol);
							byte[] protocol_bry = Xoo_protocol_itm.Ary()[relative_url_protocol].Key_w_colon_bry();
							url.Protocol_bry_(protocol_bry);
							Parse_site(src, 2, end);	// 2=//
							loop = false;
						}
						else {																	// has "/"
							encoder.Decode(src, bgn, pos, tmp_bfr, false);
							url.Site_(tmp_bfr.XtoAryAndClear());
							Parse_segs(src, pos + 1, end);
							loop = false;
							url.Err_(Gfo_url.Err_protocol_missing); pass = false;
						}
						break;
					default:
						break;
				}			
				++pos;
			}
		}
		return pass;
	}
	private void Parse_site(byte[] src, int bgn, int end) {
		int dot_count = 0, dot_pos_0 = -1, dot_pos_1 = -1;
		boolean loop = true;
		byte b = Byte_ascii.Nil;
		while (loop) {
			if (bgn == end) {
				url.Err_(Gfo_url.Err_site_missing);
				break;
			}
			b = src[bgn];
			if (b == Byte_ascii.Slash)
				++bgn;
			else
				break;
		}
		int pos = bgn;
		while (loop) {
			switch (b) {
				case Byte_ascii.Dot:
					switch (dot_count) {
						case 0: dot_pos_0 = pos; break;
						case 1: dot_pos_1 = pos; break;
					}
					++dot_count;
					break;
				case Byte_ascii.Slash:
					Site_set(src, bgn, pos, dot_count, dot_pos_0, dot_pos_1);
					Parse_segs(src, pos + 1, end);
					loop = false;
					break;
				default:
					break;
			}
			if (!loop) break;
			++pos;
			if (pos >= end) {	// NOTE: >= needed b/c sometimes "xowa-cmd:", 5 passed in
				Site_set(src, bgn, end, dot_count, dot_pos_0, dot_pos_1);
				break;
			}
			b = src[pos];
		}		
	}
	int slash_prv;
	private void Parse_segs(byte[] src, int bgn, int end) {
		if (bgn == end) return;
		int pos = bgn;
		slash_prv = bgn;
		boolean loop = true;
		while (loop) {
			if (pos == end) {
				if (slash_prv < pos) Segs_add(src, slash_prv, end);
				break;
			}
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Question:
					Segs_add(src, slash_prv, pos);
					Parse_args(src, pos + 1, end);
					loop = false;
					break;
				case Byte_ascii.Hash:
					Segs_add(src, slash_prv, pos);
					Parse_anchor(src, pos + 1, end);
					slash_prv = pos;
					loop = false;
					break;
				case Byte_ascii.Slash:
					if (slash_prv != pos)	// HACK: handles urls of form "/wiki//A"; treat 2nd / as part of "/A"
						Segs_add(src, slash_prv, pos);
					break;
				default:
					break;
			}			
			++pos;
		}
		url.Segs_((byte[][])segs.XtoAry(byte[].class));
	}
	private void Parse_anchor(byte[] src, int bgn, int end) {		
		if (bgn == end) return;
		int pos = bgn;
		boolean loop = true;
		while (loop) {
			if (pos == end) {
				Anchor_set(src, bgn, pos);
				break;
			}
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Question:
					Anchor_set(src, bgn, pos);
					Parse_args(src, pos + 1, end);
					loop = false;
					break;
				//case Byte_ascii.Slash:	// NOTE: do not handle slash (by trying to parse segs); will cause anchor to fail; EX:A/b#c/d
//					case Byte_ascii.Slash:
//						Anchor_set(src, bgn, pos);
//						Parse_segs(src, pos + 1, end);
//						break;
				default:
					break;
			}			
			++pos;
		}
	}
	private void Parse_args(byte[] src, int bgn, int end) {
//			if (bgn == end) return; // make "A?" -> "A" DATE:2014-01-19
		int pos = bgn;
		boolean loop = true;
		int key_bgn = pos, key_end = pos, val_bgn = pos;
		while (loop) {
			if (pos == end) {
				Args_add(src, key_bgn, key_end, val_bgn, pos);
				break;
			}
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Amp:
					Args_add(src, key_bgn, key_end, val_bgn, pos);
					key_bgn = pos + 1;
					break;
				case Byte_ascii.Eq:
					key_end = pos;
					val_bgn = pos + 1;	// +1 to set after eq
					break;
				default:
					break;
			}			
			++pos;
		}
		url.Args_bgn_(bgn - 1);	// NOTE: bgn is 1st char after ?; -1 to place at ?
		url.Args_((Gfo_url_arg[])args.XtoAry(Gfo_url_arg.class));
	}
	private void Site_set(byte[] src, int bgn, int end, int dot_count, int dot_pos_0, int dot_pos_1) {
		encoder.Decode(src, bgn, end, tmp_bfr, false);
		url.Site_(tmp_bfr.XtoAryAndClear());
		switch (dot_count) {
			default:
			case 2:
				encoder.Decode(src, bgn, dot_pos_0, tmp_bfr, false);
				url.Site_sub_(tmp_bfr.XtoAryAndClear());
				encoder.Decode(src, dot_pos_0 + 1, dot_pos_1, tmp_bfr, false);
				url.Site_name_(tmp_bfr.XtoAryAndClear());
				encoder.Decode(src, dot_pos_1 + 1, end, tmp_bfr, false);
				url.Site_domain_(tmp_bfr.XtoAryAndClear());
				break;
			case 1:
				encoder.Decode(src, bgn, dot_pos_0, tmp_bfr, false);
				url.Site_name_(tmp_bfr.XtoAryAndClear());
				encoder.Decode(src, dot_pos_0 + 1, end, tmp_bfr, false);
				url.Site_domain_(tmp_bfr.XtoAryAndClear());
				break;
		}
	}
	private void Segs_add(byte[] src, int bgn, int end) {
		encoder.Decode(src, bgn, end, tmp_bfr, false);
		byte[] seg = tmp_bfr.XtoAryAndClear();
		if (url.Page() != null)
			segs.Add(url.Page());
		url.Page_(seg);
		slash_prv = end + 1;	// +1 to position after / 
	}
	private void Anchor_set(byte[] src, int bgn, int end) {
		encoder.Decode(src, bgn, end, tmp_bfr, false);
		url.Anchor_(tmp_bfr.XtoAryAndClear());
	}
	private void Args_add(byte[] src, int key_bgn, int key_end, int val_bgn, int val_end) {
		encoder.Decode(src, key_bgn, key_end, tmp_bfr, false);
		byte[] key = tmp_bfr.XtoAryAndClear();
		encoder.Decode(src, val_bgn, val_end, tmp_bfr, false);
		byte[] val = tmp_bfr.XtoAryAndClear();
		Gfo_url_arg arg = new Gfo_url_arg(key, val);
		args.Add(arg);
	}
	private static final Bry_bfr tmp_bfr = Bry_bfr.reset_(500);
	public static final byte[] Bry_double_slash = new byte[] {Byte_ascii.Slash, Byte_ascii.Slash};
}
