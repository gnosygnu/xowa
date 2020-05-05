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
package gplx.xowa.mediawiki.includes.page; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
// MW.SRC:1.33.1
/**
* Special handling for category pages
*/
public class XomwWikiCategoryPage extends XomwWikiPage { 	public XomwWikiCategoryPage(XomwTitle title) {super(title);
	}
//
//		/**
//		* Don't return a 404 for categories in use.
//		* In use defined as: either the actual page exists
//		* or the category currently has members.
//		*
//		* @return boolean
//		*/
//		public function hasViewableContent() {
//			if ( parent::hasViewableContent() ) {
//				return true;
//			} else {
//				$cat = Category::newFromTitle( $this->mTitle );
//				// If any of these are not 0, then has members
//				if ( $cat->getPageCount()
//					|| $cat->getSubcatCount()
//					|| $cat->getFileCount()
//				) {
//					return true;
//				}
//			}
//			return false;
//		}
//
//		/**
//		* Checks if a category is hidden.
//		*
//		* @since 1.27
//		*
//		* @return boolean
//		*/
//		public function isHidden() {
//			$pageId = $this->getTitle()->getArticleID();
//			$pageProps = PageProps::getInstance()->getProperties( $this->getTitle(), 'hiddencat' );
//
//			return isset( $pageProps[$pageId] );
//		}
//
//		/**
//		* Checks if a category is expected to be an unused category.
//		*
//		* @since 1.33
//		*
//		* @return boolean
//		*/
//		public function isExpectedUnusedCategory() {
//			$pageId = $this->getTitle()->getArticleID();
//			$pageProps = PageProps::getInstance()->getProperties( $this->getTitle(), 'expectunusedcategory' );
//
//			return isset( $pageProps[$pageId] );
//		}
}
