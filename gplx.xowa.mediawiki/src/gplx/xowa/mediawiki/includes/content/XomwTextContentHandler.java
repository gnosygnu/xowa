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
* Base content handler implementation for flat text contents.
*
* @ingroup Content
*/
class XomwTextContentHandler extends XomwContentHandler { 
	public XomwTextContentHandler() {super(XomwDefaultSettings.CONTENT_MODEL_TEXT, XomwDefines.CONTENT_FORMAT_TEXT);
	}
	public XomwTextContentHandler(int modelId, String... formats) {super(modelId, formats);
	}

//		/**
//		* Returns the content's text as-is.
//		*
//		* @param Content $content
//		* @param String  $format The serialization format to check
//		*
//		* @return mixed
//		*/
//		public function serializeContent( Content $content, $format = null ) {
//			$this->checkFormat( $format );
//
//			return $content->getNativeData();
//		}
//
//		/**
//		* Attempts to merge differences between three versions. Returns a new
//		* Content Object for a clean merge and false for failure or a conflict.
//		*
//		* All three Content objects passed as parameters must have the same
//		* content model.
//		*
//		* This text-based implementation uses wfMerge().
//		*
//		* @param Content $oldContent The page's previous content.
//		* @param Content $myContent One of the page's conflicting contents.
//		* @param Content $yourContent One of the page's conflicting contents.
//		*
//		* @return Content|boolean
//		*/
//		public function merge3( Content $oldContent, Content $myContent, Content $yourContent ) {
//			$this->checkModelID( $oldContent->getModel() );
//			$this->checkModelID( $myContent->getModel() );
//			$this->checkModelID( $yourContent->getModel() );
//
//			$format = $this->getDefaultFormat();
//
//			$old = $this->serializeContent( $oldContent, $format );
//			$mine = $this->serializeContent( $myContent, $format );
//			$yours = $this->serializeContent( $yourContent, $format );
//
//			$ok = wfMerge( $old, $mine, $yours, $result );
//
//			if ( !$ok ) {
//				return false;
//			}
//
//			if ( !$result ) {
//				return $this->makeEmptyContent();
//			}
//
//			$mergedContent = $this->unserializeContent( $result, $format );
//
//			return $mergedContent;
//		}
//
//		/**
//		* Returns the name of the associated Content class, to
//		* be used when creating new objects. Override expected
//		* by subclasses.
//		*
//		* @since 1.24
//		*
//		* @return String
//		*/
//		protected function getContentClass() {
//			return TextContent::class;
//		}
//
//		/**
//		* Unserializes a Content Object of the type supported by this ContentHandler.
//		*
//		* @since 1.21
//		*
//		* @param String $text Serialized form of the content
//		* @param String $format The format used for serialization
//		*
//		* @return Content The TextContent Object wrapping $text
//		*/
//		public function unserializeContent( $text, $format = null ) {
//			$this->checkFormat( $format );
//
//			$class = $this->getContentClass();
//			return new $class( $text );
//		}
//
//		/**
//		* Creates an empty TextContent Object.
//		*
//		* @since 1.21
//		*
//		* @return Content A new TextContent Object with empty text.
//		*/
//		public function makeEmptyContent() {
//			$class = $this->getContentClass();
//			return new $class( '' );
//		}
//
//		/**
//		* @see ContentHandler::supportsDirectEditing
//		*
//		* @return boolean Default is true for TextContent and derivatives.
//		*/
//		public function supportsDirectEditing() {
//			return true;
//		}
//
//		public function getFieldsForSearchIndex( SearchEngine $engine ) {
//			$fields = parent::getFieldsForSearchIndex( $engine );
//			$fields['language'] =
//				$engine->makeSearchFieldMapping( 'language', SearchIndexField::INDEX_TYPE_KEYWORD );
//
//			return $fields;
//		}
//
//		public function getDataForSearchIndex( WikiPage $page, ParserOutput $output,
//											SearchEngine $engine ) {
//			$fields = parent::getDataForSearchIndex( $page, $output, $engine );
//			$fields['language'] =
//				$this->getPageLanguage( $page->getTitle(), $page->getContent() )->getCode();
//			return $fields;
//		}

}
