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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Scrib_lib_message_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_message().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test   public void Plain() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)keys_ary("sun"))							, "Sun");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)keys_ary("sunx"))						, "&lt;sunx&gt;");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)keys_ary_arg("redirectedfrom", "A"))		, "(Redirected from A)");
	}
	@Test  public void Plain_lang() {
		Xol_lang_itm lang = fxt.Parser_fxt().Wiki().Appe().Lang_mgr().Get_by_or_new(Bry_.new_a7("fr"));
		Init_msg(lang, "sun", "dim");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)keys_ary_lang("sun", "fr"))				, "dim");
	}
	@Test  public void Plain_rawMessage() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)Scrib_kv_utl_.flat_many_("rawMessage", "$1", "params", Keyval_.Ary(Keyval_.int_(1, "abc")))), "abc");
	}
	@Test  public void Plain_rawMessage_empty() {// PURPOSE:rawMessage would throw null ref if rawMessage called template that returns empty value; PAGE:it.w:L'Internazionale DATE:2015-02-25
		fxt.Parser_fxt().Init_page_create("Template:Msg", "");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)Scrib_kv_utl_.flat_many_("rawMessage", "{{Msg}}", "params", Keyval_.Ary(Keyval_.int_(1, "abc")))), "");
	}
	@Test   public void Check() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("exists"				, keys_ary("sun"))							, true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("exists"				, keys_ary("sunx"))							, false);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("isBlank"				, keys_ary("sun"))							, false);
		Init_msg("blank", "");			
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("isBlank"				, keys_ary("blank"))						, true);
		Init_msg("disabled", "-");			
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("isDisabled"			, keys_ary("sun"))							, false);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("isDisabled"			, keys_ary("blank"))						, true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("isDisabled"			, keys_ary("disabled"))						, true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_message.Invk_check, Object_.Ary("isBlank"				, keys_ary("disabled"))						, false);
	}
	@Test  public void Init_message_for_lang() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_init_message_for_lang, Object_.Ary_empty						, "lang=en");
	}
	private void Init_msg(String key, String val) {Init_msg(fxt.Core().Wiki().Lang(), key, val);}
	private void Init_msg(Xol_lang_itm lang, String key, String val) {
		lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_a7(key)).Atrs_set(Bry_.new_a7(val), false, false);
	}
	Keyval[] keys_ary(String msg_key) {return keys_ary(msg_key, null, null);}
	Keyval[] keys_ary_arg(String msg_key, String arg) {return keys_ary(msg_key, null, arg);}
	Keyval[] keys_ary_lang(String msg_key, String lang) {return keys_ary(msg_key, lang, null);}
	Keyval[] keys_ary(String msg_key, String lang, String arg) {
		boolean arg_exists = arg != null;
		boolean lang_exists = lang != null;
		int idx = 0;
		Keyval[] rv = new Keyval[1 + (arg_exists ? 1 : 0) + (lang_exists ? 1 : 0)];
		rv[0] = Keyval_.new_("keys", Keyval_.Ary(Keyval_.int_(1, msg_key)));
		if (arg_exists)
			rv[++idx] = Keyval_.new_("params", Keyval_.Ary(Keyval_.int_(1, arg)));
		if (lang_exists)
			rv[++idx] = Keyval_.new_("lang", lang);
		return rv;
	}
}	
