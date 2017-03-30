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
import gplx.xowa.mediawiki.includes.exception.*;
import gplx.xowa.mediawiki.includes.parsers.*;
/**
* A content Object represents page content, e.g. the text to show on a page.
* Content objects have no knowledge about how they relate to Wiki pages.
*/

/**
* Base implementation for content objects.
*
* @ingroup Content
*/
public abstract class XomwAbstractContent implements XomwContent {
	/**
	* Name of the content model this Content Object represents.
	* Use with CONTENT_MODEL_XXX constants
	*
	* @since 1.21
	*
	* @var String $model_id
	*/
	private int model_id;

	/**
	* @param String $modelId
	*
	* @since 1.21
	*/
	public XomwAbstractContent(int modelId) {
		this.model_id = modelId;
	}

	/**
	* @since 1.21
	*
	* @see Content::getModel
	*/
	public int getModel() {
		return this.model_id;
	}

	/**
	* @since 1.21
	*
	* @param String $modelId The model to check
	*
	* @throws MWException If the provided ID is not the ID of the content model supported by this
	* Content Object.
	*/
	protected void checkModelID(int modelId) {
		if (modelId != this.model_id) {
			throw new XomwMWException(
				"Bad content model: " +
				"expected " + this.model_id +
				"but got " + modelId
			);
		}
	}

	/**
	* @since 1.21
	*
	* @see Content::getContentHandler
	*/
	public XomwContentHandler getContentHandler() {
		return XomwContentHandler.getForContent(this);
	}

	/**
	* @since 1.21
	*
	* @see Content::getDefaultFormat
	*/
	public String getDefaultFormat() {
		return this.getContentHandler().getDefaultFormat();
	}

	/**
	* @since 1.21
	*
	* @see Content::getSupportedFormats
	*/
	public String[] getSupportedFormats() {
		return this.getContentHandler().getSupportedFormats();
	}

	/**
	* @since 1.21
	*
	* @param String $format
	*
	* @return boolean
	*
	* @see Content::isSupportedFormat
	*/
	public boolean isSupportedFormat(String format) {
		if (format == null) {
			return true; // this means "use the default"
		}

		return this.getContentHandler().isSupportedFormat(format);
	}

	/**
	* @since 1.21
	*
	* @param String $format The serialization format to check.
	*
	* @throws MWException If the format is not supported by this content handler.
	*/
	public void checkFormat(String format) {
		if (!this.isSupportedFormat(format)) {
			throw new XomwMWException(
				"Format " + format + " is not supported for content model " +
				this.getModel()
			);
		}
	}

	/**
	* @since 1.21
	*
	* @param String $format
	*
	* @return String
	*
	* @see Content::serialize
	*/
	public String serialize(String format) {
		// return this.getContentHandler().serializeContent(this, format);
		throw Err_.new_unimplemented();
	}

	/**
	* @since 1.21
	*
	* @return boolean
	*
	* @see Content::isEmpty
	*/
	public boolean isEmpty() {
		return this.getSize() == 0;
	}

	/**
	* Subclasses @Override may this to implement (light weight) validation.
	*
	* @since 1.21
	*
	* @return boolean Always true.
	*
	* @see Content::isValid
	*/
	@gplx.Virtual public boolean isValid() {
		return true;
	}

	/**
	* @since 1.21
	*
	* @param Content that
	*
	* @return boolean
	*
	* @see Content::equals
	*/
	public boolean equals(XomwContent that) {
		if (that == null) {
			return false;
		}

		if (that == this) {
			return true;
		}

		if (that.getModel() != this.getModel()) {
			return false;
		}

		return this.getNativeData() == that.getNativeData();
	}

	/**
	* Returns a list of DataUpdate objects for recording information about this
	* Content in some secondary data store.
	*
	* This default implementation returns a LinksUpdate Object and calls the
	* SecondaryDataUpdates hook.
	*
	* Subclasses @Override may this to determine the secondary data updates more
	* efficiently, preferably without the need to generate a parser output Object.
	* They should however make sure to call SecondaryDataUpdates to give extensions
	* a chance to inject additional updates.
	*
	* @since 1.21
	*
	* @param Title $title
	* @param Content $old
	* @param boolean $recursive
	* @param ParserOutput parserOutput
	*
	* @return DataUpdate[]
	*
	* @see Content::getSecondaryDataUpdates()
	*/
	// recursive=true
//		public XomwDataUpdate[] getSecondaryDataUpdates(Title title, Content old,
//			boolean recursive, ParserOutput parserOutput
//		) {
//			if (parserOutput == null) {
//				parserOutput = this.getParserOutput(title, null, null, false);
//			}
//
//			XomwDataUpdate[] updates = new XomwDataUpdate[] {
//				new LinksUpdate(title, parserOutput, recursive)
//															};
//
//			Hooks::run('SecondaryDataUpdates', [ $title, $old, $recursive, parserOutput, &$updates ]);
//
//			return updates;
//		}

	/**
	* @since 1.21
	*
	* @return Title[]|null
	*
	* @see Content::getRedirectChain
	*/
	public XomwTitle[] getRedirectChain() {
//			XomwTitle title = this.getRedirectTarget();
//			if (title == null) {
//				return null;
//			}
//			// recursive check to follow double redirects
//			int recurse = XomwDefaultSettings.wgMaxRedirects;
//
//			List_adp titles = List_adp_.New_by_many(title);
//			while (--recurse > 0) {
//				XomwTitle newtitle = null;
//				if (title.isRedirect()) {
//					$page = WikiPage::factory(title);
//					$newtitle = $page.getRedirectTarget();
//				} else {
//					break;
//				}
//				// Redirects to some special pages are not permitted
//				if (Type_adp_.Eq_typeSafe(newtitle, typeof(XomwTitle)) && newtitle.isValidRedirectTarget()) {
//					// The new title passes the checks, so make that our current
//					// title so that further recursion can be checked
//					title = newtitle;
//					titles.Add(newtitle);
//				} else {
//					break;
//				}
//			}
//
//			return (XomwTitle[])titles.To_ary_and_clear(typeof(XomwTitle));
		throw Err_.new_unimplemented();
	}

//		/**
//		* Subclasses that implement redirects should override this.
//		*
//		* @since 1.21
//		*
//		* @return Title|null
//		*
//		* @see Content::getRedirectTarget
//		*/
//		public function getRedirectTarget() {
//			return null;
//		}
//
//		/**
//		* @note Migrated here from Title::newFromRedirectRecurse.
//		*
//		* @since 1.21
//		*
//		* @return Title|null
//		*
//		* @see Content::getUltimateRedirectTarget
//		*/
//		public function getUltimateRedirectTarget() {
//			$titles = this.getRedirectChain();
//
//			return $titles ? array_pop($titles) : null;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return boolean
//		*
//		* @see Content::isRedirect
//		*/
//		public function isRedirect() {
//			return this.getRedirectTarget() != null;
//		}
//
//		/**
//		* This default implementation always returns $this.
//		* Subclasses that implement redirects should override this.
//		*
//		* @since 1.21
//		*
//		* @param Title $target
//		*
//		* @return Content $this
//		*
//		* @see Content::updateRedirect
//		*/
//		public function updateRedirect(Title $target) {
//			return $this;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return null
//		*
//		* @see Content::getSection
//		*/
//		public function getSection($sectionId) {
//			return null;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return null
//		*
//		* @see Content::replaceSection
//		*/
//		public function replaceSection($sectionId, Content $with, $sectionTitle = '') {
//			return null;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return Content $this
//		*
//		* @see Content::preSaveTransform
//		*/
//		public function preSaveTransform(Title $title, User $user, ParserOptions $popts) {
//			return $this;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return Content $this
//		*
//		* @see Content::addSectionHeader
//		*/
//		public function addSectionHeader($header) {
//			return $this;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return Content $this
//		*
//		* @see Content::preloadTransform
//		*/
//		public function preloadTransform(Title $title, ParserOptions $popts, $params = []) {
//			return $this;
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @return Status
//		*
//		* @see Content::prepareSave
//		*/
//		public function prepareSave(WikiPage $page, $flags, $parentRevId, User $user) {
//			if (this.isValid()) {
//				return Status::newGood();
//			} else {
//				return Status::newFatal("invalid-content-data");
//			}
//		}
//
//		/**
//		* @since 1.21
//		*
//		* @param WikiPage $page
//		* @param ParserOutput parserOutput
//		*
//		* @return LinksDeletionUpdate[]
//		*
//		* @see Content::getDeletionUpdates
//		*/
//		public function getDeletionUpdates(WikiPage $page, ParserOutput parserOutput = null) {
//			return [
//				new LinksDeletionUpdate($page),
//			];
//		}

	/**
	* This default implementation always returns false. Subclasses @Override may 
	* this to supply matching logic.
	*
	* @since 1.21
	*
	* @param MagicWord $word
	*
	* @return boolean Always false.
	*
	* @see Content::matchMagicWord
	*/
	@gplx.Virtual public boolean matchMagicWord(XomwMagicWord word) {
		return false;
	}

//		/**
//		* This super implementation calls the hook ConvertContent to enable custom conversions.
//		* Subclasses may override this to implement conversion for "their" content model.
//		*
//		* @param String $toModel
//		* @param String $lossy
//		*
//		* @return Content|boolean
//		*
//		* @see Content::convert()
//		*/
//		public function convert($toModel, $lossy = '') {
//			if (this.getModel() == $toModel) {
//				// nothing to do, shorten out.
//				return $this;
//			}
//
//			$lossy = ($lossy == 'lossy'); // String flag, convert to boolean for convenience
//			$result = false;
//
//			Hooks::run('ConvertContent', [ $this, $toModel, $lossy, &$result ]);
//
//			return $result;
//		}
//
//		/**
//		* Returns a ParserOutput Object containing information derived from this content.
//		* Most importantly, unless $generateHtml was false, the return value contains an
//		* HTML representation of the content.
//		*
//		* Subclasses that want to control the parser output may override this, but it is
//		* preferred to override fillParserOutput() instead.
//		*
//		* Subclasses that override getParserOutput() itself should take care to call the
//		* ContentGetParserOutput hook.
//		*
//		* @since 1.24
//		*
//		* @param Title $title Context title for parsing
//		* @param int|null $revId Revision ID (for {{REVISIONID}})
//		* @param ParserOptions|null $options Parser options
//		* @param boolean $generateHtml Whether or not to generate HTML
//		*
//		* @return ParserOutput Containing information derived from this content.
//		*/
//		public function getParserOutput(Title $title, $revId = null,
//			ParserOptions $options = null, $generateHtml = true
//		) {
//			if ($options == null) {
//				$options = this.getContentHandler().makeParserOptions('canonical');
//			}
//
//			$po = new ParserOutput();
//
//			if (Hooks::run('ContentGetParserOutput',
//				[ $this, $title, $revId, $options, $generateHtml, &$po ])) {
//
//				// Save and restore the old value, just in case something is reusing
//				// the ParserOptions Object in some weird way.
//				$oldRedir = $options.getRedirectTarget();
//				$options.setRedirectTarget(this.getRedirectTarget());
//				this.fillParserOutput($title, $revId, $options, $generateHtml, $po);
//				$options.setRedirectTarget($oldRedir);
//			}
//
//			Hooks::run('ContentAlterParserOutput', [ $this, $title, $po ]);
//
//			return $po;
//		}
//
//		/**
//		* Fills the provided ParserOutput with information derived from the content.
//		* Unless $generateHtml was false, this includes an HTML representation of the content.
//		*
//		* This is called by getParserOutput() after consulting the ContentGetParserOutput hook.
//		* Subclasses are expected to override this method (or getParserOutput(), if need be).
//		* Subclasses of TextContent should generally override getHtml() instead.
//		*
//		* This placeholder implementation always throws an exception.
//		*
//		* @since 1.24
//		*
//		* @param Title $title Context title for parsing
//		* @param int|null $revId Revision ID (for {{REVISIONID}})
//		* @param ParserOptions $options Parser options
//		* @param boolean $generateHtml Whether or not to generate HTML
//		* @param ParserOutput &$output The output Object to fill (reference).
//		*
//		* @throws MWException
//		*/
//		protected function fillParserOutput(Title $title, $revId,
//			ParserOptions $options, $generateHtml, ParserOutput &$output
//		) {
//			// Don't make abstract, so subclasses that override getParserOutput() directly don't fail.
//			throw new MWException('Subclasses of AbstractContent must override fillParserOutput!');
//		}
	public abstract byte[] getTextForSearchIndex();

	public abstract byte[] getWikitextForTransclusion();

	public abstract byte[] getTextForSummary(int maxLength);

	public abstract Object getNativeData();

	public abstract int getSize();

	public abstract XomwContent copy();

	public abstract boolean isCountable(boolean hasLinks);

	public abstract XomwParserOutput getParserOutput(XomwTitle title, int revId,
		XomwParserOptions options, boolean generateHtml);

	public abstract Object getSecondaryDataUpdates(XomwTitle title, XomwContent old,
		boolean recursive, XomwParserOutput parserOutput);

	public abstract XomwTitle getRedirectTarget();

	public abstract XomwTitle getUltimateRedirectTarget();

	public abstract boolean isRedirect();

	public abstract XomwContent updateRedirect(XomwTitle target);

	public abstract XomwContent getSection(String sectionId);

	public abstract byte[] replaceSection(String sectionId, XomwContent with, String sectionTitle);

	public abstract XomwContent preSaveTransform(XomwTitle title, Object user, XomwParserOptions parserOptions);

	public abstract XomwContent addSectionHeader(byte[] header);

	public abstract XomwContent preloadTransform(XomwTitle title, XomwParserOptions parserOptions, Object[] ary);

	public abstract Object prepareSave(Object page, int flags, int parentRevId, Object user);

	public abstract Object getDeletionUpdates(Object page,
		XomwParserOutput parserOutput);

	public abstract XomwContent convert(byte[] toModel, byte[] lossy);
}
