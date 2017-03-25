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
package gplx.xowa.mediawiki.includes.site; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
/**
* Class representing a MediaWiki site.
*
* @since 1.21
*
* @ingroup Site
*/
public class XomwMediaWikiSite extends XomwSite {	private static final String PATH_FILE = "file_path";
	private static final String PATH_PAGE = "page_path";

	/**
	* Constructor.
	*
	* @since 1.21
	*
	* @param String $type
	*/
	public XomwMediaWikiSite() {super(XomwSite.TYPE_MEDIAWIKI);
	}

	/**
	* Returns the database form of the given title.
	*
	* @since 1.21
	*
	* @param String $title The target page's title, in normalized form.
	*
	* @return String
	*/
	public byte[] toDBKey(byte[] title) {
		return XophpString.str_replace(Byte_ascii.Space_bry, Byte_ascii.Underline_bry, title);
	}

	/**
	* Returns the normalized form of the given page title, using the
	* normalization rules of the given site. If the given title is a redirect,
	* the redirect weill be resolved and the redirect target is returned.
	*
	* @note This actually makes an API request to the remote site, so beware
	*   that this function is slow and depends on an external service.
	*
	* @note If MW_PHPUNIT_TEST is defined, the call to the external site is
	*   skipped, and the title is normalized using the local normalization
	*   rules as implemented by the Title class.
	*
	* @see Site::normalizePageName
	*
	* @since 1.21
	*
	* @param String $pageName
	*
	* @return String
	* @throws MWException
	*/
//		public function normalizePageName($pageName) {
//			if (defined('MW_PHPUNIT_TEST')) {
//				// If the code is under test, don't call out to other sites, just
//				// normalize locally.
//				// Note: this may cause results to be inconsistent with the actual
//				// normalization used by the respective remote site!
//
//				$t = Title::newFromText($pageName);
//				return $t->getPrefixedText();
//			} else {
//				static $mediaWikiPageNameNormalizer = null;
//
//				if ($mediaWikiPageNameNormalizer === null) {
//					$mediaWikiPageNameNormalizer = new MediaWikiPageNameNormalizer();
//				}
//
//				return $mediaWikiPageNameNormalizer->normalizePageName(
//					$pageName,
//					this.getFileUrl('api.php')
//				);
//			}
//		}

	/**
	* @see Site::getLinkPathType
	* Returns Site::PATH_PAGE
	*
	* @since 1.21
	*
	* @return String
	*/
	@Override public String getLinkPathType() {
		return XomwMediaWikiSite.PATH_PAGE;
	}

	/**
	* Returns the relative page path.
	*
	* @since 1.21
	*
	* @return String
	*/
	public String getRelativePagePath() {
		return XophpUrl.parse_url(this.getPath(XomwMediaWikiSite.PATH_PAGE), XophpUrl.PHP_URL_PATH);
	}

	/**
	* Returns the relative file path.
	*
	* @since 1.21
	*
	* @return String
	*/
	public String getRelativeFilePath() {
		return XophpUrl.parse_url(this.getPath(XomwMediaWikiSite.PATH_FILE), XophpUrl.PHP_URL_PATH);
	}

	/**
	* Sets the relative page path.
	*
	* @since 1.21
	*
	* @param String $path
	*/
	public void setPagePath(String path) {
		this.setPath(XomwMediaWikiSite.PATH_PAGE, path);
	}

	/**
	* Sets the relative file path.
	*
	* @since 1.21
	*
	* @param String $path
	*/
	public void setFilePath(String path) {
		this.setPath(XomwMediaWikiSite.PATH_FILE, path);
	}

	/**
	* @see Site::getPageUrl
	*
	* This implementation returns a URL constructed using the path returned by getLinkPath().
	* In addition to the default behavior implemented by Site::getPageUrl(), this
	* method converts the $pageName to DBKey-format by replacing spaces with underscores
	* before using it in the URL.
	*
	* @since 1.21
	*
	* @param String|boolean $pageName Page name or false (default: false)
	*
	* @return String
	*/
	@Override public String getPageUrl(String pageName) {
		String url = this.getLinkPath();

		if (url == null) {
			return null;
		}

//			if (pageName != null) {
//				pageName = this.toDBKey(trim(pageName));
//				url = str_replace("$1", XomwGlobalFunctions.wfUrlencode(pageName), url);
//			}

		return url;
	}

	/**
	* Returns the full file path (ie site url + relative file path).
	* The path should go at the $1 marker. If the $path
	* argument is provided, the marker will be replaced by it's value.
	*
	* @since 1.21
	*
	* @param String|boolean $path
	*
	* @return String
	*/
	public String getFileUrl() {return getFileUrl(null);}
	public String getFileUrl(String path) {
		String filePath = this.getPath(XomwMediaWikiSite.PATH_FILE);

//			if (filePath != null) {
//				filePath = str_replace("$1", path, filePath);
//			}

		return filePath;
	}
}
