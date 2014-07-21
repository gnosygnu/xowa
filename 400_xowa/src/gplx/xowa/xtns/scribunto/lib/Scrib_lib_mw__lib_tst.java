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
public class Scrib_lib_mw__lib_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_mw().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void ParentFrameExists() {
		fxt.Init_frame_parent("test");
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_parentFrameExists, Object_.Ary_empty, true);
	}
	@Test  public void ParentFrameExists_false() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_parentFrameExists, Object_.Ary_empty, false);
	}
	@Test  public void GetAllExpandedArguments() {
		fxt.Init_frame_current(KeyVal_.new_("k1", "v1"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("current"), "1=\n  k1=v1");
	}
	@Test  public void GetAllExpandedArguments_parent() {
		fxt.Init_frame_parent("test", KeyVal_.new_("1", "a1"), KeyVal_.new_("2", "a2"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("parent"), "1=\n  1=a1\n  2=a2");
	}
	@Test  public void GetExpandedArgument() {
		fxt.Init_frame_current(KeyVal_.int_(1, "val_1"), KeyVal_.new_("key_2", "val_2"), KeyVal_.int_(3, "val_3"));
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, "val_1");		// get 1st by idx
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "2")		, "val_3");		// get 2nd by idx (which is "3", not "key_2)
		fxt.Test_scrib_proc_empty	(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "3"));						// get 3rd by idx (which is n/a, not "val_3")
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key_2")	, "val_2");		// get key_2
		fxt.Test_scrib_proc_empty	(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key_3"));					// key_3 n/a
	}
	@Test  public void GetExpandedArgument_parent() {
		fxt.Init_frame_parent ("test", KeyVal_.new_("1", "a1"), KeyVal_.new_("2", "a2"));
		fxt.Init_frame_current(KeyVal_.new_("2", "b2"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("parent", "1"), "a1");
	}
	@Test  public void GetExpandedArgument_numeric_key() {		// PURPOSE.FIX: frame.args[1] was ignoring "1=val_1" b/c it was looking for 1st unnamed arg (and 1 is the name for "1=val_1")
		fxt.Init_frame_current(KeyVal_.new_("1", "val_1"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1"), "val_1");			// get 1st by idx, even though idx is String
	}
	@Test  public void GetExpandedArgument_numeric_key_2() {	// PURPOSE.FIX: same as above, but for parent context; DATE:2013-09-23
		fxt.Init_frame_parent ("test", KeyVal_.new_("2", "a1"));
		fxt.Init_frame_current(KeyVal_.new_("2", "a2"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("parent", "2"), "a1");				// get 1st by idx, even though idx is String
	}
	@Test  public void GetExpandedArgument_out_of_bounds() {
		fxt.Init_frame_parent ("test");
		fxt.Test_scrib_proc_empty(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("parent", "2"));
	}
	@Test  public void IsSubsting() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_isSubsting, Object_.Ary_empty, false);
	}
	@Test  public void GetFrameTitle_current() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getFrameTitle, Object_.Ary("current")	, "Module:Mod_0");
	}
	@Test  public void GetFrameTitle_parent() {
		fxt.Init_frame_parent("Template:Test");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getFrameTitle, Object_.Ary("parent")	, "Template:Test");
	}
	@Test  public void GetFrameTitle_empty() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getFrameTitle, Object_.Ary("empty")	, Scrib_invoke_func_fxt.Null_rslt);
	}
	@Test  public void NewChildFrame() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_newChildFrame, Object_.Ary("current", "Page_0", Scrib_kv_utl_.flat_many_("key1", "val1")), "frame0");
	}
	@Test  public void SetTTL() {
		fxt.Test_scrib_proc_empty(lib, Scrib_lib_mw.Invk_setTTL, Object_.Ary(123));
		Tfds.Eq(123, fxt.Core().Frame_current().Frame_lifetime());
	}
}