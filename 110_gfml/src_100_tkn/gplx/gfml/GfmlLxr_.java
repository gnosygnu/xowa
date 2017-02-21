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
package gplx.gfml; import gplx.*;
import gplx.core.strings.*;
import gplx.core.texts.*; /*CharStream*/	
public class GfmlLxr_ {
	public static GfmlLxr general_(String key, GfmlTkn protoTkn) {return GfmlLxr_general.new_(key, protoTkn);}
	public static GfmlLxr solo_(String key, GfmlTkn singletonTkn) {return GfmlLxr_singleton.new_(key, singletonTkn.Raw(), singletonTkn);}
	public static GfmlLxr range_(String key, String[] ary, GfmlTkn protoTkn, boolean ignoreOutput) {return GfmlLxr_group.new_(key, ary, protoTkn, ignoreOutput);}

	@gplx.Internal protected static GfmlLxr symbol_(String key, String raw, String val, GfmlBldrCmd cmd) {
		GfmlTkn tkn = GfmlTkn_.singleton_(key, raw, val, cmd);
		return GfmlLxr_.solo_(key, tkn);
	}
	@gplx.Internal protected static GfmlLxr frame_(String key, GfmlFrame frame, String bgn, String end) {return GfmlLxr_frame.new_(key, frame, bgn, end, GfmlBldrCmd_pendingTkns_add.Instance, GfmlBldrCmd_frameEnd.data_());}
	public static final    GfmlLxr Null = new GfmlLxr_null();
	public static final    String CmdTknChanged_evt = "Changed";
	public static GfmlLxr as_(Object obj) {return obj instanceof GfmlLxr ? (GfmlLxr)obj : null;}
	public static GfmlLxr cast(Object obj) {try {return (GfmlLxr)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfmlLxr.class, obj);}}
}
class GfmlLxr_null implements GfmlLxr {
	public String Key() {return "gfml.nullLxr";}
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
	public GfmlTkn CmdTkn() {return GfmlTkn_.Null;} public void CmdTkn_set(GfmlTkn val) {}
	public String[] Hooks() {return String_.Ary_empty;}
	public GfmlTkn MakeTkn(CharStream stream, int hookLength) {return GfmlTkn_.Null;}
	public void SubLxr_Add(GfmlLxr... lexer) {}
	public GfmlLxr SubLxr() {return this;}
}
class GfmlLxr_singleton implements GfmlLxr, Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
	public String Key() {return key;} private String key;
	public GfmlTkn CmdTkn() {return singletonTkn;} GfmlTkn singletonTkn; 
	public void CmdTkn_set(GfmlTkn val) {
		String oldRaw = singletonTkn.Raw();
		singletonTkn = val;
		hooks = String_.Ary(val.Raw());
		Gfo_evt_mgr_.Pub_vals(this, GfmlLxr_.CmdTknChanged_evt, Keyval_.new_("old", oldRaw), Keyval_.new_("new", val.Raw()), Keyval_.new_("lxr", this));
	}
	public String[] Hooks() {return hooks;} private String[] hooks;
	public GfmlTkn MakeTkn(CharStream stream, int hookLength) {
		stream.MoveNextBy(hookLength);
		return singletonTkn;
	}
	public GfmlLxr SubLxr() {return subLxr;} GfmlLxr subLxr;
	public void SubLxr_Add(GfmlLxr... lexer) {subLxr.SubLxr_Add(lexer);}
	public static GfmlLxr_singleton new_(String key, String hook, GfmlTkn singletonTkn) {
		GfmlLxr_singleton rv = new GfmlLxr_singleton();
		rv.ctor_(key, hook, singletonTkn, GfmlLxr_.Null);
		return rv;
	}	protected GfmlLxr_singleton() {}
	@gplx.Internal protected void ctor_(String key, String hook, GfmlTkn singletonTkn, GfmlLxr subLxr) {
		this.key = key;
		this.hooks = String_.Ary(hook); 
		this.subLxr = subLxr;
		this.singletonTkn = singletonTkn;
	}
}
class GfmlLxr_group implements GfmlLxr {
	public String Key() {return key;} private String key;
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
	public GfmlTkn CmdTkn() {return outputTkn;} public void CmdTkn_set(GfmlTkn val) {} GfmlTkn outputTkn;
	public String[] Hooks() {return trie.Symbols();}
	public GfmlTkn MakeTkn(CharStream stream, int hookLength) {
		while (stream.AtMid()) {
			if (!ignoreOutput)
				sb.Add_mid(stream.Ary(), stream.Pos(), hookLength);
			stream.MoveNextBy(hookLength);

			String found = String_.cast(trie.FindMatch(stream));
			if (found == null) break;
			hookLength = trie.LastMatchCount;
		}
		if (ignoreOutput) return GfmlTkn_.IgnoreOutput;
		String raw = sb.To_str_and_clear();
		return outputTkn.MakeNew(raw, raw);
	}
	public GfmlLxr SubLxr() {throw Err_sublxr();}
	public void SubLxr_Add(GfmlLxr... lexer) {throw Err_sublxr();}
	Err Err_sublxr() {return Err_.new_unimplemented_w_msg("group lxr does not have subLxrs", "key", key, "output_tkn", outputTkn.Raw()).Trace_ignore_add_1_();}
	GfmlTrie trie = GfmlTrie.new_(); String_bldr sb = String_bldr_.new_(); boolean ignoreOutput;
	public static GfmlLxr_group new_(String key, String[] hooks, GfmlTkn outputTkn, boolean ignoreOutput) {
		GfmlLxr_group rv = new GfmlLxr_group();
		rv.key = key;
		for (String hook : hooks)
			rv.trie.Add(hook, hook);
		rv.outputTkn = outputTkn; rv.ignoreOutput = ignoreOutput;
		return rv;
	}	GfmlLxr_group() {}
}
class GfmlLxr_general implements GfmlLxr, Gfo_invk {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public String Key() {return key;} private String key;
	public GfmlTkn CmdTkn() {return txtTkn;} public void CmdTkn_set(GfmlTkn val) {} GfmlTkn txtTkn;
	public String[] Hooks() {return symTrie.Symbols();}
	public GfmlTkn MakeTkn(CharStream stream, int firstTknLength) {
		GfmlTkn rv = null;
		if (symLxr != null) {												//	symLxr has something; produce
			rv = MakeTkn_symLxr(stream);
			if (rv != GfmlTkn_.IgnoreOutput) return rv;
		}
		while (stream.AtMid()) {											//	keep moving til (a) symChar or (b) endOfStream
			Object result = symTrie.FindMatch(stream);
			symTknLen = symTrie.LastMatchCount;
			if (result == null) {											//	no match; must be txtChar;
				txtBfr.Add(stream);
				stream.MoveNext();
			}
			else {															//	symChar
				symLxr = (GfmlLxr)result;									//	set symLxr for next pass
				if (txtBfr.Has())											//		txtBfr has something: gen txtTkn
					rv = txtBfr.MakeTkn(stream, txtTkn);
				else {														//		txtBfr empty: gen symbol
					rv = MakeTkn_symLxr(stream);
					if (rv == GfmlTkn_.IgnoreOutput) continue;
				}
				return rv;
			}
		}
		if (txtBfr.Has())													//	endOfStream, but txtBfr has chars
			return txtBfr.MakeTkn(stream, txtTkn);
		return GfmlTkn_.EndOfStream;
	}
	public void SubLxr_Add(GfmlLxr... lxrs) {
		for (GfmlLxr lxr : lxrs) {
			for (String hook : lxr.Hooks())
				symTrie.Add(hook, lxr);
			Gfo_evt_mgr_.Sub_same(lxr, GfmlLxr_.CmdTknChanged_evt, this);
		}
	}
	public GfmlLxr SubLxr() {return this;}
	GfmlTkn MakeTkn_symLxr(CharStream stream) {
		GfmlLxr lexer = symLxr; symLxr = null; 
		int length = symTknLen;	symTknLen = 0;
		return lexer.MakeTkn(stream, length);
	}
	GfmlLxr_general_txtBfr txtBfr = new GfmlLxr_general_txtBfr(); GfmlTrie symTrie = GfmlTrie.new_(); GfmlLxr symLxr; int symTknLen; 
	@gplx.Internal protected static GfmlLxr_general new_(String key, GfmlTkn txtTkn) {
		GfmlLxr_general rv = new GfmlLxr_general();
		rv.key = key; rv.txtTkn = txtTkn;
		return rv;
	}	protected GfmlLxr_general() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, GfmlLxr_.CmdTknChanged_evt))			{
			symTrie.Del(m.ReadStr("old"));
			symTrie.Add(m.ReadStr("new"), m.CastObj("lxr"));
		}
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
}
class GfmlLxr_general_txtBfr {
	public int Bgn = NullPos;
	public int Len;
	public boolean Has() {return Bgn != NullPos;}
	public void Add(CharStream stream) {
		if (Bgn == NullPos) Bgn = stream.Pos();
		Len++;
	}	static final    int NullPos = -1;
	public GfmlTkn MakeTkn(CharStream stream, GfmlTkn textTkn) {
		String raw = String_.new_charAry_(stream.Ary(), Bgn, Len);
		Bgn = -1; Len = 0;
		return textTkn.MakeNew(raw, raw);
	}
}
class GfmlLxr_frame extends GfmlLxr_singleton {		GfmlFrame frame; GfmlLxr endLxr, txtLxr;
	public void BgnRaw_set(String val) {// needed for lxr pragma
		GfmlBldrCmd_frameBgn bgnCmd = GfmlBldrCmd_frameBgn.new_(frame, txtLxr);
		GfmlTkn bgnTkn = GfmlTkn_.singleton_(this.Key() + "_bgn", val, GfmlTkn_.NullVal, bgnCmd);
		this.CmdTkn_set(bgnTkn);
	}
	public void EndRaw_set(String val) {// needed for lxr pragma
		GfmlBldrCmd_frameEnd endCmd = GfmlBldrCmd_frameEnd.data_();
		GfmlTkn endTkn = GfmlTkn_.singleton_(this.Key() + "_end", val, GfmlTkn_.NullVal, endCmd);
		endLxr.CmdTkn_set(endTkn);
	}
	public static GfmlLxr new_(String key, GfmlFrame frame, String bgn, String end, GfmlBldrCmd txtCmd, GfmlBldrCmd endCmd) {
		GfmlLxr_frame rv = new GfmlLxr_frame();
		GfmlTkn txtTkn = frame.FrameType() == GfmlFrame_.Type_comment
			? GfmlTkn_.valConst_(key + "_txt", GfmlTkn_.NullVal, txtCmd)
			: GfmlTkn_.cmd_(key + "_txt", txtCmd)
			;
		GfmlLxr txtLxr = GfmlLxr_.general_(key + "_txt", txtTkn);

		GfmlTkn bgnTkn = GfmlTkn_.singleton_(key + "_bgn", bgn, GfmlTkn_.NullVal, GfmlBldrCmd_frameBgn.new_(frame, txtLxr));
		rv.ctor_(key, bgn, bgnTkn, txtLxr);

		GfmlTkn endTkn = GfmlTkn_.singleton_(key + "_end", end, GfmlTkn_.NullVal, endCmd);
		GfmlLxr endLxr = GfmlLxr_.solo_(key + "_end", endTkn);
		rv.SubLxr_Add(endLxr);

		rv.frame = frame;
		rv.endLxr = endLxr;
		rv.txtLxr = txtLxr;
		return rv;
	}	GfmlLxr_frame() {}
	public static GfmlLxr_frame as_(Object obj) {return obj instanceof GfmlLxr_frame ? (GfmlLxr_frame)obj : null;}
	public static GfmlLxr_frame cast(Object obj) {try {return (GfmlLxr_frame)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfmlLxr_frame.class, obj);}}
}
