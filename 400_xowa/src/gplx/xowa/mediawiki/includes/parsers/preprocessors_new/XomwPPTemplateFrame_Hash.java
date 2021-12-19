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
//namespace gplx.xowa.mediawiki.includes.parsers.preprocessors_new {
//	///**
//	// * Expansion frame with template arguments
//	// * @ingroup Parser
//	// */
//	class XomwPPTemplateFrame_Hash : XomwPPFrame_Hash { //		public XophpArray numberedArgs, namedArgs;
//		@gplx.frameworks.objects.New public XomwPPFrame_Hash parent;
//		public XophpArray numberedExpansionCache, namedExpansionCache;
//
//		/**
//		 * @param Preprocessor $preprocessor
//		 * @param boolean|PPFrame $parent
//		 * @param array $numberedArgs
//		 * @param array $namedArgs
//		 * @param boolean|Title $title
//		 */
//		// parent = false, numberedArgs = [], namedArgs = []; titl = false
//		public XomwPPTemplateFrame_Hash(XomwPreprocessor preprocessor, XomwPPFrame_Hash parent, XophpArray numberedArgs,
//			XophpArray namedArgs, XomwTitleOld title
//		) : super(preprocessor) {
//			this.parent = parent;
//			this.numberedArgs = numberedArgs;
//			this.namedArgs = namedArgs;
//			this.title = title;
//			String pdbk = title != null ? title.getPrefixedDBkeyStr() : XophpString_.False;
//			this.titleCache = parent.titleCache;
//			this.titleCache.Add(pdbk);
//			this.loopCheckHash = /*clone*/ parent.loopCheckHash;
//			if (pdbk != XophpString_.False) {
//				this.loopCheckHash.Add(pdbk, true);
//			}
//			this.depth = parent.depth + 1;
//			this.numberedExpansionCache = this.namedExpansionCache = XophpArray.New();
//		}
//
//		public override String toString() {
//			String s = "tplframe{";
//			boolean first = true;
//			XophpArray args = XophpArray.array_add(this.numberedArgs, this.namedArgs);
//			int args_len = args.count();
//			for (int i = 0; i < args_len; i++) {
//				XophpArrayItm itm = args.Get_at_itm(i);
//				if (first) {
//					first = false;
//				} else {
//					s += ", ";
//				}
//				s += "\"" + itm.Key() + "\":\"" +
//					XophpString_.str_replace("\"", "\\\"", itm.Val().toString()) + "\"";
//			}
//			s += "}";
//			return s;
//		}
//
//		/**
//		 * @throws MWException
//		 * @param String|int $key
//		 * @param String|PPNode $root
//		 * @param int $flags
//		 * @return String
//		 */
//		public override String cachedExpand(String key, XomwPPNode root, int flags) { // flags = 0
//			if (XophpObject_.isset_obj(this.parent.childExpansionCache.Get_by(key))) {
//				return this.parent.childExpansionCache.Get_by_str(key);
//			}
//			String retval = this.expand(root, flags);
//			if (!this.isVolatile()) {
//				this.parent.childExpansionCache.Set(key, retval);
//			}
//			return retval;
//		}
//
//		/**
//		 * Returns true if there are no arguments in this frame
//		 *
//		 * @return boolean
//		 */
//		public override boolean isEmpty() {
//			return !this.numberedArgs.count_bool() && !this.namedArgs.count_bool();
//		}
//
//		/**
//		 * @return array
//		 */
//		public override XophpArray getArguments() {
//			XophpArray arguments = XophpArray.New();
//			XophpArray merged = XophpArray.array_merge(
//					XophpArray.array_keys(this.numberedArgs),
//					XophpArray.array_keys(this.namedArgs));
//			int merged_len = merged.count();
//			for (int i = 0; i < merged_len; i++) {
//				String key = merged.Get_at_str(i);
//				arguments.Set(key, this.getArgument(key));
//			}
//			return arguments;
//		}
//
//		/**
//		 * @return array
//		 */
//		public override XophpArray getNumberedArguments() {
//			XophpArray arguments = XophpArray.New();
//			XophpArray temp = XophpArray.array_keys(this.numberedArgs);
//			int temp_len = temp.count();
//			for (int i = 0; i < temp_len; i++) {
//				String key = temp.Get_at_str(i);
//				arguments.Set(key, this.getArgument(key));
//			}
//			return arguments;
//		}
//
//		/**
//		 * @return array
//		 */
//		public override XophpArray getNamedArguments() {
//			XophpArray arguments = XophpArray.New();
//			XophpArray temp = XophpArray.array_keys(this.namedArgs);
//			int temp_len = temp.count();
//			for (int i = 0; i < temp_len; i++) {
//				String key = temp.Get_at_str(i);
//				arguments.Set(key, this.getArgument(key));
//			}
//			return arguments;
//		}
//
//		/**
//		 * @param int $index
//		 * @return String|boolean
//		 */
//		public String getNumberedArgument(int index) {
//			if (!XophpObject_.isset_obj(this.numberedArgs.Get_at(index))) {
//				return XophpString_.False;
//			}
//			if (!XophpObject_.isset_obj(this.numberedExpansionCache.Get_at(index))) {
//				// No trimming for unnamed arguments
//				this.numberedExpansionCache.Set(index, this.parent.expand(
//					(XomwPPNode)this.numberedArgs.Get_at(index),
//					XomwPPFrame.STRIP_COMMENTS
//				));
//			}
//			return this.numberedExpansionCache.Get_at_str(index);
//		}
//
//		/**
//		 * @param String $name
//		 * @return String|boolean
//		 */
//		public String getNamedArgument(String name) {
//			if (!XophpObject_.isset_obj(this.namedArgs.Get_by_str(name))) {
//				return XophpString_.False;
//			}
//			if (!XophpObject_.isset_obj(this.namedExpansionCache.Get_by_str(name))) {
//				// Trim named arguments post-expand, for backwards compatibility
//				this.namedExpansionCache.Set(name, XophpString_.trim(
//				this.parent.expand((XomwPPNode)this.namedArgs.Get_by(name), XomwPPFrame.STRIP_COMMENTS)));
//			}
//			return this.namedExpansionCache.Get_by_str(name);
//		}
//
//		/**
//		 * @param int|String $name
//		 * @return String|boolean
//		 */
//		public String getArgument(Object name) {
//			String text = this.getNumberedArgument((int)name);
//			if (String_.Eq(text, XophpString_.False)) {
//				text = this.getNamedArgument((String)name);
//			}
//			return text;
//		}
//
//		/**
//		 * Return true if the frame is a template frame
//		 *
//		 * @return boolean
//		 */
//		public override boolean isTemplate() {
//			return true;
//		}
//
//		public override void setVolatile(boolean flag) { // flag = true
//			super.setVolatile(flag);
//			this.parent.setVolatile(flag);
//		}
//
//		public override void setTTL(int ttl) {
//			super.setTTL(ttl);
//			this.parent.setTTL(ttl);
//		}
//	}
//}
