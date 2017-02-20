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
//namespace gplx.xowa.mediawiki.includes.parsers {
//	using gplx.langs.htmls;
//	using gplx.xowa.mediawiki.includes;
//	using gplx.xowa.mediawiki.includes.htmls;
//	using gplx.xowa.mediawiki.includes.linkers;
//	public class Xomw_link_holders {
//		private final    Xomw_link_renderer link_renderer;
//		private final    Bry_bfr tmp;
//		private int link_id = 0;	// MOVED:Parser.php
//		private final    XomwLinkHolderList internals = new XomwLinkHolderList();
//		private final    Xomw_atr_mgr extra_atrs = new Xomw_atr_mgr();
//		private final    Xomw_qry_mgr query = new Xomw_qry_mgr();
//		public Xomw_link_holders(Xomw_link_renderer link_renderer, Bry_bfr tmp) {
//			this.link_renderer = link_renderer;
//			this.tmp = tmp;
//		}
//		public void Clear() {
//			internals.Clear();
//
//			link_id = 0;
//		}
//		public void Make_holder(Bry_bfr bfr, XomwTitle nt, byte[] text, byte[][] query, byte[] trail, byte[] prefix) {
//			if (nt == null) {
//				// Fail gracefully
//				bfr.Add_str_a7("<!-- ERROR -->").Add(prefix).Add(text).Add(trail);
//			}
//			else {
//				// Separate the link trail from the rest of the link
////				list( $inside, $trail ) = Linker::splitTrail( $trail );
//				byte[] inside = Bry_.Empty;
//
//				XomwLinkHolderItem entry = new XomwLinkHolderItem(nt, tmp.Add_bry_many(prefix, text, inside).To_bry_and_clear(), query);
//
//				boolean is_external = false;	// $nt->isExternal()
//				if (is_external) {
//					// Use a globally unique ID to keep the objects mergable
////					$key = $this->parent->nextLinkID();
////					$this->interwikis[$key] = $entry;
////					$retVal = "<!--IWLINK $key-->{$trail}";
//				}
//				else {
//					int key = link_id++;
//					internals.Add(key, entry);
//					bfr.Add(Bry__link__bgn).Add_int_variable(key).Add(Gfh_tag_.Comm_end).Add(trail);	// "<!--LINK $ns:$key-->{$trail}";
//				}
//			}
//		}
//		public void Test__add(XomwTitle ttl, byte[] capt) {
//			int key = link_id++;
//			XomwLinkHolderItem item = new XomwLinkHolderItem(ttl, capt, Bry_.Ary_empty);
//			internals.Add(key, item);
//		}
//		public void Replace(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {
//			this.Replace_internal(pbfr);
////			$this->replaceInterwiki( $text );
//		}
//		private void Replace_internal(Xomw_parser_bfr pbfr) {
//			if (internals.Len() == 0)
//				return;
//
////			$colours = [];
////			$linkCache = LinkCache::singleton();
////			$output = $this->parent->getOutput();
////			$linkRenderer = $this->parent->getLinkRenderer();
//
////			$linkcolour_ids = [];
//
//			// SKIP:Replace_internals does db lookup to identify redlinks;
//
//			// Construct search and replace arrays
//			Bry_bfr src_bfr = pbfr.Src();
//			byte[] src = src_bfr.Bfr();
//			int src_bgn = 0;
//			int src_end = src_bfr.Len();
//			Bry_bfr bfr = pbfr.Trg();
//			pbfr.Switch();
//
//			int cur = src_bgn;
//			int prv = 0;
//			while (true) {
//				int link_bgn = Bry_find_.Find_fwd(src, Bry__link__bgn, cur, src_end);
//				if (link_bgn == Bry_find_.Not_found) {
//					bfr.Add_mid(src, prv, src_end);
//					break;
//				}
//				int key_bgn = link_bgn + Bry__link__bgn.length;
//				int key_end = Bry_find_.Find_fwd_while_num(src, key_bgn, src_end);
//				int link_key = Bry_.To_int_or(src, key_bgn, key_end, -1);
//				XomwLinkHolderItem item = internals.Get_by(link_key);
//
////					$pdbk = $entry['pdbk'];
////					$title = $entry['title'];
////					$query = isset( $entry['query'] ) ? $entry['query'] : [];
////					$key = "$ns:$index";
////					$searchkey = "<!--LINK $key-->";
////					$displayText = $entry['text'];
////					if ( isset( $entry['selflink'] ) ) {
////						$replacePairs[$searchkey] = Linker::makeSelfLinkObj( $title, $displayText, $query );
////						continue;
////					}
////					if ( $displayText === '' ) {
////						$displayText = null;
////					} else {
////						$displayText = new HtmlArmor( $displayText );
////					}
////					if ( !isset( $colours[$pdbk] ) ) {
////						$colours[$pdbk] = 'new';
////					}
////					$attribs = [];
////					if ( $colours[$pdbk] == 'new' ) {
////						$linkCache->addBadLinkObj( $title );
////						$output->addLink( $title, 0 );
////						$link = $linkRenderer->makeBrokenLink(
////							$title, $displayText, $attribs, $query
////						);
////					} else {
////						$link = $linkRenderer->makePreloadedLink(
////							$title, $displayText, $colours[$pdbk], $attribs, $query
////						);
////					}
//
//				bfr.Add_mid(src, prv, link_bgn);
//				link_renderer.Make_preloaded_link(bfr, item.Title(), item.Text(), Bry_.Empty, extra_atrs, query.Clear());
//				cur = key_end + Gfh_tag_.Comm_end_len;
//				prv = cur;
//			}
//		}
//
//		private static final    byte[] Bry__link__bgn = Bry_.new_a7("<!--LINK ");
//	}
//}
