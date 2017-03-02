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
* Collection of Site objects.
*/
public class XomwSiteList {
	public int Len() {return 0;}
	public XomwSite GetAt(int idx) {return null;}
//		/**
//		* Internal site identifiers pointing to their sites offset value.
//		*
//		* @since 1.21
//		*
//		* @var array Array of integer
//		*/
//		protected $byInternalId = [];
//
//		/**
//		* Global site identifiers pointing to their sites offset value.
//		*
//		* @since 1.21
//		*
//		* @var array Array of String
//		*/
//		protected $byGlobalId = [];
//
//		/**
//		* Navigational site identifiers alias inter-language prefixes
//		* pointing to their sites offset value.
//		*
//		* @since 1.23
//		*
//		* @var array Array of String
//		*/
//		protected $byNavigationId = [];
//
//		/**
//		* @see GenericArrayObject::getObjectType
//		*
//		* @since 1.21
//		*
//		* @return String
//		*/
//		public function getObjectType() {
//			return 'Site';
//		}
//
//		/**
//		* @see GenericArrayObject::preSetElement
//		*
//		* @since 1.21
//		*
//		* @param int|String $index
//		* @param Site $site
//		*
//		* @return boolean
//		*/
//		protected function preSetElement( $index, $site ) {
//			if ( $this->hasSite( $site->getGlobalId() ) ) {
//				$this->removeSite( $site->getGlobalId() );
//			}
//
//			$this->byGlobalId[$site->getGlobalId()] = $index;
//			$this->byInternalId[$site->getInternalId()] = $index;
//
//			$ids = $site->getNavigationIds();
//			foreach ( $ids as $navId ) {
//				$this->byNavigationId[$navId] = $index;
//			}
//
//			return true;
//		}
//
//		/**
//		* @see ArrayObject::offsetUnset()
//		*
//		* @since 1.21
//		*
//		* @param mixed $index
//		*/
//		public function offsetUnset( $index ) {
//			if ( $this->offsetExists( $index ) ) {
//				/**
//				* @var Site $site
//				*/
//				$site = $this->offsetGet( $index );
//
//				unset( $this->byGlobalId[$site->getGlobalId()] );
//				unset( $this->byInternalId[$site->getInternalId()] );
//
//				$ids = $site->getNavigationIds();
//				foreach ( $ids as $navId ) {
//					unset( $this->byNavigationId[$navId] );
//				}
//			}
//
//			parent::offsetUnset( $index );
//		}
//
//		/**
//		* Returns all the global site identifiers.
//		* Optionally only those belonging to the specified group.
//		*
//		* @since 1.21
//		*
//		* @return array
//		*/
//		public function getGlobalIdentifiers() {
//			return array_keys( $this->byGlobalId );
//		}
//
//		/**
//		* Returns if the list contains the site with the provided global site identifier.
//		*
//		* @param String $globalSiteId
//		*
//		* @return boolean
//		*/
//		public function hasSite( $globalSiteId ) {
//			return array_key_exists( $globalSiteId, $this->byGlobalId );
//		}
//
//		/**
//		* Returns the Site with the provided global site identifier.
//		* The site needs to exist, so if not sure, call hasGlobalId first.
//		*
//		* @since 1.21
//		*
//		* @param String $globalSiteId
//		*
//		* @return Site
//		*/
//		public function getSite( $globalSiteId ) {
//			return $this->offsetGet( $this->byGlobalId[$globalSiteId] );
//		}
//
//		/**
//		* Removes the site with the specified global site identifier.
//		* The site needs to exist, so if not sure, call hasGlobalId first.
//		*
//		* @since 1.21
//		*
//		* @param String $globalSiteId
//		*/
//		public function removeSite( $globalSiteId ) {
//			$this->offsetUnset( $this->byGlobalId[$globalSiteId] );
//		}
//
//		/**
//		* Returns if the list contains no sites.
//		*
//		* @since 1.21
//		*
//		* @return boolean
//		*/
//		public function isEmpty() {
//			return $this->byGlobalId === [];
//		}
//
//		/**
//		* Returns if the list contains the site with the provided site id.
//		*
//		* @param int $id
//		*
//		* @return boolean
//		*/
//		public function hasInternalId( $id ) {
//			return array_key_exists( $id, $this->byInternalId );
//		}
//
//		/**
//		* Returns the Site with the provided site id.
//		* The site needs to exist, so if not sure, call has first.
//		*
//		* @since 1.21
//		*
//		* @param int $id
//		*
//		* @return Site
//		*/
//		public function getSiteByInternalId( $id ) {
//			return $this->offsetGet( $this->byInternalId[$id] );
//		}
//
//		/**
//		* Removes the site with the specified site id.
//		* The site needs to exist, so if not sure, call has first.
//		*
//		* @since 1.21
//		*
//		* @param int $id
//		*/
//		public function removeSiteByInternalId( $id ) {
//			$this->offsetUnset( $this->byInternalId[$id] );
//		}
//
//		/**
//		* Returns if the list contains the site with the provided navigational site id.
//		*
//		* @param String $id
//		*
//		* @return boolean
//		*/
//		public function hasNavigationId( $id ) {
//			return array_key_exists( $id, $this->byNavigationId );
//		}
//
//		/**
//		* Returns the Site with the provided navigational site id.
//		* The site needs to exist, so if not sure, call has first.
//		*
//		* @since 1.23
//		*
//		* @param String $id
//		*
//		* @return Site
//		*/
//		public function getSiteByNavigationId( $id ) {
//			return $this->offsetGet( $this->byNavigationId[$id] );
//		}
//
//		/**
//		* Removes the site with the specified navigational site id.
//		* The site needs to exist, so if not sure, call has first.
//		*
//		* @since 1.23
//		*
//		* @param String $id
//		*/
//		public function removeSiteByNavigationId( $id ) {
//			$this->offsetUnset( $this->byNavigationId[$id] );
//		}
//
//		/**
//		* Sets a site in the list. If the site was not there,
//		* it will be added. If it was, it will be updated.
//		*
//		* @since 1.21
//		*
//		* @param Site $site
//		*/
//		public function setSite( Site $site ) {
//			$this[] = $site;
//		}
//
//		/**
//		* Returns the sites that are in the provided group.
//		*
//		* @since 1.21
//		*
//		* @param String $groupName
//		*
//		* @return SiteList
//		*/
//		public function getGroup( $groupName ) {
//			$group = new self();
//
//			/**
//			* @var Site $site
//			*/
//			foreach ( $this as $site ) {
//				if ( $site->getGroup() === $groupName ) {
//					$group[] = $site;
//				}
//			}
//
//			return $group;
//		}
//
//		/**
//		* A version ID that identifies the serialization structure used by getSerializationData()
//		* and unserialize(). This is useful for constructing cache keys in cases where the cache relies
//		* on serialization for storing the SiteList.
//		*
//		* @var String A String uniquely identifying the version of the serialization structure,
//		*             not including any sub-structures.
//		*/
//		static final SERIAL_VERSION_ID = '2014-03-17';
//
//		/**
//		* Returns the version ID that identifies the serialization structure used by
//		* getSerializationData() and unserialize(), including the structure of any nested structures.
//		* This is useful for constructing cache keys in cases where the cache relies
//		* on serialization for storing the SiteList.
//		*
//		* @return String A String uniquely identifying the version of the serialization structure,
//		*                including any sub-structures.
//		*/
//		public static function getSerialVersionId() {
//			return self::SERIAL_VERSION_ID . '+Site:' . Site::SERIAL_VERSION_ID;
//		}
//
//		/**
//		* @see GenericArrayObject::getSerializationData
//		*
//		* @since 1.21
//		*
//		* @return array
//		*/
//		protected function getSerializationData() {
//			// NOTE: When changing the structure, either implement unserialize() to handle the
//			//      old structure too, or update SERIAL_VERSION_ID to kill any caches.
//			return array_merge(
//				parent::getSerializationData(),
//				[
//					'internalIds' => $this->byInternalId,
//					'globalIds' => $this->byGlobalId,
//					'navigationIds' => $this->byNavigationId
//				]
//			);
//		}
//
//		/**
//		* @see GenericArrayObject::unserialize
//		*
//		* @since 1.21
//		*
//		* @param String $serialization
//		*
//		* @return array
//		*/
//		public function unserialize( $serialization ) {
//			$serializationData = parent::unserialize( $serialization );
//
//			$this->byInternalId = $serializationData['internalIds'];
//			$this->byGlobalId = $serializationData['globalIds'];
//			$this->byNavigationId = $serializationData['navigationIds'];
//
//			return $serializationData;
//		}
}
