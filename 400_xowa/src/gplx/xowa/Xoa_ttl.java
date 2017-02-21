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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.langs.htmls.entitys.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.xwikis.*; 
import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.miscs.*;
import gplx.xowa.apps.utls.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.langs.cases.*;
/* TODO.XO
	Is_known
	Create_fragment_target
*/
public class Xoa_ttl {	// PAGE:en.w:http://en.wikipedia.org/wiki/Help:Link; REF.MW: Ttl.php|secureAndSplit;
	private int wik_bgn = -1, ns_bgn = -1, page_bgn = 0, leaf_bgn = -1, anch_bgn = -1, root_bgn = -1;
	private byte[] tors_txt;
	private Xoa_ttl() {}
	public Xow_ns Ns() {return ns;} private Xow_ns ns;
	public boolean ForceLiteralLink() {return forceLiteralLink;} private boolean forceLiteralLink;
	// NOTE: in procs below, all -1 are used to skip previous delimiters; they will only occur for end_pos arguments
	public boolean Eq_page_db(Xoa_ttl comp) {if (comp == null) return false; return Bry_.Eq(this.Page_db(), comp.Page_db());}	// check page is same; ignores anchor and xwiki
	public boolean Eq_full_db(Xoa_ttl comp) {if (comp == null) return false; return Bry_.Eq(this.Full_db(), comp.Full_db());}	// check page is same; ignores anchor and xwiki
	public byte[] Raw() {return raw;} private byte[] raw = Bry_.Empty;
	public byte[] Wik_txt()  {return wik_bgn == -1 ? Bry_.Empty : Bry_.Mid(full_txt, wik_bgn, ns_bgn == -1 ? page_bgn - 1 : ns_bgn - 1);}
	public Xow_xwiki_itm Wik_itm() {return wik_itm;} private Xow_xwiki_itm wik_itm;
	public byte[] Full_txt_w_ttl_case() {return Xoa_ttl.Replace_unders(Full_db());}
	public byte[] Full_txt_by_orig() {
		int bgn = wik_bgn == -1  ? 0 : ns_bgn == -1 ? page_bgn : ns_bgn;
		int end = full_txt.length;
		if		(anch_bgn != -1) end = anch_bgn - 1;
		return Bry_.Mid(full_txt, bgn, end);
	}
	public byte[] Full_txt_raw()		{return full_txt;} private byte[] full_txt = Bry_.Empty;
	public byte[] Full_db_wo_xwiki()	{
		byte[] rv = Bry_.Mid(full_txt, wik_bgn == -1 ? 0 : ns_bgn == -1 ? page_bgn - 1 : ns_bgn - 1, full_txt.length);
		Bry_.Replace_reuse(rv, Byte_ascii.Space, Byte_ascii.Underline);
		return rv;
	}
	public byte[] Page_txt_w_anchor()	{return Bry_.Mid(full_txt, page_bgn, qarg_bgn == -1 ? full_txt.length : qarg_bgn - 1);}
	public byte[] Page_txt()			{return Bry_.Mid(full_txt, page_bgn, anch_bgn == -1 ? full_txt.length : anch_bgn - 1);}
	public byte[] Page_db() {
		byte[] rv = this.Page_txt();
		Bry_.Replace_reuse(rv, Byte_ascii.Space, Byte_ascii.Underline);
		return rv;
	}
	public String Page_db_as_str()		{return String_.new_u8(Page_db());}
	public byte[] Page_url_w_anch()		{
		synchronized (href_encoder) { // LOCK:static-obj
			return href_encoder.Encode(Bry_.Mid(full_txt, page_bgn, qarg_bgn == -1 ? full_txt.length : qarg_bgn - 1));
		}
	}
	public int Leaf_bgn() {return leaf_bgn;}
	public byte[] Base_txt() {return leaf_bgn == -1	? Page_txt() : Bry_.Mid(full_txt, page_bgn, leaf_bgn - 1);}
	public byte[] Leaf_txt() {return leaf_bgn == -1	? Page_txt() : Bry_.Mid(full_txt, leaf_bgn, anch_bgn == -1 ? full_txt.length : anch_bgn - 1);}
	public int Wik_bgn() {return wik_bgn;}
	public int Anch_bgn() {return anch_bgn;}	// NOTE: anch_bgn is not correct when page has trailing ws; EX: [[A #b]] should have anch_bgn of 3 (1st char after #), but instead it is 2
	public byte[] Anch_txt() {return anch_bgn == -1	? Bry_.Empty : Bry_.Mid(full_txt, anch_bgn, full_txt.length);}
	public byte[] Talk_txt() {return ns.Id_is_talk()		? Full_txt_w_ttl_case() : Bry_.Add(tors_txt, Page_txt());} 
	public byte[] Subj_txt() {return ns.Id_is_subj()		? Full_txt_w_ttl_case() : Bry_.Add(tors_txt, Page_txt());} 
	public byte[] Full_url() {
		synchronized (href_encoder) {	// LOCK:static-obj
			return href_encoder.Encode(full_txt);
		}
	}
	public String Full_db_as_str()	{return String_.new_u8(Full_db());}
	public byte[] Full_db()			{return ns.Gen_ttl(this.Page_db());}
	public byte[] Full_db_w_anch()  {return Replace_spaces(full_txt);}
	public byte[] Page_url() {
		synchronized (url_encoder) {	// LOCK:static-obj
			return url_encoder.Encode(this.Page_txt());
		}
	}
	public byte[] Leaf_url() {
		synchronized (url_encoder) {	// LOCK:static-obj
			return url_encoder.Encode(this.Leaf_txt());
		}
	}
	public byte[] Base_url() {
		synchronized (url_encoder) {	// LOCK:static-obj
			return url_encoder.Encode(this.Base_txt());
		}
	}
	public byte[] Root_txt() {return root_bgn == -1	? Page_txt() : Bry_.Mid(full_txt, page_bgn, root_bgn - 1);}
	public byte[] Rest_txt() {return root_bgn == -1	? Page_txt() : Bry_.Mid(full_txt, root_bgn, anch_bgn == -1 ? full_txt.length : anch_bgn - 1);}
	public byte[] Talk_url() {
		synchronized (url_encoder) {	// LOCK:static-obj
			return url_encoder.Encode(this.Talk_txt());
		}
	}
	public byte[] Subj_url() {
		synchronized (url_encoder) {	// LOCK:static-obj
			return url_encoder.Encode(this.Subj_txt());
		}
	}
	public int Qarg_bgn() {return qarg_bgn;} private int qarg_bgn = -1;
	public byte[] Qarg_txt() {return this.Qarg_bgn() == -1 ? null : Bry_.Mid(full_txt, this.Qarg_bgn(), full_txt.length);}
	public byte[] Base_txt_wo_qarg() {
		int bgn = page_bgn;
		int end = full_txt.length;
		if		(leaf_bgn != -1) end = leaf_bgn - 1;
		else if (qarg_bgn != -1) end = qarg_bgn - 1;
		return Bry_.Mid(full_txt, bgn, end);
	}
	public byte[] Leaf_txt_wo_qarg() {
		int bgn = leaf_bgn == -1 ? 0 : leaf_bgn;
		int end = full_txt.length;
		if		(anch_bgn != -1) end = anch_bgn - 1;
		else if (qarg_bgn != -1) end = qarg_bgn - 1;
		return Bry_.Mid(full_txt, bgn, end);
	}
	public byte[] Full_db_wo_qarg() {return Replace_spaces(Full_txt_wo_qarg());}
	public byte[] Full_txt_wo_qarg() {
		int bgn = wik_bgn == -1  ? 0 : ns_bgn == -1 ? page_bgn : ns_bgn;
		int end = full_txt.length;
		if		(anch_bgn != -1) end = anch_bgn - 1;
		else if (qarg_bgn != -1) end = qarg_bgn - 1;
		return Bry_.Mid(full_txt, bgn, end);
	}
	public byte[] Page_txt_wo_qargs() {	// assume that no Special page has non-ascii characters
		int full_txt_len = full_txt.length;
		int ques_pos = Bry_find_.Find_bwd(full_txt, Byte_ascii.Question, full_txt_len, page_bgn);
		return Bry_.Mid(full_txt, page_bgn, ques_pos == Bry_find_.Not_found ? full_txt_len : ques_pos);
	}

	public byte[] Get_text()			{return Page_txt();}
	public byte[] Get_prefixed_text()   {return Full_txt_wo_qarg();}
	public byte[] Get_prefixed_db_key() {return Full_db();}
	public boolean   Has_fragment() {return anch_bgn != -1;}
	public byte[] Get_fragment() {return Anch_txt();}
	public byte[] Get_link_url(Object qry_mgr, boolean query2, boolean proto) {
		// if ( $this->isExternal() || $proto !== false ) {
		//	$ret = $this->getFullURL( $query, $query2, $proto );
		// }
		// else if ( $this->getPrefixedText() === '' && $this->hasFragment() ) {
		//	$ret = $this->getFragmentForURL();
		// }
		// else {
		//	$ret = $this->getLocalURL( $query, $query2 ) . $this->getFragmentForURL();
		// }
		return Bry_.Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, this.Full_db_w_anch());
	}
	public boolean Is_always_known() {
//			$isKnown = null;

		/**
		* Allows overriding default behavior for determining if a page exists.
		* If $isKnown is kept as null, regular checks happen. If it's
		* a boolean, this value is returned by the isKnown method.
		*
		* @since 1.20
		*
		* @param Title $title
		* @param boolean|null $isKnown
		*/
//			Hooks::run( 'TitleIsAlwaysKnown', [ $this, &$isKnown ] );
//
//			if ( !is_null( $isKnown ) ) {
//				return $isKnown;
//			}
//
//			if ( $this->isExternal() ) {
//				return true;  // any interwiki link might be viewable, for all we know
//			}
//
//			switch ( $this->mNamespace ) {
//				case NS_MEDIA:
//				case NS_FILE:
//					// file exists, possibly in a foreign repo
//					return (boolean)wfFindFile( $this );
//				case NS_SPECIAL:
//					// valid special page
//					return SpecialPageFactory::exists( $this->getDBkey() );
//				case NS_MAIN:
//					// selflink, possibly with fragment
//					return $this->mDbkeyform == '';
//				case NS_MEDIAWIKI:
//					// known system message
//					return $this->hasSourceText() !== false;
//				default:
//					return false;
//			}
		return false;
	}
	public boolean Is_known() {
		return true;
	}
	public Xoa_ttl Create_fragment_target() {
		return this;
	}

	public boolean Is_external() {return this.wik_bgn != -1;}

	public static final byte Subpage_spr = Byte_ascii.Slash;	// EX: A/B/C		
	public static final    Xoa_ttl Null = null;

	private static final int Char__bidi = 1, Char__ws = 2;
	private static final    Btrie_slim_mgr char_trie = Btrie_slim_mgr.cs()
	.Add_many_int(Char__bidi	, Bry_.New_by_ints(0xE2, 0x80, 0x8E), Bry_.New_by_ints(0xE2, 0x80, 0x8F), Bry_.New_by_ints(0xE2, 0x80, 0xAA), Bry_.New_by_ints(0xE2, 0x80, 0xAB), Bry_.New_by_ints(0xE2, 0x80, 0xAC), Bry_.New_by_ints(0xE2, 0x80, 0xAD), Bry_.New_by_ints(0xE2, 0x80, 0xAE))
	.Add_many_int(Char__ws		, "\u00A0", "\u1680", "\u180E", "\u2000", "\u2001", "\u2002", "\u2003", "\u2004", "\u2005", "\u2006", "\u2007", "\u2008", "\u2009", "\u200A", "\u2028", "\u2029", "\u202F", "\u205F", "\u3000");

	private final    static Gfo_url_encoder href_encoder = Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Make();
	private final    static Xoa_url_encoder url_encoder = new Xoa_url_encoder();

	public static byte[] Replace_spaces(byte[] raw) {return Bry_.Replace(raw, Byte_ascii.Space, Byte_ascii.Underline);}
	public static byte[] Replace_unders(byte[] raw) {return Replace_unders(raw, 0, raw.length);}
	public static byte[] Replace_unders(byte[] raw, int bgn, int end) {return Bry_.Replace(raw, bgn, end, Byte_ascii.Underline, Byte_ascii.Space);}

	public static Xoa_ttl Parse(Xow_wiki wiki, byte[] raw)					{return Parse(wiki, raw, 0, raw.length);}
	public static Xoa_ttl Parse(Xow_wiki wiki, int ns_id, byte[] ttl)		{
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		byte[] raw = Bry_.Add(ns.Name_db_w_colon(), ttl);
		return Parse(wiki, raw, 0, raw.length);
	}
	public static Xoa_ttl Parse(Xow_wiki wiki, byte[] src, int bgn, int end){return Parse(wiki.App().Parser_amp_mgr(), wiki.Lang().Case_mgr(), wiki.Xwiki_mgr(), wiki.Ns_mgr(), src, bgn, end);}
	public static Xoa_ttl Parse(Xop_amp_mgr amp_mgr, Xol_case_mgr case_mgr, Xow_xwiki_mgr xwiki_mgr, Xow_ns_mgr ns_mgr, byte[] src, int bgn, int end) {
		Xoa_ttl rv = new Xoa_ttl();
		Bry_bfr bfr = Bry_bfr_.Get();	// changed from bry_mkr.Get_b512(); DATE:2016-07-06
		try		{return rv.Parse(bfr, amp_mgr, case_mgr, xwiki_mgr, ns_mgr, src, bgn, end) ? rv : null;}
		finally {bfr.Mkr_rls();}
	}
	private boolean Parse(Bry_bfr bfr, Xop_amp_mgr amp_mgr, Xol_case_mgr case_mgr, Xow_xwiki_mgr xwiki_mgr, Xow_ns_mgr ns_mgr, byte[] src, int bgn, int end) {
		/* This proc will
		- identify all parts: Wiki, Namespace, Base/Leaf, Anchor; it will also identify Subject/Talk ns 
		- trim whitespace around part delimiters; EX: "Help : Test" --> "Help:Test"; note that it will trim only if the ns part is real; EX: "Helpx : Test" is unchanged
		- replace multiple whitespaces with 1; EX: "Many     ws" --> "Many ws"
		- capitalize the first letter of the page title 
		note: a byte[] is needed b/c proc does collapsing and casing
		FUTURE:
		- "/", "a/" (should be page); "#" (not a page)
		- Talk:Help:a disallowed; Category talk:Help:a allowed
		- remove invalid characters $rxTc
		- forbid ./ /.
		- forbid ~~~
		- handle ip address urls for User and User talk
		*/
		Gfo_url_encoder anchor_encoder = null;
		Bry_bfr anchor_encoder_bfr = null;
		bfr.Clear();
		if (end - bgn == 0) return false;
		this.raw = src;
		ns = ns_mgr.Ns_main();
		boolean add_ws = false, ltr_bgn_reset = false;
		int ltr_bgn = -1, txt_bb_len = 0, colon_count = 0;
		Btrie_slim_mgr amp_trie = amp_mgr.Amp_trie(); Btrie_rv trv = null;		
		byte[] b_ary = null;
		int cur = bgn;
		int match_pos = -1;
		while (cur != end) {
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Colon:
					if (cur == bgn) {	// initial colon; flag; note that "  :" is not handled; note that colon_count is not incremented
						forceLiteralLink = true;
						++cur;
						if (cur < end && src[cur] == Byte_ascii.Colon)
							++cur;
						continue;	// do not add to bfr
					}
					else {
						if (ltr_bgn == -1) {// no ltrs seen; treat as literal; occurs for ::fr:wikt:test and fr::Help:test
							++colon_count;
							break;
						}
						boolean part_found = false;
						if (colon_count == 0) {// 1st colon; 
							Object o = ns_mgr.Names_get_or_null(bfr.Bfr(), ltr_bgn, txt_bb_len);
							if (o == null) {	// not ns; try alias
								wik_itm = xwiki_mgr.Get_by_mid(bfr.Bfr(), ltr_bgn, txt_bb_len); // check if wiki; note: wiki is not possible for other colons
								if (wik_itm != null) {
									wik_bgn = 0;			// wik_bgn can only start at 0
									part_found = true;
									anch_bgn = -1;			// NOTE: do not allow anchors to begin before wiki_itm; breaks Full_txt for [[:#batch:Main Page]]; DATE:20130102
								}
							}
							else {
								ns = (Xow_ns)o;
								byte[] ns_name = ns.Name_ui();
								int ns_name_len = ns_name.length;
								int tmp_bfr_end = bfr.Len();
								if (!Bry_.Eq(bfr.Bfr(), ltr_bgn, tmp_bfr_end, ns_name) && ns_name_len == tmp_bfr_end - ltr_bgn) {	// if (a) ns_name != bfr_txt (b) both are same length; note that (b) should not happen, but want to safeguard against mismatched arrays
									Bry_.Set(bfr.Bfr(), ltr_bgn, tmp_bfr_end, ns_name);
								}
								ns_bgn = ltr_bgn;
								part_found = true;
							}
						}
						if (part_found) {
							page_bgn = txt_bb_len + 1;	// anticipate page_bgn;
							add_ws = false;				// if there was an add_ws, ignore; EX: "Category :" should ignore space
							ltr_bgn_reset = true;		// ltr_bgn_reset
						}
						colon_count++;		// increment colon count
						break;
					}
				case Byte_ascii.Hash: 
					if (anch_bgn == -1)	// anchor begins at 1st #, not last #; EX:A#B#C has anchor of "B#C" not "C" PAGE:en.w:Grand_Central_Terminal; DATE:2015-12-31
						anch_bgn = (txt_bb_len) + 1; 
					break;
				case Byte_ascii.Slash:
					if (root_bgn == -1)
						root_bgn = (txt_bb_len) + 1;
					if (anch_bgn == -1) {	// only set leaf if anchor found; guards against A#B/C and / setting leaf; DATE:2014-01-14
						leaf_bgn = (txt_bb_len) + 1;
						qarg_bgn = -1;	// always reset qarg; handles ttls which have question_mark which are premptively assumed to be qarg; PAGE:en.w:Portal:Organized_Labour/Did_You_Know?/1 DATE:2014-06-08
					}
					break;	// flag last leaf_bgn
				case Byte_ascii.Nl:	// NOTE: for now, treat nl just like space; not sure if it should accept "a\nb" or "\nab"; need to handle trailing \n for "Argentina\n\n" in {{Infobox settlement|pushpin_map=Argentina|pushpin_label_position=|pushpin_map_alt=|pushpin_map_caption=Location of Salta in Argentina}};
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Cr:	// added \t, \r; DATE:2013-03-27
				case Byte_ascii.Underline: if (ltr_bgn != -1) add_ws = true; ++cur;
					continue;	// only mark add_ws if ltr_seen; this ignores ws at bgn; also, note "continue"
				case Byte_ascii.Question:
					if (txt_bb_len + 1 < end)	// guard against trailing ? (which shouldn't happen)
						qarg_bgn = txt_bb_len + 1;
					break;
				case Byte_ascii.Amp:
					int cur2 = cur + 1;
					if (cur2 == end) {}	// guards against terminating &; EX: [[Bisc &]]; NOTE: needed b/c Match_bgn does not do bounds checking for cur in src; src[src.length] will be called when & is last character;
					else {
						if (trv == null) trv = new Btrie_rv();
						Object html_ent_obj = amp_trie.Match_at(trv, src, cur2, end);
						if (html_ent_obj != null) {									
							Gfh_entity_itm amp_itm = (Gfh_entity_itm)html_ent_obj;
							match_pos = trv.Pos();
							if (amp_itm.Tid() == Gfh_entity_itm.Tid_name_std) {
								switch (amp_itm.Char_int()) {
									case 160:	// NOTE: &nbsp must convert to space; EX:w:United States [[Image:Dust Bowl&nbsp;- Dallas, South Dakota 1936.jpg|220px|alt=]]
										if (ltr_bgn != -1) add_ws = true;	// apply same ws rules as Space, NewLine; needed for converting multiple ws into one; EX:" &nbsp; " -> " " x> "   "; PAGEen.w:Greek_government-debt_crisis; DATE:2014-09-25
										cur = match_pos; // set cur after ";"
										continue;
									case Byte_ascii.Amp:
										b_ary = Byte_ascii.Amp_bry;		// NOTE: if &amp; convert to &; PAGE:en.w:Amadou Bagayoko?redirect=n; DATE:2014-09-23
										break;
									case Byte_ascii.Quote:
									case Byte_ascii.Lt:
									case Byte_ascii.Gt:
										b_ary = amp_itm.Xml_name_bry();
										break;
									case Gfh_entity_itm.Char_int_null:	// &#xx;
										int end_pos = Bry_find_.Find_fwd(src, Byte_ascii.Semic, match_pos, end);
										if (end_pos == Bry_find_.Not_found) {} // &# but no terminating ";" noop: defaults to current_byte which will be added below;
										else {
											b_ary = amp_itm.Xml_name_bry();									
											match_pos = end_pos + 1;
										}
										break;
									default:
										b_ary = amp_itm.U8_bry();
										break;
								}
							}
							else {
								Xop_amp_mgr_rslt amp_rv = new Xop_amp_mgr_rslt();
								amp_mgr.Parse_ncr(amp_rv, amp_itm.Tid() == Gfh_entity_itm.Tid_num_hex, src, end, cur2, match_pos);
								if (amp_rv.Pass()) {
									b_ary = gplx.core.intls.Utf16_.Encode_int_to_bry(amp_rv.Val());
									if (b_ary.length == 1 && b_ary[0] == Byte_ascii.Hash)	// NOTE: A&#x23;B should be interpreted as A#b; PAGE:en.s:The_English_Constitution_(1894) DATE:2014-09-07
										anch_bgn = (txt_bb_len) + 1; 
									match_pos = amp_rv.Pos();
								}
							}
						}
					}
					break;
				case Byte_ascii.Lt:
					if (cur + 3 < end) {
						if (	src[cur + 1] == Byte_ascii.Bang
							&&	src[cur + 2] == Byte_ascii.Dash
							&&	src[cur + 3] == Byte_ascii.Dash
							) {
							int cur3 = cur + 3;
							int find = Bry_find_.Find_fwd(src, Xop_comm_lxr.End_ary, cur3, end);
							if (find != -1) {
								cur = find + Xop_comm_lxr.End_ary.length;
								continue;
							}
							else
								return false;
						}
					}
					if (anch_bgn != -1) {
						if (anchor_encoder == null) {
							anchor_encoder = Gfo_url_encoder_.Id;
							anchor_encoder_bfr = Bry_bfr_.Reset(32);
						}
						anchor_encoder.Encode(anchor_encoder_bfr, src, cur, cur + 1);
						b_ary = anchor_encoder_bfr.To_bry_and_clear();
						match_pos = cur + 1;
					}
					break;
				// NOTE: DefaultSettings.php defines wgLegalTitleChars as " %!\"$&'()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+"; the characters above are okay; those below are not
				case Byte_ascii.Gt: case Byte_ascii.Pipe:
				case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Curly_bgn: case Byte_ascii.Curly_end:
					if (anch_bgn != -1) {
						if (anchor_encoder == null) {
							anchor_encoder = Gfo_url_encoder_.Id;
							anchor_encoder_bfr = Bry_bfr_.Reset(32);
						}
						anchor_encoder.Encode(anchor_encoder_bfr, src, cur, cur + 1);
						b_ary = anchor_encoder_bfr.To_bry_and_clear();
						match_pos = cur + 1;
					}
					else
						return false;
					break;
				default:
					if ((b & 0xff) > 127) {// PATCH.JAVA:need to convert to unsigned byte
						if (trv == null) trv = new Btrie_rv();
						Object char_obj = char_trie.Match_at_w_b0(trv, b, src, cur, end);
						if (char_obj != null) {
							int tid = ((Int_obj_val)(char_obj)).Val();
							cur = trv.Pos();
							switch (tid) {
								case Char__bidi:	// ignore bidi
									continue;
								case Char__ws:		// treat extended_ws as space; PAGE:ja.w:Template:Location_map_USA New_York; DATE:2015-07-28
									if (ltr_bgn != -1) add_ws = true;
									continue;
							}								
						}
					}
					break;
			}
			++cur;
			if (add_ws) {	// add ws and toggle flag
				bfr.Add_byte(Byte_ascii.Space); ++txt_bb_len;
				add_ws = false;
			}
			if (ltr_bgn == -1) ltr_bgn = txt_bb_len; // if 1st letter not seen, mark 1st letter					
			if		(b_ary == null) {bfr.Add_byte(b); ++txt_bb_len;} // add to bfr
			else					{bfr.Add(b_ary); txt_bb_len += b_ary.length; b_ary = null; cur = match_pos;}	// NOTE: b_ary != null only for amp_trie
			if (ltr_bgn_reset)  {// colon found; set ws to bgn mode; note that # and / do not reset 
				ltr_bgn_reset = false;
				ltr_bgn = -1;
			}
		}
		if (txt_bb_len == 0) return false;
		if (wik_bgn == -1 && page_bgn == txt_bb_len) return false;	// if no wiki, but page_bgn is at end, then ttl is ns only; EX: "Help:"; NOTE: "fr:", "fr:Help" is allowed 
		full_txt = bfr.To_bry_and_clear();
		if (	ns.Case_match() == Xow_ns_case_.Tid__1st
			&&	wik_bgn == -1 ) {	// do not check case if xwiki; EX: "fr:" would have a wik_bgn of 0 (and a wik_end of 3); "A" (and any non-xwiki ttl) would have a wik_bgn == -1
			byte char_1st = full_txt[page_bgn];
			int char_1st_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(char_1st);
			int page_end = page_bgn + char_1st_len;
			if (	char_1st_len > 1) {			// 1st char is multi-byte char
				int full_txt_len = full_txt.length;
				if (page_end > full_txt_len)	// ttl is too too short for 1st multi-byte char; EX: [[%D0]] is 208 but in utf8, 208 requires at least another char; DATE:2013-11-11
					return false;				// ttl is invalid
				else {							// ttl is long enough for 1st mult-byte char; need to use platform uppercasing; Xol_case_mgr_.Utf_8 is not sufficient
					Bry_bfr upper_1st = Bry_bfr_.Get();
					byte[] page_txt = case_mgr.Case_build_1st_upper(upper_1st, full_txt, page_bgn, full_txt_len);	// always build; never reuse; (multi-byte character will expand array)
					if (page_bgn == 0)			// page only; EX: A
						full_txt = page_txt;
					else						// ns + page; EX: Help:A
						full_txt = Bry_.Add(Bry_.Mid(full_txt, 0, page_bgn), page_txt);	// add page_txt to exsiting ns
					upper_1st.Mkr_rls();
				}
			}
			else
				full_txt = case_mgr.Case_reuse_upper(full_txt, page_bgn, page_end);
		}
		Xow_ns tors_ns = ns.Id_is_talk() ? ns_mgr.Ords_get_at(ns.Ord_subj_id()) : ns_mgr.Ords_get_at(ns.Ord_talk_id());
		tors_txt = tors_ns.Name_ui_w_colon();
		// tors_txt = tors_ns == null ? Bry_.Empty : tors_ns.Name_ui_w_colon();
		return true;
	}		
}
class Xoa_ttl_trie {
	public static Btrie_fast_mgr new_() {
		Btrie_fast_mgr rv = Btrie_fast_mgr.cs();
		rv.Add(Byte_ascii.Colon				, Byte_obj_val.new_(Id_colon));
		rv.Add(Byte_ascii.Hash				, Byte_obj_val.new_(Id_hash));
		rv.Add(Byte_ascii.Slash				, Byte_obj_val.new_(Id_slash));
		rv.Add(Byte_ascii.Space				, Byte_obj_val.new_(Id_space));
		rv.Add(Byte_ascii.Underline			, Byte_obj_val.new_(Id_underline));
		rv.Add(Byte_ascii.Amp				, Byte_obj_val.new_(Id_amp));
		rv.Add(Xop_comm_lxr.Bgn_ary			, Byte_obj_val.new_(Id_comment_bgn));
		rv.Add(Byte_ascii.Nl				, Byte_obj_val.new_(Id_newLine));
		rv.Add(Byte_ascii.Brack_bgn			, Byte_obj_val.new_(Id_invalid));
		rv.Add(Byte_ascii.Curly_bgn			, Byte_obj_val.new_(Id_invalid));
		return rv;
	}
	public static final byte Id_colon = 0, Id_hash = 1, Id_slash = 2, Id_space = 3, Id_underline = 4, Id_amp = 5, Id_comment_bgn = 6, Id_invalid = 7, Id_newLine = 8;
}
