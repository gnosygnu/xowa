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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
///**
// * Expansion frame with template arguments
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
// extends PPFrame_Hash
class XomwPPTemplateFrame_Hash extends XomwPPFrame { //	// @codingStandardsIgnoreEnd
//
//	public $numberedArgs, $namedArgs, $parent;
//	public $numberedExpansionCache, $namedExpansionCache;
//
//	/**
//	 * @param Preprocessor $preprocessor
//	 * @param boolean|PPFrame $parent
//	 * @param array $numberedArgs
//	 * @param array $namedArgs
//	 * @param boolean|Title $title
//	 */
//	public function __construct($preprocessor, $parent = false, $numberedArgs = [],
//		$namedArgs = [], $title = false
//	) {
//		parent::__construct($preprocessor);
//
//		this.parent = $parent;
//		this.numberedArgs = $numberedArgs;
//		this.namedArgs = $namedArgs;
//		this.title = $title;
//		$pdbk = $title ? $title.getPrefixedDBkey() : false;
//		this.titleCache = $parent.titleCache;
//		this.titleCache[] = $pdbk;
//		this.loopCheckHash = /*clone*/ $parent.loopCheckHash;
//		if ($pdbk !== false) {
//			this.loopCheckHash[$pdbk] = true;
//		}
//		this.depth = $parent.depth + 1;
//		this.numberedExpansionCache = this.namedExpansionCache = [];
//	}
//
//	public function __toString() {
//		$s = 'tplframe{';
//		$first = true;
//		$args = this.numberedArgs + this.namedArgs;
//		for each ($args as $name => $value) {
//			if ($first) {
//				$first = false;
//			} else {
//				$s .= ', ';
//			}
//			$s .= "\"$name\":\"" .
//				str_replace('"', '\\"', $value.__toString()) . '"';
//		}
//		$s .= '}';
//		return $s;
//	}
//
//	/**
//	 * @throws MWException
//	 * @param String|int $key
//	 * @param String|PPNode $root
//	 * @param int $flags
//	 * @return String
//	 */
//	public function cachedExpand($key, $root, $flags = 0) {
//		if (isset(this.parent.childExpansionCache[$key])) {
//			return this.parent.childExpansionCache[$key];
//		}
//		$retval = this.expand($root, $flags);
//		if (!this.isVolatile()) {
//			this.parent.childExpansionCache[$key] = $retval;
//		}
//		return $retval;
//	}
//
//	/**
//	 * Returns true if there are no arguments in this frame
//	 *
//	 * @return boolean
//	 */
//	public function isEmpty() {
//		return !count(this.numberedArgs) && !count(this.namedArgs);
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getArguments() {
//		$arguments = [];
//		for each (array_merge(
//				array_keys(this.numberedArgs),
//				array_keys(this.namedArgs)) as $key) {
//			$arguments[$key] = this.getArgument($key);
//		}
//		return $arguments;
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getNumberedArguments() {
//		$arguments = [];
//		for each (array_keys(this.numberedArgs) as $key) {
//			$arguments[$key] = this.getArgument($key);
//		}
//		return $arguments;
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getNamedArguments() {
//		$arguments = [];
//		for each (array_keys(this.namedArgs) as $key) {
//			$arguments[$key] = this.getArgument($key);
//		}
//		return $arguments;
//	}
//
//	/**
//	 * @param int $index
//	 * @return String|boolean
//	 */
//	public function getNumberedArgument($index) {
//		if (!isset(this.numberedArgs[$index])) {
//			return false;
//		}
//		if (!isset(this.numberedExpansionCache[$index])) {
//			# No trimming for unnamed arguments
//			this.numberedExpansionCache[$index] = this.parent.expand(
//				this.numberedArgs[$index],
//				PPFrame::STRIP_COMMENTS
//			);
//		}
//		return this.numberedExpansionCache[$index];
//	}
//
//	/**
//	 * @param String $name
//	 * @return String|boolean
//	 */
//	public function getNamedArgument($name) {
//		if (!isset(this.namedArgs[$name])) {
//			return false;
//		}
//		if (!isset(this.namedExpansionCache[$name])) {
//			# Trim named arguments post-expand, for backwards compatibility
//			this.namedExpansionCache[$name] = trim(
//				this.parent.expand(this.namedArgs[$name], PPFrame::STRIP_COMMENTS));
//		}
//		return this.namedExpansionCache[$name];
//	}
//
//	/**
//	 * @param int|String $name
//	 * @return String|boolean
//	 */
//	public function getArgument($name) {
//		$text = this.getNumberedArgument($name);
//		if ($text === false) {
//			$text = this.getNamedArgument($name);
//		}
//		return $text;
//	}
//
//	/**
//	 * Return true if the frame is a template frame
//	 *
//	 * @return boolean
//	 */
//	public function isTemplate() {
//		return true;
//	}
//
//	public function setVolatile($flag = true) {
//		parent::setVolatile($flag);
//		this.parent.setVolatile($flag);
//	}
//
//	public function setTTL($ttl) {
//		parent::setTTL($ttl);
//		this.parent.setTTL($ttl);
//	}
}
