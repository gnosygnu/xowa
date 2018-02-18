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
public class TextContent {
	// AbstractContent
	/**
	* @since 1.21
	*
	* @return boolean
	*
	* @see Content::isRedirect
	*/
//		public function isRedirect() {
//			return $this->getRedirectTarget() !== null;
//		}
	public boolean isRedirect() {
		return false;
	}

	private byte[] mText;
	/**
	* @param String $text
	* @param String $model_id
	* @throws MWException
	*/
	public void __construct(byte[] text, String model_id) { // = CONTENT_MODEL_TEXT 
//			parent::__construct( $model_id );
//
//			if ( $text === null || $text === false ) {
//				wfWarn( "TextContent constructed with \$text = " . var_export( $text, true ) . "! "
//					. "This may indicate an error in the caller's scope.", 2 );
//
//				$text = '';
//			}
//
//			if ( !is_string( $text ) ) {
//				throw new MWException( "TextContent expects a String in the constructor." );
//			}
//
		this.mText = text;
	}
//
//		/**
//		* @note Mutable subclasses MUST override this to return a copy!
//		*
//		* @return Content this
//		*/
//		public function copy() {
//			return this; # NOTE: this is ok since TextContent are immutable.
//		}
//
//		public function getTextForSummary( $maxlength = 250 ) {
//			global $wgContLang;
//
//			$text = this.getNativeData();
//
//			$truncatedtext = $wgContLang.truncate(
//				preg_replace( "/[\n\r]/", ' ', $text ),
//				max( 0, $maxlength ) );
//
//			return $truncatedtext;
//		}
//
//		/**
//		* Returns the text's size in bytes.
//		*
//		* @return int
//		*/
//		public function getSize() {
//			$text = this.getNativeData();
//
//			return strlen( $text );
//		}
//
//		/**
//		* Returns true if this content is not a redirect, and $wgArticleCountMethod
//		* is "any".
//		*
//		* @param boolean|null $hasLinks If it is known whether this content contains links,
//		* provide this information here, to avoid redundant parsing to find out.
//		*
//		* @return boolean
//		*/
//		public function isCountable( $hasLinks = null ) {
//			global $wgArticleCountMethod;
//
//			if ( this.isRedirect() ) {
//				return false;
//			}
//
//			if ( $wgArticleCountMethod === 'any' ) {
//				return true;
//			}
//
//			return false;
//		}

	/**
	* Returns the text represented by this Content Object, as a String.
	*
	* @return String The raw text.
	*/
	public byte[] getNativeData() {
		return this.mText;
	}

//		/**
//		* Returns the text represented by this Content Object, as a String.
//		*
//		* @return String The raw text.
//		*/
//		public function getTextForSearchIndex() {
//			return this.getNativeData();
//		}
//
//		/**
//		* Returns attempts to convert this content Object to wikitext,
//		* and then returns the text String. The conversion may be lossy.
//		*
//		* @note this allows any text-based content to be transcluded as if it was wikitext.
//		*
//		* @return String|boolean The raw text, or false if the conversion failed.
//		*/
//		public function getWikitextForTransclusion() {
//			$wikitext = this.convert( CONTENT_MODEL_WIKITEXT, 'lossy' );
//
//			if ( $wikitext ) {
//				return $wikitext.getNativeData();
//			} else {
//				return false;
//			}
//		}
//
//		/**
//		* Do a "\r\n" . "\n" and "\r" . "\n" transformation
//		* as well as trim trailing whitespace
//		*
//		* This was formerly part of Parser::preSaveTransform, but
//		* for non-wikitext content models they probably still want
//		* to normalize line endings without all of the other PST
//		* changes.
//		*
//		* @since 1.28
//		* @param $text
//		* @return String
//		*/
//		public static function normalizeLineEndings( $text ) {
//			return str_replace( [ "\r\n", "\r" ], "\n", rtrim( $text ) );
//		}
//
//		/**
//		* Returns a Content Object with pre-save transformations applied.
//		*
//		* At a minimum, subclasses should make sure to call TextContent::normalizeLineEndings()
//		* either directly or part of Parser::preSaveTransform().
//		*
//		* @param Title $title
//		* @param User $user
//		* @param ParserOptions $popts
//		*
//		* @return Content
//		*/
//		public function preSaveTransform( Title $title, User $user, ParserOptions $popts ) {
//			$text = this.getNativeData();
//			$pst = self::normalizeLineEndings( $text );
//
//			return ( $text === $pst ) ? this : new static( $pst, this.getModel() );
//		}
//
//		/**
//		* Diff this content Object with another content Object.
//		*
//		* @since 1.21
//		*
//		* @param Content $that The other content Object to compare this content Object to.
//		* @param Language $lang The language Object to use for text segmentation.
//		*    If not given, $wgContentLang is used.
//		*
//		* @return Diff A diff representing the changes that would have to be
//		*    made to this content Object to make it equal to $that.
//		*/
//		public function diff( Content $that, Language $lang = null ) {
//			global $wgContLang;
//
//			this.checkModelID( $that.getModel() );
//
//			// @todo could implement this in DifferenceEngine and just delegate here?
//
//			if ( !$lang ) {
//				$lang = $wgContLang;
//			}
//
//			$otext = this.getNativeData();
//			$ntext = $that.getNativeData();
//
//			# Note: Use native PHP diff, external engines don't give us abstract output
//			$ota = explode( "\n", $lang.segmentForDiff( $otext ) );
//			$nta = explode( "\n", $lang.segmentForDiff( $ntext ) );
//
//			$diff = new Diff( $ota, $nta );
//
//			return $diff;
//		}
//
//		/**
//		* Fills the provided ParserOutput Object with information derived from the content.
//		* Unless $generateHtml was false, this includes an HTML representation of the content
//		* provided by getHtml().
//		*
//		* For content models listed in $wgTextModelsToParse, this method will call the MediaWiki
//		* wikitext parser on the text to extract any (wikitext) links, magic words, etc.
//		*
//		* Subclasses may override this to provide custom content processing.
//		* For custom HTML generation alone, it is sufficient to override getHtml().
//		*
//		* @param Title $title Context title for parsing
//		* @param int $revId Revision ID (for {{REVISIONID}})
//		* @param ParserOptions $options Parser options
//		* @param boolean $generateHtml Whether or not to generate HTML
//		* @param ParserOutput $output The output Object to fill (reference).
//		*/
//		protected function fillParserOutput( Title $title, $revId,
//			ParserOptions $options, $generateHtml, ParserOutput &$output
//		) {
//			global $wgParser, $wgTextModelsToParse;
//
//			if ( in_array( this.getModel(), $wgTextModelsToParse ) ) {
//				// parse just to get links etc into the database, HTML is replaced below.
//				$output = $wgParser.parse( this.getNativeData(), $title, $options, true, true, $revId );
//			}
//
//			if ( $generateHtml ) {
//				$html = this.getHtml();
//			} else {
//				$html = '';
//			}
//
//			$output.setText( $html );
//		}
//
//		/**
//		* Generates an HTML version of the content, for display. Used by
//		* fillParserOutput() to provide HTML for the ParserOutput Object.
//		*
//		* Subclasses may override this to provide a custom HTML rendering.
//		* If further information is to be derived from the content (such as
//		* categories), the fillParserOutput() method can be overridden instead.
//		*
//		* For backwards-compatibility, this default implementation just calls
//		* getHighlightHtml().
//		*
//		* @return String An HTML representation of the content
//		*/
//		protected function getHtml() {
//			return this.getHighlightHtml();
//		}
//
//		/**
//		* Generates an HTML version of the content, for display.
//		*
//		* This default implementation returns an HTML-escaped version
//		* of the raw text content.
//		*
//		* @note The functionality of this method should really be implemented
//		* in getHtml(), and subclasses should override getHtml() if needed.
//		* getHighlightHtml() is kept around for backward compatibility with
//		* extensions that already override it.
//		*
//		* @deprecated since 1.24. Use getHtml() instead. In particular, subclasses overriding
//		*     getHighlightHtml() should override getHtml() instead.
//		*
//		* @return String An HTML representation of the content
//		*/
//		protected function getHighlightHtml() {
//			return htmlspecialchars( this.getNativeData() );
//		}
//
//		/**
//		* This implementation provides lossless conversion between content models based
//		* on TextContent.
//		*
//		* @param String $toModel The desired content model, use the CONTENT_MODEL_XXX flags.
//		* @param String $lossy Flag, set to "lossy" to allow lossy conversion. If lossy conversion is not
//		*     allowed, full round-trip conversion is expected to work without losing information.
//		*
//		* @return Content|boolean A content Object with the content model $toModel, or false if that
//		*     conversion is not supported.
//		*
//		* @see Content::convert()
//		*/
//		public function convert( $toModel, $lossy = '' ) {
//			$converted = parent::convert( $toModel, $lossy );
//
//			if ( $converted !== false ) {
//				return $converted;
//			}
//
//			$toHandler = ContentHandler::getForModelID( $toModel );
//
//			if ( $toHandler instanceof TextContentHandler ) {
//				// NOTE: ignore content serialization format - it's just text anyway.
//				$text = this.getNativeData();
//				$converted = $toHandler.unserializeContent( $text );
//			}
//
//			return $converted;
//		}
}
