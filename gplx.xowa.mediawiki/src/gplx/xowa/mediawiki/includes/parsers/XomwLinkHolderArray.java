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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.langs.htmls.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
import gplx.xowa.mediawiki.includes.linkers.*;
/**
* Holder of replacement pairs for wiki links
*/
public class XomwLinkHolderArray {
	private final    XomwLinkHolderList internals = new XomwLinkHolderList();
//		public $interwikis = [];
	// private int size = 0;

	private final    Bry_bfr tmp = Bry_bfr_.New();
	private final    Xomw_atr_mgr extraAtrs = new Xomw_atr_mgr();
	private final    Xomw_qry_mgr query = new Xomw_qry_mgr();
	/**
	* @var Parser
	*/
	private final    XomwParserIface parent;
//		protected $tempIdOffset;

	/**
	* @param Parser $parent
	*/
	public XomwLinkHolderArray(XomwParserIface parent) {
		this.parent = parent;
	}

	/**
	* Reduce memory usage to reduce the impact of circular references
	*/
	//	public function __destruct() {
	//		foreach ( $this as $name => $value ) {
	//			unset( this.$name );
	//		}
	//	}

	/**
	* Don't serialize the parent Object, it is big, and not needed when it is
	* a parameter to mergeForeign(), which is the only application of
	* serializing at present.
	*
	* Compact the titles, only serialize the text form.
	* @return array
	*/
	//	public function __sleep() {
	//		foreach ( this.internals as &$nsLinks ) {
	//			foreach ( $nsLinks as &$entry ) {
	//				unset( $entry['title'] );
	//			}
	//		}
	//		unset( $nsLinks );
	//		unset( $entry );
	//
	//		foreach ( this.interwikis as &$entry ) {
	//			unset( $entry['title'] );
	//		}
	//		unset( $entry );
	//
	//		return [ 'internals', 'interwikis', 'size' ];
	//	}

	/**
	* Recreate the Title objects
	*/
	//	public function __wakeup() {
	//		foreach ( this.internals as &$nsLinks ) {
	//			foreach ( $nsLinks as &$entry ) {
	//				$entry['title'] = Title::newFromText( $entry['pdbk'] );
	//			}
	//		}
	//		unset( $nsLinks );
	//		unset( $entry );
	//
	//		foreach ( this.interwikis as &$entry ) {
	//			$entry['title'] = Title::newFromText( $entry['pdbk'] );
	//		}
	//		unset( $entry );
	//	}

//		/**
//		* Merge another LinkHolderArray into this one
//		* @param LinkHolderArray $other
//		*/
//		public function merge( $other ) {
//			foreach ( $other->internals as $ns => $entries ) {
//				this.size += count( $entries );
//				if ( !isset( this.internals[$ns] ) ) {
//					this.internals[$ns] = $entries;
//				} else {
//					this.internals[$ns] += $entries;
//				}
//			}
//			this.interwikis += $other->interwikis;
//		}
//
//		/**
//		* Merge a LinkHolderArray from another parser instance into this one. The
//		* keys will not be preserved. Any text which went with the old
//		* LinkHolderArray and needs to work with the new one should be passed in
//		* the $texts array. The strings in this array will have their link holders
//		* converted for use in the destination link holder. The resulting array of
//		* strings will be returned.
//		*
//		* @param LinkHolderArray $other
//		* @param array $texts Array of strings
//		* @return array
//		*/
//		public function mergeForeign( $other, $texts ) {
//			this.tempIdOffset = $idOffset = this.parent->nextLinkID();
//			$maxId = 0;
//
//			# Renumber @gplx.Internal protected links
//			foreach ( $other->internals as $ns => $nsLinks ) {
//				foreach ( $nsLinks as $key => $entry ) {
//					$newKey = $idOffset + $key;
//					this.internals[$ns][$newKey] = $entry;
//					$maxId = $newKey > $maxId ? $newKey : $maxId;
//				}
//			}
//			$texts = preg_replace_callback( '/(<!--LINK \d+:)(\d+)(-->)/',
//				[ $this, 'mergeForeignCallback' ], $texts );
//
//			# Renumber interwiki links
//			foreach ( $other->interwikis as $key => $entry ) {
//				$newKey = $idOffset + $key;
//				this.interwikis[$newKey] = $entry;
//				$maxId = $newKey > $maxId ? $newKey : $maxId;
//			}
//			$texts = preg_replace_callback( '/(<!--IWLINK )(\d+)(-->)/',
//				[ $this, 'mergeForeignCallback' ], $texts );
//
//			# Set the parent link ID to be beyond the highest used ID
//			this.parent->setLinkID( $maxId + 1 );
//			this.tempIdOffset = null;
//			return $texts;
//		}
//
//		/**
//		* @param array $m
//		* @return String
//		*/
//		protected function mergeForeignCallback( $m ) {
//			return $m[1] . ( $m[2] + this.tempIdOffset ) . $m[3];
//		}
//
//		/**
//		* Get a subset of the current LinkHolderArray which is sufficient to
//		* interpret the given text.
//		* @param String $text
//		* @return LinkHolderArray
//		*/
//		public function getSubArray( $text ) {
//			$sub = new LinkHolderArray( this.parent );
//
//			# Internal links
//			$pos = 0;
//			while ( $pos < strlen( $text ) ) {
//				if ( !preg_match( '/<!--LINK (\d+):(\d+)-->/',
//					$text, $m, PREG_OFFSET_CAPTURE, $pos )
//				) {
//					break;
//				}
//				$ns = $m[1][0];
//				$key = $m[2][0];
//				$sub->internals[$ns][$key] = this.internals[$ns][$key];
//				$pos = $m[0][1] + strlen( $m[0][0] );
//			}
//
//			# Interwiki links
//			$pos = 0;
//			while ( $pos < strlen( $text ) ) {
//				if ( !preg_match( '/<!--IWLINK (\d+)-->/', $text, $m, PREG_OFFSET_CAPTURE, $pos ) ) {
//					break;
//				}
//				$key = $m[1][0];
//				$sub->interwikis[$key] = this.interwikis[$key];
//				$pos = $m[0][1] + strlen( $m[0][0] );
//			}
//			return $sub;
//		}
//
//		/**
//		* Returns true if the memory requirements of this Object are getting large
//		* @return boolean
//		*/
//		public function isBig() {
//			global $wgLinkHolderBatchSize;
//			return this.size > $wgLinkHolderBatchSize;
//		}

	/**
	* Clear all stored link holders.
	* Make sure you don't have any text left using these link holders, before you call this
	*/
	public void clear() {
		this.internals.Clear();//
//			this.interwikis = [];
		// this.size = 0;
	}

	/**
	* Make a link placeholder. The text returned can be later resolved to a real link with
	* replaceLinkHolders(). This is done for two reasons: firstly to avoid further
	* parsing of interwiki links, and secondly to allow all existence checks and
	* article length checks (for stub links) to be bundled into a single query.
	*
	* @param Title $nt
	* @param String $text
	* @param array $query [optional]
	* @param String $trail [optional]
	* @param String $prefix [optional]
	* @return String
	*/
	public void makeHolder(Bry_bfr bfr, XomwTitle nt, byte[] text, byte[][] query, byte[] trail, byte[] prefix) {
		if (nt == null) {
			// Fail gracefully
			bfr.Add_str_a7("<!-- ERROR -->").Add(prefix).Add(text).Add(trail);
		}
		else {
			// Separate the link trail from the rest of the link
//				list( $inside, $trail ) = Linker::splitTrail( $trail );
			byte[] inside = Bry_.Empty;

			XomwLinkHolderItem entry = new XomwLinkHolderItem
				( nt
				, tmp.Add_bry_many(prefix, text, inside).To_bry_and_clear()
				, query);

			if (nt.isExternal()) {
				// Use a globally unique ID to keep the objects mergable
//					$key = this.parent->nextLinkID();
//					this.interwikis[$key] = $entry;
//					$retVal = "<!--IWLINK $key-->{$trail}";
			}
			else {
				int key = this.parent.nextLinkID();
				this.internals.Add(key, entry);
				bfr.Add(Bry__link__bgn).Add_int_variable(key).Add(Gfh_tag_.Comm_end).Add(trail);	// "<!--LINK $ns:$key-->{$trail}";
			}
		}
	}

	/**
	* Replace <!--LINK--> link placeholders with actual links, in the buffer
	*
	* @param String $text
	*/
	public boolean replace(XomwParserBfr pbfr) {
		return this.replaceInternal(pbfr);
//			$this->replaceInterwiki( $text );
	}
	public byte[] replace(XomwParserBfr pbfr, byte[] text) {
		boolean rv = this.replace(pbfr.Init(text));
		return rv ? pbfr.Trg().To_bry_and_clear() : pbfr.Src().To_bry_and_clear();
	}

	/**
	* Replace @gplx.Internal protected links
	* @param String $text
	*/
	private boolean replaceInternal(XomwParserBfr pbfr) {
		if (internals.Len() == 0) {
			return false;
		}

		// SKIP:Replace_internals does db lookup to identify redlinks;
//			global $wgContLang;
//
//			$colours = [];
//			$linkCache = LinkCache::singleton();
//			$output = this.parent->getOutput();
		XomwLinkRenderer linkRenderer = this.parent.getLinkRenderer();
//
//			$dbr = wfGetDB( DB_REPLICA );
//
//			# Sort by namespace
//			ksort( this.internals );
//
//			$linkcolour_ids = [];
//
//			# Generate query
//			$lb = new LinkBatch();
//			$lb->setCaller( __METHOD__ );
//
//			foreach ( this.internals as $ns => $entries ) {
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
//				this.doVariants( $colours );
//			}

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
			XomwLinkHolderItem item = internals.Get_by(link_key);

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
//
//					$replacePairs[$searchkey] = $link;
//				}
//			}
			bfr.Add_mid(src, prv, link_bgn);
			linkRenderer.makePreloadedLink(bfr, item.Title(), item.Text(), Bry_.Empty, extraAtrs, query.Clear());
			cur = key_end + Gfh_tag_.Comm_end_len;
			prv = cur;
		}
//			$replacer = new HashtableReplacer( $replacePairs, 1 );
//
//			# Do the thing
//			$text = preg_replace_callback(
//				'/(<!--LINK .*?-->)/',
//				$replacer->cb(),
//				$text
//			);
		return true;
	}

//		/**
//		* Replace interwiki links
//		* @param String $text
//		*/
//		protected function replaceInterwiki( &$text ) {
//			if ( empty( this.interwikis ) ) {
//				return;
//			}
//
//			# Make interwiki link HTML
//			$output = this.parent->getOutput();
//			$replacePairs = [];
//			$linkRenderer = this.parent->getLinkRenderer();
//			foreach ( this.interwikis as $key => $link ) {
//				$replacePairs[$key] = $linkRenderer->makeLink(
//					$link['title'],
//					new HtmlArmor( $link['text'] )
//				);
//				$output->addInterwikiLink( $link['title'] );
//			}
//			$replacer = new HashtableReplacer( $replacePairs, 1 );
//
//			$text = preg_replace_callback(
//				'/<!--IWLINK (.*?)-->/',
//				$replacer->cb(),
//				$text );
//		}
//
//		/**
//		* Modify this.internals and $colours according to language variant linking rules
//		* @param array $colours
//		*/
//		protected function doVariants( &$colours ) {
//			global $wgContLang;
//			$linkBatch = new LinkBatch();
//			$variantMap = []; // maps $pdbkey_Variant => $keys (of link holders)
//			$output = this.parent->getOutput();
//			$linkCache = LinkCache::singleton();
//			$titlesToBeConverted = '';
//			$titlesAttrs = [];
//
//			// Concatenate titles to a single String, thus we only need auto convert the
//			// single String to all variants. This would improve parser's performance
//			// significantly.
//			foreach ( this.internals as $ns => $entries ) {
//				if ( $ns == NS_SPECIAL ) {
//					continue;
//				}
//				foreach ( $entries as $index => $entry ) {
//					$pdbk = $entry['pdbk'];
//					// we only deal with new links (in its first query)
//					if ( !isset( $colours[$pdbk] ) || $colours[$pdbk] === 'new' ) {
//						$titlesAttrs[] = [ $index, $entry['title'] ];
//						// separate titles with \0 because it would never appears
//						// in a valid title
//						$titlesToBeConverted .= $entry['title']->getText() . "\0";
//					}
//				}
//			}
//
//			// Now do the conversion and explode String to text of titles
//			$titlesAllVariants = $wgContLang->autoConvertToAllVariants( rtrim( $titlesToBeConverted, "\0" ) );
//			$allVariantsName = array_keys( $titlesAllVariants );
//			foreach ( $titlesAllVariants as &$titlesVariant ) {
//				$titlesVariant = explode( "\0", $titlesVariant );
//			}
//
//			// Then add variants of links to link batch
//			$parentTitle = this.parent->getTitle();
//			foreach ( $titlesAttrs as $i => $attrs ) {
//				/** @var Title $title */
//				list( $index, $title ) = $attrs;
//				$ns = $title->getNamespace();
//				$text = $title->getText();
//
//				foreach ( $allVariantsName as $variantName ) {
//					$textVariant = $titlesAllVariants[$variantName][$i];
//					if ( $textVariant === $text ) {
//						continue;
//					}
//
//					$variantTitle = Title::makeTitle( $ns, $textVariant );
//
//					// Self-link checking for mixed/different variant titles. At this point, we
//					// already know the exact title does not exist, so the link cannot be to a
//					// variant of the current title that exists as a separate page.
//					if ( $variantTitle->equals( $parentTitle ) && !$title->hasFragment() ) {
//						this.internals[$ns][$index]['selflink'] = true;
//						continue 2;
//					}
//
//					$linkBatch->addObj( $variantTitle );
//					$variantMap[$variantTitle->getPrefixedDBkey()][] = "$ns:$index";
//				}
//			}
//
//			// process categories, check if a category exists in some variant
//			$categoryMap = []; // maps $category_variant => $category (dbkeys)
//			$varCategories = []; // category replacements oldDBkey => newDBkey
//			foreach ( $output->getCategoryLinks() as $category ) {
//				$categoryTitle = Title::makeTitleSafe( NS_CATEGORY, $category );
//				$linkBatch->addObj( $categoryTitle );
//				$variants = $wgContLang->autoConvertToAllVariants( $category );
//				foreach ( $variants as $variant ) {
//					if ( $variant !== $category ) {
//						$variantTitle = Title::makeTitleSafe( NS_CATEGORY, $variant );
//						if ( is_null( $variantTitle ) ) {
//							continue;
//						}
//						$linkBatch->addObj( $variantTitle );
//						$categoryMap[$variant] = [ $category, $categoryTitle ];
//					}
//				}
//			}
//
//			if ( !$linkBatch->isEmpty() ) {
//				// construct query
//				$dbr = wfGetDB( DB_REPLICA );
//				$fields = array_merge(
//					LinkCache::getSelectFields(),
//					[ 'page_namespace', 'page_title' ]
//				);
//
//				$varRes = $dbr->select( 'page',
//					$fields,
//					$linkBatch->constructSet( 'page', $dbr ),
//					__METHOD__
//				);
//
//				$linkcolour_ids = [];
//				$linkRenderer = this.parent->getLinkRenderer();
//
//				// for each found variants, figure out link holders and replace
//				foreach ( $varRes as $s ) {
//					$variantTitle = Title::makeTitle( $s->page_namespace, $s->page_title );
//					$varPdbk = $variantTitle->getPrefixedDBkey();
//					$vardbk = $variantTitle->getDBkey();
//
//					$holderKeys = [];
//					if ( isset( $variantMap[$varPdbk] ) ) {
//						$holderKeys = $variantMap[$varPdbk];
//						$linkCache->addGoodLinkObjFromRow( $variantTitle, $s );
//						$output->addLink( $variantTitle, $s->page_id );
//					}
//
//					// loop over link holders
//					foreach ( $holderKeys as $key ) {
//						list( $ns, $index ) = explode( ':', $key, 2 );
//						$entry =& this.internals[$ns][$index];
//						$pdbk = $entry['pdbk'];
//
//						if ( !isset( $colours[$pdbk] ) || $colours[$pdbk] === 'new' ) {
//							// found link in some of the variants, replace the link holder data
//							$entry['title'] = $variantTitle;
//							$entry['pdbk'] = $varPdbk;
//
//							// set pdbk and colour
//							$colours[$varPdbk] = $linkRenderer->getLinkClasses( $variantTitle );
//							$linkcolour_ids[$s->page_id] = $pdbk;
//						}
//					}
//
//					// check if the Object is a variant of a category
//					if ( isset( $categoryMap[$vardbk] ) ) {
//						list( $oldkey, $oldtitle ) = $categoryMap[$vardbk];
//						if ( !isset( $varCategories[$oldkey] ) && !$oldtitle->exists() ) {
//							$varCategories[$oldkey] = $vardbk;
//						}
//					}
//				}
//				Hooks::run( 'GetLinkColours', [ $linkcolour_ids, &$colours ] );
//
//				// rebuild the categories in original order (if there are replacements)
//				if ( count( $varCategories ) > 0 ) {
//					$newCats = [];
//					$originalCats = $output->getCategories();
//					foreach ( $originalCats as $cat => $sortkey ) {
//						// make the replacement
//						if ( array_key_exists( $cat, $varCategories ) ) {
//							$newCats[$varCategories[$cat]] = $sortkey;
//						} else {
//							$newCats[$cat] = $sortkey;
//						}
//					}
//					$output->setCategoryLinks( $newCats );
//				}
//			}
//		}
//
//		/**
//		* Replace <!--LINK--> link placeholders with plain text of links
//		* (not HTML-formatted).
//		*
//		* @param String $text
//		* @return String
//		*/
//		public function replaceText( $text ) {
//			$text = preg_replace_callback(
//				'/<!--(LINK|IWLINK) (.*?)-->/',
//				[ &$this, 'replaceTextCallback' ],
//				$text );
//
//			return $text;
//		}
//
//		/**
//		* Callback for replaceText()
//		*
//		* @param array $matches
//		* @return String
//		* @private
//		*/
//		public function replaceTextCallback( $matches ) {
//			$type = $matches[1];
//			$key = $matches[2];
//			if ( $type == 'LINK' ) {
//				list( $ns, $index ) = explode( ':', $key, 2 );
//				if ( isset( this.internals[$ns][$index]['text'] ) ) {
//					return this.internals[$ns][$index]['text'];
//				}
//			} elseif ( $type == 'IWLINK' ) {
//				if ( isset( this.interwikis[$key]['text'] ) ) {
//					return this.interwikis[$key]['text'];
//				}
//			}
//			return $matches[0];
//		}


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
	public void Test__add(XomwTitle ttl, byte[] capt) {
		int key = parent.nextLinkID();
		XomwLinkHolderItem item = new XomwLinkHolderItem(ttl, capt, Bry_.Ary_empty);
		internals.Add(key, item);
	}
	private static final    byte[] Bry__link__bgn = Bry_.new_a7("<!--LINK ");
}
class XomwLinkHolderList {
	private int ary_len = 0, ary_max = 128;
	private XomwLinkHolderItem[] ary = new XomwLinkHolderItem[128];
	public int Len() {return ary_len;}
	public void Clear() {
		ary_len = 0;
		if (ary_max > 128)
			ary = new XomwLinkHolderItem[128];
	}
	public void Add(int key, XomwLinkHolderItem item) {
		if (key >= ary_max) {
			int new_max = ary_max * 2;
			ary = (XomwLinkHolderItem[])Array_.Resize(ary, new_max);
			ary_max = new_max;
		}
		ary[key] = item;
		ary_len++;
	}
	public XomwLinkHolderItem Get_by(int key) {return ary[key];}
}
class XomwLinkHolderItem {
	public XomwLinkHolderItem(XomwTitle title, byte[] text, byte[][] query) {
		this.title = title;
		this.text = text;
		this.query = query;
	}
	public XomwTitle Title() {return title;} private final    XomwTitle title;
	public byte[] Text()      {return text;} private final    byte[] text;
	public byte[] Pdbk()      {return title.getPrefixedDBkey();}
	public byte[][] Query()   {return query;} private final    byte[][] query;
}
