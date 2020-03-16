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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
// MW.SRC:1.33
public class XomwCategoryViewer {// extends ContextSource 
//		/** @var int */
//		public limit;
//
//		/** @var array */
//		public from;
//
//		/** @var array */
//		public until;
//
//		/** @var String[] */
//		public articles;
//
//		/** @var array */
//		public articles_start_char;
//
//		/** @var array */
//		public children;
//
//		/** @var array */
//		public children_start_char;
//
//		/** @var boolean */
//		public showGallery;
//
//		/** @var array */
//		public imgsNoGallery_start_char;
//
//		/** @var array */
//		public imgsNoGallery;
//
//		/** @var array */
//		public nextPage;
//
//		/** @var array */
//		protected prevPage;
//
//		/** @var array */
//		public flip;
//
//		/** @var Title */
//		public title;
//
//		/** @var Collation */
//		public collation;
//
//		/** @var ImageGalleryBase */
//		public gallery;
//
//		/** @var Category Category Object for this page. */
//		private cat;
//
//		/** @var array The original query array, to be used in generating paging links. */
//		private query;
//
//		/**
//		* @since 1.19 context is a second, required parameter
//		* @param Title title
//		* @param IContextSource context
//		* @param array from An array with keys page, subcat,
//		*        and file for offset of results of each section (since 1.17)
//		* @param array until An array with 3 keys for until of each section (since 1.17)
//		* @param array query
//		*/
//		function __construct(title, IContextSource context, from = [],
//			until = [], query = []
//		) {
//			this->title = title;
//			this->setContext(context);
//			this->getOutput()->addModuleStyles([
//				'mediawiki.action.view.categoryPage.styles'
//			]);
//			this->from = from;
//			this->until = until;
//			this->limit = context->getConfig()->get('CategoryPagingLimit');
//			this->cat = Category::newFromTitle(title);
//			this->query = query;
//			this->collation = Collation::singleton();
//			unset(this->query['title']);
//		}
//
//		/**
//		* Format the category data list.
//		*
//		* @return String HTML output
//		*/
//		public function getHTML() {
//			this->showGallery = this->getConfig()->get('CategoryMagicGallery')
//				&& !this->getOutput()->mNoGallery;
//
//			this->clearCategoryState();
//			this->doCategoryQuery();
//			this->finaliseCategoryState();
//
//			r = this->getSubcategorySection() .
//				this->getPagesSection() .
//				this->getImageSection();
//
//			if (r == '') {
//				// If there is no category content to display, only
//				// show the top part of the navigation links.
//				// @todo FIXME: Cannot be completely suppressed because it
//				//        is unknown if 'until' or 'from' makes this
//				//        give 0 results.
//				r = this->getCategoryTop();
//			} else {
//				r = this->getCategoryTop() .
//					r .
//					this->getCategoryBottom();
//			}
//
//			// Give a proper message if category is empty
//			if (r == '') {
//				r = this->msg('category-empty')->parseAsBlock();
//			}
//
//			lang = this->getLanguage();
//			attribs = [
//				'class' => 'mw-category-generated',
//				'lang' => lang->getHtmlCode(),
//				'dir' => lang->getDir()
//			];
//			# put a div around the headings which are in the user language
//			r = Html::openElement('div', attribs) . r . '</div>';
//
//			return r;
//		}
//
//		function clearCategoryState() {
//			this->articles = [];
//			this->articles_start_char = [];
//			this->children = [];
//			this->children_start_char = [];
//			if (this->showGallery) {
//				// Note that null for mode is taken to mean use default.
//				mode = this->getRequest()->getVal('gallerymode', null);
//				try {
//					this->gallery = ImageGalleryBase::factory(mode, this->getContext());
//				} catch (Exception e) {
//					// User specified something invalid, fallback to default.
//					this->gallery = ImageGalleryBase::factory(false, this->getContext());
//				}
//
//				this->gallery->setHideBadImages();
//			} else {
//				this->imgsNoGallery = [];
//				this->imgsNoGallery_start_char = [];
//			}
//		}
//
//		/**
//		* Add a subcategory to the @gplx.Internal protected lists, using a Category Object
//		* @param Category cat
//		* @param String sortkey
//		* @param int pageLength
//		*/
//		function addSubcategoryObject(Category cat, sortkey, pageLength) {
//			// Subcategory; strip the 'Category' namespace from the link text.
//			title = cat->getTitle();
//
//			this->children[] = this->generateLink(
//				'subcat',
//				title,
//				title->isRedirect(),
//				htmlspecialchars(title->getText())
//			);
//
//			this->children_start_char[] =
//				this->getSubcategorySortChar(cat->getTitle(), sortkey);
//		}
//
//		function generateLink(type, Title title, isRedirect, html = null) {
//			link = null;
//			Hooks::run('CategoryViewer::generateLink', [ type, title, html, &link ]);
//			if (link === null) {
//				linkRenderer = MediaWikiServices::getInstance()->getLinkRenderer();
//				if (html !== null) {
//					html = new HtmlArmor(html);
//				}
//				link = linkRenderer->makeLink(title, html);
//			}
//			if (isRedirect) {
//				link = '<span class="redirect-in-category">' . link . '</span>';
//			}
//
//			return link;
//		}
//
//		/**
//		* Get the character to be used for sorting subcategories.
//		* If there's a link from Category:A to Category:B, the sortkey of the resulting
//		* entry in the categorylinks table is Category:A, not A, which it SHOULD be.
//		* Workaround: If sortkey == "Category:".title, than use title for sorting,
//		* else use sortkey...
//		*
//		* @param Title title
//		* @param String sortkey The human-readable sortkey (before transforming to icu or whatever).
//		* @return String
//		*/
//		function getSubcategorySortChar(title, sortkey) {
//			if (title->getPrefixedText() == sortkey) {
//				word = title->getDBkey();
//			} else {
//				word = sortkey;
//			}
//
//			firstChar = this->collation->getFirstLetter(word);
//
//			return MediaWikiServices::getInstance()->getContentLanguage()->convert(firstChar);
//		}
//
//		/**
//		* Add a page in the image namespace
//		* @param Title title
//		* @param String sortkey
//		* @param int pageLength
//		* @param boolean isRedirect
//		*/
//		function addImage(Title title, sortkey, pageLength, isRedirect = false) {
//			if (this->showGallery) {
//				flip = this->flip['file'];
//				if (flip) {
//					this->gallery->insert(title);
//				} else {
//					this->gallery->add(title);
//				}
//			} else {
//				this->imgsNoGallery[] = this->generateLink('image', title, isRedirect);
//
//				this->imgsNoGallery_start_char[] = MediaWikiServices::getInstance()->
//					getContentLanguage()->convert(this->collation->getFirstLetter(sortkey));
//			}
//		}
//
//		/**
//		* Add a miscellaneous page
//		* @param Title title
//		* @param String sortkey
//		* @param int pageLength
//		* @param boolean isRedirect
//		*/
//		function addPage(title, sortkey, pageLength, isRedirect = false) {
//			this->articles[] = this->generateLink('page', title, isRedirect);
//
//			this->articles_start_char[] = MediaWikiServices::getInstance()->
//				getContentLanguage()->convert(this->collation->getFirstLetter(sortkey));
//		}
//
//		function finaliseCategoryState() {
//			if (this->flip['subcat']) {
//				this->children = array_reverse(this->children);
//				this->children_start_char = array_reverse(this->children_start_char);
//			}
//			if (this->flip['page']) {
//				this->articles = array_reverse(this->articles);
//				this->articles_start_char = array_reverse(this->articles_start_char);
//			}
//			if (!this->showGallery && this->flip['file']) {
//				this->imgsNoGallery = array_reverse(this->imgsNoGallery);
//				this->imgsNoGallery_start_char = array_reverse(this->imgsNoGallery_start_char);
//			}
//		}
//
//		function doCategoryQuery() {
//			dbr = wfGetDB(DB_REPLICA, 'category');
//
//			this->nextPage = [
//				'page' => null,
//				'subcat' => null,
//				'file' => null,
//			];
//			this->prevPage = [
//				'page' => null,
//				'subcat' => null,
//				'file' => null,
//			];
//
//			this->flip = [ 'page' => false, 'subcat' => false, 'file' => false ];
//
//			foreach ([ 'page', 'subcat', 'file' ] as type) {
//				# Get the sortkeys for start/end, if applicable.  Note that if
//				# the collation in the database differs from the one
//				# set in wgCategoryCollation, pagination might go totally haywire.
//				extraConds = [ 'cl_type' => type ];
//				if (isset(this->from[type]) && this->from[type] !== null) {
//					extraConds[] = 'cl_sortkey >= '
//						. dbr->addQuotes(this->collation->getSortKey(this->from[type]));
//				} elseif (isset(this->until[type]) && this->until[type] !== null) {
//					extraConds[] = 'cl_sortkey < '
//						. dbr->addQuotes(this->collation->getSortKey(this->until[type]));
//					this->flip[type] = true;
//				}
//
//				res = dbr->select(
//					[ 'page', 'categorylinks', 'category' ],
//					array_merge(
//						LinkCache::getSelectFields(),
//						[
//							'page_namespace',
//							'page_title',
//							'cl_sortkey',
//							'cat_id',
//							'cat_title',
//							'cat_subcats',
//							'cat_pages',
//							'cat_files',
//							'cl_sortkey_prefix',
//							'cl_collation'
//						]
//					),
//					array_merge([ 'cl_to' => this->title->getDBkey() ], extraConds),
//					__METHOD__,
//					[
//						'USE INDEX' => [ 'categorylinks' => 'cl_sortkey' ],
//						'LIMIT' => this->limit + 1,
//						'ORDER BY' => this->flip[type] ? 'cl_sortkey DESC' : 'cl_sortkey',
//					],
//					[
//						'categorylinks' => [ 'JOIN', 'cl_from = page_id' ],
//						'category' => [ 'LEFT JOIN', [
//							'cat_title = page_title',
//							'page_namespace' => NS_CATEGORY
//						] ]
//					]
//				);
//
//				Hooks::run('CategoryViewer::doCategoryQuery', [ type, res ]);
//				linkCache = MediaWikiServices::getInstance()->getLinkCache();
//
//				count = 0;
//				foreach (res as row) {
//					title = Title::newFromRow(row);
//					linkCache->addGoodLinkObjFromRow(title, row);
//
//					if (row->cl_collation === '') {
//						// Hack to make sure that while updating from 1.16 schema
//						// and db is inconsistent, that the sky doesn't fall.
//						// See r83544. Could perhaps be removed in a couple decades...
//						humanSortkey = row->cl_sortkey;
//					} else {
//						humanSortkey = title->getCategorySortkey(row->cl_sortkey_prefix);
//					}
//
//					if (++count > this->limit) {
//						# We've reached the one extra which shows that there
//						# are additional pages to be had. Stop here...
//						this->nextPage[type] = humanSortkey;
//						break;
//					}
//					if (count == this->limit) {
//						this->prevPage[type] = humanSortkey;
//					}
//
//					if (title->getNamespace() == NS_CATEGORY) {
//						cat = Category::newFromRow(row, title);
//						this->addSubcategoryObject(cat, humanSortkey, row->page_len);
//					} elseif (title->getNamespace() == NS_FILE) {
//						this->addImage(title, humanSortkey, row->page_len, row->page_is_redirect);
//					} else {
//						this->addPage(title, humanSortkey, row->page_len, row->page_is_redirect);
//					}
//				}
//			}
//		}
//
//		/**
//		* @return String
//		*/
//		function getCategoryTop() {
//			r = this->getCategoryBottom();
//			return r === ''
//				? r
//				: "<br style=\"clear:both;\"/>\n" . r;
//		}
//
//		/**
//		* @return String
//		*/
//		function getSubcategorySection() {
//			# Don't show subcategories section if there are none.
//			r = '';
//			rescnt = count(this->children);
//			dbcnt = this->cat->getSubcatCount();
//			// This function should be called even if the result isn't used, it has side-effects
//			countmsg = this->getCountMessage(rescnt, dbcnt, 'subcat');
//
//			if (rescnt > 0) {
//				# Showing subcategories
//				r .= "<div id=\"mw-subcategories\">\n";
//				r .= '<h2>' . this->msg('subcategories')->parse() . "</h2>\n";
//				r .= countmsg;
//				r .= this->getSectionPagingLinks('subcat');
//				r .= this->formatList(this->children, this->children_start_char);
//				r .= this->getSectionPagingLinks('subcat');
//				r .= "\n</div>";
//			}
//			return r;
//		}
//
//		/**
//		* @return String
//		*/
//		function getPagesSection() {
//			name = this->getOutput()->getUnprefixedDisplayTitle();
//			# Don't show articles section if there are none.
//			r = '';
//
//			# @todo FIXME: Here and in the other two sections: we don't need to bother
//			# with this rigmarole if the entire category contents fit on one page
//			# and have already been retrieved.  We can just use rescnt in that
//			# case and save a query and some logic.
//			dbcnt = this->cat->getPageCount() - this->cat->getSubcatCount()
//				- this->cat->getFileCount();
//			rescnt = count(this->articles);
//			// This function should be called even if the result isn't used, it has side-effects
//			countmsg = this->getCountMessage(rescnt, dbcnt, 'article');
//
//			if (rescnt > 0) {
//				r = "<div id=\"mw-pages\">\n";
//				r .= '<h2>' . this->msg('category_header')->rawParams(name)->parse() . "</h2>\n";
//				r .= countmsg;
//				r .= this->getSectionPagingLinks('page');
//				r .= this->formatList(this->articles, this->articles_start_char);
//				r .= this->getSectionPagingLinks('page');
//				r .= "\n</div>";
//			}
//			return r;
//		}
//
//		/**
//		* @return String
//		*/
//		function getImageSection() {
//			name = this->getOutput()->getUnprefixedDisplayTitle();
//			r = '';
//			rescnt = this->showGallery ? this->gallery->count() : count(this->imgsNoGallery);
//			dbcnt = this->cat->getFileCount();
//			// This function should be called even if the result isn't used, it has side-effects
//			countmsg = this->getCountMessage(rescnt, dbcnt, 'file');
//
//			if (rescnt > 0) {
//				r .= "<div id=\"mw-category-media\">\n";
//				r .= '<h2>' .
//					this->msg('category-media-header')->rawParams(name)->parse() .
//					"</h2>\n";
//				r .= countmsg;
//				r .= this->getSectionPagingLinks('file');
//				if (this->showGallery) {
//					r .= this->gallery->toHTML();
//				} else {
//					r .= this->formatList(this->imgsNoGallery, this->imgsNoGallery_start_char);
//				}
//				r .= this->getSectionPagingLinks('file');
//				r .= "\n</div>";
//			}
//			return r;
//		}
//
//		/**
//		* Get the paging links for a section (subcats/pages/files), to go at the top and bottom
//		* of the output.
//		*
//		* @param String type 'page', 'subcat', or 'file'
//		* @return String HTML output, possibly empty if there are no other pages
//		*/
//		private function getSectionPagingLinks(type) {
//			if (isset(this->until[type]) && this->until[type] !== null) {
//				// The new value for the until parameter should be pointing to the first
//				// result displayed on the page which is the second last result retrieved
//				// from the database.The next link should have a from parameter pointing
//				// to the until parameter of the current page.
//				if (this->nextPage[type] !== null) {
//					return this->pagingLinks(this->prevPage[type], this->until[type], type);
//				} else {
//					// If the nextPage variable is null, it means that we have reached the first page
//					// and therefore the previous link should be disabled.
//					return this->pagingLinks(null, this->until[type], type);
//				}
//			} elseif (this->nextPage[type] !== null
//				|| (isset(this->from[type]) && this->from[type] !== null)
//			) {
//				return this->pagingLinks(this->from[type], this->nextPage[type], type);
//			} else {
//				return '';
//			}
//		}
//
//		/**
//		* @return String
//		*/
//		function getCategoryBottom() {
//			return '';
//		}
//
//		/**
//		* Format a list of articles chunked by letter, either as a
//		* bullet list or a columnar format, depending on the length.
//		*
//		* @param array articles
//		* @param array articles_start_char
//		* @param int cutoff
//		* @return String
//		* @private
//		*/
//		function formatList(articles, articles_start_char, cutoff = 6) {
//			list = '';
//			if (count(articles) > cutoff) {
//				list = self::columnList(articles, articles_start_char);
//			} elseif (count(articles) > 0) {
//				// for short lists of articles in categories.
//				list = self::shortList(articles, articles_start_char);
//			}
//
//			pageLang = this->title->getPageLanguage();
//			attribs = [ 'lang' => pageLang->getHtmlCode(), 'dir' => pageLang->getDir(),
//				'class' => 'mw-content-' . pageLang->getDir() ];
//			list = Html::rawElement('div', attribs, list);
//
//			return list;
//		}
//
//		/**
//		* Format a list of articles chunked by letter in a three-column list, ordered
//		* vertically. This is used for categories with a significant number of pages.
//		*
//		* TODO: Take the headers into account when creating columns, so they're
//		* more visually equal.
//		*
//		* TODO: shortList and columnList are similar, need merging
//		*
//		* @param String[] articles HTML links to each article
//		* @param String[] articles_start_char The header characters for each article
//		* @return String HTML to output
//		* @private
//		*/
	public static String columnList(String[] articles, String[] articles_start_char) {
//			columns = array_combine(articles, articles_start_char);
		XophpArray columns = XophpArray.New();

		String ret = ""; //XophpHtml_.openElement("div", [ "class" => "mw-category" ]);

		XophpArray colContents = XophpArray.New();

		// Kind of like array_flip() here, but we keep duplicates in an
		// array instead of dropping them.
		int columns_len = columns.count();
		for (int i = 0; i < columns_len; i++) {
			XophpArrayItm itm = columns.Get_at_itm(i);
			String article = itm.Key();
			String charVal = (String)itm.Val();
			if (!colContents.isset((String)colContents.Get_by(charVal))) {
				colContents.Set(charVal, XophpArray.New());
			}
			colContents.Set(charVal, article); // colContents[char][] = article;
		}

		int colContentsLen = colContents.count();
		for (int i = 0; i < colContentsLen; i++) {
			XophpArrayItm itm = columns.Get_at_itm(i);
			String charVal = itm.Key();
			XophpArray articlesItm = (XophpArray)itm.Val();
			// Change space to non-breaking space to keep headers aligned
			String h3char = String_.Eq(charVal, " ") ? XophpHtml_.ENTITYREF_NBSP : XophpHtml_.htmlspecialchars(charVal);

			ret += "<div class=\"mw-category-group\"><h3>" + h3char;
			ret += "</h3>\n";

			ret += "<ul><li>";
			ret += XophpArray_.implode("</li>\n<li>", articlesItm);
			ret += "</li></ul></div>";

		}

//			ret += XophpHtml_.closeElement("div");
		return ret;
	}

	/**
	* Format a list of articles chunked by letter in a bullet list. This is used
	* for categories with a small number of pages (when columns aren't needed).
	* @param String[] articles HTML links to each article
	* @param String[] articles_start_char The header characters for each article
	* @return String HTML to output
	* @private
	*/
	public static String shortList(String[] articles, String[] articles_start_char) {
		String r = "<h3>" + XophpHtml_.htmlspecialchars(articles_start_char[0]) + "</h3>\n";
		r += "<ul><li>" + articles[0] + "</li>";
		int articleCount = articles.length;
		for (int index = 1; index < articleCount; index++) {
			if (articles_start_char[index] != articles_start_char[index - 1]) {
				r += "</ul><h3>" + XophpHtml_.htmlspecialchars(articles_start_char[index]) + "</h3>\n<ul>";
			}

			r += "<li>{articles[index]}</li>";
		}
		r += "</ul>";
		return r;
	}

//		/**
//		* Create paging links, as a helper method to getSectionPagingLinks().
//		*
//		* @param String first The 'until' parameter for the generated URL
//		* @param String last The 'from' parameter for the generated URL
//		* @param String type A prefix for parameters, 'page' or 'subcat' or
//		*     'file'
//		* @return String HTML
//		*/
//		private function pagingLinks(first, last, type = '') {
//			prevLink = this->msg('prev-page')->escaped();
//
//			linkRenderer = MediaWikiServices::getInstance()->getLinkRenderer();
//			if (first != '') {
//				prevQuery = this->query;
//				prevQuery["{type}until"] = first;
//				unset(prevQuery["{type}from"]);
//				prevLink = linkRenderer->makeKnownLink(
//					this->addFragmentToTitle(this->title, type),
//					new HtmlArmor(prevLink),
//					[],
//					prevQuery
//				);
//			}
//
//			nextLink = this->msg('next-page')->escaped();
//
//			if (last != '') {
//				lastQuery = this->query;
//				lastQuery["{type}from"] = last;
//				unset(lastQuery["{type}until"]);
//				nextLink = linkRenderer->makeKnownLink(
//					this->addFragmentToTitle(this->title, type),
//					new HtmlArmor(nextLink),
//					[],
//					lastQuery
//				);
//			}
//
//			return this->msg('categoryviewer-pagedlinks')->rawParams(prevLink, nextLink)->escaped();
//		}
//
//		/**
//		* Takes a title, and adds the fragment identifier that
//		* corresponds to the correct segment of the category.
//		*
//		* @param Title title The title (usually this->title)
//		* @param String section Which section
//		* @throws MWException
//		* @return Title
//		*/
//		private function addFragmentToTitle(title, section) {
//			switch (section) {
//				case 'page':
//					fragment = 'mw-pages';
//					break;
//				case 'subcat':
//					fragment = 'mw-subcategories';
//					break;
//				case 'file':
//					fragment = 'mw-category-media';
//					break;
//				default:
//					throw new MWException(__METHOD__ .
//						" Invalid section section.");
//			}
//
//			return Title::makeTitle(title->getNamespace(),
//				title->getDBkey(), fragment);
//		}
//
//		/**
//		* What to do if the category table conflicts with the number of results
//		* returned?  This function says what. Each type is considered independently
//		* of the other types.
//		*
//		* @param int rescnt The number of items returned by our database query.
//		* @param int dbcnt The number of items according to the category table.
//		* @param String type 'subcat', 'article', or 'file'
//		* @return String A message giving the number of items, to output to HTML.
//		*/
//		private function getCountMessage(rescnt, dbcnt, type) {
//			// There are three cases:
//			//   1) The category table figure seems sane.  It might be wrong, but
//			//      we can't do anything about it if we don't recalculate it on ev-
//			//      ery category view.
//			//   2) The category table figure isn't sane, like it's smaller than the
//			//      number of actual results, *but* the number of results is less
//			//      than this->limit and there's no offset.  In this case we still
//			//      know the right figure.
//			//   3) We have no idea.
//
//			// Check if there's a "from" or "until" for anything
//
//			// This is a little ugly, but we seem to use different names
//			// for the paging types then for the messages.
//			if (type === 'article') {
//				pagingType = 'page';
//			} else {
//				pagingType = type;
//			}
//
//			fromOrUntil = false;
//			if ((isset(this->from[pagingType]) && this->from[pagingType] !== null) ||
//				(isset(this->until[pagingType]) && this->until[pagingType] !== null)
//			) {
//				fromOrUntil = true;
//			}
//
//			if (dbcnt == rescnt ||
//				((rescnt == this->limit || fromOrUntil) && dbcnt > rescnt)
//			) {
//				// Case 1: seems sane.
//				totalcnt = dbcnt;
//			} elseif (rescnt < this->limit && !fromOrUntil) {
//				// Case 2: not sane, but salvageable.  Use the number of results.
//				totalcnt = rescnt;
//			} else {
//				// Case 3: hopeless.  Don't give a total count at all.
//				// Messages: category-subcat-count-limited, category-article-count-limited,
//				// category-file-count-limited
//				return this->msg("category-type-count-limited")->numParams(rescnt)->parseAsBlock();
//			}
//			// Messages: category-subcat-count, category-article-count, category-file-count
//			return this->msg("category-type-count")->numParams(rescnt, totalcnt)->parseAsBlock();
//		}
}
