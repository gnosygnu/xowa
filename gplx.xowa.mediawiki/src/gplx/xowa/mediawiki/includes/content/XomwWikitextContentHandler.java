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
package gplx.xowa.mediawiki.includes.content; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
/**
* Content handler for wiki text pages.
*
* @ingroup Content
*/
class XomwWikitextContentHandler extends XomwTextContentHandler { 
	public XomwWikitextContentHandler() {super(XomwDefaultSettings.CONTENT_MODEL_WIKITEXT, XomwDefines.CONTENT_FORMAT_WIKITEXT);
	}

//		protected function getContentClass() {
//			return WikitextContent::class;
//		}
//
//		/**
//		* Returns a WikitextContent Object representing a redirect to the given destination page.
//		*
//		* @param Title $destination The page to redirect to.
//		* @param String $text Text to include in the redirect, if possible.
//		*
//		* @return Content
//		*
//		* @see ContentHandler::makeRedirectContent
//		*/
//		public function makeRedirectContent( Title $destination, $text = '' ) {
//			$optionalColon = '';
//
//			if ( $destination->getNamespace() == NS_CATEGORY ) {
//				$optionalColon = ':';
//			} else {
//				$iw = $destination->getInterwiki();
//				if ( $iw && Language::fetchLanguageName( $iw, null, 'mw' ) ) {
//					$optionalColon = ':';
//				}
//			}
//
//			$mwRedir = MagicWord::get( 'redirect' );
//			$redirectText = $mwRedir->getSynonym( 0 ) .
//				' [[' . $optionalColon . $destination->getFullText() . ']]';
//
//			if ( $text != '' ) {
//				$redirectText .= "\n" . $text;
//			}
//
//			$class = $this->getContentClass();
//			return new $class( $redirectText );
//		}
//
//		/**
//		* Returns true because wikitext supports redirects.
//		*
//		* @return boolean Always true.
//		*
//		* @see ContentHandler::supportsRedirects
//		*/
//		public function supportsRedirects() {
//			return true;
//		}
//
//		/**
//		* Returns true because wikitext supports sections.
//		*
//		* @return boolean Always true.
//		*
//		* @see ContentHandler::supportsSections
//		*/
//		public function supportsSections() {
//			return true;
//		}
//
//		/**
//		* Returns true, because wikitext supports caching using the
//		* ParserCache mechanism.
//		*
//		* @since 1.21
//		*
//		* @return boolean Always true.
//		*
//		* @see ContentHandler::isParserCacheSupported
//		*/
//		public function isParserCacheSupported() {
//			return true;
//		}
//
//		/**
//		* Get file handler
//		* @return FileContentHandler
//		*/
//		protected function getFileHandler() {
//			return new FileContentHandler();
//		}
//
//		public function getFieldsForSearchIndex( SearchEngine $engine ) {
//			$fields = parent::getFieldsForSearchIndex( $engine );
//
//			$fields['heading'] =
//				$engine->makeSearchFieldMapping( 'heading', SearchIndexField::INDEX_TYPE_TEXT );
//			$fields['heading']->setFlag( SearchIndexField::FLAG_SCORING );
//
//			$fields['auxiliary_text'] =
//				$engine->makeSearchFieldMapping( 'auxiliary_text', SearchIndexField::INDEX_TYPE_TEXT );
//
//			$fields['opening_text'] =
//				$engine->makeSearchFieldMapping( 'opening_text', SearchIndexField::INDEX_TYPE_TEXT );
//			$fields['opening_text']->setFlag( SearchIndexField::FLAG_SCORING |
//											SearchIndexField::FLAG_NO_HIGHLIGHT );
//			// Until we have full first-class content handler for files, we invoke it explicitly here
//			$fields = array_merge( $fields, $this->getFileHandler()->getFieldsForSearchIndex( $engine ) );
//
//			return $fields;
//		}
//
//		public function getDataForSearchIndex( WikiPage $page, ParserOutput $parserOutput,
//											SearchEngine $engine ) {
//			$fields = parent::getDataForSearchIndex( $page, $parserOutput, $engine );
//
//			$structure = new WikiTextStructure( $parserOutput );
//			$fields['heading'] = $structure->headings();
//			// text fields
//			$fields['opening_text'] = $structure->getOpeningText();
//			$fields['text'] = $structure->getMainText(); // overwrites one from ContentHandler
//			$fields['auxiliary_text'] = $structure->getAuxiliaryText();
//			$fields['defaultsort'] = $structure->getDefaultSort();
//
//			// Until we have full first-class content handler for files, we invoke it explicitly here
//			if ( NS_FILE == $page->getTitle()->getNamespace() ) {
//				$fields = array_merge( $fields,
//						$this->getFileHandler()->getDataForSearchIndex( $page, $parserOutput, $engine ) );
//			}
//			return $fields;
//		}

}
