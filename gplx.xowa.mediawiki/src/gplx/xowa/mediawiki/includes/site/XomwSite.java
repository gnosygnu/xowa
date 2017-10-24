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
import gplx.xowa.mediawiki.includes.exception.*;
/**
* Represents a single site.
*/
public class XomwSite {
	private static final String TYPE_UNKNOWN = "unknown";
	protected static final String TYPE_MEDIAWIKI = "mediawiki";

	private static final String GROUP_NONE = "none";

	private static final String ID_INTERWIKI = "interwiki";
	private static final String ID_EQUIVALENT = "equivalent";

	private static final String SOURCE_LOCAL = "local";

	private static final String PATH_LINK = "link";

	//	/**
	//	* A version ID that identifies the serialization structure used by getSerializationData()
	//	* and unserialize(). This is useful for constructing cache keys in cases where the cache relies
	//	* on serialization for storing the SiteList.
	//	*
	//	* @var String A String uniquely identifying the version of the serialization structure.
	//	*/
	//	static final SERIAL_VERSION_ID = '2013-01-23';

	/**
	* @since 1.21
	*
	* @var String|null
	*/
	private String globalId = null;

	/**
	* @since 1.21
	*
	* @var String
	*/
	private String type = XomwSite.TYPE_UNKNOWN;

	/**
	* @since 1.21
	*
	* @var String
	*/
	private String group = XomwSite.GROUP_NONE;

	/**
	* @since 1.21
	*
	* @var String
	*/
	private String source = XomwSite.SOURCE_LOCAL;

	/**
	* @since 1.21
	*
	* @var String|null
	*/
	private byte[] languageCode = null;

	/**
	* Holds the local ids for this site.
	* local id type => [ ids for this type (strings) ]
	*
	* @since 1.21
	*
	* @var array[]
	*/
	private Ordered_hash localIds;

	/**
	* @since 1.21
	*
	* @var array
	*/
	private Hash_adp extraData = Hash_adp_.New();

	/**
	* @since 1.21
	*
	* @var array
	*/
	private Hash_adp extraConfig = Hash_adp_.New();

	/**
	* @since 1.21
	*
	* @var boolean
	*/
	private boolean forward = false;

	/**
	* @since 1.21
	*
	* @var int|null
	*/
	private int internalId;

	/**
	* Constructor.
	*
	* @since 1.21
	*
	* @param String type
	*/
	public XomwSite(String type) {
		this.type = type;
	}

	/**
	* Returns the global site identifier (ie enwiktionary).
	*
	* @since 1.21
	*
	* @return String|null
	*/
	public String getGlobalId() {
		return this.globalId;
	}

	/**
	* Sets the global site identifier (ie enwiktionary).
	*
	* @since 1.21
	*
	* @param String|null globalId
	*
	* @throws MWException
	*/
	public void setGlobalId(String globalId) {
		// if (globalId != null && !is_string(globalId)) {
		//	throw new MWException('globalId needs to be String or null');
		// }

		this.globalId = globalId;
	}

	/**
	* Returns the type of the site (ie mediawiki).
	*
	* @since 1.21
	*
	* @return String
	*/
	public String getType() {
		return this.type;
	}

	/**
	* Gets the group of the site (ie wikipedia).
	*
	* @since 1.21
	*
	* @return String
	*/
	public String getGroup() {
		return this.group;
	}

	/**
	* Sets the group of the site (ie wikipedia).
	*
	* @since 1.21
	*
	* @param String group
	*
	* @throws MWException
	*/
	public void setGroup(String group) {
		// if (!is_string(group)) {
		//  	throw new MWException('group needs to be a String');
		// }

		this.group = group;
	}

	/**
	* Returns the source of the site data (ie 'local', 'wikidata', 'my-magical-repo').
	*
	* @since 1.21
	*
	* @return String
	*/
	public String getSource() {
		return this.source;
	}

	/**
	* Sets the source of the site data (ie 'local', 'wikidata', 'my-magical-repo').
	*
	* @since 1.21
	*
	* @param String source
	*
	* @throws MWException
	*/
	public void setSource(String source) {
		// if (!is_string(source)) {
		//  	throw new MWException('source needs to be a String');
		// }

		this.source = source;
	}

	/**
	* Gets if site.tld/path/key:pageTitle should forward users to  the page on
	* the actual site, where "key" is the local identifier.
	*
	* @since 1.21
	*
	* @return boolean
	*/
	public boolean shouldForward() {
		return this.forward;
	}

	/**
	* Sets if site.tld/path/key:pageTitle should forward users to  the page on
	* the actual site, where "key" is the local identifier.
	*
	* @since 1.21
	*
	* @param boolean $shouldForward
	*
	* @throws MWException
	*/
	public void setForward(boolean shouldForward) {
		// if (!is_bool($shouldForward)) {
		//	throw new MWException('$shouldForward needs to be a boolean');
		// }

		this.forward = shouldForward;
	}

	/**
	* Returns the domain of the site, ie en.wikipedia.org
	* Or false if it's not known.
	*
	* @since 1.21
	*
	* @return String|null
	*/
	public String getDomain() {
		String path = this.getLinkPath();

		if (path == null) {
			return null;
		}

		return XophpUrl.parse_url(path, XophpUrl.PHP_URL_HOST);
	}


	/**
	* Returns the protocol of the site.
	*
	* @since 1.21
	*
	* @throws MWException
	* @return String
	*/
	public String getProtocol() {
		String path = this.getLinkPath();

		if (path == null) {
			return "";
		}

		String protocol = XophpUrl.parse_url(path, XophpUrl.PHP_URL_SCHEME);

		// Malformed URL
		if (protocol == null) {
			throw new XomwMWException(String_.Format("failed to parse URL '{0}'", path));
		}

		// No schema
		if (protocol == "") {
			// Used for protocol relative URLs
			protocol = "";
		}

		return protocol;
	}

	/**
	* Sets the path used to construct links with.
	* Shall be equivalent to setPath(getLinkPathType(), $fullUrl).
	*
	* @param String $fullUrl
	*
	* @since 1.21
	*
	* @throws MWException
	*/
	public void setLinkPath(String fullUrl) {
		String type = this.getLinkPathType();

		if (type == null) {
			throw new XomwMWException("This Site does not support link paths.");
		}

		this.setPath(type, fullUrl);
	}

	/**
	* Returns the path used to construct links with or false if there is no such path.
	*
	* Shall be equivalent to getPath(getLinkPathType()).
	*
	* @return String|null
	*/
	public String getLinkPath() {
		String type = this.getLinkPathType();
		return type == null ? null: this.getPath(type);
	}

	/**
	* Returns the main path type, that is the type of the path that should
	* generally be used to construct links to the target site.
	*
	* This default implementation returns Site::PATH_LINK as the default path
	* type. Subclasses @Override can this to define a different default path
	* type, or return false to disable site links.
	*
	* @since 1.21
	*
	* @return String|null
	*/
	@gplx.Virtual public String getLinkPathType() {
		return XomwSite.PATH_LINK;
	}

	/**
	* Returns the full URL for the given page on the site.
	* Or false if the needed information is not known.
	*
	* This generated URL is usually based upon the path returned by getLinkPath(),
	* but this is not a requirement.
	*
	* This implementation returns a URL constructed using the path returned by getLinkPath().
	*
	* @since 1.21
	*
	* @param boolean|String $pageName
	*
	* @return String|boolean
	*/
	@gplx.Virtual public String getPageUrl() {return getPageUrl(null);}
	@gplx.Virtual public String getPageUrl(String pageName) {
		String url = this.getLinkPath();

		if (url == null) {
			return null;
		}

		if (pageName != null) {
			url = String_.new_u8(XophpString.str_replace(Bry_.new_a7("$1"), XophpEncode.rawurlencode(Bry_.new_u8(pageName)), Bry_.new_u8(url)));
		}

		return url;
	}

	/**
	* Returns $pageName without changes.
	* Subclasses @Override may this to apply some kind of normalization.
	*
	* @see Site::normalizePageName
	*
	* @since 1.21
	*
	* @param String $pageName
	*
	* @return String
	*/
	public String normalizePageName(String pageName) {
		return pageName;
	}

	/**
	* Returns the type specific fields.
	*
	* @since 1.21
	*
	* @return array
	*/
	public Hash_adp getExtraData() {
		return this.extraData;
	}

	/**
	* Sets the type specific fields.
	*
	* @since 1.21
	*
	* @param array $extraData
	*/
	public void setExtraData(Hash_adp extraData) {
		this.extraData = extraData;
	}

	/**
	* Returns the type specific config.
	*
	* @since 1.21
	*
	* @return array
	*/
	public Hash_adp getExtraConfig() {
		return this.extraConfig;
	}

	/**
	* Sets the type specific config.
	*
	* @since 1.21
	*
	* @param array $extraConfig
	*/
	public void setExtraConfig(Hash_adp extraConfig) {
		this.extraConfig = extraConfig;
	}

	/**
	* Returns language code of the sites primary language.
	* Or null if it's not known.
	*
	* @since 1.21
	*
	* @return String|null
	*/
	public byte[] getLanguageCode() {
		return this.languageCode;
	}

	/**
	* Sets language code of the sites primary language.
	*
	* @since 1.21
	*
	* @param String languageCode
	*/
	public void setLanguageCode(byte[] languageCode) {
		this.languageCode = languageCode;
	}

	/**
	* Returns the set @gplx.Internal protected identifier for the site.
	*
	* @since 1.21
	*
	* @return String|null
	*/
	public int getInternalId() {
		return this.internalId;
	}

	/**
	* Sets the @gplx.Internal protected identifier for the site.
	* This typically is a primary key in a db table.
	*
	* @since 1.21
	*
	* @param int|null internalId
	*/
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}

	/**
	* Adds a local identifier.
	*
	* @since 1.21
	*
	* @param String type
	* @param String $identifier
	*/
	public void addLocalId(String type, String identifier) {
		if (this.localIds == null) {
			this.localIds = Ordered_hash_.New();
		}

		Ordered_hash typeHash = (Ordered_hash)this.localIds.Get_by(type);
		if (typeHash == null) {
			typeHash = Ordered_hash_.New();
			this.localIds.Add(type, typeHash);
		}

		if (typeHash.Get_by(identifier) == null) {
			typeHash.Add_as_key_and_val(identifier);
		}
	}

	/**
	* Adds an interwiki id to the site.
	*
	* @since 1.21
	*
	* @param String $identifier
	*/
	public void addInterwikiId(String identifier) {
		this.addLocalId(XomwSite.ID_INTERWIKI, identifier);
	}

	/**
	* Adds a navigation id to the site.
	*
	* @since 1.21
	*
	* @param String $identifier
	*/
	public void addNavigationId(String identifier) {
		this.addLocalId(XomwSite.ID_EQUIVALENT, identifier);
	}

	/**
	* Returns the interwiki link identifiers that can be used for this site.
	*
	* @since 1.21
	*
	* @return String[]
	*/
	public Ordered_hash getInterwikiIds() {
		return (Ordered_hash)this.localIds.Get_by(XomwSite.ID_INTERWIKI);
	}

	/**
	* Returns the equivalent link identifiers that can be used to make
	* the site show up in interfaces such as the "language links" section.
	*
	* @since 1.21
	*
	* @return String[]
	*/
	public Ordered_hash getNavigationIds() {
		return (Ordered_hash)this.localIds.Get_by(XomwSite.ID_EQUIVALENT);
	}

	/**
	* Returns all local ids
	*
	* @since 1.21
	*
	* @return array[]
	*/
	public Ordered_hash getLocalIds() {
		return this.localIds;
	}

	/**
	* Sets the path used to construct links with.
	* Shall be equivalent to setPath(getLinkPathType(), $fullUrl).
	*
	* @since 1.21
	*
	* @param String $pathType
	* @param String $fullUrl
	*
	* @throws MWException
	*/
	public void setPath(String pathType, String fullUrl) {
		//	if (!is_string($fullUrl)) {
		//		throw new MWException('$fullUrl needs to be a String');
		//	}

		Hash_adp paths = (Hash_adp)this.extraData.Get_by("paths");
		if (paths == null) {
			paths = Hash_adp_.New();
			this.extraData.Add("paths", paths);
		}

		paths.Add_if_dupe_use_nth(pathType, fullUrl);
	}

	/**
	* Returns the path of the provided type or false if there is no such path.
	*
	* @since 1.21
	*
	* @param String $pathType
	*
	* @return String|null
	*/
	public String getPath(String pathType) {
		Hash_adp paths = this.getAllPaths();
		return (String)paths.Get_by(pathType);
	}

	/**
	* Returns the paths as associative array.
	* The keys are path types, the values are the path urls.
	*
	* @since 1.21
	*
	* @return String[]
	*/
	public Hash_adp getAllPaths() {
		return (Hash_adp)this.extraData.Get_by("paths");
	}

	/**
	* Removes the path of the provided type if it's set.
	*
	* @since 1.21
	*
	* @param String $pathType
	*/
	public void removePath(String pathType) {
		Hash_adp pathsHash = (Hash_adp)this.extraData.Get_by(pathType);
		if (pathsHash != null) {
			pathsHash.Del(pathType);
		}
	}

	/**
	* @since 1.21
	*
	* @param String $siteType
	*
	* @return Site
	*/
	public static XomwSite newForType(String siteType) {
		String type = (String)XomwDefaultSettings.wgSiteTypes.Get_by(siteType);
		if (String_.Eq(type, XomwDefaultSettings.wgSiteTypes__MediaWikiSite)) {
			return new XomwMediaWikiSite();
		}

		return new XomwSite(siteType);
	}

	//
	//	/**
	//	* @see Serializable::serialize
	//	*
	//	* @since 1.21
	//	*
	//	* @return String
	//	*/
	//	public function serialize() {
	//		$fields = [
	//			'globalid' => this.globalId,
	//			'type' => this.type,
	//			'group' => this.group,
	//			'source' => this.source,
	//			'language' => this.languageCode,
	//			'localids' => this.localIds,
	//			'config' => this.extraConfig,
	//			'data' => this.extraData,
	//			'forward' => this.forward,
	//			'internalid' => this.internalId,
	//
	//		];
	//
	//		return serialize($fields);
	//	}
	//
	//	/**
	//	* @see Serializable::unserialize
	//	*
	//	* @since 1.21
	//	*
	//	* @param String $serialized
	//	*/
	//	public function unserialize($serialized) {
	//		$fields = unserialize($serialized);
	//
	//		this.__construct($fields['type']);
	//
	//		this.setGlobalId($fields['globalid']);
	//		this.setGroup($fields['group']);
	//		this.setSource($fields['source']);
	//		this.setLanguageCode($fields['language']);
	//		this.localIds = $fields['localids'];
	//		this.setExtraConfig($fields['config']);
	//		this.setExtraData($fields['data']);
	//		this.setForward($fields['forward']);
	//		this.setInternalId($fields['internalid']);
	//	}
}
