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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.btries.*;
public class Xomw_strip_state {	// REF.MW:/parser/StripState.php
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Bry_bfr tmp_1 = Bry_bfr_.New();
	private final    Bry_bfr tmp_2 = Bry_bfr_.New();
	private boolean tmp_2_used = false;
	private int general_len, nowiki_len;
	public void Clear() {
		trie.Clear();
		general_len = nowiki_len = 0;
		tmp_2_used = false;
	}
	public void Add_general(byte[] marker, byte[] val) {Add_item(Tid__general, marker, val);}
	public void Add_nowiki (byte[] marker, byte[] val) {Add_item(Tid__nowiki, marker, val);}
	public void Add_item(byte tid, byte[] marker, byte[] val) {
		trie.Add_obj(marker, new Xomw_strip_item(tid, marker, val));
		if (tid == Tid__general)
			general_len++;
		else
			nowiki_len++;
	}
	public byte[] Unstrip_general(byte[] text) {return Unstrip(Tid__general, text);}
	public byte[] Unstrip_nowiki (byte[] text) {return Unstrip(Tid__nowiki , text);}
	public byte[] Unstrip_both   (byte[] text) {return Unstrip(Tid__both   , text);}
	public byte[] Unstrip(byte tid, byte[] text) {
		boolean dirty = Unstrip(tid, tmp_1, text, 0, text.length);
		return dirty ? tmp_1.To_bry_and_clear() : text;
	}
	public void Unstrip_general(Xomw_parser_bfr pbfr) {Unstrip(Tid__general, pbfr);}
	public void Unstrip_nowiki (Xomw_parser_bfr pbfr) {Unstrip(Tid__nowiki , pbfr);}
	public void Unstrip_both   (Xomw_parser_bfr pbfr) {Unstrip(Tid__both   , pbfr);}
	private boolean Unstrip(byte tid, Xomw_parser_bfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		boolean dirty = Unstrip(tid, pbfr.Trg(), src, 0, src_bfr.Len());
		if (dirty)
			pbfr.Switch();
		return dirty;
	}
	private boolean Unstrip(byte tid, Bry_bfr trg, byte[] src, int src_bgn, int src_end) {
		// exit early if no items for type
		if      ((tid & Tid__general) == Tid__general) {
			if (general_len == 0)
				return false;
		}
		else if ((tid & Tid__nowiki) == Tid__nowiki) {
			if (nowiki_len == 0)
				return false;
		}

		int cur = src_bgn;
		int prv = cur;
		boolean dirty = false;
		// loop over each src char
		while (true) {
			// EOS: exit
			if (cur == src_end) {
				if (dirty)	// add remainder if dirty
					trg.Add_mid(src, prv, src_end);
				break;
			}

			// check if current pos matches strip state
			Object o = trie.Match_at(trv, src, cur, src_end);
			if (o != null) {	// match
				Xomw_strip_item item = (Xomw_strip_item)o;
				byte item_tid = item.Tid();
				if ((tid & item_tid) == item_tid) {	// check if types match
					// get bfr for recursion
					Bry_bfr nested_bfr = null;
					boolean tmp_2_release = false;
					if (tmp_2_used) {
						nested_bfr = Bry_bfr_.New();
					}
					else {
						nested_bfr = tmp_2;
						tmp_2_used = true;
						tmp_2_release = true;
					}

					// recurse
					byte[] item_val = item.Val();
					if (Unstrip(tid, nested_bfr, item_val, 0, item_val.length))
						item_val = nested_bfr.To_bry_and_clear();
					if (tmp_2_release)
						tmp_2_used = false;

					// add to trg
					trg.Add_mid(src, prv, cur);
					trg.Add(item_val);

					// update vars
					dirty = true;
					cur += item.Key().length;
					prv = cur;
					continue;
				}
			}
			cur++;
		}
		return dirty;
	}
	public static final    String Str__marker_bgn = "\u007f'\"`UNIQ-";
	public static final    byte[] 
	  Bry__marker__bgn		= Bry_.new_a7(Str__marker_bgn)
	, Bry__marker__end		= Bry_.new_a7("-QINU`\"'\u007f")
	;
	public static final byte Tid__general = 1, Tid__nowiki = 2, Tid__both = 3; 
}
class Xomw_strip_item {
	public Xomw_strip_item(byte tid, byte[] key, byte[] val) {
		this.tid = tid;
		this.key = key;
		this.val = val;
	}
	public byte Tid() {return tid;} private final    byte tid;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Val() {return val;} private final    byte[] val;
}
