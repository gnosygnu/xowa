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
package gplx.xowa.mediawiki.includes.interwiki; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
/**
* Value Object for representing interwiki records.
*/
public class XomwInterwiki {

	/** @var String The interwiki prefix, (e.g. "Meatball", or the language prefix "de") */
	private byte[] mPrefix;

	/** @var String The URL of the wiki, with "1" as a placeholder for an article name. */
	private byte[] mURL;

	/** @var String The URL of the file api.php  */
	private byte[] mAPI;

	/** @var String The name of the database (for a connection to be established
	*    with wfGetLB('wikiid'))
	*/
	private byte[] mWikiID;

	/** @var boolean Whether the wiki is in this project */
	private boolean mLocal;

	/** @var boolean Whether interwiki transclusions are allowed */
	private boolean mTrans;

	public byte[] interwikiId;

	public XomwInterwiki(byte[] prefix, byte[] url, byte[] api, byte[] wikiId, boolean local, boolean trans) {
		this.mPrefix = prefix;
		this.mURL = url;
		this.mAPI = api;
		this.mWikiID = wikiId;
		this.mLocal = local;
		this.mTrans = trans;
	}

	/**
	* Check whether an interwiki prefix exists
	*
	* [@]deprecated since 1.28, use InterwikiLookup instead
	*
	* @param String prefix Interwiki prefix to use
	* @return boolean Whether it exists
	*/
	public static boolean isValidInterwiki(XomwMediaWikiServices mws, byte[] prefix) {
		return mws.getInterwikiLookup().isValidInterwiki(prefix);
//			return MediaWikiServices::getInstance().getInterwikiLookup().isValidInterwiki(prefix);
	}

//		/**
//		* Fetch an Interwiki Object
//		*
//		* @deprecated since 1.28, use InterwikiLookup instead
//		*
//		* @param String prefix Interwiki prefix to use
//		* @return Interwiki|null|boolean
//		*/
//		public static function fetch(prefix) {
//			return MediaWikiServices::getInstance().getInterwikiLookup().fetch(prefix);
//		}
//
//		/**
//		* Purge the cache (local and persistent) for an interwiki prefix.
//		*
//		* @param String prefix
//		* @since 1.26
//		*/
//		public static function invalidateCache(prefix) {
//			return MediaWikiServices::getInstance().getInterwikiLookup().invalidateCache(prefix);
//		}
//
//		/**
//		* Returns all interwiki prefixes
//		*
//		* @deprecated since 1.28, unused. Use InterwikiLookup instead.
//		*
//		* @param String|null local If set, limits output to local/non-local interwikis
//		* @return array List of prefixes
//		* @since 1.19
//		*/
//		public static function getAllPrefixes(local = null) {
//			return MediaWikiServices::getInstance().getInterwikiLookup().getAllPrefixes(local);
//		}

	/**
	* Get the URL for a particular title (or with 1 if no title given)
	*
	* @param String title What text to put for the article name
	* @return String The URL
	* @note Prior to 1.19 The getURL with an argument was broken.
	*       If you if you use this arg in an extension that supports MW earlier
	*       than 1.19 please wfUrlencode and substitute 1 on your own.
	*/
	// title=null
	public byte[] getURL(byte[] title) {
		byte[] url = this.mURL;
		if (title != null) {
			url = XophpString.str_replace(ARG_1, XomwGlobalFunctions.wfUrlencode(title), url);
		}

		return url;
	}

	/**
	* Get the API URL for this wiki
	*
	* @return String The URL
	*/
	public byte[] getAPI() {
		return this.mAPI;
	}

	/**
	* Get the DB name for this wiki
	*
	* @return String The DB name
	*/
	public byte[] getWikiID() {
		return this.mWikiID;
	}

	/**
	* Is this a local link from a sister project, or is
	* it something outside, like Google
	*
	* @return boolean
	*/
	public boolean isLocal() {
		return this.mLocal;
	}

	/**
	* Can pages from this wiki be transcluded?
	* Still requires wgEnableScaryTransclusion
	*
	* @return boolean
	*/
	public boolean isTranscludable() {
		return this.mTrans;
	}

	/**
	* Get the name for the interwiki site
	*
	* @return String
	*/
	public byte[] getName(XomwEnv env) {
//			XomwMessage msg = XomwGlobalFunctions.wfMessage(env, "interwiki-name-" + this.mPrefix).inContentLanguage();
//
//			return !msg.exists() ? Bry_.Empty : msg.text();
            Tfds.Write(mPrefix);
		return null;
	}

//		/**
//		* Get a description for this interwiki
//		*
//		* @return String
//		*/
//		public function getDescription() {
//			msg = wfMessage('interwiki-desc-' . this.mPrefix).inContentLanguage();
//
//			return !msg.exists() ? '' : msg.text();
//		}
	private static final    byte[] ARG_1 = Bry_.new_a7("$1");
}
