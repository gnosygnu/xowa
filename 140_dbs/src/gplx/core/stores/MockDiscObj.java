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
package gplx.core.stores; import gplx.*; import gplx.core.*;
class MockDisc implements SrlObj, Gfo_invk {
	public int Id() {return id;} public MockDisc Id_(int val) {id = val; return this;} int id; public static final    String id_idk = "id";
	public String Name() {return name;} public MockDisc Name_(String val) {name = val; return this;} private String name; public static final    String name_idk = "name";
	public List_adp Titles() {return titles;} List_adp titles = List_adp_.New(); public static final    String titles_idk = "titles";
	public static final    MockDisc Instance = new MockDisc(); MockDisc() {}
	public SrlObj SrlObj_New(Object o)	{return new MockDisc();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, id_idk))				return Id();
		else if	(ctx.Match(k, name_idk))			return Name();
		else if	(ctx.Match(k, titles_idk))			return Titles();
		else return Gfo_invk_.Rv_unhandled;
	}
//		public Object Srl_new(GfsCtx ctx) {return new MockDisc();}
//		public void Srl(GfsCtx ctx, int ikey, String k, GfoMsg m) {
//			id = m.ReadIntOr(id_idk, id);
//			name = m.ReadStrOr(name_idk, name);
//			for (int i = 0; i < m.Subs_count(); i++) {
//				GfoMsg subMsg = m.Subs_getAt(i);
//				if		(String_.Eq(subMsg.Key(), titles_idk)) DoIt(ctx, ikey, k, subMsg, titles, MockTitle.Instance, "title");
//			}			
//		}
//		public static void DoIt(GfsCtx ctx, int ikey, String k, GfoMsg m, List_adp list, Object o, String subKey) {
//		}
	public void SrlObj_Srl(SrlMgr mgr) {
		id = mgr.SrlIntOr(id_idk, id);
		name = mgr.SrlStrOr(name_idk, name);
		mgr.SrlList(titles_idk, titles, MockTitle.Instance, "title");
	}
	public static MockDisc new_() {
		MockDisc rv = new MockDisc();
		return rv;
	}
}
class MockTitle implements SrlObj, Gfo_invk {
	public int Id() {return id;} public MockTitle Id_(int val) {id = val; return this;} int id; public static final    String id_idk = "id";
	public String Name() {return name;} public MockTitle Name_(String val) {name = val; return this;} private String name; public static final    String name_idk = "name";
	public List_adp Chapters() {return chapters;} List_adp chapters = List_adp_.New(); public static final    String chapters_idk = "chapters";
	public List_adp Audios() {return audios;} List_adp audios = List_adp_.New(); public static final    String audios_idk = "audios";
	public List_adp Subtitles() {return subtitles;} List_adp subtitles = List_adp_.New(); public static final    String subtitles_idk = "subtitles";
	public MockTitle Disc_(MockDisc disc) {disc.Titles().Add(this); return this;}
	public static MockTitle new_() {
		MockTitle rv = new MockTitle();
		return rv;
	}
	public static final    MockTitle Instance = new MockTitle(); MockTitle() {}
	public SrlObj SrlObj_New(Object o)	{return new MockTitle();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, id_idk))				return Id();
		else if	(ctx.Match(k, name_idk))			return Name();
		else if	(ctx.Match(k, chapters_idk))		return Chapters();
		else if	(ctx.Match(k, audios_idk))			return Audios();
		else if	(ctx.Match(k, subtitles_idk))		return Subtitles();
		else return Gfo_invk_.Rv_unhandled;
	}
//		public void Srl(GfsCtx ctx, int ikey, String k, GfoMsg m) {
//			id = m.ReadIntOr(id_idk, id);
//			name = m.ReadStrOr(name_idk, name);
//			for (int i = 0; i < m.Subs_count(); i++) {
//				GfoMsg subMsg = m.Subs_getAt(i);
//				if		(String_.Eq(subMsg.Key(), chapters_idk))	MockDisc.DoIt(ctx, ikey, k, subMsg, chapters, MockChapter.Instance, "chapter");
//				else if	(String_.Eq(subMsg.Key(), audios_idk))		MockDisc.DoIt(ctx, ikey, k, subMsg, audios, MockStream.Instance, "audio");
//				else if	(String_.Eq(subMsg.Key(), subtitles_idk))	MockDisc.DoIt(ctx, ikey, k, subMsg, subtitles, MockStream.Instance, "subtitle");
//			}			
//		}
	public void SrlObj_Srl(SrlMgr mgr) {
		id = mgr.SrlIntOr(id_idk, id);
		name = mgr.SrlStrOr(name_idk, name);
		mgr.SrlList(chapters_idk, chapters, MockChapter.Instance, "chapter");
		mgr.SrlList(audios_idk, audios, MockStream.Instance, "audio");
		mgr.SrlList(subtitles_idk, subtitles, MockStream.Instance, "subtitle");
	}
}
class MockChapter implements SrlObj, Gfo_invk {
	public int Id() {return id;} public MockChapter Id_(int val) {id = val; return this;} int id; public static final    String id_idk = "id";
	public String Name() {return name;} public MockChapter Name_(String val) {name = val; return this;} private String name; public static final    String name_idk = "name";
	public MockChapter Title_(MockTitle title) {title.Chapters().Add(this); return this;}
	public static final    MockChapter Instance = new MockChapter(); MockChapter() {}
	public static MockChapter new_() {
		MockChapter rv = new MockChapter();
		return rv;
	}
	public SrlObj SrlObj_New(Object o) {return new MockChapter();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, id_idk))				return Id();
		else if	(ctx.Match(k, name_idk))			return Name();
		else return Gfo_invk_.Rv_unhandled;
	}
	public void SrlObj_Srl(SrlMgr mgr) {
		id = mgr.SrlIntOr(id_idk, id);
		name = mgr.SrlStrOr(name_idk, name);
	}
}
class MockStream implements SrlObj, Gfo_invk {
	public int Id() {return id;} public MockStream Id_(int val) {id = val; return this;} int id; public static final    String id_idk = "id";
	public String Name() {return name;} public MockStream Name_(String val) {name = val; return this;} private String name; public static final    String name_idk = "name";
	public MockStream Title_(List_adp list) {list.Add(this); return this;}
	public static final    MockStream Instance = new MockStream(); MockStream() {}
	public static MockStream new_() {
		MockStream rv = new MockStream();
		return rv;
	}
	public SrlObj SrlObj_New(Object o)	{return new MockStream();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, id_idk))				return Id();
		else if	(ctx.Match(k, name_idk))			return Name();
		else return Gfo_invk_.Rv_unhandled;
	}
	public void SrlObj_Srl(SrlMgr mgr) {
		id = mgr.SrlIntOr(id_idk, id);
		name = mgr.SrlStrOr(name_idk, name);
	}
}
