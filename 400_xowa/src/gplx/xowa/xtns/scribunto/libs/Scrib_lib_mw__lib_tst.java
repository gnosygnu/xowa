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
package gplx.xowa.xtns.scribunto.libs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
public class Scrib_lib_mw__lib_tst {
	private final Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_mw().Init();
	}
	@Test public void ParentFrameExists() {
		fxt.Init_frame_parent("test");
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_parentFrameExists, ObjectUtl.AryEmpty, true);
	}
	@Test public void ParentFrameExists_false() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_parentFrameExists, ObjectUtl.AryEmpty, false);
	}
	@Test public void FrameExists_false() { // no args should not throw error; PAGE:fr.u:Projet:Laboratoire/Espaces_de_noms/Modèle/Liste_des_pages DATE:2017-05-28
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_frameExists, ObjectUtl.AryEmpty, false);
	}
	@Test public void GetAllExpandedArguments() {
		fxt.Init_frame_current(KeyVal.NewStr("k1", "v1"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, ObjectUtl.Ary("current"), "1=\n  k1=v1");
	}
	@Test public void GetAllExpandedArguments_parent() {
		fxt.Init_frame_parent("test", KeyVal.NewStr("1", "a1"), KeyVal.NewStr("2", "a2"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, ObjectUtl.Ary("parent"), "1=\n  1=a1\n  2=a2");
	}
	@Test public void GetAllExpandedArguments__zero_padded_number() {	// PURPOSE:  DATE:2016-11-23
		fxt.Init_frame_current(KeyVal.NewStr("01", "v1"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, ObjectUtl.Ary("current"), "1=\n  01=v1");
	}
	@Test public void GetExpandedArgument() {
		fxt.Init_frame_current(KeyVal.NewInt(1, "val_1"), KeyVal.NewStr("key_2", "val_2"), KeyVal.NewInt(3, "val_3"));
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "1")		, "val_1");		// get 1st by idx
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "2")		, "val_3");		// get 2nd by idx (which is "3", not "key_2)
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "3")		, null);		// get 3rd as null
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "key_2")	, "val_2");		// get key_2
		fxt.Test_scrib_proc_str		(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "key_3")	, null);		// key_3 n/a
	}
	@Test public void GetExpandedArgument_parent() {
		fxt.Init_frame_parent ("test", KeyVal.NewStr("1", "a1"), KeyVal.NewStr("2", "a2"));
		fxt.Init_frame_current(KeyVal.NewStr("2", "b2"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("parent", "1"), "a1");
	}
	@Test public void GetExpandedArgument_numeric_key() {		// PURPOSE.FIX: frame.args[1] was ignoring "1=val_1" b/c it was looking for 1st unnamed arg (and 1 is the name for "1=val_1")
		fxt.Init_frame_current(KeyVal.NewStr("1", "val_1"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "1"), "val_1");			// get 1st by idx, even though idx is String
	}
	@Test public void GetExpandedArgument_numeric_key_2() {	// PURPOSE.FIX: same as above, but for parent context; DATE:2013-09-23
		fxt.Init_frame_parent ("test", KeyVal.NewStr("2", "a1"));
		fxt.Init_frame_current(KeyVal.NewStr("2", "a2"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("parent", "1"), null);				// PAGE:en.w:Sainte-Catherine,_Quebec; DATE:2017-09-16
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("parent", "2"), "a1");				// get 1st by idx, even though idx is String
	}
	@Test public void GetExpandedArgument_ws() {		// PURPOSE: key must trim ws; EX: "1 =val_1"; PAGE:c:File:Torsåker_kyrka01.JPG; DATE:2017-10-23
		fxt.Init_frame_current(KeyVal.NewStr(" 1 ", "val_1"));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("current", "1"), "val_1");
	}
	@Test public void GetExpandedArgument_out_of_bounds() {
		fxt.Init_frame_parent ("test");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getExpandedArgument, ObjectUtl.Ary("parent", "2"), null);
	}
	@Test public void IsSubsting() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_mw.Invk_isSubsting, ObjectUtl.AryEmpty, false);
	}
	@Test public void GetFrameTitle_current() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getFrameTitle, ObjectUtl.Ary("current")	, "Module:Mod_0");
	}
	@Test public void GetFrameTitle_parent() {
		fxt.Init_frame_parent("Template:Test A");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getFrameTitle, ObjectUtl.Ary("parent")	, "Template:Test A");
	}
	@Test public void GetFrameTitle_empty() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_getFrameTitle, ObjectUtl.Ary("empty")	, Scrib_invoke_func_fxt.Null_rslt);
	}
	@Test public void NewChildFrame() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_newChildFrame, ObjectUtl.Ary("current", "Page_0", KeyValUtl.Ary(KeyVal.NewStr("key1", "val1"))), "frame0");
	}
	@Test public void ExpandTemplate__null_arg() {	// PURPOSE: auto-fill in arg "1" b/c "2" is specified; PAGE:en.w:Category:Nouns_by_language DATE:2016-04-29
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Parser_fxt().Data_create("Template:A", "b{{{1}}}c");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_expandTemplate, ObjectUtl.Ary("current", "A", KeyValUtl.Ary(KeyVal.NewInt(2, "x"))), "bc");	// fails if "bxc"; note that "2" is passed, but should not be read as "1"
	}
	@Test public void ExpandTemplate__missing_template() {// PURPOSE: return error if template is missing; PAGE:en.w:Flag_of_Greenland DATE:2016-05-02
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_scrib_proc_err(lib, Scrib_lib_mw.Invk_expandTemplate, ObjectUtl.Ary("current", "A", KeyValUtl.Ary(KeyVal.NewInt(2, "need to pass arg to reach error message"))),  "expandTemplate: template \"A\" does not exist");
	}
	@Test public void ExpandTemplate__invalid_title() {// PURPOSE: return error if title is invalid; PAGE:en.w:Tetris DATE:2017-04-09
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_scrib_proc_err(lib, Scrib_lib_mw.Invk_expandTemplate, ObjectUtl.Ary("current", "[[A]]"),  "expandTemplate: invalid title \"[[A]]\"");
	}
	@Test public void SetTTL() {
		fxt.Test_scrib_proc_empty(lib, Scrib_lib_mw.Invk_setTTL, ObjectUtl.Ary(123));
		GfoTstr.EqObj(123, fxt.Core().Frame_current().Frame_lifetime());
	}
	@Test public void LoadPHPLibrary() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_mw.Invk_loadPHPLibrary, ObjectUtl.Ary("mock_library")	, Scrib_invoke_func_fxt.Null_rslt);
	}
}
