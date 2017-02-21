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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.core.envs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.numbers.*;
public class Xop_lnki_arg_parser {
	private final    Btrie_fast_mgr key_trie = Btrie_fast_mgr.cs();
	private final    Bry_bfr int_bfr = Bry_bfr_.Reset(16);
	private final    Btrie_bwd_mgr px_trie = Btrie_bwd_mgr.cs_(); private final    Btrie_fast_mgr size_trie = Btrie_fast_mgr.cs();
	private final    Btrie_rv key_trie_rv = new Btrie_rv(), px_trie_rv = new Btrie_rv(), size_trie_rv = new Btrie_rv();
	private int lnki_w, lnki_h;
	public void Evt_lang_changed(Xol_lang_itm lang) {
		Bry_bfr tmp_bfr = int_bfr;
		Byte_obj_ref rslt = Byte_obj_ref.zero_();
		Xol_kwd_mgr mgr = lang.Kwd_mgr();
		key_trie.Clear();
		Xol_kwd_grp list = null;
		int len = Keys_ids.length;
		for (int i = 0; i < len; i++) {
			int[] val = Keys_ids[i];
			list = mgr.Get_at(val[0]);			// NOTE: val[0] is magic_word id
			if (list == null) {
				 if (Env_.Mode_testing())
					continue;		// TEST: allows partial parsing of $magicWords
				else
					 list = lang.Lang_mgr().Lang_en().Kwd_mgr().Get_at(val[0]);
			}
			Xol_kwd_itm[] words = list.Itms();
			int words_len = words.length;
			for (int j = 0; j < words_len; j++) {
				Xol_kwd_itm word = words[j];
				byte[] word_bry = Xol_kwd_parse_data.Strip(tmp_bfr, word.Val(), rslt);
				Init_key_trie(word_bry, (byte)val[1]);		// NOTE: val[1] is lnki_key tid; ASSUME: case_sensitive for all "img_" words; note that all Messages**.php seem to be case_sensitive ("array(1, ..."); resisting change b/c of complexity/perf (need a cs trie and a ci trie)
			}
		}
		list = mgr.Get_at(Xol_kwd_grp_.Id_img_width);
		if (list == null)
			list = lang.Lang_mgr().Lang_en().Kwd_mgr().Get_at(Xol_kwd_grp_.Id_img_width);
		Init_size_trie(tmp_bfr, lang.Num_mgr().Digits_mgr(), list);
	}
	public byte Identify_tid(byte[] src, int bgn, int end, Xop_lnki_tkn lnki) {
		lnki_w = Xop_lnki_tkn.Width_null;
		lnki_h = Xop_lnki_tkn.Height_null;
		byte rv = Identify_tid(src, bgn, end);
		if (lnki_w != Xop_lnki_tkn.Width_null) lnki.W_(lnki_w);
		if (lnki_h != Xop_lnki_tkn.Height_null)lnki.H_(lnki_h);
		return rv;
	}
	public byte Identify_tid(byte[] src, int bgn, int end) {
		int len = end - bgn;
		Byte_obj_val val = (Byte_obj_val)key_trie.Match_at(key_trie_rv, src, bgn, end);
		if (val != null && len == key_trie_rv.Pos() - bgn)		// check for false matches; EX: alternate= should not match alt=
			return val.Val();									// match; return val;
		Object bwd_obj = px_trie.Match_at(px_trie_rv, src, end - 1, bgn - 1);
		if (bwd_obj != null && ((Byte_obj_val)bwd_obj).Val() == Tid_dim) { // ends with "px"; try to parse size
			int_bfr.Clear();
			int match_len = end -1 - px_trie_rv.Pos();
			boolean mode_width = true;
			int itm_end = bgn + (len - match_len);	// remove trailing px
			for (int i = bgn; i < itm_end; i++) {
				byte b = src[i];
				Object o = size_trie.Match_at_w_b0(size_trie_rv, b, src, i, itm_end);
				if (o == null) {
					this.lnki_w = Xop_lnki_tkn.Width_null;	// NOTE: must null out width; EX: "123xTextpx"; PAGE:es.b:Alimentación_infantil; DATE:2015-07-10; NOTE: must be -1, not 0; DATE:2015-08-05
					return Tid_caption;						// letter or other invalid character; return caption
				}
				Byte_obj_val v = (Byte_obj_val)o;
				switch (v.Val()) {	// NOTE: d0 - d9 handle non-english numbers; EX:fa.w and ۲۰۰px; DATE:2015-07-18
					case Key_dim_d0:	int_bfr.Add_byte(Byte_ascii.Num_0); i += (size_trie_rv.Pos() - i) - 1; break;	// -1 b/c loop will ++i
					case Key_dim_d1:	int_bfr.Add_byte(Byte_ascii.Num_1); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d2:	int_bfr.Add_byte(Byte_ascii.Num_2); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d3:	int_bfr.Add_byte(Byte_ascii.Num_3); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d4:	int_bfr.Add_byte(Byte_ascii.Num_4); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d5:	int_bfr.Add_byte(Byte_ascii.Num_5); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d6:	int_bfr.Add_byte(Byte_ascii.Num_6); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d7:	int_bfr.Add_byte(Byte_ascii.Num_7); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d8:	int_bfr.Add_byte(Byte_ascii.Num_8); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_d9:	int_bfr.Add_byte(Byte_ascii.Num_9); i += (size_trie_rv.Pos() - i) - 1; break;
					case Key_dim_num:	int_bfr.Add_byte(b); break;
					case Key_space:		break;	// ignore space; EX: "100 px"
					case Key_dim_px:	{		// 2nd px found; EX: "40pxpx"; "40px px"
						int tmp_pos = size_trie_rv.Pos();
						tmp_pos = Bry_find_.Find_fwd_while_space_or_tab(src, tmp_pos, itm_end);	// look for next ws pos; 
						if (tmp_pos == itm_end)	// no non-ws found; tmp_pos == itm_end; allow itm; EX: "40pxpx"; "40px px"; DATE:2014-03-01
							i = itm_end;
						else					// non-ws found; consider as caption; EX: "20px20px"; "20pxpxpx"
							return Tid_caption;
						break;
					}
					case Key_dim_x:		{
						if (mode_width) {
							this.lnki_w = int_bfr.To_int_and_clear(-1);
							mode_width = false;
							break;
						}
						else return Tid_caption;
					}
				}
			}
			int dim = int_bfr.To_int_and_clear(-1);
			if	(mode_width)	this.lnki_w = dim;
			else				this.lnki_h = dim;
			return Tid_dim;
		}
		return Tid_caption;
	}
	private void Init_key_trie(byte[] key, byte v) {
		Byte_obj_val val = Byte_obj_val.new_(v);
		key_trie.Add(key, val);
	}
	private void Init_size_trie(Bry_bfr tmp_bfr, Xol_transform_mgr digit_mgr, Xol_kwd_grp list) {
		if (list == null && Env_.Mode_testing()) return;	// TEST: allows partial parsing of $magicWords
		size_trie.Clear(); px_trie.Clear();
		for (int i = 0; i < 10; i++)
			size_trie.Add((byte)(i + Byte_ascii.Num_0), Byte_obj_val.new_(Key_dim_num));
		int len = digit_mgr.Len(); // NOTE: add non-english numbers; EX: ۲۰۰px; DATE:2015-07-18
		for (int i = 0; i < len; ++i) {
			Keyval kv = digit_mgr.Get_at(i);
			int num = (byte)Int_.parse_or(kv.Key(), -1); if (num == -1) continue; // ignore separators; EX: "," "." 
			size_trie.Add((byte[])kv.Val(), Byte_obj_val.new_((byte)num));	// NOTE: num corresponds to dim_d0 -> d9 below
		}
		size_trie.Add(Byte_ascii.Space, Byte_obj_val.new_(Key_space));
		size_trie.Add(X_bry, Byte_obj_val.new_(Key_dim_x));
		Xol_kwd_itm[] words = list.Itms();
		int words_len = words.length;
		Byte_obj_ref rslt = Byte_obj_ref.zero_();
		for (int i = 0; i < words_len; i++) {
			byte[] word_bry = Xol_kwd_parse_data.Strip(tmp_bfr, words[i].Val(), rslt);			
			size_trie.Add(word_bry, Byte_obj_val.new_(Key_dim_px));
			px_trie.Add(word_bry, Byte_obj_val.new_(Tid_dim));
		}
	}	
	public static final    byte[] Bry_upright = Bry_.new_a7("upright"), Bry_thumbtime = Bry_.new_a7("thumbtime"), Bry_target = Bry_.new_a7("target");
	public static final byte
	  Tid_unknown = 0, Tid_thumb = 1, Tid_left = 2, Tid_right = 3, Tid_none = 4, Tid_center = 5, Tid_frame = 6, Tid_frameless = 7, Tid_upright = 8, Tid_border = 9
	, Tid_alt = 10, Tid_link = 11, Tid_baseline = 12, Tid_sub = 13, Tid_super = 14, Tid_top = 15, Tid_text_top = 16, Tid_middle = 17, Tid_bottom = 18, Tid_text_bottom = 19
	, Tid_dim = 20
	, Tid_trg = 21, Tid_caption = 22
	, Tid_page = 23
	, Tid_noplayer = 24, Tid_noicon = 25, Tid_thumbtime = 26
	, Tid_class = 27
	, Tid_target = 28
	;
	private static final    byte[] X_bry = Bry_.new_a7("x");
	private static final byte	// NOTE: d0 - d9 must match 0 - 9; DATE:2015-07-18
	  Key_dim_d0 = 0, Key_dim_d1 = 1, Key_dim_d2 = 2, Key_dim_d3 = 3, Key_dim_d4 = 4
	, Key_dim_d5 = 5, Key_dim_d6 = 6, Key_dim_d7 = 7, Key_dim_d8 = 8, Key_dim_d9 = 9
	, Key_dim_num = 10, Key_dim_x = 11, Key_dim_px = 12, Key_space = 13
	;
	private static final    int[][] Keys_ids = new int[][] 
	{ new int[] {Xol_kwd_grp_.Id_img_thumbnail		, Tid_thumb}
	, new int[] {Xol_kwd_grp_.Id_img_manualthumb	, Tid_thumb}	// RESEARCH: what is manualthumb? 'thumb=$1' vs 'thumb'
	, new int[] {Xol_kwd_grp_.Id_img_right			, Tid_right}
	, new int[] {Xol_kwd_grp_.Id_img_left			, Tid_left}
	, new int[] {Xol_kwd_grp_.Id_img_none			, Tid_none}
	, new int[] {Xol_kwd_grp_.Id_img_center			, Tid_center}
	, new int[] {Xol_kwd_grp_.Id_img_framed			, Tid_frame}
	, new int[] {Xol_kwd_grp_.Id_img_frameless		, Tid_frameless}
	, new int[] {Xol_kwd_grp_.Id_img_page			, Tid_page}		// for pdf
	, new int[] {Xol_kwd_grp_.Id_img_upright		, Tid_upright}
	, new int[] {Xol_kwd_grp_.Id_img_border			, Tid_border}
	, new int[] {Xol_kwd_grp_.Id_img_baseline		, Tid_baseline}
	, new int[] {Xol_kwd_grp_.Id_img_sub			, Tid_sub}
	, new int[] {Xol_kwd_grp_.Id_img_super			, Tid_super}
	, new int[] {Xol_kwd_grp_.Id_img_top			, Tid_top}
	, new int[] {Xol_kwd_grp_.Id_img_text_top		, Tid_text_top}
	, new int[] {Xol_kwd_grp_.Id_img_middle			, Tid_middle}
	, new int[] {Xol_kwd_grp_.Id_img_bottom			, Tid_bottom}
	, new int[] {Xol_kwd_grp_.Id_img_text_bottom	, Tid_text_bottom}
	, new int[] {Xol_kwd_grp_.Id_img_link			, Tid_link}
	, new int[] {Xol_kwd_grp_.Id_img_alt			, Tid_alt}
	, new int[] {Xol_kwd_grp_.Id_img_class			, Tid_class}
	, new int[] {Xol_kwd_grp_.Id_ogg_noplayer		, Tid_noplayer}	// RESEARCH: what does noplayer do?; find example
	, new int[] {Xol_kwd_grp_.Id_ogg_noicon			, Tid_noicon}
	, new int[] {Xol_kwd_grp_.Id_ogg_thumbtime		, Tid_thumbtime}
	};
}
