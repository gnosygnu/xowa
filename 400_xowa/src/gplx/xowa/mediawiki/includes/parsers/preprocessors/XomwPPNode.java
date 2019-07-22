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
// MW.FILE:Preprocessor
/**
* There are three types of nodes:
*     * Tree nodes, which have a name and contain other nodes as children
*     * Array nodes, which also contain other nodes but aren't considered part of a tree
*     * Leaf nodes, which contain the actual data
*
* This interface provides access to the tree structure and to the contents of array nodes,
* but it does not provide access to the @gplx.Internal protected structure of leaf nodes. Access to leaf
* data is provided via two means:
*     * PPFrame::expand(), which provides expanded text
*     * The PPNode::split*() functions, which provide metadata about certain types of tree node
* @ingroup Parser
*/
public abstract class XomwPPNode {
	/**
	* Get an array-type node containing the children of this node.
	* Returns false if this is not a tree node.
	* @return PPNode
	*/
//		public abstract XomwPPNode[] getChildren();

	/**
	* Get the first child of a tree node. False if there isn't one.
	*
	* @return PPNode
	*/
	public abstract XomwPPNode getFirstChild();

	/**
	* Get the next sibling of any node. False if there isn't one
	* @return PPNode
	*/
	public abstract XomwPPNode getNextSibling();

	/**
	* Get all children of this tree node which have a given name.
	* Returns an array-type node, or false if this is not a tree node.
	* @param String $type
	* @return boolean|PPNode
	*/
//		public abstract XomwPPNode getChildrenOfType(String type);

	/**
	* Returns the length of the array, or false if this is not an array-type node
	*/
//		public abstract int getLength();

	/**
	* Returns an item of an array-type node
	* @param int $i
	* @return boolean|PPNode
	*/
//		public abstract XomwPPNode item(int i);

	/**
	* Get the name of this node. The following names are defined here:
	*
	*    h             A heading node.
	*    template      A double-brace node.
	*    tplarg        A triple-brace node.
	*    title         The first argument to a template or tplarg node.
	*    part          Subsequent arguments to a template or tplarg node.
	*    #nodelist     An array-type node
	*
	* The subclass may define various other names for tree and leaf nodes.
	* @return String
	*/
	public abstract String getName();

	/**
	* Split a "<part>" node into an associative array containing:
	*    name          PPNode name
	*    index         String index
	*    value         PPNode value
	* @return array
	*/
//		public abstract Hash_adp splitArg();

	/**
	* Split an "<ext>" node into an associative array containing name, attr, inner and close
	* All values in the resulting array are PPNodes. Inner and close are optional.
	* @return array
	*/
//		public abstract Hash_adp splitExt();

	/**
	* Split an "<h>" node
	* @return array
	*/
//		public abstract Hash_adp splitHeading();

	public abstract String toString();
}
