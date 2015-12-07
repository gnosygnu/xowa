/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
//namespace gplx.xowa.htmls.core.wkrs.xndes.styles {
//	using gplx.langs.htmls.parsers;
//	using gplx.xowa.htmls.core.hzips;
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
//		private final Ordered_hash tag_hash = Ordered_hash_.New();
//		public Hz_tag_itm Tags__get_or_new(int name_id) {
//			return null;
//		}
//		public Hz_atr_itm Atrs__get_or_new(byte[] key) {
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
//			Hz_atr_itm[] atrs = ztag.Atrs();
//			int len = atrs.length;
//			Xoh_hzip_int_.Encode(1, bfr, ztag.Id());
//			for (int i = 0; i < len; ++i) {
//				Hz_atr_itm atr = atrs[i];
//				Save_atr(bfr, atr, Byte_ascii.Escape);
//			}
//			bfr.Add_byte_nl();
//		}
//		private void Save_atr(Bry_bfr bfr, Hz_atr_itm atr, byte dlm) {
//			// Xoh_hzip_int_.Encode(1, bfr, atr.Id());
//			Hz_val_itm[] vals = atr.Itms();
//			int len = vals.length;
//			for (int i = 0; i < len; ++i) {
//				Hz_val_itm val = vals[i];
//				bfr.Add(atr.Text()).Add_byte(dlm);
//			}
//		}
//	}
//	class Hz_tag_hzip {
//		private static final byte[] Hook_tag = Bry_.new_a7("~z");
//		public void Encode(Bry_bfr bfr, Hz_regy regy, Html_tag htag) {
//			Hz_tag_itm ztag = regy.Tags__get_or_new(htag.Name_id());
//
//			bfr.Add(Hook_tag);
//			Xoh_hzip_int_.Encode(1, bfr, ztag.Id());
//			int len = htag.Atrs__len();
//			for (int i = 0; i < len; ++i) {
//				Html_atr hatr = htag.Atrs__get_at(i);
//				Hz_atr_itm zatr = regy.Atrs__get_or_new(hatr.Key());
//				Xoh_hzip_int_.Encode(1, bfr, zatr.Id());
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
//		public Hz_tag_itm(int id, byte[] text, boolean scope_is_doc, boolean indexable) {
//			this.id = id; this.text = text; this.scope_is_doc = scope_is_doc; this.indexable = indexable;
//		}
//		public int Id() {return id;} private int id;
//		public byte[] Text() {return text;} private byte[] text;
//		public boolean Scope_is_doc() {return scope_is_doc;} private boolean scope_is_doc;
//		public boolean Indexable() {return indexable;} private boolean indexable;
//		public Hz_atr_itm[] Atrs() {if (atr_ary_is_dirty) atr_ary = (Hz_atr_itm[])atr_list.To_ary(typeof(Hz_atr_itm)); return atr_ary;} private Hz_atr_itm[] atr_ary; private boolean atr_ary_is_dirty = false;
//		private void Add(byte[] text) {
//			if (atr_list == null) atr_list = List_adp_.new_();
//			atr_list.Add(new Hz_atr_itm(atr_list.Count(), text, Bool_.Y, Bool_.Y));
//		}
//	}
//	class Hz_atr_itm {
//		private List_adp list;
//		public Hz_atr_itm(int id, byte[] text, boolean scope_is_doc, boolean indexable) {
//			this.id = id; this.text = text; this.scope_is_doc = scope_is_doc; this.indexable = indexable;
//		}
//		public int Id() {return id;} private int id;
//		public byte[] Text() {return text;} private byte[] text;
//		public boolean Scope_is_doc() {return scope_is_doc;} private boolean scope_is_doc;
//		public boolean Indexable() {return indexable;} private boolean indexable;
//		public Hz_val_itm[] Itms() {if (ary_is_dirty) ary = (Hz_val_itm[])list.To_ary(typeof(Hz_val_itm)); return ary;} private Hz_val_itm[] ary; private boolean ary_is_dirty = false;
//		private void Itms__add(byte[] text) {
//			if (list == null) list = List_adp_.new_();
//			ary_is_dirty = true;
//			list.Add(new Hz_val_itm(list.Count(), text, Bool_.Y));
//		}
//		public void Write(Bry_bfr bfr, byte[] text) {
//			Xoh_hzip_int_.Encode(1, bfr, 1);
//		}
//	}
//	class Hz_val_itm {
//		public Hz_val_itm(int id, byte[] text, boolean scope_is_doc) {
//			this.id = id; this.text = text; this.scope_is_doc = scope_is_doc;
//		}
//		public int Id() {return id;} private int id;
//		public byte[] Text() {return text;} private byte[] text;
//		public boolean Scope_is_doc() {return scope_is_doc;} private boolean scope_is_doc;
//	}
//}
