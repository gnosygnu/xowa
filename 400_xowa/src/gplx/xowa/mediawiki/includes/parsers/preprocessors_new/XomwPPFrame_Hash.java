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
import gplx.xowa.mediawiki.includes.exception.*;
import gplx.core.bits.*;
/**
* An expansion frame, used as a context to expand the result of preprocessToObj()
* @ingroup Parser
*/
public class XomwPPFrame_Hash extends XomwPPFrame { //		/**
//		* @var Parser
//		*/
//		public XomwParser parser;
//
//		/**
//		* @var Preprocessor
//		*/
//		public XomwPreprocessor preprocessor;
//
//		/**
//		* @var Title
//		*/
//		public XomwTitleOld title;
//		public XophpArray titleCache;
//
//		/**
//		* Hashtable listing templates which are disallowed for expansion in this frame,
//		* having been encountered previously in parent frames.
//		*/
//		public XophpArray loopCheckHash;
//
//		/**
//		* Recursion depth of this frame, top = 0
//		* Note that this is NOT the same as expansion depth in expand()
//		*/
//		@gplx.frameworks.objects.New public int depth;
//
//		private boolean volatile_bool;
//		private int ttl;
//
//		/**
//		* @var array
//		*/
//		public XophpArray childExpansionCache;
//
//		/**
//		* Construct a new preprocessor frame.
//		* @param Preprocessor preprocessor The parent preprocessor
//		*/
	public XomwPPFrame_Hash(XomwPreprocessor preprocessor) {
//			this.preprocessor = preprocessor;
//			this.parser = preprocessor.Parser();
//			this.title = this.parser.mTitle;
//			this.titleCache = XophpArray.New(XophpObject_.is_true(this.title) ? this.title.getPrefixedDBkeyStr() : XophpString_.False);
//			this.loopCheckHash = XophpArray.New();
//			this.depth = 0;
//			this.childExpansionCache = XophpArray.New();
	}
//
//		/**
//		* Create a new child frame
//		* $args is optionally a multi-root PPNode or array containing the template arguments
//		*
//		* @param array|boolean|PPNode_Hash_Array $args
//		* @param Title|boolean $title
//		* @param int $indexOffset
//		* @throws MWException
//		* @return PPTemplateFrame_Hash
//		*/
//		public override XomwPPFrame newChild(Object argsObj, XomwTitleOld title, int indexOffset) {
//			XophpArray namedArgs = XophpArray.New();
//			XophpArray numberedArgs = XophpArray.New();
//			if (!XophpObject_.is_true(title)) {
//				title = this.title;
//			}
//			if (XophpObject_.is_true(argsObj)) {
//				XophpArray args = null;
//				if (Type_.Eq_by_obj(argsObj, typeof(XomwPPNode_Hash_Array))) {
//					args = ((XomwPPNode_Hash_Array)argsObj).value;
//				} else if (!XophpArray.is_array(argsObj)) {
//					throw XomwMWException.New_by_method(typeof(XomwPPFrame_Hash), "newChild", ": args must be array or PPNode_Hash_Array");
//				}
//				else {
//					args = (XophpArray)argsObj;
//				}
//
//				int argsLen = args.count();
//				for (int i = 0; i < argsLen; i++) {
//					XomwPPNode arg = (XomwPPNode)args.Get_at(i);
//					XophpArray bits = arg.splitArg();
//					if (bits.Has("index")) {
//						// Numbered parameter
//						int index = bits.Get_by_int("index") - indexOffset;
//						if (namedArgs.isset(index) || numberedArgs.isset(index)) {
//						//	this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
//						//		wfEscapeWikiText(this.title),
//						//		wfEscapeWikiText(title),
//						//		wfEscapeWikiText(index)).text());
//						//	this.parser.addTrackingCategory('duplicate-args-category');
//						}
//						numberedArgs.Set(index, bits.Get_by("value"));
//						namedArgs.unset(index);
//					} else {
//						// Named parameter
//						String name = String_.Trim(this.expand(bits.Get_by("name"), XomwPPFrame.STRIP_COMMENTS));
//						if (namedArgs.isset(name) || numberedArgs.isset(name)) {
//						//	this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
//						//		wfEscapeWikiText(this.title),
//						//		wfEscapeWikiText(title),
//						//		wfEscapeWikiText(name)).text());
//						//	this.parser.addTrackingCategory('duplicate-args-category');
//						}
//						namedArgs.Set(name, bits.Get_by("value"));
//						numberedArgs.unset(name);
//					}
//				}
//			}
//			return new XomwPPTemplateFrame_Hash(this.preprocessor, this, numberedArgs, namedArgs, title);
//		}
//
//		/**
//		* @throws MWException
//		* @param String|int $key
//		* @param String|PPNode $root
//		* @param int $flags
//		* @return String
//		*/
//		public String cachedExpand(String key, Object root, int flags) { // DEFAULT:flags=0
//			// we don't have a parent, so we don't have a cache
//			return this.expand(root, flags);
//		}
//
//		private static int expansionDepth = 0; // MW.GLOBAL:expand
//		private static int expand_flags_default = 0;
//		/**
//		* @throws MWException
//		* @param String|PPNode $root
//		* @param int $flags
//		* @return String
//		*/
//		public override String expand(Object root, int flags) {
//			if (XophpString_.is_string(root)) {
//				return (String)root;
//			}
//
//			if (++this.parser.mPPNodeCount > this.parser.mOptions.getMaxPPNodeCount()) {
//				//	this.parser.limitationWarn('node-count-exceeded',
//				//			this.parser.mPPNodeCount,
//				//			this.parser.mOptions.getMaxPPNodeCount()
//				//	);
//				return "<span class=\"error\">Node-count limit exceeded</span>";
//			}
//			if (expansionDepth > this.parser.mOptions.getMaxPPExpandDepth()) {
//				//	this.parser.limitationWarn('expansion-depth-exceeded',
//				//			expansionDepth,
//				//			this.parser.mOptions.getMaxPPExpandDepth()
//				//	);
//				return "<span class=\"error\">Expansion depth limit exceeded</span>";
//			}
//			++expansionDepth;
//			if (expansionDepth > this.parser.mHighestExpansionDepth) {
//				this.parser.mHighestExpansionDepth = expansionDepth;
//			}
//
//			XophpArray outStack = XophpArray.New("", "");
//			XophpArray iteratorStack = XophpArray.New(XophpObject_.False, root);
//			XophpArray indexStack = XophpArray.New(0, 0);
//
//			while (iteratorStack.count() > 1) {
//				int level = outStack.count() - 1;
//				Object iteratorNode = iteratorStack.Get_at(level);
//				String outItm = outStack.Get_at_str(level);
//				int index = indexStack.Get_at_int(level);
//				Object contextNode;
//				if (XophpArray.is_array(iteratorNode)) {
//					XophpArray iteratorNodeArray = (XophpArray)iteratorNode;
//					if (index >= iteratorNodeArray.count()) {
//						// All done with this iterator
//						iteratorStack.Set(level, XophpObject_.False);
//						contextNode = XophpObject_.False;
//					} else {
//						contextNode = iteratorNodeArray.Get_at(index);
//						index++;
//					}
//				} else if (Type_.Eq_by_obj(iteratorNode, typeof(XomwPPNode_Hash_Array))) {
//					XomwPPNode_Hash_Array iteratorNodeHashArray = (XomwPPNode_Hash_Array)iteratorNode;
//					if (index >= iteratorNodeHashArray.getLength()) {
//						// All done with this iterator
//						iteratorStack.Set(level, XophpObject_.False);
//						contextNode = XophpObject_.False;
//					} else {
//						contextNode = iteratorNodeHashArray.item(index);
//						index++;
//					}
//				} else {
//					// Copy to contextNode and then delete from iterator stack,
//					// because this is not an iterator but we do have to execute it once
//					contextNode = iteratorStack.Get_at(level);
//					iteratorStack.Set(level, XophpObject_.False);
//				}
//
//				Object newIterator = XophpObject_.False;
//				String contextName = XophpString_.False;
//				XophpArray contextChildren = XophpArray.False;
//
//				if (!XophpObject_.is_true(contextNode)) {
//					// nothing to do
//				} else if (XophpString_.is_string(contextNode)) {
//					outItm += (String)contextNode;
//				} else if (Type_.Eq_by_obj(contextNode, typeof(XomwPPNode_Hash_Array))) {
//					newIterator = contextNode;
//				} else if (Type_.Eq_by_obj(contextNode, typeof(XomwPPNode_Hash_Attr))) {
//					// No output
//				} else if (Type_.Eq_by_obj(contextNode, typeof(XomwPPNode_Hash_Text))) {
//					outItm += ((XomwPPNode_Hash_Text)contextNode).value;
//				} else if (Type_.Eq_by_obj(contextNode, typeof(XomwPPNode_Hash_Tree))) {
//					XomwPPNode_Hash_Tree contextNodeHashTree = (XomwPPNode_Hash_Tree)contextNode;
//					contextName = contextNodeHashTree.name;
//					contextChildren = contextNodeHashTree.getRawChildren();
//				} else if (XophpArray.is_array(contextNode)) {
//					XophpArray contextNodeArray = (XophpArray)contextNode;
//					// Node descriptor array
//					if (contextNodeArray.count() != 2) {
//						throw XomwMWException.New_by_method(typeof(XomwPPFrame_Hash), "expand", 
//							": found an array where a node descriptor should be");
//					}
//					contextName = (String)contextNodeArray.Get_at(0);
//					contextChildren = contextNodeArray.Get_at_ary(1);
//				} else {
//					throw XomwMWException.New_by_method(typeof(XomwPPFrame_Hash), "expand", ": Invalid parameter type");
//				}
//
//				// Handle node descriptor array or tree Object
//				if (!XophpString_.is_true(contextName)) {
//					// Not a node, already handled above
//				} else if (String_.CharAt(contextName, 0) == '@') {
//					// Attribute: no output
//				} else if (String_.Eq(contextName, "template")) {
//					// Double-brace expansion
//					XophpArray bits = XomwPPNode_Hash_Tree.splitRawTemplate(contextChildren);
//					if (Bitmask_.Has_int(flags, XomwPPFrame.NO_TEMPLATES)) {
//						newIterator = this.virtualBracketedImplode(
//							"{{", "|", "}}",
//							bits.Get_by("title"),
//							bits.Get_by("parts")
//						);
//					} else {
//						XophpArray ret = this.parser.braceSubstitution(bits, this);
//						if (ret.isset(ObjectUtl.Cls_val_name)) {// NOTE: using Cls_val_name b/c of transpilation and Object . Object
//							newIterator = ret.Get_by(ObjectUtl.Cls_val_name);
//						} else {
//							outItm += ret.Get_by_str("text");
//						}
//					}
//				} else if (String_.Eq(contextName, "tplarg")) {
//					// Triple-brace expansion
//					XophpArray bits = XomwPPNode_Hash_Tree.splitRawTemplate(contextChildren);
//					if (Bitmask_.Has_int(flags, XomwPPFrame.NO_ARGS)) {
//						newIterator = this.virtualBracketedImplode(
//							"{{{", "|", "}}}",
//							bits.Get_by("title"),
//							bits.Get_by("parts")
//						);
//					} else {
//						XophpArray ret = this.parser.argSubstitution(bits, this);
//						if (ret.isset(ObjectUtl.Cls_val_name)) {// NOTE: using Cls_val_name b/c of transpilation and Object . Object
//							newIterator = ret.Get_by("Object");
//						} else {
//							outItm += ret.Get_by_str("text");
//						}
//					}
//				} else if (String_.Eq(contextName, "comment")) {
//					// HTML-style comment
//					// Remove it in HTML, pre+remove and STRIP_COMMENTS modes
//					// Not in RECOVER_COMMENTS mode (msgnw) though.
//					if ((this.parser.ot.Has("html"))
//						|| (this.parser.ot.Has("pre") && this.parser.mOptions.getRemoveComments())
//						|| (Bitmask_.Has_int(flags, XomwPPFrame.STRIP_COMMENTS))
//						 && !(Bitmask_.Has_int(flags, XomwPPFrame.RECOVER_COMMENTS))
//					) {
//						outItm += ""; // XOWA: no purpose?
//					} else if (this.parser.ot.Has("wiki") && !(Bitmask_.Has_int(flags, XomwPPFrame.RECOVER_COMMENTS))) {
//						// Add a strip marker in PST mode so that pstPass2() can
//						// run some old-fashioned regexes on the result.
//						// Not in RECOVER_COMMENTS mode (extractSections) though.
//						outItm += this.parser.insertStripItem(contextChildren.Get_at_str(0));
//					} else {
//						// Recover the literal comment in RECOVER_COMMENTS and pre+no-remove
//						outItm += contextChildren.Get_at_str(0);
//					}
//				} else if (String_.Eq(contextName, "ignore")) {
//					// Output suppression used by <includeonly> etc.
//					// OT_WIKI will only respect <ignore> in substed templates.
//					// The other output types respect it unless NO_IGNORE is set.
//					// extractSections() sets NO_IGNORE and so never respects it.
////					if ((!XophpObject_.isset(this.parent) && this.parser.ot.Has("wiki")) // this.parent doesn't exist?
//					if ((this.parser.ot.Has("wiki"))
//						|| (Bitmask_.Has_int(flags, XomwPPFrame.NO_IGNORE))
//					) {
//						outItm += contextChildren.Get_at_str(0);
//					} else {
//						// outItm .= '';
//					}
//				} else if (String_.Eq(contextName, "ext")) {
//					// Extension tag
//					XophpArray bits = XomwPPNode_Hash_Tree.splitRawExt(contextChildren)
//						.Add("attr", null).Add("inner", null).Add("close", null);
//					if (Bitmask_.Has_int(flags, XomwPPFrame.NO_TAGS)) {
//						String s = '<' + ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("name")).getFirstChild()).value;
//						if (bits.Has("attr")) {
//							s += ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("attr")).getFirstChild()).value;
//						}
//						if (bits.Has("inner")) {
//							s += '>' + ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("inner")).getFirstChild()).value;
//							if (bits.Has("close")) {
//								s += ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("close")).getFirstChild()).value;
//							}
//						} else {
//							s += "/>";
//						}
//						outItm += s;
//					} else {
//						outItm += this.parser.extensionSubstitution(bits, this);
//					}
//				} else if (String_.Eq(contextName, "h")) {
//					// Heading
//					if (this.parser.ot.Has("html")) {
//						// Expand immediately and insert heading index marker
//						String s = this.expand(contextChildren, flags);
//						XophpArray bits = XomwPPNode_Hash_Tree.splitRawHeading(contextChildren);
//						String titleText = this.title.getPrefixedDBkeyStr();
//						this.parser.mHeadings.Add(titleText, bits.Get_by("i"));
//						int serial = XophpArray.count(this.parser.mHeadings) - 1;
//						String marker = XomwParser.MARKER_PREFIX + "-h-" + IntUtl.To_str(serial) + "-" + XomwParser.MARKER_SUFFIX;
//						s = XophpString_.substr(s, 0, bits.Get_by_int("level")) + marker + XophpString_.substr(s, bits.Get_by_int("level"));
//						this.parser.mStripState.addGeneral(marker, "");
//						outItm += s;
//					} else {
//						// Expand in virtual stack
//						newIterator = contextChildren;
//					}
//				} else {
//					// Generic recursive expansion
//					newIterator = contextChildren;
//				}
//
//				if (XophpObject_.is_true(newIterator)) {
//					outStack.Add("");
//					iteratorStack.Add(newIterator);
//					indexStack.Add(0);
//				} else if (!XophpObject_.is_true(iteratorStack.Get_at(level))) {
//					// Return accumulated value to parent
//					// With tail recursion
//					while (!XophpObject_.is_true(iteratorStack.Get_at(level)) && level > 0) {
//						outStack.Itm_str_concat_end(level - 1, outItm);
//						outStack.pop();
//						iteratorStack.pop();
//						indexStack.pop();
//						level--;
//					}
//				}
//			}
//			--expansionDepth;
//			return outStack.Get_at_str(0);
//		}
//
//		/**
//		* @param String $sep
//		* @param int $flags
//		* @param String|PPNode $args,...
//		* @return String
//		*/
//		public String implodeWithFlags(String sep, int flags, XophpArray args) {
//			// args = XophpArray.array_slice(func_get_args(), 2);
//
//			boolean first = true;
//			String s = "";
//			int len = args.Len();
//			for (int i = 0; i < len; i++) {
//				Object root_obj = args.Get_at(i);
//				XophpArray root = null;
//				if (XophpType_.instance_of(root_obj, typeof(XomwPPNode_Hash_Array))) {
//					root = (XophpArray)((XomwPPNode_Hash_Array)root_obj).value;
//				}
//				if (!XophpType_.is_array(root_obj)) {
//					root = XophpArray.New(root_obj);
//				}
//				int root_len = root.Len();
//				for (int j = 0; j < root_len; j++) {
//					XomwPPNode node = (XomwPPNode)root.Get_at(j);
//					if (first) {
//						first = false;
//					} else {
//						s += sep;
//					}
//					s += this.expand(node, flags);
//				}
//			}
//			return s;
//		}
//
//		/**
//		* Implode with no flags specified
//		* This previously called implodeWithFlags but has now been inlined to reduce stack depth
//		* @param String $sep
//		* @param String|PPNode $args,...
//		* @return String
//		*/
//		public override String implode(String sep, params Object[] args) {
//			boolean first = true;
//			String s = "";
//			foreach (Object rootObj in args) {
//				XophpArray root = null;
//				if (Type_.Eq_by_obj(root, typeof(XomwPPNode_Hash_Array))) {
//					root = ((XomwPPNode_Hash_Array)rootObj).value;
//				}
//				if (!XophpArray.is_array(rootObj)) {
//					root = XophpArray.New(root);
//				}
//				int rootLen = root.count();
//				for (int i = 0; i < rootLen; i++) {
//					Object node = root.Get_at(i);
//					if (first) {
//						first = false;
//					} else {
//						s += sep;
//					}
//					s += this.expand(node, expand_flags_default);
//				}
//			}
//			return s;
//		}
//
//	    /**
//		* Makes an Object that, when expand()ed, will be the same as one obtained
//		* with implode()
//		*
//		* @param String $sep
//		* @param String|PPNode $args,...
//		* @return PPNode_Hash_Array
//		*/
//		public override XomwPPNode virtualImplode(String sep, params Object[] args) {
//			XophpArray outItm = XophpArray.New();
//			boolean first = true;
//
//			foreach (Object rootObj in args) {
//				XophpArray root = null;
//				if (Type_.Eq_by_obj(root, typeof(XomwPPNode_Hash_Array))) {
//					root = ((XomwPPNode_Hash_Array)rootObj).value;
//				}
//				if (!XophpArray.is_array(rootObj)) {
//					root = XophpArray.New(root);
//				}
//				int rootLen = root.count();
//				for (int i = 0; i < rootLen; i++) {
//					Object node = root.Get_at(i);
//					if (first) {
//						first = false;
//					} else {
//						outItm.Add(sep);
//					}
//					outItm.Add(node);
//				}
//			}
//			return new XomwPPNode_Hash_Array(outItm);
//		}
//
//		/**
//		* Virtual implode with brackets
//		*
//		* @param String $start
//		* @param String $sep
//		* @param String $end
//		* @param String|PPNode $args,...
//		* @return PPNode_Hash_Array
//		*/
//		public override XomwPPNode virtualBracketedImplode(String start, String sep, String end, params Object[] args) {
//			XophpArray outItm = XophpArray.New(start);
//			boolean first = true;
//
//			foreach (Object rootObj in args) {
//				XophpArray root = null;
//				if (Type_.Eq_by_obj(rootObj, typeof(XomwPPNode_Hash_Array))) {
//					root = ((XomwPPNode_Hash_Array)rootObj).value;
//				}
//				if (!XophpArray.is_array(rootObj)) {
//					root = XophpArray.New((String)rootObj);
//				}
//				int root_len = root.count();
//				for (int i = 0; i < root_len; i++) {
//					String node = root.Get_at_str(i);
//					if (first) {
//						first = false;
//					} else {
//						outItm.Add(sep);
//					}
//					outItm.Add(node);
//				}
//			}
//			outItm.Add(end);
//			return new XomwPPNode_Hash_Array(outItm);
//		}
//
//		public override String toString() {
//			return "frame{}";
//		}
//
//		/**
//		* @param boolean $level
//		* @return array|boolean|String
//		*/
//		public String getPDBK(boolean level) { // DEFAULT:false
//			if (level == false) {
//				return this.title.getPrefixedDBkeyStr();
//			} else {
//				// return isset( $this->titleCache[$level] ) ? $this->titleCache[$level] : false;
//				return this.titleCache.count() > 0 ? ((String)this.titleCache.Get_at(0)) : XophpString_.False;
//			}
//		}
//
//		/**
//		* @return array
//		*/
//		public override XophpArray getArguments() {
//			return XophpArray.False;
//		}
//
//		/**
//		* @return array
//		*/
//		public override XophpArray getNumberedArguments() {
//			return XophpArray.False;
//		}
//
//		/**
//		* @return array
//		*/
//		public override XophpArray getNamedArguments() {
//			return XophpArray.False;
//		}
//
//		/**
//		* Returns true if there are no arguments in this frame
//		*
//		* @return boolean
//		*/
//		public override boolean isEmpty() {
//			return true;
//		}
//
//		/**
//		* @param int|String $name
//		* @return boolean Always false in this implementation.
//		*/
//		public override String getArgument(String name) {
//			return XophpString_.False;
//		}
//
//		/**
//		* Returns true if the infinite loop check is OK, false if a loop is detected
//		*
//		* @param Title $title
//		*
//		* @return boolean
//		*/
//		public override boolean loopCheck(XomwTitleOld title) {
//			return !this.loopCheckHash.isset(title.getPrefixedDBkeyStr());
//		}
//
//		/**
//		* Return true if the frame is a template frame
//		*
//		* @return boolean
//		*/
//		public override boolean isTemplate() {
//			return false;
//		}
//
//		/**
//		* Get a title of frame
//		*
//		* @return Title
//		*/
//		public override XomwTitleOld getTitle() {
//			return this.title;
//		}
//
//		/**
//		* Set the volatile_bool flag
//		*
//		* @param boolean $flag
//		*/
//		public override void setVolatile(boolean flag) { // DEFAULT: flag = true
//			this.volatile_bool = flag;
//		}
//
//		/**
//		* Get the volatile_bool flag
//		*
//		* @return boolean
//		*/
//		public override boolean isVolatile() {
//			return this.volatile_bool;
//		}
//
//		/**
//		* Set the TTL
//		*
//		* @param int ttl
//		*/
//		public override void setTTL(int val) {
//			if (this.ttl == IntUtl.Null || val < this.ttl) {
//				this.ttl = val;
//			}
//		}
//
//		/**
//		* Get the TTL
//		*
//		* @return int|null
//		*/
//		public override int getTTL() {
//			return this.ttl;
//		}
}
