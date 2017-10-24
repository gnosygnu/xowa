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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.langs.htmls.*; import gplx.xowa.htmls.*;
class Hiero_parser {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	private List_adp blocks = List_adp_.New();
	private Hiero_block cur_block;
	private Bry_bfr cur_tkn = Bry_bfr_.Reset(16);
	public Hiero_block[] Parse(byte[] src, int bgn, int end) {
		synchronized (blocks) { // TS:needed b/c blocks, cur_block, etc are member vars DATE:2017-05-26
			blocks.Clear();
			this.cur_block = new Hiero_block();
			cur_tkn.Clear();
			int pos = bgn;
			while (true) {
				if (pos == end) break;
				byte b = src[pos];
				Object o = trie.Match_at_w_b0(trv, b, src, pos, end);
				if (o == null) {
					New_char(b);
					++pos;
				}
				else {
					Hiero_parser_itm itm = (Hiero_parser_itm)o;
					int new_pos = trv.Pos();
					switch (itm.Tid()) {
						case Hiero_parser_itm.Tid_comment:
							int end_comm = Bry_find_.Find_fwd(src, Gfh_tag_.Comm_end, new_pos, end);
							if (end_comm == Bry_find_.Not_found)	// --> not found; for now, ignore <!--
								pos = new_pos;
							else
								pos = end_comm + Gfh_tag_.Comm_end_len;
							break;
						case Hiero_parser_itm.Tid_block_spr:
							New_block();
							break;
						case Hiero_parser_itm.Tid_single_char:
							Single_char_block(itm);
							break;
						case Hiero_parser_itm.Tid_dot:
							Dot();
							break;
						case Hiero_parser_itm.Tid_tkn_spr:
							New_token(itm);
							break;
						default: throw Err_.new_unhandled(itm.Tid());	// should never happen
					}
					pos = new_pos;
				}
			}			
			this.New_block();// flush remaining items
			return (Hiero_block[])blocks.To_ary_and_clear(Hiero_block.class);
		}
	}
	private void New_block() {
		this.New_token(null);
		if (cur_block.Len() > 0) {
			blocks.Add(cur_block);
			cur_block = new Hiero_block();
		}
	}
	private void New_token(Hiero_parser_itm itm) {
		if (cur_tkn.Len_gt_0())
			cur_block.Add(cur_tkn.To_bry_and_clear());
		if (itm != null)
			cur_block.Add(itm.Key());
	}
	private void Single_char_block(Hiero_parser_itm itm) {
		this.New_block();
		cur_block.Add(itm.Key());
	}
	private void Dot() {
		if (cur_tkn.Eq(Byte_ascii.Dot)) {		// is "."
			cur_tkn.Add_byte(Byte_ascii.Dot);	// make ".."
			this.New_block();
		}
		else {
			this.New_block();
			cur_tkn.Add_byte(Byte_ascii.Dot);	// make "."; note that New_block clears tkn
		}
	}
	private void New_char(byte b) {
		if (b == Byte_ascii.Dot) {				// is "."; NOTE: never occurs; should always hit dot block; transcribed literally from MW
			this.New_block();
			this.cur_tkn.Add_byte(b);			// $this->token = $char;
		}
		else
			this.cur_tkn.Add_byte(b);			// $this->token .= $char;
	}
	public void Init() {
		Init_itms(Hiero_parser_itm.Tid_block_spr, " ", "-", "\t", "\n", "\r");
		Init_itms(Hiero_parser_itm.Tid_tkn_spr, "*", ":", "(", ")");
		Init_itms(Hiero_parser_itm.Tid_dot, ".");
		Init_itms(Hiero_parser_itm.Tid_single_char , "!");
		Init_itms(Hiero_parser_itm.Tid_comment, Gfh_tag_.Comm_bgn_str);
	}
	private void Init_itms(byte tid, String... keys) {
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; i++) {
			String key_str = keys[i];
			byte[] key_bry = Bry_.new_u8(key_str);
			Hiero_parser_itm itm = new Hiero_parser_itm(tid, key_bry);
			trie.Add_obj(key_bry, itm);
		}
	}
}
class Hiero_parser_itm {
	public Hiero_parser_itm(byte tid, byte[] key) {this.tid = tid; this.key = key;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Key() {return key;} private byte[] key;
	public static final byte Tid_block_spr = 1, Tid_tkn_spr = 2, Tid_single_char = 3, Tid_dot = 4, Tid_comment = 5;
}
class Hiero_block {
	private List_adp list = List_adp_.New();
	public int Len() {return list.Count();}
	public byte[] Get_at(int i) {return (byte[])list.Get_at(i);}
	public void Add(byte[] v) {list.Add(v);}
}
