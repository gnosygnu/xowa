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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
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
		Xol_lang lang = fxt.Parser_fxt().Wiki().App().Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_("fr"));
		Init_msg(lang, "sun", "dim");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)keys_ary_lang("sun", "fr"))				, "dim");
	}
	@Test  public void Plain_rawMessage() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_message.Invk_plain, Object_.Ary((Object)Scrib_kv_utl_.flat_many_("rawMessage", "$1", "params", KeyVal_.Ary(KeyVal_.int_(1, "abc")))), "abc");
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
	private void Init_msg(Xol_lang lang, String key, String val) {
		lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_ascii_(key)).Atrs_set(Bry_.new_ascii_(val), false, false);
	}
	KeyVal[] keys_ary(String msg_key) {return keys_ary(msg_key, null, null);}
	KeyVal[] keys_ary_arg(String msg_key, String arg) {return keys_ary(msg_key, null, arg);}
	KeyVal[] keys_ary_lang(String msg_key, String lang) {return keys_ary(msg_key, lang, null);}
	KeyVal[] keys_ary(String msg_key, String lang, String arg) {
		boolean arg_exists = arg != null;
		boolean lang_exists = lang != null;
		int idx = 0;
		KeyVal[] rv = new KeyVal[1 + (arg_exists ? 1 : 0) + (lang_exists ? 1 : 0)];
		rv[0] = KeyVal_.new_("keys", KeyVal_.Ary(KeyVal_.int_(1, msg_key)));
		if (arg_exists)
			rv[++idx] = KeyVal_.new_("params", KeyVal_.Ary(KeyVal_.int_(1, arg)));
		if (lang_exists)
			rv[++idx] = KeyVal_.new_("lang", lang);
		return rv;
	}
}	
