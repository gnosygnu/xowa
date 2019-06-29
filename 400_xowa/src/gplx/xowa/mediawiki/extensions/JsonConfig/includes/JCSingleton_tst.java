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
//namespace gplx.xowa.mediawiki.extensions.JsonConfig.includes {
//	import org.junit.*;
//	using gplx.langs.jsons;
//	using gplx.xowa.mediawiki;
//	using gplx.xowa.xtns.jsonConfigs.scribunto;
//	public class JCSingleton_tst {
//		private final    JCSingleton_fxt fxt = new JCSingleton_fxt();
//		@Test   public void Get() {
//			fxt.Init__store("en.wikipedia.org", "Page1"
//			, Json_doc.Make_str_by_apos
//			( "{"
//			, "  'data':"
//			, "  ["
//			, "    ["
//			, "      'Q1'"
//			, "    , 'Data:Q1'"
//			, "    ]"
//			, "  ,"
//			, "    ["
//			, "      'Q2'"
//			, "    , 'Data:Q2'"
//			, "    ]"
//			, "  ]"
//			, "}"
//			));
//			JCContent actl = fxt.Exec__getContent("en.wikipedia.org", "Page1");
//			Object o = ((JCTabularContent)actl).getField("data");
//            Tfds.Write(o);
//			/*
//			fxt.Test__get(actl, "data", "Q1")
//			*/
//		}
//	}
//	class JCSingleton_fxt {
//		private final    JCSingleton singleton;
//		private final    Xomw_page_fetcher__mock store = new Xomw_page_fetcher__mock();
//		public JCSingleton_fxt() {
//			Jscfg_xtn_mgr xtn_mgr = new Jscfg_xtn_mgr();
//			xtn_mgr.Init_xtn();
//
//			singleton = (JCSingleton)XophpEnv.Instance.Singletons().Get_by(JCSingleton.Singleton_Id);
//			singleton.Store_(store);
//		}
//		public void Init__store(String wiki, String page, String json) {
//			store.Set_wtxt(Bry_.new_u8(wiki), Bry_.new_u8(page), Bry_.new_u8(json));
//		}
//		public JCContent Exec__getContent(String wiki, String page) {
//			return singleton.getContent(wiki, "unknown_ns", page);
//		}
//	}
//}
