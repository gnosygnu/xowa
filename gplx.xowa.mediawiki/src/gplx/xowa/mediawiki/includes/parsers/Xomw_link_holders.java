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
import gplx.langs.htmls.*;
import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.htmls.*;
import gplx.xowa.mediawiki.includes.linkers.*;
public class Xomw_link_holders {
	private final    Xomw_link_renderer link_renderer;
	private final    Bry_bfr tmp;
	private int link_id = 0;	// MOVED:Parser.php
	private final    Xomw_link_holder_list internals = new Xomw_link_holder_list();
	private final    Xomw_atr_mgr extra_atrs = new Xomw_atr_mgr();
	private final    Xomw_qry_mgr query = new Xomw_qry_mgr();
	public Xomw_link_holders(Xomw_link_renderer link_renderer, Bry_bfr tmp) {
		this.link_renderer = link_renderer;
		this.tmp = tmp;
	}
	public void Clear() {
		internals.Clear();

		link_id = 0;
	}
	public void Make_holder(Bry_bfr bfr, Xoa_ttl nt, byte[] text, byte[][] query, byte[] trail, byte[] prefix) {
		if (nt == null) {
			// Fail gracefully
			bfr.Add_str_a7("<!-- ERROR -->").Add(prefix).Add(text).Add(trail);
		}
		else {
			// Separate the link trail from the rest of the link
//				list( $inside, $trail ) = Linker::splitTrail( $trail );
			byte[] inside = Bry_.Empty;

			Xomw_link_holder_item entry = new Xomw_link_holder_item(nt, tmp.Add_bry_many(prefix, text, inside).To_bry_and_clear(), query);

			boolean is_external = false;	// $nt->isExternal()
			if (is_external) {
				// Use a globally unique ID to keep the objects mergable
//					$key = $this->parent->nextLinkID();
//					$this->interwikis[$key] = $entry;
//					$retVal = "<!--IWLINK $key-->{$trail}";
			}
			else {
				int key = link_id++;
				internals.Add(key, entry);
				bfr.Add(Bry__link__bgn).Add_int_variable(key).Add(Gfh_tag_.Comm_end).Add(trail);	// "<!--LINK $ns:$key-->{$trail}";
			}
		}
	}
	public void Test__add(Xoa_ttl ttl, byte[] capt) {
		int key = link_id++;
		Xomw_link_holder_item item = new Xomw_link_holder_item(ttl, capt, Bry_.Ary_empty);
		internals.Add(key, item);
	}
	public void Replace(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {
		this.Replace_internal(pbfr);
//			$this->replaceInterwiki( $text );
	}
	private void Replace_internal(Xomw_parser_bfr pbfr) {
		if (internals.Len() == 0)
			return;

//			$colours = [];
//			$linkCache = LinkCache::singleton();
//			$output = $this->parent->getOutput();
//			$linkRenderer = $this->parent->getLinkRenderer();

//			$linkcolour_ids = [];

		// SKIP:Replace_internals does db lookup to identify redlinks;

		// Construct search and replace arrays
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		int cur = src_bgn;
		int prv = 0;
		while (true) {
			int link_bgn = Bry_find_.Find_fwd(src, Bry__link__bgn, cur, src_end);
			if (link_bgn == Bry_find_.Not_found) {
				bfr.Add_mid(src, prv, src_end);
				break;
			}
			int key_bgn = link_bgn + Bry__link__bgn.length;
			int key_end = Bry_find_.Find_fwd_while_num(src, key_bgn, src_end);
			int link_key = Bry_.To_int_or(src, key_bgn, key_end, -1);
			Xomw_link_holder_item item = internals.Get_by(link_key);

//					$pdbk = $entry['pdbk'];
//					$title = $entry['title'];
//					$query = isset( $entry['query'] ) ? $entry['query'] : [];
//					$key = "$ns:$index";
//					$searchkey = "<!--LINK $key-->";
//					$displayText = $entry['text'];
//					if ( isset( $entry['selflink'] ) ) {
//						$replacePairs[$searchkey] = Linker::makeSelfLinkObj( $title, $displayText, $query );
//						continue;
//					}
//					if ( $displayText === '' ) {
//						$displayText = null;
//					} else {
//						$displayText = new HtmlArmor( $displayText );
//					}
//					if ( !isset( $colours[$pdbk] ) ) {
//						$colours[$pdbk] = 'new';
//					}
//					$attribs = [];
//					if ( $colours[$pdbk] == 'new' ) {
//						$linkCache->addBadLinkObj( $title );
//						$output->addLink( $title, 0 );
//						$link = $linkRenderer->makeBrokenLink(
//							$title, $displayText, $attribs, $query
//						);
//					} else {
//						$link = $linkRenderer->makePreloadedLink(
//							$title, $displayText, $colours[$pdbk], $attribs, $query
//						);
//					}

			bfr.Add_mid(src, prv, link_bgn);
			link_renderer.Make_preloaded_link(bfr, item.Title(), item.Text(), Bry_.Empty, extra_atrs, query.Clear());
			cur = key_end + Gfh_tag_.Comm_end_len;
			prv = cur;
		}
	}
//		private void Replace_internal__db() {
//			// Generate query
//			$lb = new LinkBatch();
//			$lb->setCaller( __METHOD__ );
//
//			foreach ( $this->internals as $ns => $entries ) {
//				foreach ( $entries as $entry ) {
//					/** @var Title $title */
//					$title = $entry['title'];
//					$pdbk = $entry['pdbk'];
//
//					# Skip invalid entries.
//					# Result will be ugly, but prevents crash.
//					if ( is_null( $title ) ) {
//						continue;
//					}
//
//					# Check if it's a static known link, e.g. interwiki
//					if ( $title->isAlwaysKnown() ) {
//						$colours[$pdbk] = '';
//					} elseif ( $ns == NS_SPECIAL ) {
//						$colours[$pdbk] = 'new';
//					} else {
//						$id = $linkCache->getGoodLinkID( $pdbk );
//						if ( $id != 0 ) {
//							$colours[$pdbk] = $linkRenderer->getLinkClasses( $title );
//							$output->addLink( $title, $id );
//							$linkcolour_ids[$id] = $pdbk;
//						} elseif ( $linkCache->isBadLink( $pdbk ) ) {
//							$colours[$pdbk] = 'new';
//						} else {
//							# Not in the link cache, add it to the query
//							$lb->addObj( $title );
//						}
//					}
//				}
//			}
//			if ( !$lb->isEmpty() ) {
//				$fields = array_merge(
//					LinkCache::getSelectFields(),
//					[ 'page_namespace', 'page_title' ]
//				);
//
//				$res = $dbr->select(
//					'page',
//					$fields,
//					$lb->constructSet( 'page', $dbr ),
//					__METHOD__
//				);
//
//				# Fetch data and form into an associative array
//				# non-existent = broken
//				foreach ( $res as $s ) {
//					$title = Title::makeTitle( $s->page_namespace, $s->page_title );
//					$pdbk = $title->getPrefixedDBkey();
//					$linkCache->addGoodLinkObjFromRow( $title, $s );
//					$output->addLink( $title, $s->page_id );
//					$colours[$pdbk] = $linkRenderer->getLinkClasses( $title );
//					// add id to the extension todolist
//					$linkcolour_ids[$s->page_id] = $pdbk;
//				}
//				unset( $res );
//			}
//			if ( count( $linkcolour_ids ) ) {
//				// pass an array of page_ids to an extension
//				Hooks::run( 'GetLinkColours', [ $linkcolour_ids, &$colours ] );
//			}
//
//			# Do a second query for different language variants of links and categories
//			if ( $wgContLang->hasVariants() ) {
//				$this->doVariants( $colours );
//			}
//		}

	private static final    byte[] Bry__link__bgn = Bry_.new_a7("<!--LINK ");
}
class Xomw_link_holder_list {
	private int ary_len = 0, ary_max = 128;
	private Xomw_link_holder_item[] ary = new Xomw_link_holder_item[128];
	public int Len() {return ary_len;}
	public void Clear() {
		ary_len = 0;
		if (ary_max > 128)
			ary = new Xomw_link_holder_item[128];
	}
	public void Add(int key, Xomw_link_holder_item item) {
		if (key >= ary_max) {
			int new_max = ary_max * 2;
			ary = (Xomw_link_holder_item[])Array_.Resize(ary, new_max);
			ary_max = new_max;
		}
		ary[key] = item;
		ary_len++;
	}
	public Xomw_link_holder_item Get_by(int key) {return ary[key];}
}
class Xomw_link_holder_item {
	public Xomw_link_holder_item(Xoa_ttl title, byte[] text, byte[][] query) {
		this.title = title;
		this.text = text;
		this.query = query;
	}
	public Xoa_ttl Title() {return title;} private final    Xoa_ttl title;
	public byte[] Text()   {return text;} private final    byte[] text;
	public byte[] Pdbk()   {return title.Get_prefixed_db_key();}
	public byte[][] Query() {return query;} private final    byte[][] query;
}
