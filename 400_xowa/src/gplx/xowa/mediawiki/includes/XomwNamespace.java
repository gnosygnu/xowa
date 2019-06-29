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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class XomwNamespace {
//		/**
//		* These namespaces should always be first-letter capitalized, now and
//		* forevermore. Historically, they could've probably been lowercased too,
//		* but some things are just too ingrained now. :)
//		*/
//		private static $alwaysCapitalizedNamespaces = [ NS_SPECIAL, NS_USER, NS_MEDIAWIKI ];
//
//		/**
//		* Throw an exception when trying to get the subject or talk page
//		* for a given namespace where it does not make sense.
//		* Special namespaces are defined in includes/Defines.php and have
//		* a value below 0 (ex: NS_SPECIAL = -1 , NS_MEDIA = -2)
//		*
//		* @param int $index
//		* @param String $method
//		*
//		* @throws MWException
//		* @return boolean
//		*/
//		private static function isMethodValidFor($index, $method) {
//			if ($index < NS_MAIN) {
//				throw new MWException("$method does not make any sense for given namespace $index");
//			}
//			return true;
//		}
//
//		/**
//		* Can pages in the given namespace be moved?
//		*
//		* @param int $index Namespace index
//		* @return boolean
//		*/
//		public static function isMovable($index) {
//			global $wgAllowImageMoving;
//
//			$result = !($index < NS_MAIN || ($index == NS_FILE && !$wgAllowImageMoving));
//
//			/**
//			* @since 1.20
//			*/
//			Hooks::run('NamespaceIsMovable', [ $index, &$result ]);
//
//			return $result;
//		}
//
//		/**
//		* Is the given namespace is a subject (non-talk) namespace?
//		*
//		* @param int $index Namespace index
//		* @return boolean
//		* @since 1.19
//		*/
//		public static function isSubject($index) {
//			return !self::isTalk($index);
//		}
//
//		/**
//		* Is the given namespace a talk namespace?
//		*
//		* @param int $index Namespace index
//		* @return boolean
//		*/
//		public static function isTalk($index) {
//			return $index > NS_MAIN
//				&& $index % 2;
//		}
//
//		/**
//		* Get the talk namespace index for a given namespace
//		*
//		* @param int $index Namespace index
//		* @return int
//		*/
//		public static function getTalk($index) {
//			self::isMethodValidFor($index, __METHOD__);
//			return self::isTalk($index)
//				? $index
//				: $index + 1;
//		}
//
//		/**
//		* Get the subject namespace index for a given namespace
//		* Special namespaces (NS_MEDIA, NS_SPECIAL) are always the subject.
//		*
//		* @param int $index Namespace index
//		* @return int
//		*/
//		public static function getSubject($index) {
//			# Handle special namespaces
//			if ($index < NS_MAIN) {
//				return $index;
//			}
//
//			return self::isTalk($index)
//				? $index - 1
//				: $index;
//		}
//
//		/**
//		* Get the associated namespace.
//		* For talk namespaces, returns the subject (non-talk) namespace
//		* For subject (non-talk) namespaces, returns the talk namespace
//		*
//		* @param int $index Namespace index
//		* @return int|null If no associated namespace could be found
//		*/
//		public static function getAssociated($index) {
//			self::isMethodValidFor($index, __METHOD__);
//
//			if (self::isSubject($index)) {
//				return self::getTalk($index);
//			} elseif (self::isTalk($index)) {
//				return self::getSubject($index);
//			} else {
//				return null;
//			}
//		}
//
//		/**
//		* Returns whether the specified namespace exists
//		*
//		* @param int $index
//		*
//		* @return boolean
//		* @since 1.19
//		*/
//		public static function exists($index) {
//			$nslist = self::getCanonicalNamespaces();
//			return isset($nslist[$index]);
//		}
//
//		/**
//		* Returns whether the specified namespaces are the same namespace
//		*
//		* @note It's possible that in the future we may start using something
//		* other than just namespace indexes. Under that circumstance making use
//		* of this function rather than directly doing comparison will make
//		* sure that code will not potentially break.
//		*
//		* @param int $ns1 The first namespace index
//		* @param int $ns2 The second namespace index
//		*
//		* @return boolean
//		* @since 1.19
//		*/
//		public static function equals($ns1, $ns2) {
//			return $ns1 == $ns2;
//		}
//
//		/**
//		* Returns whether the specified namespaces share the same subject.
//		* eg: NS_USER and NS_USER wil return true, as well
//		*     NS_USER and NS_USER_TALK will return true.
//		*
//		* @param int $ns1 The first namespace index
//		* @param int $ns2 The second namespace index
//		*
//		* @return boolean
//		* @since 1.19
//		*/
//		public static function subjectEquals($ns1, $ns2) {
//			return self::getSubject($ns1) == self::getSubject($ns2);
//		}

	/**
	* Returns array of all defined namespaces with their canonical
	* (English) names.
	*
	* @param boolean $rebuild Rebuild namespace list (default = false). Used for testing.
	*
	* @return array
	* @since 1.17
	*/
	private static XomwNamespacesById namespaces = null;
	public static XomwNamespacesById getCanonicalNamespaces() {return getCanonicalNamespaces(false);}
	public static XomwNamespacesById getCanonicalNamespaces(boolean rebuild) {
		if (namespaces == null || rebuild) {
//				global $wgExtraNamespaces, $wgCanonicalNamespaceNames;
			namespaces = XomwSetup.wgCanonicalNamespaceNames.Clone();
			namespaces.Add(XomwDefines.NS_MAIN, "");

//				// Add extension namespaces
//				$namespaces += ExtensionRegistry::getInstance()->getAttribute('ExtensionNamespaces');
//				if (is_array($wgExtraNamespaces)) {
//					$namespaces += $wgExtraNamespaces;
//				}
//				Hooks::run('CanonicalNamespaces', [ &$namespaces ]);
		}
		return namespaces;
	}

//		/**
//		* Returns the canonical (English) name for a given index
//		*
//		* @param int $index Namespace index
//		* @return String|boolean If no canonical definition.
//		*/
//		public static function getCanonicalName($index) {
//			$nslist = self::getCanonicalNamespaces();
//			if (isset($nslist[$index])) {
//				return $nslist[$index];
//			} else {
//				return false;
//			}
//		}

	/**
	* Returns the index for a given canonical name, or NULL
	* The input *must* be converted to lower case first
	*
	* @param String $name Namespace name
	* @return int
	*/
	private static Hash_adp xNamespaces = null;
	public static int getCanonicalIndex(byte[] name) {
		if (xNamespaces == null) {
			xNamespaces = Hash_adp_bry.cs();
			XomwNamespacesById namespacesHash = getCanonicalNamespaces();
			int len = namespacesHash.Len();
			for (int i = 0; i < len; i++) {
				XomwNamespaceItem item = (XomwNamespaceItem)namespacesHash.GetAtOrNull(i);
				xNamespaces.Add(Bry_.Lcase__all(item.name), item);	// NOTE: MW does "strtolower($text)"; canonical namespaces are always ascii
			}
		}
		XomwNamespaceItem xNs = (XomwNamespaceItem)xNamespaces.Get_by(name);
		if (xNs != null) {
			return xNs.id;
		}
		else {
			return XomwNamespace.NULL_NS_ID;
		}
	}

//		/**
//		* Returns an array of the namespaces (by integer id) that exist on the
//		* wiki. Used primarily by the api in help documentation.
//		* @return array
//		*/
//		public static function getValidNamespaces() {
//			static $mValidNamespaces = null;
//
//			if (is_null($mValidNamespaces)) {
//				foreach (array_keys(self::getCanonicalNamespaces()) as $ns) {
//					if ($ns >= 0) {
//						$mValidNamespaces[] = $ns;
//					}
//				}
//				// T109137: sort numerically
//				sort($mValidNamespaces, SORT_NUMERIC);
//			}
//
//			return $mValidNamespaces;
//		}
//
//		/**
//		* Can this namespace ever have a talk namespace?
//		*
//		* @param int $index Namespace index
//		* @return boolean
//		*/
//		public static function canTalk($index) {
//			return $index >= NS_MAIN;
//		}
//
//		/**
//		* Does this namespace contain content, for the purposes of calculating
//		* statistics, etc?
//		*
//		* @param int $index Index to check
//		* @return boolean
//		*/
//		public static function isContent($index) {
//			global $wgContentNamespaces;
//			return $index == NS_MAIN || in_array($index, $wgContentNamespaces);
//		}
//
//		/**
//		* Might pages in this namespace require the use of the Signature button on
//		* the edit toolbar?
//		*
//		* @param int $index Index to check
//		* @return boolean
//		*/
//		public static function wantSignatures($index) {
//			global $wgExtraSignatureNamespaces;
//			return self::isTalk($index) || in_array($index, $wgExtraSignatureNamespaces);
//		}
//
//		/**
//		* Can pages in a namespace be watched?
//		*
//		* @param int $index
//		* @return boolean
//		*/
//		public static function isWatchable($index) {
//			return $index >= NS_MAIN;
//		}
//
//		/**
//		* Does the namespace allow subpages?
//		*
//		* @param int $index Index to check
//		* @return boolean
//		*/
//		public static function hasSubpages($index) {
//			global $wgNamespacesWithSubpages;
//			return !empty($wgNamespacesWithSubpages[$index]);
//		}
//
//		/**
//		* Get a list of all namespace indices which are considered to contain content
//		* @return array Array of namespace indices
//		*/
//		public static function getContentNamespaces() {
//			global $wgContentNamespaces;
//			if (!is_array($wgContentNamespaces) || $wgContentNamespaces == []) {
//				return [ NS_MAIN ];
//			} elseif (!in_array(NS_MAIN, $wgContentNamespaces)) {
//				// always force NS_MAIN to be part of array (to match the algorithm used by isContent)
//				return array_merge([ NS_MAIN ], $wgContentNamespaces);
//			} else {
//				return $wgContentNamespaces;
//			}
//		}
//
//		/**
//		* List all namespace indices which are considered subject, aka not a talk
//		* or special namespace. See also XomwNamespace::isSubject
//		*
//		* @return array Array of namespace indices
//		*/
//		public static function getSubjectNamespaces() {
//			return array_filter(
//				XomwNamespace::getValidNamespaces(),
//				'XomwNamespace::isSubject'
//			);
//		}
//
//		/**
//		* List all namespace indices which are considered talks, aka not a subject
//		* or special namespace. See also XomwNamespace::isTalk
//		*
//		* @return array Array of namespace indices
//		*/
//		public static function getTalkNamespaces() {
//			return array_filter(
//				XomwNamespace::getValidNamespaces(),
//				'XomwNamespace::isTalk'
//			);
//		}
//
//		/**
//		* Is the namespace first-letter capitalized?
//		*
//		* @param int $index Index to check
//		* @return boolean
//		*/
//		public static function isCapitalized($index) {
//			global $wgCapitalLinks, $wgCapitalLinkOverrides;
//			// Turn NS_MEDIA into NS_FILE
//			$index = $index == NS_MEDIA ? NS_FILE : $index;
//
//			// Make sure to get the subject of our namespace
//			$index = self::getSubject($index);
//
//			// Some namespaces are special and should always be upper case
//			if (in_array($index, self::$alwaysCapitalizedNamespaces)) {
//				return true;
//			}
//			if (isset($wgCapitalLinkOverrides[$index])) {
//				// $wgCapitalLinkOverrides is explicitly set
//				return $wgCapitalLinkOverrides[$index];
//			}
//			// Default to the global setting
//			return $wgCapitalLinks;
//		}
//
//		/**
//		* Does the namespace (potentially) have different aliases for different
//		* genders. Not all languages make a distinction here.
//		*
//		* @since 1.18
//		* @param int $index Index to check
//		* @return boolean
//		*/
//		public static function hasGenderDistinction($index) {
//			return $index == NS_USER || $index == NS_USER_TALK;
//		}
//
//		/**
//		* It is not possible to use pages from this namespace as template?
//		*
//		* @since 1.20
//		* @param int $index Index to check
//		* @return boolean
//		*/
//		public static function isNonincludable($index) {
//			global $wgNonincludableNamespaces;
//			return $wgNonincludableNamespaces && in_array($index, $wgNonincludableNamespaces);
//		}
//
//		/**
//		* Get the default content model for a namespace
//		* This does not mean that all pages in that namespace have the model
//		*
//		* @since 1.21
//		* @param int $index Index to check
//		* @return null|String Default model name for the given namespace, if set
//		*/
//		public static function getNamespaceContentModel($index) {
//			global $wgNamespaceContentModels;
//			return isset($wgNamespaceContentModels[$index])
//				? $wgNamespaceContentModels[$index]
//				: null;
//		}
//
//		/**
//		* Determine which restriction levels it makes sense to use in a namespace,
//		* optionally filtered by a user's rights.
//		*
//		* @since 1.23
//		* @param int $index Index to check
//		* @param User $user User to check
//		* @return array
//		*/
//		public static function getRestrictionLevels($index, User $user = null) {
//			global $wgNamespaceProtection, $wgRestrictionLevels;
//
//			if (!isset($wgNamespaceProtection[$index])) {
//				// All levels are valid if there's no namespace restriction.
//				// But still filter by user, if necessary
//				$levels = $wgRestrictionLevels;
//				if ($user) {
//					$levels = array_values(array_filter($levels, function ($level) use ($user) {
//						$right = $level;
//						if ($right == 'sysop') {
//							$right = 'editprotected'; // BC
//						}
//						if ($right == 'autoconfirmed') {
//							$right = 'editsemiprotected'; // BC
//						}
//						return ($right == '' || $user->isAllowed($right));
//					}));
//				}
//				return $levels;
//			}
//
//			// First, get the list of groups that can edit this namespace.
//			$namespaceGroups = [];
//			$combine = 'array_merge';
//			foreach ((array)$wgNamespaceProtection[$index] as $right) {
//				if ($right == 'sysop') {
//					$right = 'editprotected'; // BC
//				}
//				if ($right == 'autoconfirmed') {
//					$right = 'editsemiprotected'; // BC
//				}
//				if ($right != '') {
//					$namespaceGroups = call_user_func($combine, $namespaceGroups,
//						User::getGroupsWithPermission($right));
//					$combine = 'array_intersect';
//				}
//			}
//
//			// Now, keep only those restriction levels where there is at least one
//			// group that can edit the namespace but would be blocked by the
//			// restriction.
//			$usableLevels = [ '' ];
//			foreach ($wgRestrictionLevels as $level) {
//				$right = $level;
//				if ($right == 'sysop') {
//					$right = 'editprotected'; // BC
//				}
//				if ($right == 'autoconfirmed') {
//					$right = 'editsemiprotected'; // BC
//				}
//				if ($right != '' && (!$user || $user->isAllowed($right)) &&
//					array_diff($namespaceGroups, User::getGroupsWithPermission($right))
//				) {
//					$usableLevels[] = $level;
//				}
//			}
//
//			return $usableLevels;
//		}
	public static final int NULL_NS_ID = XophpUtility.NULL_INT;
}
