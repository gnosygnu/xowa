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
package gplx.xowa.htmls.core.wkrs.xndes.styles; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*;
//	class Hz_regy_load {
//		public void Load(byte[] src, int bgn, int end) {
//			int pos = bgn;
//			pos = Load_doc_tags(src, pos, end);
//			pos = Load_doc_atrs(src, pos, end);
//		}
//		private int Load_doc_tags(byte[] src, int bgn, int end) {	// EX: "table\ntr\ntd\n\n"
//			int pos = bgn;
//			int tag_bgn = pos;
//			while (pos < end) {
//				if (src[pos] == Byte_ascii.Nl) {
//					if (pos - tag_bgn == 0) break;	// \n\n denotes end of doc tags
//					Tags__add(Bry_.Mid(src, tag_bgn, pos));
//					tag_bgn = pos + 1;
//				}
//			}
//			return pos;
//		}
//		private int Load_doc_atrs(byte[] src, int bgn, int end) {	// EX: "0colspan style\n1colspan style"
//			int pos = bgn;
//			// read tag_id
//			int atr_bgn = pos;
//			while (pos < end) {
//				byte b = src[pos];
//				switch (b) {
//					case Byte_ascii.Space: 
////						Tags__add(Bry_.Mid(src, tag_bgn, pos));
//						atr_bgn = pos + 1;
//						break;
//				}
//			}
//			return pos;
//		}
//		private int Load_atr_vals(byte[] src, int bgn, int end) {	// EX: "01left;right;\n2left;right;\n\n11"
//			return -1;
//		}
//		private int Load_tag(byte[] src, int bgn, int end) {
////			byte[] tag_name
////			this.Tags__add();
////			int pos = bgn;
////			while (pos < end) {
////				byte b = src[pos];
////				
////			}
//			return -1;
//		}
//	}
//	class Hz_regy {
//		private final    Ordered_hash tag_hash = Ordered_hash_.New();
//		public Hz_tag_itm Tags__get_or_new(int name_id) {
//			return null;
//		}
//		public Xohz_style_itm Atrs__get_or_new(byte[] key) {
//			return null;
//		}
//		public void Tags__add(byte[] key) {
//		}
//		public void Save(Bry_bfr bfr) {
//			int len = tag_hash.Count();
//			for (int i = 0; i < len; ++i) {
//				Hz_tag_itm ztag = (Hz_tag_itm)tag_hash.Get_at(i);
//				if (!ztag.Scope_is_doc())
//					Save_tag(bfr, ztag);
//			}
//			for (int i = 0; i < len; ++i) {
//				Hz_tag_itm ztag = (Hz_tag_itm)tag_hash.Get_at(i);
//				if (ztag.Scope_is_doc())
//					Save_tag(bfr, ztag);
//			}
//			/*
//			table
//			td
//			
//			01a
//			atrs
//			----
//			
//			
//			<div class='a b c' style='text-align:left'>
//			~d9ABC~F!
//			<div class='a b c' style='text-align:left'>
//			td|scope|new_val
//			00new_val
//			td|new_atr|new_val|new_val2
//			*/
//		}
//		private void Save_tag(Bry_bfr bfr, Hz_tag_itm ztag) {
//			Xohz_style_itm[] atrs = ztag.Atrs();
//			int len = atrs.length;
//			Gfo_hzip_int_.Encode(1, bfr, ztag.Id());
//			for (int i = 0; i < len; ++i) {
//				Xohz_style_itm atr = atrs[i];
//				Save_atr(bfr, atr, Byte_ascii.Escape);
//			}
//			bfr.Add_byte_nl();
//		}
//		private void Save_atr(Bry_bfr bfr, Xohz_style_itm atr, byte dlm) {
//			// Gfo_hzip_int_.Encode(1, bfr, atr.Id());
//			Xohz_style_val[] vals = atr.Itms();
//			int len = vals.length;
//			for (int i = 0; i < len; ++i) {
//				Xohz_style_val val = vals[i];
//				bfr.Add(atr.Text()).Add_byte(dlm);
//			}
//		}
//	}
//	class Hz_tag_hzip {
//		private static final    byte[] Hook_tag = Bry_.new_a7("~z");
//		public void Encode(Bry_bfr bfr, Hz_regy regy, Gfh_tag htag) {
//			Hz_tag_itm ztag = regy.Tags__get_or_new(htag.Name_id());
//
//			bfr.Add(Hook_tag);
//			Gfo_hzip_int_.Encode(1, bfr, ztag.Id());
//			int len = htag.Atrs__len();
//			for (int i = 0; i < len; ++i) {
//				Gfh_atr hatr = htag.Atrs__get_at(i);
//				Xohz_style_itm zatr = regy.Atrs__get_or_new(hatr.Key());
//				Gfo_hzip_int_.Encode(1, bfr, zatr.Id());
//				byte[] hval = hatr.Val();
//				if (zatr.Indexable())
//					zatr.Write(bfr, hval);
//				else
//					bfr.Add(hval);
//			}
//		}
//	}
//	class Hz_tag_itm {
//		private List_adp atr_list;
//		public Hz_tag_itm(int uid, byte[] text, boolean scope_is_doc, boolean indexable) {
//			this.uid = uid; this.text = text; this.scope_is_doc = scope_is_doc; this.indexable = indexable;
//		}
//		public int Id() {return uid;} private int uid;
//		public byte[] Text() {return text;} private byte[] text;
//		public boolean Scope_is_doc() {return scope_is_doc;} private boolean scope_is_doc;
//		public boolean Indexable() {return indexable;} private boolean indexable;
//		public Xohz_style_itm[] Atrs() {if (atr_ary_is_dirty) atr_ary = (Xohz_style_itm[])atr_list.To_ary(typeof(Xohz_style_itm)); return atr_ary;} private Xohz_style_itm[] atr_ary; private boolean atr_ary_is_dirty = false;
//		private void Add(byte[] text) {
//			if (atr_list == null) atr_list = List_adp_.New();
//			atr_list.Add(new Xohz_style_itm(atr_list.Count(), text, Bool_.Y, Bool_.Y));
//		}
//	}
//	class Xohz_style_itm {
//		private List_adp list;
//		public Xohz_style_itm(int uid, byte[] key, boolean scope_is_doc, boolean indexable) {
//			this.uid = uid; this.key = key; this.scope_is_doc = scope_is_doc; this.indexable = indexable;
//		}
//		public int Uid() {return uid;} private int uid;
//		public byte[] Text() {return key;} private byte[] key;
//		public boolean Scope_is_doc() {return scope_is_doc;} private boolean scope_is_doc;
//		public boolean Indexable() {return indexable;} private boolean indexable;
//		public Xohz_style_val[] Itms() {if (ary_is_dirty) ary = (Xohz_style_val[])list.To_ary(typeof(Xohz_style_val)); return ary;} private Xohz_style_val[] ary; private boolean ary_is_dirty = false;
//		private void Itms__add(byte[] key) {
//			if (list == null) list = List_adp_.New();
//			ary_is_dirty = true;
//			list.Add(new Xohz_style_val(list.Count(), key, Bool_.Y));
//		}
//		public void Write(Bry_bfr bfr, byte[] key) {
//			Gfo_hzip_int_.Encode(1, bfr, 1);
//		}
//	}
//	class Xohz_style_val {
//		public Xohz_style_val(int uid, byte[] txt, boolean scope_is_doc) {
//			this.uid = uid; this.txt = txt; this.scope_is_doc = scope_is_doc;
//		}
//		public int Uid() {return uid;} private final    int uid;
//		public byte[] Txt() {return txt;} private final    byte[] txt;
//		public boolean Scope_is_doc() {return scope_is_doc;} private boolean scope_is_doc;
//	}
