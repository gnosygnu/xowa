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
//namespace gplx.xowa.htmls.core.hzips.wkrs {
//	using gplx.core.btries;
//	using gplx.xowa.htmls.core.hzips; 
//	public class Xoh_hzip_href {
//		public void Save(Bry_bfr bfr, Xoh_stat_itm stats, byte[] src, int src_len, int bgn, int pos, byte bgn_quote) {
////			// ignore anchors; EX: "#a"
////			int proto_bgn	= pos;
////			int proto_end	= Bry_find_.Find_fwd(src, Byte_ascii.Colon, proto_bgn, src_len);
////			byte proto_tid	= Tid_proto_other;
////			if (proto_end != Bry_find_.Not_found) {
////				Object proto_obj = proto_trie.Match_exact(src, pos, proto_bgn);
////				if (proto_obj != null)
////					proto_tid = ((Byte_obj_val)proto_obj).Val();
////				pos = Bry_find_.Find_fwd_while(src, proto_bgn + 1, src_len, Byte_ascii.Slash);	// eat /; EX: http:// should position after /
////			}
////			// stats.Lnke_proto_reg(proto_tid, src, proto_bgn, proto_end);
////
////			int domain_bgn		= pos;
////			int domain_end		= Bry_find_.Find_fwd(src, Byte_ascii.Slash, domain_bgn, src_len);
////			if (domain_end == Bry_find_.Not_found)		// href has no slash; assume entire String is domain EX: "www.a.org"
////				domain_end = Bry_find_.Find_fwd(src, bgn_quote, pos, src_len);
////
////			int tld_pos = Bry_find_.Find_bwd(src, Byte_ascii.Dot, domain_bgn, src_len);
////			byte tld_tid = Tid_tld_other;
////			if (tld_pos != Bry_find_.Not_found) {
////				Object tld_obj = tld_trie.Match_exact(src, domain_bgn, domain_end);
////				if (tld_obj != null)
////					tld_tid = ((Byte_obj_val)tld_obj).Val();
////				pos = Bry_find_.Find_fwd_while(src, domain_bgn + 1, src_len, Byte_ascii.Slash);	// eat /; EX: http:// should position after /
////			}
////			// stats.Lnke_tld_reg(tld_tid, src, domain_bgn, domain_end);
//		}
//		public static final byte	// 2
//		  Tid_proto_other	= 0
//		, Tid_proto_http	= 1
//		, Tid_proto_https	= 2
//		;
//		public static final byte	// 3
//		  Tid_tld_other		= 0
//		, Tid_tld_com		= 1
//		, Tid_tld_org		= 2
//		, Tid_tld_net		= 3
//		, Tid_tld_gov		= 4
//		;
//		public static final byte	// 3
//		  Tid_ext_other		= 0
//		, Tid_ext_none		= 1
//		, Tid_ext_htm		= 2
//		, Tid_ext_html		= 3
//		, Tid_ext_php		= 4
//		, Tid_ext_jsp		= 5
//		, Tid_ext_asp		= 6
//		, Tid_ext_aspx		= 7
//		;
////		private static final Btrie_slim_mgr proto_trie = Btrie_slim_mgr.ci_a7()
////		.Add_str_byte("http", Tid_proto_http)
////		.Add_str_byte("https", Tid_proto_http)
////		;
////		private static final Btrie_slim_mgr tld_trie = Btrie_slim_mgr.ci_a7()
////		.Add_str_byte("com", Tid_tld_com)
////		.Add_str_byte("org", Tid_tld_org)
////		.Add_str_byte("net", Tid_tld_net)
////		.Add_str_byte("gov", Tid_tld_gov)
////		;
////		private static final Btrie_slim_mgr ext_trie = Btrie_slim_mgr.ci_a7()
////		.Add_str_byte("htm", Tid_ext_htm)
////		.Add_str_byte("html", Tid_ext_html)
////		.Add_str_byte("php", Tid_ext_php)
////		.Add_str_byte("jsp", Tid_ext_jsp)
////		.Add_str_byte("asp", Tid_ext_asp)
////		.Add_str_byte("aspx", Tid_ext_aspx)
////		;		
//		// <a href="/site/simple.wikipedia.org/wiki/Template:Solar_System?action=edit"><span title="Edit this template" style="">e</span></a>	// xwiki [[simple:xx
//		// <a href="http://planetarynames.wr.usgs.gov/jsp/append5.jsp" class="external text" rel="nofollow">"Descriptor Terms (Feature Types)"</a>
//		/*
//		0: proto,tld,ext
//		1-n: domain
//		n: 0: domain_end
//		n: url remainder
//		n: 0: url_end
//		*/
//	}
//}
