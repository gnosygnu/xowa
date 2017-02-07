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
package gplx.xowa.mws.parsers.doubleunders; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
public class Xomw_doubleunder_wkr {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.ci_u8();
	private final    Btrie_rv trv = new Btrie_rv();
	private Xomw_doubleunder_data data;
	public void Init_by_wiki(Xomw_doubleunder_data data, Xol_lang_itm lang) {
		this.data = data;
		Reg(trie, lang.Kwd_mgr()
		, Xol_kwd_grp_.Id_notoc
		, Xol_kwd_grp_.Id_nogallery
		, Xol_kwd_grp_.Id_forcetoc
		, Xol_kwd_grp_.Id_toc
		, Xol_kwd_grp_.Id_noeditsection
		, Xol_kwd_grp_.Id_newsectionlink
		, Xol_kwd_grp_.Id_hiddencat
		, Xol_kwd_grp_.Id_index
		, Xol_kwd_grp_.Id_noindex
		, Xol_kwd_grp_.Id_staticredirect
		, Xol_kwd_grp_.Id_notitleconvert
		, Xol_kwd_grp_.Id_nocontentconvert
		);
	}
	public void Do_double_underscore(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();

		data.Reset();

		// XO.MW: MW does TOC before others; XO does it at the same time
		// Now match and remove the rest of them
		// XO.MW.BGN: $this->mDoubleUnderscores = $mwa->matchAndRemove( $text );
		int cur = src_bgn;
		int prv = cur;
		boolean dirty = false;
		while (true) {
			// reached end; stop
			if (cur == src_end) {
				if (dirty) {
					bfr.Add_mid(src, prv, src_end);
				}
				break;
			}

			// no match; keep searching
			byte b = src[cur];
			Object o = trie.Match_at_w_b0(trv, b, src, cur, src_end);
			if (o == null) {
				cur += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
				continue;
			}

			// if cs, ensure exact-match (trie is case-insensitive)
			int kwd_end = trv.Pos();
			Xomw_doubleunder_itm itm = (Xomw_doubleunder_itm)o;
			if (itm.case_match && !Bry_.Match(src, cur, kwd_end, itm.val)) {
				cur = kwd_end;
				continue;
			}

			// match; replace __KWD__ with "" (or "<!--MWTOC-->" if __TOC__)
			dirty = true;
			bfr.Add_mid(src, prv, cur);
			switch (itm.tid) {
				case Xol_kwd_grp_.Id_toc:
					// The position of __TOC__ needs to be recorded
					boolean already_seen = !data.show_toc;
					data.toc = true;
					data.show_toc = true;
					data.force_toc_position = true;

					if (already_seen) { // Set a placeholder. At the end we'll fill it in with the TOC.
						bfr.Add_str_a7("<!--MWTOC-->");
					}
					else { // Only keep the first one. XO.MW:ignore by not adding anything to bfr
					}
					break;
				// XO.MW: MW adds boolean to hash_table; XO uses boolean props; note that "remove" is done by not adding to bfr
				case Xol_kwd_grp_.Id_notoc:                data.no_toc = true; break;
				case Xol_kwd_grp_.Id_nogallery:            data.no_gallery = true; break;
				case Xol_kwd_grp_.Id_forcetoc:             data.force_toc = true; break;
				case Xol_kwd_grp_.Id_noeditsection:        data.no_edit_section = true; break;
				case Xol_kwd_grp_.Id_newsectionlink:       data.new_section_link = true; break;
				case Xol_kwd_grp_.Id_hiddencat:            data.hidden_cat = true; break;
				case Xol_kwd_grp_.Id_index:                data.index = true; break;
				case Xol_kwd_grp_.Id_noindex:              data.no_index = true; break;
				case Xol_kwd_grp_.Id_staticredirect:       data.static_redirect = true; break;
				case Xol_kwd_grp_.Id_notitleconvert:       data.no_title_convert = true; break;
				case Xol_kwd_grp_.Id_nocontentconvert:     data.no_content_convert = true; break;
				default:                                   throw Err_.new_unhandled_default(itm.tid);
			}
			cur = kwd_end;
			prv = cur;
		}
		// XO.MW.END: $this->mDoubleUnderscores = $mwa->matchAndRemove( $text );

		if (data.no_toc && !data.force_toc_position) {
			data.show_toc = false;
		}

		// XO.MW.EDIT: hidden_cat, index, noindex are used to add to tracking category

		if (dirty)
			pbfr.Switch();
	}
	private static void Reg(Btrie_slim_mgr trie, Xol_kwd_mgr mgr, int... ids) {
		for (int id : ids) {
			Xol_kwd_grp grp = mgr.Get_or_new(id);
			Xol_kwd_itm[] itms = grp.Itms();
			for (Xol_kwd_itm itm : itms) {
				byte[] val = itm.Val();
				trie.Add_obj(val, new Xomw_doubleunder_itm(id, grp.Case_match(), val));
			}
		}
	}
}
class Xomw_doubleunder_itm {
	public int tid;
	public boolean case_match;
	public byte[] val;
	public Xomw_doubleunder_itm(int tid, boolean case_match, byte[] val) {
		this.tid = tid;
		this.case_match = case_match;
		this.val = val;
	}
}
