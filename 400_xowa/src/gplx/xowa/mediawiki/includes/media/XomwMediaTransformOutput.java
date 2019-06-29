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
package gplx.xowa.mediawiki.includes.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.langs.htmls.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
import gplx.xowa.mediawiki.includes.filerepo.file.*;
public abstract class XomwMediaTransformOutput {
	public XomwMediaTransformOutput(XomwFile file, byte[] url, byte[] path, int width, int height) {
		this.file = file;
		this.url = url;
		this.width = width;
		this.height = height;
	}
//		/** @var array Associative array mapping optional supplementary image files
//		*  from pixel density (eg 1.5 or 2) to additional URLs.
//		*/
//		public $responsiveUrls = [];

	/** @var File */
	private final    XomwFile file;

	/** @var int Image width */
	protected final    int width;

	/** @var int Image height */
	protected final    int height;

	/** @var String URL path to the thumb */
	protected final    byte[] url;

//		/** @var boolean|String */
//		protected $page;
//
//		/** @var boolean|String Filesystem path to the thumb  */
//		protected $path;
//
//		/** @var boolean|String Language code, false if not set */
//		protected $lang;
//
//		/** @var boolean|String Permanent storage path  */
//		protected $storagePath = false;


	/**
	* @return int Width of the output box
	*/
	public int getWidth() {
		return this.width;
	}

	/**
	* @return int Height of the output box
	*/
	public int getHeight() {
		return this.height;
	}

//		/**
//		* @return File
//		*/
//		public function getFile() {
//			return $this->file;
//		}
//
//		/**
//		* Get the final extension of the thumbnail.
//		* Returns false for scripted transformations.
//		* @return String|boolean
//		*/
//		public function getExtension() {
//			return $this->path ? FileBackend::extensionFromPath( $this->path ) : false;
//		}
//
//		/**
//		* @return String|boolean The thumbnail URL
//		*/
//		public function getUrl() {
//			return $this->url;
//		}
//
//		/**
//		* @return String|boolean The permanent thumbnail storage path
//		*/
//		public function getStoragePath() {
//			return $this->storagePath;
//		}
//
//		/**
//		* @param String $storagePath The permanent storage path
//		* @return void
//		*/
//		public function setStoragePath( $storagePath ) {
//			$this->storagePath = $storagePath;
//			if ( $this->path === false ) {
//				$this->path = $storagePath;
//			}
//		}

	/**
	* Fetch HTML for this transform output
	*
	* @param array $options Associative array of options. Boolean options
	*     should be indicated with a value of true for true, and false or
	*     absent for false.
	*
	*     alt          Alternate text or caption
	*     desc-link    Boolean, show a description link
	*     file-link    Boolean, show a file download link
	*     custom-url-link    Custom URL to link to
	*     custom-title-link  Custom Title Object to link to
	*     valign       vertical-align property, if the output is an inline element
	*     img-class    Class applied to the "<img>" tag, if there is such a tag
	*
	* For images, desc-link and file-link are implemented as a click-through. For
	* sounds and videos, they may be displayed in other ways.
	*
	* @return String
	*/
	public abstract void toHtml(Bry_bfr bfr, Bry_bfr tmp, Xomw_params_mto options);

//		/**
//		* This will be overridden to return true in error classes
//		* @return boolean
//		*/
//		public function isError() {
//			return false;
//		}
//
//		/**
//		* Check if an output thumbnail file actually exists.
//		*
//		* This will return false if there was an error, the
//		* thumbnail is to be handled client-side only, or if
//		* transformation was deferred via TRANSFORM_LATER.
//		* This file may exist as a new file in /tmp, a file
//		* in permanent storage, or even refer to the original.
//		*
//		* @return boolean
//		*/
//		public function hasFile() {
//			// If TRANSFORM_LATER, $this->path will be false.
//			// Note: a null path means "use the source file".
//			return ( !$this->isError() && ( $this->path || $this->path === null ) );
//		}
//
//		/**
//		* Check if the output thumbnail is the same as the source.
//		* This can occur if the requested width was bigger than the source.
//		*
//		* @return boolean
//		*/
//		public function fileIsSource() {
//			return ( !$this->isError() && $this->path === null );
//		}
//
//		/**
//		* Get the path of a file system copy of the thumbnail.
//		* Callers should never write to this path.
//		*
//		* @return String|boolean Returns false if there isn't one
//		*/
//		public function getLocalCopyPath() {
//			if ( $this->isError() ) {
//				return false;
//			} elseif ( $this->path === null ) {
//				return $this->file->getLocalRefPath(); // assume thumb was not scaled
//			} elseif ( FileBackend::isStoragePath( $this->path ) ) {
//				$be = $this->file->getRepo()->getBackend();
//				// The temp file will be process cached by FileBackend
//				$fsFile = $be->getLocalReference( [ 'src' => $this->path ] );
//
//				return $fsFile ? $fsFile->getPath() : false;
//			} else {
//				return $this->path; // may return false
//			}
//		}
//
//		/**
//		* Stream the file if there were no errors
//		*
//		* @param array $headers Additional HTTP headers to send on success
//		* @return Status
//		* @since 1.27
//		*/
//		public function streamFileWithStatus( $headers = [] ) {
//			if ( !$this->path ) {
//				return Status::newFatal( 'backend-fail-stream', '<no path>' );
//			} elseif ( FileBackend::isStoragePath( $this->path ) ) {
//				$be = $this->file->getRepo()->getBackend();
//				return $be->streamFile( [ 'src' => $this->path, 'headers' => $headers ] );
//			} else { // FS-file
//				$success = StreamFile::stream( $this->getLocalCopyPath(), $headers );
//				return $success ? Status::newGood() : Status::newFatal( 'backend-fail-stream', $this->path );
//			}
//		}
//
//		/**
//		* Stream the file if there were no errors
//		*
//		* @deprecated since 1.26, use streamFileWithStatus
//		* @param array $headers Additional HTTP headers to send on success
//		* @return boolean Success
//		*/
//		public function streamFile( $headers = [] ) {
//			$this->streamFileWithStatus( $headers )->isOK();
//		}
//
//		/**
//		* Wrap some XHTML text in an anchor tag with the given attributes
//		*
//		* @param array $linkAttribs
//		* @param String $contents
//		* @return String
//		*/
//		protected function linkWrap( $linkAttribs, $contents ) {
//			if ( $linkAttribs ) {
//				return Xml::tags( 'a', $linkAttribs, $contents );
//			} else {
//				return $contents;
//			}
//		}

	/**
	* @param String $title
	* @param String|array $prms Query parameters to add
	* @return array
	*/
	public void getDescLinkAttribs(List_adp attribs, byte[] title, List_adp prms) {
		byte[] query = Bry_.Empty;
//			if ( is_array( prms ) ) {
//				$query = prms;
//			} else {
//				$query = [];
//			}
//			if ( $this->page && $this->page !== 1 ) {
//				$query['page'] = $this->page;
//			}
//			if ( $this->lang ) {
//				$query['lang'] = $this->lang;
//			}
//
//			if ( is_string( prms ) && prms !== '' ) {
//				$query = prms . '&' . wfArrayToCgi( $query );
//			}

		attribs.Clear();
		attribs.Add_many(Gfh_atr_.Bry__href, this.file.getTitle().getLocalURL(query));
		attribs.Add_many(Gfh_atr_.Bry__class, Bry__class__image);
		if (title != null) {
			attribs.Add_many(Gfh_atr_.Bry__title, title);
		}
	}

	// Wrap some XHTML text in an anchor tag with the given attributes
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	protected void Link_wrap(Bry_bfr bfr, List_adp link_attribs, byte[] contents) {
		if (link_attribs != null) {
			XomwXml.Tags(bfr, Gfh_tag_.Bry__a, link_attribs, contents);
		}
		else {
			bfr.Add(contents);
		}
	}
	private static final    byte[] Bry__class__image = Bry_.new_a7("image");
}
