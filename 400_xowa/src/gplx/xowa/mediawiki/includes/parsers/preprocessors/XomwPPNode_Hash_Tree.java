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
// MW.FILE:Preprocessor_Hash
/**
* @ingroup Parser
*/
import gplx.xowa.mediawiki.includes.exception.*;
public class XomwPPNode_Hash_Tree extends XomwPPNode {	public final    String name;
	/**
	* The store array for children of this node. It is "raw" in the sense that
	* nodes are two-element arrays ("descriptors") rather than PPNode_Hash_*
	* objects.
	*/
	private final    XophpArray rawChildren;

	/**
	* The store array for the siblings of this node, including this node itself.
	*/
	private final    XophpArray store;

	/**
	* The index into this.store which contains the descriptor of this node.
	*/
	private final    int index;

	/**
	* The offset of the name within descriptors, used in some places for
	* readability.
	*/
	private static final int NAME = 0;

	/**
	* The offset of the child list within descriptors, used in some places for
	* readability.
	*/
//		private static final int CHILDREN = 1;

	/**
	* Construct an Object using the data from $store[$index]. The rest of the
	* store array can be accessed via getNextSibling().
	*
	* @param array $store
	* @param integer $index
	*/
	public XomwPPNode_Hash_Tree(XophpArray store, int index) {
		this.store = store;
		this.index = index;

		XophpArray list = this.store.Get_at_ary(index);
		this.name = list.Get_at_str(0);
		this.rawChildren = ((Xomw_prepro_accum__hash)(list.Get_at(1))).Ary();
	}

	/**
	* Construct an appropriate PPNode_Hash_* Object with a class that depends
	* on what is at the relevant store index.
	*
	* @param array $store
	* @param integer $index
	* @return PPNode_Hash_Tree|PPNode_Hash_Attr|PPNode_Hash_Text
	*/
	public static XomwPPNode factory(XophpArray store, int index) {
		Object descriptor = store.Get_at(index);
		if (!XophpUtility.isset_obj(descriptor)) {
			return null;
		}

		XophpType type = XophpType.New(descriptor);
		if (type.is_string()) {
			return new XomwPPNode_Hash_Text(store, index);
		}
		else if (type.is_array()) {
			XophpArray descriptor_array = (XophpArray)descriptor;
			XophpArray name_array = (XophpArray)(descriptor_array.Get_by(NAME));
			if (String_.Has_at_bgn(name_array.Get_at_str(0), "@")) {
				return new XomwPPNode_Hash_Attr(store, index);
			}
			else {
				return new XomwPPNode_Hash_Text(store, index);
			}
		}
		else {
			throw XomwMWException.New_by_method(XomwPPNode_Hash_Tree.class, "factory", "invalid node descriptor");
		}
	}

	/**
	* Convert a node to XML, for debugging
	*/
	@Override public String toString() {
		String inner = "";
		String attribs = "";
		for (XomwPPNode node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (Type_.Eq_by_obj(node, XomwPPNode_Hash_Attr.class)) {
				XomwPPNode_Hash_Attr node_attr = (XomwPPNode_Hash_Attr)node;
				attribs += "  " + node_attr.name + "=\"" + Bry_.Escape_html(Bry_.new_u8(node_attr.value)) + "\"";
			} else {
				inner += node.toString();
			}
		}
		if (String_.Eq(inner, "")) {
			return "<" + this.name + attribs + "/>";
		} else {
			return "<" + this.name + attribs + ">" + inner + "</" + this.name + ">";
		}
	}

//		/**
//		* @return PPNode_Hash_Array
//		*/
//		public function getChildren() {
//			$children = [];
//			foreach (this.rawChildren as $i => $child) {
//				$children[] = self::factory(this.rawChildren, $i);
//			}
//			return new PPNode_Hash_Array($children);
//		}

	/**
	* Get the first child, or false if there is none. Note that this will
	* return a temporary proxy Object: different instances will be returned
	* if this is called more than once on the same node.
	*
	* @return PPNode_Hash_Tree|PPNode_Hash_Attr|PPNode_Hash_Text|boolean
	*/
	@Override public XomwPPNode getFirstChild() {
		if (XophpArrayUtl.isset(this.rawChildren, 0)) {
			return null;
		}
		else {
			return factory(this.rawChildren, 0);
		}
	}

	/**
	* Get the next sibling, or false if there is none. Note that this will
	* return a temporary proxy Object: different instances will be returned
	* if this is called more than once on the same node.
	*
	* @return PPNode_Hash_Tree|PPNode_Hash_Attr|PPNode_Hash_Text|boolean
	*/
	@Override public XomwPPNode getNextSibling() {
		return factory(this.store, this.index + 1);
	}

//		/**
//		* Get an array of the children with a given node name
//		*
//		* @param String $name
//		* @return PPNode_Hash_Array
//		*/
//		public function getChildrenOfType($name) {
//			$children = [];
//			foreach (this.rawChildren as $i => $child) {
//				if (is_array($child) && $child[self::NAME] === $name) {
//					$children[] = self::factory(this.rawChildren, $i);
//				}
//			}
//			return new PPNode_Hash_Array($children);
//		}
//
//		/**
//		* Get the raw child array. For @gplx.Internal protected use.
//		* @return array
//		*/
//		public function getRawChildren() {
//			return this.rawChildren;
//		}
//
//		/**
//		* @return boolean
//		*/
//		public function getLength() {
//			return false;
//		}
//
//		/**
//		* @param int $i
//		* @return boolean
//		*/
//		public function item($i) {
//			return false;
//		}

	/**
	* @return String
	*/
	@Override public String getName() {
		return this.name;
	}

//		/**
//		* Split a "<part>" node into an associative array containing:
//		*  - name          PPNode name
//		*  - index         String index
//		*  - value         PPNode value
//		*
//		* @throws MWException
//		* @return array
//		*/
//		public function splitArg() {
//			return self::splitRawArg(this.rawChildren);
//		}
//
//		/**
//		* Like splitArg() but for a raw child array. For @gplx.Internal protected use only.
//		*/
//		public static function splitRawArg(array $children) {
//			$bits = [];
//			foreach ($children as $i => $child) {
//				if (!is_array($child)) {
//					continue;
//				}
//				if ($child[self::NAME] === 'name') {
//					$bits['name'] = new self($children, $i);
//					if (isset($child[self::CHILDREN][0][self::NAME])
//						&& $child[self::CHILDREN][0][self::NAME] === '@index'
//					) {
//						$bits['index'] = $child[self::CHILDREN][0][self::CHILDREN][0];
//					}
//				} elseif ($child[self::NAME] === 'value') {
//					$bits['value'] = new self($children, $i);
//				}
//			}
//
//			if (!isset($bits['name'])) {
//				throw new MWException('Invalid brace node passed to ' . __METHOD__);
//			}
//			if (!isset($bits['index'])) {
//				$bits['index'] = "";
//			}
//			return $bits;
//		}
//
//		/**
//		* Split an "<ext>" node into an associative array containing name, attr, inner and close
//		* All values in the resulting array are PPNodes. Inner and close are optional.
//		*
//		* @throws MWException
//		* @return array
//		*/
//		public function splitExt() {
//			return self::splitRawExt(this.rawChildren);
//		}
//
//		/**
//		* Like splitExt() but for a raw child array. For @gplx.Internal protected use only.
//		*/
//		public static function splitRawExt(array $children) {
//			$bits = [];
//			foreach ($children as $i => $child) {
//				if (!is_array($child)) {
//					continue;
//				}
//				switch ($child[self::NAME]) {
//				case 'name':
//					$bits['name'] = new self($children, $i);
//					break;
//				case 'attr':
//					$bits['attr'] = new self($children, $i);
//					break;
//				case 'inner':
//					$bits['inner'] = new self($children, $i);
//					break;
//				case 'close':
//					$bits['close'] = new self($children, $i);
//					break;
//				}
//			}
//			if (!isset($bits['name'])) {
//				throw new MWException('Invalid ext node passed to ' . __METHOD__);
//			}
//			return $bits;
//		}
//
//		/**
//		* Split an "<h>" node
//		*
//		* @throws MWException
//		* @return array
//		*/
//		public function splitHeading() {
//			if (this.name !== 'h') {
//				throw new MWException('Invalid h node passed to ' . __METHOD__);
//			}
//			return self::splitRawHeading(this.rawChildren);
//		}
//
//		/**
//		* Like splitHeading() but for a raw child array. For @gplx.Internal protected use only.
//		*/
//		public static function splitRawHeading(array $children) {
//			$bits = [];
//			foreach ($children as $i => $child) {
//				if (!is_array($child)) {
//					continue;
//				}
//				if ($child[self::NAME] === '@i') {
//					$bits['i'] = $child[self::CHILDREN][0];
//				} elseif ($child[self::NAME] === '@level') {
//					$bits['level'] = $child[self::CHILDREN][0];
//				}
//			}
//			if (!isset($bits['i'])) {
//				throw new MWException('Invalid h node passed to ' . __METHOD__);
//			}
//			return $bits;
//		}
//
//		/**
//		* Split a "<template>" or "<tplarg>" node
//		*
//		* @throws MWException
//		* @return array
//		*/
//		public function splitTemplate() {
//			return self::splitRawTemplate(this.rawChildren);
//		}
//
//		/**
//		* Like splitTemplate() but for a raw child array. For @gplx.Internal protected use only.
//		*/
//		public static function splitRawTemplate(array $children) {
//			$parts = [];
//			$bits = [ 'lineStart' => '' ];
//			foreach ($children as $i => $child) {
//				if (!is_array($child)) {
//					continue;
//				}
//				switch ($child[self::NAME]) {
//				case 'title':
//					$bits['title'] = new self($children, $i);
//					break;
//				case 'part':
//					$parts[] = new self($children, $i);
//					break;
//				case '@lineStart':
//					$bits['lineStart'] = '1';
//					break;
//				}
//			}
//			if (!isset($bits['title'])) {
//				throw new MWException('Invalid node passed to ' . __METHOD__);
//			}
//			$bits['parts'] = new PPNode_Hash_Array($parts);
//			return $bits;
//		}
}
