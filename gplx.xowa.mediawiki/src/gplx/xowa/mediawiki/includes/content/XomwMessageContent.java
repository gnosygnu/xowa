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
//	<?php
//	/**
//	* Wrapper content Object allowing to handle a system message as a Content Object.
//	*/
//
//	/**
//	* Wrapper allowing us to handle a system message as a Content Object.
//	* Note that this is generally *not* used to represent content from the
//	* MediaWiki namespace, and that there is no MessageContentHandler.
//	* MessageContent is just intended as glue for wrapping a message programmatically.
//	*
//	* @ingroup Content
//	*/
//	class MessageContent extends AbstractContent {
//
//		/**
//		* @var Message
//		*/
//		protected $mMessage;
//
//		/**
//		* @param Message|String $msg A Message Object, or a message key.
//		* @param String[] $params An optional array of message parameters.
//		*/
//		public function __construct( $msg, $params = null ) {
//			# XXX: messages may be wikitext, html or plain text! and maybe even something else entirely.
//			parent::__construct( CONTENT_MODEL_WIKITEXT );
//
//			if ( is_string( $msg ) ) {
//				$this->mMessage = wfMessage( $msg );
//			} else {
//				$this->mMessage = clone $msg;
//			}
//
//			if ( $params ) {
//				$this->mMessage = $this->mMessage->params( $params );
//			}
//		}
//
//		/**
//		* Fully parse the text from wikitext to HTML.
//		*
//		* @return String Parsed HTML.
//		*/
//		public function getHtml() {
//			return $this->mMessage->parse();
//		}
//
//		/**
//		* Returns the message text. {{-transformation is done.
//		*
//		* @return String Unescaped message text.
//		*/
//		public function getWikitext() {
//			return $this->mMessage->text();
//		}
//
//		/**
//		* Returns the message Object, with any parameters already substituted.
//		*
//		* @return Message The message Object.
//		*/
//		public function getNativeData() {
//			// NOTE: Message objects are mutable. Cloning here makes MessageContent immutable.
//			return clone $this->mMessage;
//		}
//
//		/**
//		* @return String
//		*
//		* @see Content::getTextForSearchIndex
//		*/
//		public function getTextForSearchIndex() {
//			return $this->mMessage->plain();
//		}
//
//		/**
//		* @return String
//		*
//		* @see Content::getWikitextForTransclusion
//		*/
//		public function getWikitextForTransclusion() {
//			return $this->getWikitext();
//		}
//
//		/**
//		* @param int $maxlength Maximum length of the summary text, defaults to 250.
//		*
//		* @return String The summary text.
//		*
//		* @see Content::getTextForSummary
//		*/
//		public function getTextForSummary( $maxlength = 250 ) {
//			return substr( $this->mMessage->plain(), 0, $maxlength );
//		}
//
//		/**
//		* @return int
//		*
//		* @see Content::getSize
//		*/
//		public function getSize() {
//			return strlen( $this->mMessage->plain() );
//		}
//
//		/**
//		* @return Content A copy of this Object
//		*
//		* @see Content::copy
//		*/
//		public function copy() {
//			// MessageContent is immutable (because getNativeData() returns a clone of the Message Object)
//			return $this;
//		}
//
//		/**
//		* @param boolean|null $hasLinks
//		*
//		* @return boolean Always false.
//		*
//		* @see Content::isCountable
//		*/
//		public function isCountable( $hasLinks = null ) {
//			return false;
//		}
//
//		/**
//		* @param Title $title Unused.
//		* @param int $revId Unused.
//		* @param ParserOptions $options Unused.
//		* @param boolean $generateHtml Whether to generate HTML (default: true).
//		*
//		* @return ParserOutput
//		*
//		* @see Content::getParserOutput
//		*/
//		public function getParserOutput( Title $title, $revId = null,
//			ParserOptions $options = null, $generateHtml = true ) {
//			if ( $generateHtml ) {
//				$html = $this->getHtml();
//			} else {
//				$html = '';
//			}
//
//			$po = new ParserOutput( $html );
//			// Message objects are in the user language.
//			$po->recordOption( 'userlang' );
//
//			return $po;
//		}
//
//	}
