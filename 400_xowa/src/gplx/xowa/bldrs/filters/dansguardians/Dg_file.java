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
package gplx.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import gplx.core.primitives.*;
class Dg_file {
	public Dg_file(int id, String rel_path, Dg_rule[] lines) {this.id = id; this.rel_path = rel_path; this.lines = lines;}
	public int Id() {return id;} private final int id;
	public String Rel_path() {return rel_path;} private final String rel_path;	// EX: goodphrases/weighted_general
	public Dg_rule[] Lines() {return lines;} private final Dg_rule[] lines;
}
class Dg_rule {// EX: < wikipedia ><-30>
	private final Hash_adp_bry word_idx_hash = Hash_adp_bry.cs();
	public Dg_rule(int file_id, int id, int idx, int tid, byte[] key, int score, Dg_word[] words) {
		this.file_id = file_id;
		this.id = id; this.idx = idx; this.tid = tid; this.key = key; this.score = score; this.words = words;
		if (words != null) {	// static rules will have null byte[][]
			int words_len = words.length;
			for (int i = 0; i < words_len; ++i) {
				Dg_word word = words[i];
				word_idx_hash.Add_bry_obj(word.Raw(), Int_obj_ref.new_(i));
			}
		}
	}
	public int File_id() {return file_id;} private final int file_id;
	public int Id() {return id;} private final int id;
	public int Idx() {return idx;} private final int idx;
	public int Tid() {return tid;} private final int tid;
	public byte[] Key() {return key;} private final byte[] key;
	public Dg_word[] Words() {return words;} private final Dg_word[] words;
	public Hash_adp_bry Word_idx_hash() {return word_idx_hash;}
	public int Score() {return score;} private final int score;
	public static final int 
	  Tid_rule		= 0
	, Tid_comment	= 1
	, Tid_blank		= 3
	, Tid_invalid	= 4
	;
	public static final Dg_rule 
	  Itm_comment	= new Dg_rule(-1, -1, -1, Tid_comment, null, -1, null)
	, Itm_blank		= new Dg_rule(-1, -1, -1, Tid_blank, null, -1, null)
	, Itm_invalid	= new Dg_rule(-1, -1, -1, Tid_invalid, null, -1, null)
	;
	public static final int Score_banned = 0;
}
class Dg_word {
	public Dg_word(byte[] raw) {this.raw = raw;}
	public byte[] Raw() {return raw;} private final byte[] raw;
	public static String Ary_concat(Dg_word[] ary, Bry_bfr bfr, byte dlm) {
		if (ary == null) return String_.Empty;
		int len = ary.length;
		if (len == 0) return String_.Empty;
		bfr.Add_byte_apos();
		for (int i = 0; i < len; ++i) {
			Dg_word itm = ary[i];
			if (i != 0) bfr.Add_byte(dlm);
			bfr.Add(itm.Raw());
		}
		bfr.Add_byte_apos();
		return bfr.Xto_str_and_clear();
	}
	public static Dg_word[] Ary_new_by_str_ary(String[] ary) {
		int ary_len = ary.length;
		Dg_word[] rv = new Dg_word[ary_len];
		for (int i = 0; i < ary_len; ++i) {
			String raw = ary[i];
			rv[i] = new Dg_word(Bry_.new_u8(raw));
		}
		return rv;
	}
}
