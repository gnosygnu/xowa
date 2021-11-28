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
package gplx.xowa.mediawiki.includes.parsers.preprocessors_new; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:Preprocessor
/**
* @ingroup Parser
*/
// XOWA: TODO: change VIRTUAL back to ABSTRACT; WHEN: after adding _DOM classes
public abstract class XomwPPFrame {
	public static final int NO_ARGS = 1;
	public static final int NO_TEMPLATES = 2;
	public static final int STRIP_COMMENTS = 4;
	public static final int NO_IGNORE = 8;
	public static final int RECOVER_COMMENTS = 16;
	public static final int NO_TAGS = 32;

	public static final int RECOVER_ORIG = 59; // = 1|2|8|16|32 no constant expression support in PHP yet

	/** This constant exists when $indexOffset is supported in newChild() */
	public static final int SUPPORTS_INDEX_OFFSET = 1;

	// https://github.com/wikimedia/mediawiki/blob/ce615343ee8d192a65cf65f35f853c5ffcfad390/includes/parser/PPFrame.php#L25-L26
	public int depth;
	public XomwPPFrame parent;

	/**
	* Create a child frame
	*
	* @param array|boolean $args
	* @param boolean|Title $title
	* @param int $indexOffset A number subtracted from the index attributes of the arguments
	*
	* @return PPFrame
	*/
	public XomwPPFrame newChild(Object args, XomwTitleOld title, int indexOffset) {return null;}

	/**
	* Expand a document tree node, caching the result on its parent with the given key
	* @param String|int $key
	* @param String|PPNode $root
	* @param int $flags
	* @return String
	*/
	public String cachedExpand(String key, XomwPPNode root, int flags) {return null;}

	/**
	* Expand a document tree node
	* @param String|PPNode $root
	* @param int $flags
	* @return String
	*/
	public String expand(Object root) {return expand(root, 0);}
	public String expand(Object root, int flags) {return null;}

	/**
	* Implode with flags for expand()
	* @param String $sep
	* @param int $flags
	* @param String|PPNode $args,...
	* @return String
	*/
	public String implodeWithFlags(String sep, int flags, Object... args) {return null;}

	/**
	* Implode with no flags specified
	* @param String $sep
	* @param String|PPNode $args,...
	* @return String
	*/
	public String implode(String sep, Object... args) {return null;}

	/**
	* Makes an Object that, when expand()ed, will be the same as one obtained
	* with implode()
	* @param String $sep
	* @param String|PPNode $args,...
	* @return PPNode
	*/
	public XomwPPNode virtualImplode(String sep, Object... args) {return null;}

	/**
	* Virtual implode with brackets
	* @param String $start
	* @param String $sep
	* @param String $end
	* @param String|PPNode $args,...
	* @return PPNode
	*/
	public XomwPPNode virtualBracketedImplode(String start, String sep, String end, Object... args) {return null;}

	/**
	* Returns true if there are no arguments in this frame
	*
	* @return boolean
	*/
	public boolean isEmpty() {return false;}

	/**
	* Returns all arguments of this frame
	* @return array
	*/
	public XophpArray getArguments() {return null;}

	/**
	* Returns all numbered arguments of this frame
	* @return array
	*/
	public XophpArray getNumberedArguments() {return null;}

	/**
	* Returns all named arguments of this frame
	* @return array
	*/
	public XophpArray getNamedArguments() {return null;}

	/**
	* Get an argument to this frame by name
	* @param int|String $name
	* @return String|boolean
	*/
	public String getArgument(String name) {return null;}

	/**
	* Returns true if the infinite loop check is OK, false if a loop is detected
	*
	* @param Title $title
	* @return boolean
	*/
	public boolean loopCheck(XomwTitleOld title) {return false;}

	/**
	* Return true if the frame is a template frame
	* @return boolean
	*/
	public boolean isTemplate() {return false;}

	/**
	* Set the "volatile" flag.
	*
	* Note that this is somewhat of a "hack" in order to make extensions
	* with side effects (such as Cite) work with the PHP parser. New
	* extensions should be written in a way that they do not need this
	* function, because other parsers (such as Parsoid) are not guaranteed
	* to respect it, and it may be removed in the future.
	*
	* @param boolean $flag
	*/
	public void setVolatile(boolean flag) {}

	/**
	* Get the "volatile" flag.
	*
	* Callers should avoid caching the result of an expansion if it has the
	* volatile flag set.
	*
	* @see self::setVolatile()
	* @return boolean
	*/
	public boolean isVolatile() {return false;}

	/**
	* Get the TTL of the frame's output.
	*
	* This is the maximum amount of time, in seconds, that this frame's
	* output should be cached for. A value of null indicates that no
	* maximum has been specified.
	*
	* Note that this TTL only applies to caching frames as parts of pages.
	* It is not relevant to caching the entire rendered output of a page.
	*
	* @return int|null
	*/
	public int getTTL() {return 0;}

	/**
	* Set the TTL of the output of this frame and all of its ancestors.
	* Has no effect if the new TTL is greater than the one already set.
	* Note that it is the caller's responsibility to change the cache
	* expiry of the page as a whole, if such behavior is desired.
	*
	* @see self::getTTL()
	* @param int $ttl
	*/
	public void setTTL(int ttl) {}

	/**
	* Get a title of frame
	*
	* @return Title
	*/
	public XomwTitleOld getTitle() {return null;}
}
