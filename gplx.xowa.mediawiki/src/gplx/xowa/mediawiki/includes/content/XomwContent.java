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
import gplx.xowa.mediawiki.includes.parsers.*;
/**
* A content Object represents page content, e.g. the text to show on a page.
* Content objects have no knowledge about how they relate to wiki pages.
*/

/**
* Base interface for content objects.
*
* @ingroup Content
*/
public interface XomwContent {

	/**
	* @since 1.21
	*
	* @return String A String representing the content in a way useful for
	*   building a full text search index. If no useful representation exists,
	*   this method returns an empty String.
	*
	* @todo Test that this actually works
	* @todo Make sure this also works with LuceneSearch / WikiSearch
	*/
	byte[] getTextForSearchIndex();

	/**
	* @since 1.21
	*
	* @return String|boolean The wikitext to include when another page includes this
	* content, or false if the content is not includable in a wikitext page.
	*
	* @todo Allow native handling, bypassing wikitext representation, like
	*  for includable special pages.
	* @todo Allow transclusion into other content models than Wikitext!
	* @todo Used in WikiPage and MessageCache to get message text. Not so
	*  nice. What should we use instead?!
	*/
	byte[] getWikitextForTransclusion();

	/**
	* Returns a textual representation of the content suitable for use in edit
	* summaries and log messages.
	*
	* @since 1.21
	*
	* @param int $maxLength Maximum length of the summary text.
	*
	* @return String The summary text.
	*/
	// DFLT:maxLength=250 
	byte[] getTextForSummary(int maxLength);

	/**
	* Returns native representation of the data. Interpretation depends on
	* the data model used, as given by getDataModel().
	*
	* @since 1.21
	*
	* @return mixed The native representation of the content. Could be a
	*    String, a nested array structure, an Object, a binary blob...
	*    anything, really.
	*
	* @note Caller must be aware of content model!
	*/
	Object getNativeData();

	/**
	* Returns the content's nominal size in "bogo-bytes".
	*
	* @return int
	*/
	int getSize();

	/**
	* Returns the ID of the content model used by this Content Object.
	* Corresponds to the CONTENT_MODEL_XXX constants.
	*
	* @since 1.21
	*
	* @return String The model id
	*/
	int getModel();

	/**
	* Convenience method that returns the ContentHandler singleton for handling
	* the content model that this Content Object uses.
	*
	* Shorthand for ContentHandler::getForContent( $this )
	*
	* @since 1.21
	*
	* @return ContentHandler
	*/
	XomwContentHandler getContentHandler();

	/**
	* Convenience method that returns the default serialization format for the
	* content model that this Content Object uses.
	*
	* Shorthand for $this->getContentHandler()->getDefaultFormat()
	*
	* @since 1.21
	*
	* @return String
	*/
	String getDefaultFormat();

	/**
	* Convenience method that returns the list of serialization formats
	* supported for the content model that this Content Object uses.
	*
	* Shorthand for $this->getContentHandler()->getSupportedFormats()
	*
	* @since 1.21
	*
	* @return String[] List of supported serialization formats
	*/
	String[] getSupportedFormats();

	/**
	* Returns true if $format is a supported serialization format for this
	* Content Object, false if it isn't.
	*
	* Note that this should always return true if $format is null, because null
	* stands for the default serialization.
	*
	* Shorthand for $this->getContentHandler()->isSupportedFormat( $format )
	*
	* @since 1.21
	*
	* @param String $format The serialization format to check.
	*
	* @return boolean Whether the format is supported
	*/
	boolean isSupportedFormat(String format);

	/**
	* Convenience method for serializing this Content Object.
	*
	* Shorthand for $this->getContentHandler()->serializeContent( $this, $format )
	*
	* @since 1.21
	*
	* @param String $format The desired serialization format, or null for the default format.
	*
	* @return String Serialized form of this Content Object.
	*/
	String serialize(String format);

	/**
	* Returns true if this Content Object represents empty content.
	*
	* @since 1.21
	*
	* @return boolean Whether this Content Object is empty
	*/
	boolean isEmpty();

	/**
	* Returns whether the content is valid. This is intended for local validity
	* checks, not considering global consistency.
	*
	* Content needs to be valid before it can be saved.
	*
	* This default implementation always returns true.
	*
	* @since 1.21
	*
	* @return boolean
	*/
	boolean isValid();

	/**
	* Returns true if this Content objects is conceptually equivalent to the
	* given Content Object.
	*
	* Contract:
	*
	* - Will return false if $that is null.
	* - Will return true if $that === $this.
	* - Will return false if $that->getModel() != $this->getModel().
	* - Will return false if $that->getNativeData() is not equal to $this->getNativeData(),
	*   where the meaning of "equal" depends on the actual data model.
	*
	* Implementations should be careful to make equals() transitive and reflexive:
	*
	* - $a->equals( $b ) <=> $b->equals( $a )
	* - $a->equals( $b ) &&  $b->equals( $c ) ==> $a->equals( $c )
	*
	* @since 1.21
	*
	* @param Content $that The Content Object to compare to.
	*
	* @return boolean True if this Content Object is equal to $that, false otherwise.
	*/
	boolean equals(XomwContent that);

	/**
	* Return a copy of this Content Object. The following must be true for the
	* Object returned:
	*
	* if $copy = $original->copy()
	*
	* - get_class($original) === get_class($copy)
	* - $original->getModel() === $copy->getModel()
	* - $original->equals( $copy )
	*
	* If and only if the Content Object is immutable, the copy() method can and
	* should return $this. That is, $copy === $original may be true, but only
	* for immutable content objects.
	*
	* @since 1.21
	*
	* @return Content A copy of this Object
	*/
	XomwContent copy();

	/**
	* Returns true if this content is countable as a "real" wiki page, provided
	* that it's also in a countable location (e.g. a current revision in the
	* main namespace).
	*
	* @since 1.21
	*
	* @param boolean|null $hasLinks If it is known whether this content contains
	*    links, provide this information here, to avoid redundant parsing to
	*    find out.
	*
	* @return boolean
	*/
	boolean isCountable(boolean hasLinks);

	/**
	* Parse the Content Object and generate a ParserOutput from the result.
	* $result->getText() can be used to obtain the generated HTML. If no HTML
	* is needed, $generateHtml can be set to false; in that case,
	* $result->getText() may return null.
	*
	* @note To control which options are used in the cache key for the
	*       generated parser output, implementations of this method
	*       may call ParserOutput::recordOption() on the output Object.
	*
	* @param Title $title The page title to use as a context for rendering.
	* @param int $revId Optional revision ID being rendered.
	* @param ParserOptions $options Any parser options.
	* @param boolean $generateHtml Whether to generate HTML (default: true). If false,
	*        the result of calling getText() on the ParserOutput Object returned by
	*        this method is undefined.
	*
	* @since 1.21
	*
	* @return ParserOutput
	*/
	// generateHtml = true
	XomwParserOutput getParserOutput(XomwTitle title, int revId,
		XomwParserOptions options, boolean generateHtml);

	// TODO: make RenderOutput and RenderOptions super classes

	/**
	* Returns a list of DataUpdate objects for recording information about this
	* Content in some secondary data store. If the optional second argument,
	* $old, is given, the updates may model only the changes that need to be
	* made to replace information about the old content with information about
	* the new content.
	*
	* This default implementation calls
	* $this->getParserOutput( $content, $title, null, null, false ),
	* and then calls getSecondaryDataUpdates( $title, $recursive ) on the
	* resulting ParserOutput Object.
	*
	* Subclasses may implement this to determine the necessary updates more
	* efficiently, or make use of information about the old content.
	*
	* @note Implementations should call the SecondaryDataUpdates hook, like
	*   AbstractContent does.
	*
	* @param Title $title The context for determining the necessary updates
	* @param Content $old An optional Content Object representing the
	*    previous content, i.e. the content being replaced by this Content
	*    Object.
	* @param boolean $recursive Whether to include recursive updates (default:
	*    false).
	* @param ParserOutput $parserOutput Optional ParserOutput Object.
	*    Provide if you have one handy, to avoid re-parsing of the content.
	*
	* @return DataUpdate[] A list of DataUpdate objects for putting information
	*    about this content Object somewhere.
	*
	* @since 1.21
	*/
	// DFLT: recursive = true
	Object getSecondaryDataUpdates(XomwTitle title, XomwContent old,
		boolean recursive, XomwParserOutput parserOutput);

	/**
	* Construct the redirect destination from this content and return an
	* array of Titles, or null if this content doesn't represent a redirect.
	* The last element in the array is the final destination after all redirects
	* have been resolved (up to $wgMaxRedirects times).
	*
	* @since 1.21
	*
	* @return Title[]|null List of Titles, with the destination last.
	*/
	XomwTitle[] getRedirectChain();

	/**
	* Construct the redirect destination from this content and return a Title,
	* or null if this content doesn't represent a redirect.
	* This will only return the immediate redirect target, useful for
	* the redirect table and other checks that don't need full recursion.
	*
	* @since 1.21
	*
	* @return Title|null The corresponding Title.
	*/
	XomwTitle getRedirectTarget();

	/**
	* Construct the redirect destination from this content and return the
	* Title, or null if this content doesn't represent a redirect.
	*
	* This will recurse down $wgMaxRedirects times or until a non-redirect
	* target is hit in order to provide (hopefully) the Title of the final
	* destination instead of another redirect.
	*
	* There is usually no need @Override to the default behavior, subclasses that
	* want to implement redirects @Override should getRedirectTarget().
	*
	* @since 1.21
	*
	* @return Title|null
	*/
	XomwTitle getUltimateRedirectTarget();

	/**
	* Returns whether this Content represents a redirect.
	* Shorthand for getRedirectTarget() !== null.
	*
	* @since 1.21
	*
	* @return boolean
	*/
	boolean isRedirect();

	/**
	* If this Content Object is a redirect, this method updates the redirect target.
	* Otherwise, it does nothing.
	*
	* @since 1.21
	*
	* @param Title $target The new redirect target
	*
	* @return Content A new Content Object with the updated redirect (or $this
	*   if this Content Object isn't a redirect)
	*/
	XomwContent updateRedirect(XomwTitle target);

	/**
	* Returns the section with the given ID.
	*
	* @since 1.21
	*
	* @param String|int $sectionId Section identifier as a number or String
	* (e.g. 0, 1 or 'T-1'). The ID "0" retrieves the section before the first heading, "1" the
	* text between the first heading (included) and the second heading (excluded), etc.
	*
	* @return Content|boolean|null The section, or false if no such section
	*    exist, or null if sections are not supported.
	*/
	XomwContent getSection(String sectionId);

	/**
	* Replaces a section of the content and returns a Content Object with the
	* section replaced.
	*
	* @since 1.21
	*
	* @param String|int|null|boolean $sectionId Section identifier as a number or String
	* (e.g. 0, 1 or 'T-1'), null/false or an empty String for the whole page
	* or 'new' for a new section.
	* @param Content $with New content of the section
	* @param String $sectionTitle New section's subject, only if $section is 'new'
	*
	* @return String|null Complete article text, or null if error
	*/
	byte[] replaceSection(String sectionId, XomwContent with, String sectionTitle);

	/**
	* Returns a Content Object with pre-save transformations applied (or this
	* Object if no transformations apply).
	*
	* @since 1.21
	*
	* @param Title $title
	* @param User $user
	* @param ParserOptions $parserOptions
	*
	* @return Content
	*/
	XomwContent preSaveTransform(XomwTitle title, Object user, XomwParserOptions parserOptions );

	/**
	* Returns a new WikitextContent Object with the given section heading
	* prepended, if supported. The default implementation just returns this
	* Content Object unmodified, ignoring the section header.
	*
	* @since 1.21
	*
	* @param String $header
	*
	* @return Content
	*/
	XomwContent addSectionHeader(byte[] header);

	/**
	* Returns a Content Object with preload transformations applied (or this
	* Object if no transformations apply).
	*
	* @since 1.21
	*
	* @param Title $title
	* @param ParserOptions $parserOptions
	* @param array $prms
	*
	* @return Content
	*/
	XomwContent preloadTransform(XomwTitle title, XomwParserOptions parserOptions, Object[] ary);

	/**
	* Prepare Content for saving. Called before Content is saved by WikiPage::doEditContent() and in
	* similar places.
	*
	* This may be used to check the content's consistency with global state. This function should
	* NOT write any information to the database.
	*
	* Note that this method will usually be called inside the same transaction
	* bracket that will be used to save the new revision.
	*
	* Note that this method is called before any update to the page table is
	* performed. This means that $page may not yet know a page ID.
	*
	* @since 1.21
	*
	* @param WikiPage $page The page to be saved.
	* @param int $flags Bitfield for use with EDIT_XXX constants, see WikiPage::doEditContent()
	* @param int $parentRevId The ID of the current revision
	* @param User $user
	*
	* @return Status A status Object indicating whether the content was
	*   successfully prepared for saving. If the returned status indicates
	*   an error, a rollback will be performed and the transaction aborted.
	*
	* @see WikiPage::doEditContent()
	*/
	Object prepareSave(Object page, int flags, int parentRevId, Object user);

	/**
	* Returns a list of updates to perform when this content is deleted.
	* The necessary updates may be taken from the Content Object, or depend on
	* the current state of the database.
	*
	* @since 1.21
	*
	* @param WikiPage $page The deleted page
	* @param ParserOutput $parserOutput Optional parser output Object
	*    for efficient access to meta-information about the content Object.
	*    Provide if you have one handy.
	*
	* @return DataUpdate[] A list of DataUpdate instances that will clean up the
	*    database after deletion.
	*/
	Object getDeletionUpdates(Object page,
		XomwParserOutput parserOutput);

	/**
	* Returns true if this Content Object matches the given magic word.
	*
	* @since 1.21
	*
	* @param MagicWord $word The magic word to match
	*
	* @return boolean Whether this Content Object matches the given magic word.
	*/
	boolean matchMagicWord(XomwMagicWord word);

	/**
	* Converts this content Object into another content Object with the given content model,
	* if that is possible.
	*
	* @param String $toModel The desired content model, use the CONTENT_MODEL_XXX flags.
	* @param String $lossy Optional flag, set to "lossy" to allow lossy conversion. If lossy
	* conversion is not allowed, full round-trip conversion is expected to work without losing
	* information.
	*
	* @return Content|boolean A content Object with the content model $toModel, or false if
	* that conversion is not supported.
	*/
	XomwContent convert(byte[] toModel, byte[] lossy);
	// @todo ImagePage and CategoryPage interfere with per-content action handlers
	// @todo nice&sane integration of GeSHi syntax highlighting
	//   [11:59] <vvv> Hooks are ugly; make CodeHighlighter interface and a
	//   config to set the class which handles syntax highlighting
	//   [12:00] <vvv> And default it to a DummyHighlighter

}
