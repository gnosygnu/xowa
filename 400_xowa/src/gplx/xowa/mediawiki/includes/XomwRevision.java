/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes;
import gplx.xowa.mediawiki.XophpInt_;
import gplx.xowa.mediawiki.XophpObject_;
import gplx.xowa.mediawiki.XophpType_;
import gplx.xowa.mediawiki.includes.Revision.XomwRevisionRecord;
import gplx.xowa.mediawiki.includes.content.XomwContent;
import gplx.xowa.mediawiki.includes.dao.XomwIDBAccessObject;
import gplx.xowa.mediawiki.includes.libs.rdbms.database.XomwIDatabase;
import gplx.xowa.mediawiki.includes.linkers.XomwLinkTarget;
import gplx.xowa.mediawiki.includes.user.XomwUser;
// MW.SRC:1.33.1
/**
* @+deprecated since 1.31, use RevisionRecord, RevisionStore, and BlobStore instead.
*/
public class XomwRevision implements XomwIDBAccessObject {

		/** @var RevisionRecord */
		protected XomwRevisionRecord mRecord;
	
		// Revision deletion constants
		static final int DELETED_TEXT = XomwRevisionRecord.DELETED_TEXT;
		static final int DELETED_COMMENT = XomwRevisionRecord.DELETED_COMMENT;
		static final int DELETED_USER = XomwRevisionRecord.DELETED_USER;
		static final int DELETED_RESTRICTED = XomwRevisionRecord.DELETED_RESTRICTED;
		static final int SUPPRESSED_USER = XomwRevisionRecord.SUPPRESSED_USER;
		static final int SUPPRESSED_ALL = XomwRevisionRecord.SUPPRESSED_ALL;

		// Audience options for accessors
		static final int FOR_PUBLIC = XomwRevisionRecord.FOR_PUBLIC;
		static final int FOR_THIS_USER = XomwRevisionRecord.FOR_THIS_USER;
		static final int RAW = XomwRevisionRecord.RAW;
//
//		static final TEXT_CACHE_GROUP = SqlBlobStore::TEXT_CACHE_GROUP;
//
//		/**
//		* @return RevisionStore
//		*/
//		protected static function getRevisionStore($wiki = false) {
//			if ($wiki) {
//				return MediaWikiServices::getInstance().getRevisionStoreFactory()
//					.getRevisionStore($wiki);
//			} else {
//				return MediaWikiServices::getInstance().getRevisionStore();
//			}
//		}
//
//		/**
//		* @return RevisionLookup
//		*/
//		protected static function getRevisionLookup() {
//			return MediaWikiServices::getInstance().getRevisionLookup();
//		}
//
//		/**
//		* @return RevisionFactory
//		*/
//		protected static function getRevisionFactory() {
//			return MediaWikiServices::getInstance().getRevisionFactory();
//		}
//
//		/**
//		* @param boolean|String $wiki The ID of the target wiki database. Use false for the local wiki.
//		*
//		* @return SqlBlobStore
//		*/
//		protected static function getBlobStore($wiki = false) {
//			$store = MediaWikiServices::getInstance()
//				.getBlobStoreFactory()
//				.newSqlBlobStore($wiki);
//
//			if (!$store instanceof SqlBlobStore) {
//				throw new RuntimeException(
//					'The backwards compatibility code in Revision currently requires the BlobStore '
//					. 'service to be an SqlBlobStore instance, but it is a ' . get_class($store)
//				);
//			}
//
//			return $store;
//		}
//
		/**
		* Load a page revision from a given revision ID number.
		* Returns null if no such revision can be found.
		*
		* $flags include:
		*      Revision::READ_LATEST  : Select the data from the master
		*      Revision::READ_LOCKING : Select & synchronized the data from the master
		*
		* @param int $id
		* @param int $flags (optional)
		* @return Revision|null
		*/
		public static XomwRevision newFromId(int id) {return newFromId(id, 0);}
		public static XomwRevision newFromId(int id, int flags) {
//			$rec = self::getRevisionLookup().getRevisionById($id, $flags);
//			return $rec ? new Revision($rec, $flags) : null;
			return null;
		}

		/**
		* Load either the current, or a specified, revision
		* that's attached to a given link target. If not attached
		* to that link target, will return null.
		*
		* $flags include:
		*      Revision::READ_LATEST  : Select the data from the master
		*      Revision::READ_LOCKING : Select & synchronized the data from the master
		*
		* @param LinkTarget $linkTarget
		* @param int $id (optional)
		* @param int $flags Bitfield (optional)
		* @return Revision|null
		*/
		public static XomwRevision newFromTitle(XomwLinkTarget linkTarget) {return newFromTitle(linkTarget, 0, 0);}
		public static XomwRevision newFromTitle(XomwLinkTarget linkTarget, int id, int flags) {
//			$rec = self::getRevisionLookup().getRevisionByTitle($linkTarget, $id, $flags);
//			return $rec ? new Revision($rec, $flags) : null;
			return null;
		}

//		/**
//		* Load either the current, or a specified, revision
//		* that's attached to a given page ID.
//		* Returns null if no such revision can be found.
//		*
//		* $flags include:
//		*      Revision::READ_LATEST  : Select the data from the master (since 1.20)
//		*      Revision::READ_LOCKING : Select & synchronized the data from the master
//		*
//		* @param int $pageId
//		* @param int $revId (optional)
//		* @param int $flags Bitfield (optional)
//		* @return Revision|null
//		*/
//		public static function newFromPageId($pageId, $revId = 0, $flags = 0) {
//			$rec = self::getRevisionLookup().getRevisionByPageId($pageId, $revId, $flags);
//			return $rec ? new Revision($rec, $flags) : null;
//		}
//
//		/**
//		* Make a fake revision Object from an archive table row. This is queried
//		* for permissions or even inserted (as in Special:Undelete)
//		*
//		* @param Object $row
//		* @param array $overrides
//		*
//		* @throws MWException
//		* @return Revision
//		*/
//		public static function newFromArchiveRow($row, $overrides = []) {
//			/**
//			* MCR Migration: https://phabricator.wikimedia.org/T183564
//			* This method used to overwrite attributes, then passed to Revision::__construct
//			* RevisionStore::newRevisionFromArchiveRow instead overrides row field names
//			* So do a conversion here.
//			*/
//			if (array_key_exists('page', $overrides)) {
//				$overrides['page_id'] = $overrides['page'];
//				unset($overrides['page']);
//			}
//
//			/**
//			* We require a Title for both the Revision Object and the RevisionRecord.
//			* Below is duplicated logic from RevisionStore::newRevisionFromArchiveRow
//			* to fetch a title in order pass it into the Revision Object.
//			*/
//			$title = null;
//			if (isset($overrides['title'])) {
//				if (!($overrides['title'] instanceof Title)) {
//					throw new MWException('title field override must contain a Title Object.');
//				}
//
//				$title = $overrides['title'];
//			}
//			if ($title !== null) {
//				if (isset($row.ar_namespace) && isset($row.ar_title)) {
//					$title = Title::makeTitle($row.ar_namespace, $row.ar_title);
//				} else {
//					throw new InvalidArgumentException(
//						'A Title or ar_namespace and ar_title must be given'
//					);
//				}
//			}
//
//			$rec = self::getRevisionFactory().newRevisionFromArchiveRow($row, 0, $title, $overrides);
//			return new Revision($rec, self::READ_NORMAL, $title);
//		}
//
//		/**
//		* @since 1.19
//		*
//		* MCR migration note: replaced by RevisionStore::newRevisionFromRow(). Note that
//		* newFromRow() also accepts arrays, while newRevisionFromRow() does not. Instead,
//		* a MutableRevisionRecord should be constructed directly.
//		* RevisionStore::newMutableRevisionFromArray() can be used as a temporary replacement,
//		* but should be avoided.
//		*
//		* @param Object|array $row
//		* @return Revision
//		*/
//		public static function newFromRow($row) {
//			if (is_array($row)) {
//				$rec = self::getRevisionFactory().newMutableRevisionFromArray($row);
//			} else {
//				$rec = self::getRevisionFactory().newRevisionFromRow($row);
//			}
//
//			return new Revision($rec);
//		}
//
//		/**
//		* Load a page revision from a given revision ID number.
//		* Returns null if no such revision can be found.
//		*
//		* @deprecated since 1.31, use RevisionStore::getRevisionById() instead.
//		*
//		* @param IDatabase $db
//		* @param int $id
//		* @return Revision|null
//		*/
//		public static function loadFromId($db, $id) {
//			wfDeprecated(__METHOD__, '1.31'); // no known callers
//			$rec = self::getRevisionStore().loadRevisionFromId($db, $id);
//			return $rec ? new Revision($rec) : null;
//		}
//
//		/**
//		* Load either the current, or a specified, revision
//		* that's attached to a given page. If not attached
//		* to that page, will return null.
//		*
//		* @deprecated since 1.31, use RevisionStore::getRevisionByPageId() instead.
//		*
//		* @param IDatabase $db
//		* @param int $pageid
//		* @param int $id
//		* @return Revision|null
//		*/
//		public static function loadFromPageId($db, $pageid, $id = 0) {
//			$rec = self::getRevisionStore().loadRevisionFromPageId($db, $pageid, $id);
//			return $rec ? new Revision($rec) : null;
//		}
//
//		/**
//		* Load either the current, or a specified, revision
//		* that's attached to a given page. If not attached
//		* to that page, will return null.
//		*
//		* @deprecated since 1.31, use RevisionStore::getRevisionByTitle() instead.
//		*
//		* @param IDatabase $db
//		* @param Title $title
//		* @param int $id
//		* @return Revision|null
//		*/
//		public static function loadFromTitle($db, $title, $id = 0) {
//			$rec = self::getRevisionStore().loadRevisionFromTitle($db, $title, $id);
//			return $rec ? new Revision($rec) : null;
//		}
//
//		/**
//		* Load the revision for the given title with the given timestamp.
//		* WARNING: Timestamps may in some circumstances not be unique,
//		* so this isn't the best key to use.
//		*
//		* @deprecated since 1.31, use RevisionStore::getRevisionByTimestamp()
//		*   or RevisionStore::loadRevisionFromTimestamp() instead.
//		*
//		* @param IDatabase $db
//		* @param Title $title
//		* @param String $timestamp
//		* @return Revision|null
//		*/
//		public static function loadFromTimestamp($db, $title, $timestamp) {
//			$rec = self::getRevisionStore().loadRevisionFromTimestamp($db, $title, $timestamp);
//			return $rec ? new Revision($rec) : null;
//		}
//
//		/**
//		* Return the value of a select() JOIN conds array for the user table.
//		* This will get user table rows for logged-in users.
//		* @since 1.19
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo([ 'user' ]) instead.
//		* @return array
//		*/
//		public static function userJoinCond() {
//			global $wgActorTableSchemaMigrationStage;
//
//			wfDeprecated(__METHOD__, '1.31');
//			if ($wgActorTableSchemaMigrationStage & SCHEMA_COMPAT_READ_NEW) {
//				// If code is using this instead of self::getQueryInfo(), there's
//				// no way the join it's trying to do can work once the old fields
//				// aren't being used anymore.
//				throw new BadMethodCallException(
//					'Cannot use ' . __METHOD__
//						. ' when $wgActorTableSchemaMigrationStage has SCHEMA_COMPAT_READ_NEW'
//				);
//			}
//
//			return [ 'LEFT JOIN', [ 'rev_user != 0', 'user_id = rev_user' ] ];
//		}
//
//		/**
//		* Return the value of a select() page conds array for the page table.
//		* This will assure that the revision(s) are not orphaned from live pages.
//		* @since 1.19
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo([ 'page' ]) instead.
//		* @return array
//		*/
//		public static function pageJoinCond() {
//			wfDeprecated(__METHOD__, '1.31');
//			return [ 'JOIN', [ 'page_id = rev_page' ] ];
//		}
//
//		/**
//		* Return the list of revision fields that should be selected to create
//		* a new revision.
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo() instead.
//		* @return array
//		*/
//		public static function selectFields() {
//			global $wgContentHandlerUseDB, $wgActorTableSchemaMigrationStage;
//			global $wgMultiContentRevisionSchemaMigrationStage;
//
//			if ($wgActorTableSchemaMigrationStage & SCHEMA_COMPAT_READ_NEW) {
//				// If code is using this instead of self::getQueryInfo(), there's a
//				// decent chance it's going to try to directly access
//				// $row.rev_user or $row.rev_user_text and we can't give it
//				// useful values here once those aren't being used anymore.
//				throw new BadMethodCallException(
//					'Cannot use ' . __METHOD__
//						. ' when $wgActorTableSchemaMigrationStage has SCHEMA_COMPAT_READ_NEW'
//				);
//			}
//
//			if (!($wgMultiContentRevisionSchemaMigrationStage & SCHEMA_COMPAT_WRITE_OLD)) {
//				// If code is using this instead of self::getQueryInfo(), there's a
//				// decent chance it's going to try to directly access
//				// $row.rev_text_id or $row.rev_content_model and we can't give it
//				// useful values here once those aren't being written anymore,
//				// and may not exist at all.
//				throw new BadMethodCallException(
//					'Cannot use ' . __METHOD__ . ' when $wgMultiContentRevisionSchemaMigrationStage '
//					. 'does not have SCHEMA_COMPAT_WRITE_OLD set.'
//				);
//			}
//
//			wfDeprecated(__METHOD__, '1.31');
//
//			$fields = [
//				'rev_id',
//				'rev_page',
//				'rev_text_id',
//				'rev_timestamp',
//				'rev_user_text',
//				'rev_user',
//				'rev_actor' => 'NULL',
//				'rev_minor_edit',
//				'rev_deleted',
//				'rev_len',
//				'rev_parent_id',
//				'rev_sha1',
//			];
//
//			$fields += CommentStore::getStore().getFields('rev_comment');
//
//			if ($wgContentHandlerUseDB) {
//				$fields[] = 'rev_content_format';
//				$fields[] = 'rev_content_model';
//			}
//
//			return $fields;
//		}
//
//		/**
//		* Return the list of revision fields that should be selected to create
//		* a new revision from an archive row.
//		* @deprecated since 1.31, use RevisionStore::getArchiveQueryInfo() instead.
//		* @return array
//		*/
//		public static function selectArchiveFields() {
//			global $wgContentHandlerUseDB, $wgActorTableSchemaMigrationStage;
//			global $wgMultiContentRevisionSchemaMigrationStage;
//
//			if ($wgActorTableSchemaMigrationStage & SCHEMA_COMPAT_READ_NEW) {
//				// If code is using this instead of self::getQueryInfo(), there's a
//				// decent chance it's going to try to directly access
//				// $row.ar_user or $row.ar_user_text and we can't give it
//				// useful values here once those aren't being used anymore.
//				throw new BadMethodCallException(
//					'Cannot use ' . __METHOD__
//						. ' when $wgActorTableSchemaMigrationStage has SCHEMA_COMPAT_READ_NEW'
//				);
//			}
//
//			if (!($wgMultiContentRevisionSchemaMigrationStage & SCHEMA_COMPAT_WRITE_OLD)) {
//				// If code is using this instead of self::getQueryInfo(), there's a
//				// decent chance it's going to try to directly access
//				// $row.ar_text_id or $row.ar_content_model and we can't give it
//				// useful values here once those aren't being written anymore,
//				// and may not exist at all.
//				throw new BadMethodCallException(
//					'Cannot use ' . __METHOD__ . ' when $wgMultiContentRevisionSchemaMigrationStage '
//					. 'does not have SCHEMA_COMPAT_WRITE_OLD set.'
//				);
//			}
//
//			wfDeprecated(__METHOD__, '1.31');
//
//			$fields = [
//				'ar_id',
//				'ar_page_id',
//				'ar_rev_id',
//				'ar_text_id',
//				'ar_timestamp',
//				'ar_user_text',
//				'ar_user',
//				'ar_actor' => 'NULL',
//				'ar_minor_edit',
//				'ar_deleted',
//				'ar_len',
//				'ar_parent_id',
//				'ar_sha1',
//			];
//
//			$fields += CommentStore::getStore().getFields('ar_comment');
//
//			if ($wgContentHandlerUseDB) {
//				$fields[] = 'ar_content_format';
//				$fields[] = 'ar_content_model';
//			}
//			return $fields;
//		}
//
//		/**
//		* Return the list of text fields that should be selected to read the
//		* revision text
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo([ 'text' ]) instead.
//		* @return array
//		*/
//		public static function selectTextFields() {
//			wfDeprecated(__METHOD__, '1.31');
//			return [
//				'old_text',
//				'old_flags'
//			];
//		}
//
//		/**
//		* Return the list of page fields that should be selected from page table
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo([ 'page' ]) instead.
//		* @return array
//		*/
//		public static function selectPageFields() {
//			wfDeprecated(__METHOD__, '1.31');
//			return [
//				'page_namespace',
//				'page_title',
//				'page_id',
//				'page_latest',
//				'page_is_redirect',
//				'page_len',
//			];
//		}
//
//		/**
//		* Return the list of user fields that should be selected from user table
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo([ 'user' ]) instead.
//		* @return array
//		*/
//		public static function selectUserFields() {
//			wfDeprecated(__METHOD__, '1.31');
//			return [ 'user_name' ];
//		}
//
//		/**
//		* Return the tables, fields, and join conditions to be selected to create
//		* a new revision Object.
//		* @since 1.31
//		* @deprecated since 1.31, use RevisionStore::getQueryInfo() instead.
//		* @param array $options Any combination of the following strings
//		*  - 'page': Join with the page table, and select fields to identify the page
//		*  - 'user': Join with the user table, and select the user name
//		*  - 'text': Join with the text table, and select fields to load page text
//		* @return array With three keys:
//		*   - tables: (String[]) to include in the `$table` to `IDatabase.select()`
//		*   - fields: (String[]) to include in the `$vars` to `IDatabase.select()`
//		*   - joins: (array) to include in the `$join_conds` to `IDatabase.select()`
//		*/
//		public static function getQueryInfo($options = []) {
//			return self::getRevisionStore().getQueryInfo($options);
//		}
//
//		/**
//		* Return the tables, fields, and join conditions to be selected to create
//		* a new archived revision Object.
//		* @since 1.31
//		* @deprecated since 1.31, use RevisionStore::getArchiveQueryInfo() instead.
//		* @return array With three keys:
//		*   - tables: (String[]) to include in the `$table` to `IDatabase.select()`
//		*   - fields: (String[]) to include in the `$vars` to `IDatabase.select()`
//		*   - joins: (array) to include in the `$join_conds` to `IDatabase.select()`
//		*/
//		public static function getArchiveQueryInfo() {
//			return self::getRevisionStore().getArchiveQueryInfo();
//		}
//
//		/**
//		* Do a batched query to get the parent revision lengths
//		*
//		* @deprecated in 1.31, use RevisionStore::getRevisionSizes instead.
//		*
//		* @param IDatabase $db
//		* @param array $revIds
//		* @return array
//		*/
//		public static function getParentLengths($db, array $revIds) {
//			return self::getRevisionStore().listRevisionSizes($db, $revIds);
//		}
//
//		/**
//		* @param Object|array|RevisionRecord $row Either a database row or an array
//		* @param int $queryFlags
//		* @param Title|null $title
//		*
//		* @private
//		*/
//		function __construct($row, $queryFlags = 0, Title $title = null) {
//			global $wgUser;
//
//			if ($row instanceof RevisionRecord) {
//				this.mRecord = $row;
//			} elseif (is_array($row)) {
//				// If no user is specified, fall back to using the global user Object, to stay
//				// compatible with pre-1.31 behavior.
//				if (!isset($row['user']) && !isset($row['user_text'])) {
//					$row['user'] = $wgUser;
//				}
//
//				this.mRecord = self::getRevisionFactory().newMutableRevisionFromArray(
//					$row,
//					$queryFlags,
//					this.ensureTitle($row, $queryFlags, $title)
//				);
//			} elseif (is_object($row)) {
//				this.mRecord = self::getRevisionFactory().newRevisionFromRow(
//					$row,
//					$queryFlags,
//					this.ensureTitle($row, $queryFlags, $title)
//				);
//			} else {
//				throw new InvalidArgumentException(
//					'$row must be a row Object, an associative array, or a RevisionRecord'
//				);
//			}
//
//			Assert::postcondition(this.mRecord !== null, 'Failed to construct a RevisionRecord');
//		}
//
//		/**
//		* Make sure we have *some* Title Object for use by the constructor.
//		* For B/C, the constructor shouldn't fail even for a bad page ID or bad revision ID.
//		*
//		* @param array|Object $row
//		* @param int $queryFlags
//		* @param Title|null $title
//		*
//		* @return Title $title if not null, or a Title constructed from information in $row.
//		*/
//		private function ensureTitle($row, $queryFlags, $title = null) {
//			if ($title) {
//				return $title;
//			}
//
//			if (is_array($row)) {
//				if (isset($row['title'])) {
//					if (!($row['title'] instanceof Title)) {
//						throw new MWException('title field must contain a Title Object.');
//					}
//
//					return $row['title'];
//				}
//
//				$pageId = $row['page'] ?? 0;
//				$revId = $row['id'] ?? 0;
//			} else {
//				$pageId = $row.rev_page ?? 0;
//				$revId = $row.rev_id ?? 0;
//			}
//
//			try {
//				$title = self::getRevisionStore().getTitle($pageId, $revId, $queryFlags);
//			} catch (RevisionAccessException $ex) {
//				// construct a dummy title!
//				wfLogWarning(__METHOD__ . ': ' . $ex.getMessage());
//
//				// NOTE: this Title will only be used inside RevisionRecord
//				$title = Title::makeTitleSafe(NS_SPECIAL, "Badtitle/ID=$pageId");
//				$title.resetArticleID($pageId);
//			}
//
//			return $title;
//		}
//
//		/**
//		* @return RevisionRecord
//		*/
//		public function getRevisionRecord() {
//			return this.mRecord;
//		}

		/**
		* Get revision ID
		*
		* @return int|null
		*/
		public int getId() {
			return this.mRecord.getId();
		}
//
//		/**
//		* Set the revision ID
//		*
//		* This should only be used for proposed revisions that turn out to be null edits.
//		*
//		* @note Only supported on Revisions that were constructed based on associative arrays,
//		*       since they are mutable.
//		*
//		* @since 1.19
//		* @param int|String $id
//		* @throws MWException
//		*/
//		public function setId($id) {
//			if (this.mRecord instanceof MutableRevisionRecord) {
//				this.mRecord.setId(intval($id));
//			} else {
//				throw new MWException(__METHOD__ . ' is not supported on this instance');
//			}
//		}
//
//		/**
//		* Set the user ID/name
//		*
//		* This should only be used for proposed revisions that turn out to be null edits
//		*
//		* @note Only supported on Revisions that were constructed based on associative arrays,
//		*       since they are mutable.
//		*
//		* @since 1.28
//		* @deprecated since 1.31, please reuse old Revision Object
//		* @param int $id User ID
//		* @param String $name User name
//		* @throws MWException
//		*/
//		public function setUserIdAndName($id, $name) {
//			if (this.mRecord instanceof MutableRevisionRecord) {
//				$user = User::newFromAnyId(intval($id), $name, null);
//				this.mRecord.setUser($user);
//			} else {
//				throw new MWException(__METHOD__ . ' is not supported on this instance');
//			}
//		}
//
//		/**
//		* @return SlotRecord
//		*/
//		private function getMainSlotRaw() {
//			return this.mRecord.getSlot(SlotRecord::MAIN, RevisionRecord::RAW);
//		}
//
//		/**
//		* Get the ID of the row of the text table that contains the content of the
//		* revision's main slot, if that content is stored in the text table.
//		*
//		* If the content is stored elsewhere, this returns null.
//		*
//		* @deprecated since 1.31, use RevisionRecord().getSlot().getContentAddress() to
//		* get that actual address that can be used with BlobStore::getBlob(); or use
//		* RevisionRecord::hasSameContent() to check if two revisions have the same content.
//		*
//		* @return int|null
//		*/
//		public function getTextId() {
//			$slot = this.getMainSlotRaw();
//			return $slot.hasAddress()
//				? self::getBlobStore().getTextIdFromAddress($slot.getAddress())
//				: null;
//		}
//
//		/**
//		* Get parent revision ID (the original previous page revision)
//		*
//		* @return int|null The ID of the parent revision. 0 indicates that there is no
//		* parent revision. Null indicates that the parent revision is not known.
//		*/
//		public function getParentId() {
//			return this.mRecord.getParentId();
//		}
//
//		/**
//		* Returns the length of the text in this revision, or null if unknown.
//		*
//		* @return int|null
//		*/
//		public function getSize() {
//			try {
//				return this.mRecord.getSize();
//			} catch (RevisionAccessException $ex) {
//				return null;
//			}
//		}
//
//		/**
//		* Returns the base36 sha1 of the content in this revision, or null if unknown.
//		*
//		* @return String|null
//		*/
//		public function getSha1() {
//			try {
//				return this.mRecord.getSha1();
//			} catch (RevisionAccessException $ex) {
//				return null;
//			}
//		}
//
		/**
		* Returns the title of the page associated with this entry.
		* Since 1.31, this will never return null.
		*
		* Will do a query, when title is not set and id is given.
		*
		* @return Title
		*/
		public XomwTitle getTitle() {
//			$linkTarget = this.mRecord.getPageAsLinkTarget();
//			return Title::newFromLinkTarget($linkTarget);
			return null;
		}

//		/**
//		* Set the title of the revision
//		*
//		* @deprecated since 1.31, this is now a noop. Pass the Title to the constructor instead.
//		*
//		* @param Title $title
//		*/
//		public function setTitle($title) {
//			if (!$title.equals(this.getTitle())) {
//				throw new InvalidArgumentException(
//					$title.getPrefixedText()
//						. ' is not the same as '
//						. this.mRecord.getPageAsLinkTarget().__toString()
//				);
//			}
//		}

		/**
		* Get the page ID
		*
		* @return int|null
		*/
		public int getPage() {
			return this.mRecord.getPageId();
		}

//		/**
//		* Fetch revision's user id if it's available to the specified audience.
//		* If the specified audience does not have access to it, zero will be
//		* returned.
//		*
//		* @param int $audience One of:
//		*   Revision::FOR_PUBLIC       to be displayed to all users
//		*   Revision::FOR_THIS_USER    to be displayed to the given user
//		*   Revision::RAW              get the ID regardless of permissions
//		* @param User|null $user User Object to check for, only if FOR_THIS_USER is passed
//		*   to the $audience parameter
//		* @return int
//		*/
//		public function getUser($audience = self::FOR_PUBLIC, User $user = null) {
//			global $wgUser;
//
//			if ($audience === self::FOR_THIS_USER && !$user) {
//				$user = $wgUser;
//			}
//
//			$user = this.mRecord.getUser($audience, $user);
//			return $user ? $user.getId() : 0;
//		}
//
//		/**
//		* Fetch revision's username if it's available to the specified audience.
//		* If the specified audience does not have access to the username, an
//		* empty String will be returned.
//		*
//		* @param int $audience One of:
//		*   Revision::FOR_PUBLIC       to be displayed to all users
//		*   Revision::FOR_THIS_USER    to be displayed to the given user
//		*   Revision::RAW              get the text regardless of permissions
//		* @param User|null $user User Object to check for, only if FOR_THIS_USER is passed
//		*   to the $audience parameter
//		* @return String
//		*/
//		public function getUserText($audience = self::FOR_PUBLIC, User $user = null) {
//			global $wgUser;
//
//			if ($audience === self::FOR_THIS_USER && !$user) {
//				$user = $wgUser;
//			}
//
//			$user = this.mRecord.getUser($audience, $user);
//			return $user ? $user.getName() : '';
//		}
//
//		/**
//		* @param int $audience One of:
//		*   Revision::FOR_PUBLIC       to be displayed to all users
//		*   Revision::FOR_THIS_USER    to be displayed to the given user
//		*   Revision::RAW              get the text regardless of permissions
//		* @param User|null $user User Object to check for, only if FOR_THIS_USER is passed
//		*   to the $audience parameter
//		*
//		* @return String|null Returns null if the specified audience does not have access to the
//		*  comment.
//		*/
//		function getComment($audience = self::FOR_PUBLIC, User $user = null) {
//			global $wgUser;
//
//			if ($audience === self::FOR_THIS_USER && !$user) {
//				$user = $wgUser;
//			}
//
//			$comment = this.mRecord.getComment($audience, $user);
//			return $comment === null ? null : $comment.text;
//		}
//
//		/**
//		* @return boolean
//		*/
//		public function isMinor() {
//			return this.mRecord.isMinor();
//		}
//
//		/**
//		* @return int Rcid of the unpatrolled row, zero if there isn't one
//		*/
//		public function isUnpatrolled() {
//			return self::getRevisionStore().getRcIdIfUnpatrolled(this.mRecord);
//		}
//
//		/**
//		* Get the RC Object belonging to the current revision, if there's one
//		*
//		* @param int $flags (optional) $flags include:
//		*      Revision::READ_LATEST  : Select the data from the master
//		*
//		* @since 1.22
//		* @return RecentChange|null
//		*/
//		public function getRecentChange($flags = 0) {
//			return self::getRevisionStore().getRecentChange(this.mRecord, $flags);
//		}
//
//		/**
//		* @param int $field One of DELETED_* bitfield constants
//		*
//		* @return boolean
//		*/
//		public function isDeleted($field) {
//			return this.mRecord.isDeleted($field);
//		}
//
//		/**
//		* Get the deletion bitfield of the revision
//		*
//		* @return int
//		*/
//		public function getVisibility() {
//			return this.mRecord.getVisibility();
//		}

		/**
		* Fetch revision content if it's available to the specified audience.
		* If the specified audience does not have the ability to view this
		* revision, or the content could not be loaded, null will be returned.
		*
		* @param int $audience One of:
		*   Revision::FOR_PUBLIC       to be displayed to all users
		*   Revision::FOR_THIS_USER    to be displayed to $user
		*   Revision::RAW              get the text regardless of permissions
		* @param User|null $user User Object to check for, only if FOR_THIS_USER is passed
		*   to the $audience parameter
		* @since 1.21
		* @return Content|null
		*/
		public XomwContent getContent() {return getContent(FOR_PUBLIC, null);}
		public XomwContent getContent(int audience, XomwUser user) {
//			global $wgUser;
//
//			if ($audience === self::FOR_THIS_USER && !$user) {
//				$user = $wgUser;
//			}
//
//			try {
//				return this.mRecord.getContent(SlotRecord::MAIN, $audience, $user);
//			}
//			catch (RevisionAccessException $e) {
//				return null;
//			}
			return null;
		}

//		/**
//		* Get original serialized data (without checking view restrictions)
//		*
//		* @since 1.21
//		* @deprecated since 1.31, use BlobStore::getBlob instead.
//		*
//		* @return String
//		*/
//		public function getSerializedData() {
//			$slot = this.getMainSlotRaw();
//			return $slot.getContent().serialize();
//		}
//
//		/**
//		* Returns the content model for the main slot of this revision.
//		*
//		* If no content model was stored in the database, the default content model for the title is
//		* used to determine the content model to use. If no title is know, CONTENT_MODEL_WIKITEXT
//		* is used as a last resort.
//		*
//		* @todo drop this, with MCR, there no longer is a single model associated with a revision.
//		*
//		* @return String The content model id associated with this revision,
//		*     see the CONTENT_MODEL_XXX constants.
//		*/
//		public function getContentModel() {
//			return this.getMainSlotRaw().getModel();
//		}
//
//		/**
//		* Returns the content format for the main slot of this revision.
//		*
//		* If no content format was stored in the database, the default format for this
//		* revision's content model is returned.
//		*
//		* @todo drop this, the format is irrelevant to the revision!
//		*
//		* @return String The content format id associated with this revision,
//		*     see the CONTENT_FORMAT_XXX constants.
//		*/
//		public function getContentFormat() {
//			$format = this.getMainSlotRaw().getFormat();
//
//			if ($format === null) {
//				// if no format was stored along with the blob, fall back to default format
//				$format = this.getContentHandler().getDefaultFormat();
//			}
//
//			return $format;
//		}
//
//		/**
//		* Returns the content handler appropriate for this revision's content model.
//		*
//		* @throws MWException
//		* @return ContentHandler
//		*/
//		public function getContentHandler() {
//			return ContentHandler::getForModelID(this.getContentModel());
//		}
//
//		/**
//		* @return String
//		*/
//		public function getTimestamp() {
//			return this.mRecord.getTimestamp();
//		}
//
//		/**
//		* @return boolean
//		*/
//		public function isCurrent() {
//			return (this.mRecord instanceof RevisionStoreRecord) && this.mRecord.isCurrent();
//		}
//
//		/**
//		* Get previous revision for this title
//		*
//		* @return Revision|null
//		*/
//		public function getPrevious() {
//			$title = this.getTitle();
//			$rec = self::getRevisionLookup().getPreviousRevision(this.mRecord, $title);
//			return $rec ? new Revision($rec, self::READ_NORMAL, $title) : null;
//		}
//
//		/**
//		* Get next revision for this title
//		*
//		* @return Revision|null
//		*/
//		public function getNext() {
//			$title = this.getTitle();
//			$rec = self::getRevisionLookup().getNextRevision(this.mRecord, $title);
//			return $rec ? new Revision($rec, self::READ_NORMAL, $title) : null;
//		}
//
//		/**
//		* Get revision text associated with an old or archive row
//		*
//		* If the text field is not included, this uses RevisionStore to load the appropriate slot
//		* and return its serialized content. This is the default backwards-compatibility behavior
//		* when reading from the MCR aware database schema is enabled. For this to work, either
//		* the revision ID or the page ID must be included in the row.
//		*
//		* When using the old text field, the flags field must also be set. Including the old_id
//		* field will activate cache usage as long as the $wiki parameter is not set.
//		*
//		* @deprecated since 1.32, use RevisionStore::newRevisionFromRow instead.
//		*
//		* @param stdClass $row The text data. If a falsy value is passed instead, false is returned.
//		* @param String $prefix Table prefix (default 'old_')
//		* @param String|boolean $wiki The name of the wiki to load the revision text from
//		*   (same as the wiki $row was loaded from) or false to indicate the local
//		*   wiki (this is the default). Otherwise, it must be a symbolic wiki database
//		*   identifier as understood by the LoadBalancer class.
//		* @return String|false Text the text requested or false on failure
//		*/
//		public static function getRevisionText($row, $prefix = 'old_', $wiki = false) {
//			global $wgMultiContentRevisionSchemaMigrationStage;
//
//			if (!$row) {
//				return false;
//			}
//
//			$textField = $prefix . 'text';
//			$flagsField = $prefix . 'flags';
//
//			if (isset($row.$textField)) {
//				if (!($wgMultiContentRevisionSchemaMigrationStage & SCHEMA_COMPAT_WRITE_OLD)) {
//					// The text field was read, but it's no longer being populated!
//					// We could gloss over this by using the text when it's there and loading
//					// if when it's not, but it seems preferable to complain loudly about a
//					// query that is no longer guaranteed to work reliably.
//					throw new LogicException(
//						'Cannot use ' . __METHOD__ . ' with the ' . $textField . ' field when'
//						. ' $wgMultiContentRevisionSchemaMigrationStage does not include'
//						. ' SCHEMA_COMPAT_WRITE_OLD. The field may not be populated for all revisions!'
//					);
//				}
//
//				$text = $row.$textField;
//			} else {
//				// Missing text field, we are probably looking at the MCR-enabled DB schema.
//
//				if (!($wgMultiContentRevisionSchemaMigrationStage & SCHEMA_COMPAT_WRITE_OLD)) {
//					// This method should no longer be used with the new schema. Ideally, we
//					// would already trigger a deprecation warning when SCHEMA_COMPAT_READ_NEW is set.
//					wfDeprecated(__METHOD__ . ' (MCR without SCHEMA_COMPAT_WRITE_OLD)', '1.32');
//				}
//
//				$store = self::getRevisionStore($wiki);
//				$rev = $prefix === 'ar_'
//					? $store.newRevisionFromArchiveRow($row)
//					: $store.newRevisionFromRow($row);
//
//				$content = $rev.getContent(SlotRecord::MAIN);
//				return $content ? $content.serialize() : false;
//			}
//
//			if (isset($row.$flagsField)) {
//				$flags = explode(',', $row.$flagsField);
//			} else {
//				$flags = [];
//			}
//
//			$cacheKey = isset($row.old_id)
//				? SqlBlobStore::makeAddressFromTextId($row.old_id)
//				: null;
//
//			$revisionText = self::getBlobStore($wiki).expandBlob($text, $flags, $cacheKey);
//
//			if ($revisionText === false) {
//				if (isset($row.old_id)) {
//					wfLogWarning(__METHOD__ . ": Bad data in text row {$row.old_id}! ");
//				} else {
//					wfLogWarning(__METHOD__ . ": Bad data in text row! ");
//				}
//				return false;
//			}
//
//			return $revisionText;
//		}
//
//		/**
//		* If $wgCompressRevisions is enabled, we will compress data.
//		* The input String is modified in place.
//		* Return value is the flags field: contains 'gzip' if the
//		* data is compressed, and 'utf-8' if we're saving in UTF-8
//		* mode.
//		*
//		* @param mixed &$text Reference to a text
//		* @return String
//		*/
//		public static function compressRevisionText(&$text) {
//			return self::getBlobStore().compressData($text);
//		}
//
//		/**
//		* Re-converts revision text according to it's flags.
//		*
//		* @param mixed $text Reference to a text
//		* @param array $flags Compression flags
//		* @return String|boolean Decompressed text, or false on failure
//		*/
//		public static function decompressRevisionText($text, $flags) {
//			if ($text === false) {
//				// Text failed to be fetched; nothing to do
//				return false;
//			}
//
//			return self::getBlobStore().decompressData($text, $flags);
//		}
//
//		/**
//		* Insert a new revision into the database, returning the new revision ID
//		* number on success and dies horribly on failure.
//		*
//		* @param IDatabase $dbw (master connection)
//		* @throws MWException
//		* @return int The revision ID
//		*/
//		public function insertOn($dbw) {
//			global $wgUser;
//
//			// Note that this.mRecord.getId() will typically return null here, but not always,
//			// e.g. not when restoring a revision.
//
//			if (this.mRecord.getUser(RevisionRecord::RAW) === null) {
//				if (this.mRecord instanceof MutableRevisionRecord) {
//					this.mRecord.setUser($wgUser);
//				} else {
//					throw new MWException('Cannot insert revision with no associated user.');
//				}
//			}
//
//			$rec = self::getRevisionStore().insertRevisionOn(this.mRecord, $dbw);
//
//			this.mRecord = $rec;
//			Assert::postcondition(this.mRecord !== null, 'Failed to acquire a RevisionRecord');
//
//			return $rec.getId();
//		}
//
//		/**
//		* Get the super 36 SHA-1 value for a String of text
//		* @param String $text
//		* @return String
//		*/
//		public static function base36Sha1($text) {
//			return SlotRecord::base36Sha1($text);
//		}
//
//		/**
//		* Create a new null-revision for insertion into a page's
//		* history. This will not re-save the text, but simply refer
//		* to the text from the previous version.
//		*
//		* Such revisions can for instance identify page rename
//		* operations and other such meta-modifications.
//		*
//		* @param IDatabase $dbw
//		* @param int $pageId ID number of the page to read from
//		* @param String $summary Revision's summary
//		* @param boolean $minor Whether the revision should be considered as minor
//		* @param User|null $user User Object to use or null for $wgUser
//		* @return Revision|null Revision or null on error
//		*/
//		public static function newNullRevision($dbw, $pageId, $summary, $minor, $user = null) {
//			global $wgUser;
//			if (!$user) {
//				$user = $wgUser;
//			}
//
//			$comment = CommentStoreComment::newUnsavedComment($summary, null);
//
//			$title = Title::newFromID($pageId, Title::GAID_FOR_UPDATE);
//			if ($title === null) {
//				return null;
//			}
//
//			$rec = self::getRevisionStore().newNullRevision($dbw, $title, $comment, $minor, $user);
//
//			return $rec ? new Revision($rec) : null;
//		}
//
//		/**
//		* Determine if the current user is allowed to view a particular
//		* field of this revision, if it's marked as deleted.
//		*
//		* @param int $field One of self::DELETED_TEXT,
//		*                              self::DELETED_COMMENT,
//		*                              self::DELETED_USER
//		* @param User|null $user User Object to check, or null to use $wgUser
//		* @return boolean
//		*/
//		public function userCan($field, User $user = null) {
//			return self::userCanBitfield(this.getVisibility(), $field, $user);
//		}
//
//		/**
//		* Determine if the current user is allowed to view a particular
//		* field of this revision, if it's marked as deleted. This is used
//		* by various classes to avoid duplication.
//		*
//		* @param int $bitfield Current field
//		* @param int $field One of self::DELETED_TEXT = File::DELETED_FILE,
//		*                               self::DELETED_COMMENT = File::DELETED_COMMENT,
//		*                               self::DELETED_USER = File::DELETED_USER
//		* @param User|null $user User Object to check, or null to use $wgUser
//		* @param Title|null $title A Title Object to check for per-page restrictions on,
//		*                          instead of just plain userrights
//		* @return boolean
//		*/
//		public static function userCanBitfield($bitfield, $field, User $user = null,
//			Title $title = null
//		) {
//			global $wgUser;
//
//			if (!$user) {
//				$user = $wgUser;
//			}
//
//			return RevisionRecord::userCanBitfield($bitfield, $field, $user, $title);
//		}
//
//		/**
//		* Get rev_timestamp from rev_id, without loading the rest of the row
//		*
//		* @param Title $title
//		* @param int $id
//		* @param int $flags
//		* @return String|boolean False if not found
//		*/
//		static function getTimestampFromId($title, $id, $flags = 0) {
//			return self::getRevisionStore().getTimestampFromId($title, $id, $flags);
//		}
//
//		/**
//		* Get count of revisions per page...not very efficient
//		*
//		* @param IDatabase $db
//		* @param int $id Page id
//		* @return int
//		*/
//		static function countByPageId($db, $id) {
//			return self::getRevisionStore().countRevisionsByPageId($db, $id);
//		}
//
//		/**
//		* Get count of revisions per page...not very efficient
//		*
//		* @param IDatabase $db
//		* @param Title $title
//		* @return int
//		*/
//		static function countByTitle($db, $title) {
//			return self::getRevisionStore().countRevisionsByTitle($db, $title);
//		}
//
//		/**
//		* Check if no edits were made by other users since
//		* the time a user started editing the page. Limit to
//		* 50 revisions for the sake of performance.
//		*
//		* @since 1.20
//		* @deprecated since 1.24
//		*
//		* @param IDatabase|int $db The Database to perform the check on. May be given as a
//		*        Database Object or a database identifier usable with wfGetDB.
//		* @param int $pageId The ID of the page in question
//		* @param int $userId The ID of the user in question
//		* @param String $since Look at edits since this time
//		*
//		* @return boolean True if the given user was the only one to edit since the given timestamp
//		*/
//		public static function userWasLastToEdit($db, $pageId, $userId, $since) {
//			if (is_int($db)) {
//				$db = wfGetDB($db);
//			}
//
//			return self::getRevisionStore().userWasLastToEdit($db, $pageId, $userId, $since);
//		}

		/**
		* Load a revision based on a known page ID and current revision ID from the DB
		*
		* This method allows for the use of caching, though accessing anything that normally
		* requires permission checks (aside from the text) will trigger a small DB lookup.
		* The title will also be loaded if $pageIdOrTitle is an integer ID.
		*
		* @param IDatabase $db ignored!
		* @param int|Title $pageIdOrTitle Page ID or Title Object
		* @param int $revId Known current revision of this page. Determined automatically if not given.
		* @return Revision|boolean Returns false if missing
		* @since 1.28
		*/
		public static XomwRevision newKnownCurrent(XomwIDatabase $db, Object pageIdOrTitle, int revId) { //  $revId = 0
            XomwTitleOld title = XophpType_.instance_of(pageIdOrTitle, XomwTitleOld.class)
				? (XomwTitleOld)pageIdOrTitle
				: XomwTitleOld.newFromID(XophpInt_.cast(pageIdOrTitle));

			if (!XophpObject_.is_true(title)) {
				return null;
			}

//			$record = self::getRevisionLookup().getKnownCurrentRevision($title, $revId);
//			return $record ? new Revision($record) : false;
            return null;
		}
}
