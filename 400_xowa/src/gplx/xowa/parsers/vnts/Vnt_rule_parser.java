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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
class Vnt_rule_parser implements gplx.core.brys.Bry_split_wkr {
//		private final Btrie_slim_mgr vnt_trie = Btrie_slim_mgr.ci_a7();
	public void Parse(byte[] src, int src_bgn, int src_end) {
		Bry_split_.Split(src, Byte_ascii.Semic, false, this);	// trim=false for "&#entity;" check below
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		int html_entity_pos = Bry_find_.Find_bwd_while_alphanum(src, itm_bgn);
		if (Bry_.Eq(src, html_entity_pos - 2, html_entity_pos, Bry__html_entity)) return Bry_split_.Rv__extend; // reject "&#entity;"; EX: "&nbsp;zh-hans;"
		/*
		itm_bgn = skip fwd for ws;
		itm_bgn = skip fwd for "=>"
		Object vnt_obj = vnt_trie.Match_bgn(src, itm_bgn, itm_end); if (vnt_obj == null) return Bry_split_.Rv__extend;	// reject ";not_variant"; EX: ";border" in "zh-hans:<span style='color:blue;border:1px;'>;zh-hant:"
		itm_end = skip bwd for ws
//			val = trim( val[0] );
//			trg = trim( val[1] );
//			$u = explode( '=>', val, 2 );
//			// if trg is empty, strtr() could return a wrong result
//			if ( count( $u ) == 1 && trg && in_array( val, $variants ) ) {
//				bidi_ary[val] = trg;
//			} elseif ( count( $u ) == 2 ) {
//				$from = trim( $u[0] );
//				val = trim( $u[1] );
//				if ( array_key_exists( val, $unidtable )
//					&& !is_array( $unidtable[val] )
//					&& trg
//					&& in_array( val, $variants ) ) {
//					$unidtable[val] = array( $from => trg );
//				} elseif ( trg && in_array( val, $variants ) ) {
//					$unidtable[val][$from] = trg;
//				}
//			}
//			// syntax error, pass
//			if ( !isset( $this->mConverter->mVariantNames[val] ) ) {
//				bidi_ary = array();
//				$unidtable = array();
//				break;
//			}
		*/
		return Bry_split_.Rv__ok;
	}
	private static final byte[] Bry__html_entity = Bry_.new_a7("&#");
}
