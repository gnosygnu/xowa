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
import gplx.xowa.mediawiki.includes.libs.*;
/**
* Collection of Site objects.
*/
public class XomwSiteList extends XomwGenericArrayObject {	public int Len() {return 0;}
	public XomwSite GetAt(int idx) {return null;}
	public XomwSiteList() {super();
	}
	/**
	* Internal site identifiers pointing to their sites offset value.
	*
	* @since 1.21
	*
	* @var array Array of integer
	*/
	private final    Ordered_hash byInternalId = Ordered_hash_.New();

	/**
	* Global site identifiers pointing to their sites offset value.
	*
	* @since 1.21
	*
	* @var array Array of String
	*/
	private final    Ordered_hash byGlobalId = Ordered_hash_.New();

	/**
	* Navigational site identifiers alias inter-language prefixes
	* pointing to their sites offset value.
	*
	* @since 1.23
	*
	* @var array Array of String
	*/
	private final    Ordered_hash byNavigationId = Ordered_hash_.New();

	/**
	* @see GenericArrayObject::getObjectType
	*
	* @since 1.21
	*
	* @return String
	*/
	@Override public Class<?> getObjectType() {
		return XomwSite.class;
	}

	/**
	* @see GenericArrayObject::preSetElement
	*
	* @since 1.21
	*
	* @param int|String index
	* @param Site site
	*
	* @return boolean
	*/
	public boolean preSetElement(int index, XomwSite site) {
		if (this.hasSite(site.getGlobalId())) {
			this.removeSite(site.getGlobalId());
		}

		this.byGlobalId.Add(site.getGlobalId(), index);
		this.byInternalId.Add(site.getInternalId(), index);

		Ordered_hash ids = site.getNavigationIds();
		int len = ids.Len();
		for (int i = 0; i < len; i++) {
			int navId = Int_.cast(ids.Get_at(i));
			this.byNavigationId.Add(navId, index);
		}

		return true;
	}

	/**
	* @see ArrayObject::offsetUnset()
	*
	* @since 1.21
	*
	* @param mixed index
	*/
	public void offsetUnset(int index) {
		if (this.offsetExists(index)) {
			/**
			* @var Site site
			*/
			XomwSite site = (XomwSite)this.offsetGet(index);

			XophpArray.unset(this.byGlobalId, site.getGlobalId());
			XophpArray.unset(this.byInternalId, site.getInternalId());

			Ordered_hash ids = site.getNavigationIds();
			int len = ids.Len();
			for (int i = 0; i < len; i++) {
				int navId = Int_.cast(ids.Get_at(i));
				XophpArray.unset(this.byNavigationId, navId);
			}
		}

		super.offsetUnset(index);
	}

	/**
	* Returns all the global site identifiers.
	* Optionally only those belonging to the specified group.
	*
	* @since 1.21
	*
	* @return array
	*/
	public String[] getGlobalIdentifiers() {
		return XophpArray.array_keys_str(this.byGlobalId);
	}

	/**
	* Returns if the list contains the site with the provided global site identifier.
	*
	* @param String globalSiteId
	*
	* @return boolean
	*/
	public boolean hasSite(String globalSiteId) {
		return XophpArray.array_key_exists(globalSiteId, this.byGlobalId);
	}

	/**
	* Returns the Site with the provided global site identifier.
	* The site needs to exist, so if not sure, call hasGlobalId first.
	*
	* @since 1.21
	*
	* @param String globalSiteId
	*
	* @return Site
	*/
	public XomwSite getSite(String globalSiteId) {
		return (XomwSite)this.offsetGet(this.byGlobalId.Get_by(globalSiteId));
	}

	/**
	* Removes the site with the specified global site identifier.
	* The site needs to exist, so if not sure, call hasGlobalId first.
	*
	* @since 1.21
	*
	* @param String globalSiteId
	*/
	public void removeSite(String globalSiteId) {
		this.offsetUnset(this.byGlobalId.Get_by(globalSiteId));
	}

	/**
	* Returns if the list contains no sites.
	*
	* @since 1.21
	*
	* @return boolean
	*/
	@Override public boolean isEmpty() {
		return XophpArray.array_is_empty(this.byGlobalId);
	}

	/**
	* Returns if the list contains the site with the provided site id.
	*
	* @param int id
	*
	* @return boolean
	*/
	public boolean hasInternalId(int id) {
		return XophpArray.array_key_exists(id, this.byInternalId);
	}

	/**
	* Returns the Site with the provided site id.
	* The site needs to exist, so if not sure, call has first.
	*
	* @since 1.21
	*
	* @param int id
	*
	* @return Site
	*/
	public XomwSite getSiteByInternalId(int id) {
		return (XomwSite)this.offsetGet(this.byInternalId.Get_by(id));
	}

	/**
	* Removes the site with the specified site id.
	* The site needs to exist, so if not sure, call has first.
	*
	* @since 1.21
	*
	* @param int id
	*/
	public void removeSiteByInternalId(int id) {
		this.offsetUnset(this.byInternalId.Get_by(id));
	}

	/**
	* Returns if the list contains the site with the provided navigational site id.
	*
	* @param String id
	*
	* @return boolean
	*/
	public boolean hasNavigationId(String id) {
		return XophpArray.array_key_exists(id, this.byNavigationId);
	}

	/**
	* Returns the Site with the provided navigational site id.
	* The site needs to exist, so if not sure, call has first.
	*
	* @since 1.23
	*
	* @param String id
	*
	* @return Site
	*/
	public XomwSite getSiteByNavigationId(String id) {
		return (XomwSite)this.offsetGet(this.byNavigationId.Get_by(id));
	}

	/**
	* Removes the site with the specified navigational site id.
	* The site needs to exist, so if not sure, call has first.
	*
	* @since 1.23
	*
	* @param String id
	*/
	public void removeSiteByNavigationId(String id) {
		this.offsetUnset(this.byNavigationId.Get_by(id));
	}

	/**
	* Sets a site in the list. If the site was not there,
	* it will be added. If it was, it will be updated.
	*
	* @since 1.21
	*
	* @param Site site
	*/
	public void setSite(XomwSite site) {
		this.Add_or_update(site);
	}

	/**
	* Returns the sites that are in the provided group.
	*
	* @since 1.21
	*
	* @param String groupName
	*
	* @return SiteList
	*/
	public XomwSiteList getGroup(String groupName) {
		XomwSiteList group = new XomwSiteList();

		/**
		* @var Site site
		*/
		int len = this.count();
		for (int i = 0; i < len; i++) {
			XomwSite site = (XomwSite)this.Get_at(i);
			if (String_.Eq(site.getGroup(), groupName)) {
				group.Add_or_update(site);
			}
		}

		return group;
	}
	
	//	/**
	//	* A version ID that identifies the serialization structure used by getSerializationData()
	//	* and unserialize(). This is useful for constructing cache keys in cases where the cache relies
	//	* on serialization for storing the SiteList.
	//	*
	//	* @var String A String uniquely identifying the version of the serialization structure,
	//	*             not including any sub-structures.
	//	*/
	//	static final SERIAL_VERSION_ID = '2014-03-17';
	//
	//	/**
	//	* Returns the version ID that identifies the serialization structure used by
	//	* getSerializationData() and unserialize(), including the structure of any nested structures.
	//	* This is useful for constructing cache keys in cases where the cache relies
	//	* on serialization for storing the SiteList.
	//	*
	//	* @return String A String uniquely identifying the version of the serialization structure,
	//	*                including any sub-structures.
	//	*/
	//	public static function getSerialVersionId() {
	//		return self::SERIAL_VERSION_ID . '+Site:' . Site::SERIAL_VERSION_ID;
	//	}
	//
	//	/**
	//	* @see GenericArrayObject::getSerializationData
	//	*
	//	* @since 1.21
	//	*
	//	* @return array
	//	*/
	//	protected function getSerializationData() {
	//		// NOTE: When changing the structure, either implement unserialize() to handle the
	//		//      old structure too, or update SERIAL_VERSION_ID to kill any caches.
	//		return array_merge(
	//			super.getSerializationData(),
	//			[
	//				'internalIds' => this.byInternalId,
	//				'globalIds' => this.byGlobalId,
	//				'navigationIds' => this.byNavigationId
	//			]
	//		);
	//	}
	//
	//	/**
	//	* @see GenericArrayObject::unserialize
	//	*
	//	* @since 1.21
	//	*
	//	* @param String serialization
	//	*
	//	* @return array
	//	*/
	//	public function unserialize(serialization) {
	//		serializationData = super.unserialize(serialization);
	//
	//		this.byInternalId = serializationData['internalIds'];
	//		this.byGlobalId = serializationData['globalIds'];
	//		this.byNavigationId = serializationData['navigationIds'];
	//
	//		return serializationData;
	//	}
}
