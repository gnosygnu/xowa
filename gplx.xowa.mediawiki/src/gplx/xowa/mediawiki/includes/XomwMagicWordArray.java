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
import gplx.core.btries.*; import gplx.core.primitives.*;
public class XomwMagicWordArray {
	private Btrie_slim_mgr fwd_trie;
	private Btrie_bwd_mgr  bwd_trie;
	private final    Btrie_rv trv = new Btrie_rv();
//		private final    XomwMagicWordMgr magic_word_mgr;
	public final    byte[][] names;

//		/** @var array */
//		private hash;

//		private baseRegex;

//		private regex;

	public XomwMagicWordArray(XomwMagicWordMgr magic_word_mgr, byte[][] names) {
//			this.magic_word_mgr = magic_word_mgr;
		this.names = names;

		// ASSUME: all magic words in a group have the same case sensitivity
		for (byte[] name : names) {
			XomwMagicWord word = magic_word_mgr.Get(name);
			if (word == null) continue;
			XomwMagicWordSynonym[] synonyms = word.synonyms;
			int synonyms_len = synonyms.length;
			for (int i = 0; i < synonyms_len; i++) {
				XomwMagicWordSynonym synonym = synonyms[i];
				switch (synonym.arg1_tid) {
					case XomwMagicWordSynonym.Arg1__nil:
					case XomwMagicWordSynonym.Arg1__end:
						if (fwd_trie == null) fwd_trie = word.case_match ? Btrie_slim_mgr.cs() : Btrie_slim_mgr.ci_u8();
						fwd_trie.Add_obj(synonym.text_wo_arg1, synonym);
						break;
					case XomwMagicWordSynonym.Arg1__bgn:
						if (bwd_trie == null) bwd_trie = Btrie_bwd_mgr.c__(word.case_match);
						bwd_trie.Add(synonym.text_wo_arg1, synonym);
						break;
					// ignore if mid / mix
					case XomwMagicWordSynonym.Arg1__mid:
					case XomwMagicWordSynonym.Arg1__mix:
						Gfo_usr_dlg_.Instance.Warn_many("", "", "MagicWordArray: unsupported arg_1_tid: tid=~{0}", synonym.arg1_tid);
						continue;
				}
			}
		}
	}

//		/**
//		* Add a magic word by name
//		*
//		* @param String name
//		*/
//		public function add(name) {
//			this->names[] = name;
//			this->hash = this->baseRegex = this->regex = null;
//		}
//
//		/**
//		* Add a number of magic words by name
//		*
//		* @param array names
//		*/
//		public function addArray(names) {
//			this->names = array_merge(this->names, array_values(names));
//			this->hash = this->baseRegex = this->regex = null;
//		}
//
//		/**
//		* Get a 2-d hashtable for this array
//		* @return array
//		*/
//		public function getHash() {
//			if (is_null(this->hash)) {
//				global wgContLang;
//				this->hash = [ 0 => [], 1 => [] ];
//				foreach (this->names as name) {
//					magic = MagicWord::get(name);
//					case = intval(magic->isCaseSensitive());
//					foreach (magic->getSynonyms() as syn) {
//						if (!case) {
//							syn = wgContLang->lc(syn);
//						}
//						this->hash[case][syn] = name;
//					}
//				}
//			}
//			return this->hash;
//		}
//
//		/**
//		* Get the super regex
//		* @return array
//		*/
//		public function getBaseRegex() {
//			if (is_null(this->baseRegex)) {
//				this->baseRegex = [ 0 => '', 1 => '' ];
//				foreach (this->names as name) {
//					magic = MagicWord::get(name);
//					case = intval(magic->isCaseSensitive());
//					foreach (magic->getSynonyms() as i => syn) {
//						// Group name must start with a non-digit in PCRE 8.34+
//						it = strtr(i, '0123456789', 'abcdefghij');
//						group = "(?P<{it}_{name}>" . preg_quote(syn, '/') . ')';
//						if (this->baseRegex[case] === '') {
//							this->baseRegex[case] = group;
//						} else {
//							this->baseRegex[case] .= '|' . group;
//						}
//					}
//				}
//			}
//			return this->baseRegex;
//		}
//
//		/**
//		* Get an unanchored regex that does not match parameters
//		* @return array
//		*/
//		public function getRegex() {
//			if (is_null(this->regex)) {
//				super = this->getBaseRegex();
//				this->regex = [ '', '' ];
//				if (this->baseRegex[0] !== '') {
//					this->regex[0] = "/{super[0]}/iuS";
//				}
//				if (this->baseRegex[1] !== '') {
//					this->regex[1] = "/{super[1]}/S";
//				}
//			}
//			return this->regex;
//		}
//
//		/**
//		* Get a regex for matching variables with parameters
//		*
//		* @return String
//		*/
//		public function getVariableRegex() {
//			return str_replace("\\1", "(.*?)", this->getRegex());
//		}
//
//		/**
//		* Get a regex anchored to the start of the String that does not match parameters
//		*
//		* @return array
//		*/
//		public function getRegexStart() {
//			super = this->getBaseRegex();
//			newRegex = [ '', '' ];
//			if (super[0] !== '') {
//				newRegex[0] = "/^(?:{super[0]})/iuS";
//			}
//			if (super[1] !== '') {
//				newRegex[1] = "/^(?:{super[1]})/S";
//			}
//			return newRegex;
//		}
//
//		/**
//		* Get an anchored regex for matching variables with parameters
//		*
//		* @return array
//		*/
//		public function getVariableStartToEndRegex() {
//			super = this->getBaseRegex();
//			newRegex = [ '', '' ];
//			if (super[0] !== '') {
//				newRegex[0] = str_replace("\\1", "(.*?)", "/^(?:{super[0]})/iuS");
//			}
//			if (super[1] !== '') {
//				newRegex[1] = str_replace("\\1", "(.*?)", "/^(?:{super[1]})/S");
//			}
//			return newRegex;
//		}
//
//		/**
//		* @since 1.20
//		* @return array
//		*/
//		public function getNames() {
//			return this->names;
//		}
//
//		/**
//		* Parse a match array from preg_match
//		* Returns array(magic word ID, parameter value)
//		* If there is no parameter value, that element will be false.
//		*
//		* @param array m
//		*
//		* @throws MWException
//		* @return array
//		*/
//		public function parseMatch(m) {
//			reset(m);
//			while (list(key, value) = each(m)) {
//				if (key === 0 || value === '') {
//					continue;
//				}
//				parts = explode('_', key, 2);
//				if (count(parts) != 2) {
//					// This shouldn't happen
//					// continue;
//					throw new MWException(__METHOD__ . ': bad parameter name');
//				}
//				list(/* synIndex */, magicName) = parts;
//				paramValue = next(m);
//				return [ magicName, paramValue ];
//			}
//			// This shouldn't happen either
//			throw new MWException(__METHOD__ . ': parameter not found');
//		}

	/**
	* Match some text, with parameter capture
	* Returns an array with the magic word name in the first element and the
	* parameter in the second element.
	* Both elements are false if there was no match.
	*
	* @param String text
	*
	* @return array
	*/
	public void matchVariableStartToEnd(byte[][] rv, byte[] src) {
		int src_end = src.length;
		if (src_end == 0) {
			rv[0] = rv[1] = null;
			return;
		}

		byte[] name = null;
		int val_bgn = -1, val_end = -1;

		// check fwd; EX: "thumb=$1"
		if (fwd_trie != null) {
			Object o = fwd_trie.Match_at(trv, src, 0, src_end);
			if (o != null) {
				XomwMagicWordSynonym syn = ((XomwMagicWordSynonym)o);
				name = syn.magic_name;
				val_bgn = trv.Pos();
				val_end = src_end;

				// if "nil", then must be full match; EX: "thumbx" does not match "thumb"
				if (syn.arg1_tid == XomwMagicWordSynonym.Arg1__nil
					&& syn.text_wo_arg1.length != src_end) {
					rv[0] = rv[1] = null;
					return;
				}
			}
		}

		// check bwd; EX: "$1px"
		if (bwd_trie != null) {
			Object o = bwd_trie.Match_at(trv, src, src_end - 1, -1);
			if (o != null) {
				XomwMagicWordSynonym syn = ((XomwMagicWordSynonym)o);
				name = syn.magic_name;
				val_bgn = 0;
				val_end = src_end - syn.text_wo_arg1.length;
			}
		}

		rv[0] = name;
		rv[1] = val_end - val_bgn == 0 ? Bry_.Empty : Bry_.Mid(src, val_bgn, val_end);
	}

//		/**
//		* Match some text, without parameter capture
//		* Returns the magic word name, or false if there was no capture
//		*
//		* @param String text
//		*
//		* @return String|boolean False on failure
//		*/
//		public function matchStartToEnd(text) {
//			hash = this->getHash();
//			if (isset(hash[1][text])) {
//				return hash[1][text];
//			}
//			global wgContLang;
//			lc = wgContLang->lc(text);
//			if (isset(hash[0][lc])) {
//				return hash[0][lc];
//			}
//			return false;
//		}
//
//		/**
//		* Returns an associative array, ID => param value, for all items that match
//		* Removes the matched items from the input String (passed by reference)
//		*
//		* @param String text
//		*
//		* @return array
//		*/
//		public function matchAndRemove(&text) {
//			found = [];
//			regexes = this->getRegex();
//			foreach (regexes as regex) {
//				if (regex === '') {
//					continue;
//				}
//				matches = [];
//				res = preg_match_all(regex, text, matches, PREG_SET_ORDER);
//				if (res === false) {
//					LoggerFactory::getInstance('parser')->warning('preg_match_all returned false', [
//						'code' => preg_last_error(),
//						'regex' => regex,
//						'text' => text,
//					]);
//				} elseif (res) {
//					foreach (matches as m) {
//						list(name, param) = this->parseMatch(m);
//						found[name] = param;
//					}
//				}
//				res = preg_replace(regex, '', text);
//				if (res === null) {
//					LoggerFactory::getInstance('parser')->warning('preg_replace returned null', [
//						'code' => preg_last_error(),
//						'regex' => regex,
//						'text' => text,
//					]);
//				}
//				text = res;
//			}
//			return found;
//		}
//
//		/**
//		* Return the ID of the magic word at the start of text, and remove
//		* the prefix from text.
//		* Return false if no match found and text is not modified.
//		* Does not match parameters.
//		*
//		* @param String text
//		*
//		* @return int|boolean False on failure
//		*/
//		public function matchStartAndRemove(&text) {
//			regexes = this->getRegexStart();
//			foreach (regexes as regex) {
//				if (regex === '') {
//					continue;
//				}
//				if (preg_match(regex, text, m)) {
//					list(id,) = this->parseMatch(m);
//					if (strlen(m[0]) >= strlen(text)) {
//						text = '';
//					} else {
//						text = substr(text, strlen(m[0]));
//					}
//					return id;
//				}
//			}
//			return false;
//		}
}
